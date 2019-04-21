package pojo;

public class ViewEventsRow26 {
    private String date;
    private int dailyVisits;
    private double dailyRevenue;

    public ViewEventsRow26(String date, int dailyVisits, double dailyRevenue) {
        this.date = date;
        this.dailyVisits = dailyVisits;
        this.dailyRevenue = dailyRevenue;
    }

    public String getDate() {
        return date;
    }

    public int getDailyVisits() {
        return dailyVisits;
    }

    public double getDailyRevenue() {
        return dailyRevenue;
    }
}
