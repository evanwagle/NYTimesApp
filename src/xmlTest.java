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
public final class xmlTest {

    private static ArrayList<String> processFeed(XMLTree xml) {
        System.out.println(xml.child(0).label());

        ArrayList<String> articleTitles = new ArrayList<String>();

        XMLTree channel = xml.child(0);
        for (int i = 0; i < channel.numberOfChildren(); i++) {
            if (channel.child(i).label().equals("item")) {
                articleTitles.add(channel.child(i).child(0).child(0).label());
            }

        }
        return articleTitles;
    }

    /**
     * Processes titles from the NYTimes.
     *
     * @param out
     * @return an ArrayList of article descriptions.
     */
    public static void processDescription(SimpleWriter out) {

        String feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
        // Try and catch to see if feed is valid

        XMLTree xml = new XMLTree1(feedURL);
        ArrayList<String> articleDescription = new ArrayList<String>();

        // TODO Could this be simpler with recursion?
        XMLTree channel = xml.child(0);
        for (int i = 0; i < channel.numberOfChildren(); i++) {
            if (channel.child(i).label().equals("item")) {
                // Second for loop to search for description tag
                for (int j = 0; j < channel.child(i).numberOfChildren(); j++) {
                    if (channel.child(i).child(j).label()
                            .equals("description")) {
                        XMLTree description = channel.child(i).child(j);
                        // Checks if a description exists
                        if (description.numberOfChildren() > 0) {
                            articleDescription
                                    .add(description.child(0).label());
                        } else {
                            articleDescription.add("No Description");
                        }
                    }

                }

            }
        }
        out.println(articleDescription);
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

        String feedURL = "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
        // Try and catch to see if feed is valid
        try {
            XMLTree xml = new XMLTree1(feedURL);

            processDescription(out);

        } catch (Exception e) {
            out.println("Error: Could not load feed");
        }

        in.close();
        out.close();
    }

}
