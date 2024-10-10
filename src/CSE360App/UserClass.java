package CSE360App;
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
    

    public String getEmail() {
        return email;
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

    public String getPreferredFirstName() {
        return preferredFirstName != null && !preferredFirstName.isEmpty() ? preferredFirstName : firstName;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }


    public void setOneTimePassword(String otp, long expiration) {
        this.oneTimePassword = otp;
        this.otpExpiration = expiration;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public long getOtpExpiration() {
        return otpExpiration;
    }

    public void clearOneTimePassword() {
        this.oneTimePassword = null;
        this.otpExpiration = 0;
    }

    // Change password method
    public void changePassword(char[] newPassword) {
        this.password = newPassword;
    }
    
    public void setOTP(boolean isOTP) {
        this.isOTP = isOTP;
    }
    
    public boolean validatePassword(String inputPassword) {
        return new String(this.password).equals(inputPassword); // Compares input password with stored password
    }





}
