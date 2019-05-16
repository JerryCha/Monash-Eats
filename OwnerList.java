package monasheats.java;

import java.util.ArrayList;
import java.util.HashMap;

public class OwnerList implements SearchableAccountList{

    private ArrayList<Owner> ownerList;

    public OwnerList() {
        ownerList = new ArrayList<>();
    }

    public Owner[] getList() {
        return (Owner[]) ownerList.toArray();
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

    public boolean del(int index) {
        ownerList.remove(index);
        return true;
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

    @Override
    public int getIdByEmail(String email) {
        for (Owner owner : ownerList)
            if (owner.getEmail().equals(email))
                return owner.getId();

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

        Owner newOwner = new Owner();
        newOwner.setEmail(email);
        newOwner.setPwdHash(pwdHash);
        newOwner.setName(name);
        newOwner.setStreet(street);
        newOwner.setSurburb(surburb);
        newOwner.setPhone(phone);
        newOwner.setSecureQuestions(secureQuestion);
        newOwner.setVerified(false);

        return ownerList.add(newOwner);
    }
}
