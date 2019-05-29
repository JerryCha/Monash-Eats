import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class AccountController {

    private CustomerList customerList;
    private OwnerList ownerList;
    private AdminList adminList;

    /**
     * Initialize account controller. 
     */
    public AccountController() {
        customerList = CustomerList.getInstance();
        ownerList = OwnerList.getInstance();
        adminList = AdminList.getInstance();
    }

    /**
     * Authentication.
     * @param email account email
     * @param pwd account password
     * @param role attempting to login as. 1-customer, 2-owner, 3-admin
     * @return account id (role associated)
     */
    public int authenticate(String email, String pwd, int role) {
        /*String pwdHash = getSHA256String(pwd);
        System.out.println("log| pwdHash: " + pwdHash);*/String pwdHash = pwd;
        
        if (pwdHash == null)
            return -1;  // Error happened

        if (role == 1) {
            if (customerList.getByEmail(email) != null && customerList.getByEmail(email).getPwdHash().equals(pwdHash))
                return customerList.getIdByEmail(email);

        } else if (role == 2) {
            if (ownerList.getByEmail(email) != null && ownerList.getByEmail(email).getPwdHash().equals(pwdHash))
                return ownerList.getByEmail(email).getId();
        } else if (role == 3) {
            if (adminList.getByEmail(email) != null && adminList.getByEmail(email).getPwdHash().equals(pwdHash))
                return adminList.getByEmail(email).getId();
        }

        return -1;
    }

    /**
     * Register a user.
     * @param actInfo
     * @return register status. 
     */
    public boolean register(HashMap<String, String> actInfo) throws Exception{
        if (actInfo == null)
            return false;

        // Role identify code: customer, owner: 01(owner), 10(customer), 11(customer&owner)
        if (!actInfo.containsKey("roleCode"))
            return false;
        else {
            // Get roleCode
            char[] code = actInfo.get("roleCode").trim().toCharArray();

            if (code.length != 2)
                return false;   // incorrect code format.

            // Convert pwd to pwdHash
            if (actInfo.containsKey("pwd")) {
                //actInfo.put("pwdHash", getSHA256String(actInfo.get("pwd")));
                actInfo.put("pwdHash", actInfo.get("pwd"));
                actInfo.remove("pwd");
            }

            // Phone validation
            if (actInfo.containsKey("phone")) {
                String phone = actInfo.get("phone");
                if (phone.charAt(0) == '+')
                    if (isNumeric(phone.substring(1)))
                        throw new Exception("Phone format incorrect");
                else
                    if (isNumeric(phone))
                        throw new Exception("Phone format incorrect");
            }

            boolean[] results = {false, false};
            // Customer == true
            if (code[1] == '1') {
                if (customerList.has(actInfo.get("email")) != -1)
                    throw new Exception("Customer existed");
                results[0] = customerList.create(actInfo);
            }
            else
                results[0] = true;
            // Owner == true
            if (code[0] == '1') {
                if (ownerList.has(actInfo.get("email")) != -1)
                    throw new Exception("Owner existed");
                results[1] = ownerList.create(actInfo);
            }
            else
                results[1] = true;

            return results[0]&&results[1];
        }
    }

    /**
     * Get account informatin.
     * @param id account id
     * @param role account role. 1-customer, 2-owner, 3-admin
     * @return account hashmap
     */
    public HashMap<String, String> getAccount(int id, int role) {
        HashMap<String, String> accountInfo = null;
        if (role == 1) {
            return customerList.getById(id).toHashMap();
        } else if (role == 2) {
            return ownerList.getById(id).toHashMap();
        } else if (role == 3) {
            return adminList.getById(id).toHashMap();
        }

        return null;
    }

    /**
     * Delete an account. Only admin has privilege.
     * @param loginId admin id
     * @param roleCode admin role
     * @param actRole the role of account to be deleted
     * @param actId the id of account to be deleted
     * @return delete result
     */
    public boolean delAccount(int loginId, int roleCode, int actRole, int actId) {
        // Not admin
        if (roleCode != 3)
            return false;

        // Authenticate failed
        if (adminList.getById(loginId) == null)
            return false;

        if (actRole == 1) {
            return customerList.del(actId);
        } else if (actRole == 2) {
            return ownerList.del(actId);
        }

        return true;
    }

    /**
     * SHA256 hash. Currently not in use due to anormal function.
     * @param str String to be hashed.
     * @return SHA256 hash result
     */
    private String getSHA256String(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Validate an string is numeric
     * @param str The string to be validated.
     * @return validation result.
     */
    private boolean isNumeric(String str) {
        if (str.length() == 0)
            return false;

        char[] chs = str.toCharArray();
        for (char c : chs)
            if (c < '0' || c > '9')
                return false;

        return true;
    }

    /**
     * Get the account list of a role.
     * @param actType Account role code.
     * @return The list of hashmaps of account.
     */
    public ArrayList<HashMap<String, String>> getAccountList(int actType) {
        if (actType == 1) {
            return customerList.getList();
        } else if (actType == 2) {
            return ownerList.getList();
        } else if (actType == 3) {

        }

        return null;
    }

    /**
     * Call list to write data to file system.
     * Only call the list related to account service.
     */
    public void saveData() {
        customerList.save();
        ownerList.save();
        adminList.save();
    }
}
