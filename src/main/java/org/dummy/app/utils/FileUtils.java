package org.dummy.app.utils;

import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

@UtilityClass
public class FileUtils
{

    /**
     * Build the properties object corresponding to the resource file.
     * @param filePath path from the resource directory.
     * @return the Properties object corresponding to the resource file.
     * @throws IOException file not found.
     */
    public Properties getProperties(String filePath) throws IOException {
        Objects.requireNonNull(filePath);
        Optional<InputStream> propertiesStream = Optional.ofNullable(FileUtils.class.getClassLoader().getResourceAsStream(filePath));
        Properties properties = new Properties();
        properties.load(
            propertiesStream.
                orElseThrow( () -> new FileNotFoundException(filePath + " not found or not readable.") )
        );
        return properties;
    }

    /**
     * Returns the string from the file which path is given.
     * @param filePath an absolute path from '{main|test}/resources' directory.
     * @throws IOException the file is not readable or not found.
     * @return a string containing each character of the file.
     */
    public String getFileContent(String filePath) throws IOException
    {
        Optional<InputStream> requiredResource = Optional.ofNullable(FileUtils.class.getClassLoader().getResourceAsStream(filePath));

        return new String(
            requiredResource
                .orElseThrow( () -> new FileNotFoundException(filePath + " not found or not readable."))
                .readAllBytes()
        );
    }

    /**
     * returns true if the file described by filePath exists.
     * @param filePath an absolute path from '{main|test}/resources' directory.
     * @return true if the file described by filePath exists.
     */
    public boolean exists(String filePath)
    {
        InputStream fileContent = FileUtils.class.getClassLoader().getResourceAsStream(filePath);
        return fileContent != null;
    }
}
