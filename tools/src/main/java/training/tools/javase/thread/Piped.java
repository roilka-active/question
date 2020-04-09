package training.tools.javase.thread;

import training.tools.javase.thread.subthread.Print;
import training.tools.javase.thread.subthread.PrintCall;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @ClassName Piped
 * @Author Roilka
 * @Description 管道输入/输出流
 * @Date 2020/2/28
 */
public class Piped {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);
        Thread printThread = new Thread(new Print(in),"PrintThread");
        printThread.start();
        FutureTask<Integer> task = new FutureTask<Integer>(new PrintCall(in));
        task.run();
        Thread printThread1 = new Thread(task,"PrintThread1");
        printThread1.start();
        Thread printThread2 = new Thread(new Print(in),"PrintThread2");
        printThread2.start();
        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1){
                out.write(receive);
            }
        }finally {
            out.close();
        }
    }
}
