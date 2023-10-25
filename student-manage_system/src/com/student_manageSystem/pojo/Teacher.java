package com.student_manageSystem.pojo;

public class Teacher {
    String id;
    String pwd;
    String name;
    String sex;
    int age;
    String graduate_school;
    int salary;
    String telephone;

    public Teacher() {}

    public Teacher(String id, String pwd, String name, String sex, int age, String graduate_school, int salary, String telephone) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.graduate_school = graduate_school;
        this.salary = salary;
        this.telephone = telephone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGraduate_school() {
        return graduate_school;
    }

    public void setGraduate_school(String graduate_school) {
        this.graduate_school = graduate_school;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        String result = "Teacher{id:" + this.id + "," +
                "pwd:" + this.pwd + "," +
                "name:" + this.name + "," +
                "sex:" + this.sex + "," +
                "age:" + this.age + "," +
                "graduate_school:" + this.graduate_school + "," +
                "salary:" + this.salary + "," +
                "telephone:" + this.telephone + "}";
        return result;
    }
}
