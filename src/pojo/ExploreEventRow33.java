package pojo;

public class ExploreEventRow33 {

    private String eventName;
    private String siteName;
    private int ticketPrice;
    private int ticketRemaining;
    private int totalVisits;
    private int myVisits;

    public String getEventName() {
        return eventName;
    }

    public String getSiteName() {
        return siteName;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public int getTicketRemaining() {
        return ticketRemaining;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public int getMyVisits() {
        return myVisits;
    }

    public ExploreEventRow33(String eventName, String siteName, int ticketPrice, int ticketRemaining, int totalVisits, int myVisits) {
        this.eventName = eventName;
        this.siteName = siteName;
        this.ticketPrice = ticketPrice;
        this.ticketRemaining = ticketRemaining;
        this.totalVisits = totalVisits;
        this.myVisits = myVisits;
    }
}
