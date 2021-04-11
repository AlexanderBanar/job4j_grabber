package html;

import java.util.List;

public class SqlRuParse {
    public static void main(String[] args) {
        List<Post> postsList = new ParseHelper().getPostsList();
        for (Post p : postsList) {
            System.out.println(p.getId());
        }
    }
}
