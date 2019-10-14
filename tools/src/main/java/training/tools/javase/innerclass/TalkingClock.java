package training.tools.javase.innerclass;

import training.tools.utils.SysTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TalkingClock {

    private int interval;

    private boolean beep;

    public  TalkingClock(int interval,boolean beap){
        this.interval = interval;
        this.beep = beap;
    }

    public void start(){

        ActionListener listener = new TimePrinter();
        Timer t =new Timer(interval, listener);
        t.start();
    }

    public void start2(){
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date now = new Date();
                SysTools._out(now, "At the tone, the time is ");
                if (beep){
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        };
        Timer t = new Timer(interval, listener);
        t.start();
    }

    public class TimePrinter implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Date now =new Date();
            SysTools._out(now, "At the tone,the time is ");
            if (beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}
