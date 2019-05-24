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

    private static ArrayList<Admin> adminList = new ArrayList<>();
    private static AdminList instance = new AdminList();
    private static int maxId;

    private AdminList() { 
        initialize();
        maxId = 0;
        for (Admin admin : adminList)
            if (maxId < admin.getId())
                maxId = admin.getId();
    }

    public static AdminList getInstance() {
        return instance;
    }

    public static int nextId() {
        maxId += 1;
        return maxId;
    }

    public Admin get(int index) {
        return adminList.get(index);
    }

    public Admin getById(int id) {
        for (Admin admin : adminList)
            if (admin.getId() == id)
                return admin;

        return null;
    }

    public Admin getByEmail(String email) {
        for (int i = 0; i < adminList.size(); i++)
            if (adminList.get(i).getEmail().equals(email))
                return adminList.get(i);

        return null;
    }

    public void setList(ArrayList<Admin> adminList) {
        this.adminList = adminList;
    }

    public boolean add(Admin newAdmin) {
        Admin admin = new Admin();
        return adminList.add(newAdmin);
    }

    public boolean set(int index, Admin admin) {
        adminList.set(index, admin);
        return true;
    }

    public boolean del(int index) {
        adminList.remove(index);
        return true;
    }

    public int has(String email) {
        for (int i = 0; i < adminList.size(); i++)
            if (adminList.get(i).getEmail().equals(email))
                return i;

        return -1;
    }

    public int getCount() {
        return adminList.size();
    }


    public int getIdByEmail(String email) {
        for (Admin admin : adminList)
            if (admin.getEmail().equals(email))
                return admin.getId();

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
