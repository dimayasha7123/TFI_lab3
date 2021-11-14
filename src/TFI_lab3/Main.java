package TFI_lab3;

import TestGenerators.Gen1;
import Utils.UTF16FileReader;
import java.util.*;
import static TFI_lab3.Global.defPath;
import static TFI_lab3.Global.half;

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

        //todo Переполнение

        int sum = 0;
        int counter = 1;
        int counterOnes = 1;
        ArrayList<Integer> ones = new ArrayList<>();
        int pred = arrInt[0];
        for (int i = 1; i < arrInt.length; ++i) {
            int curr = arrInt[i];
            if (curr == pred) {
                if (counterOnes != 1) {
                    sum += counterOnes - 1;
                    System.out.println("-" + (counterOnes - 2 + half) + " " + Arrays.toString(ones.toArray(Integer[]::new)));
                    ones = new ArrayList<>();
                    counterOnes = 1;
                }
                counter++;
                if (counter > half) {
                    sum += counter - 1;
                    System.out.println((counter - 2) + " " + pred);
                    counter = 1;
                }
            } else if (counter != 1) {
                sum += counter;
                System.out.println((counter - 1) + " " + pred);
                pred = curr;
                counter = 1;
            } else {
                counterOnes++;
                if (counterOnes > half) {
                    counter += counterOnes - 2;
                    System.out.println("-" + (counterOnes - 3 + half) + " " + Arrays.toString(ones.toArray(Integer[]::new)));
                    ones = new ArrayList<>();
                    counterOnes = 1;
                }
                ones.add(pred);
                pred = curr;
            }
        }
        if (counterOnes != 1) {
            sum += counterOnes - 1;
            System.out.println("-" + (counterOnes - 2 + half) + " " + Arrays.toString(ones.toArray(Integer[]::new)));
        } else {
            sum += counter;
            System.out.println((counter - 1) + " " + pred);
        }
        System.out.printf("Контрольная сумма = %d", sum);
    }
}