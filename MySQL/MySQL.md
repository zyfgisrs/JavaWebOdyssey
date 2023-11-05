# 连接到MySQL

- 连接到MySQL服务的（数据库）的指令为

  `mysql -h 主机IP -P 端口 -u用户名 -p密码`

1. -p密码不要有空格。
2. -p后面没有写密码，回车会要求输入密码。
3. 如果没有写-h主机，默认就是本机。
4. 如果没有写-P端口，默认就是3306。
5. 在实际工作中，3306一般修改。

# 数据库结构

- 所谓安装数据库，就是在主机上安装一个数据库管理系统（DBMS），这个管理程序可以管理多个数据库。
- 一个数据库中可以创建多个表，以保存数据。
- 数据库管理系统（DBMS）、数据库和表的关系如图所示。

- 表的一行称之为一条记录，在java程序中，一行记录往往使用对象来表示。

# SQL语句分类

- DDL：数据定义语句 （create表，库）
- DML：数据操作语句 （增加insert，修改update，删除delete）
- DQL：数据查询语句 （select查询）
- DCL：数据控制语句 （管理数据库：比如用户授权 grant，revoke）

# 创建数据库

```mysql
CREATE DATABASE[IF NOT EXISTS] db_name
	[create_specification..]
```

`create_specification`:

- `CHARACTER SET`：指定数据库采用字符集，如果不指定字符集，默认utf8
- `COLLATE`：指定数据库字符集的==校对规则==（常用的`utf8_bin`【区分大小写】、`utf8_general_ci`【不区分大小写】注意：==默认的是`utf8_general_ci`==）

```mysql
#删除数据库
DROP DATABASE zyf_db1;
#数据库操作
#创建一个名称为zyf_db01的数据库
CREATE DATABASE zyf_db01
#创建一个utf8字符集的zyf_db02数据库
CREATE DATABASE zyf_db02 CHARACTER SET utf8
#创建一个使用utf8字符集，并带校验规则的zyf_db03数据库
CREATE DATABASE zyf_db03 CHARACTER SET utf8 COLLATE utf8_bin
```

##校对规则

1. `utf8_bin`区分大小写。
2. 默认的`utf8_general_ci`不区分大小写。

# 查看、删除数据库

```mysql
#查看当前数据库服务器中的所有数据库
SHOW DATABASES;
#查看前面创建的zyf_db01数据库的定义信息
SHOW CREATE DATABASE `zyf_db01`;
#在创建数据库，表的时候，为了规避关键字，可以使用反引号解决。
CREATE DATABASE `CREATE`
#删除数据库
DROP DATABASE `CREATE`
```

# 备份恢复数据库

## 整库

1. `mysqldump -u root -pzyf2563085 -B zyf_db01 zyf_db02 > d:\\bac.sql`
2. 进入数据库 `mysql -u root -p`
3. `source d:\\bac.sql`

##某个数据库的表

1. `mysqldump -u root -pzyf2563085 zyf_db01 t1 t2 > d:\\back.sql`省略了-B
2. 进入指定的数据库
3. 执行`source d:\\back.sql`

# 创建表、删除表

```mysql
#创建表
CREATE TABLE `USER` (
			id INT,
			`name` VARCHAR(255),
			`password` VARCHAR(255),
			`birthday` DATE)
			CHARACTER SET utf8 COLLATE utf8_bin ENGINE INNODB
			
#删除表
DROP TABLE `USER`
```

- CHARACTER SET：如果不指定则为所在数据库字符集
- COLLATE：如不指定则为所在数据库校对规则

#修改表

**修改表是对表名修改，修改表中的列名，修改表中列的数据格式，删除列，增加列，删除表，修改字符集，并不对表中的元素进行修改。**

```mysql
CREATE TABLE `demo`(
		`name` VARCHAR(20),
		`ID` INT,
		`sex` CHAR(1),
		`age` TINYINT,
		`salary` DOUBLE,
		`Brithday` DATE) CHARSET utf8 COLLATE utf8_bin ENGINE INNODB;
	
INSERT INTO `demo` VALUES('jack',1,'男',23,40000,'1998-05-04')


#添加列
ALTER TABLE `demo`
                ADD `address` VARCHAR(32) NOT NULL DEFAULT '' 
                AFTER salary;

#修改列的数据类型
ALTER TABLE `demo`
            MODIFY `name` VARCHAR(30) NOT NULL DEFAULT '' ;

ALTER TABLE `demo`
            DROP `sex`  #删除列

RENAME TABLE `demo` to `demo1`; #修改表名

ALTER TABLE demo CHARACTER SET utf8; #修改字符集

#修改列名和列的数据类型
ALTER TABLE `demo` 
        CHANGE `name` `user_name` VARCHAR(64) NOT NULL;

desc demo;

DROP TABLE `demo`;
```



# MySQL数据类型

## 1. 数值类型

###1.1 整形

**1.1.1 tinyint**

- 一个字节
- 范围带符号的-128-127
- 范围无符号的0-255

**1.1.2 smallint**

- 2个字节
- 范围带符号的：-32768-32767
- 无符号的：0-65535

**1.1.3 mediumint**

- 三个字节

**1.1.4 int**

- 四个字节

**1.1.5 bigint**

- 八个字节

### 1.2 小数类型

**float**

- 单精度，4个字节

**double**

- 双精度，8个字节

**decimal[M,D]**

- 大小不确定
- M是小数位数的总数，M最大为65。
- D是小数点后面的位数，D最大为30。
- 如果D被省略默认是0，M被省略，默认是10。
- 如果希望小数精度高，推荐使用decimal

## 2. 文本

**2.1 char**

- 0-255
- 固定长度字符串，最大255==字符==。
- char(4)，这个4表示字符数，不是字节数，不管中文还是英文都是放四个。
- char(4)是定长，就是说，即使插入”aa“，也会占用分配的4个字符空间。空着的空间使用默认字符填充。
- varchar(4)是变长，就是说，如果插入了’aa‘，实际占用的空间大小并不是4个字符，而是按照实际占用空间来分配。（varchar本身还需要占用1-3个字节来疾苦存放内容的长度。）

**2.2 varchar**

- 可变长度字符串
- 0-65535【0 - 2^16-1】字节
- 最大65532==字节==（utf8编码最大21844字符，1-3个字节用于记录大小）
- 如果表的编码是GBK，最大32766字符
- varchar(4)这个4表示字符数，不管是字母还是中文都以定义好的表的编码来存放数据utf8，这4个字符占用多少个字节他取决于编码。

```mysql
#演示字符串类型
CREATE TABLE t7(
		`name` CHAR(255))
		
CREATE TABLE t8(
		`name` VARCHAR(21844))

CREATE TABLE t9(
		`name` VARCHAR(32766)) CHARSET gbk;
```

- 什么时候用char，什么时候用varchar
  1. 如果数据定长，推荐使用char，比如md5的密码，邮编，手机号，身份证号等，用char
  2. 如果一个字段的长度是不确定的，我们使用varchar，比如留言，文章。

text

- 0 - 2^16-1
- 存放文本时，也可以使用Text数据类型，可以将TEXT列视为VARCHAR列，注意Text不能有默认值。
- 如果希望存放更多的字符，可以选择MEDIUMTEXT或者LONGTEXT。

mediumtext

- 0 - 2^24-1

longtext 

- 0 - 2^32-1

```mysql
CREATE TABLE t12(
	`content1` TEXT,
	`content2` MEDIUMTEXT,
	`content3` LONGTEXT)

INSERT INTO t12 VALUES('东北师范大学','东北师范大学11','东北师范大学111')
```

## 3. 二进制数据类型

**3.1 blob**

- 0 - 2^16-1

**3.2 longblob**

- 0 - 2^32-1

## 4. 日期类型

**4.1 date**

- 存放年月日

**4.2 time**

- 存放时分秒

**4.3 datetime**

- 8个字节

- 存放年月日，时分秒。
- YYYY-MM-DD HH:mm:ss

**4.4 year**

- 存放年

**4.5 timestamp**

- 四个字节

- 时间戳

## 5. bit类型

- bit（m） m在1-64。
- 添加数据的范围按照你给的位数来确定，比如m=8表示一个字节0~255。
- 显示按bit显示。
- 查询时，任然可以按照数字查询

```mysql
CREATE TABLE t5 (num BIT(8));
INSERT INTO t5 VALUES(1);
INSERT INTO t5 VALUES(3);
INSERT INTO t5 VALUES(12);
SELECT * FROM t5;
```

![image-20221003092551617](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221003092551617.png)

```mysql
SELECT * FROM t5 WHERE num = 12;
```

![image-20221003092907663](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221003092907663.png)

## 6. 创建表课堂练习

```mysql
#练习题 创建表
#创建一个员工表，选用适当的数据类型
CREATE TABLE emp(
		`ID` INT,
		`name` VARCHAR(12),
		`sex` CHAR(1),
		`brithday` DATE,
		`entry_date` DATETIME,
		`job` VARCHAR(32),
		`Salary` DOUBLE,
		`resume` TEXT) CHARSET utf8 COLLATE utf8_bin ENGINE INNODB;
```

# CUDR

## 1. insert语句

```mysql
CREATE TABLE emp(
		`ID` INT,
		`name` VARCHAR(12),
		`sex` CHAR(1),
		`brithday` DATE,
		`entry_date` DATETIME,
		`job` VARCHAR(32),
		`Salary` DOUBLE,
		`resume` TEXT) CHARSET utf8 COLLATE utf8_bin ENGINE INNODB;
	
INSERT INTO `emp` (`ID`,`name`,`sex`,`brithday`,`entry_date`,`job`,`Salary`,`resume`)
 VALUES (1,'jack','男','2000-1-1','2021-10-10 18:00:09','厨师',10000,'123')
```

