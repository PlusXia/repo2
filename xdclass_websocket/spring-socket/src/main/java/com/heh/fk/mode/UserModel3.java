package com.heh.fk.mode;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "user1")
public class UserModel3 {

    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserModel3{" +
                "age=" + age +
                '}';
    }
}
