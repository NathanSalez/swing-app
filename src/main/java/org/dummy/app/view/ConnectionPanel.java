package org.dummy.app.view;

import lombok.extern.log4j.Log4j2;

import org.dummy.app.exception.ConnectionException;
import org.dummy.app.exception.DaoException;
import org.dummy.app.exception.InvalidUserException;
import org.dummy.app.service.UserService;
import org.dummy.app.utils.PasswordUtils;

import javax.swing.*;
import java.util.Arrays;

@Log4j2
public class ConnectionPanel extends JPanel
{
    private JPanel rootPanel;
    private JLabel loginLabel;
    private JTextField loginInput;
    private JPasswordField passwordInput;
    private JLabel passwordLabel;
    private JButton signInButton;
    private JButton signUpButton;

    public static String SUBTITLE = "Connection";

    private UserService userService;

    private PanelContainer p;


    private void connectUser()
    {
        try {
            char[] passwd = passwordInput.getPassword();
            int userId = userService.connectUser(
                loginInput.getText(),
                PasswordUtils.convertHashToCharArray(Arrays.hashCode(passwd))
            );
            PasswordUtils.cleanPassword(passwd);
            p.setSessionId(userId);
            log.info("User {} connected.",userId);
            p.switchTo("private",PrivatePanel.SUBTITLE);
            cleanInputs();
        } catch (DaoException e) {
            log.error(e.getMessage(),e);
            JOptionPane.showMessageDialog( null, "Problem with database, please check the logs.", "Fatal error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidUserException e) {
            log.warn(e.getMessage(),e);
            JOptionPane.showMessageDialog( null, e.getMessage(), "Bad input(s)", JOptionPane.WARNING_MESSAGE);
        } catch (ConnectionException e) {
            log.warn(e.getMessage(),e);
            JOptionPane.showMessageDialog( null, e.getMessage(), "Connection error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cleanInputs()
    {
        loginInput.setText("");
        passwordInput.setText("");
    }

    public ConnectionPanel(PanelContainer p) throws DaoException {
        this.p = p;

        userService = new UserService();
        signUpButton.addActionListener(actionEvent -> p.switchTo("register", RegisterPanel.SUBTITLE));
        signInButton.addActionListener(actionEvent -> connectUser());
    }

    /* package */ JPanel getRootPanel() {
        return rootPanel;
    }
}
