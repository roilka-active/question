package training.tools.utils;

public class SysTools {

    public static <T> void _out(T obj,String... message){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < message.length; i++) {
            stringBuilder.append(message[i]);
        }
        System.out.println(stringBuilder+":"+obj);
    }

    public static void main(String... args){
        String s =System.getProperty("user.dir");
        _out(s);
    }
}
