/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseConnection;
import java.sql.*;

/**
 *
 * @author USER
 */
public class DatabaseConnection {
    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/inventory_pabrik_helm", "root", ""); // Ubah sesuai
            System.out.println("Koneksi berhasil");
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}