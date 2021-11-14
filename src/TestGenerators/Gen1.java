/**
 * |_|>
 * |_|>    Created by Dimyasha on 14.11.2021
 * |_|>
 */

package TestGenerators;

import Utils.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static TFI_lab3.Global.half;

public class Gen1 {
    static Random rnd = new Random();

    static int grl() {
        return rnd.nextInt(half) + half + 1;
    }


    public static void generate(String path) {
        try (FileWriter writer = new FileWriter(path, StandardCharsets.UTF_16, false)) {
            StringBuilder sb = new StringBuilder();
            int n = 4;
            Tuple<String, Integer>[] chain = new Tuple[4];
            chain[0] = new Tuple<>("a", grl());
            chain[1] = new Tuple<>("b", grl());
            chain[2] = new Tuple<>("c", half);
            chain[3] = new Tuple<>("d", half + 1);
            int sum = 0;
            for (int i = 0; i < n; ++i) {
                if (i != 0) {
                    String filler = "qwerty";
                    sb.append(filler);
                    sum += filler.length();
                }
                sb.append(chain[i].x.repeat(chain[i].y));
                System.out.printf("Символов \"%s\" в наличии: %d шт.\n", chain[i].x, chain[i].y);
                sum += chain[i].y;
            }
            System.out.printf("Длина сгенерированного сообщения: %d\n", sum);
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
