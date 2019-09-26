package training.tools.javase.listen;


import javax.swing.*;
import java.awt.event.ActionListener;

public class ListenTest {

    public static void main(String[] args) {
        ActionListener listener = new TimePrinter();

        /**
           构造一个定时器，每个interval 毫秒钟通告listener 一次
         */
        Timer t = new Timer(10000,listener);

        /**
           启动定时器。一旦启动成功，定时器将调用监听器的actionPerformed。
         */
        t.start();

        /**
           显示一个包含一条消息和OK按钮的对话框。这个对话框将位于其parent组件的中央。
         如果parent 为 null， 对话框显示在屏幕的中央。
         */
        JOptionPane.showMessageDialog(null, "Quit program?");
        /**
           停止定时器。一旦停止成功，定时器将不再调用监听器的actionPerformed。
         */
        System.exit(0);
    }
}
