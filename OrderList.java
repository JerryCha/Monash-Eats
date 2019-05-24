import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class OrderList {
    private static ArrayList<Order> orderList = new ArrayList<>();
    private static HashMap<Integer, Cart> cartList = new HashMap<>();
    private static OrderList instance = new OrderList();

    private OrderList() {
        initialize();
    }
    
    public static OrderList getInstance() {
        return instance;
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
        order.setDatetime(new Date()); // TODO: Complete creating an date object with current time.
        order.setTotalPrice(cartList.get(cusId).getTotalPrice());
        order.setStatus("Pending");
        order.setTimeToDeliver(-1);
        if (!info.containsKey("cusId") && !info.containsKey("address") && !info.containsKey("phone")
                && !info.containsKey("couponCode"))
            return false;
        order.setCusId(cusId);
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
        FileReader reader = null;
        FileReader reader2 = null;
        try {
            // load cart
            reader = new FileReader("cart_list.dat");
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length == 4) {
                    Cart cart = new Cart();
                    cart.setResId(Integer.parseInt(line[1]));
                    String[] items = line[2].trim().split(";");
                    for (String item : items) {
                        String[] fields = item.trim().split("-");
                        if (fields.length == 5) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("itemId", fields[0]);
                            map.put("itemName", fields[1]);
                            map.put("itemDesc", fields[2]);
                            map.put("unitPrice", fields[3]);
                            cart.addItem(map, Integer.parseInt(fields[4]));
                        }
                    }
                    cartList.put(Integer.parseInt(line[0]), cart);
                }
            }

            // load order
            reader2 = new FileReader("order_list.dat");
            Scanner scanner2 = new Scanner(reader2);
            while (scanner2.hasNextLine()) {
                String[] line = scanner2.nextLine().split(",");
                if (line.length == 11) {
                    Order order = new Order();
                    order.setCusId(Integer.parseInt(line[0]));
                    order.setResId(Integer.parseInt(line[1]));
                    //order.setDatetime(line[2]);
                    order.setAddress(line[3].trim()+", "+line[4].trim());
                    order.setPhone(line[5]);
                    String[] items = line[6].trim().split(";");
                    for (String item : items) {
                        String[] fields = item.trim().split("-");
                        if (fields.length == 5) {
                            HashMap<String, String> itemMap = new HashMap<>();
                            itemMap.put("itemId", fields[0]);
                            itemMap.put("itemName", fields[1]);
                            itemMap.put("itemDesc", fields[2]);
                            itemMap.put("unitPrice", fields[3]);
                            order.addItem(itemMap, Integer.parseInt(fields[4]));
                        }
                    }
                    order.setTotalPrice(Double.parseDouble(line[7]));
                    order.setCouponCode(line[8]);
                    order.setStatus(line[9]);
                    order.setTimeToDeliver(Double.parseDouble(line[10]));

                    orderList.add(order);
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                    reader2.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return true;
    }

    public boolean save() {
        StringBuffer buffer;
        StringBuffer buffer2;

        PrintWriter writer = null;
        PrintWriter writer2 = null;
        File target = null;
        File backup = null;
        File target2 = null;
        File backup2 = null;
        try {
            target = new File("cart_list.dat");
            backup = new File("cart_list.dat.bak");
            Files.copy(target.toPath(), backup.toPath());
            // Write cart_list
            Iterator<Map.Entry<Integer, Cart>> it = cartList.entrySet().iterator();
            buffer = new StringBuffer();
            while (it.hasNext()) {
                Map.Entry<Integer, Cart> entry = it.next();
                buffer.append(entry.getKey());
                buffer.append(",");
                buffer.append(entry.getValue().toString());
                buffer.append("\n");
            }
            writer = new PrintWriter("cart_list.dat");
            writer.write(buffer.toString());
            backup.delete();

            target2 = new File("order_list.dat");
            backup2 = new File("order_list.dat.bak");
            Files.copy(target2.toPath(), backup2.toPath());
            // Write order_list
            buffer2 = new StringBuffer();
            for (Order order : orderList) {
                buffer2.append(order.toString());
                buffer2.append("\n");
            }
            writer2 = new PrintWriter("order_list.dat");
            writer2.write(buffer2.toString());
            backup2.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            backup.renameTo(target);
            backup2.renameTo(target2);
        } finally {
            if (writer != null)
                try {
                    writer.close();
                    writer2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return true;
    }
}
