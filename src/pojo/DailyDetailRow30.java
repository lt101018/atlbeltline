package pojo;

public class DailyDetailRow30 {
    private String eventName;
    private String staffNames;
    private int visits;
    private int revenue;

    public String getEventName() {
        return eventName;
    }

    public String getStaffNames() {
        return staffNames;
    }

    public int getVisits() {
        return visits;
    }

    public int getRevenue() {
        return revenue;
    }

    public DailyDetailRow30(String eventName, String staffNames, int visits, int revenue) {
        this.eventName = eventName;
        this.staffNames = staffNames;
        this.visits = visits;
        this.revenue = revenue;
    }
}