- 插入的数据应该与字段的数据类型相同。
- 数据的长度应该在列的规定范围内。
- 在VALUES中列出的数据位置必须与被加入的列的排列位置相对应。
- 列可以插入控制（前提是允许为空）。
- `insert into tab_name (列名...)  values (),(),()`形式添加多条记录
- 字符和日期类型的数据应包含在单引号中。
- 如果是表中的所有字段添加数据，可以不写前面的字段名称。

- 默认值的使用，当不给某个字段值时，如果有默认值就会添加，否则报错。如果某个列没有指定NOT NULL，那么当添加数据时，没有给定值，则会默认给NULL。

## 2. update语句

```mysql
UPDATE emp SET Salary = 50000;
#没有指定where，对所有员工进行操作
```

```mysql
UPDATE emp SET Salary = 500000
			WHERE name = 'zyf'
```

```mysql
UPDATE emp SET Salary = Salary + 10000
			WHERE name = 'zyf'
#zyf的薪水在原有基础上增加1000
```

- UPDATE语法可以用新值更新原有的表行中的各列
- SET子句指示要修改哪些列和要给予哪些值
- WHERE子句指定应更新哪些行。如果没有WHERE子句，则更新所有的行。
- 如果需要修改多个字段，可以通过`set 字段1 = 值1，字段2 = 值2...`的语句去修改。

```mysql
UPDATE emp SET Salary = Salary + 10000 ,`job`='科学家'
			WHERE name = 'zyf'
```

## 3. Delete语句

```mysql
DELETE FROM `emp`
			WHERE `name` = 'zyf';
#删除名字为zyf的记录

#删除名字为NULL的记录
DELETE FROM `demo`
	WHERE `name` is Null;  #使用=号不能删除
```

- 如果不使用where语句，将删除表中所有的数据（表还在，只是删除了记录）。
- DELETE语句不能删除某一列的值。
- 使用delete语句仅仅删除记录，不删除表本身。如果要删除表，使用`drop table`语句，`drop table 表名`。

## 4. Select

```mysql
SELECT [DISTINCT] *|{column1, column2, column3..}
		FROM tablename;
```

- Select指定查询哪些列的数据
- column指定列名
- *号代表查询所有列
- From指定查询哪张表
- DISTINCT可选，指显示结果时，是否去掉重复数据。

```mysql
CREATE TABLE student(
			`id` INT NOT NULL DEFAULT 1,
			`name` VARCHAR(20) NOT NULL DEFAULT '',
			`chinese` FLOAT NOT NULL DEFAULT 0.0,
			`english` FLOAT NOT NULL DEFAULT 0.0,
			`math` FLOAT NOT NULL DEFAULT 0.0);

INSERT INTO `student` (`id`,`name`,`chinese`,`english`,`math`) VALUES (1,'周杨凡',99,99,99)
INSERT INTO `student` (`id`,`name`,`chinese`,`english`,`math`) VALUES (2,'韩信',89,68,100)
INSERT INTO `student` (`id`,`name`,`chinese`,`english`,`math`) VALUES (3,'jack',67,99,24)
INSERT INTO `student` (`id`,`name`,`chinese`,`english`,`math`) VALUES (4,'张飞',35,78,99)
INSERT INTO `student` (`id`,`name`,`chinese`,`english`,`math`) VALUES (5,'赵云',99,99,78)

SELECT * FROM `student`

SELECT `name`,`chinese` FROM `student`

SELECT DISTINCT `chinese` FROM `student` #所有字段都相同才会去重
```

- 使用表示式对列进行运算
- 表达式中使用AS可以修改列名

```mysql
SELECT `name` AS `姓名`, (`chinese`+`english`+`math`)AS `总分`
			FROM `student`
```

- 在where中经常使用的运算符

![image-20221005121856283](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221005121856283.png)

```MYSQL
SELECT * FROM `student`
		WHERE `english` > 98 and `name` LIKE '周%' 
#模糊查询
		
SELECT * FROM `student`
		WHERE (`english`+`math`) > 180 and `name` LIKE '%杨%'
		

SELECT * FROM `student`
		WHERE (`english`+`math`) BETWEEN 150 AND 200
		
SELECT * FROM `student`
		WHERE (`english`+`math`) BETWEEN 150 AND 200 
		AND `name` LIKE '周%'
```

- order by 子句排序查询结果

```mysql
  SELECT * FROM `student` WHERE `chinese` > 60
  	ORDER BY `math` DESC
```

```mysql
  SELECT (chinese + math + english) AS `总分` , `name` FROM `student`
  		ORDER BY `总分` DESC  #降序
		
  SELECT (chinese + math + english) AS `总分` , `name` FROM `student`
  		ORDER BY `总分` #m默认升序
```

 

### 4.1 数值型函数

**4.1.1 统计函数count**

- **count返回行的总数**

```mysql
SELECT COUNT(*) FROM `student`
#统计学生数量

SELECT COUNT(*) FROM `student`
		WHERE `chinese` > 80
#语文成绩大于80分的

SELECT COUNT(*) FROM `student`
		WHERE `chinese` + `english` > 180
#语文加英语大于180分的人数
```

*`count(*)`和`count(列名)`的区别*

创建一个表

![image-20221022113212984](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221022113212984.png)

```mysql
SELECT COUNT(*) FROM `student01`
# 返回结果为4

SELECT COUNT(id) FROM `student01`
# 返回结果为3
```

**结果表明，count(*)返回满足查找条件所有行的个数，而count(列名)返回的是满足查找条件且此列不为空的总个数。**

**4.1.2 函数sum**

**Sum函数返回满足where条件的行的和，一般使用在数值列。**

```mysql
SELECT SUM(chinese)  FROM `student`

SELECT SUM(chinese),SUM(english)  FROM `student`

SELECT SUM(chinese) AS `语文总分` FROM `student`

SELECT SUM(chinese) AS `语文总分`, SUM(english) AS `英语总分` FROM `student` 
#对多列求和不能少了逗号
```

4.1.3 函数AVG

**AVG函数返回列的均值，只对数值型列有意义**

```mysql
SELECT AVG(chinese) FROM `student`

SELECT AVG(chinese) AS `语文均分` FROM `student`
```

4.1.4 MAX和MIN函数

```mysql
SELECT MAX(chinese) AS `语文最高分`, MIN(chinese) AS `语文最低分` FROM `student`

SELECT MIN(chinese + english) AS `总分最低分` FROM `student`
```

4.1.5 分组统计函数

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221022165659991.png" alt="image-20221022165659991" style="zoom:50%;" />

```mysql
#显示每个部门每种岗位的平均工资和最低工资
SELECT AVG(sal), MIN(sal), deptno FROM emp
			GROUP BY deptno;
			
#显示每个部门的每种岗位的平均工资和最低工资
SELECT AVG(sal), MIN(sal), deptno, job FROM emp
			GROUP BY deptno, job;
			
#显示平均工资低于2000的部门号和平均工资
SELECT deptno, AVG(sal) AS `sal_avg` 
			FROM emp GROUP BY deptno
			HAVING sal_avg < 2000;
```

**GROUP BY进行分组，HAVING是对分组的结果进行过滤。**

### 4.2 字符串函数

```mysql
#返回字符串的字符集
SELECT CHARSET(ename) FROM emp;

#连接字符串
SELECT CONCAT(ename,' job is ', job) FROM emp;

#INSTR(str,substr) 返回substr在str中出现的位置
SELECT INSTR('zhouyangfan', 'fan') FROM DUAL;
# dual亚原表 系统默认的表，可用作测试
# 返回8

#UCASE(str) 将str转成大写 LCASE(str) 转换为小写
SELECT UCASE(ename) FROM emp;

#LEFT(str,len) 从str中从左边起去len个字符  RIGHT(str,len) 从右边取
SELECT LEFT(ename,2) FROM emp;

#LENGTH(str) 统计每一行str的长度 按照字节返回
SELECT LENGTH(ename) FROM emp;
SELECT LENGTH('周杨凡') FROM DUAL; #返回9

#如果是manger，就替换成经理
SELECT ename, REPLACE(job,'MANAGER','经理') FROM emp;

#STRCMP(expr1,expr2) 逐字符比较两字符串大小
SELECT STRCMP('ZYF','SYF') FROM DUAL;

#SUBSTRING(str,pos,len) 从str字符串中第pos位置取出len个字符
SELECT SUBSTRING(ename,2,2) FROM emp;

# LTRIM(str) 	去掉字符串左边的空格 
# RTRIM(str)  去掉字符串右边的空格
# TRIM([remstr FROM] str) 两边都去
SELECT TRIM('  ZYF  ') FROM DUAL;
```

### 4.3 数学函数

```mysql
#数学相关函数

#绝对值
SELECT ABS(-10) FROM DUAL;

#十进制转二进制
SELECT BIN(10) FROM DUAL;

#CEIL(X)向上取整，的到比X大的整数
SELECT CEIL(1.1) FROM DUAL; #返回2
SELECT CEIL(-1.1) FROM DUAL; #返回-1

#CONV(N,from_base,to_base)进制转换 
SELECT CONV(16,16,10) FROM DUAL;

#FLOOR(X) 向下取整
SELECT FLOOR(1.9) FROM DUAL;

#FORMAT(X,D) 保留小数位数
SELECT FORMAT(11.11111,4) FROM DUAL; #11.1111

#HEX(N_or_S) 转16进制
SELECT HEX(455) FROM DUAL;

#LEAST(value1,value2,...) 求最小值
SELECT LEAST(1.1,8,3.4) FROM DUAL;

#取余
SELECT MOD(13,5) FROM DUAL;

#RAND()  返回0-1的一个随机书，括号内为种子
SELECT RAND(3) FROM DUAL;
```

