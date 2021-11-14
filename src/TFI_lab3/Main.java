package TFI_lab3;

import CompressionMethods.RLE;
import Utils.UTF16FileReader;
import Utils.UTF16FileWriter;

import java.util.*;

import static TFI_lab3.Global.defPath;

public class Main {

    public static void main(String[] args) {
        String path = defPath + "test2.txt";
        String genPath = defPath + "test1.txt";
        //Gen1.generate(genPath);

        UTF16FileReader fileReader = new UTF16FileReader(path);
        fileReader.ReadToEnd();
        fileReader.printHexDump();

        Integer[] arrInt = fileReader.getData();
        System.out.printf("Считано символов: %d\n", arrInt.length);

        RLE rle = new RLE(arrInt);
        rle.Compress(true);

        String destPath = defPath + "rle.txt";

        UTF16FileWriter fw = new UTF16FileWriter(destPath, rle.getOutput());
        fw.WriteAll();

    }
}