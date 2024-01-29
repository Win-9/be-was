package content;

public enum BoardContent {
    BOARD("<li>\n" +
                  "  <div class=\"wrap\">\n" +
                  "    <div class=\"main\">\n" +
                  "      <strong class=\"subject\">\n" +
                  "        <a href=\"./qna/show.html\">%s</a>\n" +
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
                  "</li>");
    private String text;
    BoardContent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getText(String username) {
        return String.format(text, username);
    }

    public String getText(int index, String id, String name, String email) {
        return String.format(text, index, id, name, email);
    }
}
