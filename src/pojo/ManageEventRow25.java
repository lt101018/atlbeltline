package pojo;

public class ManageEventRow25 {

    private String name;
    private int staffCount;
    private int duration;
    private int totalVisits;
    private int totalRevenue;

    public ManageEventRow25(String name, int staffCount, int duration, int totalVisits, int totalRevenue) {
        this.name = name;
        this.staffCount = staffCount;
        this.duration = duration;
        this.totalVisits = totalVisits;
        this.totalRevenue = totalRevenue;
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

    public int getTotalRevenue() {
        return totalRevenue;
    }
}
