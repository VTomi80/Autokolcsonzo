package autokolcsonzo.dao;

import autokolcsonzo.model.Berles;
import autokolcsonzo.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BerlesDAO {

    public void hozzaadBerles(Berles berles) {
        String sql = "INSERT INTO berlesek (auto_id, berlo_id, kezdet, napok, ar) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, berles.getAuto().getId());
            stmt.setInt(2, berles.getBerlo().getId());
            stmt.setDate(3, java.sql.Date.valueOf(berles.getKezdet()));
            stmt.setInt(4, berles.getNapok());
            stmt.setInt(5, berles.getAr());

            stmt.executeUpdate();
            System.out.println("Bérlés sikeresen hozzáadva.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean autoElérheto(int autoId, LocalDate kezdete, int napok) {
        String sql = "SELECT * FROM berlesek WHERE auto_id = ? AND " +
                "(kezdet <= ? AND ? <= (kezdet + INTERVAL '1 day' * napok))";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, autoId);
            stmt.setDate(2, java.sql.Date.valueOf(kezdete));
            stmt.setDate(3, java.sql.Date.valueOf(kezdete.plusDays(napok)));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Az autó már lefoglalt ezen a napon.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Az autó elérhető a megadott időpontban.");
        return true;
    }



}
