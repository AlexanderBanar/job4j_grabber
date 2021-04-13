package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import com.ibm.icu.text.Transliterator;

public class ParseHelper implements Parse {
    private SqlRuDateTimeParser timeParser = new SqlRuDateTimeParser();
    private Document docFromOneLink;

    @Override
    public List<Post> list(String link) {
        List<Post> postsList = new ArrayList<>();
        try {
            Document sitePage = Jsoup.connect(link).get();
            Elements sitePageLinksWithText = sitePage.select(".postslisttopic");
            for (Element linkWithText : sitePageLinksWithText) {
                String pureLink = extractLink(linkWithText);
                Post postFromALink = detail(pureLink);
                postsList.add(postFromALink);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postsList;
    }

    @Override
    public Post detail(String link) {
        String[] linkSplit = link.split("/");
        int id = Integer.parseInt(linkSplit[4]);
        String name = toCyrillicTrans(linkSplit[5]);
        String text = null;
        LocalDateTime created = null;
        try {
            text = getText(link);
            created = getCreated(link);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Post(id, name, text, link, created);
    }

    private String toCyrillicTrans(String latinText) {
        var cyrillicToLatin = "Latin-Russian/BGN";
        Transliterator toCyrillicTrans = Transliterator.getInstance(cyrillicToLatin);
        return toCyrillicTrans.transliterate(latinText);
    }

    private String extractLink(Element linkWithText) {
        Element href = linkWithText.child(0);
        return href.attr("href");
    }

    private LocalDateTime getCreated(String link) throws IOException {
        Elements targetSearchZone = this.docFromOneLink.select(".msgTable");
        String dateMessedWithText = targetSearchZone.get(0)
                .child(0)
                .child(2)
                .text();
        return this.timeParser.parse(dateExtraction(dateMessedWithText));
    }

    private String dateExtraction(String dateMessedWithText) {
        String[] dateAndTimeSplit = dateMessedWithText.split(",");
        String date = dateAndTimeSplit[0];
        String[] timeWithText = dateAndTimeSplit[1].split(" ");
        String time = timeWithText[1];
        return new StringBuilder(date)
                .append(", ")
                .append(time)
                .toString();
    }

    private String getText(String link) throws IOException {
        this.docFromOneLink = Jsoup.connect(link).get();
        Elements row = this.docFromOneLink.select(".msgTable");
        return row.get(0)
                .child(0)
                .child(1)
                .child(1)
                .text();
    }
}
