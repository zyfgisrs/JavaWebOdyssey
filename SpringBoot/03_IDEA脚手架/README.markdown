# IDEA脚手架创建SpringBoot项目

- 创建springboot3项目

  <img src="assets/image-20231009122110957.png" alt="image-20231009122110957" style="zoom:67%;" />

- 选择版本以及添加相关依赖

  <img src="assets/image-20231009122241283.png" alt="image-20231009122241283" style="zoom:67%;" />

- 点击创建

生成的`build.gradle`文件：

```groovy
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.1.4'
	id 'io.spring.dependency-management' version '1.1.3'
}

group = 'com.zhouyf'
version = '0.0.1-SNAPSHOT'

java {
	sourceCompatibility = '17'
}

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
	useJUnitPlatform()
}
```

