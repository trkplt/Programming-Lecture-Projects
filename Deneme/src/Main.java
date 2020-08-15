import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String patternString = "roll([ a-zA-z0-9]*)";
        Pattern pattern = Pattern.compile(patternString);
        String toMatch = "roll asjhfgkASFa sadfjkjhskd fd";
        Matcher matcher = pattern.matcher(toMatch);
        if (matcher.matches()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
        }
    }
}
