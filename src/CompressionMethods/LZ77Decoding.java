/**
 * |_|>
 * |_|>    Created by Dimyasha on 15.11.2021
 * |_|>
 */

package CompressionMethods;

import CompressionMethods.Abstract.AbstractMethod;

import java.nio.charset.Charset;

public class LZ77Decoding extends AbstractMethod {

    public LZ77Decoding() {
        super();
    }

    public LZ77Decoding(Integer[] data) {
        super(data);
    }

    public LZ77Decoding(String path, Charset charset) {
        super(path, charset);
    }

    @Override
    public void Invoke(boolean print) {

    }
}
