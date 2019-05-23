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

        public ArrayList<RestaurantRate> getRestaurantRate(int resId){
                ArrayList<RestaurantRate> results = new ArrayList<>();
                for (RestaurantRate rate : resRateList)
                        if (rate.getResId() == resId)
                                results.add(rate);

                return results;
        }

        public ArrayList<Integer> getRestaurantRateValue(int resId) {
                ArrayList<Integer> results = new ArrayList<>();
                for (RestaurantRate rate : resRateList)
                        if (rate.getResId() == resId)
                                results.add(rate.getRateVale());

                return results;
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
