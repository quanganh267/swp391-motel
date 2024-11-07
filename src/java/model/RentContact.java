/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class RentContact {
    private int rentContractId;
    private String guestName;
    private int roomId;
    private Date startDate;
    private Date endDate;
    private String phoneNumber;
    private String email;
    private String address;
    private String status;
    private BigDecimal totalPrice;

    public RentContact() {
    }

    public RentContact(int rentContractId, String guestName, int roomId, Date startDate, Date endDate, String phoneNumber, String email, String address, String status, BigDecimal totalPrice) {
        this.rentContractId = rentContractId;
        this.guestName = guestName;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public int getRentContractId() {
        return rentContractId;
    }

    public void setRentContractId(int rentContractId) {
        this.rentContractId = rentContractId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
