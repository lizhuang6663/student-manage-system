package com.student_manageSystem;

import com.student_manageSystem.dao.impl.ManagerImpl;
import com.student_manageSystem.dao.impl.StudentImpl;
import com.student_manageSystem.dao.impl.TeacherImpl;
import com.student_manageSystem.pojo.Student;
import com.student_manageSystem.pojo.Teacher;
import com.student_manageSystem.utils.CommonUtils;
import com.student_manageSystem.utils.ViewUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * 管理员操作学生、教师；管理员自身的一些操作
 */
public class Manager_operate {
    private static final StudentImpl studentDao = new StudentImpl();
    private static final TeacherImpl teacherDao = new TeacherImpl();
    private static final ManagerImpl managerDao = new ManagerImpl();


    /**
     * 关于管理员的操作
     * ------------------------------------------------------------------------------------------------------------------------
     * 管理员登录
     */
    public static void managerLog() {
        Main.stage.setTitle("管理员登录");
        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label id = new Label("管理员ID");
        TextField idTxt = new TextField();
        Label pwd = new Label("管理员密码");
        PasswordField pwdFld = new PasswordField();
        Button log = new Button("登录");
        Button cancel = new Button("取消");

        //点击登录按钮
        log.setOnAction(actionEvent -> {
            if (managerDao.manager_log(idTxt.getText().trim(), pwdFld.getText().trim())) {
                managerSelect();
            }else {
                ViewUtils.tips("请检查数据");
            }
        });

        //点击取消按钮，返回首页面
        cancel.setOnAction(actionEvent -> {
            Main.begin();
        });

        gridPane.add(id, 0, 0);
        gridPane.add(idTxt, 1, 0);
        gridPane.add(pwd, 0, 1);
        gridPane.add(pwdFld, 1, 1);
        gridPane.add(log, 0, 2);
        gridPane.add(cancel, 1, 2);

        Scene scene = new Scene(gridPane, 500, 400);
        Main.stage.setScene(scene);
    }



    /**
     * 管理员登录后的 选择
     */
    public static void managerSelect() {
        Main.stage.setTitle("管理员选项");
        Button manageStudent = new Button("管理学生");
        Button manageTeacher = new Button("管理教师");
        Button dataStatistical = new Button("成绩统计");
        Button back = new Button("退出系统");

        //点击管理学生按钮
        manageStudent.setOnAction(actionEvent -> {
            Main.manageStudent(false, null);
        });

        //点击管理教师按钮
        manageTeacher.setOnAction(actionEvent -> {
            manageTeacher();
        });

        //点击成绩统计
        dataStatistical.setOnAction(actionEvent -> {
            dataStatistical();
        });

        //点击退出按钮
        back.setOnAction(actionEvent -> {
            Main.begin();
        });

        //单行面板
        VBox vBox = ViewUtils.getVBox("-fx-font-size: 18;", 40, Pos.CENTER, manageStudent, manageTeacher, dataStatistical, back);
        Main.stage.setScene(new Scene(vBox, 500, 400));
    }








