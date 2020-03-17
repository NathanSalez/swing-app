package org.dummy.app.view;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.dummy.app.exception.DaoException;
import org.dummy.app.utils.I18NUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Identify the graphic structure of the application's frame.<br/>
 * Contains every view of the application.<br/>
 * @author Nathan Salez
 */
@Log4j2
public class PanelContainer extends JPanel {

    private CardLayout layoutManager;

    private Application p;

    private ResourceBundle languageData;

    private JLabel subtitle;

    private JPanel bottomPanel;

    private JPanel centerPanel;

    private JPanel topPanel;

    private JButton quitButton;

    public PanelContainer(Application p, String language)
    {
        this.p = p;

        layoutManager = new CardLayout();
        centerPanel = new JPanel();
        centerPanel.setLayout(layoutManager);
        try {
            languageData = I18NUtils.getPanelTranslation("translations",language);
            centerPanel.add(new ConnectionPanel(this).getRootPanel(), "connection");
            centerPanel.add(new RegisterPanel(this).getRootPanel(), "register");
            centerPanel.add(new PrivatePanel(this).getRootPanel(),"private");
        } catch (DaoException | MissingResourceException e) {
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "The application failed to start, please contact an administrator.", "Fatal error", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(p::dispose);
        }

        buildTopPanel();
        buildBottomPanel();

        switchTo("connection");

        this.setLayout( new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel,BorderLayout.CENTER);
        this.add(bottomPanel,BorderLayout.PAGE_END);
    }

    private void buildSubtitle()
    {
        this.subtitle = new JLabel("TITLE_NOT_FOUND",JLabel.CENTER);
        this.subtitle.setFont(new Font(null, Font.PLAIN, 40));
    }

    private void setSubtitle(String text)
    {
        this.subtitle.setText(text);
    }

    private void buildTopPanel()
    {
        buildSubtitle();
        topPanel = new JPanel();
        topPanel.add(this.subtitle);
        topPanel.setBackground( new Color(254, 254, 226));
        topPanel.setBorder( BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black) );
    }

    private void buildBottomPanel()
    {
        buildQuitButton();

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setBackground( new Color(254, 254, 226));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(quitButton);
    }

    private void buildQuitButton()
    {
        this.quitButton = new JButton(languageData.getString("exit"));
        this.quitButton.setFont( Application.FONT_BUTTON);
        this.quitButton.addActionListener((ActionEvent e) -> {p.dispose();});
    }

    /**
     * Switch to another view with its name.<br/>
     * @param page Name of the view to display
     */
    public void switchTo(@NonNull String page)
    {
        setSubtitle(languageData.getString("subtitle." + page));
        layoutManager.show( centerPanel, page );
    }

    public void setSessionId(int userId)
    {
        p.setUserSessionId(userId);
    }

    public int getSessionId()
    {
        return p.getUserSessionId();
    }

    public String getString(String key)
    {
        String translatedKey = key;
        try
        {
            translatedKey = languageData.getString(key);
        }catch (MissingResourceException e)
        {
            log.warn("Key '" + key + "' is missing for resource bundle " + languageData.getLocale() + ".");
        }
        return translatedKey;
    }

}
