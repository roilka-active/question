package training.tools.javase.listen;

import training.tools.utils.SysTools;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TimePrinter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        Date now = new Date();
        SysTools._out(now, "At the tone,the time is ");
        /**
           获得默认的工具箱。工具箱包含有关GUI环境的信息。
         beep():发出一声铃响
         */
        Toolkit.getDefaultToolkit().beep();
    }
}
