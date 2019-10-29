package training.tools.utils;

public class SysTools {

    public static <T> void _out(T obj,String... message){
        StringBuilder stringBuilder = new StringBuilder();
        if (message == null){}
        for (int i = 0; i < message.length; i++) {
            stringBuilder.append(message[i]);
        }
        _out(stringBuilder+":"+obj);
    }
    public static <T> void _out(T obj,Object before,Object after){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(before);
        stringBuilder.append(obj);
        stringBuilder.append(after);
       _out(stringBuilder);
    }
    public static <T> void _out(T obj){
        System.out.println(obj);
    }
    public static void main(String... args){
        String s =System.getProperty("user.dir");
        _out(s);
    }
}
