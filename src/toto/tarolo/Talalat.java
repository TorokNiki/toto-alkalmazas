package toto.tarolo;

public class Talalat {
    private int talalatokSzama;
    private int nyertTalalatokSzama;
    private int nyeremeny;

    public Talalat(int talalatokSzama, int nyertTalalatokSzama, int nyeremeny) {
        this.talalatokSzama = talalatokSzama;
        this.nyertTalalatokSzama = nyertTalalatokSzama;
        this.nyeremeny = nyeremeny;
    }

    public int getTalalatokSzama() {
        return talalatokSzama;
    }

    public int getNyertTalalatokSzama() {
        return nyertTalalatokSzama;
    }

    public int getNyeremeny() {
        return nyeremeny;
    }
}
