package mod13_5190411485;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class KoneksiDB {
    private static Connection koneksi;
    
    public static Connection getKoneksi()
    {
        if(koneksi == null)
        {
            try {
                String url = "jdbc:mysql://localhost:3306/db_mhs";
                String username = "root";
                String password = "";
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                
                koneksi = DriverManager.getConnection(url, username, password);
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return koneksi;
    }
}
