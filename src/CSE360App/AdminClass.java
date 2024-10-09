package CSE360App;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;


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
    	System.out.println("Are you sure you want to delete the account for " + user.getUsername() + "? (Yes/No)");
        Scanner scanner = new Scanner(System.in);
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Yes")) {
            users.remove(user);
            System.out.println("User " + user.getUsername() + " has been deleted.");
            return true;
        } else {
            System.out.println("User deletion canceled.");
            return false;
        }
    }

    public List<UserClass> listUserAccounts() {		//to list all the user accounts
    	System.out.println("Listing all user accounts: ");
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
    	if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);  // Remove role from the user
            System.out.println("Role " + role + " removed from user " + user.getUsername());
        } else {
            System.out.println("User " + user.getUsername() + " does not have the role " + role);
        }
    }

    public List<UserClass> listRoles() {
        return users;
    }
    
    public List<String> getRoles(UserClass user) {
        return user.getRoles();  // Return the user's roles
    }
}
