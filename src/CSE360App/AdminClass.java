package CSE360App;
import java.util.List;
import java.util.LinkedList;


public class AdminClass extends UserClass {
    private List<String> roles = new LinkedList<>();
    private List<UserClass> users = new LinkedList<>();

    public AdminClass(String username, char[] password, boolean isOTP, String firstName, String middleName, String lastName, List<String> roles) {
        super(username, password, isOTP, firstName, middleName, lastName, roles);
    }

    public void inviteUser(String email, String role) {
    }

    public void resetUserAccount(UserClass user) {
    }

    public boolean deleteUserAccount(UserClass user) {
        return false;
    }

    public List<UserClass> listUserAccounts() {
    	System.out.println("Listng all user accounts: ");
    	for (UserClass user : users) {
    		String fullname = user.getFirstName() + " " +user.getMiddleName() + " " +  user.getLastName();
    		System.out.println("Username: " + user.getUsername() + ", Full Name:  " + fullname + ", Role: " + user.getRoles());                      
    	}
    	
        return new LinkedList<>();
    }

    public void addRole(UserClass user, String roles) {
    	if (!user.getRoles().contains(roles)) {
            user.getRoles().add(roles); 
            System.out.println("Role " + roles + " added to user " + user.getUsername());
        } else {
            System.out.println("User " + user.getUsername() + " already has the role " + roles);
        }
    }

    public void removeRole(UserClass user, String role) {
    }

    public List<UserClass> ListRoles() {
        return users;
    }
}
