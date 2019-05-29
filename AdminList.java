import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AdminList {

    // Admin list. 
    private static ArrayList<Admin> adminList = new ArrayList<>();
    // AdminList instance
    private static AdminList instance = new AdminList();
    // 
    private static int maxId;

    /**
     * Constructor to load data from file and get the current max id of admin account.
     */
    private AdminList() { 
        initialize();
        maxId = 0;
        for (Admin admin : adminList)
            if (maxId < admin.getId())
                maxId = admin.getId();
    }

    /**
     * Get instance of class
     * @return instance of class
     */
    public static AdminList getInstance() {
        return instance;
    }

    /**
     * Get next admin id. Used in admin create.
     * @return next id
     */
    public static int nextId() {
        maxId += 1;
        return maxId;
    }

    /**
     * Get admin by index
     * @param index index
     * @return The indexth's admin
     */
    public Admin get(int index) {
        return adminList.get(index);
    }

    /**
     * Get admin by adminId
     * @param id admin id
     * @return Admin with adminId. If not found, return null.
     */
    public Admin getById(int id) {
        for (Admin admin : adminList)
            if (admin.getId() == id)
                return admin;

        return null;
    }

    /**
     * Get admin by email
     * @param email email address of admin.
     * @return Admin with email address. If not found, return null.
     */
    public Admin getByEmail(String email) {
        for (int i = 0; i < adminList.size(); i++)
            if (adminList.get(i).getEmail().equals(email))
                return adminList.get(i);

        return null;
    }

    /**
     * Set adminList
     * @param adminList
     */
    public void setList(ArrayList<Admin> adminList) {
        this.adminList = adminList;
    }

    /**
     * Add an admin
     * @param newAdmin the new admin
     * @return add result.
     */
    public boolean add(Admin newAdmin) {
        Admin admin = new Admin();
        return adminList.add(newAdmin);
    }

    /**
     * Set indexth's admin
     * @param index 
     * @param admin
     * @return set result
     */
    public boolean set(int index, Admin admin) {
        adminList.set(index, admin);
        return true;
    }

    /**
     * Delete an admin by index
     * @param index
     * @return deletion result
     */
    public boolean del(int index) {
        adminList.remove(index);
        return true;
    }

    /**
     * Check whether there is an admin with given email address
     * @param email
     * @return adminId. -1 means no such admin with given email address.
     */
    public int has(String email) {
        for (int i = 0; i < adminList.size(); i++)
            if (adminList.get(i).getEmail().equals(email))
                return i;

        return -1;
    }

    /**
     * Get the number of admin.
     * @return The number of admin
     */
    public int getCount() {
        return adminList.size();
    }


    /**
     * Get the admin id by email
     * @param email admin's email address
     * @return admin id. -1 means no such admin.
     */
    public int getIdByEmail(String email) {
        for (Admin admin : adminList)
            if (admin.getEmail().equals(email))
                return admin.getId();

        return -1;
    }

    /**
     * Create admin
     * @param actInfo
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
                String[] qaArray = qa.split(",");

                if (qaArray.length != 2) // Validate whether it is a pair
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

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPwdHash(pwdHash);
        admin.setName(name);
        admin.setStreet(street);
        admin.setSurburb(surburb);
        admin.setPhone(phone);
        admin.setSecureQuestions(secureQuestion);
        admin.setId(nextId());

        return adminList.add(admin);
    }

    /**
     * Initialize data by reading file.
     */
    public void initialize() {
        FileReader reader = null;
        try {
            reader = new FileReader("admin_list.dat");
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length == 8) {
                    Admin admin = new Admin();
                    admin.setId(Integer.parseInt(line[0]));
                    admin.setEmail(line[1]);
                    admin.setPwdHash(line[2]);
                    admin.setName(line[3]);
                    admin.setStreet(line[4]);
                    admin.setSurburb(line[5]);
                    admin.setPhone(line[6]);
                    String[] qas = line[7].split(";");
                    if (qas.length == 2) {
                        HashMap<Integer, String> qaMap = new HashMap<>();
                        for (String qa : qas) {
                            String[] pair = qa.split("-");
                            qaMap.put(Integer.parseInt(pair[0]), pair[1]);
                        }
                        admin.setSecureQuestions(qaMap);
                    } else
                        admin.setSecureQuestions(null);

                    adminList.add(admin);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no such data source file.");
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.println("IOException happened. May be something wrong with your IO system");
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
            target = new File("admin_list.dat");
            backup = new File("admin_list.dat.bak");
            Files.copy(target.toPath(), backup.toPath());
            writer = new PrintWriter("admin_list.dat");
            StringBuffer buffer = new StringBuffer();
            for (Admin admin : adminList) {
                buffer.append(admin.toString());
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
