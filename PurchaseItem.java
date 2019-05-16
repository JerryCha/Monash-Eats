package monasheats.java;

import java.util.HashMap;

public class PurchaseItem extends Item {

    private int quantity;

    public PurchaseItem() {
        super();
    }

    public PurchaseItem(Integer itemId, String itemName, String itemDesc, Double unitPrice, int quantity) {
        super(itemId, itemName, itemDesc, unitPrice);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQty(int n) {
        quantity += n;
    }

    public void decreaseQty(int n) {
        quantity -= n;
    }

    public double getTotalPrice() {
        return getUnitPrice().doubleValue() * quantity;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = super.toHashMap();
        map.put("quantity", Integer.toString(quantity));
        return map;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(super.toString());
        buffer.append(quantity);
        // itemId, itemName, itemDesc, unitPrice, quantity
        return buffer.toString();
    }

}
