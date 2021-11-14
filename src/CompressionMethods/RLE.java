/**
 * |_|>
 * |_|>    Created by Dimyasha on 14.11.2021
 * |_|>
 */

package CompressionMethods;

import CompressionMethods.Abstract.AbstractMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static TFI_lab3.Global.half;

public class RLE extends AbstractMethod {

    public RLE(Integer[] data) {
        super(data);
    }

    @Override
    public void Compress(boolean print) {
        int sum = 0;            //контрольная сумма
        int counter = 1;        //счетчик подярд идущих
        int counterOnes = 1;    //кол-во подряд идущих разных одинарных символов
        int prev = input[0];   //предыдущий элемент

        ArrayList<Integer> ones = new ArrayList<>();
        ArrayList<Integer> outputArr = new ArrayList<>();

        for (int i = 1; i < input.length; ++i) {

            int curr = input[i];

            if (curr == prev) {

                //если до этого были разные подряд идущие одиночные символы
                if (counterOnes != 1) {
                    sum += counterOnes - 1;

                    int num = counterOnes - 2 + half;
                    Integer[] numbers = ones.toArray(Integer[]::new);

                    outputArr.add(num);
                    Collections.addAll(outputArr, numbers);

                    if (print) System.out.println("-" + num + " " + Arrays.toString(numbers));

                    ones = new ArrayList<>();
                    counterOnes = 1;
                }

                counter++;

                //случай переполнения
                if (counter > half) {
                    sum += counter - 1;

                    int num = counter - 2;
                    int number = prev;

                    outputArr.add(num);
                    outputArr.add(number);

                    if (print) System.out.println(num + " " + number);

                    counter = 1;
                }
            } else if (counter != 1) {
                sum += counter;

                int num = counter - 1;
                int number = prev;

                outputArr.add(num);
                outputArr.add(number);

                if (print) System.out.println(num + " " + number);

                prev = curr;
                counter = 1;
            } else {

                counterOnes++;

                if (counterOnes > half) {
                    counter += counterOnes - 2;

                    int num = counterOnes - 3 + half;
                    Integer[] numbers = ones.toArray(Integer[]::new);

                    outputArr.add(num);
                    Collections.addAll(outputArr, numbers);

                    if (print) System.out.println("-" + num + " " + Arrays.toString(numbers));

                    ones = new ArrayList<>();
                    counterOnes = 1;
                }

                ones.add(prev);
                prev = curr;
            }
        }

        if (counterOnes != 1) {
            sum += counterOnes - 1;

            int num = counterOnes - 2 + half;
            Integer[] numbers = ones.toArray(Integer[]::new);

            outputArr.add(num);
            Collections.addAll(outputArr, numbers);

            if (print) System.out.println("-" + num + " " + Arrays.toString(numbers));

        } else {
            sum += counter;

            int num = counter - 1;
            int number = prev;

            outputArr.add(num);
            outputArr.add(number);

            if (print) System.out.println(num + " " + number);
        }

        output = outputArr.toArray(Integer[]::new);
        if (print) System.out.printf("Контрольная сумма = %d", sum);
    }

    @Override
    public void Decompress(boolean print) {

    }
}
