package content;

public enum UserContent {
    NON_LOGIN("<li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>\n" +
            "<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>"),
    LOGIN("<li><a href=\"../user/logout\" role=\"button\">로그아웃</a></li>\n" +
            "<li><a href=\"#\" role=\"button\">%s 님</a></li>\n" +
            "<li><a href=\"../user/profile.html\" role=\"button\">개인정보</a></li>\n"),
    USER_PROFILE("<div class=\"media\">\n" +
            "                        <a class=\"thumbnail pull-left\" href=\"#\">\n" +
            "                            <img class=\"media-object\" src=\"../images/80-text.png\">\n" +
            "                        </a>\n" +
            "                        <div class=\"media-body\">\n" +
            "                            <h4 class=\"media-heading\">%s</h4>\n" +
            "                            <p>\n" +
            "                                <a href=\"#\" class=\"btn btn-xs btn-default\"><span class=\"glyphicon glyphicon-envelope\"></span>&nbsp;%s</a>\n" +
            "                            </p>\n" +
            "                        </div>\n" +
            "                    </div>"),
    USER_LIST("<tr>\n" +
            "<th scope=\"row\">%s</th> " +
            "<td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
            "</tr>");
    private String text;
    UserContent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getText(String username, String email) {
        return String.format(text, username, email);
    }

    public String getText(String username) {
        return String.format(text, username);
    }

    public String getText(int index, String id, String name, String email) {
        return String.format(text, index, id, name, email);
    }
}
