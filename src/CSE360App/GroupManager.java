/**
 * <p> GroupManager Class </p>
 * 
 * <p> Description: The GroupManager class is used to hold the temporary groups made within launches of the program
 * Used as a temporary tool for GUI development
 * 	   
			   	   
 */

package src.CSE360App;

import java.util.ArrayList;

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
     * getGroupIDbyName: Finds ID of the group given a name
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
