package org.dummy.app.view;

import lombok.extern.log4j.Log4j2;
import org.dummy.app.dao.UserDao;
import org.dummy.app.exception.DaoException;
import org.dummy.app.flow.Record;
import org.dummy.app.model.User;
import org.dummy.app.service.UserService;
import org.dummy.app.view.model.UserListTable;

import javax.swing.*;

@Log4j2
public class PrivatePanel extends JPanel
{

    private JTable userTable;
    private JPanel rootPanel;
    private JButton disconnectButton;

    private PanelContainer p;

    private UserService userService;

    private void setTranslations()
    {
        disconnectButton.setText( p.getString("disconnect"));
    }

    public PrivatePanel(PanelContainer p) throws DaoException {
        this.p = p;
        userService = new UserService();
        setTranslations();
        disconnectButton.addActionListener(
            actionEvent -> {
                p.switchTo("connection");
                log.info("User {} disconnected.", p.getSessionId());
                p.setSessionId(0);
            }
        );

        try
        {
            userTable.setModel( UserListTable.getInstance( userService.getAllUsers() ));
        } catch (DaoException | IllegalArgumentException e) {
            log.error(e.getMessage(),e);
            JOptionPane.showMessageDialog( null, "Problem with database, please check the logs.", "Fatal error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* package */ JPanel getRootPanel() {
        return rootPanel;
    }
}
