
import java.util.EnumMap;
import java.util.Map;

public class ParkingLotStatus {

    private final Map<SlotType, Integer> availableByType;

    public ParkingLotStatus(Map<SlotType, Integer> availableByType) {
        this.availableByType = new EnumMap<>(availableByType);
    }

    public Map<SlotType, Integer> getAvailableByType() {
        return new EnumMap<>(availableByType);
    }

    @Override
    public String toString() {
        return "ParkingLotStatus{"
                + "availableByType=" + availableByType
                + '}';
    }
}
