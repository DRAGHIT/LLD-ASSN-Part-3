
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class NearestSlotAllocator implements SlotAllocator {

    private final List<ParkingSlot> allSlots;

    public NearestSlotAllocator(List<ParkingSlot> allSlots) {
        this.allSlots = allSlots;
    }

    @Override
    public Optional<ParkingSlot> allocateNearestAvailableSlot(SlotType requestedType, EntryGate gate) {
        List<ParkingSlot> candidates = new ArrayList<>();
        for (ParkingSlot slot : allSlots) {
            if (slot.getSlotType() == requestedType && !slot.isOccupied()) {
                candidates.add(slot);
            }
        }
        candidates.sort(Comparator
                .comparingInt((ParkingSlot s) -> distanceScore(s, gate))
                .thenComparing(ParkingSlot::getSlotId));

        for (ParkingSlot slot : candidates) {
            if (slot.tryReserve()) {
                return Optional.of(slot);
            }
        }
        return Optional.empty();
    }

    private int distanceScore(ParkingSlot slot, EntryGate gate) {
        int floorDiff = Math.abs(slot.getFloorNumber() - gate.getFloorNumber());
        int posDiff = Math.abs(slot.getPosition() - gate.getPosition());
        return floorDiff * 1000 + posDiff;
    }
}
