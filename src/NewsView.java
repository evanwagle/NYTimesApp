import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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

    public NewsView() {

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("The New York Times - Breaking News");

        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        this.add(mainPanel);

        // Header with NYTimes Logo
        //BufferedImage logo = ImageIO.read(new File("nytimesLogo.png"));
        ImageIcon nyTimesLogo = new ImageIcon("nytimesLogo.png");
        Image scaleImage = nyTimesLogo.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT);
        JLabel headerLogo = new JLabel(nyTimesLogo);
        mainPanel.add(headerLogo, BorderLayout.PAGE_START);
        headerLogo.setPreferredSize(new Dimension(600, 200));

        // Scroll pane and organizes layout
        JPanel scrollPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Adds panel and left and right padding
        mainPanel.add(scrollPanel);
        //scrollPanel.setBorder(new EmptyBorder(0, 100, 0, 100));

        // Creates scroll pane for vertical scroll bar and changes scroll speed
        JScrollPane scroll = new JScrollPane(scrollPanel);
        scroll.getVerticalScrollBar().setUnitIncrement(8);
        mainPanel.add(scroll);

        SimpleWriter out = new SimpleWriter1L();

        // For loop for thumbnails
        int thumbnailYPos = 0;
        int titleYPos = 0;
        int descYPos = 1;
        for (int i = 0; i < NewsController.processTitle(out).size(); i++) {
            // Article Thumbnail
            gbc.gridx = 0;
            gbc.gridy = thumbnailYPos;
            gbc.gridheight = 2;
            gbc.weightx = 1;
            gbc.weighty = 1;
            int articleTotal = NewsController.processTitle(out).size();
            String thumbnailURL = NewsController.processThumbnail(out, articleTotal)[i];

            if (thumbnailURL.equals("noImageAvailable.png")) {
                ImageIcon thumbnailImg = new ImageIcon(thumbnailURL);
                JLabel thumbnail = new JLabel(thumbnailImg);
                scrollPanel.add(thumbnail, gbc);
            } else {
                // Gets thumbnail from urls
                try {
                    URL imgURL = new URL(thumbnailURL);
                    BufferedImage thumbnailImg = ImageIO.read(imgURL);
                    JLabel thumbnail = new JLabel(new ImageIcon(thumbnailImg));
                    scrollPanel.add(thumbnail, gbc);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            thumbnailYPos += 2;

            // Article Title as JButton with Article Link
            gbc.gridx = 1;
            gbc.gridy = titleYPos;
            gbc.gridheight = 1;
            String title = NewsController.processTitle(out).get(i);
            JButton articleTitleButton = new JButton(title);
            articleTitleButton.setFont(new Font("Roboto", Font.BOLD, 16));
            // Linking with JButton
            String link = NewsController.processLink(out).get(i);
            scrollPanel.add(articleTitleButton, gbc);
            titleYPos += 2;

            // Article Description
            gbc.gridx = 1;
            gbc.gridy = descYPos;
            gbc.gridheight = 1;
            String description = NewsController.processDescription(out).get(i);
            JLabel descText = new JLabel("<html><p>" + description + "</p></html>");
            scrollPanel.add(descText, gbc);
            descYPos += 2;

        }

        //scrollPanel.add(new JButton(NewsController.processFeed(out).get(i)))
        /*
         * Starts the app window and ensures main window is appropriately sized
         * and it exits this program when closed, and that its visible to the
         * user
         */

        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //this.setBounds(0, 0, screenSize.width, screenSize.height);
        this.pack();
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
