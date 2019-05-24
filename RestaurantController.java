import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantController {
    private RestaurantList restaurantList;
    private OwnerList ownerList;
    private RateList rateList;

    public RestaurantController() {
        restaurantList = RestaurantList.getInstance();
        ownerList = OwnerList.getInstance();
        rateList = RateList.getInstance();
    }

    public boolean createRestaurant(HashMap<String, String> info) {
        return false;
    }

    public boolean editRestaurant(int actId, int actRole, int resId, HashMap<String, String> info) throws Exception {
        if (actRole == 1)
            throw new Exception("You are not authorized edit restaurant");
        else if (actRole == 2) {
            if (ownerList.getById(actId) == null)
                throw new Exception("You are not authorized edit restaurant");
        }

        if (info.containsKey("name"))
            restaurantList.getRestaurant(resId).setName(info.get("name"));
        
        if (info.containsKey("desc"))
            restaurantList.getRestaurant(resId).setDesc(info.get("desc"));
        
        if (info.containsKey("surburb"))
            restaurantList.getRestaurant(resId).setSurburb(info.get("surburb"));

        if (info.containsKey("street"))
            restaurantList.getRestaurant(resId).setStreet(info.get("street"));

        if (info.containsKey("email"))
            restaurantList.getRestaurant(resId).setEmail(info.get("email"));

        if (info.containsKey("phone"))
            restaurantList.getRestaurant(resId).setPhone(info.get("phone"));

        return true;
    }

    public boolean delRestaurant(int actId, int actRole, int resId) {
        return false;
    }

    // name, surburb, rates
    public ArrayList<HashMap<String, String>> listRestaurant(String keyWord) {
        ArrayList<HashMap<String, String>> results = new ArrayList<>();
        for (int i = 0; i < restaurantList.getRestaurantList().size(); i++) {
            if (restaurantList.getRestaurantList().get(i).getName().contains(keyWord)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("resId", Integer.toString(restaurantList.getRestaurantList().get(i).getResId()));
                map.put("name", restaurantList.getRestaurantList().get(i).getName());
                map.put("surburb", restaurantList.getRestaurantList().get(i).getSurburb());
                ArrayList<Integer> rate = rateList.getRestaurantRateValue(restaurantList.getRestaurantList().get(i).getResId());
                double avg = 0.0;
                if (rate.size() != 0) {
                    for (int r : rate)
                        avg += r;
                    avg = avg / rate.size();
                }
                map.put("rate", avg == 0?"Not rated":Double.toString(avg));

                results.add(map);
            }
        }

        return results;
    }

    public ArrayList<HashMap<String, String>> listRestaurantByOwner(int ownerId) {
        ArrayList<HashMap<String, String>> results = new ArrayList<>();
        for (int i = 0; i < restaurantList.getRestaurantList().size(); i++) {
            if (restaurantList.getRestaurantList().get(i).getOwnerId() == ownerId) {
                HashMap<String, String> map = new HashMap<>();
                map.put("resId", Integer.toString(restaurantList.getRestaurantList().get(i).getResId()));
                map.put("name", restaurantList.getRestaurantList().get(i).getName());
                map.put("surburb", restaurantList.getRestaurantList().get(i).getSurburb());
                ArrayList<Integer> rate = rateList.getRestaurantRateValue(restaurantList.getRestaurantList().get(i).getResId());
                double avg = 0.0;
                for (int r : rate)
                    avg += r;
                avg = avg / rate.size();
                map.put("rate", Double.toString(avg));

                results.add(map);
            }
        }

        return results;
    }

    public ArrayList<HashMap<String, String>> searchItem(String keyWord) {
        return null;
    }

    public HashMap<String, String> viewRestaurant(int resId) {
        if (restaurantList.getRestaurant(resId) == null)
            return null;
        return restaurantList.getRestaurant(resId).toHashMap();
    }

    public HashMap<String, String> viewItem(int resId, int itemId) {
        HashMap<String, String> itemMap = restaurantList.getRestaurant(resId).getItemById(itemId).toHashMap();
        // Count rate
        ArrayList<Integer> itemRates = rateList.getItemRatesValue(resId, itemId);
        int max = 0;
        if (itemRates.size() != 0) {
            int[] rateCount = new int[]{0, 0, 0, 0, 0};
            for (int i : itemRates)
                rateCount[i-1] += 1;
                
            for (int i = 1; i < 5; i++)
                if (rateCount[i] > rateCount[max-1])
                    max = i+1;
        }
        // add rate to map
        String rateStr;
        switch(max) {
            case 1: {rateStr = "Very Bad";break;}
            case 2: {rateStr = "Bad";break;}
            case 3: {rateStr = "Medium";break;}
            case 4: {rateStr = "Good";break;}
            case 5: {rateStr = "Very Good";break;}
            default: {rateStr = "Not rated";break;}
        }
        itemMap.put("itemRate", rateStr);
        // return
        return itemMap;
    }

    public boolean editItem(int resId, char operator, int itemId, HashMap<String, String> info) {
        if (operator == 'a') {
            restaurantList.getRestaurant(resId).addItem(info);
            return true;
        }
        else if (operator == 'm') {
            if (info.containsKey("name"))
                restaurantList.getRestaurant(resId).getItemById(itemId).setItemName(info.get("name"));
            if (info.containsKey("desc"))
                restaurantList.getRestaurant(resId).getItemById(itemId).setItemDesc(info.get("desc"));
            if (info.containsKey("unitPrice"))
                restaurantList.getRestaurant(resId).getItemById(itemId).setUnitPrice(Double.parseDouble(info.get("unitPrice")));
            return true;
        }
        else if (operator == 'd') {
            restaurantList.getRestaurant(resId).delItem(itemId);
        }

        return false;
    }

    public boolean editCoupon(int resId, char operator, HashMap<String, String> info) {
        if (operator == 'a') {
            restaurantList.getRestaurant(resId).addCoupon(info);
        }
        else if (operator == 'm') {
            restaurantList.getRestaurant(resId).setCoupon(info);
        }
        else if (operator == 'd') {
            restaurantList.getRestaurant(resId).delCoupon(info.get("couponCode"));
        }

        return false;
    }

    private boolean authorize() {
        return false;
    }

    private boolean lengthWithinRange(String str, int lowerBound, int upperBound) {
        return false;
    }

    public void saveData() {
        restaurantList.save();
    }
}
