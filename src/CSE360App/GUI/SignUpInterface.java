
package src.CSE360App.GUI;

import src.CSE360App.AdminClass;
import src.CSE360App.PasswordEvaluationGUITestbed;
import src.CSE360App.PasswordEvaluator;
import src.CSE360App.GUI.Admin_Stuff.AdminInterface;
import src.CSE360App.GUI.Student_Stuff.StudentInterface;
// JavaFX imports needed to support the Graphical User Interface
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;

/**
 * <p>
 * SignUp Class
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based user interface testbed to develop and test UI
 * ideas for the user sign up page.
 * </p>
 * <p>
 * This GUI is activated when a user inputs a first-time Invite Code, allows
 * them to set up their account
 * 
 * 
 * 
 * 
 */

public class SignUpInterface {

	/**********************************************************************************************
	 * 
	 * Attributes
	 * 
	 **********************************************************************************************/

	// These are the application values required by the Graphical User Interface
	// The names of the variables specify their function and each is initialize as
	// required
	private Label label_ApplicationTitle = new Label("CSE 360 App");
	private Label label_Password = new Label("Enter the password here");
	private Label label_PasswordConfirmation = new Label("Confirm your password here");
	private Label label_Username = new Label("Enter username here");
	private Label label_Email = new Label("Enter email address here");

	// Profficiency Level Labels
	private Label label_profficiencyChecker = new Label(
			"Please select your Proficiency Level for each of the following: ");
	private ToggleGroup Astah = new ToggleGroup();
	private ToggleGroup Eclipse = new ToggleGroup();
	private ToggleGroup Git = new ToggleGroup();
	private ToggleGroup Java = new ToggleGroup();
	private ToggleGroup JavaFX = new ToggleGroup();
	private ToggleGroup Scrum = new ToggleGroup();

	private RadioButton beginner = new RadioButton("Beginner");
	private RadioButton intermediate = new RadioButton("Intermediate");
	private RadioButton advanced = new RadioButton("Advanced");
	private RadioButton expert = new RadioButton("Expert");

	// Textfield's for user input
	private TextField text_UserName = new TextField();
	private TextField text_Password = new PasswordField();
	private TextField text_PasswordConfirmation = new PasswordField();
	private TextField text_Email = new TextField();
	private TextFlow errPassword;

	// Prof. Carter's password validation stuff
	private Label label_errPassword = new Label("");
	private Label noInputFound = new Label("");
	private Text errPasswordPart1 = new Text(); // The error message is composed of three parts
	private Text errPasswordPart2 = new Text();
	private Label errPasswordPart3 = new Label("");
	private Label validPassword = new Label("");
	private Label label_Requirements = new Label("A valid password must satisfy the following requirements:");
	private Label label_UpperCase = new Label("At least one upper case letter");
	private Label label_LowerCase = new Label("At least one lower case letter");
	private Label label_NumericDigit = new Label("At least one numeric digit");
	private Label label_SpecialChar = new Label("At least one special character");
	private Label label_LongEnough = new Label("At least eight characters");

	// Login Button: Actual button itself and an error message if password is not
	// structured correctly
	private Button SignUpButton = new Button("Sign Up");
	private Label errorMessage = new Label("");
	private String role;

	/**********************************************************************************************
	 * 
	 * Constructors
	 * 
	 **********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the graphical user interface.
	 * These assignments determine the location, size, font, color, and change and
	 * event handlers for each GUI object.
	 */
	public SignUpInterface(Stage primaryStage, String SignupCode) {

//		String role = "";

		if (SignupCode.endsWith("ADMSTU")) {
			role = "admin-student";
		} else if (SignupCode.endsWith("ADM")) {
			role = "admin";
		} else if (SignupCode.endsWith("INS")) {
			role = "instructor";
		} else {
			role = "student";
		}
		// Used Vbox for easy centering
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);

