import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrderList {
    private ArrayList<Order> orderList;
    private HashMap<Integer, Cart> cartList;

    public OrderList() {
        orderList = new ArrayList<>();
        cartList = new HashMap<>();
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public HashMap<Integer, Cart> getCartList() {
        return cartList;
    }

    public ArrayList<HashMap<String, String>> getOrderById(int id, int roleId) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        if (roleId == 1) {
            for (Order order : orderList)
                if (order.getCusId() == id)
                    result.add(order.toHashMap());
        } else if (roleId == 4) {
            for (Order order : orderList)
                if (order.getResId() == id)
                    result.add(order.toHashMap());
        } else if (roleId == 3) {
            for (Order order : orderList)
                result.add(order.toHashMap());
        }

        if (result.size() == 0)
            return null;
        return result;
    }

    public Cart getCart(int cusId) {
        if (cartList.containsKey(cusId))
            return cartList.get(cusId);

        return null;
    }

    // An order can be added when purchase for the cart.
    public boolean addOrder(int cusId, HashMap<String, String> info) {
        Order order = new Order();
        order.setItemList(cartList.get(cusId).getItemList());
        order.setResId(cartList.get(cusId).getResId());
        order.setDatetime(new Date());  // TODO: Complete creating an date object with current time.
        order.setTotalPrice(cartList.get(cusId).getTotalPrice());
        order.setStatus("Pending");
        order.setTimeToDeliver(-1);
        if (!info.containsKey("cusId") && !info.containsKey("address")
                && !info.containsKey("phone") && !info.containsKey("couponCode"))
            return false;
        order.setCusId(Integer.parseInt(info.get("cusId")));
        order.setAddress(info.get("address"));
        order.setPhone("phone");
        order.setCouponCode(info.get("couponCode"));
        return orderList.add(order);
    }

    public boolean addCart(int cusId, int resId) {
        return (cartList.put(cusId, new Cart(resId)) == null);
    }

    public boolean hasCart(int cusId) {
        return cartList.containsKey(cusId);
    }

    public void setCartList(HashMap<Integer, Cart> cartList) {
        this.cartList = cartList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public boolean initialize() {
        return true;
    }

    public boolean save() {
        return true;
    }
}
