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

    private static ArrayList<Customer> customerList = new ArrayList<>();
    private static CustomerList instance = new CustomerList();
    
    private CustomerList() {
        initialize();
    }
    
    public static CustomerList getInstance() {
        return instance;
    }

    public ArrayList<HashMap<String, String>> getList() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (Customer customer : customerList)
            list.add(customer.toHashMap());
        return list;
    }

    public Customer get(int index) {
        return customerList.get(index);
    }

    public Customer getById(int id) {
        for (Customer cus : customerList)
            if (cus.getId() == id)
                return cus;

        return null;
    }

    public Customer getByEmail(String email) {
        for (int i = 0; i < customerList.size(); i++)
            if (customerList.get(i).getEmail().equals(email))
                return customerList.get(i);

        return null;
    }

    public void setList(ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }

    public boolean set(int index, Customer customer) {
        customerList.set(index, customer);
        return true;
    }

    // TODO: Change parameter type to HashMap. Add parser.
    public boolean add(Customer newCustomer) {
        newCustomer.setId(customerList.size()+1);
        return customerList.add(newCustomer);
    }

    public boolean del(int cusId) {
        for (Customer cus : customerList)
            if (cus.getId() == cusId)
                return customerList.remove(cus);
        return false;
    }

    public int has(String email) {
        // Initialize iterator
        for (int i = 0; i < customerList.size(); i++)
            if (customerList.get(i).getEmail().equals(email))
                return i;   // Found

        return -1;   // Not found
    }

    public int getCount() {
        return customerList.size();
    }

    public int getIdByEmail(String email) {
        for (int i = 0; i < customerList.size(); i++)
            if (customerList.get(i).getEmail().equals(email))
                return customerList.get(i).getId();
        return -1;
    }

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
        newCustomer.setId(customerList.size()+1);

        return customerList.add(newCustomer);
    }

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
