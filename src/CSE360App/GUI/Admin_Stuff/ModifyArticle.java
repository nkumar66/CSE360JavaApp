package src.CSE360App.GUI.Admin_Stuff;

import java.util.ArrayList;

import src.CSE360App.GUI.ArticleInterface;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
	 // Labels
    private Label label_Title = new Label("Title: ");
    private Label label_PublicTitle = new Label("Public Title: ");
    private Label label_Author = new Label("Author: ");
    private Label label_Abstract = new Label("Abstract: ");
    private Label label_PublicAbstract = new Label("Public Abstract: ");
    private Label label_Body = new Label("Body: ");
    private Label label_SkillLevel = new Label("Skill Level: ");
    private Label label_AccessLevel = new Label("Access Level: ");
    private Label label_GroupID = new Label("Group ID: ");
    private Label label_Keywords = new Label("Keywords: ");
    private Label label_Links = new Label("Links: ");

    // TextFields
    private TextField text_Title = new TextField();
    private TextField text_PublicTitle = new TextField();
    private TextField text_Author = new TextField();
    private TextField text_Abstract = new TextField();
    private TextField text_PublicAbstract = new TextField();
    private TextArea text_Body = new TextArea();
    private TextField text_GroupID = new TextField();
    private TextField text_Keywords = new TextField();
    private TextField text_Links = new TextField();

    // ComboBoxes for Skill Level and Access Level
    private ComboBox<String> skillLevelComboBox = new ComboBox<>();
    private ComboBox<String> accessLevelComboBox = new ComboBox<>();

    // Buttons
    private Button submitButton = new Button("Submit");
    private Button returnToArticlesButton = new Button("Return");

    // Window size
    private int width = 700;
    private int height = 600;

    // Storage for tags
    private ArrayList<String> keywords = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();
    
    
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
        setupUI(primaryStage, role, "", "", "", keywords, links);
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
    public ModifyArticle(Stage primaryStage, String role, String title, String description, String body,
                         ArrayList<String> oldKeywords, ArrayList<String> oldLinks) {
        setupUI(primaryStage, role, title, description, body, oldKeywords, oldLinks);
    }

    
    /***
     * setupUI: Uses passed in data to prefill and present user with nicely formatted modify / create article page
     * @param primaryStage
     * @param role
     * @param title
     * @param description
     * @param body
     * @param initialKeywords
     * @param initialLinks
     */
    private void setupUI(Stage primaryStage, String role, String title, String description, String body,
                         ArrayList<String> initialKeywords, ArrayList<String> initialLinks) {
    	
    	
    	ArrayList<String> keywords = new ArrayList<>();
		ArrayList<String> links = new ArrayList<>();
    	
        // Populate ComboBoxes
        skillLevelComboBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");
        skillLevelComboBox.setValue("Beginner");

        accessLevelComboBox.getItems().addAll("Public", "Instructor", "Admin");
        accessLevelComboBox.setValue("Public");

        // Set initial text field values if provided
        text_Title.setText(title);
        text_Abstract.setText(description);
        text_Body.setText(body);

        // GridPane layout setup
        GridPane layout = new GridPane();
        VBox outerLayer = new VBox(layout);
        outerLayer.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setAlignment(Pos.CENTER);

        // Add fields to the layout
        int rowIndex = 0;
        
        // TITLES
        addFormField(layout, label_Title, text_Title, rowIndex++);
        addFormField(layout, label_PublicTitle, text_PublicTitle, rowIndex++);
        
        //AUTHORS
        addFormField(layout, label_Author, text_Author, rowIndex++);

        //ABSTRACTS
        addFormField(layout, label_Abstract, text_Abstract, rowIndex++);
        addFormField(layout, label_PublicAbstract, text_PublicAbstract, rowIndex++);
        
        //BODY
        
        //sizing for labels / input
        label_Title.setPrefWidth(400);
        text_Body.setPrefSize(500, 200);
        addFormField(layout, label_Body, text_Body, rowIndex++);
        
        //SKILL LEVEL / ACCESS LEVEL
        addFormField(layout, label_SkillLevel, skillLevelComboBox, rowIndex++);
        addFormField(layout, label_AccessLevel, accessLevelComboBox, rowIndex++);
        
        //GROUP ID
        addFormField(layout, label_GroupID, text_GroupID, rowIndex++);
        

        // Keywords
        
        addTagField(layout, label_Keywords, text_Keywords, initialKeywords, rowIndex++);
        
        // Links
        addTagField(layout, label_Links, text_Links, initialLinks, rowIndex++);

        // Add submit and return buttons
        HBox buttonBox = new HBox(10, submitButton, returnToArticlesButton);
        buttonBox.setAlignment(Pos.CENTER);
        layout.add(buttonBox, 0, rowIndex, 3, 1);

        // Set up event handlers
        submitButton.setOnAction(e -> handleSubmission());
        returnToArticlesButton.setOnAction(e -> new ArticleInterface(primaryStage, role));

        // Wrap layout in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Scene scene = new Scene(outerLayer, width, height);
        scene.getStylesheets().add("file:/C:/Users/Brian/git/CSE360App/src/CSE360App/application.css");
		layout.getStyleClass().add("root");
		layout.getStyleClass().add("label-padding");
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Modify Article");
    }

    /***
     * addFormField: Creates a form field for normal user inputs
     * @param layout
     * @param label
     * @param comboBox
     * @param row
     */
    private void addFormField(GridPane layout, Label label, TextField field, int row) {
        label.setFont(Font.font("Arial", 14));
        layout.add(label, 0, row);
        layout.add(field, 1, row);
    }

    
    /***
     * addFormField: Creates a form field for text area user inputs
     * @param layout
     * @param label
     * @param comboBox
     * @param row
     */
    private void addFormField(GridPane layout, Label label, TextArea field, int row) {
    	field.setWrapText(true);
        label.setFont(Font.font("Arial", 14));
        layout.add(label, 0, row);
        layout.add(field, 1, row);
    }
    
    
    /***
     * addFormField: Creates a form field for comboboxes  user inputs
     * @param layout
     * @param label
     * @param comboBox
     * @param row
     */
    private void addFormField(GridPane layout, Label label, ComboBox<String> comboBox, int row) {
        label.setFont(Font.font("Arial", 14));
        layout.add(label, 0, row);
        layout.add(comboBox, 1, row);
    }
    
    /***
     * initalizeTags: If any of the tag boxes have values, intialize them and display them.
     * @param values
     * @param tagContainer
     */
    private void initializeTags(ArrayList<String> values, FlowPane tagContainer) {
        for (String value : values) {
            addTag(value, tagContainer, values, false); // false to skip adding it again in values list
        }
    }

    /***
	addTagField: Helper function to create "tag select" options, where user can save list of words
     * 		   This one is the collection of tags
     * @param layout
     * @param label
     * @param textField
     * @param values
     * @param row
     */
    private void addTagField(GridPane layout, Label label, TextField textField, ArrayList<String> values, int row) {
        FlowPane tagContainer = new FlowPane();
        tagContainer.setHgap(8);
        


        // Populate existing tags
        initializeTags(values, tagContainer);

        // Add new tags
        textField.setOnAction(event -> {
            String tagText = textField.getText().trim();
            if (!tagText.isEmpty()) {
                addTag(tagText, tagContainer, values,true);
                textField.clear();
            }
        });

        label.setFont(Font.font("Arial", 14));
        layout.add(label, 0, row);
        layout.add(textField, 1, row);
        layout.add(tagContainer, 2, row);
    }

    /***
     * addTag: Helper function to create "tag select" options, where user can save list of words
     * 		   This one is the individual word, or tag
     * @param text
     * @param tagContainer
     * @param values
     */
    private void addTag(String text, FlowPane tagContainer, ArrayList<String> values, boolean addToList) {
        HBox tagBox = new HBox(5);
        Text tagText = new Text(text);
        
        if(addToList) {
        values.add(text);
        }
        
        
        Button removeButton = new Button("x");
        removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
        removeButton.setOnAction(e -> {
            tagContainer.getChildren().remove(tagBox);
            values.remove(text);
        });

        tagBox.getChildren().addAll(tagText, removeButton);
        tagContainer.getChildren().add(tagBox);
    }

    /***
     * handleSubmission: Eventually will send and save article in database
     * As-Is: Just prints statement
     * NEEDS: Actual logic
     * 
     */
    private void handleSubmission() {
        System.out.println("Submitted!");
        System.out.printf("Title: %s\nAbstract: %s\nBody: %s\nKeywords: %s\nLinks: %s\n",
                text_Title.getText(), text_Abstract.getText(), text_Body.getText(),
                String.join(", ", keywords), String.join(", ", links));
    }
}