
import java.util.concurrent.atomic.AtomicBoolean;

public class ParkingSlot {

    private final String slotId;
    private final int floorNumber;
    private final int position;
    private final SlotType slotType;
    private final AtomicBoolean occupied = new AtomicBoolean(false);

    public ParkingSlot(String slotId, int floorNumber, int position, SlotType slotType) {
        this.slotId = slotId;
        this.floorNumber = floorNumber;
        this.position = position;
        this.slotType = slotType;
    }

    public String getSlotId() {
        return slotId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getPosition() {
        return position;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public boolean isOccupied() {
        return occupied.get();
    }

    public boolean tryReserve() {
        return occupied.compareAndSet(false, true);
    }

    public boolean release() {
        return occupied.compareAndSet(true, false);
    }
}
