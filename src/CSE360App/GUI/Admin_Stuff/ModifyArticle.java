package src.CSE360App.GUI.Admin_Stuff;

import java.util.ArrayList;
import java.util.List;

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
import src.CSE360App.SkillLevel;
import src.CSE360App.AccessLevel;
import src.CSE360App.DatabaseHelper;

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

    // Storage for tags
    private ArrayList<String> keywords = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();

    public ModifyArticle(Stage primaryStage, String role) {
        setupUI(primaryStage, role, "", "", "", keywords, links, "", "", "", SkillLevel.BEGINNER, AccessLevel.PUBLIC);
        
    }

    public ModifyArticle(Stage primaryStage, String role, String title, String description, String body,
            ArrayList<String> oldKeywords, ArrayList<String> oldLinks,
            String publicTitle, String publicAbstract, String groupID, SkillLevel skillLevel, AccessLevel accessLevel) {

// Ensure accessLevel is not null
if (accessLevel == null) {
System.err.println("Error: AccessLevel is null. Defaulting to PUBLIC.");
accessLevel = AccessLevel.PUBLIC; // Default to PUBLIC
}

setupUI(primaryStage, role, title, description, body, oldKeywords, oldLinks, publicTitle, publicAbstract, groupID, skillLevel, accessLevel);
}

    private void setupUI(Stage primaryStage, String role, String title, String description, String body,
                         ArrayList<String> initialKeywords, ArrayList<String> initialLinks,
                         String publicTitle, String publicAbstract, String groupID,
                         SkillLevel skillLevel, AccessLevel accessLevel) {

        // Populate ComboBoxes
        skillLevelComboBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");
        skillLevelComboBox.setValue(skillLevel.toString());

        accessLevelComboBox.getItems().addAll("Public", "Instructor", "Admin");
        accessLevelComboBox.setValue(accessLevel.toString());

        // Set initial text field values if provided
        text_Title.setText(title);
        text_PublicTitle.setText(publicTitle);
        text_Author.setText("");
        text_Abstract.setText(description);
        text_PublicAbstract.setText(publicAbstract);
        text_Body.setText(body);
        text_GroupID.setText(groupID);

        // Initialize keywords and links
        keywords.addAll(initialKeywords);
        links.addAll(initialLinks);

        // GridPane layout setup
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setAlignment(Pos.CENTER);

        // Add fields to the layout
        int rowIndex = 0;
        addFormField(layout, label_Title, text_Title, rowIndex++);
        addFormField(layout, label_PublicTitle, text_PublicTitle, rowIndex++);
        addFormField(layout, label_Author, text_Author, rowIndex++);
        addFormField(layout, label_Abstract, text_Abstract, rowIndex++);
        addFormField(layout, label_PublicAbstract, text_PublicAbstract, rowIndex++);
        addFormField(layout, label_Body, text_Body, rowIndex++);
        addFormField(layout, label_SkillLevel, skillLevelComboBox, rowIndex++);
        addFormField(layout, label_AccessLevel, accessLevelComboBox, rowIndex++);
        addFormField(layout, label_GroupID, text_GroupID, rowIndex++);
        addTagField(layout, label_Keywords, text_Keywords, keywords, rowIndex++);
        addTagField(layout, label_Links, text_Links, links, rowIndex++);

        // Add submit and return buttons
        HBox buttonBox = new HBox(10, submitButton, returnToArticlesButton);
        buttonBox.setAlignment(Pos.CENTER);
        layout.add(buttonBox, 0, rowIndex, 2, 1);

        // Set up event handlers
        submitButton.setOnAction(e -> handleSubmission());
        returnToArticlesButton.setOnAction(e -> new ArticleInterface(primaryStage, role));

        // Wrap layout in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Modify Article");
    }

    private void addFormField(GridPane layout, Label label, TextField field, int row) {
        label.setFont(Font.font("Arial", 14));
        layout.add(label, 0, row);
        layout.add(field, 1, row);
    }

    private void addFormField(GridPane layout, Label label, TextArea field, int row) {
        label.setFont(Font.font("Arial", 14));
        field.setWrapText(true);
        layout.add(label, 0, row);
        layout.add(field, 1, row);
    }

    private void addFormField(GridPane layout, Label label, ComboBox<String> comboBox, int row) {
        label.setFont(Font.font("Arial", 14));
        layout.add(label, 0, row);
        layout.add(comboBox, 1, row);
    }

    private void addTagField(GridPane layout, Label label, TextField textField, ArrayList<String> values, int row) {
        FlowPane tagContainer = new FlowPane();
        tagContainer.setHgap(8);

        // Populate existing tags
        for (String value : values) {
            addTag(value, tagContainer, values, false);
        }

        // Add new tags
        textField.setOnAction(event -> {
            String tagText = textField.getText().trim();
            if (!tagText.isEmpty()) {
                addTag(tagText, tagContainer, values, true);
                textField.clear();
            }
        });

        layout.add(label, 0, row);
        layout.add(textField, 1, row);
        layout.add(tagContainer, 2, row);
    }

    private void addTag(String text, FlowPane tagContainer, ArrayList<String> values, boolean addToList) {
        HBox tagBox = new HBox(5);
        Text tagText = new Text(text);
        Button removeButton = new Button("x");
        removeButton.setOnAction(e -> {
            tagContainer.getChildren().remove(tagBox);
            values.remove(text);
        });
        tagBox.getChildren().addAll(tagText, removeButton);
        tagContainer.getChildren().add(tagBox);
        if (addToList) values.add(text);
    }

    private void handleSubmission() {
        try {
            // Connect to the database
            DatabaseHelper dbHelper = DatabaseHelper.getInstance();
            dbHelper.connectToDataBase();

            // Collect keywords and links from their respective lists
            ArrayList<String> collectedKeywords = new ArrayList<>(keywords);
            ArrayList<String> collectedLinks = new ArrayList<>(links);

            // Call addArticle to save the article in the database
            dbHelper.addArticle(
                List.of("Admin"),                          // Assuming admin role for now
                text_Title.getText(),                      // Title
                text_PublicTitle.getText(),                // Public Title
                text_Author.getText(),                     // Author
                text_Abstract.getText(),                   // Abstract
                text_PublicAbstract.getText(),             // Public Abstract
                text_Body.getText(),                       // Body
                SkillLevel.valueOf(skillLevelComboBox.getValue().toUpperCase()), // Skill Level
                text_GroupID.getText(),                    // Group ID
                AccessLevel.valueOf(accessLevelComboBox.getValue().toUpperCase()), // Access Level
                new java.util.Date(),                      // Current Date
                collectedKeywords,                         // Keywords
                collectedLinks                              // Links
            );

            // Provide feedback to the user
            System.out.println("Article submitted successfully!");
        } catch (Exception e) {
            // Handle errors during submission
            System.err.println("Error submitting article: " + e.getMessage());
            e.printStackTrace();
        }
    }

}