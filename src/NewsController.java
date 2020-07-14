import java.util.ArrayList;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Controller class for news desktop app.
 *
 * @author evanw
 *
 *
 */
public final class NewsController {

    // Creates model and view objects
    private final NewsModel model;
    private final NewsView view;

    /**
     * Processes titles from the NYTimes.
     *
     * @param out
     * @return an ArrayList of article titles.
     */
    public static ArrayList<String> processTitle(SimpleWriter out) {

        String feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
        // Try and catch to see if feed is valid
        try {
            XMLTree xml = new XMLTree1(feedURL);
            ArrayList<String> articleTitles = new ArrayList<String>();

            XMLTree channel = xml.child(0);
            for (int i = 0; i < channel.numberOfChildren(); i++) {
                if (channel.child(i).label().equals("item")) {
                    articleTitles
                            .add(channel.child(i).child(0).child(0).label());
                }

            }
            return articleTitles;

        } catch (Exception e) {
            out.println("Error: Could not load feed");
            return null;
        }
    }

    /**
     * Processes titles from the NYTimes.
     *
     * @param out
     * @return an ArrayList of article descriptions.
     */
    public static ArrayList<String> processDescription(SimpleWriter out) {

        String feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
        // Try and catch to see if feed is valid
        try {
            XMLTree xml = new XMLTree1(feedURL);
            ArrayList<String> articleDescription = new ArrayList<String>();

            // TODO Could this be simpler with recursion?
            XMLTree channel = xml.child(0);
            for (int i = 0; i < channel.numberOfChildren(); i++) {
                if (channel.child(i).label().equals("item")) {
                    // Second for loop to search for description tag
                    for (int j = 0; j < channel.child(i)
                            .numberOfChildren(); j++) {
                        if (channel.child(i).child(j).label()
                                .equals("description")) {
                            XMLTree description = channel.child(i).child(j);
                            // Checks if a description exists
                            if (description.numberOfChildren() > 0) {
                                articleDescription
                                        .add(description.child(0).label());
                            } else {
                                articleDescription.add("No Description.");
                            }
                        }

                    }

                }
            }
            return articleDescription;

        } catch (Exception e) {
            out.println("Error: Could not load feed");
            return null;
        }
    }

    private static void updateViewToMatchModel(NewsModel model, NewsView view) {

        String output = model.output();
        /*
         * Update view to reflect changes in model
         */
    }

    // Constructor which connects this class to the respecting model and view
    public NewsController(NewsModel model, NewsView view) {
        this.model = model;
        this.view = view;

        updateViewToMatchModel(this.model, this.view);
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        /*
         * Open I/O streams.
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        in.close();
        out.close();
    }

}
