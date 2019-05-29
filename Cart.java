import java.util.ArrayList;
import java.util.HashMap;

public class Cart {

    // restaurant id
    private int resId;
    // item list
    private ArrayList<PurchaseItem> itemList;

    /**
     * Constructor that create a cart by specifying the new empty item list.
     */
    public Cart() {
        itemList = new ArrayList<>();
    }

    /**
     * Constructor that create a cart by specifying the restaurant id and the new empty item list.
     */
    public Cart(int resId) {
        itemList = new ArrayList<>();
        this.resId = resId;
    }

    /**
     * Get the item list.
     * @return item list.
     */
    public ArrayList<PurchaseItem> getItemList() {
        return itemList;
    }

    /**
     * Get item by index
     * @param index item list's index
     * @return the ith's item.
     */
    public PurchaseItem getItem(int index) {
        return itemList.get(index);
    }

    /**
     * Get the item by itemId
     * @param itemId
     * @return Item with itemId. If not found, return null.
     */
    public PurchaseItem getItemById(int itemId) {
        for (PurchaseItem item : itemList)
            if (item.getItemId() == itemId)
                return item;

        return null;
    }

    /**
     * set item list
     * @param itemList
     */
    public void setItemList(ArrayList<PurchaseItem> itemList) {
        this.itemList = itemList;
    }

    /**
     * Get the total price of cart.
     * @return The total price of cart items without coupon.
     */
    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (PurchaseItem item : itemList)
            totalPrice += item.getTotalPrice();
        return totalPrice;
    }

    /**
     * Get the restaurant id
     * @return restaurant id
     */
    public int getResId() {
        return resId;
    }

    /**
     * Set the restaurant id.
     * @param resId restaurant id.
     */
    public void setResId(int resId) {
        this.resId = resId;
    }

    /**
     * Get the item list's id.
     * @return
     */
    public ArrayList<Integer> getItemIdList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (PurchaseItem item : itemList)
            list.add(item.getItemId());

        return list;
    }

    /**
     * Get the number of item in the cart
     * @return the number of item in the cart
     */
    public int itemCount() {
        return itemList.size();
    }

    /**
     * Add item
     * @param item item
     * @return the result of add
     */
    public boolean addItem(PurchaseItem item) {
        return itemList.add(item);
    }

    /**
     * Add item by hashmap
     * @param itemInfo item's information. generally converted from item.toHashMap
     * @param quantity the number of this item
     * @return
     */
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

    /**
     * delete an item by item id
     * @param itemId
     * @return deletion result
     */
    public boolean delItem(int itemId) {
        return true;
    }

    /**
     * Check whether there is an item with item id.
     * @param itemId
     * @return the result
     */
    public boolean hasItem(int itemId) {
        for (PurchaseItem item : itemList)
            if (item.getItemId() == itemId)
                return true;

        return false;
    }

    /**
     * Set an item 
     * @param index the index of item to be set
     * @param item the item
     * @return result
     */
    public boolean setItem(int index, PurchaseItem item) {
        itemList.set(index, item);
        return true;
    }

    /**
     * Convert an item's information to hashmap. attribute as the key.
     * @return the hashmap
     */
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("resId", Integer.toString(resId));
        StringBuffer buffer = new StringBuffer();
        for (PurchaseItem item : itemList) {
            buffer.append(item.toString());
            buffer.append(';');
        }
        map.put("items", buffer.toString());
        map.put("totalPrice", Double.toString(getTotalPrice()));

        return map;
    }

    /**
     * Override the toString method to convert the attributes value to a string.
     * Format: resId,<item list>
     * <item list>: itemId-itemName-itemDesc-unitPrice-quantity; Each item is splited by ';'
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(resId);
        buffer.append(",");
        for (PurchaseItem item : itemList) {
            buffer.append(item.toString());
            buffer.append(';');
        }
        buffer.append(",");
        buffer.append(getTotalPrice());
        return buffer.toString();
    }
}
