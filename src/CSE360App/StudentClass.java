package src.CSE360App;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentClass extends UserClass {
    private static String[] skills = {"Astah", "Eclipse", "Git", "Java", "JavaFX", "Scrum"};  // Static as requested
    private Map<String, SkillLevel> skillMap = new HashMap<>();  // Skill levels for each skill

    public StudentClass(String username, char[] password, boolean isOTP, String firstName, String middleName,
                        String lastName, String preferredFirstName, String email, List<String> roles) {
        // Call the parent class constructor (UserClass)
        super(username, password, isOTP, firstName, middleName, lastName, preferredFirstName, email, roles);
        
        // Initialize skillMap with default skill levels
        for (String skill : skills) {
            skillMap.put(skill, SkillLevel.INTERMEDIATE);  // Default skill level
        }
    }

   
    public StudentClass(String username, char[] password, boolean isOTP, String firstName, String middleName,
                        String lastName, String preferredFirstName, String email, List<String> roles, SkillLevel[] skillLevels) {
        super(username, password, isOTP, firstName, middleName, lastName, preferredFirstName, email, roles);
        
        // Initialize skillMap with provided skill levels
        for (int i = 0; i < skills.length; i++) {
            skillMap.put(skills[i], skillLevels[i]);
        }
    }


    public String getSkillLevel(String typeOfSkill) {		//to retrieve the skill level for each skill level
        if (skillMap.containsKey(typeOfSkill)) {
            return skillMap.get(typeOfSkill).toString();
        } else {
            return "No skill";
        }
    }


    public void setSkillLevel(String typeOfSkill, SkillLevel skill) {
        skillMap.put(typeOfSkill, skill);		//to set the skill level to a value
    }


    public static String[] getSkills() {		//getter for skills
        return skills;
    }
}
