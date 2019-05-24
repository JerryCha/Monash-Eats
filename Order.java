import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Order {

    private ArrayList<PurchaseItem> itemList;
    private int cusId;
    private int resId;
    private double totalPrice;
    private Date datetime;
    private String phone;
    private String address;
    private String status;
    private double timeToDeliver;
    private String couponCode;

    public Order() {
        itemList = new ArrayList<>();
    }

    public ArrayList<PurchaseItem> getItemList() {
        return itemList;
    }

    public boolean addItem(HashMap<String, String> itemInfo, int quantity) {
        PurchaseItem item = new PurchaseItem();
        if (itemInfo.containsKey("itemId"))
            item.setItemId(Integer.parseInt(itemInfo.get("itemId")));
        if (itemInfo.containsKey("itemName"))
            item.setItemName(itemInfo.get("itemName"));
        if (itemInfo.containsKey("itemDesc"))
            item.setItemDesc(itemInfo.get("itemDesc"));
        if (itemInfo.containsKey("unitPrice"))
            item.setUnitPrice(Double.parseDouble(itemInfo.get("unitPrice")));

        item.setQuantity(quantity);
        
        itemList.add(item);
        return true;
    }

    // TODO: Determine to search by name or id.
    public PurchaseItem getItem(int itemId) {
        for (PurchaseItem item : itemList)
            if (item.getItemId() == itemId)
                return item;

        return null;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getCusId() {
        return cusId;
    }

    public int getResId() {
        return resId;
    }

    public double getTimeToDeliver() {
        return timeToDeliver;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getStatus() {
        return status;
    }

    public String getReceipt() {
        return this.toString();
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public void setItemList(ArrayList<PurchaseItem> itemList) {
        this.itemList = itemList;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimeToDeliver(double timeToDeliver) {
        this.timeToDeliver = timeToDeliver;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(cusId);
        buffer.append(",");
        buffer.append(resId);
        buffer.append(",");
        buffer.append(datetime);
        buffer.append(",");
        buffer.append(address);
        buffer.append(",");
        buffer.append(phone);
        buffer.append(",");
        for (PurchaseItem item : itemList) {
            buffer.append(item.toString());
            buffer.append(";");
        }
        buffer.append(",");
        buffer.append(totalPrice);
        buffer.append(",");
        buffer.append(couponCode);
        buffer.append(",");
        buffer.append(status);
        buffer.append(",");
        buffer.append(timeToDeliver);
        
        return buffer.toString();
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("cusId", Integer.toString(cusId));
        map.put("resId", Integer.toString(resId));
        //map.put() put formatted time
        map.put("phone", phone);
        map.put("address", address);
        map.put("status", status);
        map.put("timeToDeliver", Double.toString(timeToDeliver));
        map.put("couponCode", couponCode);

        StringBuffer buffer = new StringBuffer();
        for (PurchaseItem item : itemList) {
            buffer.append(item.toString());
            buffer.append(";");
        }
        map.put("itemList", buffer.toString());
        return map;
    }
}
