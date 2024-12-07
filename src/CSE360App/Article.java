/**
 * <p> Article Class </p>
 * 
 * <p> Description: The article class is used to hold the important attributes that an article holds such as:
 * 	   
 * 	   Title
 * 	   Header (Skill Level)
 * 	   Short Description
 * 	   A list of keywords
 * 	   A list of links
 * 	   Body					   	   
 */

package src.CSE360App;

import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class Article {

	/**************
	 * Attributes
	 **************/

	
	// Not really sure what header is supposed to look like? The description is a bit all over the place
	/***
	 * 
	 * "a unique header including information such as the level of the article (e.g., beginner, intermediate, advanced, expert), 
	 * grouping identifiers (so it is easy for the instructional team to update or delete a related set of articles), 
	 * and other system information that might limit who can read the article for sensitive/restricted information"
	 */
	
	private long id;
	private String title;
	private String publicTitle;
	private String author;
	private String Abstract; // abstract
	private String public_Abstract;
	private String body;
	private SkillLevel skillLevel;
	private AccessLevel accessLevel;
	private String groupID;	
	private ArrayList<String> keywords;
	private ArrayList<String> links;

	/**
	 * This method serves as the constructor for an article. As-is: Used generic
	 * data types for each attribute. NEEDS: Update data types for appropriate
	 * encryption and safety.
	 * 
	 * @param title
	 * @param header
	 * @param SDescription
	 * @param keywords
	 * @param Links
	 * @param body
	 */
	public Article(String title, SkillLevel skillLevel, String Abstract, String authors, ArrayList<String> keywords,
			ArrayList<String> Links, String body) {
		this.title = title;
		this.skillLevel = skillLevel;
		this.Abstract = Abstract;
		this.author = authors;
		this.keywords = keywords;
		this.links = Links;
		this.body = body;

	}
	
	// Add a new constructor to match the deserialization format
	public Article(long id, String title, String publicTitle, String author, String abstr, SkillLevel skillLevel, String body) {
	    this.id = id;
	    this.title = title;
	    this.publicTitle = publicTitle;
	    this.author = author;
	    this.Abstract = abstr;
	    this.skillLevel = skillLevel;
	    this.body = body;

	    // Initialize other optional fields with defaults
	    this.keywords = new ArrayList<>();
	    this.links = new ArrayList<>();
	}


	/**
	 * getHeaderAsString: Converts the "header" (AKA as skill level for article) to
	 * a string for easy GUI display
	 * 
	 * @return Article's skill level as a string.
	 */
	public String getSkillLevelAsString() {

		String val;
		switch (this.skillLevel) {

		case BEGINNER:
			val = "Beginner";
			break;

		case INTERMEDIATE:
			val = "Intermediate";
			break;

		case ADVANCED:
			val = "Advanced";

		case EXPERT:
			val = "Expert";

		default:
			val = "ERROR";

		}

		return val;
	}

	/**
	 * getKeywordsAsString: Converts the keywords array list into a comma delimited
	 * string. As-is: Just converts the array-list into a string NEEDS: Probably
	 * needs optimization
	 * 
	 * @return keywords as a comma delimited string
	 */
	public String getKeywordsAsString() {
	    if (keywords == null || keywords.isEmpty()) {
	        return "No keywords available"; // Handle empty or null lists
	    }
	    return String.join(", ", keywords);
	}


	/**
	 * getLinksAsString: Converts the links array list into a comma delimited
	 * string. As-is: Just converts the array-list into a string NEEDS: Probably
	 * needs optimization
	 * 
	 * @return links as a comma delimited string
	 */
	public String getLinksAsString() {
	    if (links == null || links.isEmpty()) {
	        return "No links available"; // Handle empty or null lists gracefully
	    }
	    return String.join(", ", links);
	}


	// Below are just the getter & setter methods for each attribute.
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}
	
	public String getPublicTitle() {
		return publicTitle;
	}
	
	public void setPublicTitle(String newPublicTitle) {
		publicTitle = newPublicTitle;
	}

	public String getAuthor( ) {
		return author;
	}
	
	public void setAuthor(String newAuthor) {
		author = newAuthor;
	}
	
	public String getAbstract() {
		return Abstract;
	}

	public void setAbstract(String newDescription) {

		Abstract = newDescription;
	}

	
	public String getPublicAbstract() {
		return public_Abstract;
	}

	public void setPublicAbstract(String newDescription) {

		public_Abstract = newDescription;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String newBody) {
		body = newBody;
	}
	


	public SkillLevel getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String newSkillLevel) {
		SkillLevel val;

		newSkillLevel = newSkillLevel.toLowerCase();

		switch (newSkillLevel) {

		case "beginner":
			val = SkillLevel.BEGINNER;
			break;

		case "intermediate":
			val = SkillLevel.INTERMEDIATE;
			break;

		case "advanced":
			val = SkillLevel.ADVANCED;
			break;

		case "expert":
			val = SkillLevel.EXPERT;
			break;

		default:
			val = SkillLevel.BEGINNER;
			break;

		}

		skillLevel = val;
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String newAccessLevel) {
		AccessLevel val;

		newAccessLevel = newAccessLevel.toLowerCase();

		switch (newAccessLevel) {

		case "public":
			val = AccessLevel.PUBLIC;
			break;

		case "admin":
			val = AccessLevel.ADMIN;
			break;

		case "instructor":
			val = AccessLevel.INSTRUCTOR;
			break;

		default:
			val = AccessLevel.PUBLIC;
			break;

		}

		accessLevel = val;
	}
	
	public String getGroupID() {
		return groupID;
	}
	
	public void setGroupID(String newID) {
		groupID = newID;
	}

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(ArrayList<String> newKeywords) {
		keywords = newKeywords;
	}

	public void addKeyword(String newKeyword) {
		keywords.add(newKeyword);
	}

	public ArrayList<String> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<String> newLinks) {
		links = newLinks;
	}

	public void addLink(String newLink) {
		links.add(newLink);
	}
	
	
	/***
	 * setColumnArticleAttributes: Helper method that defaults all user-based
	 * selectable lists to display by article title
	 * 
	 * @param <T>
	 * @param listView
	 */
	public static <T extends Article> void setColumnArticleAttributes(ListView<T> listView) {
		listView.setCellFactory(param -> new ListCell<T>() {

			// Essentially just override what is displayed
			@Override
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					int index = listView.getItems().indexOf(item) + 1;
					setText(index + ". " + item.getTitle() + ": " + item.getAuthor() + ", " + item.getAbstract()); // Only display the title of the Article
				}
			}
		});
	}
	
	@Override
	public String toString() {
	    return String.join("|",
	        String.valueOf(id),        // ID
	        title,                     // Title
	        publicTitle,               // Public Title
	        author,                    // Author
	        Abstract,                  // Abstract
	        skillLevel.name(),         // Skill Level
	        body                       // Body
	    );
	}


	/***
	 * Converts a serialized string into an `Article` object.
	 * The serialized string is expected to contain the article's fields separated by the '|' character.
	 * @param serializedArticle
	 * @return
	 */
	public static Article fromString(String serializedArticle) {
	    String[] parts = serializedArticle.split("\\|");
	    if (parts.length < 7) { // Ensure all fields are present
	        throw new IllegalArgumentException("Invalid serialized article format");
	    }

	    long id = Long.parseLong(parts[0].trim());
	    String title = parts[1].trim();
	    String publicTitle = parts[2].trim();
	    String author = parts[3].trim();
	    String abstr = parts[4].trim();
	    SkillLevel skillLevel = SkillLevel.valueOf(parts[5].trim().toUpperCase());
	    String body = parts[6].trim();

	    // Create and return a new Article object
	    return new Article(id, title, publicTitle, author, abstr, skillLevel, body);
	}






	
	/***
	 * This overrides the equals operator so that it compares articles by titles 
	 * NEEDS: CHANGE THIS TO COMPARE IDS W/ DATABASE
	 * 
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true; // Same reference
	    if (o == null || getClass() != o.getClass()) return false; // Null or different class
	    Article article = (Article) o; 
	    return this.title.equals(article.title); // Compare unique attribute
	}

	@Override
	public int hashCode() {
	    return Objects.hash(title); // Use the same attribute as in equals()
	}

	
	
	/***
	 * populateArticles: TEMPORARY method that makes fake articles in order to
	 * create GUI As-is: Just makes fake articles with default attributes Needs:
	 * real articles
	 */
	public static void populateArticles(ArrayList<Article> articles) {
        // Clear the existing list of articles
        articles.clear();

        try {
            // Get an instance of the DatabaseHelper and connect to the database
            DatabaseHelper dbHelper = DatabaseHelper.getInstance();
            dbHelper.connectToDataBase();

            // SQL query to fetch all articles
            String query = "SELECT * FROM cse360articles";
            Statement stmt = dbHelper.statement;
            ResultSet rs = stmt.executeQuery(query);

            // Iterate over the ResultSet to create Article objects
            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String publicTitle = rs.getString("publicTitle");
                String author = rs.getString("author");
                String abstr = rs.getString("abstract");
                String body = rs.getString("body");
                SkillLevel skillLevel = SkillLevel.valueOf(rs.getString("skillLevel").toUpperCase());

                // Add each Article to the list
                articles.add(new Article(id, title, publicTitle, author, abstr, skillLevel, body));
            }
        } catch (Exception e) {
            System.err.println("Error fetching articles from database: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
