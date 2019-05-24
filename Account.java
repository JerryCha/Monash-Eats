import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Account {

    // Attributes
    protected int accountId;
    protected String email;
    protected String pwdHash;
    protected String name;
    protected String street;
    protected String surburb;
    protected String phone;
    protected HashMap<Integer, String> secureQuestions;

    public String getAddress() {
        return street + ", " + surburb;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPwdHash() {
        return pwdHash;
    }

    public String getStreet() {
        return street;
    }

    public String getSurburb() {
        return surburb;
    }

    public HashMap<Integer, String> getSecureQuestions() {
        return secureQuestions;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.accountId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setSurburb(String surburb) {
        this.surburb = surburb;
    }

    public void setSecureQuestions(HashMap<Integer, String> secureQuestions) {
        this.secureQuestions = secureQuestions;
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> actMap = new HashMap<>();
        actMap.put("actId", Integer.toString(accountId));
        actMap.put("email", email);
        actMap.put("pwdHash", pwdHash);
        actMap.put("name", name);
        actMap.put("street", street);
        actMap.put("surburb", surburb);
        actMap.put("phone", phone);

        if (secureQuestions != null) {
            StringBuffer qaStrBuffer = new StringBuffer();
            Iterator it = secureQuestions.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                qaStrBuffer.append(pair.getKey());
                qaStrBuffer.append(",");
                qaStrBuffer.append((String)pair.getValue());
                qaStrBuffer.append(";");
            }

            actMap.put("secureQuestion", qaStrBuffer.toString());
        } else 
            actMap.put("secureQuestion", "");

        return actMap;
    }
}
