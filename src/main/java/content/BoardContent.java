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
                  "        <span class=\"point\">%s</span>\n" +
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
            "                  </div>"),
    BOARD_COMMENT("<article class=\"article\" id=\"answer-1405\">\n" +
            "                              <div class=\"article-header\">\n" +
            "                                  <div class=\"article-header-thumb\">\n" +
            "                                      <img src=\"https://graph.facebook.com/v2.3/1324855987/picture\" class=\"article-author-thumb\" alt=\"\">\n" +
            "                                  </div>\n" +
            "                                  <div class=\"article-header-text\">\n" +
            "                                      <a href=\"/users/1/자바지기\" class=\"article-author-name\">%s</a>\n" +
            "                                      <a href=\"#answer-1434\" class=\"article-header-time\" title=\"퍼머링크\">\n" +
            "                                          %s\n" +
            "                                      </a>\n" +
            "                                  </div>\n" +
            "                              </div>\n" +
            "                              <div class=\"article-doc comment-doc\">\n" +
            "                                  <p>%s</p>\n" +
            "                              </div>\n" +
            "                              <div class=\"article-utils\">\n" +
            "                                  <ul class=\"article-utils-list\">\n" +
            "                                      <li>\n" +
            "                                          <a class=\"link-modify-article\" href=\"/questions/413/answers/1405/form\">수정</a>\n" +
            "                                      </li>\n" +
            "                                      <li>\n" +
            "                                          <form class=\"delete-answer-form\" action=\"/questions/413/answers/1405\" method=\"POST\">\n" +
            "                                              <input type=\"hidden\" name=\"_method\" value=\"DELETE\">\n" +
            "                                              <button type=\"submit\" class=\"delete-answer-button\">삭제</button>\n" +
            "                                          </form>\n" +
            "                                      </li>\n" +
            "                                  </ul>\n" +
            "                              </div>\n" +
            "                          </article>"),
    BOARD_COUNT("<p class=\"qna-comment-count\"><strong>%s</strong>개의 의견</p>"),
    COMMENT_FORM("<form class=\"answer-form\" method=\"post\" action=\"/board/comment?boardId=%s\">\n" +
            "                              <div class=\"form-group\" style=\"padding:14px;\">\n" +
            "                                  <textarea class=\"form-control\" name=\"comment\" id=\"comment\" placeholder=\"Update your status\"></textarea>\n" +
            "                              </div>\n" +
            "                              <button class=\"btn btn-success pull-right\" type=\"submit\">답변하기</button>\n" +
            "                              <div class=\"clearfix\" />\n" +
            "                          </form>");

    private String text;
    BoardContent(String text) {
        this.text = text;
    }

    public String getText(int count) {
        return String.format(text, count);
    }

    public String getText(long index) {
        return String.format(text, index);
    }

    public String getText(String name, String time, String content) {
        return String.format(text, name, time, content);
    }

    public String getText(String title, String name, String time, String contents) {
        return String.format(text, title, name, time, contents);
    }

    public String getText(long index, String title, String time, String username, long id) {
        return String.format(text, index, title, time, username, id);
    }

}
