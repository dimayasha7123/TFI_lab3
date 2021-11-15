/**
 * |_|>
 * |_|>    Created by Dimyasha on 15.11.2021
 * |_|>
 */

package CompressionMethods;

import CompressionMethods.Abstract.AbstractMethod;

import java.util.ArrayList;

import static TFI_lab3.Global.half;

public class RLEDecoding extends AbstractMethod {
    public RLEDecoding(Integer[] data) {
        super(data);
    }

    public RLEDecoding(String path) {
        super(path);
    }

    @Override
    public void Invoke(boolean print) {
        boolean inOrder = false;
        int curr = 0;
        ArrayList<Integer> outArr = new ArrayList<>();
        for (int i = 0; i < input.length; ++i) {
            if (!inOrder) {
                curr = input[i];
                inOrder = true;
            } else {
                if (curr < half) {
                    for (int j = 0; j <= curr; ++j) outArr.add(input[i]);
                } else {
                    for (int j = 0; j <= curr - half; ++j, ++i) outArr.add(input[i]);
                    i--;
                }
                inOrder = false;
            }
        }
        output = outArr.toArray(Integer[]::new);
    }
}
