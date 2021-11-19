package CompressionMethods.Abstract;

public interface LZMapAbstract {
    void add(String key);
    int getValue(String key);
    boolean contains(String key);
    void print();
}
