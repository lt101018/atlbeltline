package pojo;

public class ExploreSiteRow35 {

    private String siteName;
    private int eventCount;
    private int totalVisits;
    private int myVisits;

    public String getSiteName() {
        return siteName;
    }

    public int getEventCount() {
        return eventCount;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public int getMyVisits() {
        return myVisits;
    }

    public ExploreSiteRow35(String siteName, int eventCount, int totalVisits, int myVisits) {
        this.siteName = siteName;
        this.eventCount = eventCount;
        this.totalVisits = totalVisits;
        this.myVisits = myVisits;
    }
}
