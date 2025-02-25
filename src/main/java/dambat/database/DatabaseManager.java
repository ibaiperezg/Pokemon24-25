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

    // Crear la base de datos si no existe
    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement stmt = conn.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS jugadores (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)")) {
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("❌ ERROR al crear la base de datos: " + e.getMessage());
        }
    }
    public static void verificarBaseDeDatos() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table'";
        
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            System.out.println("📂 Tablas en la base de datos:");
            while (rs.next()) {
                System.out.println("🔹 " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("❌ ERROR al verificar la base de datos: " + e.getMessage());
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
            System.out.println("✅ Tabla 'ranking' verificada o creada correctamente.");
        } catch (SQLException e) {
            System.out.println("❌ ERROR al crear la tabla 'ranking': " + e.getMessage());
        }
    }
    

    // Guardar el nombre en la base de datos
    public static void saveName(String nombre) {
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO jugadores (nombre) VALUES (?)")) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
            System.out.println("✅ Nombre guardado en la base de datos.");
        } catch (SQLException e) {
            System.out.println("❌ ERROR al guardar el nombre: " + e.getMessage());
        }
    }

    // Recuperar el último nombre guardado
    public static String getLastSavedName() {
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement stmt = conn.prepareStatement("SELECT nombre FROM jugadores ORDER BY id DESC LIMIT 1");
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.out.println("❌ ERROR al recuperar el nombre: " + e.getMessage());
        }
        return null;
    }

    // **Guardar el tiempo en la base de datos**
    public static void guardarTiempo(String nombre, double tiempo) {
        String sql = "INSERT INTO ranking (nombre, tiempo) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, tiempo);
            pstmt.executeUpdate();
            System.out.println("✅ Tiempo guardado en la base de datos: " + nombre + " - " + tiempo + "s");
        } catch (SQLException e) {
            System.out.println("❌ ERROR al guardar el tiempo: " + e.getMessage());
        }
    }

    // **Obtener los 5 mejores tiempos ordenados**
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
