package autokolcsonzo.dao;

import autokolcsonzo.model.Auto;
import autokolcsonzo.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoDAO {

    public void hozzaadAuto(Auto auto) {
        String sql = "INSERT INTO autok (tipus, rendszam, dij, utas_szam, teherbiras, uzemanyag, billent, elerheto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, auto.getTipus());
            stmt.setString(2, auto.getRendszam());
            stmt.setInt(3, auto.getDij());
            stmt.setObject(4, auto.getUtasSzam(), Types.INTEGER);
            stmt.setObject(5, auto.getTeherbiras(), Types.INTEGER);
            stmt.setString(6, auto.getUzemanyag());
            stmt.setObject(7, auto.getBillent(), Types.BOOLEAN);
            stmt.setBoolean(8, auto.isElerheto());

            stmt.executeUpdate();
            System.out.println("Autó sikeresen hozzáadva!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Auto> listazAutokat() {
        List<Auto> autok = new ArrayList<>();
        String sql = "SELECT * FROM autok";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Auto auto = new Auto(
                        rs.getInt("id"),
                        rs.getString("tipus"),
                        rs.getString("rendszam"),
                        rs.getInt("dij"),
                        (Integer) rs.getObject("utas_szam"),
                        (Integer) rs.getObject("teherbiras"),
                        rs.getString("uzemanyag"),
                        (Boolean) rs.getObject("billent"),
                        rs.getBoolean("elerheto")
                );
                autok.add(auto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autok;
    }
    public void torolAuto(int autoId) {
        String sql = "DELETE FROM autok WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, autoId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Autó sikeresen törölve.");
            } else {
                System.out.println("Nincs ilyen autó a rendszerben.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
