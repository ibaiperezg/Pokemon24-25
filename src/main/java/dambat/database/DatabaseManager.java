package dambat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
