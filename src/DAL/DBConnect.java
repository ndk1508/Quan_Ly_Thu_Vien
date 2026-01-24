package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/ql_thuvien?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "";

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối CSDL thành công!");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");	
            e.printStackTrace();
        }
        return conn;
    }
}
