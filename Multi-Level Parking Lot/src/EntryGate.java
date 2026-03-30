
public class EntryGate {

    private final String gateId;
    private final int floorNumber;
    private final int position;

    public EntryGate(String gateId, int floorNumber, int position) {
        this.gateId = gateId;
        this.floorNumber = floorNumber;
        this.position = position;
    }

    public String getGateId() {
        return gateId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getPosition() {
        return position;
    }
}
