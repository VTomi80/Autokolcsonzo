package autokolcsonzo;

import autokolcsonzo.dao.AutoDAO;
import autokolcsonzo.model.Auto;

public class Main {
    public static void main(String[] args) {
        // Teszteljük az adatbázishoz való kapcsolódást és a DAO műveleteket

        AutoDAO autoDAO = new AutoDAO();

        // Teszt: új autó hozzáadása
        Auto auto = new Auto(0, "Toyota Corolla", "ABC-123", 12000, 5, 1000, "Benzin", true, true);
        autoDAO.hozzaadAuto(auto);
        System.out.println("Autó hozzáadva.");

        // Teszt: összes autó lekérdezése
        System.out.println("Összes autó:");
        autoDAO.listazAutokat().forEach(System.out::println);
    }
}
