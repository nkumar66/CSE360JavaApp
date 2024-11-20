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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class UserGroups {

	long id;
	String name;
	ArrayList<Article> articles;
	Map<AdminClass, ArrayList<String>> admins;
	Map<UserClass, ArrayList<String>> instructors; // <--- Will hold first instructor and other instructors w/ their permissions.
	Map<StudentClass, ArrayList<String>> students;
	GroupAccess access_level;
	
	
	/***
	 * Constructor
	 * @param name
	 * @param articles
	 * @param defaultRights
	 * @param admins
	 * @param instructors
	 * @param students
	 */
	public UserGroups(String name, ArrayList<Article> articles,  Map<AdminClass, ArrayList<String>>  admins, Map<UserClass, ArrayList<String>> instructors, Map<StudentClass, ArrayList<String>> students, GroupAccess access_level, long id) {
		this.name = name;
		this.articles = articles;
		this.admins = admins;
		this.instructors = instructors;
		this.students = students;
		this.access_level = access_level;
		this.id = id;
	}
	
	
	/***
	 * Below are the getters / setters for this object.
	 * 
	 * @return
	 */
	public long getID() {
		return this.id;
	}
	
	
    public String getGroupName() {
        return name;
    }
	
	public ArrayList<Article> getArticles() {
		return this.articles;
	}
	
	public Map<AdminClass, ArrayList<String>> getAdmins() {
		return this.admins;
	}
	
	public Map<UserClass, ArrayList<String>> getInstructors() {
		return this.instructors;
	}
	
	
	public Map<StudentClass, ArrayList<String>> getStudents() {
		return this.students;
	}
	
	public GroupAccess getAccessLevel() {
		return this.access_level;
	}
	
	public void setGroupName(String name) {
		this.name = name;
	}
	
    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public void setAdmins(Map<AdminClass, ArrayList<String>> admins) {
        this.admins = admins;
    }

    public void setInstructors(Map<UserClass, ArrayList<String>> instructors) {
        this.instructors = instructors;
    }

    public void setStudents(Map<StudentClass, ArrayList<String>> students) {
        this.students = students;
    }

    public void setAccessLevel(GroupAccess accessLevel) {
        this.access_level = accessLevel;
    }


	
	
	/***
	 * setColumnAttributes: Helper method that defaults all user-based selectable
	 * lists to display by group name
	 * @param <T>
	 * @param listView
	 */
	public static <T extends UserGroups> void setColumnAttributes(ListView<T> listView) {
		listView.setCellFactory(param -> new ListCell<T>() {
			@Override
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText("Group Name: " + item.getGroupName());
				}
			}
		});
	}
	
	/***
	 * This overrides the equals operator so that it compares Groups by id 
	 * NEEDS: CHANGE THIS TO COMPARE IDS W/ DATABASE
	 * 
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true; // Same reference
	    if (o == null || getClass() != o.getClass()) return false; // Null or different class
	    UserGroups group = (UserGroups) o; 
	    return this.id == group.id; // Compare unique attribute
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id); // Use the same attribute as in equals()
	}


	
	
	
	
}
