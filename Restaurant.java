import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Restaurant {

    private int resId;
    private int ownerId;
    private String name;
    private String desc;
    private String surburb;
    private String street;
    private String email;
    private String phone;
    private ArrayList<Coupon> couponList;
    private ArrayList<Item> menu;
    private Date openTime;
    private double businessHour;
    private int openDay;

    public Restaurant() {
        couponList = new ArrayList<>();
        menu = new ArrayList<>();
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getResId() {
        return resId;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getSurburb() {
        return surburb;
    }

    public String getStreet() {
        return street;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<Coupon> getCouponList() {
        return couponList;
    }

    public Coupon getCoupon(int index) {
        return couponList.get(index);
    }

    public Coupon getCoupon(String couponCode) {
        for (Coupon coupon : couponList)
            if (coupon.getCouponCode().equals(couponCode))
                return coupon;

        return null;
    }

    public ArrayList<Item> getMenu() {
        return menu;
    }

    public Item getItem(int index) {
        return menu.get(index);
    }

    public Item getItemById(int itemId) {
        for (Item item : menu)
            if (item.getItemId() == itemId)
                return item;

        return null;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public double getBusinessHour() {
        return businessHour;
    }

    public int getOpenDay() {
        return openDay;
    }

    public void setOwnerId(int id) {
        this.ownerId = id;
    }

    public void setResId(int id) {
        this.resId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setSurburb(String surburb) {
        this.surburb = surburb;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCouponList(ArrayList<Coupon> couponList) {
        this.couponList = couponList;
    }

    public void setMenu(ArrayList<Item> menu) {
        this.menu = menu;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public void setBusinessHour(double businessHour) {
        this.businessHour = businessHour;
    }

    public void setOpenDay(int openDay) {
        this.openDay = openDay;
    }

    public void addItem(Item item) {
        menu.add(item);
    }

    public void addItem(HashMap<String, String> info) {
        Item item = new Item();
        item.setItemId(menu.size()+1);
        if (info.containsKey("name"))
            item.setItemName(info.get("name"));
        if (info.containsKey("desc"))
            item.setItemDesc(info.get("desc"));
        if (info.containsKey("unitPrice"))
            item.setUnitPrice(Double.parseDouble(info.get("unitPrice")));

        menu.add(item);
    }

    public boolean hasItem(int itemId) {
        for (Item item : menu)
            if (item.getItemId() == itemId)
                return true;

        return false;
    }

    public void delItem(int itemId) {
        for (Item item : menu)
            if (item.getItemId() == itemId) {
                menu.remove(item);
                break;
            }
    }

    public void delItem(Item item) {
        menu.remove(item);
    }

    public void addCoupon(HashMap<String, String> couponParams) {
        if (couponParams == null)
            System.out.println("null params");
        else {
            Coupon coupon = new Coupon();
            if (couponParams.containsKey("couponCode"))
                coupon.setCouponCode(couponParams.get("couponCode"));
            if (couponParams.containsKey("couponDesc"))
                coupon.setCouponDesc(couponParams.get("couponDesc"));
            if (couponParams.containsKey("expireDate")) {
                // TODO: Complete date parse.
                String rawDate = couponParams.get("expireDate");
                Date date = new Date();
            }
            if (couponParams.containsKey("operator"))
                coupon.setOperator(couponParams.get("operator").charAt(0));
            if (couponParams.containsKey("value"))
                coupon.setValue(Double.parseDouble(couponParams.get("value")));
            if (couponParams.containsKey("appliedItemId")) {
                String rawIds = couponParams.get("appliedItemId");
                ArrayList<Integer> idList = new ArrayList<>();
                for (String id : rawIds.trim().split(","))
                    try {
                        idList.add(Integer.parseInt(id));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
            }

            couponList.add(coupon);
        }
    }

    public void setCoupon(HashMap<String, String> couponParams) {
        if (couponParams == null)
            System.out.println("null params");
        else {
            if (couponParams.containsKey("couponDesc"))
                getCoupon(couponParams.get("couponCode")).setCouponDesc(couponParams.get("couponDesc"));
            if (couponParams.containsKey("expireDate")) {
                // TODO: Complete date parse.
                String rawDate = couponParams.get("expireDate");
                Date date = new Date();
            }
            if (couponParams.containsKey("operator"))
                getCoupon(couponParams.get("couponCode")).setOperator(couponParams.get("operator").charAt(0));
            if (couponParams.containsKey("value"))
                getCoupon(couponParams.get("couponCode")).setValue(Double.parseDouble(couponParams.get("value")));
            if (couponParams.containsKey("appliedItemId")) {
                String rawIds = couponParams.get("appliedItemId");
                ArrayList<Integer> idList = new ArrayList<>();
                for (String id : rawIds.trim().split(","))
                    try {
                        idList.add(Integer.parseInt(id));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public boolean hasCoupon(String couponCode) {
        for (Coupon coupon : couponList)
            if (coupon.getCouponCode().equals(couponCode))
                return true;

        return false;
    }

    public void delCoupon(String couponCode) {
        for (Coupon coupon : couponList)
            if (coupon.getCouponCode().equals(couponCode)) {
                couponList.remove(coupon);
                break;
            }
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("resId", Integer.toString(resId));
        map.put("ownerId", Integer.toString(ownerId));
        map.put("name", name);
        map.put("desc", desc);
        map.put("surburb", surburb);
        map.put("street", street);
        map.put("phone", phone);
        map.put("email", email);
        map.put("openTime", "11:00");   // Temporary
        map.put("businessHour", Double.toString(businessHour));
        map.put("openDay", Integer.toString(openDay));

        StringBuffer buffer = new StringBuffer();
        for (Item item : menu) {
            buffer.append(item.toString());
            buffer.append(";");
        }
        map.put("menu", buffer.toString());

        buffer.setLength(0);    // Clear buffer.
        for (Coupon coupon : couponList) {
            buffer.append(coupon.toString());
            buffer.append(";");
        }
        map.put("couponList", buffer.toString());
        return map;
    }
}
