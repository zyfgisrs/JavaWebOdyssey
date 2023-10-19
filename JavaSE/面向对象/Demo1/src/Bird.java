public class Bird extends Animal{
    @Override
    public void sayHello() {
        System.out.println("ssss");
        super.sayHello();
        eat();
    }

    public static void main(String[] args) {
        Bird bird = new Bird();
        bird.sayHello();
    }
}
