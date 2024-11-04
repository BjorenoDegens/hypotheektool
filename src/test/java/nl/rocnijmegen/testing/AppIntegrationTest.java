package nl.rocnijmegen.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class AppIntegrationTest {

    @Test
    public void testBerekenLeningMetPartner() {
        String input = "2000\nja\n2000\nnee\n25\n1234\n10";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        App.main(new String[0]);

        System.setOut(originalOut);

        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains("Het maximaal te lenen bedrag is: €"), "Max loan amount not displayed correctly.");
        assertTrue(actualOutput.contains("De maandlasten zijn: €"), "Monthly payment not displayed correctly.");
    }

    @Test
    public void testBerekenLeningZonderPartner() {
        String input = "2000\nnee\nnee\n25\n1234\n10";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        App.main(new String[0]);

        System.setOut(originalOut);

        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains("Het maximaal te lenen bedrag is: €"), "Max loan amount not displayed correctly.");
        assertTrue(actualOutput.contains("De maandlasten zijn: €"), "Monthly payment not displayed correctly.");
    }

    @Test
    public void testGeblokkeerdePostcodeFout() {
        String input = "2000\nja\n2000\nnee\n25\n9682\n10";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        App.main(new String[0]);

        System.setOut(originalOut);

        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains("Uw postcode komt niet in aanmerking voor een hypotheek vanwege aardbevingsrisico's."),
                "Blocked postcode message not displayed.");
    }
}
