import java.util.Iterator;
import java.util.Map;

public class Customer extends Account {

    public Customer() {

    }

    /**
     * Convert customer information to a string
     * format: accountId,email,pwdHash,name,street,surburb,phone,quiz1Code-quiz1Ans;quiz2Code-quiz2Ans;
     * @return information string.
     */
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
        return buffer.toString();
    }
}
