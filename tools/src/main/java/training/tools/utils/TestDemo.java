package training.tools.utils;

import training.entity.EmployeeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName TestDemo
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/14 15:12
 **/
public class TestDemo {
    public static void main(String[] args) {
        List<EmployeeInfo> resultlist = new ArrayList<>();
        EmployeeInfo info = new EmployeeInfo();
        info.setId(1);
        info.setAccountId(3);
        resultlist.add(info);
        info = new EmployeeInfo();
        info.setId(2);
        info.setAccountId(3);
        resultlist.add(info);
        info = new EmployeeInfo();
        info.setId(3);
        info.setAccountId(4);
        resultlist.add(info);
        info = new EmployeeInfo();
        info.setId(4);
        info.setAccountId(5);
        resultlist.add(info);

        Map<Integer, EmployeeInfo> map = resultlist.stream().collect(Collectors.toMap(EmployeeInfo::getAccountId, Function.identity(), (key, val) -> val));
        SysTools._out(map);
    }
}
