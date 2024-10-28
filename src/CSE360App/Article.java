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

package CSE360App;

import java.util.ArrayList;

public class Article {

	/**************
	 * Attributes
	 **************/
	private long id;
	private String title;
	private SkillLevel header;
	private String SDescription;
	private ArrayList<String> keywords;
	private ArrayList<String> links;
	private String body;

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
	public Article(String title, SkillLevel header, String SDescription, ArrayList<String> keywords,
			ArrayList<String> Links, String body) {
		this.title = title;
		this.header = header;
		this.SDescription = SDescription;
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
	public String getHeaderAsString() {

		String val;
		switch (this.header) {

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

	public String getBody() {
		return body;
	}

	public void setBody(String newBody) {
		body = newBody;
	}

	public SkillLevel getSkillLevel() {
		return header;
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

		header = val;
	}

	public String getShortDescription() {
		return SDescription;
	}

	public void setShortDescription(String newDescription) {

		SDescription = newDescription;
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
