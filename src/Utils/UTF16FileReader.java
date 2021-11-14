/**
 * |_|>
 * |_|>    Created by Dimyasha on 14.11.2021
 * |_|>
 */

package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class UTF16FileReader {
    protected final String path;
    protected Integer[] data;

    public UTF16FileReader(String path) {
        this.path = path;
    }

    public Integer[] getData() {
        return data;
    }

    public void ReadToEnd() {
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_16);
            ArrayList<Integer> intList = new ArrayList<>();
            int c = -1;
            while (true) {
                try {
                    if ((c = isr.read()) == -1) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intList.add(c);
            }
            data = new Integer[intList.size()];
            intList.toArray(data);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String toBinaryWithZeros(int x) {
        StringBuilder ans = new StringBuilder(Integer.toBinaryString(x));
        while (ans.length() != 16) ans.insert(0, "0");
        ans.insert(12, " ");
        ans.insert(8, " ");
        ans.insert(4, " ");
        return ans.toString();
    }

    private static String getChar(int x) {
        return switch (x) {
            case 9 -> "\\t";
            case 13 -> "\\n";
            case 10 -> "\\r";
            default -> String.format("%c", x);
        };
    }

    private int getIntLength(int x) {
        int ans = 0;
        while (x > 0) {
            ans++;
            x /= 10;
        }
        return ans;
    }

    public void printHexDump() {
        String[] arrBin = Arrays.stream(Objects.requireNonNull(data))
                .map(UTF16FileReader::toBinaryWithZeros)
                .toArray(String[]::new);

        System.out.println("HexDump for \"" + path + "\"");
        for (int i = 0; i < data.length; ++i) {
            String formatString = "%" + getIntLength(data.length) + "d | %s | %-5d | %-3s\n";
            System.out.printf(formatString, i, arrBin[i], data[i], getChar(data[i]));
        }
    }
}
