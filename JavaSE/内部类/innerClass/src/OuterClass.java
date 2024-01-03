public class OuterClass {
    private String name = "外部类属性";


    public class InnerClass{
        private String name = "内部类属性";

        private void show(){
            System.out.println(OuterClass.this.name);
            System.out.println(this.name);
        }
    }

    public void invokeInnerMethod(){
        InnerClass innerClass = new InnerClass();
        innerClass.show();
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        outerClass.invokeInnerMethod();
    }
}


