import java.util.ArrayList;
import java.util.Date;

public class Coupon {

    private String couponCode;
    private String couponDesc;
    private Date expireDate;
    private char operator;  // Mathmatical operator to do the discount calculation
    private double value;   // The value to do the discount calculation
    ArrayList<Integer> appliedItemId;   // The list of item this coupon applys to

    /**
     * Constructor
     */
    public Coupon() {
        appliedItemId = new ArrayList<>();
    }

    /**
     * Get the coupon code
     * @return coupon code
     */
    public String getCouponCode() {
        return couponCode;
    }

    /**
     * Get the coupon description
     * @return coupon description
     */
    public String getCouponDesc() {
        return couponDesc;
    }

    /**
     * Get the expire date
     * @return expire date
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * Get the operator
     * @return operator. By far, it should be 'x' indicating multiply, 'r' indicating minus
     */
    public char getOperator() {
        return operator;
    }

    /**
     * Get the value of operation
     * If the operator is 'x', the value represents multipler.
     * If the operator is 'r', the value represents the price to be minus from original price.
     * @return value. 
     */
    public double getValue() {
        return value;
    }

    /**
     * get the applied item id list
     * @return appliedIdList
     */
    public ArrayList<Integer> getAppliedItemId() {
        return appliedItemId;
    }

    /**
     * Set the coupon code
     * @param couponCode
     */
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    /**
     * Set coupon description
     * @param couponDesc
     */
    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    /**
     * Set coupon expire date
     * @param expireDate
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * Set coupon operator
     * @param operator
     */
    public void setOperator(char operator) {
        this.operator = operator;
    }

    /**
     * Set the value
     * @param value 
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Set appliedItemId list
     * @param applieditemId
     */
    public void setAppliedItemId(ArrayList<Integer> appliedItemId) {
        this.appliedItemId = appliedItemId;
    }

    /**
     * Convert coupon to a string representation.
     * format: couponCode-couponDesc-expireDate-operator-value-appliedId1_appliedId2_
     * @return coupon string representation.
     */
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
