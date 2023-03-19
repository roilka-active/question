package com.roilka.roilka.question.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

//    public static void main(String[] args) {
//        //测试SpringEL解析器
//        String template = "#{#user}，早上好";//设置文字模板,其中#{}表示表达式的起止，#user是表达式字符串，表示引用一个变量。
//        ExpressionParser paser = new SpelExpressionParser();//创建表达式解析器
//
//        //通过evaluationContext.setVariable可以在上下文中设定变量。
//        EvaluationContext context = new StandardEvaluationContext();
//        context.setVariable("user", "黎明");
//
//        //解析表达式，如果表达式是一个模板表达式，需要为解析传入模板解析器上下文。
//        Expression expression = paser.parseExpression(template, new TemplateParserContext());
//
//        //使用Expression.getValue()获取表达式的值，这里传入了Evalution上下文，第二个参数是类型参数，表示返回值的类型。
//        System.out.println(expression.getValue(context, String.class));
//
//        String pl = "你好呀，";
//        String[] arr ={};
//        System.out.println(getMsg(pl,arr));
//        String[] arr1 ={"zhangsan","","sd"};
//        System.out.println(getMsg(pl,arr1));
//        String[] arr2 ={"zhangsan",null,"sd"};
//        System.out.println(getMsg(pl,arr2));
//        getMsg(pl, new String[]{"s"});
//    }

    public static String getMsg(String placeHolder, String... arg) {
        MessageFormat messageFormat = new MessageFormat(placeHolder);

        return messageFormat.format(arg);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Long timestamp = System.currentTimeMillis();
        String secret = "ZSEC9414792a45eebffff04f738a1fc91a4ca644ec5aa160e6985f2f890ac2ad0f37";

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        System.out.println(sign);
    }
}
