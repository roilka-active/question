package training.tools.javase.collection;

import com.fasterxml.jackson.databind.util.LinkedNode;
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
        TreeSet treeSet = new TreeSet();
        treeSet.add(6);
        treeSet.add(7);
        SysTools._out(treeSet.higher(5));



    }

    /*
      查找 2 -- 2000000 数字范围内的所有素数
     */
    public static  void  testBitSet(){
        final int n = 2000000;
        long start = System.currentTimeMillis();
        BitSet b =new BitSet(n+1);
        int count = 0;
        int i ;
        for ( i = 2; i <= n; i++) {
            b.set(i);
        }
        i = 2;
        while (i * i <= n){
            if (b.get(i)){
                count ++;
                int k = 2* i;
                while (k <= n){
                    b.clear(k);
                    k += i;
                }
            }
            i++;
        }
        while (i <= n){
            if (b.get(i)){
                count++;

            }
            i++;
        }
        long end = System.currentTimeMillis();
        SysTools._out(count,null," primes");
        SysTools._out((end - start),null," milliseconds");
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
