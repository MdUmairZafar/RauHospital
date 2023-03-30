package sample.demo2;

import javafx.scene.control.PasswordField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");

    public static boolean isValid(PasswordField passwordField) {
        String password = passwordField.getText();
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }
}