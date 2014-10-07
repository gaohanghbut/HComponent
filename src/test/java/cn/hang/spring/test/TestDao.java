package cn.hang.spring.test;

public class TestDao {
    private String name;
    public void selectAll() {
        System.out.println("all " + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
