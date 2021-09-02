import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handler for mouse events.
 * Class job is to change the background color
 * of the button that user hovered the cursor
 * for visual feedback to the user.
 *
 * @author Dimitrios Tselikis
 * @version 1.0
 */
public class ButtonHandler extends MouseAdapter {
    /**
     * Changing the background color of the button
     * specified by the source that produced the
     * MouseEvent to a light cyan
     *
     * @param e MouseEvent
     *
     * @since 1.0
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        JButton btn = (JButton)e.getSource();
        btn.setBackground(new Color(145, 219, 242, 88));
    }

    /**
     * Changing the background of the button
     * specified by the source that produced the
     * MouseEvent to null (transparent)
     *
     * @param e MouseEvent
     *
     * @since 1.0
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        JButton btn = (JButton)e.getSource();
        btn.setBackground(null);
    }

    /**
     * Changing the background of the button
     * specified by the source that produced the
     * MouseEvent to null (transparent)
     *
     * @param e MouseEvent
     *
     * @since 1.0
     */
    @Override
    public void mouseExited(MouseEvent e) {
        JButton btn = (JButton)e.getSource();
        btn.setBackground(null);
    }
}
