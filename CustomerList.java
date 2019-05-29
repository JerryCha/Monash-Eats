import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CustomerList {

    // customer list
    private static ArrayList<Customer> customerList = new ArrayList<>();
    // class instance
    private static CustomerList instance = new CustomerList();
    // Current maximum id
    private static int maxId;
    
    /**
     * Constructor that initialize the list
     */
    private CustomerList() {
        initialize();
        maxId = 0;
        for (Customer cus : customerList)
            if (cus.getId() > maxId)
                maxId = cus.getId();
    }

    /**
     * get the next id
     * @return next maxId
     */
    private static int nextId() {
        maxId += 1;
        return maxId;
    }
    
    /**
     * Get the class's instance
     * @return instance
     */
    public static CustomerList getInstance() {
        return instance;
    }

    /**
     * Get the list's information
     * @return List of customer hashmap.
     */
    public ArrayList<HashMap<String, String>> getList() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (Customer customer : customerList)
            list.add(customer.toHashMap());
        return list;
    }

    /**
     * Get customer by index
     * @param index index
     * @return customer
     */
    public Customer get(int index) {
        return customerList.get(index);
    }

    /**
     * Get customer by id
     * @param id customer id
     * @return customer. if not found, return null.
     */
    public Customer getById(int id) {
        for (Customer cus : customerList)
            if (cus.getId() == id)
                return cus;

        return null;
    }

    /**
     * Get customer by email
     * @param email email
     * @return customer. If not found, return null.
     */
    public Customer getByEmail(String email) {
        for (int i = 0; i < customerList.size(); i++)
            if (customerList.get(i).getEmail().equals(email))
                return customerList.get(i);

        return null;
    }

    /**
     * Set customerlist
     * @param customerList 
     */
    public void setList(ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }

    /**
     * Set customer
     * @param index index of customer in the list
     * @param customer
     * @return result
     */
    public boolean set(int index, Customer customer) {
        customerList.set(index, customer);
        return true;
    }

    /**
     * Add a customer to the list
     * @param newCustomer new customer
     * @return result
     */
    public boolean add(Customer newCustomer) {
        newCustomer.setId(customerList.size()+1);
        return customerList.add(newCustomer);
    }

    /**
     * Delete a customer by id.
     * @param cusId customer id
     * @return deletion result
     */
    public boolean del(int cusId) {
        for (Customer cus : customerList)
            if (cus.getId() == cusId)
                return customerList.remove(cus);
        return false;
    }

    /**
     * Has a customer in the list. Checked by email.
     * @param email 
     * @return index. If not found, return -1
     */
    public int has(String email) {
        // Initialize iterator
        for (int i = 0; i < customerList.size(); i++)
            if (customerList.get(i).getEmail().equals(email))
                return i;   // Found

        return -1;   // Not found
    }

    /**
     * Get the number of customer in the list
     * @return the number of customer
     */
    public int getCount() {
        return customerList.size();
    }

    /**
     * Get the customer id by email
     * @param email 
     * @return customer id. If not found, return null.
     */
    public int getIdByEmail(String email) {
        for (int i = 0; i < customerList.size(); i++)
            if (customerList.get(i).getEmail().equals(email))
                return customerList.get(i).getId();
        return -1;
    }

    /**
     * Create a customer by passing information wrapped in hashmap
     * @param actInfo information
     * @return creation result.
     */
    public boolean create(HashMap<String, String> actInfo) {
        String email = null;
        String pwdHash = null;
        String name = null;
        String street = null;
        String surburb = null;
        String phone = null;
        HashMap<Integer, String> secureQuestion = new HashMap<>();

        // Unpack information wrap.
        if (actInfo.containsKey("email"))
            email = actInfo.get("email");
        if (actInfo.containsKey("pwdHash"))
            pwdHash = actInfo.get("pwdHash");
        if (actInfo.containsKey("name"))
            name = actInfo.get("name");
        if (actInfo.containsKey("street"))
            street = actInfo.get("street");
        if (actInfo.containsKey("surburb"))
            surburb = actInfo.get("surburb");
        if (actInfo.containsKey("phone"))
            phone = actInfo.get("phone");
        if (actInfo.containsKey("secureQuestion")) {
            String[] qaPair = actInfo.get("secureQuestion").trim().split(";");
            for (String qa : qaPair) {
                String[] qaArray = qa.split("-");

                if (qaArray.length != 2)    // Validate whether it is a pair
                    continue;

                try {
                    secureQuestion.put(Integer.parseInt(qaArray[0]), qaArray[1]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Customer newCustomer = new Customer();
        newCustomer.setEmail(email);
        newCustomer.setPwdHash(pwdHash);
        newCustomer.setName(name);
        newCustomer.setStreet(street);
        newCustomer.setSurburb(surburb);
        newCustomer.setPhone(phone);
        newCustomer.setSecureQuestions(secureQuestion);
        newCustomer.setId(nextId());

        return customerList.add(newCustomer);
    }

    /**
     * Initialize the cusomter list by reading file
     */
    public void initialize() {
        FileReader reader = null;
        try {
            reader = new FileReader("customer_list.dat");
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length == 8) {
                    Customer customer = new Customer();
                    customer.setId(Integer.parseInt(line[0]));
                    customer.setEmail(line[1]);
                    customer.setPwdHash(line[2]);
                    customer.setName(line[3]);
                    customer.setStreet(line[4]);
                    customer.setSurburb(line[5]);
                    customer.setPhone(line[6]);
                    String[] qas = line[7].split(";");
                    if (qas.length == 2) {
                        HashMap<Integer, String> qaMap = new HashMap<>();
                        for (String qa : qas) {
                            String[] pair = qa.split("-");
                            qaMap.put(Integer.parseInt(pair[0]), pair[1]);
                        }
                        customer.setSecureQuestions(qaMap);
                    } else
                        customer.setSecureQuestions(null);

                    customerList.add(customer);
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Save data to file system.
     */
    public void save() {
        PrintWriter writer = null;
        File target = null;
        File backup = null;
        try {
            target = new File("customer_list.dat");
            backup = new File("customer_list.bak");
            Files.copy(target.toPath(), backup.toPath());
            writer = new PrintWriter("customer_list.dat");
            writer = new PrintWriter("customer_list.dat");
            StringBuffer buffer = new StringBuffer();
            for (Customer customer : customerList) {
                buffer.append(customer.toString());
                buffer.append("\n");
            }
            writer.write(buffer.toString());
            backup.delete();
        } catch (IOException e) {
            e.printStackTrace();
            backup.renameTo(target);
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
