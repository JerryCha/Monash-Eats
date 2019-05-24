import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RestaurantList {

    private static ArrayList<Restaurant> restaurantList = new ArrayList<>();
    private static RestaurantList instance = new RestaurantList();
    private static int maxId;
    
    private RestaurantList() {
        initialize();
        maxId = 0;
        for (Restaurant res : restaurantList)
            if (maxId < res.getResId())
                maxId = res.getResId();
    }
    
    public static RestaurantList getInstance() {
        return instance;
    }

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public static int nextId() {
        maxId += 1;
        return maxId;
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
    /*
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
    */
    public boolean setRestaurant(int index) {
        return true;
    }

    // TODO: Load list from file
    public boolean initialize() {
        // 13 fields
        FileReader reader = null;
        try {
            reader = new FileReader("restaurant_list.dat");
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().trim().split(",");
                if (line.length == 13) {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setResId(Integer.parseInt(line[0]));
                    restaurant.setOwnerId(Integer.parseInt(line[1]));
                    restaurant.setName(line[2]);
                    restaurant.setDesc(line[3]);
                    restaurant.setStreet(line[4]);
                    restaurant.setSurburb(line[5]);
                    restaurant.setEmail(line[6]);
                    restaurant.setPhone(line[7]);
                    String[] coupons = line[8].split(";");
                    for (String coupon : coupons) {
                        String[] fields = coupon.split("-");
                        if (fields.length == 6) {
                            HashMap<String, String> couponMap = new HashMap<>();
                            couponMap.put("couponCode", fields[0]);
                            couponMap.put("couponDesc", fields[1]);
                            couponMap.put("expireDate", fields[2]);
                            couponMap.put("operator", fields[3]);
                            couponMap.put("value", fields[4]);
                            couponMap.put("appliedItemId", fields[5]);

                            restaurant.addCoupon(couponMap);
                        }
                    }
                    String[] items = line[9].split(";");
                    for (String item : items) {
                        String[] fields = item.split("-");
                        if (fields.length == 4) {
                            HashMap<String, String> itemMap = new HashMap<>();
                            itemMap.put("itemId", fields[0]);
                            itemMap.put("itemName", fields[1]);
                            itemMap.put("itemDesc", fields[2]);
                            itemMap.put("unitPrice", fields[3]);

                            restaurant.addItem(itemMap);
                        }
                    }
                    // restaurant.setOpenTime(line[10]);
                    restaurant.setBusinessHour(Double.parseDouble(line[11]));
                    restaurant.setOpenDay(Integer.parseInt(line[12]));

                    restaurantList.add(restaurant);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader == null)
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return true;
    }

    // TODO: Write list to file
    public boolean save() {
        StringBuffer buffer = new StringBuffer();
        for (Restaurant res : restaurantList) {
            buffer.append(res.toString());
            buffer.append("\n");
        }

        FileWriter writer = null;
        File target = null;
        File backup = null;
        try {
            target = new File("restaurant_list.dat");
            backup = new File("restaurant_list.dat.bak");
            Files.copy(target.toPath(), backup.toPath());
            writer = new FileWriter("restaurant_list.dat");
            writer.write(buffer.toString());
            backup.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            backup.renameTo(target);
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
        }
        return true;
    }
}
