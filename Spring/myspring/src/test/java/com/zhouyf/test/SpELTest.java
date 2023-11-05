package com.zhouyf.test;

import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;

public class SpELTest {
    @Test
    public void test1(){
        String context1 = "#{\"zyf\" + \"slp\"}";
        String context2 = "#{'zyf' + 'slp'}";
        System.out.println(spel(context1));
        System.out.println(spel(context2));
    }

    @Test
    public void test2(){
        String context1 = "#{1.1}";
        String context2 = "#{1 + 1}";
        String context3 = "#{1.1 * 1}";
        String context4 = "#{true}";
        String context5 = "#{'zyf'}";
        System.out.println("结果" + spel(context1) + ", 类型为" +
                spel(context1).getClass().getName());
        System.out.println("结果" + spel(context2) + ", 类型为" +
                spel(context2).getClass().getName());
        System.out.println("结果" + spel(context3) + ", 类型为" +
                spel(context3).getClass().getName());
        System.out.println("结果" + spel(context4) + ", 类型为" +
                spel(context4).getClass().getName());
        System.out.println("结果" + spel(context5) + ", 类型为" +
                spel(context5).getClass().getName());
    }

    @Test
    //解析关系表达式
    public void test3(){
        String context1 = "#{10 eq 10}";
        String context2 = "#{1 gt 10}";
        String context3 = "#{2 le 1}";
        String context4 = "#{4 ge 3}";
        String context5 = "#{3 lt 4}";
        String context6 = "#{3 between {2,5}}";
        String context7 = "#{'zyf' == 'jjj'}";
        String context8 = "#{'zyf' eq 'zyf'}";
        String context9 = "#{10 < 11}";
        System.out.println("结果" + spel(context1) + ", 类型为" +
                spel(context1).getClass().getName());
        System.out.println("结果" + spel(context2) + ", 类型为" +
                spel(context2).getClass().getName());
        System.out.println("结果" + spel(context3) + ", 类型为" +
                spel(context3).getClass().getName());
        System.out.println("结果" + spel(context4) + ", 类型为" +
                spel(context4).getClass().getName());
        System.out.println("结果" + spel(context5) + ", 类型为" +
                spel(context5).getClass().getName());
        System.out.println("结果" + spel(context6) + ", 类型为" +
                spel(context6).getClass().getName());
        System.out.println("结果" + spel(context7) + ", 类型为" +
                spel(context7).getClass().getName());
        System.out.println("结果" + spel(context8) + ", 类型为" +
                spel(context8).getClass().getName());
        System.out.println("结果" + spel(context9) + ", 类型为" +
                spel(context9).getClass().getName());
    }

    @Test
    //解析逻辑表达式
    public void test4(){
        String context1 = "#{10 eq 10 and 10 lt 12}";
        String context2 = "#{11 gt 10 or 10 lt 12}";
        String context3 = "#{not(10 gt 11)}";
        System.out.println("结果" + spel(context1) + ", 类型为" +
                spel(context1).getClass().getName());
        System.out.println("结果" + spel(context2) + ", 类型为" +
                spel(context2).getClass().getName());
        System.out.println("结果" + spel(context3) + ", 类型为" +
                spel(context3).getClass().getName());
    }


    @Test
    //解析三目运算符
    public void test5(){
        String context1 = "#{10 == 10 ? 'zyf' : 'slp'}";
        String context2 = "#{11 < 9 ? 7.1 : 5}";
        String context3 = "#{'zyf' != null ? 'zyf' : 12}";
        System.out.println("结果" + spel(context1) + ", 类型为" +
                spel(context1).getClass().getName());
        System.out.println("结果" + spel(context2) + ", 类型为" +
                spel(context2).getClass().getName());
        System.out.println("结果" + spel(context3) + ", 类型为" +
                spel(context3).getClass().getName());
    }

    @Test
    //解析字符串处理
    public void test6(){
        String context1 = "#{'spel'.toUpperCase()}";
        String context2 = "#{'spel'[0]}";
        String context3 = "#{'spel'.substring(0,2)}";
        System.out.println("结果" + spel(context1) + ", 类型为" +
                spel(context1).getClass().getName());
        System.out.println("结果" + spel(context2) + ", 类型为" +
                spel(context2).getClass().getName());
        System.out.println("结果" + spel(context3) + ", 类型为" +
                spel(context3).getClass().getName());
    }

    @Test
    //解析Class表达式
    public void test7(){
        String context1 = "#{T(java.lang.String)}";
        String context2 = "#{T(java.util.Date)}";
        String context3 = "#{T(java.util.Arrays)}";
        String context4 = "#{T(java.lang.String).valueOf(123)}";
        String context5 = "#{T(java.util.Arrays).asList(1,2,3)}";
        System.out.println("结果" + spel(context1) + ", 类型为" +
                spel(context1).getClass().getName());
        System.out.println("结果" + spel(context2) + ", 类型为" +
                spel(context2).getClass().getName());
        System.out.println("结果" + spel(context3) + ", 类型为" +
                spel(context3).getClass().getName());
        System.out.println("结果" + spel(context4) + ", 类型为" +
                spel(context4).getClass().getName());
        System.out.println("结果" + spel(context5) + ", 类型为" +
                spel(context5).getClass().getName());
    }

    @Test
    void test8(){
        String exp = "#{#varA + #varB}";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp, ParserContext.TEMPLATE_EXPRESSION);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("varA", "zyf");
        context.setVariable("varB", "slp");
        String value1 = expression.getValue(context, String.class);
        System.out.println(value1);
        context.setVariable("varA", 111.1);
        context.setVariable("varB", 222);
        Double value2 = expression.getValue(context, Double.class);
        System.out.println(value2);
    }

    @Test
    void test9(){
        String context = "#{{'zyf','slp','lucy'}}";
        System.out.println("结果" + spel(context) + ", 类型为" +
                spel(context).getClass().getName());
    }


    @Test
    void test10(){
        String context = "#{{'zyf':111,'slp':222,'lucy':333}}";
        System.out.println("结果" + spel(context) + ", 类型为" +
                spel(context).getClass().getName());
    }



    public static Object spel(String context){
        SpelExpressionParser parser = new SpelExpressionParser();
        //使用#{...}作为表达式的定界符
        Expression expression = parser.parseExpression(context, ParserContext.TEMPLATE_EXPRESSION);
        //评估表达式，并返回结果
        return expression.getValue();
    }
}
