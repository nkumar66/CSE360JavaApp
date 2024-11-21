package src.CSE360App;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class UserGroups {

    private long id;
    private String name;
    private ArrayList<Article> articles;
    private Map<AdminClass, ArrayList<String>> admins;
    private Map<UserClass, ArrayList<String>> instructors;
    private Map<StudentClass, ArrayList<String>> students;
    private GroupAccess access_level;
    private Set<UserClass> users; // Unified user set for easy management

    /***
     * Constructor
     * 
     * @param name
     * @param articles
     * @param admins
     * @param instructors
     * @param students
     * @param access_level
     * @param id
     */
    public UserGroups(String name, ArrayList<Article> articles, Map<AdminClass, ArrayList<String>> admins,
            Map<UserClass, ArrayList<String>> instructors, Map<StudentClass, ArrayList<String>> students,
            GroupAccess access_level, long id) {
        this.name = name;
        this.articles = articles;
        this.admins = admins;
        this.instructors = instructors;
        this.students = students;
        this.access_level = access_level;
        this.id = id;
        this.users = new HashSet<>(); // Initialize the unified user set
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

    /**
     * Add a user to the group
     * 
     * @param user UserClass object
     * @return boolean indicating success
     */
    public boolean addUser(UserClass user) {
        if (users.contains(user)) {
            System.out.println("User " + user.getUsername() + " already exists in the group.");
            return false;
        }
        users.add(user);
        System.out.println("User " + user.getUsername() + " added to the group: " + name);
        return true;
    }

    /**
     * Remove a user from the group
     * 
     * @param user UserClass object
     * @return boolean indicating success
     */
    public boolean removeUser(UserClass user) {
        if (!users.contains(user)) {
            System.out.println("User " + user.getUsername() + " is not a member of the group.");
            return false;
        }
        users.remove(user);
        System.out.println("User " + user.getUsername() + " removed from the group: " + name);
        return true;
    }

    /**
     * Check if a user exists in the group
     * 
     * @param user UserClass object
     * @return boolean indicating existence
     */
    public boolean hasUser(UserClass user) {
        return users.contains(user);
    }

    /**
     * Get all users in the group
     * 
     * @return Set of UserClass objects
     */
    public Set<UserClass> getUsers() {
        return users;
    }

    /***
     * setColumnAttributes: Helper method that defaults all user-based selectable
     * lists to display by group name
     * 
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
                    int index = listView.getItems().indexOf(item) + 1;
                    setText(index + ". " + "Group Name: " + item.getGroupName());
                }
            }
        });
    }

    /***
     * This overrides the equals operator so that it compares Groups by id NEEDS:
     * CHANGE THIS TO COMPARE IDS W/ DATABASE
     * 
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true; // Same reference
        if (o == null || getClass() != o.getClass())
            return false; // Null or different class
        UserGroups group = (UserGroups) o;
        return this.id == group.id; // Compare unique attribute
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Use the same attribute as in equals()
    }
}
