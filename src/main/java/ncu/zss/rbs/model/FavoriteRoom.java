package ncu.zss.rbs.model;

public class FavoriteRoom {
    private Integer id;

    private String roombuilding;

    private String roomnumber;

    private String persontype;

    private String personid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoombuilding() {
        return roombuilding;
    }

    public void setRoombuilding(String roombuilding) {
        this.roombuilding = roombuilding == null ? null : roombuilding.trim();
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber == null ? null : roomnumber.trim();
    }

    public String getPersontype() {
        return persontype;
    }

    public void setPersontype(String persontype) {
        this.persontype = persontype == null ? null : persontype.trim();
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }
}