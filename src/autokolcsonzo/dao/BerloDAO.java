package autokolcsonzo.dao;

import autokolcsonzo.model.Berlo;
import autokolcsonzo.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BerloDAO {

    public void hozzaadBerlo(Berlo berlo) {
        //új bérlő hozzáadása
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
        //A listában kijelölt bérlő törlése a táblából
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

    public List<Berlo> listazBerlok() {
        //Bérlők listázása, a riportok ablak Bérlők listázása gombja által meghívott metódus
        List<Berlo> berlok = new ArrayList<>();

        System.out.println("Lekérdezett bérlők száma: " + berlok.size());

        String sql = "SELECT * FROM berlok ORDER BY id";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nev = rs.getString("nev");
                String jogsi = rs.getString("jogositvany_szam");
                String tel = rs.getString("telefonszam");

                berlok.add(new Berlo(id, nev, jogsi, tel));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return berlok;

    }

    public List<String> listazJogositvanyokatNevAlapjan(String nev) {
        //Jogosítványszámok keresése a főmenűben megadott név alapján a Bérlők táblában. Azért szükséges, hogy ne tudjunk rossz hogosítványszámot
        //megadni, hanem csak a bérlők táblába már felvitt személyek közül lehessen választani
        List<String> lista = new ArrayList<>();
        String sql = "SELECT jogositvany_szam FROM berlok WHERE nev = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nev);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("jogositvany_szam"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Berlo keresBerloNevEsJogsiAlapjan(String nev, String jogsi) {
        //KEreső metódus az ellenőrzéshez a főképernyőn
        String sql = "SELECT * FROM berlok WHERE nev = ? AND jogositvany_szam = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nev);
            stmt.setString(2, jogsi);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Berlo(
                        rs.getInt("id"),
                        rs.getString("nev"),
                        rs.getString("jogositvany_szam"),
                        rs.getString("telefonszam")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean vanIlyenJogsi(String jogsiSzam) {
        //Ellenőrző metódus az ellenőrzéshez a főképernyőn
        String sql = "SELECT 1 FROM berlok WHERE jogositvany_szam = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jogsiSzam);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // van ilyen jogosítványszám

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
