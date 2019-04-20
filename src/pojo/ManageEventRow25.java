package pojo;

public class ManageEventRow25 {

    private String name;
    private int staffCount;
    private int duration;
    private int totalVisits;
    private double totalRevenue;
    private String siteName;
    private String startDate;

    public String getSiteName() {
        return siteName;
    }

    public String getStartDate() {
        return startDate;
    }

    public ManageEventRow25(String name, int staffCount, int duration, int totalVisits, double totalRevenue, String siteName, String startDate) {
        this.name = name;
        this.staffCount = staffCount;
        this.duration = duration;
        this.totalVisits = totalVisits;
        this.totalRevenue = totalRevenue;
        this.siteName = siteName;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public int getStaffCount() {
        return staffCount;
    }

    public int getDuration() {
        return duration;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
