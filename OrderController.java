package monasheats.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderController {
    private OrderList orderList;
    private RestaurantList restaurantList;
    private CustomerList customerList;

    public OrderController() {
        orderList = new OrderList();
        restaurantList = new RestaurantList();
    }

    public boolean placeOrder(HashMap<String, String> info) {
        if (!info.containsKey("cusId"))
            return false;
        int cusId = -1;
        try {
            cusId = Integer.parseInt(info.get("cusId"));
            info.remove("cusId");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Check amount restriction
        if (!isWithinAmout(cusId, 0, 100))
            return false;

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
                    else if (op == '-')
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
        return orderList.getCart(cusId).toHashMap();
    }

    // 'm': modify quantity, 'a': add item. operateList<itemId, quantity>
    public boolean editCart(int cusId, int resId, char operator, HashMap<Integer, Integer> operateList) {

        if (operator == 'm') {
            // Check whether customer has cart
            if (!orderList.hasCart(cusId))
                return false;

            for (Map.Entry<Integer, Integer> entry : operateList.entrySet())
                if (orderList.getCart(cusId).hasItem(entry.getKey()))
                    orderList.getCart(cusId).getItemById(entry.getKey()).setQuantity(entry.getValue());

            return true;
        } else if (operator == 'a') {
            // Check whether customer has cart. If not, create one for them.
            if (!orderList.hasCart(cusId))
                orderList.addCart(cusId, resId);

            for (Map.Entry<Integer, Integer> entry : operateList.entrySet()) {
                HashMap<String, String> item = restaurantList.getRestaurant(resId).getItem(entry.getKey()).toHashMap();
                if (orderList.getCart(cusId).hasItem(entry.getKey()))
                    orderList.getCart(cusId).getItemById(entry.getKey()).increaseQty(entry.getValue());
                orderList.getCart(cusId).addItem(item, entry.getValue());
            }

            return true;
        }
        return false;
    }

    // Call before placing order, verifying whether total price without coupon over $100.
    public boolean isWithinAmout(int cusId, double lowerBound, double upperBound) {
        double price = orderList.getCart(cusId).getTotalPrice();
        if (price > upperBound || price < lowerBound)
            return false;
        return true;
    }
}
