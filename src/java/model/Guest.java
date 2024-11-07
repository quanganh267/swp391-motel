package model;

public class Guest {

    private int guestId;          // ID của khách
    private String name;          // Tên của khách
    private String email;         // Email của khách
    private String phoneNum;      // Số điện thoại của khách
    private String identityId;    // ID định danh của khách

    // Constructor với các tham số
    public Guest(int guestId, String name, String email, String phoneNum, String identityId) {
        this.guestId = guestId;
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.identityId = identityId;
    }

    // Constructor mặc định
    public Guest() {
    }

    // Getter và setter cho guestId
    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    // Getter và setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và setter cho email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter và setter cho phoneNum
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    // Getter và setter cho identityId
    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }
} 