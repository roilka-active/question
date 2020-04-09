package training.tools.javase.generic;

import training.tools.javase.Employee;
import training.tools.utils.SysTools;

import java.util.ArrayList;
import java.util.List;

/**
 *  泛型测试
 */
public class GenericTest {

    public static void main(String[] args) {
        Pair<Integer> p1 = new Pair<>();
        Pair<String> p2 = new Pair<>();
        SysTools._out(p1 instanceof  Pair<?>);
        SysTools._out(p2 instanceof  Pair);
        SysTools._out(p1.getClass() == p2.getClass());
        p1.setFirst(2);
        SysTools._out(p1.equals(p2) );
        SysTools._out(p1.toString());

        Pair<String>[] table = new Pair[10];
        Object[] objArr = table;
        objArr[1] = new Pair<Employee>();

        int a = 1024;
        SysTools._out(a & 512);
    }
}
