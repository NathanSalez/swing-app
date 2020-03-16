package org.dummy.app.view;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the frame of application.<br/>
 * It contains the following meta datas :
 * <ul>
 *     <li>Dimension</li>
 *     <li>Title</li>
 * </ul>
 */
public class Application extends JFrame {

    public static final Dimension MINIMUM_FRAME_DIMENSION = new Dimension(435,300);

    public static final Font FONT_BUTTON = new Font(null,Font.BOLD, 17);

    private int userSessionId;

    public Application()
    {
        this.setContentPane( new PanelContainer(this) );
        this.setTitle("Dummy App");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setMinimumSize(MINIMUM_FRAME_DIMENSION);
        this.setVisible(true);
    }

    public int getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(int userSessionId) {
        this.userSessionId = userSessionId;
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }
}
