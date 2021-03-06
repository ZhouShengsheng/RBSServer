package ncu.zss.rbs.model;

import java.util.Date;

public class RoomBooking {

	private Integer id;

    private String groupid;

    private String roombuilding;

    private String roomnumber;

    private String applicanttype;

    private String applicantid;

    private Date fromtime;
    
    private long fromTimestamp;

    private Date totime;
    
    private long toTimestamp;

    private String bookreason;

    private Short progress;

    private String adminid;

    private String facultyid;
    
    private String declinedReason;
    
    private boolean expired;
    
    private String creationTime;
    
    public String getDeclinedReason() {
		return declinedReason;
	}

	public void setDeclinedReason(String declinedReason) {
		this.declinedReason = declinedReason;
	}

	public long getFromTimestamp() {
		return fromTimestamp;
	}

	public void setFromTimestamp(long fromTimestamp) {
		this.fromTimestamp = fromTimestamp;
	}

	public long getToTimestamp() {
		return toTimestamp;
	}

	public void setToTimestamp(long toTimestamp) {
		this.toTimestamp = toTimestamp;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getFromtime() {
        return fromtime;
    }

    public void setFromtime(Date fromtime) {
        this.fromtime = fromtime;
    }

    public Date getTotime() {
        return totime;
    }

    public void setTotime(Date totime) {
        this.totime = totime;
    }

    public String getBookreason() {
        return bookreason;
    }

    public void setBookreason(String bookreason) {
        this.bookreason = bookreason == null ? null : bookreason.trim();
    }

    public Short getProgress() {
        return progress;
    }

    public void setProgress(Short progress) {
        this.progress = progress;
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
    
    public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

}