/**
 * <p> GroupManager Class </p>
 * 
 * <p> Description: The GroupManager class is used to hold the temporary groups made within launches of the program
 * Used as a temporary tool for GUI development
 * 	   
			   	   
 */

package src.CSE360App;

import java.util.ArrayList;
import java.util.Map;

public class GroupManager {
	// Static ArrayList to store all groups (resets each execution)
	private static ArrayList<UserGroups> groups = new ArrayList<>();

	// Add a new group to the list
	public static void addGroup(UserGroups group) {
		groups.add(group);
	}

	// Remove a group from the list
	public static void removeGroup(UserGroups group) {
		groups.remove(group);
	}

	// Get all groups
	public static ArrayList<UserGroups> getGroups() {
		return groups;
	}

	public static int getSize() {
		return groups.size();
	}

	// Print all groups (for debugging or display purposes)
	public static void printGroups() {
		for (UserGroups group : groups) {
			System.out.println("Group: " + group.getGroupName());
		}
	}

	/***
	 * getGroupByID: Returns the group object of the matching ID
	 * 
	 * @param id
	 * @return
	 */
	public static UserGroups getGroupByID(long id) {
		for (UserGroups group : groups) { // assuming groupsList is your list of groups
			if (group.getID() == id) {
				return group; // Return the group if it matches
			}
		}
		return null; // Return null if no group is found with the title
	}

	/***
	 * Print Groups w/ Attributes: Helper function mainly used for debugging
	 * purposes
	 * 
	 */
	public static void printGroupsWithAttributes() {
		System.out.println("******************************************");
		for (UserGroups group : groups) {
			System.out.println("Group ID: " + group.getID());
			System.out.println("Group Name: " + group.getGroupName());
			System.out.println("Access Level: " + group.getAccessLevel());
//            System.out.println("Number of Articles: " + group.getArticles().size());

			// Print Admin usernames and permissions
			System.out.println("Admins:");
			for (Map.Entry<AdminClass, ArrayList<String>> entry : group.getAdmins().entrySet()) {
				AdminClass admin = entry.getKey();
				ArrayList<String> permissions = entry.getValue();
				System.out.println(" - " + admin.getUsername() + " Permissions: " + permissions);
			}

			// Print Instructor usernames and permissions
			System.out.println("Instructors:");
			for (Map.Entry<UserClass, ArrayList<String>> entry : group.getInstructors().entrySet()) {
				UserClass instructor = entry.getKey();
				ArrayList<String> permissions = entry.getValue();
				System.out.println(" - " + instructor.getUsername() + " Permissions: " + permissions);
			}

			// Print Student usernames and permissions
			System.out.println("Students:");
			for (Map.Entry<StudentClass, ArrayList<String>> entry : group.getStudents().entrySet()) {
				StudentClass student = entry.getKey();
				ArrayList<String> permissions = entry.getValue();
				System.out.println(" - " + student.getUsername() + " Permissions: " + permissions);
			}

			// Total Users
//            System.out.println("Total Users: " + group.getUsers().size());
			System.out.println("----------------------------");
		}
		System.out.println("******************************************");
	}

	/***
	 * getGroupIDbyName: Finds ID of the group given a name
	 * 
	 * @param groupName
	 * @return
	 */
	public static Integer getGroupIdByName(String groupName) {
		System.out.println("Looking for group name: " + groupName);
		for (UserGroups group : groups) {
			System.out.println("Checking group: " + group.getGroupName());
			if (group.getGroupName().equalsIgnoreCase(groupName)) {
				return (int) group.getID();
			}
		}
		return null; // Not found
	}

}
