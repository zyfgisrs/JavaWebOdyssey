# Random

Java中的`Random`类用于生成伪随机数。它位于`java.util`包中，提供了多种方法来生成不同类型的随机数。以下是`Random`类的一些主要API及其使用方法：

## 构造函数

- **Random()**：创建一个新的随机数生成器。
- **Random(long seed)**：使用指定的种子创建一个新的随机数生成器。种子是随机数生成过程的起点，相同的种子会产生相同的随机数序列。

## 基本方法

- **nextBoolean()**：返回下一个伪随机的布尔值。
- **nextInt()**：返回下一个伪随机的整数。
- **nextInt(int bound)**：返回一个伪随机的、介于0（包含）和指定上限`bound`（不包含）之间的整数。
- **nextLong()**：返回下一个伪随机的长整型数。
- **nextDouble()**：返回下一个伪随机的、介于0.0（包含）和1.0（不包含）之间的双精度数。
- **nextFloat()**：返回下一个伪随机的、介于0.0（包含）和1.0（不包含）之间的单精度数。
- **nextBytes(byte[] bytes)**：生成随机字节并将其放入用户提供的字节数组中。

### 使用示例

```java
import java.util.Random;

public class RandomExample {
    public static void main(String[] args) {
        Random random = new Random(); // 创建Random对象

        // 生成不同类型的随机数
        int randomInt = random.nextInt(); // 生成一个随机整数
        int randomIntWithBound = random.nextInt(100); // 生成一个0到99的随机整数
        long randomLong = random.nextLong(); // 生成一个随机长整型数
        boolean randomBoolean = random.nextBoolean(); // 生成一个随机布尔值
        double randomDouble = random.nextDouble(); // 生成一个随机双精度数
        float randomFloat = random.nextFloat(); // 生成一个随机单精度数

        // 打印生成的随机数
        System.out.println("Random Int: " + randomInt);
        System.out.println("Random Int with Bound (0-99): " + randomIntWithBound);
        System.out.println("Random Long: " + randomLong);
        System.out.println("Random Boolean: " + randomBoolean);
        System.out.println("Random Double: " + randomDouble);
        System.out.println("Random Float: " + randomFloat);
    }
}
```

在此示例中，我们创建了一个`Random`对象，并使用它来生成各种类型的随机数。

## 注意事项

- 在需要高度安全的随机数时（例如密码或安全密钥生成），应使用`java.security.SecureRandom`类，而不是`Random`类。
- 使用相同种子（seed）的`Random`对象将生成相同的随机数序列。这在某些情况下有用，例如重现测试场景。