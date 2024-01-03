package anonymous;

//使用匿名内部类演示不同动物游泳
public class Test {
    public static void main(String[] args) {

        new Animal().performSwim(new Swimming() {
            @Override
            public void swim() {
                System.out.println("狗在游泳");
            }
        });

        new Animal().performSwim(new Swimming() {
            @Override
            public void swim() {
                System.out.println("猫在游泳");
            }
        });
    }
}

interface Swimming {
    void swim();
}

class Animal{

    public void performSwim(Swimming s){
        System.out.println("开始游泳.....");
        s.swim();
    }
}