package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.SqlRuDateTimeParser;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 6; i++) {
            String url = String.format("https://www.sql.ru/forum/job-offers/%d", i);
            Document doc = Jsoup.connect(url).get();
            Elements row = doc.select(".postslisttopic");
            Elements col = doc.select(".altCol");
            int dateIndex = 1;
            for (Element td : row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                String dateToParse = col.get(dateIndex).textNodes().get(0).toString();
                System.out.println(new SqlRuDateTimeParser().parse(dateToParse));
                dateIndex += 2;
            }
        }
    }
}
