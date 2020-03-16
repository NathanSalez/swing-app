package org.dummy.app.view;

import lombok.extern.log4j.Log4j2;
import org.dummy.app.exception.DaoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Identify the graphic structure of the application's frame.<br/>
 * Contains every view of the application.<br/>
 * @author Nathan Salez
 */
@Log4j2
public class PanelContainer extends JPanel {

    CardLayout layoutManager;

    private Application p;

    private JComboBox<String> listTranslation;

    private JLabel subtitle;

    private JPanel bottomPanel;

    private JPanel centerPanel;

    private JPanel topPanel;

    private JButton quitButton;

    public PanelContainer(Application p)
    {
        this.p = p;

        buildTopPanel();

        buildBottomPanel();

        layoutManager = new CardLayout();
        centerPanel = new JPanel();
        centerPanel.setLayout(layoutManager);
        try {
            centerPanel.add(new ConnectionPanel(this).getRootPanel(), "connection");
            centerPanel.add(new RegisterPanel(this).getRootPanel(), "register");
            centerPanel.add(new PrivatePanel(this).getRootPanel(),"private");
        } catch (DaoException e) {
            log.error(e.getMessage(),e);
            JOptionPane.showMessageDialog( null, "The application failed to start, please contact an administrator.", "Fatal error", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(p::dispose);
        }
        switchTo("connection", ConnectionPanel.SUBTITLE);

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

        buildListTranslation();

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setBackground( new Color(254, 254, 226));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(listTranslation);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(quitButton);
    }

    private void buildQuitButton()
    {
        this.quitButton = new JButton("Exit");
        this.quitButton.setFont( Application.FONT_BUTTON);
        this.quitButton.addActionListener((ActionEvent e) -> {p.dispose();});
    }

    private void buildListTranslation()
    {
        this.listTranslation = new JComboBox<>();
        this.listTranslation.addItem("Français");
        this.listTranslation.addItem("English");
        this.listTranslation.setFont( Application.FONT_BUTTON);
    }

    /**
     * Switch to another view with its name.<br/>
     * @param page Name of the view to display
     * @param subtitle Subtitle to insert.
     */
    public void switchTo(String page, String subtitle)
    {
        setSubtitle(subtitle);
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

}
