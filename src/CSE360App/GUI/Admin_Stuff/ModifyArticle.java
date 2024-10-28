package CSE360App.GUI.Admin_Stuff;

import CSE360App.GUI.ArticleInterface;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * <p>
 * ModifyArticle Class
 * </p>
 * 
 * <p>
 * Description: The modify article class allows for the creation / modification
 * of any article.
 * 
 */
public class ModifyArticle {
	private Label label_Title = new Label("Title: ");
	private Label label_Header = new Label("Header: ");
	private Label label_ShortDescription = new Label("Description: ");
	private Label label_Keywords = new Label("Keywords: ");
	private Label label_Links = new Label("Links: ");
	private Label label_Body = new Label("Body: ");

	private TextField text_Title = new TextField();
	private TextField text_Header = new TextField();
	private TextField text_ShortDescription = new TextField();
	private TextField text_Keywords = new TextField();
	private TextField text_Links = new TextField();
	private TextField text_Body = new TextField();

	// Customizable window size
	private int width = 1000;
	private int height = 800;

	// Button declarations
	private Button submit = new Button("Submit");
	private Button returnToArticles = new Button("Return");

	/***
	 * ModifyArticle: This constructor is used to CREATE articles, takes now values
	 * other than default stage and role. As-Is: Skeleton of GUI concept for this
	 * portion, really does nothing at the moment. NEEDS: Method of capturing
	 * keywords, links and header Actual logic of creating header and adding it to
	 * database Formatting
	 * 
	 * @param primaryStage
	 * @param role
	 */
	public ModifyArticle(Stage primaryStage, String role) {

		// Used GridPane to easily layout labels & text fields
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(10);
		layout.setAlignment(Pos.CENTER);

		// TITLE
		label_Title.setFont(Font.font("Arial", 14));
		text_Title.setPromptText("Title");
		text_Body.setPrefWidth(400);
		text_Title.setMaxWidth(Double.MAX_VALUE);
		layout.add(label_Title, 0, 0);
		layout.add(text_Title, 1, 0);

		// HEADER
//		label_Header.setFont(Font.font("Arial", 14));
//		text_Header.setPromptText("Header");

		// SHORT DESCRIPTION
		label_ShortDescription.setFont(Font.font("Arial", 14));
		text_ShortDescription.setPromptText("Description");
		text_Body.setPrefWidth(400);
		text_ShortDescription.setMaxWidth(Double.MAX_VALUE);
		text_ShortDescription.setPrefHeight(100);
		layout.add(label_ShortDescription, 0, 1);
		layout.add(text_ShortDescription, 1, 1);

		// KEYWORDS
//		label_Keywords.setFont(Font.font("Arial", 14));
//		text_Keywords.setPromptText("Keywords");

		// LINKS
//		label_Links.setFont(Font.font("Arial", 14));
//		text_Links.setPromptText("Links");

		// BODY
		label_Body.setFont(Font.font("Arial", 14));
		text_Body.setPromptText("Body");
		text_Body.setPrefWidth(400);
		text_Body.setMaxWidth(Double.MAX_VALUE);
		text_Body.setPrefHeight(200);
		layout.add(label_Body, 0, 2);
		layout.add(text_Body, 1, 2);

		// BUTTONS
		layout.add(submit, 5, 5);
		layout.add(returnToArticles, 6, 5);

		/***
		 * Submit button: Used to submit newly created article if all fields are correct
		 * As-Is: Just prints statement NEEDS: Actual logic
		 */
		submit.setOnAction(e -> {
			System.out.println("Submitted!");
		});

		/***
		 * Return To Articles Button: Returns user to main article page
		 */
		returnToArticles.setOnAction(e -> {
			ArticleInterface articleInterface = new ArticleInterface(primaryStage, role);
		});

		// Wrap the VBox in a ScrollPane
		ScrollPane scrollPane = new ScrollPane(layout);
		scrollPane.setFitToWidth(true);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		Scene signUpScene = new Scene(scrollPane, width, height);
		primaryStage.setScene(signUpScene);
		primaryStage.setTitle("Sign Up");
	}

	/***
	 * ModifyArticle: This constructor is used to MODIFY articles, takes current
	 * values and pre-fills them into text fields. As-Is: Skeleton of GUI concept
	 * for this portion, really does nothing at the moment. NEEDS: Method of
	 * capturing keywords, links and header Actual logic of creating header and
	 * adding it to database Formatting
	 * 
	 * @param primaryStage
	 * @param role
	 * @param title
	 * @param description
	 * @param body
	 */
	public ModifyArticle(Stage primaryStage, String role, String title, String description, String body) {

		// The following code snippet pre-fills the text fields with the current values.
		text_Title.setText(title);
		text_ShortDescription.setText(description);
		text_Body.setText(body);

		// Used GridPane to easily layout labels & text fields
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(10);
		layout.setAlignment(Pos.CENTER);

		// TITLE
		label_Title.setFont(Font.font("Arial", 14));
		text_Title.setPromptText("Title");
		text_Body.setPrefWidth(400);
		text_Title.setMaxWidth(Double.MAX_VALUE);
		layout.add(label_Title, 0, 0);
		layout.add(text_Title, 1, 0);

		// HEADER
//		label_Header.setFont(Font.font("Arial", 14));
//		text_Header.setPromptText("Header");

		// SHORT DESCRIPTION
		label_ShortDescription.setFont(Font.font("Arial", 14));
		text_ShortDescription.setPromptText("Description");
		text_Body.setPrefWidth(400);
		text_ShortDescription.setMaxWidth(Double.MAX_VALUE);
		text_ShortDescription.setPrefHeight(100);
		layout.add(label_ShortDescription, 0, 1);
		layout.add(text_ShortDescription, 1, 1);

		// KEYWORDS
//		label_Keywords.setFont(Font.font("Arial", 14));
//		text_Keywords.setPromptText("Keywords");

		// LINKS
//		label_Links.setFont(Font.font("Arial", 14));
//		text_Links.setPromptText("Links");

		// BODY
		label_Body.setFont(Font.font("Arial", 14));
		text_Body.setPromptText("Body");
		text_Body.setPrefWidth(400);
		text_Body.setMaxWidth(Double.MAX_VALUE);
		text_Body.setPrefHeight(200);
		layout.add(label_Body, 0, 2);
		layout.add(text_Body, 1, 2);

		// BUTTONS
		layout.add(submit, 5, 5);
		layout.add(returnToArticles, 6, 5);

		/***
		 * Submit button: Used to submit newly created article if all fields are correct
		 * As-Is: Just prints statement NEEDS: Actual logic
		 */
		submit.setOnAction(e -> {
			System.out.println("Submitted!");
		});

		/***
		 * Return To Articles Button: Returns user to main article page
		 */
		returnToArticles.setOnAction(e -> {
			ArticleInterface articleInterface = new ArticleInterface(primaryStage, role);
		});

		// Wrap the VBox in a ScrollPane
		ScrollPane scrollPane = new ScrollPane(layout);
		scrollPane.setFitToWidth(true);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		Scene signUpScene = new Scene(scrollPane, width, height);
		primaryStage.setScene(signUpScene);
		primaryStage.setTitle("Sign Up");
	}

}
