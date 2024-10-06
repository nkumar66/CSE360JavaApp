package CSE360App;

/* The following class evaluates whether or not a user inputted username meets the minimum length requirement.
 * 
 */
public class UsernameEvaluator {
	
	
    public static String usernameErrorMessage = "";
    public static String usernameInput = "";
    public static int usernameIndexofError = -1;
    public static boolean isLengthValid = false;
    private static String inputLine = "";
    private static boolean running;

    
    public static void displayInputState() {
    	System.out.println(inputLine);
    	System.out.println("Username size: " + inputLine.length());
    }
    
    public static String evaluateUsername(String input) {
    	usernameErrorMessage = "";
    	usernameIndexofError = 0;
    	inputLine = input;
    	
    	if(input.length() <= 0) {
    		usernameErrorMessage = "*** Error *** the username field is empty!";
    		return usernameErrorMessage;
    	}
    	
    	usernameInput = input;
    	isLengthValid = false;
    	running = true;
    	
    	if(input.length() >= 8 && input.length() <= 16) {
    		System.out.println("Username length is valid!");
    		isLengthValid = true;
    	} else {
    		usernameIndexofError = input.length();
    		return "*** Error *** Username must be between 8 and 16 characters!";
    	}
    	
    	return isLengthValid ? "" : usernameErrorMessage;
    }


}
