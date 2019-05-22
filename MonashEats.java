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
                    HashMap<String, String> info = accountController.getAccount(loginId, loginRole);
                    System.out.println("Here's your account information.");
                    System.out.println(info.get("actId"));
                    System.out.println(info.get("email"));
                    System.out.println(info.get("name"));
                    System.out.println(info.get("street"));
                    System.out.println(info.get("surburb"));
                    System.out.println(info.get("phone"));
                } else if (cmd[1].equals("manage")) {
                    if (loginRole != 2) {
                        System.out.println("You are not admin.");
                        continue;
                    }
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
                if (loginId == -3) {
                    System.out.println("Authentication failed");
                    continue;
                }
                // Set account role
                loginRole = role;
            } 
            // Register an account
            else if (cmd[0].equals("register")) {
                System.out.println("- Register needs you to provide your email address and password");
                System.out.println("- It's optional to fill your name, address, phone, secure question now. You can finalize them at anytime.");
                System.out.println();

                HashMap<String, String> info = new HashMap<>();

                System.out.print("What is your email address? ");
                String email = key.nextLine().trim();
                info.put("email", email);
                System.out.print("What is your password? ");
                String pwd = key.nextLine().trim();
                info.put("pwd", pwd);
                System.out.println("You are a: 1 - Customer; 2 - Restaurant Owner; 3 - Both.");
                System.out.print("Please specifies by number. ");
                int code = acceptInt("");
                String role = formatedBinaryStr(code, 2);
                info.put("roleCode", role);

                System.out.println("Any fields to complete? Enter the field number to specify them, using ',' to split your choices. e.g. 1,3,5");
                System.out.println("\t1. name, 2. address, 3. phone, 4. secure question\n");
                System.out.print("Your choices (0 - skip): ");
                String[] choices = acceptString("").split(","); System.out.println(choices.length);
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
                        System.out.println("submit phase");
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
            }
            else if (cmd[0].equals("restaurant")) {

            }
            // Cart
            else if (cmd[0].equals("cart")) {
                if (cmd.length == 1)
                    if (loginId == -1 || loginRole == -1) {
                        System.out.println("You haven't logged in yet or you are not logged in as a customer.");
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
                    boolean ok = orderController.placeOrder(info);

                    if (ok)
                        System.out.println("Placed successfully");
                    else
                        System.out.println("Placed failed");
                }
                // View history orders
                else if (cmd[1].equals("list")) {

                }
            }
            else if (cmd[0].equals("rate")) {
                System.out.println("TO BE IMPLEMENTED SOON...");
            }
            else if (cmd[0].equals("exit")) {
                break;
            }
            else 
                System.out.println(cmd[0] + " is not a valid command.");
        }

        key.close();
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

    private String acceptString(String msg) {
        Scanner key = new Scanner(System.in);
        String str = key.nextLine().trim();
        return str;
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
}