		// Sets the styling for every label.
		label_ApplicationTitle.setFont(Font.font("Arial", 24));
		label_Username.setFont(Font.font("Arial", 14));
		label_Password.setFont(Font.font("Arial", 14));
		label_PasswordConfirmation.setFont(Font.font("Arial", 14));
		label_Email.setFont(Font.font("Arial", 14));
		label_profficiencyChecker.setFont(Font.font("Arial", 14));

		// Setup any text prompts and listeners here
		// Password Listener: Used for password requirements
		text_UserName.setPromptText("Username");
		text_Password.setPromptText("Password");
		text_PasswordConfirmation.setPromptText("Confirm Password");
		text_Password.textProperty().addListener((observable, oldValue, newValue) -> {
		    // Reset the flags each time the content changes
		    resetAssessments();
		    errPasswordPart1.setText("");  // Clear previous text before updating
		    errPasswordPart2.setText("");  // Clear the arrow indicator
		    errPasswordPart3.setText("");  // Clear the descriptive message
		    validPassword.setText("");
		    
		    // Check if the password is empty after erasing characters
		    if (newValue.isEmpty()) {
		        resetAssessments();
		    } else {
		        // If the password field is not empty, perform validation
		        performEvaluation();
		    }
			text_PasswordConfirmation.setPromptText("Confirm Password");
		});
		text_Email.setPromptText("Email Addresss");

		// Establish an error message for the case where there is no input
		noInputFound.setTextFill(Color.RED);
		noInputFound.setAlignment(Pos.BASELINE_LEFT);
		setupLabelUI(noInputFound, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10,
				110);

