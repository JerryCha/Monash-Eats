import java.util.ArrayList;
import java.util.HashMap;

public class Cart {

    private int resId;
    private ArrayList<PurchaseItem> itemList;

    public Cart() {
        itemList = new ArrayList<>();
    }

    public Cart(int resId) {
        itemList = new ArrayList<>();
        this.resId = resId;
    }

    public ArrayList<PurchaseItem> getItemList() {
        return itemList;
    }

    public PurchaseItem getItem(int index) {
        return itemList.get(index);
    }

    public PurchaseItem getItemById(int itemId) {
        for (PurchaseItem item : itemList)
            if (item.getItemId() == itemId)
                return item;

        return null;
    }

    public void setItemList(ArrayList<PurchaseItem> itemList) {
        this.itemList = itemList;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (PurchaseItem item : itemList)
            totalPrice += item.getTotalPrice();
        return totalPrice;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public ArrayList<Integer> getItemIdList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (PurchaseItem item : itemList)
            list.add(item.getItemId());

        return list;
    }

    public int itemCount() {
        return itemList.size();
    }

    // TODO: Re-consider parameters
    public boolean addItem(PurchaseItem item) {
        return itemList.add(item);
    }

    public boolean addItem(HashMap<String, String> itemInfo, int quantity) {
        PurchaseItem item = new PurchaseItem();
        if (itemInfo.containsKey("itemId"))
            item.setItemId(Integer.parseInt("itemId"));
        if (itemInfo.containsKey("itemName"))
            item.setItemName(itemInfo.get("itemName"));
        if (itemInfo.containsKey("itemDesc"))
            item.setItemDesc(itemInfo.get("itemDesc"));
        if (itemInfo.containsKey("unitPrice"))
            item.setUnitPrice(Double.parseDouble(itemInfo.get("unitPrice")));

        item.setQuantity(quantity);

        return true;
    }

    public boolean delItem(int itemId) {
        return true;
    }

    public boolean hasItem(int itemId) {
        for (PurchaseItem item : itemList)
            if (item.getItemId() == itemId)
                return true;

        return false;
    }

    public boolean setItem(int index, PurchaseItem item) {
        itemList.set(index, item);
        return true;
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("resId", Integer.toString(resId));
        StringBuffer buffer = new StringBuffer();
        for (PurchaseItem item : itemList) {
            buffer.append(item.toHashMap());
            buffer.append(';');
        }

        return map;
    }
}
