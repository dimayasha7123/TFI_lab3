package TFI_lab3;

import CompressionMethods.RLEDecoding;
import CompressionMethods.RLEEncoding;
import Utils.UTF16FileReader;
import Utils.UTF16FileWriter;

import static TFI_lab3.Global.defPath;

public class Main {

    public static void main(String[] args) {
        String path = defPath + "test2.txt";
        String genPath = defPath + "test1.txt";
        //Gen1.generate(genPath);

        UTF16FileReader fileReader = new UTF16FileReader(path);
        fileReader.ReadToEnd();
        //fileReader.printHexDump();

        Integer[] arrInt = fileReader.getData();
        System.out.printf("Считано символов: %d\n", arrInt.length);

        RLEEncoding rleEnc = new RLEEncoding(arrInt);
        rleEnc.Invoke(true);

        String encodedPath = defPath + "rle1.txt";

        UTF16FileWriter fw = new UTF16FileWriter(encodedPath, rleEnc.getOutput());
        fw.WriteAll();

        /*UTF16FileReader fr = new UTF16FileReader(defPath + "rle2.txt");
        fr.ReadToEnd();
        fr.printHexDump();*/

        RLEDecoding rleDec = new RLEDecoding(encodedPath);
        rleDec.Invoke(true);
        rleDec.WriteTo(defPath + "test2_encoded_decoded.txt");
    }
}