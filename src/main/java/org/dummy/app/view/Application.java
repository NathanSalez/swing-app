package org.dummy.app.view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

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

    @Getter
    @Setter
    private int userSessionId;

    public Application(String language)
    {
        this.setContentPane( new PanelContainer(this, language) );
        this.setTitle("Dummy App");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setMinimumSize(MINIMUM_FRAME_DIMENSION);
        this.setVisible(true);
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }
}
