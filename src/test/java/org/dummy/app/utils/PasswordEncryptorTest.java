package org.dummy.app.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncryptorTest
{


    @Test
    void testPasswordEncryptor()
    {
        char[] passwd1 = new char[]{'b','i','t','e'};
        char[] passwd2 = new char[]{'b','i','t'};
        char[] passwd3 = new char[]{'b','i','t','t','e','n'};
        char[] passwd4 = new char[]{'b','i','t','e','t','n'};

        System.out.println(Arrays.hashCode(passwd1));
        System.out.println(Arrays.hashCode(passwd2));
        System.out.println(Arrays.hashCode(passwd3));
        System.out.println(Arrays.hashCode(passwd4));
    }
}