### 4.4 时间相关函数

```mysql
#日期相关函数

#返回当前的日期
SELECT CURRENT_DATE FROM DUAL;

SELECT NOW() FROM DUAL;

#返回当前的时间
SELECT CURRENT_TIME FROM DUAL;

#返回当前时间戳
SELECT CURRENT_TIMESTAMP FROM DUAL;

CREATE TABLE mes(
id INT,
content VARCHAR(30),
send_time DATETIME);

INSERT INTO mes
       VALUES(1, '北京新闻', CURRENT_TIMESTAMP)

INSERT INTO mes
    VALUES(2, '上海新闻', NOW())

INSERT INTO mes
    VALUES(3, '广州新闻', NOW())
		
#应用实例
#显示所有的新闻信息，发布日期只显示日期，不用显示时间
SELECT id, content, DATE(send_time) FROM mes;


#请查询在20分钟内发布的帖子

#DATE_ADD(date,INTERVAL expr unit) unit可以为year、minute、second、day、hour
SELECT * FROM mes
     WHERE DATE_ADD(send_time, INTERVAL 20 MINUTE) >= NOW()
 
		 
SELECT * FROM mes
     WHERE DATE_SUB(NOW(), INTERVAL 20 MINUTE) < send_time

#请求出2011-11-11和1990-1-1相差多少天
SELECT DATEDIFF(NOW(),'1999-5-3') FROM DUAL;

#加入能活80岁，还能活几天
SELECT DATEDIFF(DATE_ADD('1999-5-3',INTERVAL 100 YEAR),NOW()) FROM DUAL;

SELECT TIMEDIFF('10:11:23','12:56:34') FROM DUAL; #前面的时间减后面
#-02:45:11

#返回当前时间的年，月，日
SELECT YEAR(NOW()) FROM DUAL;
SELECT MONTH(NOW()) FROM DUAL;
SELECT DAY(NOW()) FROM DUAL;

#UNIX_TIMESTAMP()返回的是1970-1-1 到现在的秒数
SELECT UNIX_TIMESTAMP() FROM DUAL;

SELECT UNIX_TIMESTAMP()/(24*3600*365) FROM DUAL;
SELECT UNIX_TIMESTAMP() FROM DUAL;

# FROM_UNIXTIME(unix_timestamp) 把UNIX_TIMESTAMP的秒数转成指定格式
# 在开发中可以存放一个整数（int）来表示一个时间，通过FROM_UNIXTIME来转换
SELECT FROM_UNIXTIME(1718483484, '%Y-%m-%d') FROM DUAL; #2024-06-16

SELECT FROM_UNIXTIME(1718483484, '%Y-%m-%d %H:%i:%s') FROM DUAL;
```

### 4.5 加密和系统函数

```mysql
#加密和系统函数

#查询用户，可以查看登录到mysql的有哪些用户，以及登录的IP
SELECT USER() FROM DUAL;

#查询当前使用数据库的名称
SELECT DATABASE() FROM DUAL;

#MD5(str) 为字符串算出一个MD5 32位的字符串，常用（用户密码）加密
#root 密码是 zyf2563085 -> 加密md5 -> 在数据库中存放的是加密后的密码
SELECT LENGTH(MD5('zyf2563085')) FROM DUAL;

#演示用户表，存放密码时，存放的是MD5加密

CREATE TABLE `user`(
      id INT,
			`name` VARCHAR(32) NOT NULL DEFAULT '',
			`pwd` CHAR(32) NOT NULL DEFAULT ''
);

INSERT INTO user VALUES(1, '周杨凡', MD5('ZYF'));

SELECT * FROM user
			WHERE `name` = '周杨凡' AND pwd = MD5('ZYF');

#mysql数据库的用户密码就是PASSWORD函数加密的
SELECT PASSWORD('ZYF') FROM DUAL;
```

### 4.6 流程控制函数

```mysql
#演示流程控制语句

#IF(expr1,expr2,expr3) 如果expr1为空，则返回expr2，否则返回expr3
SELECT IF(TRUE, '北京', '上海') FROM DUAL;

#IFNULL(expr1,expr2) 如果expr1不为空，则返回expr1，否则返回expr2
SELECT IFNULL(NULL,'123') FROM DUAL;
SELECT IFNULL('234','123') FROM DUAL;

#如果expr1为true，则返回expr2，如果expr3为true，则返回expr4，否则返回expr5
SELECT CASE 
			WHEN FALSE THEN 'zyf' 
			WHEN TRUE THEN 'slp' 
			ELSE 'mm' END;

#需求
#查询emp表，如果comm是null，则显示0.0。
#如果emp表的job是CLERK则显示职员，如果是MANAGER则显示经理，如果是SALESMAN则显示销售人员，其他正常显示

#判断是否为NULL 要使用is null 判断不为空 使用is not, 不使用等号
SELECT ename, IF(comm IS NULL, 0.0, comm)
			FROM emp;
			
SELECT ename, IFNULL(comm , 0.0)
			FROM emp;
			
SELECT ename, (SELECT CASE
	WHEN job = 'CLERK' THEN '职员'
	WHEN job = 'MANAGER' THEN '经理'
	WHEN job = 'SALESMAN' THEN '销售人员'
	ELSE job END) as 'job'
	FROM emp;
```

### 4.7 增强查询

```mysql
# 增强查询 使用where子句
# 查找1992.1.1后入职的员工
# 在mysql中日期可以直接进行比较
SELECT * FROM emp
    WHERE hiredate > '1992-01-01'


#显示首字母为S的员工的姓名和工资
#%表示0到多个任意字符
SELECT ename , sal FROM emp
				WHERE ename LIKE 'S%'
				
#显示第三个字符为大写O的所有员工的姓名和工资
#_:表示单个字符
SELECT ename, sal FROM emp
  WHERE ename LIKE '__O%'
	
#显示没有上级的雇员情况 
SELECT * FROM emp
    WHERE mgr is NULL;
		
#查询表结构
DESC emp;

SELECT * FROM emp
			ORDER BY sal;
			
SELECT * FROM emp
			ORDER BY deptno ASC, sal DESC;
```

### 4.8 分页查询

```mysql
# 分页查询
# 按雇员的id号升序取出，每页显示三条记录，请分别显示 第一页，第二页，第三页

#第一页
SELECT * FROM emp
		ORDER BY empno
		LIMIT 0, 3;
		
#第二页
SELECT * FROM emp
		ORDER BY empno
		LIMIT 3, 3;
		
#推导一个公式
SELECT * FROM emp
		ORDER BY empno
		LIMIT 每页显示的记录数 * (第几页 - 1), 每页显示记录数
```

### 4.9 增强分组函数和分组子句

```mysql
#增强分组函数和分组子句

#(1)显示每种岗位的雇员总数、平均工资
SELECT COUNT(*) AS `雇员总数`, AVG(sal) AS `平均工资`, job FROM emp
			GROUP BY job;
			
#显示雇员的总数以及获得补助的雇员数
SELECT COUNT(*) AS `雇员总数`, COUNT(IF(comm is NULL, NULL, 1)) AS `获得补助的雇员总数` 
			FROM emp

#统计没有补助的雇员数
SELECT COUNT(*) AS `雇员总数`, COUNT(IF(comm is NULL, 1, NULL)) AS `没有获得补助的雇员总数` 
			FROM emp
		
#显示管理者总人数
SELECT COUNT(DISTINCT mgr) FROM emp;
			

#显示雇员工资的最大差额
SELECT MAX(sal) - MIN(sal) FROM emp;
```

### 4.10 语句顺序

```mysql
SELECT COLUMN1, COLUMN2, COLUMN3.. FROM table
			GROUP BY COLUMN
			HAVING condition
			ORDER BY column
			LIMIT start, row;
```

## 5. 多表查询

```mysql
#多表查询
/*
显示雇员名，雇员工资及所在部门的名字
   1.雇员名，雇员工资来自 emp表
	 2.部门名字来自dept表
	 3.需求对 emp和dept查询
*/

SELECT * FROM emp, dept
```

![image-20221026144306233](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221026144306233.png)

**默认情况下：当两个表查询时，规则**：

- 从第一张表中，取出每一行和第二张的表的每一行进行一个组合，就返回结果（含有两张表的所有列）
- 一共返回了记录数：第一张表的行数*第二章表的行数
- 这样多表查询默认返回的结果，称为笛卡尔积
- 解决这个多表的关键是要正确写出正确的过滤条件 where

```mysql
#多表查询
/*
显示雇员名，雇员工资及所在部门的名字
   1.雇员名，雇员工资来自 emp表
	 2.部门名字来自dept表
	 3.需求对 emp和dept查询
*/



SELECT ename, sal, dname, FROM emp, dept
   WHERE emp.deptno = dept.deptno
	 
	 
SELECT ename, sal, dname, emp.deptno FROM emp, dept
   WHERE emp.deptno = dept.deptno
#deptno两个表中都有，需要明确指出查询的是哪个表中的deptno，否则会报错
```

**技巧：多表查询的条件不能少于`表的个数-1`，否则会出现笛卡尔积**

```mysql
#如何显示部门号为10的员工，员工名和工资
SELECT deptno, ename, sal
				FROM emp
				WHERE deptno = 10
				
显示各个员工的姓名，工资及其工资的级别
SELECT ename, sal, grade
				FROM emp, sal_grade
				WHERE sal BETWEEN losal and hisal
```

### 自连接

**自连接是指在同一张表的连接查询【将同一张表看作两张表使用】**

