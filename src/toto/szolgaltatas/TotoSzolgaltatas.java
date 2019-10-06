package toto.szolgaltatas;

import toto.tarolo.Eredmeny;
import toto.tarolo.Fordulo;
import toto.tarolo.Talalat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TotoSzolgaltatas {
    private List<Fordulo> fordulokLista;

    public void legnagyobbNyeremeny() {


        int legnagyobb = 0;
        for (int i = 0; i < fordulokLista.size(); i++) {
            List<Talalat> talalatok = fordulokLista.get(i).getTalalatok();
            for (int j = 0; j < talalatok.size(); j++) {
                if (talalatok.get(i).getNyeremeny() > legnagyobb) {
                    legnagyobb = talalatok.get(i).getNyeremeny();
                }
            }
        }
        System.out.printf("A legnagyobb  nyeremeny amit rogzitettek: %f Ft", legnagyobb);
    }

    public void beolvasas(String file) {
        this.fordulokLista = new ArrayList<>();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.d.");
            int talatSzam = 14;
            adatBeolvasas(file, formatter, talatSzam);
        } catch (FileNotFoundException ex) {
            System.err.printf("Hiba: %s", ex.getMessage());
        } catch (IOException ex) {
            System.err.printf("Hiba: %s", ex.getMessage());
        } catch (Exception e) {
            System.err.printf("Hiba: Exeption %s", e.getMessage());
        }
    }

    private void adatBeolvasas(String file, DateTimeFormatter formatter, int talatSzam) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String sor = br.readLine();
        while (sor != null) {
            String[] adatok = sor.split(";");
            int ev = Integer.parseInt(adatok[0]);
            int het = Integer.parseInt(adatok[1]);
            int forduloHet;
            forduloHet = getForduloHet(adatok[2]);
            // adatok[3].replace('.','-');
            LocalDate datum;
            datum = getDate(formatter, adatok[3], ev, het, forduloHet);
            List<Talalat> talalatokLita = new ArrayList<>();
            List<Eredmeny> eredmenyeklista = new ArrayList<>();
            setTalalatLista(talatSzam, adatok, talalatokLita);
            talatSzam--;
            setEredmenyLista(adatok, eredmenyeklista);
            Fordulo fordulo = getFordulo(ev, het, forduloHet, datum, talalatokLita, eredmenyeklista);
            this.fordulokLista.add(fordulo);
            sor = br.readLine();
        }
        br.close();
        fr.close();
    }

    private void setEredmenyLista(String[] adatok, List<Eredmeny> eredmenyeklista) {
        for (int i = 0; i < 13 + 1; i++) {
            switch (adatok[i + 6]) {
                case "1":
                    eredmenyeklista.add(Eredmeny._1);
                    break;
                case "2":
                    eredmenyeklista.add(Eredmeny._2);
                    break;
                case "X":
                    eredmenyeklista.add(Eredmeny.X);
                    break;
            }
        }
    }

    private void setTalalatLista(int talatSzam, String[] adatok, List<Talalat> talalatokLita) {
        for (int i = 0; i < 10; i += 2) {
            int nyertTalalatokSzama = Integer.parseInt(adatok[i + 4]);
            String ujadat = adatok[i + 5].replace(" Ft", "");
            ujadat = ujadat.replace(" ", "");
            int nyeremeny = Integer.parseInt(ujadat);
            talalatokLita.add(new Talalat(talatSzam, nyertTalalatokSzama, nyeremeny));

        }
    }

    private LocalDate getDate(DateTimeFormatter formatter, String s, int ev, int het, int forduloHet) {
        LocalDate datum;
        if (s.equals("")) {
            datum = LocalDate.of(ev, 1, 1).plusWeeks(het).plusDays(forduloHet - 1);
        } else {
            datum = LocalDate.parse(s, formatter);
        }
        return datum;
    }

    private int getForduloHet(String s) {
        int forduloHet;
        if (s.equals("")) {
            forduloHet = 1;
        } else {
            forduloHet = Integer.parseInt(s);
        }
        return forduloHet;
    }

    private Fordulo getFordulo(int ev, int het, int forduloHet, LocalDate datum, List<Talalat> talalatokLita, List<Eredmeny> eredmenyeklista) {
        Fordulo fordulo = new Fordulo();
        fordulo.setEv(ev);
        fordulo.setHet(het);
        fordulo.setForduloAHeten(forduloHet);
        fordulo.setDatum(datum);
        fordulo.setTalalatok(talalatokLita);
        fordulo.setEredmenyek(eredmenyeklista);
        return fordulo;
    }
}
