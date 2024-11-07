/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author djxjs
 */
public class PaymentLine {
    private int paymentLine_id; // ID duy nhất của dòng thanh toán
    private String status; // Trạng thái thanh toán
    private double price_per_month; // Giá mỗi tháng
    private int available_guest; // Số lượng khách có thể
    private String description; // Mô tả
    private int record_id; // ID của bản ghi thanh toán
    private String billing_period; // Kỳ thanh toán
    private double total_payment; // Tổng thanh toán

    public PaymentLine() {
    }

    public PaymentLine(int paymentLine_id, String status, double price_per_month, int available_guest, String description, int record_id, String billing_period, double total_payment) {
        this.paymentLine_id = paymentLine_id;
        this.status = status;
        this.price_per_month = price_per_month;
        this.available_guest = available_guest;
        this.description = description;
        this.record_id = record_id;
        this.billing_period = billing_period;
        this.total_payment = total_payment;
    }

    public int getPaymentLine_id() {
        return paymentLine_id;
    }

    public void setPaymentLine_id(int paymentLine_id) {
        this.paymentLine_id = paymentLine_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice_per_month() {
        return price_per_month;
    }

    public void setPrice_per_month(double price_per_month) {
        this.price_per_month = price_per_month;
    }

    public int getAvailable_guest() {
        return available_guest;
    }

    public void setAvailable_guest(int available_guest) {
        this.available_guest = available_guest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getBilling_period() {
        return billing_period;
    }

    public void setBilling_period(String billing_period) {
        this.billing_period = billing_period;
    }

    public double getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(double total_payment) {
        this.total_payment = total_payment;
    }
    
    
}
