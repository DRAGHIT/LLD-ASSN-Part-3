
import java.time.LocalDateTime;

public interface PricingPolicy {

    double calculateAmount(SlotType slotType, LocalDateTime entryTime, LocalDateTime exitTime);
}