```mysql
#显示公司员工名字和他上级的名字
#把同一张表当作两张表使用，需要取别名，列名不明确可以指定列的别名
SELECT worker.ename AS `员工名`, boss.ename AS `上级名`
				FROM emp worker, emp boss
				WHERE worker.mgr = boss.empno
```

## 6. 子查询

**子查询是嵌入在其他sql语句中的select语句，也叫嵌套查询**

**单行子查询：只返回一行数据的子查询语句**

**多行子查询：返回多行数据的子查询，使用关键字in**

```mysql
#如何显示与SMITH同一部门的所有员工
/*
   1.查询到SMITH的部门编号
	 2.把上面的select语句当作一个子查询来使用
*/

SELECT deptno FROM emp
				WHERE ename = 'SMITH'
				
SELECT * FROM emp
				WHERE deptno = (SELECT deptno FROM emp
				WHERE ename = 'SMITH')
```

```mysql
#如何查询和部门10的工作相同的雇员的名字、岗位
#工资、部门号，但是不含10号部门自己的雇员

/*
		1.查询到10号部门有哪些工作
		2.把上面查询的结果当作子查询使用
*/

SELECT DISTINCT job FROM emp
			WHERE deptno = 10
			

SELECT ename, job, sal, deptno 
			FROM emp
			WHERE job in (
			SELECT DISTINCT job FROM emp
			WHERE deptno = 10
			) AND deptno != 10;
```

在多行子查询中使用all操作符

```mysql
# 显示工资比部门30的所有员工的工资高的员工姓名、工资和部门号


SELECT ename, sal, deptno 
			FROM emp
			WHERE sal > all(
				SELECT sal FROM emp
						WHERE deptno = 30
		)

# 显示工资比部门30的其中一个员工的工资高的员工姓名、工资和部门号
SELECT ename, sal, deptno 
			FROM emp
			WHERE sal > ANY(
				SELECT sal FROM emp
						WHERE deptno = 30
		)
		
```

### 子查询临时表

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221026171124671.png" alt="image-20221026171124671" style="zoom:50%;" />

```mysql
#子查询临时表
#查询每个品牌(cat_id)价格最高的商品名称，价格和编号（good_id）
SELECT MAX(shop_price) FROM shope
	GROUP BY cat_id


SELECT shope.cat_id, shope.shop_price , shope.goods_name
			FROM  (SELECT MAX(shop_price) AS max_price, cat_id
			FROM shope
			GROUP BY cat_id) temp, shope
			WHERE max_price = shope.shop_price 
			AND temp.cat_id = shope.cat_id
```

### 多列子查询

**多列子查询是指查询返回多个列数据的子查询语句**

```MYSQL
#多列子查询
#查询与smith部门和岗位完全相同的所有雇员（并不包含smith本人）

/*
		1.得到smith的部门和岗位
		2.把上面的查询当作子查询来使用，并且使用多列子查询的语法进行匹配
*/

SELECT deptno, job
		FROM emp
		WHERE ename = 'SMITH';
		
SELECT * 
   FROM emp
	 WHERE (deptno , job) = (
				SELECT deptno, job
				FROM emp
				WHERE ename = 'WARD'
	 ) AND ename != 'WARD'
```

练习

```mysql
#查询每个部门的信息（部门名，编号，地址）和人员数量
/*
    1.部门名，地址在dept表
		2.编号在dept表和emp表
		3.统计人员数量，使用emp表
		4.一共两张表，共有的字段为deptno
*/

SELECT COUNT(*) AS `人数`, deptno FROM emp
		GROUP BY deptno
		
		
SELECT dname, dept.deptno, `sum`, loc
			FROM dept, (SELECT COUNT(*) AS `sum`, deptno 
									FROM emp
									GROUP BY deptno
									) AS temp
			WHERE temp.deptno = dept.deptno	
```

## 7. 表复制

**有时，为了对某个sql语句进行效率测试，我们需要海量数据时，可以使用此法为表创建海量数据。**

```mysql
#表复制
#先把emp表的记录复制到my_tab01中
INSERT INTO my_table
		(id, `name`, sal, job, deptno)
		SELECT empno, ename, sal, job, deptno FROM emp;
		
#自我复制
INSERT INTO my_table 
		SELECT * FROM my_table
```

**删掉一张表的重复记录**

```mysql
#去重
/*
  1.创建一张临时表my_tmp，该表的结构和my_tab02
  2.把my_tmp的记录，通过distinct关键字处理后，把记录复制到my_tmp表
  3.清除掉my_tab02记录
  4.把my_tmp表的记录复制到my_tab02
  5.drop掉临时表my_tmp
*/

CREATE TABLE my_tmp LIKE my_tab02

INSERT INTO my_tmp 
		SELECT DISTINCT * FROM my_tab02
		
DELETE FROM my_tab02

INSERT INTO my_tab02
		SELECT * FROM my_tmp
		
DROP TABLE my_tmp
```

## 8. 合并查询

**有时在实际应用中，为了合并多个select语句的结果，可以使用集合操作符号union，union all**

1. union all

   - 该操作符用于取得两个结果集的并集。当使用该操作符时，不会取消重复行

     ```MYSQL
     #合并查询 union all
     
     SELECT ename, sal, job FROM emp
     		WHERE sal > 2500
     		UNION ALL
     SELECT ename, sal, job FROM emp
     		WHERE job = 'MANAGER'
     ```

     

2. union

   - 会自动去掉结果重复行

     ```mysql
     SELECT ename, sal, job FROM emp
     		WHERE sal > 2500
     		UNION
     SELECT ename, sal, job FROM emp
     		WHERE job = 'MANAGER'
     ```

     

## 9. 表外连接

```mysql
#外连接

#创建stu
CREATE TABLE stu(
	id INT,
	`name` VARCHAR(32)
)

INSERT INTO stu VALUES(1, 'jack'), (2, 'tom'), (3, 'kity'), (4, 'nono')
SELECT * FROM stu


#创建exam
CREATE TABLE exam(
	id INT,
	grade INT
);
INSERT INTO exam VALUES(1, 56), (2, 76), (11, 8)

SELECT `name`, stu.id, grade
		FROM stu, exam
		WHERE stu.id = exam.id;

SELECT `name`, stu.id, grade
		FROM stu LEFT JOIN exam
		ON stu.id = exam.id;
		
SELECT `name`, stu.id, grade
		FROM stu RIGHT JOIN exam
		ON stu.id = exam.id;
```

**左连接：左边的表和右边的表没有匹配的记录（on后面），也会把左边的表的记录显示出来。**

**外连接：左边的表和右边的表没有匹配的记录（on后面），也会把右边的表的记录显示出来。**

<u>练习：列出部门名称和这些部门员工的信息（名字和工作），同时列出那些没有员工的部门</u>

```mysql
/*
   1.部门名称（dname）在dept表
   2.员工的名字（ename）和工作（job）在emp表
   3.连接字段为depton
*/

#左连接
SELECT dept.dname, ename, job
		FROM dept LEFT JOIN emp
		ON dept.deptno = emp.deptno
```

小节：在实际的开发中，在绝大多数情况下，时前面学过的连接，

# mysql约束

- 约束用于确保数据库的数据满足特定的商业规则。
- 在mysql中，约束包括：<u>not null</u>，<u>unique</u>，<u>primary key</u>，<u>foreign key</u>和<u>check</u>五种。

## 1. primary key（主键）

基本使用：`字段名 字段类型 primary key`

**用于唯一的标识表行的数据，当定义主键约束后，该列的值不能重复**

```mysql
#主键的使用

CREATE TABLE t17(
	id INT PRIMARY KEY,  #表示id列是主键
	`name` VARCHAR(32),
	email VARCHAR(32)
)

INSERT INTO t17
		VALUES(1, 'jack', 'jack@sohu.com')

INSERT INTO t17
		VALUES(1, 'zyf', 'zyfan468@nenu.edu.com')
//会报错
```

细节：

1. 主键的值不能重复，且**不能为空**

2. 一个表中只能有一个主键，但可以是复合主键

   ```Mysql
   CREATE TABLE t17(
   	id INT PRIMARY KEY,  #表示id列是主键
   	`name` VARCHAR(32) PRIMARY KEY,
   	email VARCHAR(32)
   )
   //两个主键将会报错
   ```

   *演示复合主键（id 和 name做成一个复合主键）*

   ```mysql
   CREATE TABLE t18(
   	id INT,
   	`name` VARCHAR(32),
   	email VARCHAR(32),
   	PRIMARY KEY (id, `name`) #复合主键
   )
   
   
   INSERT INTO t18
   		VALUES(1, 'jack', 'jack@sohu.com')
   		
   INSERT INTO t18
   		VALUES(1, 'zyf', 'zyfan468@nenu.edu.com')
   		
   INSERT INTO t18
   		VALUES(1, 'zyf', '2563085@163.com') #id 和 name都相同，报错
   ```

3. 查看表的结构和约束情况    **DESC指令**

4. 在实际开发中，每个表往往都会设计一个主键

## 2. unique

**当定义了唯一约束后，该列值是不能重复的**

`字段名 字段类型 unique`

细节

1. 如果没有指定not null，则unqiue字段可以有多个null。
2. 一张表可以有多个unique字段。
3. 如果一个列是unique not null使用效果类似parimry key

## 3. 外键

foreign key

**用于定义主表和表之间的关系：外键约束要定义在从表上，主表则必须具有主键约束或是unique约束，当定义外按约束后，要求外键列数据必须在主表的主键列存在或是为null。**

