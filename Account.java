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

    /**
     * Get account's address. Concatenating street and surburb.
     * @return street+", "+surburb
     */
    public String getAddress() {
        return street + ", " + surburb;
    }

    /**
     * Get email address
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get account id
     * @return  account id
     */
    public int getId() {
        return accountId;
    }

    /**
     * Get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get phone number
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Get password hash
     * @return  password hash
     */
    public String getPwdHash() {
        return pwdHash;
    }

    /**
     * Get street
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Get surburb
     * @return Get surburb.
     */
    public String getSurburb() {
        return surburb;
    }

    /**
     * Get secure questions. Packed in HashMap. Integer as the key is the reference number of question.
     * @return secure quesrion
     */
    public HashMap<Integer, String> getSecureQuestions() {
        return secureQuestions;
    }

    /**
     * Get email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set account id.
     * @param id account id.
     */
    public void setId(int id) {
        this.accountId = id;
    }

    /**
     * Set name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set phone.
     * @param phone phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }

    /**
     * Set street
     * @param street street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get surburb
     * @param surburb surburb
     */
    public void setSurburb(String surburb) {
        this.surburb = surburb;
    }

    /**
     * Set secure question
     * @param secureQuestions sequre question map.
     */
    public void setSecureQuestions(HashMap<Integer, String> secureQuestions) {
        this.secureQuestions = secureQuestions;
    }

    /**
     * Convert account into a hashmap. Attribute as the key.
     * @return hashmap of account
     */
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
