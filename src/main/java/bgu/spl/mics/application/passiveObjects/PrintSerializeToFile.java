package bgu.spl.mics.application.passiveObjects;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class PrintSerializeToFile {
    private String filename;


    public PrintSerializeToFile(String filename) {
        this.filename = filename;
    }

    public void printSerialized(HashMap<?, ?> hashMap) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(hashMap);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
