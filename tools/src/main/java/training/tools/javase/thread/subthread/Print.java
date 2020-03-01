package training.tools.javase.thread.subthread;

import java.io.IOException;
import java.io.PipedReader;

/**
 * @ClassName Print
 * @Author Roilka
 * @Description 管道输出
 * @Date 2020/2/28
 */
public class Print implements Runnable {

    private PipedReader in;

    public Print(PipedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        int receive = 0;
        try {
            while ((receive = in.read()) != -1) {
                Profiler.begin();
                System.out.print("this is runnable" + (char) receive + ":" + Math.random());

                System.out.print("耗时" + Profiler.end() + "mills");
            }
        } catch (IOException e) {

        }
    }
}
