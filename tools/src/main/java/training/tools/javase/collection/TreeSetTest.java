package training.tools.javase.collection;

import training.tools.javase.collection.entity.Item;
import training.tools.utils.SysTools;

import java.util.*;
import java.util.Queue;

public class TreeSetTest {

    public static void main(String[] args) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (int i = 0; i < 100; i++) {
           int t = new Random().nextInt(100);
            treeSet.add(t);
        }
        int a =0;

        SysTools._out(treeSet.ceiling(30),"ceiling(30)");
        SysTools._out(treeSet.floor(30),"floor(30)");
        SysTools._out(treeSet.higher(30),"higher(30)");
        SysTools._out(treeSet.lower(30),"lower(30)");

        SysTools._out(treeSet.pollFirst(),"pollFirst()");
        SysTools._out(treeSet.pollLast(),"pollLast()");
        Iterator<Integer> iterator = treeSet.descendingIterator();
        SysTools._out(iterator.next());
        SysTools._out(iterator.next());
        SysTools._out(iterator.next());
        SysTools._out(iterator.next());
        ItemComparator comp = new TreeSetTest().new ItemComparator();
        SortedSet<Item> sortByDescription = new TreeSet<>();
        ArrayDeque<Integer> q = new ArrayDeque();
         q.addFirst(2);
         q.add(3);
         q.addLast(5);
         SysTools._out(q.offer(3),"offer(3)");
        SysTools._out(q.peek(),"peek()");
        SysTools._out(q.element(),"element()");
        SysTools._out(q.poll(),"poll()");
        SysTools._out(q,"Q ");
        while (q.size() >0){
            q.pollFirst();
            q.pollLast();
        }

    }
    public class ItemComparator implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            String descrA = o1.getDescription();
            String descrB = o2.getDescription();
            return descrA.compareTo(descrB);
        }
    }

}
