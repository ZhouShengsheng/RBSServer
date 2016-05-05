package ncu.zss.rbs.model;

import java.util.Date;

public class RoomBookingInfo {
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

    private String adminname;

    private String admindesignation;

    private Boolean admingender;

    private String adminoffice;

    private String adminphone;

    private String facultyname;

    private String facultydesignation;

    private Boolean facultygender;

    private String facultyoffice;

    private String facultyphone;

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

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname == null ? null : adminname.trim();
    }

    public String getAdmindesignation() {
        return admindesignation;
    }

    public void setAdmindesignation(String admindesignation) {
        this.admindesignation = admindesignation == null ? null : admindesignation.trim();
    }

    public Boolean getAdmingender() {
        return admingender;
    }

    public void setAdmingender(Boolean admingender) {
        this.admingender = admingender;
    }

    public String getAdminoffice() {
        return adminoffice;
    }

    public void setAdminoffice(String adminoffice) {
        this.adminoffice = adminoffice == null ? null : adminoffice.trim();
    }

    public String getAdminphone() {
        return adminphone;
    }

    public void setAdminphone(String adminphone) {
        this.adminphone = adminphone == null ? null : adminphone.trim();
    }

    public String getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname == null ? null : facultyname.trim();
    }

    public String getFacultydesignation() {
        return facultydesignation;
    }

    public void setFacultydesignation(String facultydesignation) {
        this.facultydesignation = facultydesignation == null ? null : facultydesignation.trim();
    }

    public Boolean getFacultygender() {
        return facultygender;
    }

    public void setFacultygender(Boolean facultygender) {
        this.facultygender = facultygender;
    }

    public String getFacultyoffice() {
        return facultyoffice;
    }

    public void setFacultyoffice(String facultyoffice) {
        this.facultyoffice = facultyoffice == null ? null : facultyoffice.trim();
    }

    public String getFacultyphone() {
        return facultyphone;
    }

    public void setFacultyphone(String facultyphone) {
        this.facultyphone = facultyphone == null ? null : facultyphone.trim();
    }
}