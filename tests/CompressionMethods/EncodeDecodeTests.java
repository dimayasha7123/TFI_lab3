package CompressionMethods;

import CompressionMethods.Abstract.AbstractMethod;
import UTFE.TableOutput.Table;
import Utils.Tuple;
import Utils.UTF16FileReader;
import Utils.UTF16FileWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.lang.model.SourceVersion;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class EncodeDecodeTests {
    static ArrayList<Tuple<Integer[], String>> srcData = new ArrayList<>();
    static String srcPath = "testData\\source\\";

    @BeforeAll
    static void setUp() {
        File srcDir = new File(srcPath);
        String[] files = srcDir.list();
        for (String path : Objects.requireNonNull(files)) {
            UTF16FileReader fr = new UTF16FileReader(srcPath + path);
            fr.ReadToEnd();
            srcData.add(new Tuple<>(fr.getData(), path));
        }
    }

    void CommonTest(AbstractMethod encMethod, AbstractMethod decMethod, String methodName) {
        String encPath = "testData\\encoded\\" + methodName + "\\";
        String decPath = "testData\\decoded\\" + methodName + "\\";
        String encPrefix = "ENC_";
        String decPrefix = "DEC_";

        //todo Добавить логи того, что сейчас происходит

        //удаляем все в encPath
        File[] forDelete = Objects.requireNonNull(new File(encPath).listFiles());
        Arrays.stream(forDelete).forEach((x) -> {
            boolean deleted = x.delete();
            if (!deleted) System.out.println(x + "почему-то не удален ¯\\_(ツ)_/¯");
        });

        //удаляем все в decPath
        forDelete = Objects.requireNonNull(new File(decPath).listFiles());
        Arrays.stream(forDelete).forEach((x) -> {
            boolean deleted = x.delete();
            if (!deleted) System.out.println(x + "почему-то не удален ¯\\_(ツ)_/¯");
        });

        //сжимаем
        srcData.forEach((x) -> {
            encMethod.setInput(x.x);
            encMethod.Invoke(false);
            UTF16FileWriter fw = new UTF16FileWriter(encPath + encPrefix + x.y, encMethod.getOutput());
            fw.WriteAll();
        });

        //считываем сжатое
        File encDir = new File(encPath);
        String[] encPaths = encDir.list();
        ArrayList<Tuple<Integer[], String>> forDec = new ArrayList<>();
        for (String path : Objects.requireNonNull(encPaths)) {
            UTF16FileReader fr = new UTF16FileReader(encPath + path);
            fr.ReadToEnd();
            forDec.add(new Tuple<>(fr.getData(), path));
        }

        //разжимаем
        forDec.forEach((x) -> {
            decMethod.setInput(x.x);
            decMethod.Invoke(false);
            UTF16FileWriter fw = new UTF16FileWriter(decPath + decPrefix + x.y, decMethod.getOutput());
            fw.WriteAll();
        });

        File decDir = new File(decPath);
        File srcDir = new File(srcPath);
        File[] decFiles = Objects.requireNonNull(decDir.listFiles());
        File[] srcFiles = Objects.requireNonNull(srcDir.listFiles());
        File[] encFiles = Objects.requireNonNull(encDir.listFiles());

        int n = decFiles.length;

        Long[] expected = new Long[n];
        Long[] actual = new Long[n];
        Long[] encoded = new Long[n];

        for (int i = 0; i < n; ++i) {
            expected[i] = srcFiles[i].length();
            actual[i] = decFiles[i].length();
            encoded[i] = encFiles[i].length();
        }
        System.out.println(methodName + " report:");

        ArrayList<Object[]> tableData = new ArrayList<>();
        tableData.add(new Object[]{"File", "Source", "Encoded", "Decoded", "Compress."});
        double avgRatio = 0;
        for (int i = 0; i < n; ++i) {
            double ratio = (double) actual[i] / (double) encoded[i];
            avgRatio += ratio;
            tableData.add(new Object[]{srcData.get(i).y, actual[i], encoded[i], expected[i], ratio});
        }
        avgRatio /= n;

        System.out.println(Table.TableToString(tableData.toArray(Object[][]::new)));
        System.out.printf("Average compression = %.2f\n", avgRatio);

        assertArrayEquals(expected, actual);

    }

    @DisplayName("RLE")
    @Test
    void RLETest() {
        CommonTest(new RLEEncoding(), new RLEDecoding(), "RLE");
    }

    @DisplayName("LZ77")
    @Test
    void LZ77Test() {

    }

    @DisplayName("Huffman")
    @Test
    void HuffmanTest() {

    }
}