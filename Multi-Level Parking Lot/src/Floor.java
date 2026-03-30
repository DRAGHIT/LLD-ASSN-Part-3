
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Floor {

    private final int floorNumber;
    private final List<ParkingSlot> slots;

    public Floor(int floorNumber, List<ParkingSlot> slots) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>(slots);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }
}
