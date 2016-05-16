package ncu.zss.rbs.model;

public class PushNotification {
    private Integer id;

    private String usertype;

    private String userid;

    private String apntoken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype == null ? null : usertype.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getApntoken() {
        return apntoken;
    }

    public void setApntoken(String apntoken) {
        this.apntoken = apntoken == null ? null : apntoken.trim();
    }
}