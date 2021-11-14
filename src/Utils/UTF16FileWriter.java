/**
 * |_|>
 * |_|>    Created by Dimyasha on 14.11.2021
 * |_|>
 */

package Utils;

import java.io.FileWriter;
import java.io.IOException;

public class UTF16FileWriter {
    protected final String path;
    protected final Integer[] data;

    public UTF16FileWriter(String path, Integer[] data) {
        this.path = path;
        this.data = data;
    }

    public Integer[] getData() {
        return data;
    }

    public void WriteAll(){
        try {
            FileWriter fw = new FileWriter(path, false);
            for (Integer datum : data) {
                fw.write(String.format("%c", datum));
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
