import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * View.
 *
 * @author evanw
 *
 */
public final class NewsView extends JFrame implements ActionListener {
    /**
     * Creation of news source buttons.
     */
    private final JButton bHome, bWorld, bUS, bBusiness, bTechnology, bSports, bScience, bHealth;
    /**
     * Class variable for feedURL to be changed.
     */
    public static String feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
    /**
     * Panel where news elements are added to.
     */
    public static JPanel scrollPanel = new JPanel();
    /**
     * Class variable for positioning news elements.
     */
    public static GridBagConstraints gbc;

    /**
     * Sets view of the NYTimes App.
     */
    public NewsView() {

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("The New York Times - Breaking News");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        this.add(mainPanel);

        // NYTimes header, creates image panel, sets dimension, adds to mainPanel
        ImageIcon img = new ImageIcon(this.getClass().getResource("nytimesLogo.png"));
        JLabel headerLogo = new JLabel(img);
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.white);
        imagePanel.add(headerLogo);
        imagePanel.setPreferredSize(new Dimension(600, 300));
        mainPanel.add(imagePanel);

        // Scroll pane and organizes layout
        gbc = new GridBagConstraints();
        scrollPanel = new JPanel(new GridBagLayout());
        scrollPanel.setBackground(Color.white);

        // Source layout for navigating different categories
        JPanel newsSource = new JPanel(new FlowLayout());
        newsSource.setBackground(Color.white);
        // Creates buttons
        this.bHome = new JButton("Home Page");
        this.bWorld = new JButton("World");
        this.bUS = new JButton("U.S.");
        this.bBusiness = new JButton("Business");
        this.bTechnology = new JButton("Technology");
        this.bSports = new JButton("Sports");
        this.bScience = new JButton("Science");
        this.bHealth = new JButton("Health");

        // Adds buttons to panel
        newsSource.add(this.bHome);
        newsSource.add(this.bWorld);
        newsSource.add(this.bUS);
        newsSource.add(this.bBusiness);
        newsSource.add(this.bTechnology);
        newsSource.add(this.bSports);
        newsSource.add(this.bScience);
        newsSource.add(this.bHealth);

        // Registers action listeners
        this.bHome.addActionListener(this);
        this.bWorld.addActionListener(this);
        this.bUS.addActionListener(this);
        this.bBusiness.addActionListener(this);
        this.bTechnology.addActionListener(this);
        this.bSports.addActionListener(this);
        this.bScience.addActionListener(this);
        this.bHealth.addActionListener(this);

        mainPanel.add(newsSource);

        // Creates scroll pane for vertical scroll bar and changes scroll speed
        JScrollPane scroll = new JScrollPane(NewsView.scrollPanel);
        scroll.getVerticalScrollBar().setUnitIncrement(8);
        NewsView.scrollPanel.setBorder(new EmptyBorder(0, 80, 0, 80));
        mainPanel.add(scroll);

        NewsSource.processSource();

        /*
         * Starts app window, ensures window is sized, exits program when closed
         */
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor when feed is loading
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        // Determine events occurred for callback.
        Object source = event.getSource();
        if (source == this.bHome) {
            NewsView.feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
        } else if (source == this.bWorld) {
            NewsView.feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/World.xml";
        } else if (source == this.bUS) {
            NewsView.feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/US.xml";
        } else if (source == this.bBusiness) {
            NewsView.feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
        } else if (source == this.bTechnology) {
            NewsView.feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml";
        } else if (source == this.bSports) {
            NewsView.feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/Sports.xml";
        } else if (source == this.bScience) {
            NewsView.feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/Science.xml";
        } else if (source == this.bHealth) {
            NewsView.feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/Health.xml";
        }
        // Removes current news, processes new source and reloads panel
        NewsView.scrollPanel.removeAll();
        NewsSource.processSource();
        NewsView.scrollPanel.revalidate();
        NewsView.scrollPanel.repaint();

        /*
         * Sets cursor back to normal
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
