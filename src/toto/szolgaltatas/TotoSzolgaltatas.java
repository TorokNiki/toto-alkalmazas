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
    public void beolvasas(String file){
           this.fordulokLista= new ArrayList<>();
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.d.");
                int talatSzam=14;
                FileReader fr= new FileReader(file);
                BufferedReader br= new BufferedReader(fr);
                String sor= br.readLine();
                int sdfg=0;
                while (sor!=null) {
                    String[] adatok= sor.split(";");
                    int ev = Integer.parseInt(adatok[0]);
                    int het = Integer.parseInt(adatok[1]);
                    int forduloHet = Integer.parseInt(adatok[2]);
                   // adatok[3].replace('.','-');
                    LocalDate datum= LocalDate.parse(adatok[3],formatter);
                    List<Talalat> talalatokLita= new ArrayList<>();
                    List<Eredmeny> eredmenyeklista= new ArrayList<>();
                    for (int i = 0; i < 10; i+=2) {
                        int nyertTalalatokSzama= Integer.parseInt(adatok[i+4]);
                        String ujadat= adatok[i+5].replace(" Ft","");
                        ujadat= ujadat.replace(" ","");
                        int nyeremeny=Integer.parseInt(ujadat);
                        talalatokLita.add(new Talalat(talatSzam,nyertTalalatokSzama,nyeremeny));
                        talatSzam--;
                    }
                    for (int i = 0; i < 13+1; i++) {
                        switch (adatok[i+6]){
                            case "1": eredmenyeklista.add(Eredmeny._1); break;
                            case  "2": eredmenyeklista.add(Eredmeny._2); break;
                            case  "X": eredmenyeklista.add(Eredmeny.X); break;
                        }
                    }
                    Fordulo fordulo= getFordulo(ev,het,forduloHet,datum,talalatokLita,eredmenyeklista);
                    this.fordulokLista.add(fordulo);
                    sor= br.readLine();
                    sdfg = sdfg++;
                }
                br.close();
                fr.close();

            }catch(FileNotFoundException ex){
                System.err.printf("Hiba: %s",ex.getMessage());
            }catch(IOException ex){
                System.err.printf("Hiba: %s",ex.getMessage());
            }catch(Exception e){
                System.err.printf("Hiba: Exeption %s", e.getMessage());
            }

    }

    private Fordulo getFordulo(int ev,int het,int forduloHet,LocalDate datum,List<Talalat> talalatokLita,List<Eredmeny> eredmenyeklista) {
        Fordulo fordulo= new Fordulo();
        fordulo.setEv(ev);
        fordulo.setHet(het);
        fordulo.setForduloAHeten(forduloHet);
        fordulo.setDatum(datum);
        fordulo.setTalalatok(talalatokLita);
        fordulo.setEredmenyek(eredmenyeklista);
        return fordulo;
    }
}
