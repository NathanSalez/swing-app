package org.dummy.app.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@UtilityClass
@FieldDefaults(level= AccessLevel.PRIVATE)
public class I18NUtils
{
    public static final Map<String,Locale> translationLanguages = Map.of(
        "Français",Locale.FRANCE,
        "English",Locale.ENGLISH);

    public ResourceBundle getPanelTranslation(String name, String language) throws MissingResourceException
    {
        Locale locale = translationLanguages.getOrDefault(language,Locale.ENGLISH);
        return ResourceBundle.getBundle(name, locale);
    }
}
