
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class HourlyPricingPolicy implements PricingPolicy {

    private final Map<SlotType, Double> ratePerHour = new EnumMap<>(SlotType.class);

    public HourlyPricingPolicy(double smallRate, double mediumRate, double largeRate) {
        ratePerHour.put(SlotType.SMALL, smallRate);
        ratePerHour.put(SlotType.MEDIUM, mediumRate);
        ratePerHour.put(SlotType.LARGE, largeRate);
    }

    @Override
    public double calculateAmount(SlotType slotType, LocalDateTime entryTime, LocalDateTime exitTime) {
        if (exitTime.isBefore(entryTime)) {
            throw new IllegalArgumentException("Exit time cannot be before entry time");
        }
        long mins = Duration.between(entryTime, exitTime).toMinutes();
        long hours = Math.max(1, (long) Math.ceil(mins / 60.0));
        return hours * ratePerHour.get(slotType);
    }
}
