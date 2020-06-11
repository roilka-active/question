package com.roilka.roilka.question.common.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ObjectUtils
 * @Description 对象常用工具类
 * @Author zhanghui1
 * @Date 2020/6/3 16:28
 **/
public class ObjectUtils {

    public static <T> Map<String, Object> objectToMap(T t) {
        Class<?> aClass = t.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        Map<String, Object> collect = Arrays.stream(declaredFields).collect(Collectors.toMap(Field::getName, field -> {
            field.setAccessible(true);
            try {
                return field.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }

        }));
        return collect;
    }

    public static void main(String[] args) {
        //测试SpringEL解析器
        String template = "#{#user}，早上好";//设置文字模板,其中#{}表示表达式的起止，#user是表达式字符串，表示引用一个变量。
        ExpressionParser paser = new SpelExpressionParser();//创建表达式解析器

        //通过evaluationContext.setVariable可以在上下文中设定变量。
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", "黎明");

        //解析表达式，如果表达式是一个模板表达式，需要为解析传入模板解析器上下文。
        Expression expression = paser.parseExpression(template, new TemplateParserContext());

        //使用Expression.getValue()获取表达式的值，这里传入了Evalution上下文，第二个参数是类型参数，表示返回值的类型。
        System.out.println(expression.getValue(context, String.class));

        String pl = "你好呀，";
        String[] arr ={};
        System.out.println(getMsg(pl,arr));
        String[] arr1 ={"zhangsan","","sd"};
        System.out.println(getMsg(pl,arr1));
        String[] arr2 ={"zhangsan",null,"sd"};
        System.out.println(getMsg(pl,arr2));
        getMsg(pl, new String[]{"s"});
    }

    public static String getMsg(String placeHolder, String... arg) {
        MessageFormat messageFormat = new MessageFormat(placeHolder);

        return messageFormat.format(arg);
    }
}
