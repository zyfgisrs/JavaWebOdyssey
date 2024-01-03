public class AnotherClass {
    public void invokeMethod(){
        OuterClass outerClass = new OuterClass();

        OuterClass.InnerClass innerClass = outerClass.new InnerClass();
    }
}
