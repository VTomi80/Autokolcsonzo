package autokolcsonzo.dao;

import autokolcsonzo.model.Berles;
import autokolcsonzo.model.BerlesListaElem;
import autokolcsonzo.model.Berlo;
import autokolcsonzo.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BerlesDAO {

    public void hozzaadBerles(Berles berles) {
        //új bérlő hozzáadása
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

    public boolean autoElerheto(int autoId, LocalDate kezdete, int napok) {
        //A rendelés felvitelénél ezzel a metódussal ellenőrizzük, hogy a megadott autó elérhető-e ebben az időintervallumban
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


    public List<BerlesListaElem> listazBerleseket() {
        //A riportok ablak Bérlések listázása gombja által meghívott metódus
        List<BerlesListaElem> lista = new ArrayList<>();

        String sql = """
            SELECT b.nev, a.rendszam, br.kezdet, br.napok, br.ar
            FROM berlesek br
            JOIN berlok b ON br.berlo_id = b.id
            JOIN autok a ON br.auto_id = a.id
            ORDER BY br.kezdet;
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nev = rs.getString("nev");
                String rendszam = rs.getString("rendszam");
                LocalDate kezdet = rs.getDate("kezdet").toLocalDate();
                int napok = rs.getInt("napok");
                int ar = rs.getInt("ar");

                lista.add(new BerlesListaElem(nev, rendszam, kezdet, napok, ar));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void torolBerles(BerlesListaElem elem) {
        //A listában megjelölt rekord törlése a táblából
        String sql = """
        DELETE FROM berlesek
        WHERE auto_id = (SELECT id FROM autok WHERE rendszam = ?)
          AND berlo_id = (SELECT id FROM berlok WHERE nev = ?)
          AND kezdet = ?
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, elem.getRendszam());
            stmt.setString(2, elem.getBerloNev());
            stmt.setDate(3, Date.valueOf(elem.getKezdet()));

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Nem történt törlés, nem talált rekord.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

