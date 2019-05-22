import java.util.ArrayList;

public class RateList {
        private ArrayList<RestaurantRate> resRateList;
        private ArrayList<ItemRate> itemRateList;
        
        public RateList(){
                resRateList = new ArrayList<>();
                itemRateList = new ArrayList<>();
        }
        public ArrayList<RestaurantRate> getResteList(){
                return resRateList;
        }

        public RestaurantRate getRestaurantRate(int index){
                return resRateList.get(index);
        }
        public ArrayList<ItemRate> getitemRate(int index){
                return itemRateList;
        }
        public ItemRate getItemRate(int index){
                return itemRateList.get(index);
        }
        public boolean addRestaurantRate(RestaurantRate restaurantRate){
                return resRateList.add(restaurantRate);
        }
}
