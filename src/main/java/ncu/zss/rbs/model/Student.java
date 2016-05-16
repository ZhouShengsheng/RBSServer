package ncu.zss.rbs.model;

public class Student {
    private String id;

    private String idDigest;

    private String password;

    private String name;

    private String classname;

    private Boolean gender;

    private String dormroomnumber;

    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIdDigest() {
        return idDigest;
    }

    public void setIdDigest(String idDigest) {
        this.idDigest = idDigest == null ? null : idDigest.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getDormroomnumber() {
        return dormroomnumber;
    }

    public void setDormroomnumber(String dormroomnumber) {
        this.dormroomnumber = dormroomnumber == null ? null : dormroomnumber.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
    
    public String toString() {
		return String.format("%s %s %s", id, name, classname);
    }
}