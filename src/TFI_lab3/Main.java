package TFI_lab3;

import CompressionMethods.LZ77Encoding;

import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        LZ77Encoding lz = new LZ77Encoding("testData\\source\\lztest1.txt", StandardCharsets.UTF_16);
        lz.Invoke(true);


    }
}