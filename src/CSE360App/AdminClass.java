package CSE360App;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*******
 * <p> Admin Class </p>
 * 
 * <p> Description: The Admin Class contains the many functions that an Admin is allowed to do and can do. </p>
 * 
 * 
 */ 

public class AdminClass extends UserClass {
	private static List<UserClass> users = new LinkedList<>();  // List of registered users
	private static Map<String, String[]> inviteCodes = new HashMap<>();  // Map of invite codes
    
	
	public AdminClass(String username, char[] password, boolean isOTP, String firstName, String middleName, String lastName, String preferredFirstName, String email, List<String> roles) {         //Construtor
	    super(username, password, isOTP, firstName, middleName, lastName, preferredFirstName, email, roles);	//Initializing the details of the user
	}


    public static void inviteUser(String email, String role) {	    // Method created in order to invite the user by giving a code and their email.
        // Generate an invite code
        String inviteCode = generateInviteCode();		
        
        // Store the invite code
        inviteCodes.put(inviteCode, new String[]{email, role});
        System.out.println("Invite code for " + email + ": " + inviteCode);		//prints out the invite code
    }
    

    public static void completeSignUp(String inviteCode, char[] password, char[] confirmPassword, String firstName, String middleName, String lastName, String preferredFirstName) {   // Method created in order to signup/log into the page.
        if (!Arrays.equals(password, confirmPassword)) {		// If statement created to check if the password exists.
            System.out.println("Passwords do not match. Please try again.");		// If the password does not exists, (doesn't match) then print out an error message asking them to try again.
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
        String confirmation = scanner.nextLine();		//to get input from the console

        if (confirmation.equalsIgnoreCase("Yes")) {		//if the case is yes, then prints the user has been deleted
            users.remove(user);
            System.out.println("User " + user.getUsername() + " has been deleted.");
            return true;
        } else {
            System.out.println("User deletion canceled.");		//otherwise the user deletion is canceled, and it doens't do anything.
            return false;
        }
    }

    public void addRole(UserClass user, String roles) {
    	if (!user.getRoles().contains(roles)) {				//if the user doesn't have the role, then it'll add the role
            user.getRoles().add(roles); 
            System.out.println("Role " + roles + " added to user " + user.getUsername());		//prints out the confirmation that this happened
        } else {
            System.out.println("User " + user.getUsername() + " already has the role " + roles);		//otherwise prints that it already has the role.
        }
    }

    public void removeRole(UserClass user, String role) {
    	if (user.getRoles().contains(role)) {		//Checks if the user actually has the role
            user.getRoles().remove(role);  // Remove role from the user
            System.out.println("Role " + role + " removed from user " + user.getUsername());
        } else {
            System.out.println("User " + user.getUsername() + " does not have the role " + role);
        }
    }
    public List<UserClass> listUsers() {  //to list all the user accounts
        System.out.println("Listing all user accounts: ");
        for (UserClass user : users) {		//goes through all the users in the user Class and prints their first, middle, last name as well as username
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
        return Integer.toHexString((int) (Math.random() * Integer.MAX_VALUE));		//generates the invite code with a random value
    }
    
    public static boolean validateInviteCode(String inviteCode) {
        return inviteCodes.containsKey(inviteCode);				//checks if the invite code is the correct one that matches
    }
    
    public static UserClass findUserByUsername(String username) {
        for (UserClass user : users) {		//goes through all the users in the user class and checks if the username matches what you're looking for
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // Return null if no user is found
    }
    
    public static UserClass findUserByEmail(String email) {
        for (UserClass user : users) {				//goes through all the users in the user class and checks if their email matches the one you're looking for.
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null; // Return null if no user is found
    }



    
    
}
