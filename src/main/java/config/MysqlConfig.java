package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {
    public static final String url = "jdbc:mysql://localhost:3306/Quan_Ly_Thi_Trac_Nghiem";
    public static final String username ="root";

    public static final String password = "your_password_here";

    public static Connection getConnection(){
        Connection connection = null;
        try {
//            chỉ định driver sử dụng
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
        }catch (Exception e){
            System.out.println("Lỗi kết nối cơ sở dữ liệu " + e.getMessage());
        }
        return connection;
    }

    public static boolean testConnection() {
        Connection connection = null;
        try {
            connection = getConnection();
            if (connection != null) {
                connection.close();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error testing database connection: " + e.getMessage());
        }
        return false;
    }
}
