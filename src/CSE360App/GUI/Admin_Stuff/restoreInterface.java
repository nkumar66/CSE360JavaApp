package CSE360App.GUI.Admin_Stuff;

import java.io.File;
import java.util.Optional;

import CSE360App.Article;
import CSE360App.GUI.ArticleInterface;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

/**
 * <p>
 * restoreInterface Class
 * </p>
 * 
 * <p>
 * Description: The GUI Component of restoring a file.
 * 
 */

public class restoreInterface {

	// Customizable window options
	private int width = 1000;
	private int height = 800;

	// Button declarations
	private Button browseButton = new Button("Browse");
	private Button restoreButton = new Button("Restore");
	private Button returnSettings = new Button("Return to Settings");
	private Button returnArticles = new Button("Return to Articles");

	private ButtonType overwriteButton = new ButtonType("Overwrite");
	private ButtonType mergeButton = new ButtonType("Merge");

	Alert alert = new Alert(AlertType.CONFIRMATION);

	/***
	 * restoreInterface: Serves as main GUI component for the restoration of a
	 * specific file As-is: Just serves as GUI shell NEEDS: Actual restoration logic
	 * 
	 * @param primaryStage
	 * @param role
	 */
	public restoreInterface(Stage primaryStage, String role) {

		/**
		 * The section of code below creates a text field that is meant to be filled
		 * with an absolute path to a file that will holds the backup file.
		 */
		TextField filePath = new TextField();
		filePath.setPromptText("Click 'Browse' to select the backup file.");
		filePath.setPrefWidth(.3 * width);

		browseButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select a File");

			File selectedDirectory = fileChooser.showOpenDialog(primaryStage);

			if (selectedDirectory != null) {
				filePath.setText(selectedDirectory.getAbsolutePath());
			}
		});

		/***
		 * This button just returns the user to the article page.
		 */
		returnArticles.setOnAction(e -> {
			ArticleInterface articleInterface = new ArticleInterface(primaryStage, role);
		});

		/***
		 * This is the restore button, NEEDS DB LOGIC
		 */
		restoreButton.setOnAction(e -> {
			// ADD RESTORE LOGIC HERE!
			confirmationDialog();
			System.out.println("you restored!");
		});
		HBox fileSelection = new HBox(10, filePath, browseButton, restoreButton);
		fileSelection.setAlignment(Pos.CENTER);
		VBox layout = new VBox(10, fileSelection, returnArticles);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, width / 2, height / 2);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Direcotry Search");
		primaryStage.show();
	}

	/***
	 * confirmationDialog: Prompts dialog box when user clicks "restore" As-is: Just
	 * GUI component, presents two buttons and waits until one is pressed. NEEDS:
	 * Actual logic for restoration.
	 */
	public void confirmationDialog() {
		alert.setTitle("Restore Options");
		alert.setHeaderText("Choose Restore Option");

		alert.getButtonTypes().setAll(overwriteButton, mergeButton);

		Optional<ButtonType> result = alert.showAndWait();

		// Reacts based on which button is clicked.
		if (result.isPresent()) {
			if (result.get() == overwriteButton) {
				System.out.println("Overwriting");
			} else if (result.get() == mergeButton) {
				System.out.println("Merging!");
			}
		}

	}

}
