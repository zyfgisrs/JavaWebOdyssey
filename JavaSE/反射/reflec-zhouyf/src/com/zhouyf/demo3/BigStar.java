package com.zhouyf.demo3;

public class BigStar implements Star{

    private String name;

    public BigStar(String name) {
        this.name = name;
    }

    @Override
    public String sing() {
        System.out.println(name + "演唱会表演~~~~~~");
        return "谢谢";
    }

    @Override
    public String dance() {
        System.out.println(name + "舞蹈表演~~~~~~");
        return "谢谢";
    }
}
