import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

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

    //private static final int ROWS_PANEL_GRID = 2, COLS_PANEL_GRID = 1;

    public NewsView() {

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("The New York Times - Breaking News");

        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        this.add(mainPanel);

        //header.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Header with NYTimes Logo
        //BufferedImage logo = ImageIO.read(new File("nytimesLogo.png"));
        ImageIcon nyTimesLogo = new ImageIcon("nytimesLogo.png");
        Image scaleImage = nyTimesLogo.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT);
        JLabel headerLogo = new JLabel(nyTimesLogo);
        mainPanel.add(headerLogo, BorderLayout.PAGE_START);
        headerLogo.setPreferredSize(new Dimension(600, 200));

        // Scroll pane and sets up scroll speed
        JPanel scrollPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        mainPanel.add(scrollPanel);

        JScrollPane scroll = new JScrollPane(scrollPanel);
        scroll.getVerticalScrollBar().setUnitIncrement(8);
        mainPanel.add(scroll);

        SimpleWriter out = new SimpleWriter1L();

        // For loop for headlines
        for (int i = 0; i < NewsController.processTitle(out).size(); i++) {
            // Article Thumbnail
            String thumbnailURL = NewsController.processThumbnail(out).get(i);
            ImageIcon thumbnailImg = new ImageIcon();
            JLabel thumbnail = new JLabel(thumbnailImg);
            scrollPanel.add(thumbnail);
            // Article Title
            String title = NewsController.processTitle(out).get(i);
            JButton articleTitle = new JButton(title);
            scrollPanel.add(articleTitle);
            articleTitle.setFont(new Font("Roboto", Font.BOLD, 14));
            articleTitle.setHorizontalAlignment(SwingConstants.LEFT);
            // Article Description
            String description = NewsController.processDescription(out).get(i);
            scrollPanel.add(new JLabel(description));
        }

        //scrollPanel.add(new JButton(NewsController.processFeed(out).get(i)))
        /*
         * Starts the app window and ensures main window is appropriately sized
         * and it exits this program when closed, and that its visible to the
         * user
         */

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, screenSize.width, screenSize.height);
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
