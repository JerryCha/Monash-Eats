package monasheats.java;

import java.util.HashMap;

public interface SearchableAccountList {

    Account getById(int id);

    Account getByEmail(String email);

    boolean del(int index);

    int has(String email);

    int getCount();

    int getIdByEmail(String email);

    boolean create(HashMap<String, String> actInfo);
}
