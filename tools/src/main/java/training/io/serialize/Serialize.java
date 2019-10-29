package training.io.serialize;

import training.tools.utils.SysTools;

import java.io.*;
import java.util.ArrayList;

public class Serialize implements Serializable {


    private static final long serialVersionUID = 1785067145129187120L;

    public int sum = 1390;

    static final String prePath = "D:\\GIT\\owner\\Manager\\training\\tools\\src\\main\\resources";
    public static void main(String[] args) {
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos = new FileOutputStream(prePath + "\\sensi_words.txt");
            oos = new ObjectOutputStream(fos);
            Serialize serialize = new Serialize();
            oos.writeObject(serialize);
            SysTools._out(oos);
            oos.flush();
            oos.close();
        }catch (FileNotFoundException e){

        }catch (IOException e){

        }
    }
}
