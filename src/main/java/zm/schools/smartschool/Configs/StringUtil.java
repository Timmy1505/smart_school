package zm.schools.smartschool.Configs;

public class StringUtil {
    public static String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split(" ");
        StringBuilder capitalizedString = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalizedString.append(Character.toUpperCase(word.charAt(0)))
                                  .append(word.substring(1).toLowerCase())
                                  .append(" ");
            }
        }
        return capitalizedString.toString().trim();
    }
}
