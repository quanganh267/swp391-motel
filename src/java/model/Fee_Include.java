/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author djxjs
 */
public class Fee_Include {
   private int feeinclude_id;
    private String note;
    private int count;
    private double price;
    private String Unit;

    public Fee_Include() {
    }

    public Fee_Include(int feeinclude_id, String note, int count, double price, String Unit) {
        this.feeinclude_id = feeinclude_id;
        this.note = note;
        this.count = count;
        this.price = price;
        this.Unit = Unit;
    }

    public int getFeeinclude_id() {
        return feeinclude_id;
    }

    public void setFeeinclude_id(int feeinclude_id) {
        this.feeinclude_id = feeinclude_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    
    
    
}
