package manager;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private Map<String, String> accounts;

    public AccountManager() {
        this.accounts = new HashMap<>();
        accounts.put("Patrick", "1234");
        accounts.put("Molly", "FloPup");
    }

    public boolean exists(String userName){
        return accounts.containsKey(userName);
    }

    public boolean checkPassword(String userName, String password){
        return exists(userName) && accounts.get(userName).equals(password);
    }

    public boolean createAccount(String userName, String password){
        if (exists(userName)) return false;
        accounts.put(userName, password);
        return true;
    }
}
