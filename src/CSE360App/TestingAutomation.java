package CSE360App;

import java.util.Arrays;
import java.util.List;

public class TestingAutomation {

    public static void performTestCase() {
        // Step 1: Create Users
        AdminClass admin = new AdminClass("adminUser", "adminPass".toCharArray(), false, "Admin", "A.", "User", "Admin", "admin@example.com", List.of("Admin"));
        StudentClass student = new StudentClass("studentUser", "studentPass".toCharArray(), false, "Student", "S.", "User", "Student", "student@example.com", List.of("Student"));

        // Step 2: Perform Admin Actions
        System.out.println("\n--- Testing Admin Actions ---");
        AdminClass.inviteUser("newUser@example.com", "Student");
        AdminClass.completeSignUp("someInviteCode", "newPass".toCharArray(), "newPass".toCharArray(), "First", "M", "Last", "Preferred");

        // Reset user account and delete a user
        //admin.resetUserAccount(student);
        //admin.deleteUserAccount(student);

        // Add and remove roles
        //admin.addRole(student, "Team Leader");
        admin.removeRole(student, "Team Leader");

        // List all users
        admin.listUsers();

        // Step 3: Perform Student Actions
        System.out.println("\n--- Testing Student Actions ---");
        System.out.println("Initial skill levels:");
        for (String skill : StudentClass.getSkills()) {
            System.out.println(skill + ": " + student.getSkillLevel(skill));
        }

        // Set new skill level
        student.setSkillLevel("Java", SkillLevel.EXPERT);
        System.out.println("Updated skill level for Java: " + student.getSkillLevel("Java"));

        // Step 4: Test Role Assignment and Password Validation for a User
        System.out.println("\n--- Testing User Actions ---");
        System.out.println("User Roles: " + student.getRoles());
        System.out.println("Password validation (correct password): " + student.validatePassword("studentPass"));
        System.out.println("Password validation (wrong password): " + student.validatePassword("wrongPass"));
    }

    public static void main(String[] args) {
        performTestCase();
    }
}
