/**
 * |_|>
 * |_|>    Created by Dimyasha on 14.11.2021
 * |_|>
 */

package Utils;

import java.io.*;
import java.nio.charset.Charset;

public class UTF16FileWriter {
    protected final String path;
    protected final Integer[] data;
    protected final Charset charset;

    public UTF16FileWriter(String path, Integer[] data, Charset charset) {
        this.path = path;
        this.data = data;
        this.charset = charset;
    }

    public Integer[] getData() {
        return data;
    }

    public void WriteAll(){
        try {
            Writer fw = new OutputStreamWriter(
                    new FileOutputStream(path, false), charset);
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
