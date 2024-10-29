package src.CSE360App.GUI.Login;

// JavaFX imports needed to support the Graphical User Interface
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*******
 * <p>
 * LoginGUI Class
 * </p>
 * 
 * <p>
 * Description: Serves as the "main" for the JavaFX front end. Everything begins
 * here.
 * </p>
 * 
 * 
 * 
 */

public class LoginGUI extends Application {

	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 1000;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 800;

	/** A temporary object referencing the application's user interface */
	public LoginInterface theGUI;

	/** The default constructor */
	public LoginGUI() {
	}

	/*
	 * Main method, calls login interface when application is started.
	 * 
	 */
	@Override
	public void start(Stage theStage) throws Exception {

		// Starts Login Page when application is started
		theStage.setTitle("Login"); // Label the stage (a window)
		theGUI = new LoginInterface(theStage);
		theStage.show(); // Show the stage to the user

	}

	/*********************************************************************************************/

	/**********************************************************************************************
	 * This is the method that launches the JavaFX application
	 * 
	 * @param args The standard argument list for a Java Mainline
	 * 
	 */
	public static void main(String[] args) { // This method may not be required
		launch(args); // for all JavaFX applications using
	} // other IDEs.
}
