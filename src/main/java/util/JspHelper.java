package util;

public class JspHelper {
    private static final String FORMAT = "\\WEB-INF\\%s.jsp";

    public static String get(String path) {
        return String.format(FORMAT, path);
    }
}
