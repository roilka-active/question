package training.io;/**
 * Package: training.io
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/10/14 22:37
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import training.tools.utils.SysTools;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author zhanghui
 * @description IO测试
 * @date 2019/10/14
 */
public class IOTest {

    final String SrcPath = this.getClass().getResource("/").getPath();
    static final String prePath = "D:\\GIT\\owner\\Manager\\training\\tools\\src\\main\\resources";
    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();
        String path =System.getProperty("java.class.path");
        FileInputStream fin = new FileInputStream( prePath + "\\sensi_words.txt");
        DataInputStream din = new DataInputStream(fin);
        double s = din.readDouble();
        SysTools._out(s,"这是文件里读出来的：");
        SysTools._out(System.currentTimeMillis() - start,"消耗时间：");
        FileReader fileReader = new FileReader(new File(prePath + "\\sensi_words.txt"));
        SysTools._out(fin.getFD(),"文件描述");


       /* InputStreamReader in = new InputStreamReader(System.in);

        SysTools._out(in.read());*/
    }
}
