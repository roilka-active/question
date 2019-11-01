package training.tools.javase.collection;

import training.tools.javase.Employee;
import training.tools.utils.SysTools;

import java.util.*;

public class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueue<GregorianCalendar> pq = new PriorityQueue<>();
        pq.add(new GregorianCalendar(1999, Calendar.DECEMBER,9));
        pq.add(new GregorianCalendar(2013, Calendar.DECEMBER,4));
        pq.add(new GregorianCalendar(1845, Calendar.DECEMBER,10));
        pq.add(new GregorianCalendar(1769, Calendar.JULY,12));

        SysTools._out("Iterating over elements ...");
        for (GregorianCalendar date : pq){
            SysTools._out(date.get(Calendar.YEAR));
        }
        SysTools._out("Removing elements");
        while (!pq.isEmpty()){
            SysTools._out(pq.remove().get(Calendar.YEAR));
        }
        WeakHashMap<String,Integer> map = new WeakHashMap();
        map.put("a", 123);
        map.put("c", 212);
        Iterator<Map.Entry<String,Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Integer> subMap = iterator.next();
            SysTools._out(" : ",subMap.getKey(),subMap.getValue());
        }
        Employee[] employees = new Employee[10];
        employees[0] = new Employee(34L);
        employees[1] = new Employee(23L);
        List<Employee> employeeList = Arrays.asList(employees);
        Iterator<Employee> iterator1 = employeeList.iterator();
        iterator1.next();
        while (iterator1.hasNext()){
            SysTools._out(iterator1.next());
        }
    }
}
