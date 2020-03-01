package training.tools.javase.thread.subthread;

import java.io.IOException;
import java.io.PipedReader;
import java.util.concurrent.Callable;

/**
 * @ClassName Print
 * @Author Roilka
 * @Description 管道输出
 * @Date 2020/2/28
 */
public class PrintCall implements Callable {

    private PipedReader in;

    public PrintCall(PipedReader in) {
        this.in = in;
    }



    @Override
    public Object call() throws Exception {
        int receive = 0;
        try {
            while ((receive = in.read()) != -1) {
                Profiler.begin();
                System.out.print("this is callable "+(char) receive + ":" + Math.random());

                System.out.print("耗时" + Profiler.end() + "mills");
            }
        } catch (IOException e) {

        }
        return null;
    }
}
