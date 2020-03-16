package org.dummy.app.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class PasswordUtils
{
    private void cleanPassword(char[] password)
    {
        Arrays.fill(password, (char) 0);
    }

    public void cleanPassword(char[]... passwords)
    {
        for (char[] password : passwords)
        {
            cleanPassword(password);
        }
    }

    public char[] convertHashToCharArray(int hashcode)
    {
        return ("" + hashcode).toCharArray();
    }
}
