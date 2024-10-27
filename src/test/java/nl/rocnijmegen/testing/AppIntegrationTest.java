package nl.rocnijmegen.testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
public class AppIntegrationTest {

    @Mock
    private Scanner mockScanner;

    @InjectMocks
    private App app;

    @Test
    public void testBerekenLeningMetPartner() {
        when(mockScanner.next()).thenReturn("2000", "ja", "2000", "nee", "25", "1234", "10");

        app.hypotheekCalculator();
    }

    @Test
    public void testBerekenLeningZonderPartner() {
        when(mockScanner.next()).thenReturn("2000", "nee", "nee", "25", "1234", "10");

        app.hypotheekCalculator();
    }

    @Test
    public void testGeblokkeerdePostcodeFout() {
        when(mockScanner.next()).thenReturn("2000", "ja", "2000", "nee", "25", "9682", "10");

        app.hypotheekCalculator();
    }
}
