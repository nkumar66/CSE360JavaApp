package CSE360App;
import java.util.HashMap;
import java.util.Map;

public class StudentClass extends UserClass {
	boolean isAdmin;
	String[] skills = {"Astah", "Eclipse", "Git", "Java", "JavaFX", "Scrum"};
	Map<String, SkillLevel> skillMap = new HashMap<>();

	public StudentClass(String username, char[] password, boolean isOTP, String firstName, String middleName,
			String lastName) {
		super(username, password, isOTP, firstName, middleName, lastName);
		for(int i = 0; i < skills.length; i++) {
			skillMap.put(skills[i], SkillLevel.INTERMEDIATE);
		}
	}
	public StudentClass(String username, char[] password, boolean isOTP, String firstName, String middleName,
			String lastName, SkillLevel[] skillLevels) {
		super(username, password, isOTP, firstName, middleName, lastName);
		for(int i = 0; i < skillLevels.length; i++) {
			skillMap.put(skills[i], skillLevels[i]);
		}
	}
	public String getSkillLevel(String typeOfSkill) {
		if(skillMap.containsKey(typeOfSkill)) {
			SkillLevel sk = skillMap.get(typeOfSkill);
			return sk.toString();
		}
		else {
			return "No skill";
		}
	}
	
	public void setSkillLevel(String typeOfSkill, SkillLevel skill) {
		skillMap.put(typeOfSkill, skill);
	}

}