    /**
     * 管理员增加学生
     * @param borderPane : 边界布局
     * @param students : 学生集合
     */
    public static void addStudent_forManager(BorderPane borderPane, ArrayList<Student> students) {
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

            try {
                //学生ID不能重复；学生修改的教师必须存在
                if (isEmpty && isNum && isSuitable && isSuitable2) {
                    String sexDemo = man.isSelected() ? "男" : "女";

                    studentDao.insert_student(studentIDTxt.getText(), pwdTxt.getText(), nameTxt.getText(), sexDemo,
                            Integer.parseInt(gradeTxt.getText()), teacher_idTxt.getText());

                    ViewUtils.tips("增加成功");
                    tipStage.close();
                    //刷新表格中的数据
                    refresh_studentTable_forManager(borderPane, students);

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
     * 删除学生或者老师
     * @param borderPane : 边界布局
     * @param students : 学生集合
     * @param teachers : 教师集合
     * @param b : true删除学生，false删除老师
     */
    public static void deletePerson(BorderPane borderPane, ArrayList<Student> students, ArrayList<Teacher> teachers, boolean b) {
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

            try {
                //删除学生，先判断是否存在该学生
                if (b && studentDao.judge_StudentIDIsExist(IDTxt.getText())) {
                    studentDao.delete_student(IDTxt.getText());
                    ViewUtils.tips("成功删除该学生");
                    tipStage.close();
                    //刷新表格中的数据
                    refresh_studentTable_forManager(borderPane, students);

                }else if (!b && teacherDao.judge_teacherIDIsExist(IDTxt.getText()) && !teacherDao.judgeTeacher_havaStudents(IDTxt.getText())) {  //删除老师，先判断是否存在该老师，而且该教师没有教学生
                    teacherDao.delete_teacher(IDTxt.getText());
                    ViewUtils.tips("成功删除该老师");
                    tipStage.close();
                    //刷新表格中的数据
                    refresh_teacherTable(borderPane, teachers);
                } else if (!b && teacherDao.judgeTeacher_havaStudents(IDTxt.getText())) {
                    ViewUtils.tips("请确保该教师没有教授学生");
                } else {
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

        Scene scene = new Scene(gridPane, 360, 200);
        tipStage.setScene(scene);
        tipStage.show();
    }





    /**
     * 教师或管理员修改学生，管理员修改教师：必须先输入正确的ID号码后，才能修改
     * @param borderPane : 边界布局
     * @param students : 学生集合
     * @param teachers : 教师集合
     * @param select : true.管理员修改学生；false.管理员修改老师
     */
    public static void inputID(BorderPane borderPane, ArrayList<Student> students, ArrayList<Teacher> teachers, boolean select) {
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
            try {
                //修改学生，先查询该学生是否存在
                if (select && studentDao.judge_StudentIDIsExist(IDTxt.getText())) {
                    updateStudent(borderPane, students, IDTxt.getText());
                    tipStage.close();

                }else if(!select && teacherDao.judge_teacherIDIsExist(IDTxt.getText())) {  //修改老师，先查询该老师是否存在
                    updateTeacher(borderPane, teachers, IDTxt.getText());
                    tipStage.close();

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

        Scene scene = new Scene(gridPane, 360, 200);
        tipStage.setScene(scene);
        tipStage.show();
    }




    /**
     * 输入正确的ID号码后，可以修改学生了（学生的密码无法修改）
     * @param borderPane : 边界布局
     * @param students : 学生集合
     * @param ID : 需要修改的学生的 原来的ID号码，我们要根据原来的ID来查找这个学生
     */
    public static void updateStudent(BorderPane borderPane, ArrayList<Student> students, String ID) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();
        ViewUtils.setTopTip("修改学生", tipStage);

        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        //先查询这个学生
        studentDao.select_studentTableByID_forManager(students, ID);
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
        Label teacher_id = new Label("教师 :  " );
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
            boolean isSuitable2 = teacherDao.judge_teacherNameIsExist(teacher_idTxt.getText()) && (!studentDao.judge_StudentIDIsExist(studentIDTxt.getText()) || studentIDTxt.getText().equals(ID));

            try {
                //数据正常
                if (isData && isNum && isSuitable && isSuitable2){
                    String sexDemo = man.isSelected() ? "男" : "女";
                    int totalScore = Integer.parseInt(chineseTxt.getText()) + Integer.parseInt(mathTxt.getText()) + Integer.parseInt(englishTxt.getText()) +
                            Integer.parseInt(chemistryTxt.getText()) + Integer.parseInt(politicalTxt.getText()) + Integer.parseInt(historyTxt.getText());

                    //把数据封装到Student对象中，传入到函数
                    Student student1 = new Student(studentIDTxt.getText(), "", nameTxt.getText(), sexDemo, Integer.parseInt(gradeTxt.getText()),
                            Integer.parseInt(chineseTxt.getText()), Integer.parseInt(mathTxt.getText()), Integer.parseInt(englishTxt.getText()), Integer.parseInt(chemistryTxt.getText()),
                            Integer.parseInt(politicalTxt.getText()), Integer.parseInt(historyTxt.getText()), totalScore, studentDao.replaceName_withId(teacher_idTxt.getText()));

                    //教师姓名转化为教师ID：studentDao.replaceName_withId(teacher_idTxt.getText())
                    //修改学生信息
                    studentDao.update_studentByTeacherAndManager(ID, student1);

                    ViewUtils.tips("修改成功");
                    tipStage.close();
                    //刷新表格中的数据
                    refresh_studentTable_forManager(borderPane, students);
                }else  {
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
        ToggleGroup toggleGroup = new ToggleGroup();
        //将单选按钮添加到到ToggleGroup对象，ToggleGroup对象将管理它们，使得一次只能选择一个单选按钮。
        man.setToggleGroup(toggleGroup);
        woman.setToggleGroup(toggleGroup);

        Scene scene = new Scene(gridPane, 440, 680);
        tipStage.setScene(scene);
        tipStage.show();
    }





    /**
     * 管理员查询学生
     * @param borderPane : 边界布局
     * @param students : 学生集合
     */
    public static void queryStudent_forManager(BorderPane borderPane, ArrayList<Student> students) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();
        ViewUtils.setTopTip("可以进行ID查询、班级查询、ID和班级共同查询、姓名模糊查询", tipStage);

        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label studentID = new Label("学号 :  ");
        TextField studentIDTxt = new TextField();
        Label grade = new Label("年级 :  ");
        TextField gradeTxt = new TextField();
        Label name = new Label("姓名 :  ");
        TextField nameTxt = new TextField();
        Button query = new Button("查询");
        Button back = new Button("返回");

        gridPane.add(studentID, 0, 0);
        gridPane.add(studentIDTxt, 1, 0);
        gridPane.add(grade, 0, 1);
        gridPane.add(gradeTxt, 1, 1);
        gridPane.add(name, 0, 2);
        gridPane.add(nameTxt, 1, 2);
        gridPane.add(query, 0, 4);
        gridPane.add(back, 1, 4);

        //点击查询按钮
        query.setOnAction(actionEvent -> {
            //只通过ID查询学生
            if (!studentIDTxt.getText().equals("") && gradeTxt.getText().equals("") && nameTxt.getText().equals("")){
                //将ID符合的学生添加到students集合中
                studentDao.select_studentTableByID_forManager(students, studentIDTxt.getText());//students 集合里面的数据以及改变了

                Main.studentTable(borderPane, students);
                tipStage.close();

            } else if (studentIDTxt.getText().equals("") && !gradeTxt.getText().equals("") && CommonUtils.isStrToNum(gradeTxt.getText())  && nameTxt.getText().equals("")) {//只通过班级查询学生，grade必须可以转换为数字，不然从数据库中查找的时候会报错
                //将grade符合的学生添加到students集合中
                studentDao.select_studentTableByGrade_forManager(students, Integer.parseInt(gradeTxt.getText()) );

                Main.studentTable(borderPane, students);
                tipStage.close();

            }else if (!studentIDTxt.getText().equals("") && !gradeTxt.getText().equals("") && CommonUtils.isStrToNum(gradeTxt.getText()) && nameTxt.getText().equals("")){//同时通过ID和班级查询，grade必须可以转换为数字
                //将ID和grade同时符合的学生添加到students集合中
                studentDao.select_studentTableByIDAndGrade_forManager(students, studentIDTxt.getText(), Integer.parseInt(gradeTxt.getText()));

                Main.studentTable(borderPane, students);
                tipStage.close();

            } else if (studentIDTxt.getText().equals("") && gradeTxt.getText().equals("") && !nameTxt.getText().equals("")) {//只通过姓名模糊查询
                //只通过姓名模糊查询
                studentDao.select_studentTableByName_forManager(students, nameTxt.getText());

                Main.studentTable(borderPane, students);
                tipStage.close();

            } else { //没有输入数据
                ViewUtils.tips("请检查数据");
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
     * 管理员刷新学生表格(增加，删除，修改后调用)
     * @param borderPane ： 边界布局
     * @param students ： 学生集合
     */
    public static void refresh_studentTable_forManager(BorderPane borderPane, ArrayList<Student> students) {
        //填写学生数据
        studentDao.selectAll_studentTable_forManager(students);

        //展示学生表格
        Main.studentTable(borderPane, students);
    }









    /**
     * 管理员管理老师
     */
    public static void manageTeacher() {
        //教师集合
        ArrayList<Teacher> teachers = new ArrayList<>();

        Main.stage.setTitle("管理员管理老师");
        BorderPane borderPane = new BorderPane();

        Button add = new Button("增加");
        Button delete = new Button("删除");
        Button update = new Button("修改");
        Button query = new Button("查询");
        Button refresh = new Button("刷新");
        Button back = new Button("返回");

        //点击增加教师按钮
        add.setOnAction(actionEvent -> {
            addTeacher(borderPane, teachers);
        });

        //点击删除教师按钮
        delete.setOnAction(actionEvent -> {
            //删除学生和老师的界面相同
            //删除老师
            deletePerson(borderPane, null, teachers, false);
        });

        //点击修改教师按钮
        update.setOnAction(actionEvent -> {
            inputID(borderPane, null, teachers, false);
        });

        //点击查询教师按钮
        query.setOnAction(actionEvent -> {
            queryTeacher(borderPane);
        });

        //点击刷新
        refresh.setOnAction(actionEvent -> {
            refresh_teacherTable(borderPane, teachers);
        });

        //点击返回按钮
        back.setOnAction(actionEvent -> {
            managerSelect();
        });

        //单行面板
        HBox hBox = ViewUtils.getHBox("-fx-font-size: 18;", 40, Pos.CENTER, true, add, delete, update, query, refresh, back);
        //设置borderPane的背景颜色
        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200),null,null)));//设置hBox的背景颜色
        //返回按钮的外边距
        hBox.setMargin(back, new Insets(20, 20, 20, 60));

        //将hBox 单行面板 添加到边界布局的顶部
        borderPane.setTop(hBox);

        //从数据库中把老师数据全部填写到teachers
        teacherDao.selectAll_teacherTable(teachers);


        //展示教师表格
        teacherTable(borderPane, teachers);
        Main.stage.setScene(new Scene(borderPane,738, 500));
    }




    /**
     * 教师表格
     * @param borderPane ： 边界布局
     * @param teachers ： 教师集合
     */
    public static void teacherTable(BorderPane borderPane, ArrayList<Teacher> teachers) {
        //教师的信息表格
        TableView<Teacher> tableView = new TableView<>();

        //TableColumn类 创建列。然后使用TableView 类的 getColumns()方法将创建的列添加到表中。
        TableColumn id = new TableColumn<>("工号");
        TableColumn pwd = new TableColumn<>("密码");
        TableColumn name = new TableColumn<>("姓名");
        TableColumn sex = new TableColumn<>("性别");
        TableColumn age = new TableColumn<>("年龄");
        TableColumn graduate_school = new TableColumn<>("毕业学校");
        TableColumn salary = new TableColumn<>("工资");
        TableColumn telephone = new TableColumn<>("电话号码");

        //表格列宽宽度设置
        id.setMinWidth(90);
        name.setMinWidth(70);
        sex.setMinWidth(60);
        pwd.setMinWidth(85);
        age.setMinWidth(70);
        graduate_school.setMinWidth(120);
        salary.setMinWidth(70);
        telephone.setMinWidth(100);

        //确定数据导入的列
        //引号里面的内容一定要和teacher对象的变量名称对应，Teacher还要有相关变量的set,get方法
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        pwd.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        graduate_school.setCellValueFactory(new PropertyValueFactory<>("graduate_school"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        tableView.getColumns().addAll(id, pwd, name, sex, age, graduate_school, salary, telephone);

        //将教师数组集合添加到表格中
        tableView.getItems().addAll(teachers);

        //将表格添加到边界布局的正中心
        borderPane.setCenter(tableView);
    }




    /**
     * 管理员增加教师
     * @param borderPane ：边界布局
     * @param teachers ：教师集合
     */
    public static void addTeacher(BorderPane borderPane, ArrayList<Teacher> teachers) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();
        ViewUtils.setTopTip("增加教师", tipStage);

        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label teacherID = new Label("工号 :  ");
        TextField teacherIDTxt = new TextField();
        Label pwd = new Label("密码 :  " );
        TextField pwdTxt = new TextField();
        Label name = new Label("姓名 :  " );
        TextField nameTxt = new TextField();
        Label sex = new Label("性别 :  " );
        RadioButton man = new RadioButton("男");
        RadioButton woman = new RadioButton("女");
        Label age = new Label("年龄 :  " );
        TextField ageTxt = new TextField();
        Label graduate_school = new Label("毕业学校 :  " );
        TextField graduate_schoolTxt = new TextField();
        Label salary = new Label("工资 :  " );
        TextField salaryTxt = new TextField();
        Label telephone = new Label("电话 :  " );
        TextField telephoneTxt = new TextField();
        Button add = new Button("增加");
        Button back = new Button("返回");

        //单行面板
        HBox hBox = new HBox(man, woman);

        gridPane.add(teacherID, 0, 0);
        gridPane.add(teacherIDTxt, 1, 0);
        gridPane.add(pwd, 0, 1);
        gridPane.add(pwdTxt, 1, 1);
        gridPane.add(name, 0, 2);
        gridPane.add(nameTxt, 1, 2);
        gridPane.add(sex, 0, 3);
        gridPane.add(hBox, 1, 3);
        gridPane.add(age, 0, 4);
        gridPane.add(ageTxt, 1, 4);
        gridPane.add(graduate_school, 0, 5);
        gridPane.add(graduate_schoolTxt, 1, 5);
        gridPane.add(salary, 0, 6);
        gridPane.add(salaryTxt, 1, 6);
        gridPane.add(telephone, 0, 7);
        gridPane.add(telephoneTxt, 1, 7);
        gridPane.add(add, 0, 9);
        gridPane.add(back, 1, 9);

        //点击增加按钮
        add.setOnAction(actionEvent -> {
            //检查是否为空
            boolean isData = !teacherIDTxt.getText().equals("") && !pwdTxt.getText().equals("") && !nameTxt.getText().equals("") &&
                    (man.isSelected() || woman.isSelected()) && !graduate_schoolTxt.getText().equals("") && !salaryTxt.getText().equals("") &&
                    !telephoneTxt.getText().equals("");
            //部分数据是否可以转换为数字
            boolean isNum = CommonUtils.isStrToNum(ageTxt.getText()) && CommonUtils.isStrToNum(salaryTxt.getText());
            //数据是否在合适的范围内
            boolean isSuitable = !ageTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(ageTxt.getText()), 22, 100);
            boolean isSuitable2 = !teacherDao.judge_teacherIDIsExist(teacherIDTxt.getText());

            if (isData && isNum && isSuitable && isSuitable2){
                String sexDemo = man.isSelected() ? "男" : "女";


                //将教师的数据封装到Teacher对象中，传入函数
                Teacher teacher = new Teacher(teacherIDTxt.getText(), pwdTxt.getText(), nameTxt.getText(), sexDemo, Integer.parseInt(ageTxt.getText()),
                        graduate_schoolTxt.getText(), Integer.parseInt(salaryTxt.getText()), telephoneTxt.getText());
                //增加
                teacherDao.insert_teacher(teacher);
                //刷新教师表格
                refresh_teacherTable(borderPane, teachers);
                ViewUtils.tips("增加成功");
                tipStage.close();

            } else if (!isSuitable2) {
                ViewUtils.tips("请检查该教师的ID");
            } else {
                ViewUtils.tips("请检查数据");
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

        Scene scene = new Scene(gridPane, 380, 580);
        tipStage.setScene(scene);
        tipStage.show();
    }






    /**
     * 输入正确的ID号码后，管理员可以修改老师了
     * @param borderPane ：边界布局
     * @param teachers ：教师集合
     * @param ID : 需要修改的教师的 ID号码，我们将根据原先的ID来找到该教师
     */
    public static void updateTeacher(BorderPane borderPane, ArrayList<Teacher> teachers, String ID) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();
        ViewUtils.setTopTip("修改学生", tipStage);

        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        //先通过ID查询教师，给 TextField赋值
        teacherDao.select_teacherTableByID(teachers, ID);
        Teacher teacher = teachers.get(0);

        Label teacherID = new Label("教师ID :  ");
        TextField teacherIDTxt = new TextField(teacher.getId());
        Label pwd = new Label("密码 :  ");
        TextField pwdTxt = new TextField(teacher.getPwd());
        Label name = new Label("姓名 :  " );
        TextField nameTxt = new TextField(teacher.getName());
        Label sex = new Label("性别 :  " );
        RadioButton man = new RadioButton("男");
        RadioButton woman = new RadioButton("女");
        if (teacher.getSex().equals("男")) {
            man.setSelected(true);
        } else if (teacher.getSex().equals("女")) {
            woman.setSelected(true);
        }
        Label age = new Label("年龄 :  " );
        TextField ageTxt = new TextField(String.valueOf(teacher.getAge()));
        Label graduate_school = new Label("毕业学校 :  " );
        TextField graduate_schoolTxt = new TextField(teacher.getGraduate_school());
        Label salary = new Label("工资 :  " );
        TextField salaryTxt = new TextField(String.valueOf(teacher.getSalary()));
        Label telephone = new Label("电话 :  " );
        TextField telephoneTxt = new TextField(String.valueOf(teacher.getTelephone()));
        Button update = new Button("修改");
        Button back = new Button("返回");

        //单行面板
        HBox hBox = new HBox(man, woman);

        gridPane.add(teacherID, 0, 0);
        gridPane.add(teacherIDTxt, 1, 0);
        gridPane.add(pwd, 0, 1);
        gridPane.add(pwdTxt, 1, 1);
        gridPane.add(name, 0, 2);
        gridPane.add(nameTxt, 1, 2);
        gridPane.add(sex, 0, 3);
        gridPane.add(hBox, 1, 3);
        gridPane.add(age, 0, 4);
        gridPane.add(ageTxt, 1, 4);
        gridPane.add(graduate_school, 0, 5);
        gridPane.add(graduate_schoolTxt, 1, 5);
        gridPane.add(salary, 0, 6);
        gridPane.add(salaryTxt, 1, 6);
        gridPane.add(telephone, 0, 7);
        gridPane.add(telephoneTxt, 1, 7);
        gridPane.add(update, 0, 9);
        gridPane.add(back, 1, 9);

        //点击修改按钮
        update.setOnAction(actionEvent -> {
            //检查数据是否为空
            boolean isData = !teacherIDTxt.getText().equals("") && !pwdTxt.getText().equals("")  && !nameTxt.getText().equals("") &&
                    (man.isSelected() || woman.isSelected()) && !graduate_schoolTxt.getText().equals("") &&
                    !salaryTxt.getText().equals("") && !telephoneTxt.getText().equals("");
            //部分数据是否可以转换为数字
            boolean isNum = CommonUtils.isStrToNum(ageTxt.getText()) && CommonUtils.isStrToNum(salaryTxt.getText());
            boolean isSuitable = !ageTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(ageTxt.getText()), 22, 100);
            //教师ID不能有重复的
            boolean isSuitable2 = !teacherDao.judge_teacherIDIsExist(teacherIDTxt.getText()) || teacherIDTxt.getText().equals(ID);

            if (isData && isNum && isSuitable && isSuitable2){
                String sexDemo = man.isSelected() ? "男" : "女";

                //将教师属性封装到Teacher对象中，传入参数
                Teacher teacher1 = new Teacher(teacherIDTxt.getText(), pwdTxt.getText(), nameTxt.getText(), sexDemo, Integer.parseInt(ageTxt.getText()),
                        graduate_schoolTxt.getText(), Integer.parseInt(salaryTxt.getText()), telephoneTxt.getText());

                //修改教师信息
                teacherDao.update_teacher(ID, teacher1);
                ViewUtils.tips("修改成功");
                tipStage.close();
                //刷新教师表格
                refresh_teacherTable(borderPane, teachers);
            } else if (!isSuitable2) {
                ViewUtils.tips("请检查教师ID是否正确");
            } else {
                ViewUtils.tips("请检查数据");
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

        Scene scene = new Scene(gridPane, 440, 680);
        tipStage.setScene(scene);
        tipStage.show();
    }



    /**
     * 管理员查询教师
     * @param borderPane : 边界布局
     */
    public static void queryTeacher(BorderPane borderPane) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();

        ViewUtils.setTopTip("可以进行ID查询、姓名模糊查询", tipStage);
        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label teacherID = new Label("工号 :  ");
        TextField teacherIDTxt = new TextField();
        Label name = new Label("姓名");
        TextField nameTxt = new TextField();
        Button query = new Button("查询");
        Button back = new Button("返回");

        gridPane.add(teacherID, 0, 0);
        gridPane.add(teacherIDTxt, 1, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(nameTxt, 1, 1);
        gridPane.add(query, 0, 3);
        gridPane.add(back, 1, 3);

        //点击查询按钮
        query.setOnAction(actionEvent -> {
            ArrayList<Teacher> teachers = new ArrayList<>();

            //只查询ID
            if (!teacherIDTxt.getText().equals("") && nameTxt.getText().equals("")){
                //将ID符合的教师添加到teachers集合中
                teacherDao.select_teacherTableByID(teachers, teacherIDTxt.getText());

                teacherTable(borderPane,teachers);
                tipStage.close();

            } else if (teacherIDTxt.getText().equals("") && !nameTxt.getText().equals("")) {
                //只通过姓名模糊查询
                teacherDao.select_teacherTableByName(teachers, nameTxt.getText());

                teacherTable(borderPane,teachers);
                tipStage.close();

            } else { //没有输入数据
                ViewUtils.tips("请检查数据");
            }
        });

        //点击返回按钮，关闭提示框
        back.setOnAction(actionEvent -> {
            tipStage.close();
        });

        Scene scene = new Scene(gridPane, 340, 400);
        tipStage.setScene(scene);
        tipStage.show();
    }



    /**
     * 刷新教师表格
     * @param borderPane : 边界布局
     * @param teachers : 教师集合
     */
    public static void refresh_teacherTable(BorderPane borderPane, ArrayList<Teacher> teachers) {
        //填写全部的教师数据
        teacherDao.selectAll_teacherTable(teachers);

        //展示教师表格
        teacherTable(borderPane, teachers);
    }






    /**
     * 成绩柱状图
     */
    public static void dataStatistical() {
        Main.stage.setTitle("成绩统计");
        Button back = new Button("返回");

        //点击返回
        back.setOnAction(actionEvent -> {
            managerSelect();
        });

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("成绩分布");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("人数");
        BarChart barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("年级1");

        XYChart.Series dataSeries2 = null;
        XYChart.Series dataSeries3 = null;
        XYChart.Series dataSeries4 = null;

        try {
            dataSeries1.getData().add(new XYChart.Data("0<=x<200", managerDao.histogram(1, 0, 200)));
            dataSeries1.getData().add(new XYChart.Data("200<=x<350" , managerDao.histogram(1, 200, 350)));
            dataSeries1.getData().add(new XYChart.Data("350<=x<500" , managerDao.histogram(1, 350, 500)));
            dataSeries1.getData().add(new XYChart.Data("500<=x<=600" , managerDao.histogram(1, 500, 601)));

            dataSeries2 = new XYChart.Series();
            dataSeries2.setName("年级2");
            dataSeries2.getData().add(new XYChart.Data("0<=x<200", managerDao.histogram(2, 0, 200)));
            dataSeries2.getData().add(new XYChart.Data("200<=x<350" , managerDao.histogram(2, 200, 350)));
            dataSeries2.getData().add(new XYChart.Data("350<=x<500" , managerDao.histogram(2, 350, 500)));
            dataSeries2.getData().add(new XYChart.Data("500<=x<=600" , managerDao.histogram(2, 500, 601)));


            dataSeries3 = new XYChart.Series();
            dataSeries3.setName("年级3");
            dataSeries3.getData().add(new XYChart.Data("0<=x<200", managerDao.histogram(3, 0, 200)));
            dataSeries3.getData().add(new XYChart.Data("200<=x<350" , managerDao.histogram(3, 200, 350)));
            dataSeries3.getData().add(new XYChart.Data("350<=x<500" , managerDao.histogram(3, 350, 500)));
            dataSeries3.getData().add(new XYChart.Data("500<=x<=600" , managerDao.histogram(3, 500, 601)));

            dataSeries4 = new XYChart.Series();
            dataSeries4.setName("年级4");
            dataSeries4.getData().add(new XYChart.Data("0<=x<200", managerDao.histogram(4, 0, 200)));
            dataSeries4.getData().add(new XYChart.Data("200<=x<350" , managerDao.histogram(4, 200, 350)));
            dataSeries4.getData().add(new XYChart.Data("350<=x<500" , managerDao.histogram(4, 350, 500)));
            dataSeries4.getData().add(new XYChart.Data("500<=x<=600" , managerDao.histogram(4, 500, 601)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        barChart.getData().add(dataSeries1);
        barChart.getData().add(dataSeries2);
        barChart.getData().add(dataSeries3);
        barChart.getData().add(dataSeries4);

        //单列面板
        VBox vBox = new VBox(barChart, back);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMargin(back, new Insets(20, 0, 0, 0));

        Scene scene = new Scene(vBox, 500, 540);
        Main.stage.setScene(scene);
    }


}
