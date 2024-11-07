/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author djxjs
 */
public class Fee_Include_Line {
    private int FeeIncludeLine_id;
    private int PaymentLine_id;
    private int FeeInclude_id;

    public Fee_Include_Line() {
    }

    public Fee_Include_Line(int FeeIncludeLine_id, int PaymentLine_id, int FeeInclude_id) {
        this.FeeIncludeLine_id = FeeIncludeLine_id;
        this.PaymentLine_id = PaymentLine_id;
        this.FeeInclude_id = FeeInclude_id;
    }

    public int getFeeIncludeLine_id() {
        return FeeIncludeLine_id;
    }

    public void setFeeIncludeLine_id(int FeeIncludeLine_id) {
        this.FeeIncludeLine_id = FeeIncludeLine_id;
    }

    public int getPaymentLine_id() {
        return PaymentLine_id;
    }

    public void setPaymentLine_id(int PaymentLine_id) {
        this.PaymentLine_id = PaymentLine_id;
    }

    public int getFeeInclude_id() {
        return FeeInclude_id;
    }

    public void setFeeInclude_id(int FeeInclude_id) {
        this.FeeInclude_id = FeeInclude_id;
    }
    
}
