package toto;

import toto.szolgaltatas.TotoSzolgaltatas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Alk {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        TotoSzolgaltatas szolgaltatas = new TotoSzolgaltatas();
        szolgaltatas.beolvasas("toto.csv");
        szolgaltatas.legnagyobbNyeremeny();
        szolgaltatas.statisztika();
        System.out.print("Kerem adjon meg egy datumot (2011.11.22.): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.d.");
        LocalDate bedatum = LocalDate.parse(sc.nextLine(), formatter);
        System.out.print("Kerem adjon meg egy tippet: ");
        String tipp = sc.nextLine();
        System.out.print("Eredmeny: talalat: %f, nyeremeny: %f Ft");
    }
}
