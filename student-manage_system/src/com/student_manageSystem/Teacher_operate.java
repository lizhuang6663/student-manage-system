package com.student_manageSystem;

import com.student_manageSystem.dao.impl.StudentImpl;
import com.student_manageSystem.dao.impl.TeacherImpl;
import com.student_manageSystem.pojo.Student;
import com.student_manageSystem.utils.CommonUtils;
import com.student_manageSystem.utils.ViewUtils;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;


/**
 * 教师只能增删改查 自己的学生
 */
public class Teacher_operate {
    private static final StudentImpl studentDao = new StudentImpl();
    private static final TeacherImpl teacherDao = new TeacherImpl();




    /**
     * 教师增加学生
     * @param borderPane : 边界布局
     * @param students : 学生集合
     * @param id_t : 教师ID
     */
    public static void addStudent(BorderPane borderPane, ArrayList<Student> students, String id_t) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();

        ViewUtils.setTopTip("增加学生",tipStage);
        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label studentID = new Label("学生学号");
        TextField studentIDTxt = new TextField();
        Label pwd = new Label("学生密码");
        TextField pwdTxt = new TextField();
        Label name = new Label("学生姓名");
        TextField nameTxt = new TextField();
        Label sex = new Label("性别");
        RadioButton man = new RadioButton("男");
        RadioButton woman = new RadioButton("女");
        Label grade = new Label("学生年级");
        TextField gradeTxt = new TextField();
        Label teacher_id = new Label("教师姓名");
        TextField teacher_idTxt = new TextField();
        Button add = new Button("增加");
        Button back = new Button("返回");
        //单行面板
        HBox hBox = new HBox(man, woman);

        gridPane.add(studentID, 0, 0);
        gridPane.add(studentIDTxt, 1, 0);
        gridPane.add(pwd, 0, 1);
        gridPane.add(pwdTxt, 1, 1);
        gridPane.add(name, 0, 2);
        gridPane.add(nameTxt, 1, 2);
        gridPane.add(sex, 0, 3);
        gridPane.add(hBox, 1, 3);
        gridPane.add(grade, 0, 4);
        gridPane.add(gradeTxt, 1, 4);
        gridPane.add(teacher_id, 0, 6);
        gridPane.add(teacher_idTxt, 1, 6);
        gridPane.add(add, 0, 8);
        gridPane.add(back, 1, 8);

