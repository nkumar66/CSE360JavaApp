package CSE360App;
import java.util.ArrayList;
import java.util.List;

public class AdminClass extends UserClass {
    private List<String> roles = new ArrayList<>();

    public AdminClass(String username, char[] password, boolean isOTP, String firstName, String middleName, String lastName) {
        super(username, password, isOTP, firstName, middleName, lastName);
    }

    public void inviteUser(String email, String role) {
    }

    public void resetUserAccount(UserClass user) {
    }

    public boolean deleteUserAccount(UserClass user) {
        return false;
    }

    public List<UserClass> listUserAccounts() {
        return new ArrayList<>();
    }

    public void addRole(UserClass user, String role) {
    }

    public void removeRole(UserClass user, String role) {
    }

    public List<String> getRoles() {
        return roles;
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public void removeRole(String role) {
        roles.remove(role);
    }
}