```mysql

###定义主表
CREATE TABLE my_class(
		id INT PRIMARY KEY,
		`name`  VARCHAR(10)
	)

###定义从表	
CREATE TABLE my_stu(
	id INT PRIMARY KEY,
	`name` VARCHAR(32) NOT NULL DEFAULT '',
	class_id INT, #学生所在班级的编号
	FOREIGN KEY (class_id) REFERENCES my_class(id)
)

INSERT INTO my_class VALUES(100,'java'),(200,'php')

INSERT INTO my_stu VALUES(1,'tom','100') #成功
INSERT INTO my_stu VALUES(2,'zyf','300') #报错，外键约束


DELETE FROM `my_class`
	WHERE `id` = 100; #报错，因为从表中有class_id为100的记录
```

细节：

1. 外键是指向表的字段，要求是primary key或者是unique。
2. 表的类型是innodb，这样才支持外键。
3. 外键的字段类型和主键字段的类型一致（长度可以不同）。
4. 外键字段的值，必须在主键字段出现过，或者为null【前提是外键字段允许为null】。
5. 一旦建立外键的关系，数据不能随意删除。

## 4. check

**用于强制行数据必须满足的条件，假定在sal列上定义了check约束，并要求sal列值在1000~2000之间如果不在这个区间就会提示报错**

oracle和sql server均支持check，但是mysql5.7目前还不支持check，只做语法校验，但不会生效。

`列名  类别  check（check条件）`

在mysql中实现check的功能，一般是在程序控制，或者是通过触发器完成

# 自增长

在某张表中，存在一个id列（整数），我们希望在添加记录时，该列从1开始，自动增长。

`字段名  整型  PRIMARY KEY auto_increment`

```MYSQL
CREATE TABLE t24(
		id INT PRIMARY KEY AUTO_INCREMENT,
		email VARCHAR(32) NOT NULL DEFAULT '',
		`name` VARCHAR(32) NOT NULL DEFAULT ''
)

DESC t24

INSERT INTO t24 VALUES(NULL, 'zyf@qq.com', 'zyf')
INSERT INTO t24 (email, `name`) VALUES ('hsp@qq.com', 'hsp')
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221031143253048.png" alt="image-20221031143253048" style="zoom:50%;" />

细节：

1. 一般来说自增长是和primary key配合使用。
2. 自增长也可以单独使用【但是需要配合一个unique】。
3. 自增长修饰的字段为整数型（虽然小数也可以但是非常非常少这样用）。
4. 自增长默认从1开始，但也可以通过命令修改。`alter table 表名 auto_increment = 指定值` 。
5. 如果添加数据时，给自增长的字段指定值，以指定的值为准，以后的自增长从指定的值开始增长。一般来说，就按照自增长的规则来添加数据。

# 索引

- 没有索引的话，查询慢，因为需要全表扫描
- 形成索引的数据结构（比如二叉树），大幅度提升查询速度
- 创建索引占用空间，对更新、删除、插入语句的效率有影响（维护索引）
- `CREATE  INDEX  索引名  ON  表名（列名）`

索引的类型

1. 主键索引，主键自动选择索引（类型Primary Key）。

2. 唯一索引（UNIQUE）。

3. 普通索引（INDEX）。

4. 全文索引（FULLTEXT）【适用于MyISAM】。

   开发中考虑使用：全文搜索Solr和ElasticSearh（ES）。

创建索引

```mysql
#创建索引
CREATE TABLE t25(
		id INT,
		`name` VARCHAR(32)
)

#查询表是否有索引
SHOW INDEX FROM t25;

#添加索引
#添加唯一索引
CREATE UNIQUE INDEX id_index ON t25(id);

#添加普通索引
CREATE INDEX id_index ON t25(id);

#如何选择普通索引和唯一索引
#如果某列的值，是不会重复的，则优先考虑使用unique，否则使用普通索引

#添加普通索引方式2
ALTER TABLE t25 ADD INDEX id_index(id)


#添加主键索引

CREATE TABLE t26(
		id INT,
		`name` VARCHAR(32)
)

ALTER TABLE t26 ADD PRIMARY KEY(id)

SHOW INDEX FROM t25

#删除普通索引
DROP INDEX id_index ON t25

#删除主键索引
ALTER TABLE t26 DROP PRIMARY KEY
SHOW INDEX FROM t26


#查询索引
SHOW INDEX FROM t25
SHOW INDEXES FROM t25
SHOW KEYS FROM t25
DESC t25
```

索引使用规则：

1. 较频繁的作为查询条件字段应该创建索引。
2. 唯一性条件太差的字段不适合单独创建索引，即使频繁作为查询条件。
3. 更新非常频繁的字段不适合创建索引。
4. 不会出现在WHERE子句中字段不该创建索引。

# 事务

​        **事务用于保证数据的一致性，它由一组相关的dml语句组成，该组的dml语句（update，insert，delete）要么全部成功，要么全部失败。比如转账就要用事务来处理，用以保证数据的一致性。**

当执行事务操作时（dml语句），mysql会在表上加锁，防止其他用户改表数据，这对用户来讲是十分重要的。

mysql数据库控制台事务的几个重要的操作

1. start transaction  -> 开始一个事务
2. savepoint 保存点名  ->设置保存点
3. rollback to 保存点名 -> 回退事务
4. **rollback -> 回退全部事务**
5. commit  -> 提交事务。所有的操作生效，保存点删除，不能回退

## 回退事务和提交事务

回退事务：保存点是事务中的点，用于取消部分事务，当结束事务时，会自动的删除该事务所定义的所有保存点。当执行回退事务时，通过指定保存点可以回退到指定的点。

提交事务：适应commit语句可以提交事务，当执行了commit语句之后，会确认事物的变化，结束事务、删除保存点、释放锁、数据生效。当使用commit语句结束事务之后，其他会话将可以查看到事务变化后的新数据。

## 事务细节

1. 如果不开始事务，默认情况下，dml操作时**自动提交**的，不能回滚。
2. *如果开始一个事务，你没有创建保存点，你可以执行rollback，默认就是回退到你事务开始的状态。*
3. 也可以在这个事务中（还没提交时），创建多个保存点。
4. 可以在事务没提交时，选择回到哪个保存点。
5. mysql事务机制需要innodb的存储引擎，myisam不行。
6. 开始一个事务start transaction， set autocommit = off。

## 事务隔离级别

- 多个连接开启各自事务操作数据库中的数据时，数据库系统要负责隔离操作，以保证各个连接在获取数据时的准确性。
- 如果不考虑隔离性，可能会发生如下问题：
  1. 脏读：当一个事务读取另一个事务尚未提交修改时，产生脏读。
  2. 幻读：同一查询在同一事务中多次进行，由于其他提交事务所做的插入操作，每次返回不同的结果集，此时发生幻读。
  3. 不可重复读：同一查询在同一事务中多次进行，由于其他提交事务所做的修改或删除，每次返回不同的结果集，此时发生不可重复读。例如要盘点10：00之前的订单，但却统计到10：00以后另一个客户端提交的结果。

| MySQL的隔离级别              | 脏读 | 不可重复读 | 幻读 | 加锁读 |
| ---------------------------- | ---- | ---------- | ---- | ------ |
| 读未提交（Read uncommitted） | √    | √          | √    | 不加锁 |
| 读已提交（Read committed）   | ×    | √          | √    | 不加锁 |
| 可重复读（Repeatable read）  | ×    | ×          | ×    | 不加锁 |
| 可串行化（Serializable）     | ×    | ×          | ×    | 加锁   |

- 隔离级别是与事务相关的，不能离开事务谈隔离级别。

## 隔离级别相关指令

1. `select @@tx_isolation;`查看当前会话隔离级别。
2. `select @@global.tx_isolation;`查看系统当前隔离级别。
3. `set session transaction isolation level repeatable read;`设置当前会话隔离级别。
4. `set global session transaction isolation level repeatable read;`设置系统当前隔离级别。
5. mysql默认的事务隔离级别是repeatable read，一般情况下，没有特殊要求，没有必要修改（因为该级别可以满足绝大部分项目需求）。
6. 全局修改，修改my.ini配置文件，在最后【mysqld中】加上`transaction-isolation = REPEATABLE-READ`，可选参数有
   - READ-UNCOMMITTED
   - READ-COMMITTED
   - REPEATABLE-READ
   - SERIALIZABLE

#### 事务的acid特性

1. **原子性**：是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生。
2. **一致性**：事务必须使数据库从一个一致性状态变换到另一个一致性状态。
3. **隔离性**：事务的隔离性是多个用户并发访问数据库时，数据库为每个用户开启的事务，不能被其他事务的操作数据所干扰，多个并发事务之间要相互隔离。
4. **持久性**：持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来即使数据库发生故障也不应该对其有任何影响。

# 表类型和存储引擎

1. MySQL表类型右存储引擎决定，主要 包括MyISAM、innoDB、Memory等。
2. MySQL数据表主要支持六种类型、分别是：CSV、Memory、ARCHIVE、MRG_MYISAM、MYISAM、InnoDB。
3. 这六种又分为两类，一类是“事务安全型”，比如：InnoDB；其余都属于第二类，称为“非事务安全型”（mysiam和memory）。

| 特点         | Myisam | InnoDB | Memory | Archive |
| ------------ | ------ | ------ | ------ | ------- |
| 批量插入速度 | 高     | 低     | 高     | 非常高  |
| 事务安全     |        | 支持   |        |         |
| 全文索引     | 支持   |        |        |         |
| 锁机制       | 表锁   | 行锁   | 表锁   | 行锁    |
| 支持外键     |        | 支持   |        |         |
| 存储限制     | 没有   | 64TB   | 有     | 没有    |

- MyISAM不支持事务，也不支持外键，但其访问速度快，对事务完整性没有要求。
- InnDB存储引擎提供了具有提交、回滚和崩溃恢复能力的事务安全。但比起MyISAM存储引擎，InnoDB写的处理效率差一些并且会占用更多的磁盘空间以保留数据和索引。
- MEMORY存储引擎使用存在内存中的内容来创建表。每个MEMORY表只实际对应一个磁盘文件。MEMORY类型的表访问速度非常快，因为它的数据是放在内存中的，并且默认使用HASH索引。但是一旦MySQL服务关闭，表中的数据就会丢失，表的结构还在。

## 选择存储引擎

1. 如果应用不需要事务，处理的只是基本的CRUD操作，那么使用MyISAM，速度快。
2. 如果需要支持事务，选择InnoDB。
3. Memory存储引擎就是将数据存储在内存中，由于没有磁盘IO的等待，速度极快。但由于是内存存储引擎，所做的任何修改在服务器重启后都将消失。（经典用法：在线的用户状态）

## 修改存储引擎

表存储引擎修改指令：

`ALTER TABLE t28 ENGINE = INNODB;`

# 视图

- 视图：是一个虚拟表，其内容由查询定义。同真实的表一样，视图包含列，其数据来自对应的真实表（基表）
- 视图是根据基表（可以是多个基表）来创建的，虚拟表。
- 视图也有列，但数据来自基表。
- 通过视图可以修改基表的数据。
- 基表的修改，也会影响到视图的数据。

## 视图基本使用

1. `create view 视图名 as select 语句`
2. `alter view 视图名 as select 语句`
3. `SHOW CREATE VIEW 视图名`
4. `drop view 视图名1，视图名2`

## 视图的使用细节

1. 创建视图后，数据库中只有一个视图结构文件（视图名.frm）
2. 视图的数据变化会影响到基表，基表的数据变化也会影响到视图
3. 视图中可以再使用视图。

```MYSQL
#针对emp, dept和sal_grade 三张表,创建一个视图emp_view03
#可以显示雇员部门名称和薪水级别
				
