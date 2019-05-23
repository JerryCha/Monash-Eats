import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantList {

    private ArrayList<Restaurant> restaurantList;

    public RestaurantList() {
        restaurantList = new ArrayList<>();
        initialize();
    }

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(ArrayList<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public Restaurant getRestaurant(int resId) {
        for (Restaurant res : restaurantList)
            if (res.getResId() == resId)
                return res;

        return null;
    }

    public boolean addRestaurant(Restaurant res) {
        return restaurantList.add(res);
    }

    public boolean delRestaurant(int resId) {
        for (Restaurant res : restaurantList)
            if (res.getResId() == resId) {
                restaurantList.remove(res);
                return true;
            }

        return false;
    }

    public HashMap<Restaurant, ArrayList<Item>> searchItem(String itemName) {
        HashMap<Restaurant, ArrayList<Item>> result = new HashMap<>();
        for (Restaurant res : restaurantList) {
            ArrayList<Item> itemList = new ArrayList<>();
            for (int i = 0; i < res.getMenu().size(); i++)
                if (res.getMenu().get(i).getItemName().contains(itemName))
                    itemList.add(res.getMenu().get(i));

                if (itemList.size() != 0)
                    result.put(res, itemList);
        }

        if (result.size() == 0)
            return null;
        return result;
    }

    public boolean setRestaurant(int index) {
        return true;
    }

    // TODO: Load list from file
    public boolean initialize() {
        return true;
    }

    // TODO: Write list to file
    public boolean save() {
        return true;
    }
}
