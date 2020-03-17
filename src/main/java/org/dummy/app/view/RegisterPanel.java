package org.dummy.app.view;

import lombok.extern.log4j.Log4j2;
import org.dummy.app.exception.DaoException;
import org.dummy.app.exception.InvalidUserException;
import org.dummy.app.service.UserService;
import org.dummy.app.utils.PasswordUtils;
import javax.swing.*;
import java.util.Arrays;

@Log4j2
public class RegisterPanel {

    private PanelContainer p;
    private JPanel rootPanel;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordInput;
    private JPasswordField confirmPasswordInput;
    private JLabel confirmPasswordLabel;
    private JButton signUpButton;
    private JTextField loginInput;
    private JButton signInButton;

    private UserService userService;

    private void registerUser()
    {
        try {
            char[] passwd = passwordInput.getPassword();
            char[] confirmPasswd = confirmPasswordInput.getPassword();
            int userId = userService.registerUser(
                loginInput.getText(),
                PasswordUtils.convertHashToCharArray(Arrays.hashCode(passwd)),
                PasswordUtils.convertHashToCharArray(Arrays.hashCode(confirmPasswd))
            );
            PasswordUtils.cleanPassword(passwd,confirmPasswd);
            log.info("User {} registered.",userId);
            p.setSessionId(userId);
            p.switchTo("private");
            cleanInputs();
        } catch (DaoException e) {
            log.error(e.getMessage(),e);
            JOptionPane.showMessageDialog( null, "Problem with database, please check the logs.", "Fatal error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidUserException e) {
            log.warn(e.getMessage(),e);
            JOptionPane.showMessageDialog( null, e.getMessage(), "Bad input(s)", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cleanInputs()
    {
        loginInput.setText("");
        passwordInput.setText("");
        confirmPasswordInput.setText("");
    }

    private void setTranslations()
    {
        loginLabel.setText( p.getString("login"));
        passwordLabel.setText( p.getString("passwd"));
        confirmPasswordLabel.setText( p.getString("passwd.confirm"));
        signUpButton.setText( p.getString("register"));
        signInButton.setText( p.getString("connect"));
    }

    public RegisterPanel(PanelContainer p) throws DaoException
    {
        this.p = p;

        userService = new UserService();

        signInButton.addActionListener(actionEvent -> p.switchTo("connection"));
        signUpButton.addActionListener(actionEvent -> registerUser());
    }

    /* package */ JPanel getRootPanel() {
        return rootPanel;
    }
}
