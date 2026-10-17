package de.timonexus;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void shouldPrintSystemLaeuft() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            Main.main(new String[]{});

            assertEquals("System laeuft!" + System.lineSeparator(),
                    output.toString());
        } finally {
            System.setOut(originalOut);
        }
    }
}