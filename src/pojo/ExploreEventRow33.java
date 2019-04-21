package pojo;

public class ExploreEventRow33 {

    private String eventName;
    private String siteName;
    private double ticketPrice;
    private int ticketRemaining;
    private int totalVisits;
    private int myVisits;
    private String startDate;
    private String endDate;
    private String description;

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getEventName() {
        return eventName;
    }

    public String getSiteName() {
        return siteName;
    }

    public double getTicketPrice() {
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

    public ExploreEventRow33(String eventName, String siteName, double ticketPrice, int ticketRemaining, int totalVisits, int myVisits, String startDate, String endDate, String description) {
        this.eventName = eventName;
        this.siteName = siteName;
        this.ticketPrice = ticketPrice;
        this.ticketRemaining = ticketRemaining;
        this.totalVisits = totalVisits;
        this.myVisits = myVisits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

}
