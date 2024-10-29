package src.CSE360App;

import src.CSE360App.GUI.SignUpInterface;
// JavaFX imports needed to support the Graphical User Interface
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class PasswordEvaluationGUITestbed extends Application {
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 1000;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 800;
	
	/** A temporary object referencing the application's user interface */
	public SignUpInterface theGUI;
	
	/** The default constructor */
	public PasswordEvaluationGUITestbed() {
	}

	//this method starts after the application gets loaded into the memory
	public void start(Stage theStage) throws Exception {
		
		theStage.setTitle("CSE 360 App");			// Label the stage (a window)
		
		Pane theRoot = new Pane();							// Create a pane within the window
		
		theGUI = new SignUpInterface(theStage, "ADM");				// Create the Graphical User Interface
		
		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene
		
		theStage.setScene(theScene);						// Set the scene on the stage
		
		theStage.show();									// Show the stage to the user
		
		//all the buttons of the GUI are going to be shown here, sets the scene as well
	}
	
	/*********************************************************************************************/

	/**********************************************************************************************
	 * This is the method that launches the JavaFX application
	 * 
	 * @param args	The standard argument list for a Java Mainline
	 * 
	 */
	public static void main(String[] args) {				// This method may not be required
		launch(args);										// for all JavaFX applications using
	}														// other IDEs.
}
