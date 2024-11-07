/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author djxjs
 */
public class FeeInclude {
    private int feeinclude_id;
    private String note;
    private int count;
    private double price;

    // Constructor không tham số
    public FeeInclude() {
    }

    // Constructor có tham số
    public FeeInclude(int feeinclude_id, String note, int count, double price) {
        this.feeinclude_id = feeinclude_id;
        this.note = note;
        this.count = count;
        this.price = price;
    }

    // Getter
    public int getFeeinclude_id() {
        return feeinclude_id;
    }

    public String getNote() {
        return note;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    // Setter
    public void setFeeinclude_id(int feeinclude_id) {
        this.feeinclude_id = feeinclude_id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Override toString()
    @Override
    public String toString() {
        return "FeeInclude{" +
                "feeinclude_id=" + feeinclude_id +
                ", note='" + note + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
