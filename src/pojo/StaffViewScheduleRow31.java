package pojo;

public class StaffViewScheduleRow31 {

    private String eventName;
    private String siteName;
    private String startDate;
    private String endDate;
    private int staffCount;

    public StaffViewScheduleRow31(String eventName, String siteName, String startDate, String endDate, int staffCount) {
        this.eventName = eventName;
        this.siteName = siteName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.staffCount = staffCount;
    }

    public String getEventName() {
        return eventName;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getStaffCount() {
        return staffCount;
    }
}
