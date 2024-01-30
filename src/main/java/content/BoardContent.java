package content;

import java.time.LocalDateTime;

public enum BoardContent {
    BOARD("<li>\n" +
                  "  <div class=\"wrap\">\n" +
                  "    <div class=\"main\">\n" +
                  "      <strong class=\"subject\">\n" +
                  "        <a href=\"./qna/show?boardId=%d\">%s</a>\n" +
                  "      </strong>\n" +
                  "      <div class=\"auth-info\">\n" +
                  "        <i class=\"icon-add-comment\"></i>\n" +
                  "        <span class=\"time\">%s</span>\n" +
                  "        <a href=\"./user/profile.html\" class=\"author\">%s</a>\n" +
                  "      </div>\n" +
                  "      <div class=\"reply\" title=\"댓글\">\n" +
                  "        <i class=\"icon-reply\"></i>\n" +
                  "        <span class=\"point\">0</span>\n" +
                  "      </div>\n" +
                  "    </div>\n" +
                  "  </div>\n" +
                  "</li>"),
    BOARD_DEFAIL("<header class=\"qna-header\">\n" +
            "              <h2 class=\"qna-title\">%s</h2>\n" +
            "          </header>\n" +
            "          <div class=\"content-main\">\n" +
            "              <article class=\"article\">\n" +
            "                  <div class=\"article-header\">\n" +
            "                      <div class=\"article-header-thumb\">\n" +
            "                          <img src=\"https://graph.facebook.com/v2.3/100000059371774/picture\" class=\"article-author-thumb\" alt=\"\">\n" +
            "                      </div>\n" +
            "                      <div class=\"article-header-text\">\n" +
            "                          <a href=\"/users/92/kimmunsu\" class=\"article-author-name\">%s</a>\n" +
            "                          <a href=\"/questions/413\" class=\"article-header-time\" title=\"퍼머링크\">\n" +
            "                              %s\n" +
            "                              <i class=\"icon-link\"></i>\n" +
            "                          </a>\n" +
            "                      </div>\n" +
            "                  </div>\n" +
            "                  <div class=\"article-doc\">\n" +
            "                      <p>%s</p>\n" +
            "                  </div>");
    private String text;
    BoardContent(String text) {
        this.text = text;
    }

    public String getText(String title, String name, String time, String contents) {
        return String.format(text, title, name, time, contents);
    }

    public String getText(long index, String title, String time, String username) {
        return String.format(text, index, title, time, username);
    }

}
