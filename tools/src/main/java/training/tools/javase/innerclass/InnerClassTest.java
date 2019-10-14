package training.tools.javase.innerclass;

import javax.swing.*;
import java.util.ArrayList;

public class InnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000, true);
        clock.start();
        TalkingClock.TimePrinter inner = new TalkingClock(1000, true).new TimePrinter();
        //keep program 莽撞running until user selects "0k"
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);

        ArrayList<String> friends = new ArrayList<String>(){{add("tony");add("Jack");}};
    }
}
