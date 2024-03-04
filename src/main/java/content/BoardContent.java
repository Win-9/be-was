package content;

public enum BoardContent {
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
}
