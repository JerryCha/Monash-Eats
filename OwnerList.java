import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OwnerList{

    private static ArrayList<Owner> ownerList = new ArrayList<>();
    private static OwnerList instance = new OwnerList();

    private OwnerList() {
        initialize();
    }
    
    public static OwnerList getInstance() {
        return instance;
    }

    public ArrayList<HashMap<String, String>> getList() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (Owner owner : ownerList)
            list.add(owner.toHashMap());
        return list;
    }

    public Owner get(int index) {
        return ownerList.get(index);
    }

    public Owner getById(int id) {
        for (Owner owner : ownerList)
            if (owner.getId() == id)
                return owner;

        return null;
    }

    public Owner getByEmail(String email) {
        for (int i = 0; i < ownerList.size(); i++)
            if (ownerList.get(i).getEmail().equals(email))
                return ownerList.get(i);

        return null;  // NOT FOUND
    }

    public void setList(ArrayList<Owner> ownerList) {
        this.ownerList = ownerList;
    }

    public boolean set(int index, Owner owner) {
        ownerList.set(index, owner);
        return true;
    }

    public boolean add(Owner newOwner) {
        newOwner.setId(ownerList.size()+1);
        return ownerList.add(newOwner);
    }

    public boolean del(int ownerId) {
        for (int i = 0; i < ownerList.size(); i++)
            if (ownerList.get(i).getId() == ownerId) {
                ownerList.remove(i);
                return true;
            }
            
        return false;
    }

    public int has(String email) {
        for (int i = 0; i < ownerList.size(); i++)
            if (ownerList.get(i).getEmail().equals(email))
                return i;

        return -1;
    }

    public int getCount() {
        return ownerList.size();
    }

    public int getIdByEmail(String email) {
        for (Owner owner : ownerList)
            if (owner.getEmail().equals(email))
                return owner.getId();

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

        Owner newOwner = new Owner();
        newOwner.setEmail(email);
        newOwner.setPwdHash(pwdHash);
        newOwner.setName(name);
        newOwner.setStreet(street);
        newOwner.setSurburb(surburb);
        newOwner.setPhone(phone);
        newOwner.setSecureQuestions(secureQuestion);
        newOwner.setVerified(false);
        newOwner.setId(ownerList.size()+1);

        return ownerList.add(newOwner);
    }

    public void initialize() {
        FileReader reader = null;
        try {
            reader = new FileReader("owner_list.dat");
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length == 9) {
                    Owner owner = new Owner();
                    owner.setId(Integer.parseInt(line[0]));
                    owner.setEmail(line[1]);
                    owner.setPwdHash(line[2]);
                    owner.setName(line[3]);
                    owner.setStreet(line[4]);
                    owner.setSurburb(line[5]);
                    owner.setPhone(line[6]);
                    String[] qas = line[7].split(";");
                    if (qas.length == 2) {
                        HashMap<Integer, String> qaMap = new HashMap<>();
                        for (String qa : qas) {
                            String[] pair = qa.split("-");
                            qaMap.put(Integer.parseInt(pair[0]), pair[1]);
                        }
                        owner.setSecureQuestions(qaMap);
                    } else
                        owner.setSecureQuestions(null);
                    owner.setVerified(Boolean.parseBoolean(line[8]));

                    ownerList.add(owner);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        PrintWriter writer = null;
        File target = null;
        File backup = null;
        try {
            target = new File("owner_list.dat");
            backup = new File("owner_list.dat.bak");
            Files.copy(target.toPath(), backup.toPath());
            writer = new PrintWriter("owner_list.dat");
            StringBuffer buffer = new StringBuffer();
            for (Owner owner : ownerList) {
                buffer.append(owner.toString());
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
