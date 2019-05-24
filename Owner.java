import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Owner extends Account {

    // Status of being verified by admin.
    private boolean isVerified;

    public Owner() {

    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = super.toHashMap();
        map.put("isVerified", Boolean.toString(isVerified));
        return map;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(accountId);
        buffer.append(",");
        buffer.append(email);
        buffer.append(",");
        buffer.append(pwdHash);
        buffer.append(",");
        buffer.append(name);
        buffer.append(",");
        buffer.append(street);
        buffer.append(",");
        buffer.append(surburb);
        buffer.append(",");
        buffer.append(phone);
        buffer.append(",");
        if (secureQuestions == null || secureQuestions.size() == 0) {
            buffer.append(" ");
            buffer.append(",");
        } else {
            Iterator<Map.Entry<Integer,String>> it = secureQuestions.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer,String> entry = it.next();
                buffer.append(entry.getKey());
                buffer.append("-");
                buffer.append(entry.getValue());
                buffer.append(";");
            }
        }
        buffer.append(",");
        buffer.append(isVerified);
        return buffer.toString();
    }
}
