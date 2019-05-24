import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MonashEats {

    private int loginId;
    private int loginRole;

    private AccountController accountController;
    private OrderController orderController;
    private RestaurantController restaurantController;
    private RateController rateController;

    public static void main(String[] args) {
        MonashEats monashEats = new MonashEats();
        monashEats.start();
    }

    public void start() {
        System.out.println("Welcome to MonashEats");

        loginId = -1;
        loginRole = -1;
        // Initialize data
        accountController = new AccountController();
        orderController = new OrderController();
        restaurantController = new RestaurantController();
        rateController = new RateController();

        //
        Scanner key = new Scanner(System.in);

        while (true) {
            // Accept command
            System.out.print("$ ");
            String[] cmd = key.nextLine().split(" ");

            // Parse command
            // Account
            if (cmd[0].equals("account")) {
                // Args count verification.
                if (cmd.length == 1) {
                    if (!isLoggedIn()) {
                        System.out.println("You haven't logged in.");
                        continue;
                    }
                    HashMap<String, String> info = accountController.getAccount(loginId, loginRole);
                    System.out.println("Here's your account information.");
                    System.out.println("Account ID: " + info.get("actId"));
                    System.out.print("Account type: ");
                    switch(loginRole) {
                        case 1: {System.out.println("customer");break;}
                        case 2: {System.out.println("Owner");break;}
                        case 3: {System.out.println("Admin");break;}
                    }
                    System.out.println("Email: " + info.get("email"));
                    System.out.println("Name: " + info.get("name"));
                    System.out.println("" + info.get("street"));
                    System.out.println("" + info.get("surburb"));
                    System.out.println("" + info.get("phone"));
                } else if (cmd[1].equals("edit")) {
                    System.out.println("TO BE IMPLEMENTED...");
                }
            }
            // Login
            else if (cmd[0].equals("login")) {
                System.out.print("Please enter your account: ");
                String act = key.nextLine().trim();
                System.out.print("Please enter your password: ");
                String pwd = key.nextLine().trim();
                System.out.print("Please choose the role you want to login: 1-Customer, 2-Owner, 3-Admin");

                int role = -1;
                do {
                    role = acceptInt("");
                } while (!inRange(role, 1, 3));
                // Authenticate, return actId.
                loginId = accountController.authenticate(act, pwd, role);
                // Verify Success
                if (loginId == -1) {
                    System.out.println("Authentication failed");
                    continue;
                } else
                    System.out.println("Authentication success");
                // Set account role
                loginRole = role;
            }
            // Register an account
            else if (cmd[0].equals("register")) {
                System.out.println("- Register needs you to provide your email address and password");
                System.out.println(
                        "- It's optional to fill your name, address, phone, secure question now. You can finalize them at anytime.");
                System.out.println();

                HashMap<String, String> info = new HashMap<>();

                System.out.print("What is your email address? ");
                String email = key.nextLine().trim();
                info.put("email", email);
                System.out.print("What is your password? ");
                String pwd = key.nextLine().trim();
                info.put("pwd", pwd);
                System.out.println("You are a: 1 - Customer; 2 - Restaurant Owner; 3 - Both. ");
                System.out.print("Please specifies by number. ");
                int code = acceptInt("");
                String role = formatedBinaryStr(code, 2);
                info.put("roleCode", role);

                System.out.println(
                        "Any fields to complete? Enter the field number to specify them, using ',' to split your choices. e.g. 1,3,5");
                System.out.println("\t1. name, 2. address, 3. phone, 4. secure question\n");
                System.out.print("Your choices (0 - skip): ");
                String[] choices = acceptString("").split(",");
                int[] optNo = new int[choices.length];
                int choiceCounter = 0;
                for (int i = 0; i < choices.length; i++) {
                    try {
                        optNo[i] = Integer.parseInt(choices[i]);
                        choiceCounter += 1;
                    } catch (NumberFormatException e) {
                        System.out.println(choices[i] + " is not a number");
                    }
                }
                if (choiceCounter != 0 || optNo[0] != 0) {
                    for (int i : optNo)
                        switch (i) {
                        // Put name
                        case 1: {
                            System.out.print("What's your name?");
                            info.put("name", key.nextLine().trim());
                            break;
                        }
                        // Put address
                        case 2: {
                            System.out.print("Which street and the number do you stay in?");
                            info.put("street", key.nextLine().trim());
                            System.out.print("Which surburb stay in?");
                            info.put("surburb", key.nextLine().trim());
                            break;
                        }
                        // Put phone
                        case 3: {
                            System.out.print("What's your phone number?");
                            info.put("phone", key.nextLine().trim());
                            break;
                        }
                        // Put secure question
                        case 4: {
                            System.out.println("1 - What's your father's name? ");
                            System.out.println("2 - What's the name of your primary school? ");
                            System.out.println("3 - What's your pet's name? ");
                            System.out.println("4 - Which city do you born? ");

                            // Standardize string preparation
                            StringBuffer buffer = new StringBuffer();

                            // Q1
                            System.out.print("Choose the first question by entering its number: ");
                            int qCode = acceptInt("");
                            System.out.print("Your answer. ");
                            String ans = null;
                            do {
                                ans = key.nextLine().trim();
                            } while (ans.equals(""));

                            buffer.append(qCode);
                            buffer.append("-");
                            buffer.append(ans);
                            buffer.append(";");

                            // Q2
                            System.out.print("Choose the second question by entering its number: ");
                            qCode = acceptInt("");
                            System.out.print("Your answer. ");
                            ans = null;
                            do {
                                ans = key.nextLine().trim();
                            } while (ans.equals(""));

                            buffer.append(qCode);
                            buffer.append("-");
                            buffer.append(ans);
                            info.put("secureQuestion", buffer.toString());

                            break;
                        }
                        }
                }

                boolean ok = false;
                do {
                    try {
                        ok = accountController.register(info);
                    } catch (Exception e) {
                        if (e.getMessage().equals("Phone format incorrect")) {
                            System.out.print(e.getMessage() + " Please re-enter: ");
                            info.put("phone", acceptString(""));
                        } else
                            e.printStackTrace();
                    }
                } while (!ok);

                System.out.println("Successfully registered");
            }
            // Log out
            else if (cmd[0].equals("logout")) {
                if (loginId == -1)
                    System.out.println("You haven't logged in");
                else {
                    loginId = -1;
                    loginRole = -1;
                    System.out.println("You have successfully logged out");
                }
            } else if (cmd[0].equals("restaurant")) {
                if (cmd.length == 1 || cmd[1].equals("list")) {
                    if (loginRole == 1) {
                        ArrayList<HashMap<String, String>> res = restaurantController.listRestaurant("");
                        printRestaurantList(res);
                    } else if (loginRole == 2) {
                        printRestaurantList(restaurantController.listRestaurantByOwner(loginId));
                    }
                    continue;
                }

                if (cmd[1].equals("search")) {
                    try {
                        ArrayList<HashMap<String, String>> res = restaurantController.listRestaurant(cmd[2]);
                        if (res.size() == 0)
                            System.out.println("Retaurant not found");
                    } catch (Exception e) {
                        ArrayList<HashMap<String, String>> res = restaurantController.listRestaurant("");
                        printRestaurantList(res);
                    }
                }
                // View restaurant by resId
                else if (cmd[1].equals("view")) {
                    if (loginId == -1) {
                        System.out.println("Sorry, only logged in user could view restaurant");
                        continue;
                    }
                    if (loginRole != 1) {
                        System.out.println("Sorry, your are not logged in as customer");
                        continue;
                    }
                    try {
                        int resId = Integer.parseInt(cmd[2]);
                        HashMap<String, String> resInfo = restaurantController.viewRestaurant(resId);
                        if (resInfo == null) {
                            System.out.println("Restaurant not found");
                            continue;
                        }
                        printRestaurant(resInfo);

                        System.out.println("Instructions:");
                        System.out.println(" -> Use add <item_id> to add item. e.g. add 114.");
                        System.out.println(" -> Use view <item_id> to see item detail. e.g. view 114.");
                        System.out.println(" -> Type finish to submit or use leave to back to leave.");
                        String[] subCmds;
                        HashMap<Integer, Integer> ops = new HashMap<>();
                        boolean ctnFlag = true;
                        do {
                            subCmds = acceptString("> ").split(" ");

                            if (subCmds[0].equals("add")) {
                                if (subCmds.length != 2) {
                                    System.out.println("Not adequate args");
                                    continue;
                                }
                                try {
                                    ops.put(Integer.parseInt(subCmds[1]), 1);
                                } catch (Exception e) {
                                    System.out.println(subCmds[1] + " is not a number");
                                }
                            } else if (subCmds[0].equals("view")) {
                                if (subCmds.length != 2) {
                                    System.out.println("Not adequate args");
                                    continue;
                                }
                                try {
                                    HashMap<String, String> itemInfo = restaurantController.viewItem(resId,
                                            Integer.parseInt(subCmds[1]));
                                    System.out.println("\t* Item ID: " + itemInfo.get("itemId"));
                                    System.out.println("\t* Item Name: " + itemInfo.get("itemName"));
                                    System.out.println("\t* Item Description: " + itemInfo.get("itemDesc"));
                                    System.out.println("\t* Item Price: " + itemInfo.get("unitPrice"));
                                    System.out.println("\t* Item Rate: " + itemInfo.get("itemRate"));
                                } catch (Exception e) {
                                    System.out.println(subCmds[1] + " is not a number");
                                }
                            } else if (subCmds[0].equals("finish"))
                                ctnFlag = false;
                            else if (subCmds[0].equals("leave"))
                                ctnFlag = false;
                        } while (ctnFlag);

                        boolean ok = false;
                        if (subCmds[0].equals("finish")) {
                            ok = orderController.editCart(loginId, resId, 'a', ops);
                        }
                        if (!ok)
                            System.out.println("Not ok");
                        else
                            System.out.println("OK");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Please specify the restaurant id");
                    }
                }
                // Editing restaurant
                else if (cmd[1].equals("manage")) {
                    if (cmd.length != 3) {
                        System.out.println("Please specify restaurant number");
                        continue;
                    }

                    int resId;
                    try {
                        resId = Integer.parseInt(cmd[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("incorrect restaurant number");
                        continue;
                    }

                    HashMap<String, String> info = restaurantController.viewRestaurant(resId);
                    if (info == null) {
                        System.out.println("Restaurant not found");
                        continue;
                    }

                    String[] subCmds;
                    do {
                        info = restaurantController.viewRestaurant(resId);
                        printRestaurant(info);
                        System.out
                                .println("\nSelect the fields you want to edit using number (split by ,). e.g. 1,2,3");
                        System.out.println(
                                "To save&exit, enter exit. Any invalid selection will not be handled.");
                        System.out.println(
                                "1 - Name; 2 - Description; 3 - Address; 4 - Email; 5 - Phone. To edit coupon or menu, please type 6 or 7 only.");

                        subCmds = acceptString(info.get("name") + " > ").split(",");

                        try {
                            // 7 - Menu
                            if (subCmds.length == 1 && subCmds[0].equals("7")) {
                                String[] items = info.get("menu").split(";");
                                HashMap<Integer, HashMap<String, String>> itemMap = new HashMap<>();
                                for (String item : items) {
                                    String[] fields = item.split("-");
                                    HashMap<String, String> m = new HashMap<>();
                                    System.out.println("Item ID: " + fields[0]);
                                    System.out.println("---- Name: " + fields[1]);
                                    m.put("itemName", fields[1]);
                                    System.out.println("---- Description: " + fields[2]);
                                    m.put("itemDesc", fields[2]);
                                    System.out.println("---- Unit Price: " + fields[3]);
                                    m.put("unitPrice", fields[3]);
                                    itemMap.put(Integer.parseInt(fields[0]), m);
                                }
                                System.out.println(
                                        "Commands: add - add an item, edit <item id> - edit specific item, del <item id> - delete specific item, exit - exit menu edit.");

                                String[] menuCmd;
                                do {
                                    menuCmd = acceptString(info.get("name") + " > menu edit > ").split(" ");
                                    if (menuCmd[0].equals("add")) {
                                        HashMap<String, String> newItemMap = new HashMap<>();
                                        newItemMap.put("itemName", acceptString("New item's name: "));
                                        newItemMap.put("itemDesc", acceptString("New item's description: "));
                                        newItemMap.put("unitPrice", acceptDouble("New item's unit price: ").toString());

                                        restaurantController.editItem(resId, 'a', -1, newItemMap); // ItemID not need
                                                                                                   // for add
                                    } else if (menuCmd[0].equals("edit")) {
                                        if (menuCmd.length == 1) {
                                            System.out.println("Please specify the item id");
                                            continue;
                                        }

                                        int id;
                                        try {
                                            id = Integer.parseInt(menuCmd[1]);
                                        } catch (NumberFormatException e) {
                                            continue;
                                        }
                                        HashMap<String, String> newItemMap = itemMap.get(id);

                                        newItemMap.put("name", acceptString("New item's name: "));
                                        newItemMap.put("desc", acceptString("New item's description: "));
                                        newItemMap.put("unitPrice", acceptDouble("New item's unit price: ").toString());

                                        restaurantController.editItem(resId, 'm', id, newItemMap);
                                    } else if (menuCmd[0].equals("del")) {
                                        if (menuCmd.length == 1) {
                                            System.out.println("Please specify the item id");
                                            continue;
                                        }
                                        int id;
                                        try {
                                            id = Integer.parseInt(menuCmd[1]);
                                        } catch (NumberFormatException e) {
                                            continue;
                                        }
                                        restaurantController.editItem(resId, 'd', id, null); // info not need for
                                                                                             // deletion
                                    }
                                } while (!menuCmd[0].equals("exit"));
                            }
                            // 6 - Coupon
                            else if (subCmds.length == 1 && subCmds[0].equals("6")) {
                                String[] coupons = info.get("couponList").split(";");
                                for (String coupon : coupons) {
                                    String[] fields = coupon.split("-");
                                    System.out.println("Coupon Code: " + fields[0]);
                                    System.out.println("------ Description: " + fields[1]);
                                    System.out.println("------ Expire Date: " + fields[2]);
                                    System.out.println("------ Operator: " + fields[3]);
                                    System.out.println("------ Value: " + fields[4]);
                                }
                                System.out.println(
                                        "Commands: add - add a coupon, edit <coupon code> - edit coupon, del <coupon code> - delete coupon, exit - exit menu edit.");

                                String[] cpCmd;
                                do {
                                    cpCmd = acceptString(info.get("name") + " > coupon edit > ").split(" ");
                                    if (cpCmd[0].equals("add")) {
                                        HashMap<String, String> couponMap = new HashMap<>();
                                        couponMap.put("couponCode", acceptString("New coupon's code: "));
                                        couponMap.put("couponDesc", acceptString("New coupon's description: "));
                                        couponMap.put("expireDate", acceptString("New coupon's expire date: "));
                                        couponMap.put("operator", acceptString("New coupon's operator: "));
                                        couponMap.put("value", acceptString("New coupon's value: "));
                                        couponMap.put("appliedItemId",
                                                acceptString("New coupon's applied item id(split by ','): "));
                                        restaurantController.editCoupon(resId, 'a', couponMap);
                                    } else if (cpCmd[0].equals("edit")) {
                                        if (cpCmd.length == 1) {
                                            System.out.println("Please specify the coupon");
                                            continue;
                                        }

                                        HashMap<String, String> couponMap = new HashMap<>();
                                        couponMap.put("couponCode", acceptString("New coupon's code: "));
                                        couponMap.put("couponDesc", acceptString("New coupon's description: "));
                                        couponMap.put("expireDate", acceptString("New coupon's expire date: "));
                                        couponMap.put("operator", acceptString("New coupon's operator: "));
                                        couponMap.put("value", acceptString("New coupon's value: "));
                                        couponMap.put("appliedItemId",
                                                acceptString("New coupon's applied item id(split by ','): "));
                                        restaurantController.editCoupon(resId, 'm', couponMap);
                                    } else if (cpCmd[0].equals("del")) {
                                        if (cpCmd.length == 1) {
                                            System.out.println("Please specify the coupon");
                                            continue;
                                        }

                                        HashMap<String, String> couponMap = new HashMap<>();
                                        couponMap.put("couponCode",
                                                acceptString("Coupon to be deleted by entering its code: "));
                                        restaurantController.editCoupon(resId, 'd', info);
                                    } 
                                    else if (cpCmd[0].equals("exit")) {
                                        break;
                                    }
                                    else
                                        System.out.println("Invalid command");
                                } while (true);
                            } 
                            else if (subCmds[0].equals("exit")) {
                                break;
                            }
                            else {
                                for (String s : subCmds) {
                                    int code = Integer.parseInt(s.trim());
                                    switch (code) {
                                        case 1: {
                                            info.put("name", acceptString("New name: "));
                                            break;
                                        }
                                        case 2: {
                                            info.put("desc", acceptString("New description: "));
                                            break;
                                        }
                                        case 3: {
                                            info.put("street", acceptString("New street: "));
                                            info.put("surburb", acceptString("New surburb: "));
                                            break;
                                        }
                                        case 4: {
                                            info.put("email", acceptString("New email: "));
                                            break;
                                        }
                                        case 5: {
                                            info.put("phone", acceptString("New phone: "));
                                            break;
                                        }
                                    }
                                }
                                restaurantController.editRestaurant(loginId, loginRole, resId, info);
                            }
                        } catch (Exception e) {

                        }
                    } while (true);
                }
            }
            // Cart
            else if (cmd[0].equals("cart")) {
                // Check args count
                if (cmd.length == 1)
                    if (loginId == -1 || loginRole == -1) {
                        System.out.println("You haven't logged in yet or you are not logged in as a customer.");
                        continue;
                    }
                // Verify role
                if (loginRole != 1) {
                    System.out.println("You are not logged in as a customer.");
                    continue;
                }
                HashMap<String, String> cart = orderController.viewCart(loginId);
                System.out.println(cart.get("resId"));
                System.out.println("Total Price: " + cart.get("totalPrice"));
                String[] items = cart.get("items").split(";");
                for (String str : items) {
                    // itemId, itemName, itemDesc, unitPrice, quantity
                    String[] itemProperty = str.split("-");
                    System.out.println("\t" + itemProperty[1] + " " + itemProperty[3] + " " + itemProperty[4]);
                }
                if (cmd.length == 2)
                    if (cmd[1].equals("edit")) {
                        System.out.println("TO BE IMPLEMENTED...");
                    }
            }
            // Order
            else if (cmd[0].equals("order")) {
                if (cmd.length < 2) {
                    System.out.println("Please specify your action: place, list");
                    continue;
                }

                // Place order by converting cart to order
                if (cmd[1].equals("place")) {
                    if (loginRole != 1) {
                        System.out.println("Sorry, you are not logged in as customer.");
                        continue;
                    }
                    HashMap<String, String> info = accountController.getAccount(loginId, loginRole);
                    if (!info.containsKey("name")) {
                        System.out.println("Your account does not contain the name. Please specify recipient's name: ");
                        info.put("name", acceptString("").trim());
                    }
                    if (!info.containsKey("street")) {
                        System.out.println("Your account does not contain delivered street. Please specify it: ");
                        info.put("street", acceptString("").trim());
                    }
                    if (!info.containsKey("surburb")) {
                        System.out.println("Your account does not contain delivered surburb. Please specify it: ");
                        info.put("surburb", acceptString("").trim());
                    }
                    if (!info.containsKey("phone")) {
                        System.out.println("Your account does not contain phone number. Please specify it: ");
                        info.put("phone", acceptString("").trim());
                    }
                    boolean ok;
                    try {
                        ok = orderController.placeOrder(info);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (e.getMessage().equals("amount exceed"))
                            System.out.println("You couldn't place an order over $100.");
                        ok = false;
                    }

                    if (ok)
                        System.out.println("Placed successfully");
                    else
                        System.out.println("Placed failed");
                }
                // View history orders
                else if (cmd[1].equals("list")) {
                    if (loginId == -1 || loginRole == -1) {
                        System.out.println("You haven't logged in.");
                        continue;
                    }
                    // Retrieve orders
                    ArrayList<HashMap<String, String>> orders = orderController.getHistoryOrder(loginId, loginRole);
                    // Print order number
                    System.out.println(orders.size() + " order records have been found.");
                    // Print orders
                    for (int i = 0; i < orders.size(); i++) {
                        if (loginRole == 1)
                            System.out.println("Restaurant" + orders.get(i).get("resId"));
                        else
                            System.out.println("Customer: " + orders.get(i).get("cusId"));
                        
                        System.out.println("* Status: " + orders.get(i).get("status"));
                        System.out.println("* Time to deliver: " + orders.get(i).get("timeToDeliver"));
                        System.out.println("* Coupon: " + orders.get(i).get("couponCode"));
                        String[] items = orders.get(i).get("itemList").split(";");
                        for (String item : items) {
                            System.out.println(item);
                        }
                    }
                }
            }
            // Admin
            else if (cmd[0].equals("admin")) {
                if (loginRole != 3) {
                    System.out.println("Only administrator has access right");
                    continue;
                }
                // customer, owner, restaurant, 
                if (cmd.length < 2) {
                    System.out.println("Administrator could use the following commands to manage system.");
                    System.out.println("\tcustomer - manage customer, owner - manage owner, restaurant - manage restaurant");
                    continue;
                }

                if (cmd[1].equals("owner")) {
                    String[] ownCmd;

                    do {
                        System.out.println("");
                        ArrayList<HashMap<String, String>> list = accountController.getAccountList(2);
                        for (int i = 0; i < list.size(); i++) {
                            HashMap<String, String> ele = list.get(i);
                            System.out.println(ele.get("actId") + " - " + ele.get("email") + " - " + (Boolean.parseBoolean(ele.get("isVerified"))?"verified":"not verified"));
                        }
                        System.out.println("Command instruction: list - list onwers, del <owner id> - delete the owner");
                        ownCmd = acceptString("admin > owner management > ").split(" ");
                        try {
                            if (ownCmd[0].equals("del")) {
                                int ownerId = Integer.parseInt(ownCmd[1]);
                                boolean ok = accountController.delAccount(loginId, loginRole, 2, ownerId);
                                if (!ok)
                                    System.out.println("delete failed");
                                else
                                    System.out.println("Owner ID " + ownerId + " has been deleted");
                            }
                            else if (ownCmd[0].equals("list")) {
                                continue;
                            }
                            else if (ownCmd[0].equals("exit"))
                                break;
                            else 
                                System.out.println(ownCmd[0] + " is not a valid command.");
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    } while (true);
                }
                else if (cmd[1].equals("customer")) {
                    System.out.println("TO BE IMPLEMENTED");
                }
                else if (cmd[1].equals("restaurant")) {
                    System.out.println("TO BE IMPLEMENTED");
                }
            }
            // Rate
            else if (cmd[0].equals("rate")) {
                System.out.println("TO BE IMPLEMENTED SOON...");
            }
            // Exit
            else if (cmd[0].equals("exit")) {
                break;
            }
            // Invalid command
            else 
                System.out.println(cmd[0] + " is not a valid command.");
        }

        // Save datas
        accountController.saveData();
        restaurantController.saveData();
        orderController.saveData();

        key.close();
        System.out.println("Thanks for using...");
    }

    private int acceptInt(String msg) {
        String str;
        int nInt = -101;
        boolean errorFlag = false;
        do
        {
            str = acceptString(msg);
            try
            {
                nInt = Integer.parseInt(str);
                errorFlag = false;
            }
            catch (NumberFormatException e)
            {
                System.out.println(str + " is not a number");
                errorFlag = true;
            }
            catch (Exception e)
            {
                System.out.println("error occured");
                errorFlag = true;
            }
        } while (errorFlag);

        return nInt;
    }

    public Double acceptDouble(String msg) {
        String str;
        double nDouble = -101;
        boolean errorFlag = false;
        do
        {
            str = acceptString(msg);
            try
            {
                nDouble = Double.parseDouble(str);
                errorFlag = false;
            }
            catch (NumberFormatException e)
            {
                System.out.println(str + " is not a number");
                errorFlag = true;
            }
            catch (Exception e)
            {
                System.out.println("error occured");
                errorFlag = true;
            }
        } while (errorFlag);

        return nDouble;
    }

    private String acceptString(String msg) {
        Scanner key = new Scanner(System.in);
        System.out.print(msg);
        String str = key.nextLine().trim();
        return str;
    }

    private void printRestaurant(HashMap<String, String> resInfo) {
        System.out.println(resInfo.get("resId") + " - " + resInfo.get("name"));
        System.out.println(resInfo.get("desc"));
        System.out.println("- Email: " + resInfo.get("email") + ", - Phone: " + resInfo.get("phone"));
        System.out.println("- Address: " + resInfo.get("street") + ", " + resInfo.get("surburb"));
        System.out.println("- Open Time: " + resInfo.get("openTime") + ", - Close Time: " + "resInfo.get(\"+businessHour\")");
        System.out.println("- Operating Day: " + formatedBinaryStr(Integer.parseInt(resInfo.get("openDay")), 7));   // TODO: Parse
        // Print coupon
        System.out.println(" * Coupons: ");
        String[] coupons = resInfo.get("couponList").split(";");
        for (String coupon : coupons) {
            String[] fields = coupon.split("-");
            System.out.println("  - " + fields[0] + ": " + fields[1] + ", valid until " + fields[2]);
        }

        String[] items = resInfo.get("menu").split(";");
        System.out.println(" * Menu: ");
        for (String item : items) {
            String[] str = item.split("-");
            System.out.println("- " + str[0] + " " + str[1] + " " +str[3]);
        }
    }

    private void printRestaurantList(ArrayList<HashMap<String, String>> res) {
        for (int i = 0; i < res.size(); i++) {
            System.out.println("\t* " + res.get(i).get("resId") + ", " + res.get(i).get("name") + ", " 
                                                    + res.get(i).get("surburb") + ", " + res.get(i).get("rate"));
        }
    }

    private boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }

        return true;
    }

    private boolean inRange(int x, int lowerBound, int UpperBound) {
        if (x > UpperBound || x < lowerBound)
            return false;

        return  true;
    }

    private String formatedBinaryStr(int x, int width) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Integer.toBinaryString(x));
        while (buffer.length() < width)
            buffer.insert(0, '0');
        return buffer.toString();
    }

    private boolean isLoggedIn() {
        return (loginId==-1)?false:true;
    }
}