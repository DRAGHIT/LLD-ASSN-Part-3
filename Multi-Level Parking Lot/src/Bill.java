
import java.time.LocalDateTime;

public class Bill {

    private final String ticketId;
    private final LocalDateTime exitTime;
    private final long billedMinutes;
    private final double amount;

    public Bill(String ticketId, LocalDateTime exitTime, long billedMinutes, double amount) {
        this.ticketId = ticketId;
        this.exitTime = exitTime;
        this.billedMinutes = billedMinutes;
        this.amount = amount;
    }

    public String getTicketId() {
        return ticketId;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public long getBilledMinutes() {
        return billedMinutes;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Bill{"
                + "ticketId='" + ticketId + '\''
                + ", exitTime=" + exitTime
                + ", billedMinutes=" + billedMinutes
                + ", amount=" + amount
                + '}';
    }
}
