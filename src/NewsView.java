import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * View.
 *
 * @author evanw
 *
 */
public final class NewsView extends JFrame {

    /**
     * Controller object.
     */
    private NewsController controller;

    private static final int ROWS_PANEL_GRID = 2, COLS_PANEL_GRID = 1;

    public NewsView() {

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("The New York Times - Breaking News");

        JPanel mainPanel = new JPanel(new GridLayout(0, COLS_PANEL_GRID));

        JLabel header = new JLabel("The New York Times | U.S. News");

        JScrollPane scroll = new JScrollPane(mainPanel);

        // Adds label to window
        mainPanel.add(header);

        // Creates panel

        // Adds main panel to the main window
        this.add(scroll);

        // mainPanel.add(bRefresh);

        SimpleWriter out = new SimpleWriter1L();

        // For loop for headlines
        for (int i = 0; i < NewsController.processTitle(out).size(); i++) {
            String title = NewsController.processTitle(out).get(i);
            String description = NewsController.processDescription(out).get(i);

            mainPanel.add(new JButton(title));
            mainPanel.add(new JLabel(description));
        }

        //scrollPanel.add(new JButton(NewsController.processFeed(out).get(i)))
        /*
         * Starts the app window and ensures main window is appropriately sized
         * and it exits this program when closed, and that its visible to the
         * user
         */

        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     *
     * @param controller
     *            controller to register
     */
    public void registerObserver(NewsController controller) {
        this.controller = controller;
    }

}
