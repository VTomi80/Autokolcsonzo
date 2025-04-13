package autokolcsonzo.model;

public class Auto {
    private int id;
    private String tipus;
    private String rendszam;
    private int dij;
    private Integer utasSzam;
    private Integer teherbiras;
    private String uzemanyag;
    private Boolean billent;
    private boolean elerheto;

    //Auto osztály
    public Auto(int id, String tipus, String rendszam, int dij, Integer utasSzam,
                Integer teherbiras, String uzemanyag, Boolean billent, boolean elerheto) {
        this.id = id;
        this.tipus = tipus;
        this.rendszam = rendszam;
        this.dij = dij;
        this.utasSzam = utasSzam;
        this.teherbiras = teherbiras;
        this.uzemanyag = uzemanyag;
        this.billent = billent;
        this.elerheto = elerheto;
    }

    // Getterek és setterek
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipus() { return tipus; }
    public void setTipus(String tipus) { this.tipus = tipus; }

    public String getRendszam() { return rendszam; }
    public void setRendszam(String rendszam) { this.rendszam = rendszam; }

    public int getDij() { return dij; }
    public void setDij(int dij) { this.dij = dij; }

    public Integer getUtasSzam() { return utasSzam; }
    public void setUtasSzam(Integer utasSzam) { this.utasSzam = utasSzam; }

    public Integer getTeherbiras() { return teherbiras; }
    public void setTeherbiras(Integer teherbiras) { this.teherbiras = teherbiras; }

    public String getUzemanyag() { return uzemanyag; }
    public void setUzemanyag(String uzemanyag) { this.uzemanyag = uzemanyag; }

    public Boolean getBillent() { return billent; }
    public void setBillent(Boolean billent) { this.billent = billent; }

    public boolean isElerheto() { return elerheto; }
    public void setElerheto(boolean elerheto) { this.elerheto = elerheto; }

    @Override
    public String toString() {
        return tipus + " (" + rendszam + ") - " + dij + " Ft/nap";
    }
}
