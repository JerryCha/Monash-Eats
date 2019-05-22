import java.util.ArrayList;
import java.util.HashMap;

public class CustomerList implements SearchableAccountList {

    private ArrayList<Customer> customerList;

    public CustomerList() {
        customerList = new ArrayList<>();
    }

    public ArrayList<Customer> getList() {
        return customerList;
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

    public boolean del(int index) {
        customerList.remove(index);
        return true;
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

    @Override
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
        if (actInfo.containsKey("pwd"))
            pwdHash = actInfo.get("pwd");
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

        return customerList.add(newCustomer);
    }

}
