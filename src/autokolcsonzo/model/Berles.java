package autokolcsonzo.model;

import java.time.LocalDate;

public class Berles {
    private int id;
    private Auto auto;
    private Berlo berlo;
    private LocalDate kezdet;
    private int napok;
    private int ar;

    public Berles(int id, Auto auto, Berlo berlo, LocalDate kezdet, int napok, int ar) {
        this.id = id;
        this.auto = auto;
        this.berlo = berlo;
        this.kezdet = kezdet;
        this.napok = napok;
        this.ar = ar;
    }

    // Getterek és setterek
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Auto getAuto() { return auto; }
    public void setAuto(Auto auto) { this.auto = auto; }

    public Berlo getBerlo() { return berlo; }
    public void setBerlo(Berlo berlo) { this.berlo = berlo; }

    public LocalDate getKezdet() { return kezdet; }
    public void setKezdet(LocalDate kezdet) { this.kezdet = kezdet; }

    public int getNapok() { return napok; }
    public void setNapok(int napok) { this.napok = napok; }

    public int getAr() { return ar; }
    public void setAr(int ar) { this.ar = ar; }

    @Override
    public String toString() {
        return "Bérlés: " + berlo.getNev() + " → " + auto.getRendszam() +
                ", kezdete: " + kezdet + ", napok: " + napok + ", ár: " + ar + " Ft";
    }
}
