package training.tools.jvm;

public class GCTest {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    private byte[] bigSize = new byte[2 * _1MB];
    public static void testGC(){
        GCTest a = new GCTest();
        GCTest b = new GCTest();
        a.instance = b;
        b.instance = a;
        System.gc();
    }

    public static void main(String[] args) {
        testGC();
    }
}