CREATE VIEW emp_view03
		AS
		 (SELECT ename, dname, grade
				FROM emp, dept, sal_grade
				WHERE emp.deptno = dept.deptno
				AND
				sal BETWEEN losal AND hisal)
```

# 管理

- 当我们做项目开发时，可以根据不同的开发人员，赋给他相应的MySQL操作权限。所以数据库管理人员（root），根据需要创建不同的用户，赋予相应的权限，供开发人员使用。
- mysql中的用户，都存储在系统数据库mysql中user中。
- 其中user表中的重要字段说明：
  1. host：允许登录的“位置”，localhost表示该用户只允许本机登录，也可以指定ip地址，比如：192.168.1.100。
  2. user：用户名。
  3. authentication_string：密码。是通过mysql的password（）函数加密之后的密码。authentication：鉴定，证明真实性

- 创建用户：`create user ‘用户名’ @ ‘允许登录位置’ identified by ‘密码’`创建用户同时指定密码
- 删除用户：`drop user ‘用户名’ @ ‘允许登录的位置’`

说明：

- 不同的数据库用户，操作的库和表不相同。
- 不同的数据库用户，登录到DBMS后，根据相应的权限，可以操作的数据库和数据对象（表，视图，触发器）都不一样。
- 修改密码

```
#可以修改自己的密码
SET PASSWORD = PASSWORD('abcdef')

#修改其他人的密码需要权限
SET PASSWORD FOR 'root'@'localhost' = PASSWORD()
```

## MySQL中的权限

- 给用户授权

  `grant 权限列表 on 库.对象名 to '用户名' @ '登录位置' 【identified by '密码'】`

  对象名可以是表，视图，触发器

- 权限列表，多个权限用逗号隔开
  1. `grant all on ....`表示赋予该用户在该对象上的所有权限
  2. 

- `*.*`：代表本系统中的所有数据库的所有对象（表，视图，存储过程等）

  `库.*`：表示某个数据中的所有数据对象（表，视图，存储过程等）

- identified by可以省略，也可以写出

  1. 如果用户存在，就是修改该用户的密码。
  2. 如果该用户不存在，就是创建该用户。

- 回收用户权限

  `revoke 权限列表 on 库.对象名 from '用户名'@'登录位置'；`

```mysql
#mysql权限管理

#创建一个用户（你的名字），密码123，并且只可以从本地登录，不让远程登录mysql
#创建库和表下的news表，要求使用root用户创建
#给用户分配查看news表和添加数据的权限
#测试看看用户是否只有这几个权限
#修改密码为abc，要求使用root用户完成
#重新登录
#使用root用户删除你的账户

CREATE USER 'yangfan'@'localhost' IDENTIFIED BY '123';


CREATE DATABASE testDB;

CREATE TABLE news(
		id INT,
		content VARCHAR(32)
);

INSERT INTO `news` VALUES (100, '北京新闻');
SELECT * FROM news;

#给yangfan用户分配查看news表和添加news的权限

GRANT SELECT, INSERT 
		ON testdb.news
		TO 'yangfan'@'localhost'
		
#修改yangfan的密码为ABC
SET PASSWORD FOR 'yangfan'@'localhost' = PASSWORD('ABC')

#回收yangfan用户在testdb表的所有权限
REVOKE SELECT, UPDATE, INSERT ON testdb.news FROM 'yangfan'@'localhost'

DROP USER 'zyf'@'localhost';

SELECT * FROM USER;
```

细节说明

1. 在创建用户的时候，如果不指定Host，则为%，%表示所有的IP都有连接权限create user xxx；
2. 也可以这样指定 create user 'xxx'@'192.168.1.%' 表示xxx用户在192.168.1.*的ip可以登录mysql
3. 在删除用户的时候，如果host不是%，需要明确指定`'用户'@'host值'`

# JDBC和数据库连接池

- JDBC为了**访问不同的数据库**提供了统一的接口，为使用者屏蔽了细节问题。
- Java程序员使用JDBC，可以连接任何提供了JDBC驱动程序的数据库系统，从而完成对数据库的各种操作
- JDBC的基本原理
- JDBC是Java提供一套用于数据库操作的接口API，Java程序员只需要面向这套接口编程即可。不同的数据库厂商，需要针对这套接口，提供不同实现。

## JDBC入门

JDBC程序编写步骤

前置工作：在项目创建一个文件夹比如libs，将mysql.jar拷贝到该目录下，点击add to project..加入到项目中

1. 注册驱动 - 加载Driver类
2. 获取连接 - 得到Connection
3. 执行增删改查 - 发送SQL命令 给mysql执行
4. 释放资源 - 关闭相关的连接

```java
public class Jdbc01 {
    public static void main(String[] args) throws SQLException {
        //1.注册驱动
        Driver driver = new Driver();

        //2.得到连接
        //jdbc:mysql:// 表示协议
        //localhost主机，可以是ip地址
        //3306表示mysql监听的窗口
        //zyf_db05 表示连接到mysql DBMS的哪个数据库
        //mysql的连接本质就是Socket连接
        String url = "jdbc:mysql://localhost:3306/zyf_db07";
        //将用户名和密码放入到Properties中
        Properties properties = new Properties();
        //user 和 password是规定好的，后面值根据实际情况写
        properties.setProperty("user", "root");
        properties.setProperty("password", "zyf2563085");
        Connection connect = driver.connect(url, properties);
      
      
        //3.执行SQL
//        String sql = "insert into actor values(null , '刘德华', '男', '1970-11-11', '110')";
//        String sql = "update  actor set name = '周星驰' where id = 1";
        String sql = "delete from actor where id = 1";
        Statement statement = connect.createStatement();
        int rows = statement.executeUpdate(sql);
        //如果是dml语句，返回的rows就是影响的行数
        System.out.println(rows > 0 ? "成功" : "失败");
      
      
      
      
        //4关闭连接资源
        statement.close();
        connect.close();
    }
}
```

## 连接数据库

java连接mysql的五种方式

```mysql
package com.zyf.jdbc;

import com.mysql.jdbc.Driver;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: 周杨凡
 * @date: 2022-11-07
 * 分析java 连接mysql的5种方式
 */
public class JdbcConn {

    //方式1
    @Test
    public void connect01() {
        try {
            Driver driver = new Driver();
            String url = "jdbc:mysql://localhost:3306/zyf_db07";
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "zyf2563085");
            Connection connect = driver.connect(url, properties);
            System.out.println(connect);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     //方式2
    @Test
    public void connect02() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.getConstructor().newInstance();
        String url = "jdbc:mysql://localhost:3306/zyf_db07";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "zyf2563085");
        Connection connect = driver.connect(url, properties);
        System.out.println("方式2" + connect);
    }

    @Test
    //方式3 使用DriverManager 代替 Driver 进行统一管理

    public void connect03() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.getConstructor().newInstance();

        //
        String url = "jdbc:mysql://localhost:3306/zyf_db07";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "zyf2563085");


        String user = "root";
        String password = "zyf2563085";


        DriverManager.registerDriver(driver);
        Connection connection1 = DriverManager.getConnection(url, properties);
        System.out.println("方式3" + connection1);
        Connection connection2 = DriverManager.getConnection(url, user, password);
        System.out.println("方式4" + connection1);
    }


    //方式4 使用Class.forName 自动完成注册驱动，简化代码
    @Test
    public void connect04() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        // 静态代码快在类加载时会执行一次，
        // DriverManager.registerDriver(new Driver());
        // 因此注册已经完成
        String url = "jdbc:mysql://localhost:3306/zyf_db07";
        String user = "root";
        String password = "zyf2563085";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    @Test
    //方式5 在方式4的基础上改进，添加配置文件，让连接mysql更加灵活
    public void connect05() throws IOException, ClassNotFoundException, SQLException {
        //通过Properties对象获取文件的配置信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");


        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("方式5: " + connection);

    }
}
```

## ResultSet

- 表示数据库结果集的数据表，通常通过执行查询数据库的语句生成。
- ResultSet对象保持一个光标指向其当前的数据行。最初，光标位于第一行之前。
- next方法将光标移动到下一行，并且由于在ResultSet对象中没有更多行时返回false，因此可以在while循环中使用循环来遍历结果集。

`statement.executeQuery()`：执行给定的SQL语句，该语句返回结果集

结果集的具体存放位置在elementData的对象数组中。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221108110935410.png" alt="image-20221108110935410" style="zoom:50%;" />

## 		预处理查询

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221108124944531.png" alt="image-20221108124944531" style="zoom:50%;" />

PreparedStatement为Statement子接口

1. PreparedStatement执行的SQL语句中的参数用问号(?)来表示，调用PreparedStatement对象的`setXXX()`方法来设置这些参数。`setXXX()`方法有两个参数，第一个参数是要设置的SQL语句中的参数索引（从1开始），第二个是设置的SQL语句中的参数的值。
2. 调用`executeQuery()`，返回ResultSet对象。
3. 调用`executeUpdate()`：执行更新（dml）。

预处理的好处：

1. 不再使用+拼接sql语句，减少语法错误。
2. 有效解决了sql注入的问题。
3. 大大减少了编译次数，效率提高。

```java
package com.zyf.jdbc.PreparedStatement;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author: 周杨凡
 * @date: 2022-11-08
 * 演示preparedStatement
 */
