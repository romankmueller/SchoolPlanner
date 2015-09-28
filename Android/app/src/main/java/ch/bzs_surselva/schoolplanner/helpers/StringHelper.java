package ch.bzs_surselva.schoolplanner.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringHelper
{
    public static String nullToEmpty(String value)
    {
        if (value == null)
        {
            return "";
        }

        return value;
    }

    public static boolean isEmailValid(String email)
    {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches())
        {
            isValid = true;
        }

        return isValid;
    }
}
