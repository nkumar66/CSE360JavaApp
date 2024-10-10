package CSE360App;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class AdminClass extends UserClass {
	private static List<UserClass> users = new LinkedList<>();  // List of registered users
	private static Map<String, String[]> inviteCodes = new HashMap<>();  // Map of invite codes
    

	public AdminClass(String username, char[] password, boolean isOTP, String firstName, String middleName, String lastName, String preferredFirstName, String email, List<String> roles) {
	    super(username, password, isOTP, firstName, middleName, lastName, preferredFirstName, email, roles);
	}


    public static void inviteUser(String email, String role) {
        // Generate an invite code
        String inviteCode = generateInviteCode();
        
        // Store the invite code
        inviteCodes.put(inviteCode, new String[]{email, role});
        System.out.println("Invite code for " + email + ": " + inviteCode);
    }
    

    public static void completeSignUp(String inviteCode, char[] password, char[] confirmPassword, String firstName, String middleName, String lastName, String preferredFirstName) {
        if (!Arrays.equals(password, confirmPassword)) {
            System.out.println("Passwords do not match. Please try again.");
            return;
        }

        // Check if there are no users yet (meaning this is the first user)
        boolean isFirstUser = users.isEmpty();
        
        String role = "Student"; // Default role is Student

        if (isFirstUser) {
            // If first user, make them Admin
            role = "Admin";
            System.out.println("First user detected, assigning Admin role.");
        } else if (inviteCodes.containsKey(inviteCode)) {
            // Use the role from the invite code if provided
            String[] inviteDetails = inviteCodes.get(inviteCode);
            role = inviteDetails[1];
        } else {
            System.out.println("Invalid invite code.");
            return;
        }

        // Use the provided preferred name if entered, otherwise default to first name
        if (!preferredFirstName.isEmpty()) {
            firstName = preferredFirstName;
        }

        // Create a new user with the provided details
        UserClass newUser = new UserClass(firstName, password, false, firstName, middleName, lastName, preferredFirstName, "email@example.com", List.of(role)); // Email is a placeholder
        users.add(newUser);

        inviteCodes.remove(inviteCode); // Clean up used invite code if relevant
        System.out.println("User " + newUser.getUsername() + " has successfully signed up with the role: " + role);
    }



    public static void resetUserAccount(UserClass user) {
        // Generate one-time password and set expiration
        String oneTimePassword = generateOneTimePassword();
        long expirationTime = System.currentTimeMillis() + 86400000; // 24 hours from now

        user.setOneTimePassword(oneTimePassword, expirationTime); // Set OTP and expiration
        user.setOTP(true);  // Mark the account as OTP required
        System.out.println("One-time password for " + user.getUsername() + ": " + oneTimePassword);
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
    public List<UserClass> listUsers() {  //to list all the user accounts
        System.out.println("Listing all user accounts: ");
        for (UserClass user : users) {
            String fullname = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
            System.out.println("Username: " + user.getUsername() + ", Full Name: " + fullname + ", Role: " + user.getRoles());
        }
        return new LinkedList<>(users);  // Return a copy of the user list
    }
    
    public List<String> getRoles(UserClass user) {
        return user.getRoles();  // Return the user's roles
    }
    
    private static String generateOneTimePassword() {
        return Integer.toHexString((int) (Math.random() * Integer.MAX_VALUE)); // Multiply a random float from 0 to 1 by the int max value and hex it to look cool 
    }
    
    private static String generateInviteCode() {
        return Integer.toHexString((int) (Math.random() * Integer.MAX_VALUE));
    }
    
    public static boolean validateInviteCode(String inviteCode) {
        return inviteCodes.containsKey(inviteCode);
    }
    
    public static UserClass findUserByUsername(String username) {
        for (UserClass user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // Return null if no user is found
    }
    
    public static UserClass findUserByEmail(String email) {
        for (UserClass user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null; // Return null if no user is found
    }



    
    
}
