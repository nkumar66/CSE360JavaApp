// User Class
package CSE360App;
import java.util.List;


public class UserClass {
	
	public String username;
	
	private char[] password;
	private boolean isOTP;
	private String firstName;
	private String middleName;
	private String lastName;
	private List<String> roles;


public UserClass(String username, char[] password, boolean isOTP,
        String firstName, String middleName, String lastName, List<String> roles){
	this.username = username;
	this.password = password;
	this.isOTP = true;
	this.firstName = firstName;
	this.middleName = middleName;
	this.lastName = lastName;
	}


	// change password method 
	public void changePassword(char [] newPassword){
		this.password = newPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}
	
	public List<String> getRoles() {
		return roles;
	}
}

