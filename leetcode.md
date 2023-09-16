# 

# 1. 计算两数之和

给定一个整数数组 `nums` 和一个整数目标值 `target`，请你在该数组中找出 和为目标值 `target` 的那 两个 整数，并返回它们的数组下标。你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。

- 暴力枚举

```java
class Solution {
  public int[] twoSum(int[] nums, int target) {
      int[] res = new int[2];
      for (int i = 0; i < nums.length; i++) {
          int a = nums[i];
          int b = target - a;
          for (int j = i + 1; j < nums.length; j++) {
              if (nums[j] == b) {
                  res[0] = i;
                  res[1] = j;
              }
          }
      }
      return res;
  }
}
```
```java
class Solution {
  public int[] twoSum(int[] nums, int target) {
      int n = nums.length;
      for (int i = 0; i < n; ++i) {
          for (int j = i + 1; j < n; ++j) {
              if (nums[i] + nums[j] == target) {
                  return new int[]{i, j}; //数组简写
              }
          }
      }
      return new int[0]; //返回一个空数组
  }
}
```

```java
public class Solution {
  public static void main(String[] args) {
      Solution s = new Solution();
      int a = 9;
      int[] b = {1, 2, 3, 5, 7, 9};
      System.out.println((Arrays.toString(s.twoSum(b, a))));
  }

  public int[] twoSum(int[] nums, int target) {
      HashMap<Integer, Integer> map = new HashMap<>();
      for (int i = 0; i < nums.length; i++) {
          if (map.containsKey(target - nums[i])) {
              return new int[]{i, map.get(target - nums[i])};
          }//使用这种形式创建数组
          map.put(nums[i], i);
      }
      return new int[0];
  }
}
```

------

# 6. N字型变换

```java
public static String convert(String s, int numRows) {
    if (numRows == 1) {
        return s;
    }
    ArrayList<StringBuffer> list = new ArrayList<>();
    for (int i = 0; i < numRows; i++) {
        list.add(new StringBuffer());
    }
    int i = 0;
    int flag = -1;
    for (char c : s.toCharArray()) {
        list.get(i).append(c);
        if (i == numRows - 1 || i == 0) {
            flag = -flag;
        }
        i += flag;
    }

    String res = "";
    for (StringBuffer stringBuffer : list) {
        res = res + stringBuffer;
    }
    return res;
}
```



#   9 回文数

给你一个整数 `x` ，如果 `x` 是一个回文整数，返回 `true` ；否则，返回 `false` 。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。

```java
class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        if (x < 10 ) {
            return true;
        }

        String s = x + "";
        char[] c = s.toCharArray(); //字符串转char数组


        for (int i = 0; i < c.length / 2; i++) {
            if (c[i] != c[c.length - i - 1]) {
                return false;
            }
        }
        return true;
    }
}
```

int 转 String方法

```java
//使用String方法
int a = 111;
String s = String.valueOf(111);
System.out.println(s);
```

```java
//使用+
int a = 111;
String s = a + "";
System.out.println(s);
```

```java
//使用包装类Integer方法
String s = Integer.toString(111);
System.out.println(s);
```

------

# DFS算法



# BFS算法

