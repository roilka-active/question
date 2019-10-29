package training.io;/**
 * Package: training.io
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/10/14 22:37
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import training.tools.utils.SysTools;

import java.io.*;

/**
 * @author zhanghui
 * @description IO测试
 * @date 2019/10/14
 */
public class IOTest {

    final String SrcPath = this.getClass().getResource("/").getPath();
    static final String prePath = "D:\\GIT\\owner\\Manager\\training\\tools\\src\\main\\resources";
    public static void main(String[] args) throws IOException {

        String path =System.getProperty("java.class.path");
        FileInputStream fin = new FileInputStream( prePath + "\\sensi_words.txt");
        DataInputStream din = new DataInputStream(fin);
        double s = din.readDouble();
        SysTools._out(s,"这是文件里读出来的：");
        FileReader fileReader = new FileReader(new File(prePath + "\\sensi_words.txt"));
        SysTools._out(fin.getFD(),"文件描述");
    }
}
