import java.io.Serializable;
import java.util.Locale;

public class Settings implements Serializable {

    private String account = "account";

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}