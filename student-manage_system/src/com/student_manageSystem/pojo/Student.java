package com.student_manageSystem.pojo;

public class Student {
    String id;
    String pwd;
    String name;
    String sex;
    int grade;
    int chinese;
    int math;
    int english;
    int chemistry;
    int political;
    int history;
    int total_score;
    String teacher_id;

    public Student() {}

    public Student(String id, String pwd, String name, String sex, int grade, int chinese, int math, int english, int chemistry, int political, int history, int total_score, String teacher_id) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.sex = sex;
        this.grade = grade;
        this.chinese = chinese;
        this.math = math;
        this.english = english;
        this.chemistry = chemistry;
        this.political = political;
        this.history = history;
        this.total_score = total_score;
        this.teacher_id = teacher_id;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getChinese() {
        return chinese;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getChemistry() {
        return chemistry;
    }

    public void setChemistry(int chemistry) {
        this.chemistry = chemistry;
    }

    public int getPolitical() {
        return political;
    }

    public void setPolitical(int political) {
        this.political = political;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        String result = "Student{id:" + this.id + "," +
                "pwd:" + this.pwd + "," +
                "name:" + this.name + "," +
                "sex:" + this.sex + "," +
                "grade:" + this.grade + "," +
                "chinese:" + this.chinese + "," +
                "math:" + this.math + "," +
                "english:" + this.english + "," +
                "chemistry:" + this.chemistry + "," +
                "political:" + this.political + "," +
                "history:" + this.history + "," +
                "total_score:" + this.total_score + "," +
                "teacher_id:" + this.teacher_id + "}";
        return result;
    }
}