public class PreparedStatement_ {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");


        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        String sql = "select `id`, `name` from `actor` where `name` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "we");

        ResultSet resultSet = preparedStatement.executeQuery(); //括号中不给值
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            System.out.println(id + "\t" + name);
        }
    }
}
```

## JDBC API

![image-20221109114503740](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221109114503740.png)

## JDBCUtils

在jdbc操作中，获取连接和释放资源是经常使用到的，可以将其封装JDBC连接的工具类JDBCUtils

JDBC工具类代码

```java
package com.zyf.jdbc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author: 周杨凡
 * @date: 2022-11-09
 * 这是一个工具类
 */
public class JDBCUtils {
    //定义相关的属性
    //因为只需要一份，因此，我们做成静态的

    private static String user;
    private static String password;
    private static String url;
    private static String driver;

    //在代码块中初始化
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src//mysql.properties"));
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driver = properties.getProperty("driver");
        } catch (IOException e) {
            //在实际开发中，将编译异常转成运行异常
            //这使调用者，可以选择捕获该异常，也可以默认处理该异常，比较方便
            throw new RuntimeException(e);
        }
    }

    //连接数据库，返回Connection
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //关闭资源
    //结果集ResultSet，Statement和PreparedStatement，connection
    //如果需要关闭资源就传入对象，否则就传入空

    public static void close(ResultSet set, Statement statement, Connection connection){
        //判断是否为空
        try {
            if(set != null){
                set.close();
            }
            if(statement != null){
                statement.close();
            }

            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
```

利用工具类进行增删改查

```java
package com.zyf.jdbc.utils;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: 周杨凡
 * @date: 2022-11-09
 */
public class JDBCUtils_Use {

    @Test
    public void testDML(){
        Connection connection = null;

        PreparedStatement preparedStatement = null;

        String sql = "update actor set name = ? where id = ?";

        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "lll");
            preparedStatement.setInt(2,1);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }

    @Test
    public void testSel(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from actor where name = ? ";
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "lll");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println(id + "\t" + name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtils.close(resultSet, preparedStatement, connection);
        }
    }
}
```

## 事务

1. JDBC程序中当一个Connection对象创建时，默认情况下是自动提交事务：每次执行一个SQL语句时，如果执行成功，就会向数据库自动提交，而不能回滚。
2. JDBC程序中为了让多个SQL语句作为一个整体执行，需要使用事务。
3. 调用Connection的setAutoCommit(false)可以取消自动提交事务。
4. 在所有的SQL语句都成功执行后，调用Connection对象的commit()方法提交事务。
5. 在其中某个操作失败或出现异常时，调用rollback()方法回滚事务。

**不使用事务**

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221109154243953.png" alt="image-20221109154243953" style="zoom:33%;" />

```java
package com.zyf.jdbc.transaction_;

import com.zyf.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: 周杨凡
 * @date: 2022-11-09
 * 演示jdbc中如何使用事务
 */
public class Transaction_ {

    @Test
    public void noTransaction() {
        Connection connection = null;

        PreparedStatement preparedStatement = null;

        String sql1 = "update account set balance = balance - 100 where id = 1";
        String sql2 = "update account set balance = balance + 100 where id = 2";
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();
            int i = 1 / 0;
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }
}
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221109154409756.png" alt="image-20221109154409756" style="zoom:33%;" />

不能保证数据的一致性

**使用事务**

```java
public void useTransaction(){
    Connection connection = null;

    PreparedStatement preparedStatement = null;

    String sql1 = "update account set balance = balance - 100 where id = 1";
    String sql2 = "update account set balance = balance + 100 where id = 2";
    try {
        connection = JDBCUtils.getConnection();
        //setAutoCommit(false)相当于开启了事务
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(sql1);
        preparedStatement.executeUpdate();
        int i = 1 / 0;
        preparedStatement = connection.prepareStatement(sql2);
        preparedStatement.executeUpdate();

        //提交是事务
        connection.commit();
    } catch (Exception e) {
        //这里可以进行回滚，撤销执行的SQL
        //默认回滚到事务开始的状态
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("执行发生了异常，撤销执行的SQL");
        e.printStackTrace();
    } finally {
        JDBCUtils.close(null, preparedStatement, connection);
    }
}
```

## 批处理

- 当需要成批插入或跟新记录时。可以采用Java的批量跟新机制，这一机制允许许多条语句一次性提交给数据库批量处理。通常情况下比单独提交处理更有效率。
- JDBC的批处理语句包括下面方法：
  1. addBatch()：添加需要批量处理的SQL语句或参数
  2. executeBatch()：执行批处理语句；
  3. clearBatch()；清空批处理包的语句

- **JDBC连接MySQL时，如果要使用批处理功能，请在url加参数`?rewriteBatchedStatements=true`**
- 批处理往往和PreparedStatement一起搭配使用，既可以减少编译次数，又减少运行次数，效率大大提高。

```java
package com.zyf.jdbc.batch_;

import com.zyf.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.beans.Transient;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author: 周杨凡
 * @date: 2022-11-09
 */
public class Batch_ {
    //传统方法，添加5000条数据到admin2

    @Test
    public void noBatch() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into admin2 values (null, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始执行");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            preparedStatement.setString(1, "jack" + i);
            preparedStatement.setString(2, "666");
            preparedStatement.executeUpdate();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间为" + (end - start));
        JDBCUtils.close(null, preparedStatement, connection);
    }

    //使用批量方式添加
    @Test
    public void Batch() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into admin2 values (null, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始执行");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            preparedStatement.setString(1, "jack" + i);
            preparedStatement.setString(2, "666");
            //将sql语句加入到批处理包中
            preparedStatement.addBatch();
            if((i + 1) % 1000 == 0){
                preparedStatement.executeBatch();
                //清空一把
                preparedStatement.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("批量方式执行时间为" + (double)(end - start)/1000);
        JDBCUtils.close(null, preparedStatement, connection);
    }
}
```

批处理源码

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221109165218915.png" alt="image-20221109165218915" style="zoom:50%;" />

执行批处理，会将sql语句放入到名为batchedArgs的ArrayList集合的elementData的对象数组中。

## 数据库连接池

1. 传统的JDBC数据库连接使用DriverManger来获取，每次向数据库建立连接的时候要将Connection加载到内存中，再进行验证。频繁进行数据连接操作将占用很多的系统资源，容易造成服务器崩溃。
2. 每一次数据库的连接，使用完成后都得断开，如果程序出现异常而未能关闭，将导致数据库内存泄漏，最终将导致重启数据库。
3. 传统获取连接得方式，不能控制创建连接得数量，如连接过多，也可能导致内存泄漏，MySQL崩溃。
4. 解决传统开发中的数据库连接问题，可以采用数据库连接池技术（connection pool）

**数据库连接池的基本原理**

1. 预先再缓冲池中放入一定数量的连接，当需要建立数据库连接时，只需从“缓冲池”中取出一个，使用完毕后再放回去
2. 数据库连接池负责分配、管理和释放数据库连接，它允许应用程序重复使用一个现有的数据库连接，而不是重新建立一个。
3. 当应用程序向连接池请求的连接数超过最大连接数量时，这些请求将被加入到等待队列中。

创建C3P0数据库连接池的两种方式

```java
package com.zyf.jdbc.datasource;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: 周杨凡
 * @date: 2022-11-09
 * 演示c3p0的使用
 */
public class C3P0_ {
    //相关的参数在程序中指定
    
    @Test
    public void testC3P0_01() throws Exception {
        //创建一个数据源对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        //通过配置文件获取相关的连接信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src//mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");

        //给数据源comboPooledDataSource设置相关参数
        //连接是由comboPooledDataSource来管理
        comboPooledDataSource.setDriverClass(driver);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);


        //设置初始化连接数
        comboPooledDataSource.setInitialPoolSize(10);
        //最大连接数
        comboPooledDataSource.setMaxPoolSize(50);
        //这个方法就是从DataSource接口实现的
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println("连接成功");
        connection.close();
    }

    @Test
    //第二种方式使用配置模板来操作
    public void testC3P0_02() throws SQLException {
        //将c3p0提供的c3p0-config.xml拷贝到src目录下
        //该文件制定了连接数据库和连接池的相关参数
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("zyf");
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println("连接ok");
        connection.close();
    }
}
```