		// Establish an error message for the password, left aligned
		label_errPassword.setTextFill(Color.RED);
		label_errPassword.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errPassword, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 150 - 10,
				Pos.BASELINE_LEFT, 22, 126);

		// Error Message components for the Password
		errPasswordPart1.setFill(Color.BLACK);
		errPasswordPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		errPasswordPart2.setFill(Color.RED);
		errPasswordPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		errPassword = new TextFlow(errPasswordPart1, errPasswordPart2);
		errPassword.setMinWidth(PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10);
		errPassword.setLayoutX(22);
		errPassword.setLayoutY(100);

		setupLabelUI(errPasswordPart3, "Arial", 14, 400, Pos.BASELINE_LEFT, 20, 125);

		// Position the requirements assessment display for each required aspect

		// The requirements title
		setupLabelUI(label_Requirements, "Arial", 16, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				10, 400);

		// Upper case character found or not found
		setupLabelUI(label_UpperCase, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 460);
		label_UpperCase.setTextFill(Color.RED);
		label_LowerCase.setText("At least one lower case letter");

		// Lower case character found or not found
		setupLabelUI(label_LowerCase, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 520);
		label_LowerCase.setTextFill(Color.RED);

		// Numeric character found or not found
		setupLabelUI(label_NumericDigit, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 580);
		label_NumericDigit.setTextFill(Color.RED);

		// Special character found or not found
		setupLabelUI(label_SpecialChar, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 640);
		label_SpecialChar.setTextFill(Color.RED);

		// Log enough satisfied or not
		setupLabelUI(label_LongEnough, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 700);
		label_LongEnough.setTextFill(Color.RED);
		resetAssessments();

		// Setup the valid Password message
		validPassword.setTextFill(Color.GREEN);
		validPassword.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(validPassword, "Arial", 18, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 150 - 10,
				Pos.BASELINE_LEFT, 10, 380);

		// login button that sets new scene if credentials are valid

		SignUpButton.setLayoutX(900);
		SignUpButton.setLayoutY(250);
		SignUpButton.setOnAction(e -> {
			// Gather user input
			String username = text_UserName.getText();
			String password = text_Password.getText();
			String confirmPassword = text_PasswordConfirmation.getText();
			String email = text_Email.getText();
			String preferredFirstName = ""; // You can add this to the form later if needed

			// Basic validation
			if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
				errorMessage.setText("All fields must be filled.");
				return;
			}

			if (!password.equals(confirmPassword)) {
				errorMessage.setText("Passwords do not match.");
				return;
			}

			// Now call completeSignUp from AdminClass and pass in the form data
			// Invite code is empty as this example assumes direct user sign-up
			AdminClass.completeSignUp("", // No invite code for now
					password.toCharArray(), confirmPassword.toCharArray(), username, "", // Middle name (you can add
																							// this to the form if
																							// needed)
					"", // Last name (you can add this to the form if needed)
					preferredFirstName // You can add a field for this in the form
			);

			// Success logic: after completing sign-up, navigate to next screen (login or
			// app)
			login(primaryStage, role);
		});

		// Error message that displays if user attempts to login w/ invalid password
		errorMessage.setTextFill(Color.RED);
		errorMessage.setLayoutX(10);
		errorMessage.setLayoutY(250);

		// Place all of the just-initialized GUI elements into the pane, whether they
		// have text or not
		layout.getChildren().addAll(label_ApplicationTitle, label_Username, text_UserName, label_Email, text_Email,
				label_Password, text_Password, label_PasswordConfirmation, text_PasswordConfirmation, noInputFound,
				label_errPassword, errPassword, errPasswordPart3, validPassword, label_Requirements, label_UpperCase,
				label_LowerCase, label_NumericDigit, label_SpecialChar, label_LongEnough);

		// Setup Profficency Level Check boxes here

		if (role.equals("student") || role.equals("student-admin")) {

			VBox javaFxSection = createProficiencySection("JavaFX", JavaFX);
			VBox astahSection = createProficiencySection("Astah", Astah);
			VBox eclipseSection = createProficiencySection("Eclipse", Eclipse);
			VBox gitSection = createProficiencySection("Git", Git);
			VBox javaSection = createProficiencySection("Java", Java);
			VBox scrumSection = createProficiencySection("Scrum", Scrum);

			layout.getChildren().addAll(label_profficiencyChecker, astahSection, eclipseSection, gitSection,
					javaSection, javaFxSection, scrumSection);
		}

		layout.getChildren().add(SignUpButton);

		// Wrap the VBox in a ScrollPane
		ScrollPane scrollPane = new ScrollPane(layout);
		scrollPane.setFitToWidth(true);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		Scene signUpScene = new Scene(scrollPane, 1000, 800);
		primaryStage.setScene(signUpScene);
		primaryStage.setTitle("Sign Up");
	}

	/**********
	 * Login: Private local method to handle login, and switching to new stage /
	 * scene. As is: As long as the criteria for the sign up button are met, sends
	 * it to logged in page by creating new VBox. NEEDS: To create an actual "logged
	 * in" or "main app" gui for most of the functionality, then send it there.
	 */
	private void login(Stage primaryStage, String role) {
		if (role.equals("admin-student") || role.equals("admin")) {
			new AdminInterface(primaryStage, role);
		} else if (role.equals("instructor")) {
			new InstructorInterface(primaryStage);
		} else {
			new StudentInterface(primaryStage);
		}
	}

	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e) {
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setEditable(e);
	}

	/**********************************************************************************************
	 * 
	 * User Interface Actions
	 * 
	 **********************************************************************************************/

	/**********
	 * Reset all the relevant flags and error messages whenever the user changes the
	 * input
	 */
	private void setPassword() {
		label_errPassword.setText("");
		noInputFound.setText("");
		errPasswordPart1.setText("");
		errPasswordPart2.setText("");
		validPassword.setText("");
		resetAssessments(); // Reset the flags for all of the assessment criteria
		performEvaluation(); // Perform the evaluation to set all the assessment flags
	}

	private VBox createProficiencySection(String labelText, ToggleGroup group) {
		Label label = new Label(labelText);
		label.setFont(Font.font("Arial", 14));

		RadioButton beginner = new RadioButton("Beginner");
		RadioButton intermediate = new RadioButton("Intermediate");
		RadioButton advanced = new RadioButton("Advanced");
		RadioButton expert = new RadioButton("Expert");

		beginner.setToggleGroup(group);
		intermediate.setToggleGroup(group);
		advanced.setToggleGroup(group);
		expert.setToggleGroup(group);

		// Arrange label and radio buttons in a VBox
		HBox buttonLayout = new HBox(10);
		buttonLayout.setAlignment(Pos.CENTER_LEFT);
		buttonLayout.getChildren().addAll(beginner, intermediate, advanced, expert);

		VBox proficiencyLayout = new VBox(5);
		proficiencyLayout.setAlignment(Pos.CENTER_LEFT);
		proficiencyLayout.getChildren().addAll(label, buttonLayout);

		return proficiencyLayout;
	}

	/****
	 * evaluateUsername: Validates that the username entered is between 8 and 16
	 * characters. As is: Just checks that the length of the username is between 8
	 * and 16 characters. NEEDS: Might need more validation, like no numbers,
	 * special characters, etc.
	 * 
	 * @return true if it is valid, false otherwise
	 */
	private boolean evaluateUsername() {
		String username = text_UserName.getText();

		noInputFound.setText("");

		if (username.isEmpty()) {
			noInputFound.setText("No username input found!");
			return false;
		} else if (username.length() < 8 || username.length() > 16) {
			noInputFound.setText("username must be between 8 and 16 characters");
			return false;
		}

		return true;
	}

	/***
	 * evaluateEmail:Evaluate if the inputted username is valid or not As is: Checks
	 * for 4 characters or more, if it ends in .com, and has 1 '@' symbol NEEDS: To
	 * be able handle more email types, or find another way of validating its an
	 * email.
	 */
	private boolean evaluateEmail() {
		String email = text_Email.getText();

		noInputFound.setText("");

		if (email.isEmpty()) {
			noInputFound.setText("No email input found!");
			return false;
		} else if (email.length() <= 4) {
			noInputFound.setText("Email is not long enough!");
		}

		if (!email.endsWith(".com") || !email.endsWith(".edu") || !email.endsWith(".org")) {
			noInputFound.setText(".com missing from end of email!");
			return false;
		}

		int atCount = 0;

		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '@') {
				atCount++;
			}
		}

		if (atCount != 1) {
			noInputFound.setText("Email must have 1 '@' symbol!");
			return false;
		}

		return true;
	}

	/**********
	 * Evaluate the input whenever the user changes it and update the GUI and the
	 * console so the user knows what is going on
	 * 
	 * @return valid or invalid
	 */
	private boolean performEvaluation() {
		// Get the user input string from the GUI
		String inputText = text_Password.getText();
		char[] passwordChars = inputText.toCharArray();

		// If the input is empty, set that flag and stop
		if (inputText.isEmpty()) {
			noInputFound.setText("No input text found!");
			return false; // Return false if there's no input
		} else {
			// There is input to process. Call the evaluatePassword method to assess each of
			// the
			// remaining criteria
			String errMessage = PasswordEvaluator.evaluatePassword(passwordChars);
			updateFlags(); // Check for each criterion and set the GUI for that element to green

			if (!errMessage.isEmpty()) {
				// If the returned string from evaluatePassword is not empty, there are errors
				System.out.println(errMessage); // Display the error message on the console

				// Insert the error message into the GUI field so it is displayed there
				label_errPassword.setText(PasswordEvaluator.passwordErrorMessage);

				// Fetch the location of the error from the PasswordEvaluator. If the returned
				// value is negative, we do not display an up arrow beneath the character that
				// caused the error
				if (PasswordEvaluator.passwordIndexofError <= -1)
					return false; // Return false if an error occurred

				// Create a copy of the string up to the point of the error and add a special
				// up arrow character at the point of the error
				String input = PasswordEvaluator.passwordInput; // The input
				errPasswordPart1.setText(input.substring(0, PasswordEvaluator.passwordIndexofError)); // Copy it up to
																										// the error
				errPasswordPart2.setText("\u21EB"); // Append the up arrow
				validPassword.setTextFill(Color.RED); // Set the characters to red

				// Append descriptive lines after the indication of the position of the error
				errPasswordPart3.setText("The red arrow points at the character causing the error!");
				validPassword.setText("Failure! The password is not valid.");
				return false; // Return false if password is invalid
			}

			// If no error message was found, check to see if all the criteria has been met
			else if (PasswordEvaluator.foundUpperCase && PasswordEvaluator.foundLowerCase
					&& PasswordEvaluator.foundNumericDigit && PasswordEvaluator.foundSpecialChar
					&& PasswordEvaluator.foundLongEnough) {
				// All the criteria have been met. Display the success message to the console
				System.out.println("Success! The password satisfies the requirements.");

				// Display the success message and make it green on the GUI
				validPassword.setTextFill(Color.GREEN);
				validPassword.setText("Success! The password satisfies the requirements.");
				return true; // Return true if the password is valid
			} else {
				// At least one criterion has not been satisfied. Display an appropriate message
				// in red on the console
				validPassword.setTextFill(Color.RED);
				validPassword.setText("The password as currently entered is not yet valid.");
				return false; // Return false if password is invalid
			}
		}
	}

	/**********
	 * Reset each of the criteria to red and not yet satisfied after the user makes
	 * any change to the input. The evaluation code updates the text and turns it
	 * green when a criterion is satisfied.
	 */
	protected void resetAssessments() {
		// Upper case character
		label_UpperCase.setText("At least one upper case letter - Not yet satisfied");
		setupLabelUI(label_UpperCase, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 230);
		label_UpperCase.setTextFill(Color.RED);

		// Lower case character
		label_LowerCase.setText("At least one lower case letter - Not yet satisfied");
		setupLabelUI(label_LowerCase, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 260);
		label_LowerCase.setTextFill(Color.RED);

		// Numeric character
		label_NumericDigit.setText("At least one numeric digit - Not yet satisfied");
		setupLabelUI(label_NumericDigit, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 290);
		label_NumericDigit.setTextFill(Color.RED);

		// Special character
		label_SpecialChar.setText("At least one special character - Not yet satisfied");
		setupLabelUI(label_SpecialChar, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 320);
		label_SpecialChar.setTextFill(Color.RED);

		// Not long enough
		label_LongEnough.setText("At least eight characters - Not yet satisfied");
		setupLabelUI(label_LongEnough, "Arial", 14, PasswordEvaluationGUITestbed.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT,
				30, 350);
		label_LongEnough.setTextFill(Color.RED);
		errPasswordPart3.setText("");
	}

	/**********
	 * Check each criterion. If satisfied, update the text and turn it green
	 */
	private void updateFlags() {
		// Upper case character
		if (PasswordEvaluator.foundUpperCase) {
			label_UpperCase.setText("At least one upper case letter - Satisfied");
			label_UpperCase.setTextFill(Color.GREEN);
		}

		// Lower case character
		if (PasswordEvaluator.foundLowerCase) {
			label_LowerCase.setText("At least one lower case letter - Satisfied");
			label_LowerCase.setTextFill(Color.GREEN);
		}

		// Numeric character
		if (PasswordEvaluator.foundNumericDigit) {
			label_NumericDigit.setText("At least one numeric digit - Satisfied");
			label_NumericDigit.setTextFill(Color.GREEN);
		}

		// Special character
		if (PasswordEvaluator.foundSpecialChar) {
			label_SpecialChar.setText("At least one special character - Satisfied");
			label_SpecialChar.setTextFill(Color.GREEN);
		}

		// Long enough
		if (PasswordEvaluator.foundLongEnough) {
			label_LongEnough.setText("At least eight characters - Satisfied");
			label_LongEnough.setTextFill(Color.GREEN);
		}
	}
}
