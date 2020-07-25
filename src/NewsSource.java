import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class NewsSource {

    public static void processSource() {
        SimpleWriter out = new SimpleWriter1L();

        // For loop for thumbnails
        int thumbnailYPos = 0;
        int titleYPos = 0;
        int descYPos = 1;

        for (int i = 0; i < NewsController.processTitle(out, NewsView.feedURL).size(); i++) {
            // Article Thumbnail
            NewsView.gbc.gridx = 0;
            NewsView.gbc.gridy = thumbnailYPos;
            NewsView.gbc.gridheight = 2;
            NewsView.gbc.weightx = 1;
            NewsView.gbc.weighty = 1;
            int articleTotal = NewsController.processTitle(out, NewsView.feedURL).size();
            String thumbnailURL = NewsController.processThumbnail(out, articleTotal, NewsView.feedURL)[i];
            // Displays thumbnail if there is no image available
            if (thumbnailURL.equals("noImageAvailable.png")) {
                ImageIcon thumbnailImg = new ImageIcon(NewsSource.class.getResource("noImageAvailable.png"));
                JLabel thumbnail = new JLabel(thumbnailImg);
                NewsView.scrollPanel.add(thumbnail, NewsView.gbc);
            } else {
                // Gets thumbnail from URLs
                try {
                    URL imgURL = new URL(thumbnailURL);
                    BufferedImage thumbnailImg = ImageIO.read(imgURL);
                    JLabel thumbnail = new JLabel(new ImageIcon(thumbnailImg));
                    NewsView.scrollPanel.add(thumbnail, NewsView.gbc);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            thumbnailYPos += 2;

            // Article Title as JButton with Article Link, moves Ypos variable moves new title down 2 spaces
            NewsView.gbc.gridx = 1;
            NewsView.gbc.gridy = titleYPos;
            NewsView.gbc.gridheight = 1;
            // Aligns left and resets insets padding
            NewsView.gbc.anchor = GridBagConstraints.WEST;
            NewsView.gbc.insets = new Insets(0, 20, 0, 0);
            String title = NewsController.processTitle(out, NewsView.feedURL).get(i);
            JLabel articleTitleButton = new JLabel(title);
            articleTitleButton.setFont(new Font("Roboto", Font.BOLD, 16));
            // TODO Linking with JButton
            String link = NewsController.processLink(out, NewsView.feedURL).get(i);
            NewsView.scrollPanel.add(articleTitleButton, NewsView.gbc);
            titleYPos += 2;

            // Article Description
            NewsView.gbc.gridx = 1;
            NewsView.gbc.gridy = descYPos;
            NewsView.gbc.gridheight = 1;
            // Adds padding with insets
            NewsView.gbc.insets = new Insets(0, 20, 0, 0);

            String description = NewsController.processDescription(out, NewsView.feedURL).get(i);
            JLabel descText = new JLabel(description);
            NewsView.scrollPanel.add(descText, NewsView.gbc);
            descYPos += 2;

        }
    }
}
