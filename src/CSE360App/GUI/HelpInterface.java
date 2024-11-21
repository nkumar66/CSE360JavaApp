package src.CSE360App.GUI;

import java.io.File;

import src.CSE360App.Article;
import src.CSE360App.GUI.ArticleInterface;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

/**
 * <p>
 * HelpInterface Class
 * </p>
 * 
 * <p>
 * Description: The GUI Component of sending questions.
 * 
 */

public class HelpInterface {

	// Customizable window options
	private int width = 1000;
	private int height = 800;

	// Button declarations
	private Button question = new Button("Send Question");
	private Button returnButton = new Button("Return");

	private TextField Question = new TextField();
	private TextField need = new TextField();
	private TextField find = new TextField();
	/***
	 * backupInterface: Serves as main GUI component for the back up of selected
	 * articles As-is: Just serves as GUI shell NEEDS: Actual logic for backing up
	 * to database.
	 * 
	 * @param primaryStage
	 * @param role
	 * @param selectedItems
	 */
	public HelpInterface(Stage primaryStage, String role, String type) {

		/**
		 * The section of code below creates a text field that is meant to be filled
		 * with an absolute path to a directory that will host where the back is stored.
		 */
	    // Create a TextField for entering a question
	    Question.setPromptText("Enter question here");
	    Question.setPrefWidth(.3 * width);

	    // Create an HBox to hold file selection or question entry
	    HBox fileSelection = new HBox(10, Question);
	    HBox buttons = new HBox(10, question);
	    HBox specQuestions = new HBox(10);

	    // Handle the "Submit Question" button for the general case
	    question.setOnAction(e -> {
	        // Simulate adding the question logic here (e.g., save to database)
	        boolean isSavedSuccessfully = true; // Replace with your database save logic result

	        if (isSavedSuccessfully) {
	            showSuccessDialog("Message sent successfully!");
	            ArticleInterface articleInterface = new ArticleInterface(primaryStage, role);
	        } else {
	            showErrorDialog("Failed to send the message. Please try again.");
	        }
	    });

	    // Handle the specific question case if type equals "spec"
	    if (type.equalsIgnoreCase("spec")) {
	        // Add specific input fields
	        need.setPromptText("What do you need?");
	        need.setPrefWidth(.3 * width);
	        find.setPromptText("What can't you find?");
	        find.setPrefWidth(.3 * width);

	        specQuestions.getChildren().addAll( need, find);

	        question.setOnAction(e -> {
	            // Simulate adding the question logic here
	            boolean isSavedSuccessfully = true; // Replace with actual logic

	            if (isSavedSuccessfully) {
	                showSuccessDialog("Message sent successfully!");
	                ArticleInterface articleInterface = new ArticleInterface(primaryStage, role);
	            } else {
	                showErrorDialog("Failed to send the message. Please try again.");
	            }
	        }); 
	    }
	    
        returnButton.setOnAction(e-> {
        	ArticleInterface articleInterface = new ArticleInterface(primaryStage, role);
        });

	    // Layout and Scene setup
	    buttons.setAlignment(Pos.CENTER);
	    fileSelection.setAlignment(Pos.CENTER);
	    VBox layout = new VBox(10, fileSelection, specQuestions, question, returnButton);
	    layout.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(layout, width / 2, height / 2);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Question?");
	    primaryStage.show();
	}

	// Helper method to display a success dialog
	private void showSuccessDialog(String message) {
	    Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
	    successDialog.setTitle("Success");
	    successDialog.setHeaderText(null);
	    successDialog.setContentText(message);
	    successDialog.showAndWait();
	}

	// Helper method to display an error dialog
	private void showErrorDialog(String message) {
	    Alert errorDialog = new Alert(Alert.AlertType.ERROR);
	    errorDialog.setTitle("Error");
	    errorDialog.setHeaderText(null);
	    errorDialog.setContentText(message);
	    errorDialog.showAndWait();
	}
}