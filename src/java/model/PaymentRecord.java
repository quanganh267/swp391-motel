/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author djxjs
 */
public class PaymentRecord {
    private int record_id;
    private String date;
    private int room_id;
    private float total_discount;
    private int motel_id;
    private int guest_id;
    private int contract_id;

    public PaymentRecord() {
    }

    public PaymentRecord(int record_id, String date, int room_id, float total_discount, int motel_id, int guest_id, int contract_id) {
        this.record_id = record_id;
        this.date = date;
        this.room_id = room_id;
        this.total_discount = total_discount;
        this.motel_id = motel_id;
        this.guest_id = guest_id;
        this.contract_id = contract_id;
    }

    public int getRecord_id() {
        return record_id;
    }

    public String getDate() {
        return date;
    }

    public int getRoom_id() {
        return room_id;
    }

    public float getTotal_discount() {
        return total_discount;
    }

    public int getMotel_id() {
        return motel_id;
    }

    public int getGuest_id() {
        return guest_id;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public void setTotal_discount(float total_discount) {
        this.total_discount = total_discount;
    }

    public void setMotel_id(int motel_id) {
        this.motel_id = motel_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }
    
    
}
