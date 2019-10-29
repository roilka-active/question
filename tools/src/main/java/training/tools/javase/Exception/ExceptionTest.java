package training.tools.javase.Exception;

import java.io.ByteArrayOutputStream;

public class ExceptionTest {

    public static void main(String[] args) {
        Throwable t = new Throwable();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //t.printStackTrace(out);
        String description = out.toString();
    }
}
