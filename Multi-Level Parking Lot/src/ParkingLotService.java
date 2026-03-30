
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLotService {

    private final Map<String, EntryGate> gateMap = new ConcurrentHashMap<>();
    private final List<ParkingSlot> allSlots;
    private final SlotAllocator allocator;
    private final PricingPolicy pricingPolicy;
    private final Map<String, ParkingTicket> activeTickets = new ConcurrentHashMap<>();

    public ParkingLotService(List<EntryGate> gates, List<ParkingSlot> slots, SlotAllocator allocator, PricingPolicy pricingPolicy) {
        for (EntryGate gate : gates) {
            gateMap.put(gate.getGateId(), gate);
        }
        this.allSlots = slots;
        this.allocator = allocator;
        this.pricingPolicy = pricingPolicy;
    }

    public ParkingTicket generateParkingTicket(Vehicle vehicle, LocalDateTime entryTime, SlotType requestedSlotType, String entryGateId) {
        EntryGate gate = gateMap.get(entryGateId);
        if (gate == null) {
            throw new IllegalArgumentException("Invalid gate id: " + entryGateId);
        }
        Optional<ParkingSlot> opt = allocator.allocateNearestAvailableSlot(requestedSlotType, gate);
        if (opt.isEmpty()) {
            throw new IllegalStateException("No slot available for type " + requestedSlotType);
        }
        ParkingTicket ticket = new ParkingTicket(vehicle, entryTime, gate, opt.get());
        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    public Bill generateBill(ParkingTicket ticket, LocalDateTime exitTime) {
        ParkingTicket current = activeTickets.get(ticket.getTicketId());
        if (current == null) {
            throw new IllegalArgumentException("Ticket not found or already closed: " + ticket.getTicketId());
        }
        if (!current.closeTicket()) {
            throw new IllegalStateException("Bill already generated for ticket: " + ticket.getTicketId());
        }

        activeTickets.remove(ticket.getTicketId());
        boolean released = current.getAssignedSlot().release();
        if (!released) {
            throw new IllegalStateException("Slot release failed for ticket: " + ticket.getTicketId());
        }

        long mins = Duration.between(current.getEntryTime(), exitTime).toMinutes();
        if (mins < 0) {
            throw new IllegalArgumentException("Exit time cannot be before entry time");
        }
        double amount = pricingPolicy.calculateAmount(current.getAssignedSlot().getSlotType(), current.getEntryTime(), exitTime);
        return new Bill(current.getTicketId(), exitTime, mins, amount);
    }

    public ParkingLotStatus getParkingLotStatus() {
        Map<SlotType, Integer> counts = new EnumMap<>(SlotType.class);
        counts.put(SlotType.SMALL, 0);
        counts.put(SlotType.MEDIUM, 0);
        counts.put(SlotType.LARGE, 0);

        for (ParkingSlot slot : allSlots) {
            if (!slot.isOccupied()) {
                counts.put(slot.getSlotType(), counts.get(slot.getSlotType()) + 1);
            }
        }
        return new ParkingLotStatus(counts);
    }
}
