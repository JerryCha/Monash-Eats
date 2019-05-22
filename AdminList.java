import java.util.ArrayList;
import java.util.HashMap;

public class AdminList implements SearchableAccountList{

    private ArrayList<Admin> adminList;

    public AdminList() {
        adminList = new ArrayList<>();
    }

    public Admin[] getList() {
        return (Admin[]) adminList.toArray();
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

    @Override
    public int getIdByEmail(String email) {
        for (Admin admin : adminList)
            if (admin.getEmail().equals(email))
                return admin.getId();

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
                String[] qaArray = qa.split(",");

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

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPwdHash(pwdHash);
        admin.setName(name);
        admin.setStreet(street);
        admin.setSurburb(surburb);
        admin.setPhone(phone);
        admin.setSecureQuestions(secureQuestion);
        admin.setId(adminList.size()+1);

        return adminList.add(admin);
    }
}
