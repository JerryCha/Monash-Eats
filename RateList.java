import java.util.ArrayList;

public class RateList {
        private static ArrayList<RestaurantRate> resRateList = new ArrayList<>();
        private static ArrayList<ItemRate> itemRateList = new ArrayList<>();
        private static RateList instance = new RateList();
        private RateList(){
                
        }
        
        public static RateList getInstance() {
            return instance;
        }
        
        public ArrayList<RestaurantRate> getResRateList(){
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
        public ArrayList<ItemRate> getitemRateList(int index){
                return itemRateList;
        }
        public ArrayList<Integer> getItemRatesValue(int resId, int itemId){
                ArrayList<Integer> results = new ArrayList<>();
                for (ItemRate rate : itemRateList)
                        if (rate.getResId() == resId && rate.getItemId() == itemId)
                                results.add(rate.getRateVaule());
                return results;
        }
        public boolean addRestaurantRate(RestaurantRate restaurantRate){
                return resRateList.add(restaurantRate);
        }
}
