package html;

import java.util.List;

public class SqlRuParse {
    public static void main(String[] args) {
        String url = "https://www.sql.ru/forum/job-offers/1";
        List<Post> postsList = new ParseHelper().list(url);
        for (Post p : postsList) {
            System.out.println(p.getId());
        }
    }
}
