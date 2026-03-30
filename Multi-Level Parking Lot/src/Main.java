
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<EntryGate> gates = List.of(
                new EntryGate("G1", 0, 2),
                new EntryGate("G2", 1, 7),
                new EntryGate("G3", 2, 1)
        );

        List<ParkingSlot> slots = new ArrayList<>();
        slots.add(new ParkingSlot("S-0-1", 0, 1, SlotType.SMALL));
        slots.add(new ParkingSlot("S-0-5", 0, 5, SlotType.SMALL));
        slots.add(new ParkingSlot("M-0-3", 0, 3, SlotType.MEDIUM));
        slots.add(new ParkingSlot("M-1-8", 1, 8, SlotType.MEDIUM));
        slots.add(new ParkingSlot("L-2-1", 2, 1, SlotType.LARGE));
        slots.add(new ParkingSlot("L-2-9", 2, 9, SlotType.LARGE));

        SlotAllocator allocator = new NearestSlotAllocator(slots);
        PricingPolicy pricingPolicy = new HourlyPricingPolicy(20, 40, 100);
        ParkingLotService parkingLotService = new ParkingLotService(gates, slots, allocator, pricingPolicy);

        Vehicle v1 = new Vehicle("DL01AA1111", VehicleType.CAR);
        LocalDateTime t1 = LocalDateTime.now().minusMinutes(95);
        ParkingTicket ticket1 = parkingLotService.generateParkingTicket(v1, t1, SlotType.MEDIUM, "G1");

        Vehicle v2 = new Vehicle("DL02BB2222", VehicleType.BUS);
        LocalDateTime t2 = LocalDateTime.now().minusMinutes(135);
        ParkingTicket ticket2 = parkingLotService.generateParkingTicket(v2, t2, SlotType.LARGE, "G3");

        System.out.println("Status after entries: " + parkingLotService.getParkingLotStatus());

        Bill b1 = parkingLotService.generateBill(ticket1, LocalDateTime.now());
        System.out.println("Bill1: " + b1);

        Bill b2 = parkingLotService.generateBill(ticket2, LocalDateTime.now());
        System.out.println("Bill2: " + b2);

        System.out.println("Final status: " + parkingLotService.getParkingLotStatus());
    }
}
