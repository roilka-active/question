package training.tools.filter;/**
 * Package: training.tools.filter
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/7/31 11:53
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */


import com.google.common.collect.Lists;
import lombok.Data;
import training.tools.utils.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanghui
 * @description
 * @date 2019/7/31
 */

public class FilterTest {

    public static void main(String[] args) throws Exception {
        /*FilterTest filterTest = new FilterTest();
        long start = System.currentTimeMillis();
        String ss = filterTest.filterKeyWord("");
        long end = System.currentTimeMillis();
        long sub = end - start;
        System.out.println("本次过滤总消耗："+sub+"毫秒");
        System.out.println(ss);*/

        List<User> users = Lists.newArrayList();
        users.add(new User(0, "zhangsan", 40));
        users.add(new User(1, "lisi", 30));
        users.add(new User(2, null, 90));
        users.add(new User(3, "zhangsan", 20));
        users.add(new User(4, "wangwu", 18));
        users.add(new User(5, "zhaoliu", 9));
        users.add(new User(2, null, 30));
        users.sort(Comparator.comparing(User::getAge));
        final String collect = users.stream().map(User::getName).collect(Collectors.joining("-"));
        users.stream().filter(record -> record.getName() == null).forEach(re -> re.setName(collect));
        System.out.println(collect);
        System.out.println(users);

    }


    public String filterKeyWord(String content) throws Exception {
        List<String> testSuit = new ArrayList<String>();
        long length = 0;
        //String text ="<p>我是html标签</p><script src=\"\"></script>/naaa国家级";
        /*String[] texts = content.split("</p>");
        for (String str : texts) {
            str = BigFileCopy.filterHtml(str);
            testSuit.add(str);
        }
        length = content.length();
        say.info(String.format("待过滤文本共 %d 行，%d 个字符。", testSuit.size(), length));*/
        //String text = "就开始的恢复健康合肥会蒲团计拉萨的回复ID是很过分的抗衰老换个房间都是法规的数量和热内割发代首高科技反倒是股份第三个号发快递";
        testSuit.add(content);
        SensitiveFilter filter = SensitiveFilter.DEFAULT;
        //自定义添加过滤字符配置文件中输入 用英文  ; 隔开(业务需要自定义过滤词汇)
		/*SystemConfService confService = new SystemConfService();
		String filterValues = confService.getConfingvalueByKey("cmsfiltervalue");
		if(StringUtils.isNotBlank(filterValues)){
			for (String filterValue : filterValues.split(";")) {
				if(StringUtils.isNotBlank(filterValue)){
					filter.put(filterValue);
				}
			}
		}*/
		long startTime = System.currentTimeMillis();
        int replaced = 0;
        int linetext = 0;
        String infolog = "";
        for (String sentence : testSuit) {
            sentence = sentence.replace(" ", "");
            String result = filter.filter(sentence, '`');
            linetext++;
            if (result != sentence) {
                replaced++;
                char[] charArray1 = result.toCharArray();
                char[] charArray2 = sentence.toCharArray();
                String res = "";
                for (int i = 0; i < charArray2.length; i++) {
                    if (charArray1[i] != charArray2[i] && StringUtils.isNotBlank(charArray1[i] + "") && StringUtils.isNotBlank(charArray2[i] + "")) {
                        res += charArray2[i];
                    }
                }
                infolog += "第" + linetext + "行, " + res + " 为敏感字汇,请修改！";
                say(infolog);
            }
        }
        say(String.format("其中 %d 行有替换", replaced));
        if (replaced != 0) {
            long endTime = System.currentTimeMillis();
            long subTime = endTime-startTime;
            System.out.println("过滤消耗时间为："+subTime+"毫秒");
            return infolog;
        }
        return "";

    }

    void say(Object object) {
        System.out.println(object);
    }
}
@Data
class User{
    private int id;
    private String name;
    private int age;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}