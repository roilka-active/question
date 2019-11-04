package training.tools.javase.collection;

import training.tools.javase.Employee;
import training.tools.utils.SysTools;

import java.util.*;
import java.util.Queue;

public class LinkedListTest {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        ArrayList<Integer> arrList = new ArrayList<>();

        Collections.sort(list);

        Map<String, Employee> map = Collections.synchronizedMap(new HashMap<>());
        //itratorOut(list);
        //insertCollection();

    }
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[nums.length];
        for(int i=0;i<nums.length;i++){
            result[i] = nums[i] - target;
        }
        return result;
    }
    public static  void  insertCollection(){
        LinkedList<Integer> linkedList = new LinkedList();
        for (int i = 0; i <20 ; i++) {
            linkedList.add(i);
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(-1*i);
        }
        ListIterator<Integer> listIterator = linkedList.listIterator();
        Iterator<Integer> iterator = arrayList.iterator();
        while (listIterator.hasNext()){
            listIterator.next();
        }
        for (int i = linkedList.size() - 1; i >= 0 ; i--) {

        }
        List<Integer> result = new LinkedList<>();
        while (listIterator.hasPrevious() && iterator.hasNext()){
            listIterator.add(iterator.next());
            listIterator.previous();
            listIterator.previous();
            listIterator.remove();
        }
        for (;listIterator.hasPrevious();listIterator.hasPrevious(),listIterator.hasPrevious()){

        }
        SysTools._out(linkedList);
    }
    public static <T> void itratorOut(LinkedList<T> list){
        Iterator<T> iterator = list.iterator();
        iterator.remove();
        while (iterator.hasNext()){
            SysTools._out(iterator.next());
        }
    }

    public static void compareList(){
        LinkedList<Integer> list = new LinkedList<>();
        ArrayList<Integer> arrList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add((int)Math.random()*10000);
            arrList.add((int)Math.random()*10000);
        }
        long start = System.currentTimeMillis();
        double t= 0L;
        int s = 0;
        for (Integer i : list){
            t = Double.valueOf(Math.floor(i.doubleValue() * 2 / 3)).intValue();
        }
        SysTools._out(System.currentTimeMillis() - start ,"linkedList 查询消耗"," ms");
        long begin = System.currentTimeMillis();
        for (Integer i : list){
            s = i * 2 / 3;
        }
        SysTools._out(System.currentTimeMillis() - begin ,"ArrayList 查询消耗"," s");

    }

    public static <T extends Comparable> T max(Collection<T> c){
        if (c == null || c.isEmpty()){
            throw new NoSuchElementException();
        }
        Iterator<T> iterator = c.iterator();
        T largest = iterator.next();
        while (iterator.hasNext()){
            T text = iterator.next();
            if (largest.compareTo(text)  >= 0){
                continue;
            }
            largest = text;
        }
        return largest;
    }
}
