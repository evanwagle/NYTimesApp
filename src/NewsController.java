import java.util.ArrayList;
import java.util.Arrays;

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

    /**
     * Processes titles from the NYTimes.
     *
     * @param out
     * @param feedURL
     *            String for NYTimes RSS URL
     * @return an ArrayList of article titles.
     */
    public static ArrayList<String> processTitle(SimpleWriter out, String feedURL) {

        // Try and catch to see if feed is valid
        try {
            XMLTree xml = new XMLTree1(feedURL);
            ArrayList<String> articleTitles = new ArrayList<String>();

            XMLTree channel = xml.child(0);
            for (int i = 0; i < channel.numberOfChildren(); i++) {
                if (channel.child(i).label().equals("item")) {
                    articleTitles.add(channel.child(i).child(0).child(0).label());
                }

            }
            return articleTitles;

        } catch (Exception e) {
            out.println("Error: Could not load feed");
            return null;
        }
    }

    /**
     * Processes links from the NYTimes.
     *
     * @param out
     * @param feedURL
     *            String for NYTimes RSS URL
     * @return an ArrayList of article links.
     */
    public static ArrayList<String> processLink(SimpleWriter out, String feedURL) {

        // Try and catch to see if feed is valid
        try {
            XMLTree xml = new XMLTree1(feedURL);
            ArrayList<String> articleTitles = new ArrayList<String>();

            XMLTree channel = xml.child(0);
            for (int i = 0; i < channel.numberOfChildren(); i++) {
                if (channel.child(i).label().equals("item")) {
                    articleTitles.add(channel.child(i).child(1).child(0).label());
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
     * @param feedURL
     *            String for NYTimes RSS URL
     * @return an ArrayList of article descriptions.
     */
    public static ArrayList<String> processDescription(SimpleWriter out, String feedURL) {

        // Try and catch to see if feed is valid
        try {
            XMLTree xml = new XMLTree1(feedURL);
            ArrayList<String> articleDescription = new ArrayList<String>();

            // TODO Could this be simpler with recursion?
            XMLTree channel = xml.child(0);
            for (int i = 0; i < channel.numberOfChildren(); i++) {
                if (channel.child(i).label().equals("item")) {
                    // Second for loop to search for description tag
                    for (int j = 0; j < channel.child(i).numberOfChildren(); j++) {
                        if (channel.child(i).child(j).label().equals("description")) {
                            XMLTree description = channel.child(i).child(j);
                            // Checks if a description exists
                            if (description.numberOfChildren() > 0) {
                                articleDescription.add(description.child(0).label());
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

    /**
     * Processes titles from the NYTimes.
     *
     * @param out
     * @param feedURL
     *            String for NYTimes RSS URL
     * @return an ArrayList of article descriptions.
     */
    public static String[] processThumbnail(SimpleWriter out, int articleTotal, String feedURL) {

        // Try and catch to see if feed is valid
        try {
            XMLTree xml = new XMLTree1(feedURL);
            String[] articleThumbnail = new String[articleTotal];
            Arrays.fill(articleThumbnail, "noImageAvailable.png");
            int articlePos = 0;

            XMLTree channel = xml.child(0);
            for (int i = 0; i < channel.numberOfChildren(); i++) {
                if (channel.child(i).label().equals("item")) {
                    // Second for loop to search for thumbnail media tag
                    boolean foundMedia = false;
                    for (int j = 0; j < channel.child(i).numberOfChildren(); j++) {
                        if (channel.child(i).child(j).label().equals("media:content")) {
                            XMLTree mediaContent = channel.child(i).child(j);
                            // Checks if a thumbnail exists
                            if (mediaContent.hasAttribute("url")) {
                                articleThumbnail[articlePos] = mediaContent.attributeValue("url");
                                articlePos++;
                                foundMedia = true;
                            }
                        }
                        // Increases array position if at end of loop and media thumbnail still is not found
                        else if (j == channel.child(i).numberOfChildren() - 1 && !foundMedia) {
                            articlePos++;
                        }
                    }
                }
            }
            return articleThumbnail;

        } catch (Exception e) {
            out.println("Error: Could not load feed");
            return null;
        }
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
