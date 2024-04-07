import util.ConnectionManager;

import java.sql.Connection;

public class main {
    public static void main(String[] args) {
        Connection connection = ConnectionManager.getConnection();
        if (connection == null) {
            System.out.println("no");
        } else {
            System.out.println("yes");
        }
    }
}
