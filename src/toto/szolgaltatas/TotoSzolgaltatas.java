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

    public void tipp(LocalDate bedatum, String tipp) {
        int eltalalt = 0;
        int tippIndex = 0;
        int nyeremeny = 0;
        int tippNyeremenye = 0;
        int azonosTippelokSzama = 0;
        eltalalt = getEltalaltTalalatokSzama(bedatum, tipp, eltalalt, tippIndex);
        for (int i = 0; i < fordulokLista.size(); i++) {
            if (fordulokLista.get(i).getDatum().equals(bedatum)) {
                List<Talalat> talalatok = fordulokLista.get(i).getTalalatok();
                for (int j = 0; j < talalatok.size(); j++) {
                    // nyeremeny += talalatok.get(j).getNyeremeny();

                    if (talalatok.get(j).getTalalatokSzama() == eltalalt) {
                        //  azonosTippelokSzama = talalatok.get(j).getNyertTalalatokSzama();
                        tippNyeremenye = talalatok.get(j).getNyeremeny();
                    }
                }
            }
        }
        // nyeremeny /= 100;//1% nyeremény
        // tippNyeremenye = getTippNyeremenye(eltalalt, nyeremeny, tippNyeremenye, azonosTippelokSzama);
        System.out.printf("Eredmeny: talalat: %d, nyeremeny: %,8d Ft", eltalalt, tippNyeremenye);
    }

    private int getTippNyeremenye(int eltalalt, int nyeremeny, int tippNyeremenye, int azonosTippelokSzama) {
        if (eltalalt == 14) {
            if (azonosTippelokSzama > 0) {
                tippNyeremenye = nyeremeny * 37 / azonosTippelokSzama;

            } else {
                tippNyeremenye = nyeremeny * 37;

            }
        } else if (eltalalt == 13) {
            if (azonosTippelokSzama > 0) {
                tippNyeremenye = nyeremeny * 16 / azonosTippelokSzama;

            } else {
                tippNyeremenye = nyeremeny * 16;

            }
        } else if (eltalalt == 12) {
            if (azonosTippelokSzama > 0) {
                tippNyeremenye = nyeremeny * 12 / azonosTippelokSzama;
            } else {
                tippNyeremenye = nyeremeny * 12;
            }
        } else if (eltalalt == 11) {
            if (azonosTippelokSzama > 0) {
                tippNyeremenye = nyeremeny * 12 / azonosTippelokSzama;
            } else {
                tippNyeremenye = nyeremeny * 12;
            }
        } else if (eltalalt == 10) {
            if (azonosTippelokSzama > 0) {
                tippNyeremenye = nyeremeny * 23 / azonosTippelokSzama;
            } else {
                tippNyeremenye = nyeremeny * 23;
            }
        }
        return tippNyeremenye;
    }

    private int getEltalaltTalalatokSzama(LocalDate bedatum, String tipp, int eltalalt, int tippIndex) {
        for (int i = 0; i < fordulokLista.size(); i++) {
            if (fordulokLista.get(i).getDatum().equals(bedatum)) {
                List<Eredmeny> eredmenyek = fordulokLista.get(i).getEredmenyek();
                List<Eredmeny> tippEredmeny = new ArrayList<>();
                for (int j = 0; j < eredmenyek.size(); j++) {
                    switch (tipp.charAt(tippIndex)) {
                        case '1':
                            tippEredmeny.add(Eredmeny._1);
                            break;
                        case '2':
                            tippEredmeny.add(Eredmeny._2);
                            break;
                        case 'x':
                        case 'X':
                            tippEredmeny.add(Eredmeny.X);
                            break;
                    }
                    tippIndex++;
                    if (eredmenyek.get(j).equals(tippEredmeny.get(j))) {
                        eltalalt++;
                    }
                }
            }
        }
        return eltalalt;
    }

    public void statisztika() {
        double osszDB = 0;
        double hazaiDB = 0;
        double dontetlenDB = 0;
        double vendegDB = 0;
        double hazaiSzazalek = 0, vendegSzazalek = 0, dontetlenSzazalek = 0;
        for (int i = 0; i < fordulokLista.size(); i++) {
            List<Eredmeny> eredmenyek = fordulokLista.get(i).getEredmenyek();
            for (int j = 0; j < eredmenyek.size(); j++) {
                switch (eredmenyek.get(j)) {
                    case _1:
                        hazaiDB++;
                        break;
                    case _2:
                        vendegDB++;
                        break;
                    case X:

                        dontetlenDB++;
                        break;
                }
                osszDB++;
            }
        }
        hazaiSzazalek = hazaiDB / osszDB * 100;
        vendegSzazalek = vendegDB / osszDB * 100;
        dontetlenSzazalek = dontetlenDB / osszDB * 100;

        System.out.printf("Statisztika: #1 csapat nyert: %.2f %%, #2 csapat nyert: %.2f %%, dontetlen: %.2f %%\n", hazaiSzazalek, vendegSzazalek, dontetlenSzazalek);
    }

    public void legnagyobbNyeremeny() {
        int legnagyobb = 0;
        for (int i = 0; i < fordulokLista.size(); i++) {
            List<Talalat> talalatok = fordulokLista.get(i).getTalalatok();
            for (int j = 0; j < talalatok.size(); j++) {
                if (talalatok.get(j).getNyeremeny() > legnagyobb) {
                    legnagyobb = talalatok.get(j).getNyeremeny();
                }
            }
        }
        System.out.printf("A legnagyobb  nyeremeny amit rogzitettek: %,8d Ft\n", legnagyobb);
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
        int sorok = 0;
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
            setEredmenyLista(adatok, eredmenyeklista);
            Fordulo fordulo = getFordulo(ev, het, forduloHet, datum, talalatokLita, eredmenyeklista);
            this.fordulokLista.add(fordulo);
            sorok++;
            sor = br.readLine();
        }
        //System.out.println(sorok);
        br.close();
        fr.close();
    }

    private void setEredmenyLista(String[] adatok, List<Eredmeny> eredmenyeklista) {
        for (int i = 0; i < 14; i++) {
            switch (adatok[i + 14]) {
                case "1":
                case "+1":
                    eredmenyeklista.add(Eredmeny._1);
                    break;
                case "2":
                case "+2":
                    eredmenyeklista.add(Eredmeny._2);
                    break;
                case "X":
                case "+X":
                case "x":
                case "+x":
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
            talatSzam--;

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
