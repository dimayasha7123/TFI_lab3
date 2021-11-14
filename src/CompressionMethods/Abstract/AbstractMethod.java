/**
 * |_|>
 * |_|>    Created by Dimyasha on 14.11.2021
 * |_|>
 */

package CompressionMethods.Abstract;

public abstract class AbstractMethod {
    protected final Integer[] input;

    protected Integer[] output;
    public Integer[] getOutput() {
        return output;
    }

    public AbstractMethod(Integer[] data) {
        this.input = data;
    }

    public abstract void Compress(boolean print);
    public abstract void Decompress(boolean print);

}
