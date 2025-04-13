package autokolcsonzo.dao;

import autokolcsonzo.model.Auto;
import autokolcsonzo.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoDAO {

    public void hozzaadAuto(Auto auto) {
        //új autó hozzáadása
        String sql = "INSERT INTO autok (tipus, rendszam, dij, utas_szam, teherbiras, uzemanyag, billent, elerheto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection(); //kapcsolódás az adatbázishoz a DBUtil osztály getConnection metódusával
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

    public List<Auto> listazAutok() { //Autó objektumok elehelyézse a listában
        List<Auto> autok = new ArrayList<>(); //Készítek egy listát, aminek minden eleme egy Auto objektum
        String sql = "SELECT * FROM autok"; //sql query definiálása: minden rekordot kiolvasunk az autók táblából

        try (Connection conn = DBUtil.getConnection(); //kapcsolódás az adatbázishoz
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) { //SQL query indítása

            while (rs.next()) { //Addig megy a ciklus, amíg talál rekordot az Autok táblában
                Auto auto = new Auto( //elhelyezzük egy auto nevű objetkumban a tábla aktuális rekordjának megfelelő mezőit
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
                autok.add(auto); //az auto objektumot - a tábla mezőinek értékeivel - betesszük az Autok listába
            }

        } catch (SQLException e) {
            e.printStackTrace(); //kivételkezelés, hiba esetére
        }

        return autok;
    }
    public void torolAuto(int autoId) { //Autok táblából redkord törlése
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

    public List<String> listazEgyediTipusokat() {
        //Egyedi típusok listázása, a főképernyőn lévő autótípusok combo box-hoz szükséges, hogy minden táblában szereplő típus csak egyszer szerepeljen

        List<String> tipusok = new ArrayList<>();

        String sql = "SELECT DISTINCT tipus FROM autok ORDER BY tipus";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tipusok.add(rs.getString("tipus"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipusok;
    }

    public boolean vanIlyenRendszam(String rendszam) {
        //Új autó felvitelénél ellenőrizzük, hogy a megadott rendszám nem létezik-e már a táblában
        String sql = "SELECT 1 FROM autok WHERE rendszam = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rendszam);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // van ilyen rendszám
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



}
