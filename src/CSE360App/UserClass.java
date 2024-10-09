package CSE360App;
import java.util.List;


public class UserClass {
    private String username;
    private char[] password;
    private boolean isOTP;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<String> roles;
    
    private String oneTimePassword;
    private long otpExpiration;


    public UserClass(String username, char[] password, boolean isOTP, String firstName, String middleName, String lastName, List<String> roles) {
        this.username = username;
        this.password = password;
        this.isOTP = isOTP;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.roles = roles;
    }
    
    // Method to set the one-time password and expiration time
    public void setOneTimePassword(String otp, long expiration) {
        this.oneTimePassword = otp;
        this.otpExpiration = expiration;
    }
    // Method to get the one-time password
    public String getOneTimePassword() {
        return oneTimePassword;
    }

    // Method to get OTP expiration time
    public long getOtpExpiration() {
        return otpExpiration;
    }

    // Method to clear the OTP after it's used (maybe not needed)
    public void clearOneTimePassword() {
        this.oneTimePassword = null;
        this.otpExpiration = 0;
    }
    
    public boolean isOTP() {
        return isOTP;
    }
    
    public void setOTP(boolean isOTP) {
        this.isOTP = isOTP;
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

