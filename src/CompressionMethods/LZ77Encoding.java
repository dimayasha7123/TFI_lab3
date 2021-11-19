/**
 * |_|>
 * |_|>    Created by Dimyasha on 15.11.2021
 * |_|>
 */

package CompressionMethods;

import CompressionMethods.Abstract.AbstractMethod;
import CompressionMethods.Abstract.LZMapAbstract;
import Utils.Tuple;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LZ77Encoding extends AbstractMethod {

    public LZ77Encoding() {
        super();
    }

    public LZ77Encoding(Integer[] data) {
        super(data);
    }

    public LZ77Encoding(String path, Charset charset) {
        super(path, charset);
    }

    protected LZMapAbstract map = null;
    //есть три варианта как хранить словарь:
    // 1) Обычный массив, будем бежать по нему с начала
    // 2) Дерево поиска
    // 3) Встроенный map

    public void setMap(LZMapAbstract map) {
        this.map = map;
    }

    protected String getStringFromIntegerArray() {
        StringBuilder sb = new StringBuilder();
        for (Integer i : input) {
            sb.append((char) ((int) i));
        }
        return sb.toString();
    }

    @Override
    public void Invoke(boolean print) {
        int bitCount = 16;
        if (map == null) map = new LZMapArray();

        //создаем строка из массива кодов символов
        String inputString = getStringFromIntegerArray();

        //добавляем все символы в словарь
        for (int i = 0; i < 1 << bitCount; ++i) {
            map.add(Character.toString((char) i));
        }

        //основная часть алгоритма
        String X = "";
        X += inputString.charAt(0);
        ArrayList<Integer> thread = new ArrayList<>();

        int len = inputString.length();
        for (int i = 1; i <= len; ++i) {
            if (i == len) thread.add(map.getValue(X));
            else {
                String Y = "";
                Y += inputString.charAt(i);
                if (map.contains(X + Y)) {
                    X += Y;
                } else {
                    thread.add(map.getValue(X));
                    map.add(X + Y);
                    X = Y;
                }
            }
        }

        //переписываем в битовую строку
        int places = 1;
        StringBuilder sb = new StringBuilder();
        for (Integer i : thread) {
            String binStr = Integer.toBinaryString(i);
            if (binStr.length() > places) places = binStr.length();
            sb.append("0".repeat(places - binStr.length()));
            sb.append(binStr);
        }
        String bitStr = sb.toString();

        //переписываем в utf коды
        ArrayList<Integer> outputArr = new ArrayList<>();
        int step = 16;
        for (int i = step; i < bitStr.length(); i += step) {
            outputArr.add(
                    Integer.parseInt(
                            bitStr.substring(i - step, i)
                                    .replaceFirst("^0+(?!$)", "")
                            , 2
                    )
            );
        }

        //дописываем оставшиеся биты, что не влезли
        outputArr.add(
                Integer.parseInt(
                        bitStr.substring(bitStr.length() - bitStr.length() % step)
                                .replaceFirst("^0+(?!$)", "")
                        , 2
                )
        );

        if (print) {

            System.out.println(sb);
            System.out.println(Arrays.toString(outputArr.toArray(Integer[]::new)));
            System.out.println(Arrays.toString(thread.toArray(Integer[]::new)));
        }
    }


}


class LZMapArray implements LZMapAbstract {
    ArrayList<String> arr = new ArrayList<>();

    @Override
    public void add(String key) {
        arr.add(key);
    }

    @Override
    public int getValue(String key) {
        for (int i = 0; i < arr.size(); ++i) {
            if (arr.get(i).equals(key)) return i;
        }
        return -1;
    }

    @Override
    public boolean contains(String key) {
        for (String s : arr) {
            if (s.equals(key)) return true;
        }
        return false;
    }

    private int getIntLength(int x) {
        int ans = 0;
        while (x > 0) {
            ans++;
            x /= 10;
        }
        return ans;
    }

    @Override
    public void print() {
        for (int i = 0; i < arr.size(); ++i) {
            String frmtStr = "%" + getIntLength(arr.size()) + "d | %s\n";
            System.out.printf(frmtStr, i, arr.get(i));
        }
    }
}
