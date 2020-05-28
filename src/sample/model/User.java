package sample.model;

public class User {
    private int id;
    private String fName,lName,userName,password,email,mobilNo,date,time,score;

    public User() {
    }

    public User(String fName, String lName, String userName, String password, String email, String mobilNo, String date, String time, String score) {
        this.fName = fName;
        this.lName = lName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.mobilNo = mobilNo;
        this.date = date;
        this.time = time;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilNo() {
        return mobilNo;
    }

    public void setMobilNo(String mobilNo) {
        this.mobilNo = mobilNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
