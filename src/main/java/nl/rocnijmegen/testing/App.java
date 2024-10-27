package nl.rocnijmegen.testing;

import java.util.Scanner;

public class App {
    private Scanner scanner;

    public App(Scanner scanner) {
        this.scanner = scanner;
    }

    public void hypotheekCalculator() {
        double partnerInkomen = 0;
        boolean studieschuld;

        System.out.println("Voer uw maandinkomen in:");
        double maandInkomen = scanner.nextDouble();

        partnerInkomen = heeftPartner(partnerInkomen);

        studieschuld = heeftStudischuld();

        System.out.println("Voer uw leeftijd in:");
        int leeftijd = scanner.nextInt();

        System.out.println("Voer uw postcode in:");
        String postcode = scanner.next();
        if (isGeblokkeerdePostcode(postcode)) {
            System.out.println("Uw postcode komt niet in aanmerking voor een hypotheek vanwege aardbevingsrisico's.");
            return;
        }

        System.out.println("Kies een rentevaste periode (1, 5, 10, 20, 30 jaar):");
        int rentevastePeriode = scanner.nextInt();
        double rente = bepaalRente(rentevastePeriode);
        if (rente <= 0) {
            System.out.println("Ongeldige rentevaste periode ingevoerd.");
            return;
        }

        double maximaalTeLenen = berekenMaximaalTeLenen(maandInkomen, partnerInkomen, studieschuld);

        System.out.println("Het maximaal te lenen bedrag is: €" + String.format("%.2f", maximaalTeLenen));
        double maandlasten = berekenMaandlasten(maximaalTeLenen, rente, rentevastePeriode);
        System.out.println("De maandlasten zijn: €" + String.format("%.2f", maandlasten));
        double totaleBetaling = maandlasten * 12 * rentevastePeriode;
        System.out.println("De totale betaling na " + rentevastePeriode + " jaar is: €" + String.format("%.2f", totaleBetaling));
    }

    public boolean heeftStudischuld() {
        System.out.println("Heeft u een studieschuld? (ja/nee):");
        String heeftStudieschuld = scanner.next();
        return heeftStudieschuld.equalsIgnoreCase("ja");
    }

    public double heeftPartner(double partnerInkomen) {
        System.out.println("Heeft u een partner? (ja/nee):");
        String heeftPartner = scanner.next();
        if (heeftPartner.equalsIgnoreCase("ja")) {
            System.out.println("Voer het maandinkomen van uw partner in:");
            return scanner.nextDouble();
        }
        return partnerInkomen;
    }

    public static boolean isGeblokkeerdePostcode(String postcode) {
        return postcode.equals("9679") || postcode.equals("9681") || postcode.equals("9682");
    }

    public static double bepaalRente(int rentevastePeriode) {
        switch (rentevastePeriode) {
            case 1:
                return 0.02;
            case 5:
                return 0.03;
            case 10:
                return 0.035;
            case 20:
                return 0.045;
            case 30:
                return 0.05;
            default:
                return 0;
        }
    }

    public static double berekenMaximaalTeLenen(double inkomen, double partnerInkomen, boolean studieschuld) {
        double totaalInkomen = inkomen + partnerInkomen;
        double maxLening = totaalInkomen * 4.5;

        if (studieschuld) {
            maxLening *= 0.75;
        }

        return maxLening;
    }

    public double berekenMaandlasten(double lening, double rente, int looptijdJaren) {
        double maandRente = rente / 12;
        int looptijdMaanden = looptijdJaren * 12;

        return (lening * maandRente * Math.pow(1 + maandRente, looptijdMaanden)) /
                (Math.pow(1 + maandRente, looptijdMaanden) - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        App app = new App(scanner);
        app.hypotheekCalculator();
    }
}
