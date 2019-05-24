import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrderController {
    private OrderList orderList;
    private RestaurantList restaurantList;
    private CustomerList customerList;

    public OrderController() {
        orderList = OrderList.getInstance();
        customerList = CustomerList.getInstance();
        restaurantList = RestaurantList.getInstance();
    }

    public boolean placeOrder(HashMap<String, String> info) throws Exception {
        if (!info.containsKey("actId")) {
            System.out.println("actId not exist");
            return false;
        }
        int cusId = -1;
        try {
            cusId = Integer.parseInt(info.get("actId"));
            info.remove("actId");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Check amount restriction
        if (!isWithinAmount(cusId, 0, 100))
            throw new Exception("amount exceed");

        // Calculate discounted price if couple applied
        String couponCode = null;
        if (info.containsKey("couponCode"))
            couponCode = info.get("couponCode");
        if (couponCode != null) {
            int resId = orderList.getCart(cusId).getResId();
            if (restaurantList.getRestaurant(resId).hasCoupon(couponCode)) {
                char op = restaurantList.getRestaurant(resId).getCoupon(couponCode).getOperator();
                double val = restaurantList.getRestaurant(resId).getCoupon(couponCode).getValue();
                ArrayList<Integer> appliedItems = restaurantList.getRestaurant(resId).
                                                                    getCoupon(couponCode).getAppliedItemId();
                // op: 'x': price*value/unit, '-' price-value/unit
                // Get intersection
                appliedItems.retainAll(orderList.getCart(cusId).getItemIdList());
                if (appliedItems.size() != 0) {
                    if (op == 'x')
                        for (Integer i : appliedItems) {
                            double original = orderList.getCart(cusId).getItemById(i).getUnitPrice();
                            orderList.getCart(cusId).getItemById(i).setUnitPrice(original*val);
                        }
                    else if (op == 'r') // reduce
                        for (Integer i : appliedItems) {
                            double original = orderList.getCart(cusId).getItemById(i).getUnitPrice();
                            orderList.getCart(cusId).getItemById(i).setUnitPrice(original-val);
                        }
                }
            }
        }

        // finally Convert cart to order
        return orderList.addOrder(cusId, info);
    }

    // Role Id: 1 - cus, 2 - owner, 3 - admin, 4 - res
    public ArrayList<HashMap<String, String>> getHistoryOrder(int actId, int roleCode) {

        return orderList.getOrderById(actId, roleCode);
    }

    // Only customer could access
    public HashMap<String, String> viewCart(int cusId) {
        if (orderList.hasCart(cusId))
            return orderList.getCart(cusId).toHashMap();

        return null;
    }

    // 'm': modify quantity, 'a': add item. operateList<itemId, quantity>
    public boolean editCart(int cusId, int resId, char operator, HashMap<Integer, Integer> operateList) {

        if (operator == 'm') {
            // Check whether customer has cart
            if (!orderList.hasCart(cusId))
                return false;

            Iterator<Map.Entry<Integer, Integer>> it = operateList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> entry = it.next();
                if (orderList.getCart(cusId).hasItem(entry.getKey()))
                    orderList.getCart(cusId).getItemById(entry.getKey()).setQuantity(entry.getValue());
            }

            return true;
        } else if (operator == 'a') {
            // Check whether customer has cart. If not, create one for them.
            if (!orderList.hasCart(cusId))
                orderList.addCart(cusId, resId);

            Iterator<Map.Entry<Integer, Integer>> it = operateList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> entry = it.next();
                HashMap<String, String> item = restaurantList.getRestaurant(resId).getItemById(entry.getKey()).toHashMap();
                if (orderList.getCart(cusId).hasItem(entry.getKey()))
                    orderList.getCart(cusId).getItemById(entry.getKey()).increaseQty(entry.getValue());
                orderList.getCart(cusId).addItem(item, entry.getValue());
            }

            return true;
        }
        return false;
    }

    // Call before placing order, verifying whether total price without coupon over $100.
    public boolean isWithinAmount(int cusId, double lowerBound, double upperBound) {
        double price = orderList.getCart(cusId).getTotalPrice();
        if (price > upperBound || price < lowerBound)
            return false;
        return true;
    }

    public void saveData() {
        orderList.save();
    }
}
