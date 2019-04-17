package pojo;

public class ManageUserRow18 {
    private String username;
    private int emailCount;
    private String userType;
    private String status;

    public ManageUserRow18(String username, int emailCount, String userType, String status) {
        this.username = username;
        this.emailCount = emailCount;
        this.userType = userType;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public String getUserType() {
        return userType;
    }

    public String getStatus() {
        return status;
    }
}
