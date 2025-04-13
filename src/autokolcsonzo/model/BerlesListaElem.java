package autokolcsonzo.model;

import java.time.LocalDate;

public class BerlesListaElem {
    private String berloNev;
    private String rendszam;
    private LocalDate kezdet;
    private LocalDate vege;
    private int napok;
    private int ar;

    //Berlse listaelem osztály. Azért szükséges még egy osztály felépítése, mert a Bérlések listázása táblázatban nem csak a
    //berles táblát használjuk, hanem a három táblából fésülünk össze adatokat, hogy olvasható, érthető formában jelenjenek meg
    //az adatok. Pl. ne berlo_id szerepeljen benne, hanem a bérlő neve.

    public BerlesListaElem(String berloNev, String rendszam, LocalDate kezdet, int napok, int ar) {
        this.berloNev = berloNev;
        this.rendszam = rendszam;
        this.kezdet = kezdet;
        this.napok = napok;
        this.ar = ar;
        this.vege = kezdet.plusDays(napok); // automatikus vége
    }

    //Getterek, setterek
    public String getBerloNev() { return berloNev; }
    public String getRendszam() { return rendszam; }
    public LocalDate getKezdet() { return kezdet; }
    public LocalDate getVege() { return vege; }
    public int getNapok() { return napok; }
    public int getAr() { return ar; }
}
