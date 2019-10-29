package training.tools.javase.collection;

import training.tools.javase.Employee;
import training.tools.javase.Manager;
import training.tools.javase.generic.Pair;
import training.tools.utils.SysTools;

import java.util.*;

public class CollectionTest {
    public static void main(String[] args) {

        List<Integer> aList = new ArrayList<>();
        List<Integer> bList = new ArrayList<>();
        for (int i = 0; i < 20000 ; i++) {

        }
        Class obj = Manager.class.getSuperclass();
        SysTools._out(obj);

        int a = 15;
        SysTools._out(a >> 1);
        Pair<String>[] table = new Pair[10]; // ERROR
        Object[] objArray = table;
        objArray[0] = new Pair<Employee>();
        table[0].getFirst();




    }

    public  static  void testLinkList(){
        List<String> staff = new LinkedList<>();
        staff.add("Amy");
        staff.add("Bob");
        staff.add("Carl");
        Iterator<String> iter = staff.iterator();
        String first = iter.next();
        String second = iter.next();
        iter.remove();
    }

    public static void testQueue(){
        Deque<String> queue = new ArrayDeque<String>();
        queue.add("文");
        queue.add("承");
        queue.add("武");
        queue.add("略");

        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()){
            SysTools._out(iterator.next(),"这是迭代结果");
        }
    }
}