        //点击增加学生
        add.setOnAction(actionEvent -> {
            //数据要符合规范
            boolean isEmpty = !studentIDTxt.getText().equals("") && !pwdTxt.getText().equals("")  && !nameTxt.getText().equals("")
                    && (man.isSelected() || woman.isSelected()) && !teacher_idTxt.getText().equals("");
            boolean isNum = CommonUtils.isStrToNum(gradeTxt.getText());
            boolean isSuitable = !gradeTxt.getText().equals("") &&  CommonUtils.isDataSuitable(Integer.parseInt(gradeTxt.getText()), 1, 4);
            //要判断教师的姓名是否存在，而不是教师ID，教师姓名要存在；学生学号不能存在
            boolean isSuitable2 = teacherDao.judge_teacherNameIsExist(teacher_idTxt.getText()) && !studentDao.judge_StudentIDIsExist(studentIDTxt.getText());

            Student student = new Student();
            student.setTeacher_id(teacher_idTxt.getText());
            student.setId(studentID.getText());
            studentDao.select_teacherByTeacher_id(student);
            boolean isSuitable3 = teacherDao.judgeTeacher_nameAndId(student.getTeacher_id(),id_t);

            try {
                //学生ID不能重复；学生修改的教师必须存在
                if (isEmpty && isNum && isSuitable && isSuitable2 && isSuitable3) {
                    String sexDemo = man.isSelected() ? "男" : "女";

                    studentDao.insert_student(studentIDTxt.getText(), pwdTxt.getText(), nameTxt.getText(), sexDemo,
                            Integer.parseInt(gradeTxt.getText()), teacher_idTxt.getText());

                    ViewUtils.tips("增加成功");
                    tipStage.close();
                    //刷新表格中的数据
                    refresh_studentTable_forTeacher(borderPane, students, id_t);

                }else if (!isSuitable2) {
                    ViewUtils.tips("请检查教师姓名或学生ID");
                }else {
                    ViewUtils.tips("请检查数据");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

        //点击返回按钮，关闭提示框
        back.setOnAction(actionEvent -> {
            tipStage.close();
        });

        //设置性别只能选择一个
        ToggleGroup toggleGroup = new ToggleGroup();//将单选按钮添加到到ToggleGroup对象，它将管理它们，使得一次只能选择一个单选按钮。
        man.setToggleGroup(toggleGroup);
        woman.setToggleGroup(toggleGroup);

        Scene scene = new Scene(gridPane, 340, 400);
        tipStage.setScene(scene);
        tipStage.show();
    }






    /**
     * 删除学生，老师
     * @param borderPane : 边界布局
     * @param students : 学生集合
     * @param id_t : 教师ID
     */
    public static void deletePerson(BorderPane borderPane, ArrayList<Student> students, String id_t) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();
        ViewUtils.setTopTip("删除人员", tipStage);
        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label ID = new Label("ID号码 :  ");
        TextField IDTxt = new TextField();
        Button delete = new Button("删除");
        Button back = new Button("返回");

        gridPane.add(ID, 0, 0);
        gridPane.add(IDTxt, 1, 0);
        gridPane.add(delete, 0, 2);
        gridPane.add(back, 1, 2);

        //点击删除按钮
        delete.setOnAction(actionEvent -> {

            Student student = new Student();
            student.setTeacher_id(id_t);
            student.setId(IDTxt.getText());
            studentDao.select_teacherByTeacher_id(student);
            boolean isSuitable3 = teacherDao.judgeTeacher_nameAndId(student.getTeacher_id(),id_t);

            //删除学生，先判断是否存在该学生
            if (studentDao.judge_StudentIDIsExist(IDTxt.getText()) && isSuitable3) {
                studentDao.delete_student(IDTxt.getText());
                ViewUtils.tips("成功删除该学生");
                tipStage.close();
                //刷新表格中的数据
                refresh_studentTable_forTeacher(borderPane, students, id_t);
            }else {
                ViewUtils.tips("请检查数据");
            }
        });

        //点击返回按钮，关闭提示框
        back.setOnAction(actionEvent -> {
            tipStage.close();
        });

        Scene scene = new Scene(gridPane, 360, 200);
        tipStage.setScene(scene);
        tipStage.show();
    }






    /**
     * 教师修改学生：必须先输入正确的ID号码后，才能修改
     * @param borderPane : 边界布局
     * @param students : 学生集合
     * @param id_t : 教师ID，用于判断某个老师是否可以操作某个学生（教师只能操作自己的学生）
     */
    public static void inputID(BorderPane borderPane, ArrayList<Student> students, String id_t) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();
        ViewUtils.setTopTip("搜索ID号码", tipStage);

        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label ID = new Label("ID号码 :  ");
        TextField IDTxt = new TextField();
        Button search = new Button("搜索");
        Button back = new Button("返回");

        gridPane.add(ID, 0, 0);
        gridPane.add(IDTxt, 1, 0);
        gridPane.add(search, 0, 2);
        gridPane.add(back, 1, 2);

        //点击搜索按钮
        search.setOnAction(actionEvent -> {
            Student student = new Student();
            student.setTeacher_id(id_t);
            student.setId(IDTxt.getText());
            studentDao.select_teacherByTeacher_id(student);
            boolean isSuitable3 = teacherDao.judgeTeacher_nameAndId(student.getTeacher_id(),id_t);

            //修改学生，先查询该学生是否存在，而且要修改的学生属于该教师
            if (studentDao.judge_StudentIDIsExist(IDTxt.getText()) && isSuitable3) {
                updateStudent(borderPane, students, IDTxt.getText(), id_t);
                tipStage.close();
            }else {
                ViewUtils.tips("请检查数据");
            }

        });

        //点击返回按钮，关闭提示框
        back.setOnAction(actionEvent -> {
            tipStage.close();
        });

        Scene scene = new Scene(gridPane, 360, 200);
        tipStage.setScene(scene);
        tipStage.show();
    }





    /**
     * 输入正确的ID号码后，教师修改学生（学生的密码无法修改）
     * @param borderPane : 边界布局
     * @param students : 学生集合
     * @param id_sOld : 需要修改的学生的 原来的ID号码，我们要根据原来的ID来查找这个学生
     * @param id_t : 教师ID
     */
    public static void updateStudent(BorderPane borderPane, ArrayList<Student> students, String id_sOld, String id_t) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();
        ViewUtils.setTopTip("修改学生", tipStage);

        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        //先查询这个学生
        studentDao.select_studentTableByID_forTeacher(students, id_sOld, id_t);
        Student student = students.get(0);


        Label studentID = new Label("学号 :  ");
        TextField studentIDTxt = new TextField(student.getId());
        Label name = new Label("姓名 :  " );
        TextField nameTxt = new TextField(student.getName());
        Label sex = new Label("性别 :  " );
        RadioButton man = new RadioButton("男");
        RadioButton woman = new RadioButton("女");
        if (student.getSex().equals("男")) {
            man.setSelected(true);
        } else if (student.getSex().equals("女")) {
            woman.setSelected(true);
        }
        Label grade = new Label("年级 :  ");
        TextField gradeTxt = new TextField(String.valueOf(student.getGrade()));
        Label chinese = new Label("语文 :  " );
        TextField chineseTxt = new TextField(String.valueOf(student.getChinese()));
        Label math = new Label("数学 :  " );
        TextField mathTxt = new TextField(String.valueOf(student.getMath()));
        Label english = new Label("英语 :  " );
        TextField englishTxt = new TextField(String.valueOf(student.getEnglish()));
        Label chemistry = new Label("化学 :  " );
        TextField chemistryTxt = new TextField(String.valueOf(student.getChemistry()));
        Label political = new Label("政治 :  " );
        TextField politicalTxt = new TextField(String.valueOf(student.getPolitical()));
        Label history = new Label("历史 :  " );
        TextField historyTxt = new TextField(String.valueOf(student.getHistory()));
        Label teacher_id = new Label("教师姓名 :  " );
        TextField teacher_idTxt = new TextField(student.getTeacher_id());
        Button update = new Button("修改");
        Button back = new Button("返回");

        //单行面板
        HBox hBox = new HBox(man, woman);

        gridPane.add(studentID, 0, 0);
        gridPane.add(studentIDTxt, 1, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(nameTxt, 1, 1);
        gridPane.add(sex, 0, 2);
        gridPane.add(hBox, 1, 2);
        gridPane.add(grade, 0, 3);
        gridPane.add(gradeTxt, 1, 3);
        gridPane.add(chinese, 0, 4);
        gridPane.add(chineseTxt, 1, 4);
        gridPane.add(math, 0, 5);
        gridPane.add(mathTxt, 1, 5);
        gridPane.add(english, 0, 6);
        gridPane.add(englishTxt, 1, 6);
        gridPane.add(chemistry, 0, 7);
        gridPane.add(chemistryTxt, 1, 7);
        gridPane.add(political, 0, 8);
        gridPane.add(politicalTxt, 1, 8);
        gridPane.add(history, 0, 9);
        gridPane.add(historyTxt, 1, 9);
        gridPane.add(teacher_id, 0, 10);
        gridPane.add(teacher_idTxt, 1, 10);
        gridPane.add(update, 0, 12);
        gridPane.add(back, 1, 12);

        //点击修改按钮
        update.setOnAction(actionEvent -> {
            //是否为空
            boolean isData = !studentIDTxt.getText().equals("") && !nameTxt.getText().equals("") && (man.isSelected() || woman.isSelected());
            //其中的班级和分数是否可以转换为数字
            boolean isNum = CommonUtils.isStrToNum(gradeTxt.getText()) && CommonUtils.isStrToNum(chineseTxt.getText()) && CommonUtils.isStrToNum(mathTxt.getText()) &&
                    CommonUtils.isStrToNum(englishTxt.getText()) && CommonUtils.isStrToNum(chemistryTxt.getText()) && CommonUtils.isStrToNum(politicalTxt.getText()) &&
                    CommonUtils.isStrToNum(historyTxt.getText());
            //数据是否合适
            boolean isSuitable = !gradeTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(gradeTxt.getText()), 1, 4) &&
                    !chineseTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(chineseTxt.getText()), 0, 100) &&
                    !mathTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(mathTxt.getText()), 0, 100) &&
                    !englishTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(englishTxt.getText()), 0, 100) &&
                    !chemistryTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(chemistryTxt.getText()), 0, 100) &&
                    !politicalTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(politicalTxt.getText()), 0, 100) &&
                    !historyTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(historyTxt.getText()), 0, 100);
            //判断该教师是否存在；判断新修改的学生ID是否已经被占有了
            boolean isSuitable2 = teacherDao.judge_teacherNameIsExist(teacher_idTxt.getText()) && (!studentDao.judge_StudentIDIsExist(studentIDTxt.getText()) || studentIDTxt.getText().equals(id_sOld));

            boolean isSuitable3 =  teacherDao.judgeTeacher_nameAndId(teacher_idTxt.getText(), id_t);

            //数据正常
            if (isData && isNum && isSuitable && isSuitable2 && isSuitable3){
                String sexDemo = man.isSelected() ? "男" : "女";
                int totalScore = Integer.parseInt(chineseTxt.getText()) + Integer.parseInt(mathTxt.getText()) + Integer.parseInt(englishTxt.getText()) +
                        Integer.parseInt(chemistryTxt.getText()) + Integer.parseInt(politicalTxt.getText()) + Integer.parseInt(historyTxt.getText());

                //教师姓名转化为教师ID：studentDao.replaceName_withId(teacher_idTxt.getText())
                //修改学生信息
                //把数据封装到Student对象中，传入到函数
                Student student1 = new Student(studentIDTxt.getText(),"", nameTxt.getText(), sexDemo, Integer.parseInt(gradeTxt.getText()),
                        Integer.parseInt(chineseTxt.getText()), Integer.parseInt(mathTxt.getText()), Integer.parseInt(englishTxt.getText()), Integer.parseInt(chemistryTxt.getText()),
                        Integer.parseInt(politicalTxt.getText()), Integer.parseInt(historyTxt.getText()), totalScore, studentDao.replaceName_withId(teacher_idTxt.getText()));

                studentDao.update_studentByTeacherAndManager(id_sOld, student1);

                ViewUtils.tips("修改成功");
                tipStage.close();
                //刷新表格中的数据
                refresh_studentTable_forTeacher(borderPane, students, id_t);
            }else  {
                ViewUtils.tips("请检查数据");
            }

        });

        //点击返回按钮，关闭提示框
        back.setOnAction(actionEvent -> {
            tipStage.close();
        });

        //设置性别只能选择一个
        ToggleGroup toggleGroup = new ToggleGroup();
        //将单选按钮添加到到ToggleGroup对象，ToggleGroup对象将管理它们，使得一次只能选择一个单选按钮。
        man.setToggleGroup(toggleGroup);
        woman.setToggleGroup(toggleGroup);

        Scene scene = new Scene(gridPane, 440, 680);
        tipStage.setScene(scene);
        tipStage.show();
    }





    /**
     * 教师查询学生
     * @param borderPane : 边界布局
     * @param students : 学生集合
     * @param id_t : 教师ID
     */
    public static void queryStudent_byTeacher(BorderPane borderPane, ArrayList<Student> students, String id_t) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();
        ViewUtils.setTopTip("可以进行ID查询、姓名模糊查询", tipStage);

        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label studentID = new Label("学号 :  ");
        TextField studentIDTxt = new TextField();
        Label name = new Label("姓名 :  ");
        TextField nameTxt = new TextField();
        Button query = new Button("查询");
        Button back = new Button("返回");

        gridPane.add(studentID, 0, 0);
        gridPane.add(studentIDTxt, 1, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(nameTxt, 1, 1);
        gridPane.add(query, 0, 3);
        gridPane.add(back, 1, 3);

        //点击查询按钮
        query.setOnAction(actionEvent -> {
            //只通过ID查询学生
            if (!studentIDTxt.getText().equals("") && nameTxt.getText().equals("")){
                //将ID符合的学生添加到students集合中
                studentDao.select_studentTableByID_forTeacher(students, studentIDTxt.getText(), id_t);

                Main.studentTable(borderPane, students);
                tipStage.close();

            } else if (studentIDTxt.getText().equals("") && !nameTxt.getText().equals("")) {//只通过姓名模糊查询
                //只通过姓名模糊查询
                studentDao.select_studentTableByName_forTeacher(students, nameTxt.getText(), id_t);

                Main.studentTable(borderPane, students);
                tipStage.close();

            } else { //没有输入数据
                ViewUtils.tips("请检查数据，二选一");
            }
        });

        //点击返回按钮，关闭提示框
        back.setOnAction(actionEvent -> {
            tipStage.close();
        });

        Scene scene = new Scene(gridPane, 480, 400);
        tipStage.setScene(scene);
        tipStage.show();
    }





    /**
     * 教师刷新学生表格(增加，删除，修改后调用)
     * @param borderPane ： 边界布局
     * @param students ： 学生集合
     * @param id_t : 教师ID
     */
    public static void refresh_studentTable_forTeacher(BorderPane borderPane, ArrayList<Student> students, String id_t) {
        //填写学生数据
        studentDao.selectAll_studentTable_forTeacher(students, id_t);

        //展示学生表格
        Main.studentTable(borderPane, students);
    }


}
