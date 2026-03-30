
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParkingTicket {

    private final String ticketId;
    private final Vehicle vehicle;
    private final LocalDateTime entryTime;
    private final EntryGate entryGate;
    private final ParkingSlot assignedSlot;
    private final AtomicBoolean active = new AtomicBoolean(true);

    public ParkingTicket(Vehicle vehicle, LocalDateTime entryTime, EntryGate entryGate, ParkingSlot assignedSlot) {
        this.ticketId = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.entryTime = entryTime;
        this.entryGate = entryGate;
        this.assignedSlot = assignedSlot;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public EntryGate getEntryGate() {
        return entryGate;
    }

    public ParkingSlot getAssignedSlot() {
        return assignedSlot;
    }

    public boolean closeTicket() {
        return active.compareAndSet(true, false);
    }

    public boolean isActive() {
        return active.get();
    }
}
