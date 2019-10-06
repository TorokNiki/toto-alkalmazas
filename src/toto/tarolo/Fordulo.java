package toto.tarolo;


import java.time.LocalDate;
import java.util.List;

public class Fordulo {
    private int ev;
    private int het;
    private int forduloAHeten;
    private LocalDate datum;
    private List<Eredmeny> eredmenyekLista;
    private List<Talalat> talalatokLista;

    public Fordulo() {
    }

    public int getEv() {
        return ev;
    }

    public void setEv(int ev) {
        this.ev = ev;
    }

    public int getHet() {
        return het;
    }

    public void setHet(int het) {
        this.het = het;
    }

    public int getForduloAHeten() {
        return forduloAHeten;
    }

    public void setForduloAHeten(int forduloAHeten) {
        this.forduloAHeten = forduloAHeten;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public List<Talalat> getTalalatok() {
        return talalatokLista;
    }

    public void setTalalatok(List<Talalat> talalat) {
        this.talalatokLista = talalat;
    }

    public List<Eredmeny> getEredmenyek() {
        return eredmenyekLista;
    }

    public void setEredmenyek(List<Eredmeny> eredmenyek) {
        this.eredmenyekLista = eredmenyek;
    }
}
