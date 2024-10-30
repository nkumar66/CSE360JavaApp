package src.CSE360App.GUI;

import java.net.URL;

import javafx.geometry.Insets;
//JavaFX imports needed to support the Graphical User Interface
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 * <p>
 * LoginInterface Class
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based login interface sets up the login page for
 * users, where they can either login with their username & password or enter an
 * invite code.
 * </p>
 */
public class LoginInterface {

	/**************
	 * Attributes
	 **************/

	// These are the application values required by the GUI for the login page
	// The names of the variables specify their function and each is initialize as
	// required
	private Label label_ApplicationTitle = new Label("CSE 360 App");
	private Label label_Username = new Label("Enter username here");
	private Label label_Password = new Label("Enter the password here");
	private Label label_InviteCode = new Label("New Users: Enter Invite Code");
	private Label errorMessage = new Label("");

	private TextField text_UserName = new TextField();
	private PasswordField text_Password = new PasswordField();
	private TextField text_InviteCode = new TextField();

	private Button loginButton = new Button("Login");
	private Button logoutButton = new Button("Logout");

	private String adminuser = "admin";
	private String pass = "pass";

	private String studentuser = "student";

	
	private int width = 400;
	private int height = 300;
	/******
	 * This method initializes all of the elements of the login GUI.
	 * 
	 * @param primaryStage
	 */
	public LoginInterface(Stage primaryStage) {

		// Used "Vbox" for easy centering
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(10));

		// Set default fault and size for every label on login page.
		label_ApplicationTitle.setFont(javafx.scene.text.Font.font("Arial", 24));
		label_Username.setFont(javafx.scene.text.Font.font("Arial", 14));
		label_Password.setFont(javafx.scene.text.Font.font("Arial", 14));
		label_InviteCode.setFont(javafx.scene.text.Font.font("Arial", 14));

		errorMessage.setTextFill(Color.RED);

		// Text fields for each element necessary.
		text_UserName.setPromptText("Username");
		text_Password.setPromptText("Password");
		text_InviteCode.setPromptText("Invite Code (optional)");
		


		// Login button action
		/*
		 * This is where the login button logic will go: if the username and password
		 * field are filled, check database and allow user in if they match Else if
		 * invite code is filled, check code and send user to sign up page after that.
		 */
		loginButton.setOnAction(e -> {
			/*
			 * As is: Checks if the invite code field is empty NEEDS: Method that handles
			 * checking if code is valid generated
			 */
			if (!text_InviteCode.getText().isEmpty()) {
				proceedToSignUp(primaryStage);

				/*
				 * As is: if the invite code is empty, checks if username and password are
				 * valid. If the username and password are valid according to method, sends to
				 * main app. NEEDS: More validation techniques
				 */
			} else if (isInputValid()) {

				/*
				 * Method validates username and password As is: just checks if they're not
				 * empty NEEDS: Actual verification with database of username and password.
				 */
				proceedToLoggedIn(primaryStage, text_UserName.getText());
			} else {

				// Otherwise, warns user of invalid username or password.
				errorMessage.setText("Invalid username or password");
			}
		});

		// Adds all of the attributes to the layout.
		layout.getChildren().addAll(label_ApplicationTitle, label_Username, text_UserName, label_Password,
				text_Password, label_InviteCode, text_InviteCode, loginButton, errorMessage);

		// The code below builds the actual window for the app, customize as necessary.
		Scene scene = new Scene(layout, width, height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());



		layout.getStyleClass().add("root");






		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();
	}

	/*****
	 * isInputValid: Validates username and password Checks if credentials are empty
	 * and vaidates them
	 * 
	 * @return true if both username and password are valid, false otherwise.
	 *****/

	private boolean isInputValid() {
		String username = text_UserName.getText();
		String password = text_Password.getText(); // Now password is a String

		if (username.isEmpty()) {
			errorMessage.setText("Username cannot be empty");
			return false;
		} else if (password.isEmpty()) {
			errorMessage.setText("Password cannot be empty");
			return false;
		}

		if ((username.equalsIgnoreCase(adminuser) || username.equalsIgnoreCase(studentuser))
				&& password.equalsIgnoreCase(pass)) {
//			System.out.println("RETURNING TRUE");
			return true;
		} else {
//			System.out.println("RETURNING falsev :(");
			return false;
		}
		// Validate against stored users
//        UserClass foundUser = AdminClass.findUserByUsername(username);
//        if (foundUser != null && foundUser.validatePassword(password)) {
//            return true;
//        } else {
//            errorMessage.setText("Invalid username or password");
//            return false;
//        }
	}

	/****
	 * proceedToLoggedIn: Sends user to main page if they enter valid credentials As
	 * is: Just sends to a blank scene that says they are logged in NEEDS: Once the
	 * main app GUI is built, send them there. (Will look like proceed to SignUp
	 * below).
	 * 
	 * @param primaryStage
	 ***/
	private void proceedToLoggedIn(Stage primaryStage, String role) {
		ArticleInterface articleInterface = new ArticleInterface(primaryStage, role);
//    	logoutButton.setOnAction(e -> {
//    		new LoginInterface(primaryStage);
//    	});
	}

	// If the Invite Code is valid, send them to signUp GUI
	// ** ONCE BUILT, SEND USER TO SIGNUP GUI, WHICH WILL SEND THEM TO APP GUI AFTER
	// THEY COMPLETE THAT**

	/***
	 * ProceedToSignUp: Sends users who enter valid invite code to signup page As
	 * is: Sends as long as invite code is filled NEEDS: Create validateInviteCode,
	 * then if that returns true use this function
	 * 
	 * @param primaryStage
	 */
	private void proceedToSignUp(Stage primaryStage) {
		SignUpInterface signUpInterface = new SignUpInterface(primaryStage, text_InviteCode.getText());

	}

	// VALIDATEOTP NEEDED?
	/**
	 * validateInviteCode: Compares user inputted inviteCode to database and ensures
	 * it is valid As is: non-functional NEEDS: Need Database up to create this
	 * function
	 * 
	 * 
	 * @param inviteCode
	 * @return
	 */
	private boolean validateInviteCode(String inviteCode) {
		return false;
	}
}
