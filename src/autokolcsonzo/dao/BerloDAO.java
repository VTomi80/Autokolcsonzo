package autokolcsonzo.dao;

import autokolcsonzo.model.Berlo;
import autokolcsonzo.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BerloDAO {

    public void hozzaadBerlo(Berlo berlo) {
        String sql = "INSERT INTO berlok (nev, jogositvany_szam, telefonszam) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, berlo.getNev());
            stmt.setString(2, berlo.getJogositvanySzam());
            stmt.setString(3, berlo.getTelefonszam());

            stmt.executeUpdate();
            System.out.println("Bérlő sikeresen hozzáadva.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void torolBerlo(int berloId) {
        String sql = "DELETE FROM berlok WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, berloId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bérlő sikeresen törölve.");
            } else {
                System.out.println("Nincs ilyen bérlő a rendszerben.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
