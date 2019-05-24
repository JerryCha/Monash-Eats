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
        appliedItemId = new ArrayList<>();
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

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(couponCode);
        buffer.append("-");
        buffer.append(couponDesc);
        buffer.append("-");
        buffer.append("expireDate");
        buffer.append("-");
        buffer.append(operator);
        buffer.append("-");
        buffer.append(value);
        buffer.append("-");
        if (appliedItemId.size() == 0)
            buffer.append("-1");
        else
            for (int id : appliedItemId) {
                buffer.append(id);
                buffer.append("_");
            }

        return buffer.toString();
    }
}
