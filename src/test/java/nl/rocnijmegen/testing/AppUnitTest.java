package nl.rocnijmegen.testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
public class AppUnitTest {

    @Mock
    private Scanner mockScanner;

    @InjectMocks
    private App app;

    @Test
    public void testBepaalRente() {
        assertEquals(0.02, App.bepaalRente(1), "Rente voor 1 jaar moet 0,02 zijn");
        assertEquals(0.035, App.bepaalRente(10), "Rente voor 10 jaar moet 0,035 zijn");
        assertEquals(0.05, App.bepaalRente(30), "Rente voor 30 jaar moet 0,05 zijn");
        assertEquals(0, App.bepaalRente(100), "Rente voor een ongeldige periode moet 0 zijn");
    }

    @Test
    public void testIsGeblokkeerdePostcode() {
        assertTrue(App.isGeblokkeerdePostcode("9679"), "Postcode 9679 is geblokkeerd");
        assertTrue(App.isGeblokkeerdePostcode("9682"), "Postcode 9682 is geblokkeerd");
        assertFalse(App.isGeblokkeerdePostcode("1234"), "Postcode 1234 is niet geblokkeerd");
        assertFalse(App.isGeblokkeerdePostcode("5678"), "Postcode 5678 is niet geblokkeerd");
    }

    @Test
    public void testBerekenMaximaalTeLenen() {
        assertEquals(9000.0, App.berekenMaximaalTeLenen(2000.0, 0.0, false), 0.01, "Maximale lening zonder partner en zonder studieschuld moet 9000 zijn");
        assertEquals(18000.0, App.berekenMaximaalTeLenen(2000.0, 2000.0, false), 0.01, "Maximale lening met partner en zonder studieschuld moet 18000 zijn");
        assertEquals(13500.0, App.berekenMaximaalTeLenen(2000.0, 2000.0, true), 0.01, "Maximale lening met partner en studieschuld moet 13500 zijn");
    }

    @Test
    public void testBerekenMaandlasten() {
        double lening = 200000.0;
        double rente = 0.05;
        int looptijdJaren = 30;
        double maandlasten = app.berekenMaandlasten(lening, rente, looptijdJaren);
        assertEquals(1073.64, maandlasten, 0.01, "Maandlasten voor een lening van 200.000 met 5% rente en 30 jaar looptijd moeten ongeveer 1073,64 zijn");
    }

    @Test
    public void testHeeftStudischuld() {
        when(mockScanner.next()).thenReturn("ja").thenReturn("nee");
        assertTrue(app.heeftStudischuld(), "Moet true retourneren bij invoer 'ja'");
        assertFalse(app.heeftStudischuld(), "Moet false retourneren bij invoer 'nee'");
    }

    @Test
    public void testHeeftPartner() {
        when(mockScanner.next()).thenReturn("ja");
        when(mockScanner.nextDouble()).thenReturn(2500.0);
        assertEquals(2500.0, app.heeftPartner(0.0), 0.01, "Moet het partner inkomen retourneren bij invoer 'ja'");
        when(mockScanner.next()).thenReturn("nee");
        assertEquals(0.0, app.heeftPartner(0.0), 0.01, "Moet 0.0 retourneren bij invoer 'nee'");
    }

    @Test
    public void testHypotheekCalculatorMetGeblokkeerdePostcode() {
        when(mockScanner.nextDouble()).thenReturn(3000.0);
        when(mockScanner.next()).thenReturn("nee", "ja", "9681");
        when(mockScanner.nextInt()).thenReturn(30, 5);
        app.hypotheekCalculator();
        verify(mockScanner, atLeastOnce()).next();
    }

    @Test
    public void testHypotheekCalculatorNormaalScenario() {
        when(mockScanner.nextDouble()).thenReturn(3000.0, 1500.0);
        when(mockScanner.next()).thenReturn("ja", "nee", "1234");
        when(mockScanner.nextInt()).thenReturn(25, 10);
        app.hypotheekCalculator();
        verify(mockScanner, atLeastOnce()).next();
    }
}
