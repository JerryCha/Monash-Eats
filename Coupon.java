package monasheats.java;

import java.util.ArrayList;
import java.util.Date;

public class Coupon {

    private String couponCode;
    private String couponDesc;
    private Date expireDate;
    private char operator;
    private double value;
    ArrayList<Integer> appliedItemId;

    public Coupon() {

    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public char getOperator() {
        return operator;
    }

    public double getValue() {
        return value;
    }

    public ArrayList<Integer> getAppliedItemId() {
        return appliedItemId;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setAppliedItemId(ArrayList<Integer> appliedItemId) {
        this.appliedItemId = appliedItemId;
    }
}
