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
	public Article(String title, SkillLevel skillLevel, String Abstract, ArrayList<String> keywords,
			ArrayList<String> Links, String body) {
		this.title = title;
		this.skillLevel = skillLevel;
		this.Abstract = Abstract;
		this.keywords = keywords;
		this.links = Links;
		this.body = body;

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
		String val = "";
		for (int i = 0; i < keywords.size() - 1; i++) {
			val += keywords.get(i) + ", ";
		}
		val += keywords.getLast();

		return val;

	}

	/**
	 * getLinksAsString: Converts the links array list into a comma delimited
	 * string. As-is: Just converts the array-list into a string NEEDS: Probably
	 * needs optimization
	 * 
	 * @return links as a comma delimited string
	 */
	public String getLinksAsString() {
		String val = "";
		for (int i = 0; i < links.size() - 1; i++) {
			val += links.get(i) + ", ";
		}
		val += links.getLast();

		return val;

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

}
