package CSE360App;


public class PasswordEvaluator {

	
	//variables that are used thorughout the various functions
	public static String passwordErrorMessage = "";
	public static String passwordInput = "";
	public static int passwordIndexofError = -1;
	public static boolean foundUpperCase = false;
	public static boolean foundLowerCase = false;
	public static boolean foundNumericDigit = false;
	public static boolean foundSpecialChar = false;
	public static boolean foundLongEnough = false;
	private static String inputLine = "";
	private static char currentChar;
	private static int currentCharNdx;
	private static boolean running;

	private static void displayInputState() {			//displays the pasword size, current char index, and current character
		System.out.println(inputLine);
		System.out.println(inputLine.substring(0,currentCharNdx) + "?");
		System.out.println("The password size: " + inputLine.length() + "  |  The currentCharNdx: " + 
				currentCharNdx + "  |  The currentChar: \"" + currentChar + "\"");
	}

	public static String evaluatePassword(char[] input) {
		passwordErrorMessage = "";					//evaluates the password to make sure it is a proper password
		passwordIndexofError = 0;
		inputLine = new String(input);
		currentCharNdx = 0;
		
		if(input.length <= 0) return "*** Error *** The password is empty!";
		
		currentChar = input[0];		// The current character from the above indexed position

		passwordInput = new String(input);
		foundUpperCase = false;
		foundLowerCase = false;	
		foundNumericDigit = false;			//all the default values, which can then be updated later through the use of the functions.
		foundSpecialChar = false;
		foundNumericDigit = false;
		foundLongEnough = false;
		running = true;

		while (running) {
			displayInputState();
			if (currentChar >= 'A' && currentChar <= 'Z') {
				System.out.println("Upper case letter found");						//runs through all the conditions to check to make sure that it runs correctly
				foundUpperCase = true;
			} else if (currentChar >= 'a' && currentChar <= 'z') {
				System.out.println("Lower case letter found");
				foundLowerCase = true;
			} else if (currentChar >= '0' && currentChar <= '9') {
				System.out.println("Digit found");
				foundNumericDigit = true;
			} else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar) >= 0) {		//checks for the special characters, at least the common ones
				System.out.println("Special character found");
				foundSpecialChar = true;
			} else {
				passwordIndexofError = currentCharNdx;
				return "*** Error *** An invalid character has been found!";				
			}
			if (currentCharNdx >= 7) {
				System.out.println("At least 8 characters found");
				foundLongEnough = true;
			}
			currentCharNdx++;
			if (currentCharNdx >= inputLine.length())
				running = false;
			else
				currentChar = input[currentCharNdx];		//otherwise prints the current index of the character
			
			System.out.println();
		}
		
		String errMessage = "";
		if (!foundUpperCase)
			errMessage += "Upper case; ";
		
		if (!foundLowerCase)
			errMessage += "Lower case; ";
		
		if (!foundNumericDigit)							//these are all conditions to make sure it is a good enough password so it checks all the conditions
			errMessage += "Numeric digits; ";
			
		if (!foundSpecialChar)
			errMessage += "Special character; ";
			
		if (!foundLongEnough)
			errMessage += "Long Enough; ";
		
		if (errMessage == "")
			return "";
		
		passwordIndexofError = currentCharNdx;
		return errMessage + "conditions were not satisfied";			//last condition, if nothing else works then just say the conditions were not satisfied.

	}
}
