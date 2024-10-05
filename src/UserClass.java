// User Class
public class UserClass {
	
	public String username;
	
	private char[] password;
	private boolean isOTP;
	private String firstName;
	private String middleName;
	private String lastName;	


public UserClass(String username, char[] password, boolean isOTP,
        String firstName, String middleName, String lastName){
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
}

