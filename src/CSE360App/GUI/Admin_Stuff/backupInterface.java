package CSE360App.GUI.Admin_Stuff;

import java.io.File;

import CSE360App.Article;
import CSE360App.GUI.ArticleInterface;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

/**
 * <p>
 * backupInterface Class
 * </p>
 * 
 * <p>
 * Description: The GUI Component of backing up selected articles.
 * 
 */

public class backupInterface {

	// Customizable window options
	private int width = 1000;
	private int height = 800;

	// Button declarations
	private Button browseButton = new Button("Browse");
	private Button backupButton = new Button("Backup");
	private Button returnSettings = new Button("Return to Settings");
	private Button returnArticles = new Button("Return to Articles");

	/***
	 * backupInterface: Serves as main GUI component for the back up of selected
	 * articles As-is: Just serves as GUI shell NEEDS: Actual logic for backing up
	 * to database.
	 * 
	 * @param primaryStage
	 * @param role
	 * @param selectedItems
	 */
	public backupInterface(Stage primaryStage, String role, ObservableList<Article> selectedItems) {

		/**
		 * The section of code below creates a text field that is meant to be filled
		 * with an absolute path to a directory that will host where the back is stored.
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
		 * This is the backup button, NEEDS DB LOGIC
		 */
		backupButton.setOnAction(e -> {
			// ADD BACKUP LOGIC HERE!

			System.out.println("you restored!");
		});
		HBox fileSelection = new HBox(10, filePath, browseButton, backupButton);
		fileSelection.setAlignment(Pos.CENTER);
		VBox layout = new VBox(10, fileSelection, returnArticles);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, width, height);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Direcotry Search");
		primaryStage.show();
	}

}
