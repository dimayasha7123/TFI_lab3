/**
 * |_|>
 * |_|>    Created by Dimyasha on 14.11.2021
 * |_|>
 */

package CompressionMethods.Abstract;

import Utils.UTF16FileReader;
import Utils.UTF16FileWriter;

public abstract class AbstractMethod {
    protected final Integer[] input;
    protected Integer[] output;

    public Integer[] getOutput() {
        return output;
    }
    public Integer[] getInput() { return input; }

    public AbstractMethod(Integer[] data) {
        this.input = data;
    }

    public AbstractMethod(String path){
        UTF16FileReader fileReader = new UTF16FileReader(path);
        fileReader.ReadToEnd();
        input = fileReader.getData();
    }

    public abstract void Invoke(boolean print);

    public void WriteTo(String path){
        UTF16FileWriter fw = new UTF16FileWriter(path, getOutput());
        fw.WriteAll();
    }
}
