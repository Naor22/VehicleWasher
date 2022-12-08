// Naor Ben Azra - 318544939 && Osher Ben Hamo - 209264076

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VehicleLogger {

    public void writeFile(String str) {
        FileWriter writer;
        File log = new File("log.txt");
        try {
            writer = new FileWriter(log, true);
            writer.write(str + "\n");
            writer.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
