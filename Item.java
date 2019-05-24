import java.util.HashMap;

public class Item {

    private int itemId;
    private String itemName;
    private String itemDesc;
    private Double unitPrice;

    public Item() {

    }

    public Item(Integer itemId, String itemName, String itemDesc, Double unitPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.unitPrice = unitPrice;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("itemId", Integer.toString(itemId));
        hashmap.put("itemName", itemName);
        hashmap.put("itemDesc", itemDesc);
        hashmap.put("unitPrice", Double.toString(unitPrice));

        return hashmap;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(itemId);
        buffer.append('-');
        buffer.append(itemName);
        buffer.append('-');
        buffer.append(itemDesc);
        buffer.append('-');
        buffer.append(unitPrice);
        return buffer.toString();
    }
}
