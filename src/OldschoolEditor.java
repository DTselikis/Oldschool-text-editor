import com.sun.deploy.panel.JavaPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Class that contains the main method.
 * Its job is to create a new instance (object) of MyFrame class
 * and set its attributes, along with setting
 * the look and feel based on the running system.
 *
 * @author Dimitrios Tselikis
 * @version 1.0
 */
public class OldschoolEditor{
    public static void main(String[] args) {
        MyFrame editor = new MyFrame("Oldschool Text Editor");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        editor.setSize(720, 480);
        editor.setResizable(true);
        editor.setLocationRelativeTo(null);
        editor.setVisible(true);
        // Closing operation will be handled via windowListener
        editor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
