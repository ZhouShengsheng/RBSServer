package ncu.zss.rbs.model;

import java.util.Date;

public class RoomBookingGroup {
    private String groupid;

    private String roombuilding;

    private String roomnumber;

    private String applicanttype;

    private String applicantid;

    private String timeintervals;

    private String bookreason;

    private String status;

    private String adminid;

    private String facultyid;

    private String declinereason;

    private Byte expired;

    private Date creationtime;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
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

    public String getApplicanttype() {
        return applicanttype;
    }

    public void setApplicanttype(String applicanttype) {
        this.applicanttype = applicanttype == null ? null : applicanttype.trim();
    }

    public String getApplicantid() {
        return applicantid;
    }

    public void setApplicantid(String applicantid) {
        this.applicantid = applicantid == null ? null : applicantid.trim();
    }

    public String getTimeintervals() {
        return timeintervals;
    }

    public void setTimeintervals(String timeintervals) {
        this.timeintervals = timeintervals == null ? null : timeintervals.trim();
    }

    public String getBookreason() {
        return bookreason;
    }

    public void setBookreason(String bookreason) {
        this.bookreason = bookreason == null ? null : bookreason.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid == null ? null : adminid.trim();
    }

    public String getFacultyid() {
        return facultyid;
    }

    public void setFacultyid(String facultyid) {
        this.facultyid = facultyid == null ? null : facultyid.trim();
    }

    public String getDeclinereason() {
        return declinereason;
    }

    public void setDeclinereason(String declinereason) {
        this.declinereason = declinereason == null ? null : declinereason.trim();
    }

    public Byte getExpired() {
        return expired;
    }

    public void setExpired(Byte expired) {
        this.expired = expired;
    }

    public Date getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }
}