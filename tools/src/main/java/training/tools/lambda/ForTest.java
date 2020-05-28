package training.tools.lambda;

import training.tools.javase.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName ForTest
 * @Author Roilka
 * @Description 测试for循环效率
 * @Date 2020/2/26
 */
public class ForTest {
    public static void main(String[] args) {
        List<Employee> userList = initList(10);
        for (int i = 1; i < 2; i++) {
            System.out.println("--------------------第" + i + "次");
            long t1 = System.nanoTime();

            testLambda(userList);
            long t2 = System.nanoTime();
            testForeach(userList);
            long t3 = System.nanoTime();
            System.out.println("lambda---" + (t2 - t1) / 1000 + "μs");
            System.out.println("增强for--" + (t3 - t2) / 1000 + "μs");
        }
    }

    /**
     * 增强for测试
     * @param userList
     */
    private static void testForeach(List<Employee> userList) {
        for (Employee user : userList) {
            user.setSalary(user.getSalary() + 1);
            for (Employee te : userList){
                te.hashCode();
            }
        }
    }


    /**
     * lambda forEach测试
     * @param userList
     */
    private static void testLambda(List<Employee> userList) {
        userList.forEach(user -> {
            user.setSalary(user.getSalary() + 1);
            userList.forEach(h -> h.hashCode());
        });
        Stream<Employee> peek = userList.stream().peek(e -> e.setSalary(e.getSalary() + 1));
        peek.forEach(System.out::println);
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
    }

    /**
     * 初始化测试集合
     * @param size
     * @return
     */
    private static List<Employee> initList(int size) {
        List<Employee> userList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            userList.add(new Employee(i));
        }
        return userList;
    }

}
