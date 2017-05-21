package com.zxf.reflect;

/**
 * Created by thinkpad on 2017/5/16.
 */
public class UserBean {
    @Alias("user_name")
    public String userName;
    @Alias("user_id")
    private long userId;

    public UserBean(String userName, long userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getName() {
        return userName;
    }

    public long getId() {
        return userId;
    }

    @Invoke
    @Test(value = "static_method",id=1)
    public static void staticMethod(String devName) {
        System.out.printf("Hi %s, I'm a static method\n", devName);
    }

    @Invoke
    @Test(value="public_method",id=2)
    public void publicMethod() {
        System.out.println("I'm a public method\n");
    }

    @Invoke
    @Test(value="private_method",id=3)
    private void privateMethod() {
        System.out.println("I'm a private method\n");
    }

    @Test(id = 4)
    public void testFailure(){
        throw new RuntimeException("Test Failure");
    }
}
