
import java.util.Optional;

public interface SlotAllocator {

    Optional<ParkingSlot> allocateNearestAvailableSlot(SlotType requestedType, EntryGate gate);
}
