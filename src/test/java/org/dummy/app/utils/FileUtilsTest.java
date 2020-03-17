package org.dummy.app.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void exists()
    {
        String goodPathFile = "testFile";
        String badPathFile = "dqjsfsfgsdsdg";
        assertTrue(FileUtils.exists(goodPathFile));
        assertFalse(FileUtils.exists(badPathFile));
    }
}
