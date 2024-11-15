package src.CSE360App;
import java.util.ArrayList;
import java.util.List;

public class UserClass {
    private String username;
    private char[] password;
    private boolean isOTP;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredFirstName;
    private String email;
    private List<String> roles;

    private String oneTimePassword;
    private long otpExpiration;

    public UserClass(String username, char[] password, boolean isOTP, String firstName, String middleName, String lastName, String preferredFirstName, String email, List<String> roles) {
        this.username = username;
        this.password = password;
        this.isOTP = isOTP;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.preferredFirstName = preferredFirstName;
        this.email = email;
        this.roles = roles;
    }
    


    
    public String getEmail() {			//to return an email
        return email;
    }

    public String getFirstName() {			//to retrieve first name
        return firstName;
    }

    public String getMiddleName() {		//to retrieve middle name
        return middleName;
    }

    public String getLastName() {		//to retrieve last name
        return lastName;
    }
  
    public String getPreferredFirstName() {		//to get the first name that is preferred 
        return preferredFirstName != null && !preferredFirstName.isEmpty() ? preferredFirstName : firstName;
    }

    public String getUsername() {		//getter for username
        return username;
    }

    public List<String> getRoles() {			//getter for users roles
        return roles;
    }


    public void setOneTimePassword(String otp, long expiration) {			//sets the one time password
        this.oneTimePassword = otp;
        this.otpExpiration = expiration;
    }

    public String getOneTimePassword() {		//getter for one time password
        return oneTimePassword;
    }

    public long getOtpExpiration() {		//getter for expiration date for otp
        return otpExpiration;
    }

    public void clearOneTimePassword() {
        this.oneTimePassword = null;			//clears the one time password so no one can access it anymore
        this.otpExpiration = 0;
    }

    // Change password method
    public void changePassword(char[] newPassword) {
        this.password = newPassword;			//updates the password from the old password to new password
    }
    
    public void setOTP(boolean isOTP) {			//setter for the otp
        this.isOTP = isOTP;
    }
    
    public boolean validatePassword(String inputPassword) {
        return new String(this.password).equals(inputPassword); // Compares input password with stored password
    }


	/***
	 * populateArticles: TEMPORARY method that makes fake articles in order to
	 * create GUI As-is: Just makes fake articles with default attributes Needs:
	 * real articles
	 */
	public static void populateUsers(ArrayList<UserClass> users) {
		
		String password = "pass";
		String OTP = "pass";
		String email = "@asu.com";
		
		char[] pass = password.toCharArray();
		List<String> admin = new ArrayList<>();
		admin.add("admin");
		List<String> instructor = new ArrayList<>();
		instructor.add("instructor");		
		List<String> student = new ArrayList<>();
		student.add("student");	
		
		
		users.add(new UserClass("Brian123", pass, false, "Brian", "B.","Venegas", "Brian", "brian" + email, admin));
		users.add(new UserClass("Adrian456", pass, false, "Adrian", "","Sotelo", "Adrian", "adrian" + email, instructor));
		users.add(new UserClass("Saul789", pass, false, "Saul", "J.","Jimenez", "Saul", "saul" + email, student));
		users.add(new UserClass("Joseph987", pass, false, "Joseph", "","Guerrero", "Joey", "joseph" + email, admin));
		users.add(new UserClass("Yaire852", pass, false, "Yaire", "","Estrada", "Yaire", "yaire" + email, admin));
		users.add(new UserClass("Danny951", pass, false, "Daniel", "","Martinez", "Danny", "danny" + email, admin));

	}


}
