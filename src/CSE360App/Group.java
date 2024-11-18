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

public class Group {

	long id;
	String name;
	ArrayList<Article> articles;
	ArrayList<String> defaultRights;
	ArrayList<AdminClass> admins;
	ArrayList<UserClass> instructors; // <--- Will hold first instructor and other instructors w/ their permissions.
	ArrayList<StudentClass> students;
	
	
	/***
	 * Constructor
	 * @param name
	 * @param articles
	 * @param defaultRights
	 * @param admins
	 * @param instructors
	 * @param students
	 */
	public Group(String name, ArrayList<Article> articles, ArrayList<String> defaultRights, ArrayList<AdminClass> admins, ArrayList<UserClass> instructors, ArrayList<StudentClass> students) {
		this.name = name;
		this.articles = articles;
		this.defaultRights = defaultRights;
		this.admins = admins;
		this.instructors = instructors;
		this.students = students;
	}
	
	
	
	
	
	
}
