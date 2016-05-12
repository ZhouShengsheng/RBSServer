package ncu.zss.rbs.model;

import java.util.Date;

public class StudentBookingGroup {
    private String groupid;

    private String roombuilding;

    private String roomnumber;

    private String timeintervals;

    private String bookreason;

    private String adminid;

    private String facultyid;

    private String declinereason;

    private Date creationtime;

    private String studentid;

    private String studentname;

    private String studentclassname;

    private Boolean studentgender;

    private String studentdormroomnumber;

    private String studentphone;

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

    public Date getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid == null ? null : studentid.trim();
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname == null ? null : studentname.trim();
    }

    public String getStudentclassname() {
        return studentclassname;
    }

    public void setStudentclassname(String studentclassname) {
        this.studentclassname = studentclassname == null ? null : studentclassname.trim();
    }

    public Boolean getStudentgender() {
        return studentgender;
    }

    public void setStudentgender(Boolean studentgender) {
        this.studentgender = studentgender;
    }

    public String getStudentdormroomnumber() {
        return studentdormroomnumber;
    }

    public void setStudentdormroomnumber(String studentdormroomnumber) {
        this.studentdormroomnumber = studentdormroomnumber == null ? null : studentdormroomnumber.trim();
    }

    public String getStudentphone() {
        return studentphone;
    }

    public void setStudentphone(String studentphone) {
        this.studentphone = studentphone == null ? null : studentphone.trim();
    }
}