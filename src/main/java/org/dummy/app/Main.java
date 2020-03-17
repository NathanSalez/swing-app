package org.dummy.app;


import lombok.extern.log4j.Log4j2;
import org.dummy.app.view.Application;

import javax.swing.*;

@Log4j2
public class Main
{
    public static void main(String[] args) {
        log.info("Hello there");
        SwingUtilities.invokeLater(
                () -> {
                    Application ac1 = new Application("Français");
                }
        );
    }
}
