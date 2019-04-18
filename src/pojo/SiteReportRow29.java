package pojo;

public class SiteReportRow29 {
    private String date;
    private int eventCount;
    private int staffCount;
    private int totalVisits;
    private int totalRevenue;

    public SiteReportRow29(String date, int eventCount, int staffCount, int totalVisits, int totalRevenue) {
        this.date = date;
        this.eventCount = eventCount;
        this.staffCount = staffCount;
        this.totalVisits = totalVisits;
        this.totalRevenue = totalRevenue;
    }

    public String getDate() {
        return date;
    }

    public int getEventCount() {
        return eventCount;
    }

    public int getStaffCount() {
        return staffCount;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }
}
