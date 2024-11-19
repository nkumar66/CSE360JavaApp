/**
 * <p> Group Class </p>
 * 
 * <p> Description: The Group class is used to hold the important attributes that an Group holds such as:
 * 	   
 * 	   id
 * 	   Title
 * 	   List of Articles
 * 	   List of default rights
 * 	   List of Admins
 * 	   List of Instructors
 * 	   List of Students					   	   
 */


package src.CSE360App;

import java.util.ArrayList;
import java.util.Map;

public class Group {

	long id;
	String name;
	ArrayList<Article> articles;
	ArrayList<String> defaultRights;
	Map<AdminClass, ArrayList<String>> admins;
	Map<UserClass, ArrayList<String>> instructors; // <--- Will hold first instructor and other instructors w/ their permissions.
	Map<StudentClass, ArrayList<String>> students;
	
	
	/***
	 * Constructor
	 * @param name
	 * @param articles
	 * @param defaultRights
	 * @param admins
	 * @param instructors
	 * @param students
	 */
	public Group(String name, ArrayList<Article> articles,  Map<AdminClass, ArrayList<String>>  admins, Map<UserClass, ArrayList<String>> instructors, Map<StudentClass, ArrayList<String>> students) {
		this.name = name;
		this.articles = articles;
		this.admins = admins;
		this.instructors = instructors;
		this.students = students;
	}
	
	
	
	
	
	
}