创建德鲁伊数据库连接池

```java
package com.zyf.jdbc.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author: 周杨凡
 * @date: 2022-11-09
 */
public class Druid_ {

    @Test
    public void testDruid() throws Exception {
        //加入jar包
        //加入配置文件druid.properties，将该文件拷贝到项目的src目录
        //创建Properties对象,读取配置文件
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\druid.properties"));

        //创建一个指定参数的数据库连接池,Druid连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        System.out.println("连接成功");
        connection.close();
    }
}
```

使用德鲁伊数据库连接池改进JDBCUtils工具类

```java
package com.zyf.jdbc.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author: 周杨凡
 * @date: 2022-11-09
 * 基于德鲁伊数据库连接池的工具类
 */
public class JDBCUtilsByDruid {
    private static DataSource ds;

    //在静态代码块种完成ds的初始化
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src//druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //编写getConnection方法

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    //关闭连接,数据库连接池中，不是断掉连接，而是将使用connection对象放回连接池

    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        try {
            if(resultSet != null){
                resultSet.close();
            }

            if(statement != null){
                statement.close();
            }

            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221110092523371.png" alt="image-20221110092523371" style="zoom:67%;" />

## Apache-DBUtils工具类

- 关闭连接connection是关联的，即如果关闭连接，就不能使用结果集
- 结果集不利于数据的管理（只能用一次）
- 使用数据也不方便

**解决思路：**

- 一条结果集对应一个对象，将对象放入到ArrayList中。

```java
@Test
public void testSelectToArrayList(){
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ArrayList<Actor> list = new ArrayList<>();
    String sql = "select * from actor ";
    try {
        connection = JDBCUtilsByDruid.getConnection();
        System.out.println(connection.getClass());
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String sex = resultSet.getString("sex");
            Date borndate = resultSet.getDate("borndate");
            String phone = resultSet.getString("phone");
            //把得到的resultset记录封装到Actor对象，放入到list集合中
            list.add(new Actor(id, name, sex, borndate, phone));
        }
        System.out.println("list集合数据 = " + list);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }finally {
        JDBCUtilsByDruid.close(resultSet, preparedStatement, connection);
    }
}

package com.zyf.jdbc.utils;

import java.util.Date;

/**
 * @author: 周杨凡
 * @date: 2022-11-10
 * Actor对象和actor表的记录对应
 */
public class Actor { //javabean POJO
    private Integer id;
    private String name;
    private String sex;
    private Date borndate;
    private String phone;

    public Actor(){}

    public Actor(Integer id, String name, String sex, Date borndate, String phone) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.borndate = borndate;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBorndate() {
        return borndate;
    }

    public void setBorndate(Date borndate) {
        this.borndate = borndate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", borndate=" + borndate +
                ", phone='" + phone + '\'' +
                '}';
    }
}
```

Apache-DBUtils工具类基本介绍

1. commons-dbutils是Apache组织提供的一个开源JDBC工具类库，它是对JDBC的封装，使用dbutils能极大简化jdbc编码的工作量。

2. DbUtils

   - QueryRunner类，该类封装了SQL的执行，是线程安全的。可以实现增删改查和批处理。

   - 使用QueryRunner类实现查询。

   - ResultSetHandler接口，该接口用于处理java.sql.ResultSet，将数据按要求转换为另外一种形式

apache-DBUtils + Druid进行增删改查

```java
package com.zyf.jdbc.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: 周杨凡
 * @date: 2022-11-09
 */
@SuppressWarnings({"all"})
public class DBUtils_USE {

    //使用apache-DBUtils 工具类 + druid 的方式完成对表的crud操作
    @Test
    public void testQueryMany() throws SQLException {
        //1.得到连接
        Connection connection = JDBCUtilsByDruid.getConnection();

        //2.使用DBUtils类和接口，先引入DBUtils相关的jar文件
        //3.创建queryRunner
        QueryRunner queryRunner = new QueryRunner();
        //4.就可以执行相关方法，返回一个Arraylist结果集
        String sql = "select * from actor where id > ?";
        //query方法就是执行sql语句，得到ResultSet 封装到ArrayList集合中
        //connection ：连接
        //sql： 查询语句
        //new BeanListHandler<>(Actor.class)：在将resultset -> Actor ->封装到集合中
        //底层使用反射机制，去获取Actor属性，然后去封装
        //最后一个参数1就是sql语句中?的赋值，可以有多个值，因为是可变参数Object... params
        //底层得到的resultset, perparestatement会在query中关闭
        List<Actor> list = queryRunner.query(connection, sql, new BeanListHandler<>(Actor.class), 1);
        System.out.println("输出集合信息");
        for (Actor actor : list) {
            System.out.println(actor);
        }
        //释放资源
        JDBCUtilsByDruid.close(null, null, connection);
    }

    //演示apache-dbutils + druid 返回的结果是单行记录
    @Test
    public void testQuerySingle() throws SQLException {
        Connection connection = JDBCUtilsByDruid.getConnection();
        QueryRunner queryRunner = new QueryRunner();

        String sql = "select * from actor where id = ?";
        //返回单行记录，使用BeanHandler
        Actor actor = queryRunner.query(connection, sql, new BeanHandler<>(Actor.class), 2);
        System.out.println(actor);

        //释放资源
        JDBCUtilsByDruid.close(null, null, connection);
    }


    @Test
    //演示apache-dbutils 完成查询结果是单行单列的情况（返回Object）
    public void testScalar() throws SQLException {
        Connection connection = JDBCUtilsByDruid.getConnection();
        QueryRunner queryRunner = new QueryRunner();

        String sql = "select `name` from actor where id = ?";
        Object scalar = queryRunner.query(connection, sql, new ScalarHandler<>(), 1);
        System.out.println(scalar);
        //释放资源
        JDBCUtilsByDruid.close(null, null, connection);
    }

    @Test
    //演示apache——dbutils + druid 完成 dml (update， insert, delete)
    public void testDML() throws SQLException {
        Connection connection = JDBCUtilsByDruid.getConnection();
        QueryRunner queryRunner = new QueryRunner();
//        String sql = "update actor set name = ? where id = ?";
        String sql = "insert into actor values(?, ?, ?, ?, ?)";
        //update也可以执行insert和delete
        //update方法的返回值为影响的行数
        int affectedRow = queryRunner.update(connection, sql, 5,  "周杰伦", "男", "1978-09-09", "1322199418");
        System.out.println(affectedRow > 0 ? "执行成功" : "执行没有影响数据表");

        //释放资源
        JDBCUtilsByDruid.close(null, null, connection);
    }
}
```

## DAO和增删改查通用方法 - BasicDap

apache-DBUtils + Druid简化了JDBC开发，但还有不足：

1. SQL语句是固定，不能通过参数传入，通用性不好，需要进行改进，更方便执行增删改查
2. 对于select操作，如果有返回值，返回值类型不能固定，需要使用泛型
3. 将来的表很多，业务需求复杂，不可能只靠一个Java类完成

基本说明：

1. DAO：data access object数据访问对象
2. 这样的通用类，成为BasicDao，是专门和数据库交互的，即完成对数据库（表）的crud操作
3. 在BasicDao的基础上，实现一张表对应一个Dao，更好的完成功能。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221122183928526.png" alt="image-20221122183928526" style="zoom:50%;" />

BasicDAO

```java
package com.zyf.dao_.dao;

import com.zyf.dao_.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: 周杨凡
 * @date: 2022-11-22
 * 开发BasicDAO，是其他类的父类
 */
public class BasicDAO<T> {
    private QueryRunner qr = new QueryRunner();

    //开发通用的dml方法，针对任意的表
    public int update(String sql, Object...parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();

            int update = qr.update(connection, sql, parameters);
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e);
            //将编译异常转为运行异常，抛出
        }finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }


    /**
     * @param sql sql语句可以有？
     * @param clazz 传入一个Class对象，比如Actor.class
     * @param parameters 传入？的具体的值，可以是多个
     * @return 根据Actor.class类型返回ArrayList集合
     */
    //返回多个对象（即查询结果是多行）即对任意表
    public List<T> queryMulti(String sql, Class<T> clazz, Object...parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();
            List<T> query = qr.query(connection, sql, new BeanListHandler<>(clazz), parameters);

            return query;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }

    //查询单行结果的通用方法
    public T querySingle(String sql, Class<T> clazz, Object...parameters)  {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

    //返回单值的方法

    public Object queryScalar(String sql, Object...parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new ScalarHandler<>(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }
}
```

TestDAO

```java
package com.zyf.dao_.test;

import com.zyf.dao_.dao.ActorDAO;
import com.zyf.dao_.domain.Actor;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: 周杨凡
 * @date: 2022-11-22
 */
public class TestDAO {
    //测试ActorDAO 对actor表的crud操作
    @Test
    public void testActorDao()  {
        //1.查询
        ActorDAO actorDAO = new ActorDAO();

        List<Actor> actors = actorDAO.queryMulti("select * from actor where id >= ?", Actor.class, 1);
        System.out.println("查询结果");
        for (Actor actor : actors) {
            System.out.println(actor);
        }

        Actor actor = actorDAO.querySingle("select * from actor where id = ?", Actor.class, 1);
        System.out.println("单个查询结果");
        System.out.println(actor);

        //3查询单行单列
        Object o = actorDAO.queryScalar("select name from actor where id = ?", 3);
        System.out.println("查询单行单列");
        System.out.println(o);

        //dml操作
        int update = actorDAO.update("insert into actor values(?, ?, ?, ?, ?)",6,"王刚","男","1956-06-09","13068985658");
        System.out.println(update);
    }
}
```

