package CSE360App;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentClass extends UserClass {
	private boolean isAdmin;
	private static String[] skills = {"Astah", "Eclipse", "Git", "Java", "JavaFX", "Scrum"};
	private Map<String, SkillLevel> skillMap = new HashMap<>();

	public StudentClass(String username, char[] password, boolean isOTP, String firstName, String middleName,
			String lastName, List<String> roles) {
		super(username, password, isOTP, firstName, middleName, lastName, roles);
		for (int i = 0; i < skills.length; i++) {
			skillMap.put(skills[i], SkillLevel.INTERMEDIATE);
		}
	}

	public StudentClass(String username, char[] password, boolean isOTP, String firstName, String middleName,
			String lastName, List<String> roles, SkillLevel[] skillLevels) {
		super(username, password, isOTP, firstName, middleName, lastName, roles);
		for (int i = 0; i < skillLevels.length; i++) {
			skillMap.put(skills[i], skillLevels[i]);
		}
	}

	public String getSkillLevel(String typeOfSkill) {
		if (skillMap.containsKey(typeOfSkill)) {
			SkillLevel sk = skillMap.get(typeOfSkill);
			return sk.toString();
		} else {
			return "No skill";
		}
	}

	public void setSkillLevel(String typeOfSkill, SkillLevel skill) {
		skillMap.put(typeOfSkill, skill);
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
