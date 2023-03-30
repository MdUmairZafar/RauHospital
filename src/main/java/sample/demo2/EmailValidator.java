package sample.demo2;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {



    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public static boolean isValid(TextField textField) {
        String email = textField.getText();
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}