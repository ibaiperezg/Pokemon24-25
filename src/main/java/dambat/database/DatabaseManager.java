package dambat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:nombres.db";

    // Datu-basea sortu, existitzen ez bada
    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement stmt = conn.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS jugadores (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)")) {
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("❌ ERROREA datu-basea sortzean: " + e.getMessage());
        }
    }

    public static void verificarBaseDeDatos() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table'";
        
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            System.out.println("📂 Datu-baseko taulak:");
            while (rs.next()) {
                System.out.println("🔹 " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("❌ ERROREA datu-basea egiaztatzean: " + e.getMessage());
        }
    }
    
    public static void createRankingTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ranking ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "nombre TEXT NOT NULL, "
                   + "tiempo REAL NOT NULL)";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ 'ranking' taula egiaztatu edo behar bezala sortu da.");
        } catch (SQLException e) {
            System.out.println("❌ ERROREA 'ranking' taula sortzean: " + e.getMessage());
        }
    }
    
    // Izena datu-basean gorde
    public static void saveName(String nombre) {
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO jugadores (nombre) VALUES (?)")) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
            System.out.println("✅ Izena datu-basean gorde da.");
        } catch (SQLException e) {
            System.out.println("❌ ERROREA izena gordetzean: " + e.getMessage());
        }
    }

    // Azken gordetako izena berreskuratu
    public static String getLastSavedName() {
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement stmt = conn.prepareStatement("SELECT nombre FROM jugadores ORDER BY id DESC LIMIT 1");
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.out.println("❌ ERROREA izena berreskuratzean: " + e.getMessage());
        }
        return null;
    }

    // **Denbora datu-basean gorde**
    public static void guardarTiempo(String nombre, double tiempo) {
        String sql = "INSERT INTO ranking (nombre, tiempo) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, tiempo);
            pstmt.executeUpdate();
            System.out.println("✅ Denbora datu-basean gorde da: " + nombre + " - " + tiempo + "s");
        } catch (SQLException e) {
            System.out.println("❌ ERROREA denbora gordetzean: " + e.getMessage());
        }
    }

    // **5 denbora onenak lortu ordenatuta**
    public static List<String> obtenerTopTiempos() {
        List<String> topTiempos = new ArrayList<>();
        String sql = "SELECT nombre, tiempo FROM ranking ORDER BY tiempo ASC LIMIT 5";
        try (Connection conn = DriverManager.getConnection(URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double tiempo = rs.getDouble("tiempo");
                topTiempos.add(nombre + " - " + tiempo + "s");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topTiempos;
    }
}
