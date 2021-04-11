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

public class ParseHelper {
    public List<Post> getPostsList() {
        List<Post> postsList = new ArrayList<>();
        try {
            for (int i = 1; i < 2; i++) {
                String url = String.format("https://www.sql.ru/forum/job-offers/%d", i);
                Document doc = Jsoup.connect(url).get();
                Elements row = doc.select(".postslisttopic");
                Elements col = doc.select(".altCol");
                int dateIndex = 1;
                for (Element td : row) {
                    Post temp = getPost(td, col, dateIndex);
                    postsList.add(temp);
                    dateIndex += 2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postsList;
    }

    private Post getPost(Element td, Elements col, int dateIndex) throws IOException {
        Element href = td.child(0);
        String link = href.attr("href");
        String name = href.text();
        int id = getId(link);
        String text = getText(link);
        LocalDateTime created = getCreated(col, dateIndex);
        return new Post(id, name, text, link, created);
    }

    private String getText(String link) throws IOException {
        Document doc2 = Jsoup.connect(link).get();
        Elements row2 = doc2.select(".msgTable");
        return row2.get(0)
                .child(0)
                .child(1)
                .child(1)
                .text();
    }

    private LocalDateTime getCreated(Elements col, int dateIndex) {
        String dateToParse = col.get(dateIndex)
                .textNodes().get(0)
                .toString();
        return new SqlRuDateTimeParser().parse(dateToParse);
    }

    private int getId(String link) {
        String[] splitForId = link.split("/");
        return Integer.parseInt(splitForId[4]);
    }
}
