package CSE360App;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.control.ScrollPane;

/**
 * <p> ArticleInterface Class </p>
 * 
 * <p> Description: The article interface class is used to display content to all users and allow admins / instructors to customize content
 * 	   				   	   
 */
public class ArticleInterface {

	// TEMP: Temporary array list of articles for GUI testing
	private ArrayList<Article> articles = new ArrayList<>();
	
	// Serves as the collection of data / attributes that go into an article (Allows for easy formatting in GUI)
	private TextFlow contentArea = new TextFlow();

	// Customizable window size for Article reader.
	private int width = 1000;
	private int height = 800;

	
	// Button: Sends admin to settings page where they can backup, restore, add / delete, etc.
	private Button returnButton = new Button("Admin Settings");

	
	/**
	 * ArticleInterface: Window that shows all articles on left with title and keywords, and displays selected article on reader on right side.
	 * @param primaryStage
	 * @param role
	 */
	public ArticleInterface(Stage primaryStage, String role) {

		
		// TEMP: Populates fake articles for sake of testing GUI
		populateArticles();
		
		// JavaFX array list that allows the use of "listeners" which interacts with UI
		ObservableList<String> articleTitles = FXCollections.observableArrayList();
		for (Article article : articles) {
			articleTitles.add(article.getTitle());
		}

		ListView<String> listView = new ListView<>(articleTitles);
		listView.setPrefWidth(width * .2);
		
		// The following section of code allows the ability to "select" articles by clicking them, which calls display article method
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				Article selectedArticle = findArticleByTitle(newValue);
				if (selectedArticle != null) {
					displayArticle(selectedArticle);
				}
			}
		});
		
		
		// Uses border pane layout to utilize its modularity
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(20));

		// **LEFT: Set article list on left side of border pane
		layout.setLeft(listView);
		
		// Wraps the content of an article so that it allows for easier, uniform formatting 
		VBox contentWrapper = new VBox(contentArea);
		contentWrapper.setAlignment(Pos.CENTER);
		contentArea.setTextAlignment(TextAlignment.CENTER);
		
		// ScrollPane: Allows users to scroll if content overflows given window size.
		ScrollPane contentScrollPane = new ScrollPane(contentWrapper);
		contentScrollPane.setFitToWidth(true);
		contentScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		layout.setCenter(contentScrollPane);

		// **BOTTOM: Set buttons for users on bottom.
		HBox bottomBox = new HBox(returnButton);
		bottomBox.setAlignment(Pos.CENTER);
		layout.setBottom(bottomBox);

		returnButton.setOnAction(e -> {
			switch (role) {
			case "admin":
			case "student-admin":
				AdminInterface adminInterface = new AdminInterface(primaryStage);
			}
		});

		Scene articleScene = new Scene(layout, width, height);
		primaryStage.setScene(articleScene);
		primaryStage.setTitle("Article");
	}

	/**
	 * findArticleByTitle: Function to search for articles
	 * As-is: Just uses the title of the article for search
	 * NEEDS: Definitely needs to be optimized to use some sort of key and more efficient searching method
	 * @param title
	 * @return
	 */
	private Article findArticleByTitle(String title) {
		for (Article article : articles) {
			if (article.getTitle().equalsIgnoreCase(title)) {
				return article;
			}
		}

		return null;
	}

	/**
	 * displayArticle: Function that formats and displays every attribute of an article in the article viewer.
	 * @param article
	 */
	private void displayArticle(Article article) {
		contentArea.getChildren().clear();

		if (article != null) {
			//TITLE
			Text titleText = new Text(article.getTitle() + "\n\n");
			titleText.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 48));
			
			//HEADER / SKILL LEVEL
			Text headerText = new Text(article.getSkillLevel().toString() + "\n\n");
			headerText.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 12));
			
			// DESCRIPTION
			Text descriptionText = new Text(article.getShortDescription() + "\n\n");
			descriptionText.setFont(javafx.scene.text.Font.font("Arial", 12));
			
			// KEYWORDS
			Text keywordText = new Text(article.getKeywordsAsString() + "\n\n");
			keywordText.setFont(javafx.scene.text.Font.font("Arial", 12));
			
			// LINKS
			Text linksText = new Text(article.getLinksAsString() + "\n\n");
			linksText.setFont(javafx.scene.text.Font.font("Arial", 12));
			
			// BODY
			Text bodyText = new Text(article.getBody());
			bodyText.setFont(javafx.scene.text.Font.font("Arial", 12));

			contentArea.getChildren().addAll(titleText, headerText, descriptionText, keywordText, linksText, bodyText);
		}
	}

	/***
	 * populateArticles: TEMPORARY method that makes fake articles in order to create GUI
	 * As-is: Just makes fake articles with default attributes
	 * Needs: real articles
	 */
	private void populateArticles() {

		SkillLevel genericSkill = SkillLevel.BEGINNER;
		String genericShortDescription = "This is a short description.";
		ArrayList<String> genericKeywords = new ArrayList<>();
		genericKeywords.add("Keyword 1");
		genericKeywords.add("Keyword 2");
		genericKeywords.add("Keyword 3");
		ArrayList<String> genericLinks = new ArrayList<>();
		genericLinks.add("Link 1");
		genericLinks.add("Link 2");
		genericLinks.add("Link 3");

		articles.add(new Article("Title 1", genericSkill, genericShortDescription, genericKeywords, genericLinks,
				"This is the content of Article 1."));
		articles.add(new Article("Title 2", genericSkill, genericShortDescription, genericKeywords, genericLinks,
				"This is the content of Article 2."));
		articles.add(new Article("Title 3", genericSkill, genericShortDescription, genericKeywords, genericLinks,
				"This is the content of Article 3."));
		articles.add(new Article("Title 4", genericSkill, genericShortDescription, genericKeywords, genericLinks,
				"This is the content of Article 4."));
		articles.add(new Article("Title 5", genericSkill, genericShortDescription, genericKeywords, genericLinks,
				"This is the content of Article 5."));
		articles.add(new Article("Title 6", genericSkill, genericShortDescription, genericKeywords, genericLinks,
				"This is the content of Article 6."));
	}

}
