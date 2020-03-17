package org.dummy.app.utils;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class I18NUtilsTest {

    @Test
    void getPanelTranslation()
    {
        ResourceBundle defaultTranslationList = I18NUtils.getPanelTranslation("connect","unknownLanguage");
        ResourceBundle englishTranslationList = I18NUtils.getPanelTranslation("connect","English");
        ResourceBundle frenchTranslationList = I18NUtils.getPanelTranslation("connect","Français");

        assertNotNull(I18NUtils.getPanelTranslation("connect","Français"));
        assertThrows(MissingResourceException.class, () -> I18NUtils.getPanelTranslation("unknown","Français"));
        assertEquals(Locale.ENGLISH,englishTranslationList.getLocale());
        assertEquals(Locale.ENGLISH, defaultTranslationList.getLocale());
        assertEquals("S'inscrire",frenchTranslationList.getString("register"));
    }
}
