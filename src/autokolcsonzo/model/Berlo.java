package autokolcsonzo.model;

public class Berlo {
    private int id;
    private String nev;
    private String jogositvanySzam;
    private String telefonszam;
    //Berlo osztály

    public Berlo(int id, String nev, String jogositvanySzam, String telefonszam) {
        this.id = id;
        this.nev = nev;
        this.jogositvanySzam = jogositvanySzam;
        this.telefonszam = telefonszam;
    }

    // Getterek, setterek
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public String getJogositvanySzam() { return jogositvanySzam; }
    public void setJogositvanySzam(String jogositvanySzam) { this.jogositvanySzam = jogositvanySzam; }

    public String getTelefonszam() { return telefonszam; }
    public void setTelefonszam(String telefonszam) { this.telefonszam = telefonszam; }

    @Override
    public String toString() {
        return nev + " (" + jogositvanySzam + ")";
    }
}
