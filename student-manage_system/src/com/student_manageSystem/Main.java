package com.student_manageSystem;

import com.student_manageSystem.dao.impl.StudentImpl;
import com.student_manageSystem.dao.impl.TeacherImpl;
import com.student_manageSystem.dao.StudentDao;
import com.student_manageSystem.dao.TeacherDao;
import com.student_manageSystem.pojo.Student;
import com.student_manageSystem.utils.CommonUtils;
import com.student_manageSystem.utils.MD5Utils;
import com.student_manageSystem.utils.ViewUtils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;



/**
 * @projectName : student_manage_system
 * @className : Main
 * @description : 系统与用户交互界面
 * @author : lizhuang
 * @createDate : 10.19
 */
public class Main extends Application {
    //新添加：
    //1.限制每个教师只能查到自己的学生
    //2.传入学生，教师的参数时，由于参数较多，要封装到一个学生，教师对象中
    //3.输入学号，姓名时，在首尾输入空格无影响
    //4.对学生的密码进行MD5加密(不可逆加密)


    //更新：
    //密码框


    public static Stage stage;

    //多态调用
    static StudentDao studentDao = new StudentImpl();
    static TeacherDao teacherDao = new TeacherImpl();






    /**
     * 主入口
     */
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        stage.setResizable(false);

        begin();
        stage.show();
    }


    /**
     * 首页面
     */
    public static void begin() {
        GridPane gridPane = new GridPane();
        stage.setTitle("学生管理系统首页");
        //设置gridPane 的一些属性
        ViewUtils.setGridPane(gridPane);

        Button manageLog = new Button("管理员登录");
        Button teacherLog = new Button("教师登录");
        Button studentLog = new Button("学生登录");
        Button studentSign = new Button("学生注册");

        //点击管理员登录按钮
        manageLog.setOnAction(actionEvent -> {
            Manager_operate.managerLog();
        });

        //点击老师登录按钮
        teacherLog.setOnAction(actionEvent -> {
            teacher_log();
        });

        //点击学生登录按钮
        studentLog.setOnAction(actionEvent -> {
            student_log();
        });

        //点击学生注册按钮
        studentSign.setOnAction(actionEvent -> {
            student_sign();
        });

        VBox vBox = ViewUtils.getVBox("-fx-font-size: 18;", 20, Pos.CENTER, manageLog, teacherLog, studentLog, studentSign);
        Scene scene = new Scene(vBox, 500, 400);
        stage.setScene(scene);
    }






    /**
     * 学生的一些操作
     * ------------------------------------------------------------------------------------------------------------------------
     * 学生注册
     */
    public static void student_sign() {
        stage.setTitle("学生注册");
        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);//设置GridPane的一些属性

        Label id = new Label("学生学号");
        TextField idTxt = new TextField();
        Label pwd = new Label("学生密码");
        PasswordField pwdFld = new PasswordField();
        Label name = new Label("学生姓名");
        TextField nameTxt = new TextField();
        Label sex = new Label("性别");
        RadioButton man = new RadioButton("男");
        RadioButton woman = new RadioButton("女");
        Label grade = new Label("学生年级");
        TextField gradeTxt = new TextField();
        Label teacher_id = new Label("教师姓名");
        TextField teacher_idTxt = new TextField();
        Button sign = new Button("注册");
        Button back = new Button("返回");

        //点击注册按钮
        sign.setOnAction(actionEvent -> {
            //数据要符合规范，grade,classSchool成绩必须是数字的形式，并且1<=grade<=4，0<=成绩<=100，ID必须没有重复
            //数据是否为空
            boolean isEmpty = !idTxt.getText().trim().equals("") && !pwdFld.getText().trim().equals("") && !nameTxt.getText().trim().equals("")
                    && (man.isSelected() || woman.isSelected()) && !teacher_idTxt.getText().trim().equals("");

            //部分数据的范围是否合适
            boolean isSuitable =  !gradeTxt.getText().trim().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(gradeTxt.getText().trim()), 1, 4);
            boolean isSuitable2 = teacherDao.judge_teacherNameIsExist(teacher_idTxt.getText().trim()) && !studentDao.judge_StudentIDIsExist(idTxt.getText().trim());

            //要注册的学生ID不能存在，学生的教师姓名必须是存在的
            if (isEmpty && isSuitable && isSuitable2) {
                String sexDemo = man.isSelected() ? "男" : "女";

                studentDao.insert_student(idTxt.getText().trim(), pwdFld.getText().trim(), nameTxt.getText().trim(), sexDemo, Integer.parseInt(gradeTxt.getText().trim()), teacher_idTxt.getText().trim());
                ViewUtils.tips("注册成功");
                begin();//注册成功后返回到主页面

            } else if (!isSuitable2) {
                ViewUtils.tips("请检查教师姓名或学生ID");
            } else {
                ViewUtils.tips("请检查数据");
            }
        });

        //点击取消按钮，返回首页面
        back.setOnAction(actionEvent -> {
            begin();
        });

        //将男，女单选添加到单行面板中
        HBox hBox = new HBox(man, woman);

        gridPane.add(id, 0, 0);
        gridPane.add(idTxt, 1, 0);
        gridPane.add(pwd, 0, 1);
        gridPane.add(pwdFld, 1, 1);
        gridPane.add(name, 0, 2);
        gridPane.add(nameTxt, 1, 2);
        gridPane.add(sex, 0, 3);
        gridPane.add(hBox, 1, 3);
        gridPane.add(grade, 0, 4);
        gridPane.add(gradeTxt, 1, 4);
        gridPane.add(teacher_id, 0, 5);
        gridPane.add(teacher_idTxt, 1, 5);
        gridPane.add(sign, 0, 7);
        gridPane.add(back, 1, 7);

        //设置性别只能选择一个
        //将单选按钮添加到到ToggleGroup对象，它将管理它们，使得一次只能选择一个单选按钮。
        ToggleGroup toggleGroup = new ToggleGroup();
        man.setToggleGroup(toggleGroup);
        woman.setToggleGroup(toggleGroup);

        Scene scene = new Scene(gridPane, 500, 400);
        stage.setScene(scene);
    }



    /**
     * 学生登录页面
     */
    public static void student_log() {
        stage.setTitle("学生登录");
        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label idLabel = new Label("学生学号");
        TextField idTxt = new TextField();
        Label pwdLabel = new Label("学生密码");
        PasswordField pwdFld = new PasswordField();
        Button log = new Button("登录");
        Button back = new Button("返回");

        //点击登录
        log.setOnAction(actionEvent -> {
            try {
                if (studentDao.student_log(idTxt.getText().trim(), pwdFld.getText().trim())) {
                    //进入学生选项(trim()去掉字符串两端的空格)
                    student_select(idTxt.getText().trim(), pwdFld.getText().trim());
                }else {
                    ViewUtils.tips("请检查数据");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //点击取消
        back.setOnAction(actionEvent -> {
            begin();
        });


        //安排每个组件的位置
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idTxt, 1, 0);
        gridPane.add(pwdLabel, 0, 1);
        gridPane.add(pwdFld, 1, 1);
        gridPane.add(log, 0, 3);
        gridPane.add(back, 1, 3);

        Scene scene = new Scene(gridPane, 500, 400);
        stage.setScene(scene);
    }




    /**
     * 学生登录后选择选项
     * @param id ： 传入ID用来查看学生分数 或 修改学生信息
     * @param pwd : 传入ID用来查看学生分数 或 修改学生信息
     */
    public static void student_select(String id, String pwd) {
        stage.setTitle("学生选择");
        Button checkScore = new Button("查看分数");
        Button update = new Button("修改信息");
        Button back = new Button("返回");

        //点击查看分数
        checkScore.setOnAction(actionEvent -> {
            student_checkScore(id, pwd);
        });

        //点击修改信息
        update.setOnAction(actionEvent -> {
            student_modify(id, pwd);
        });

        //点击取消
        back.setOnAction(actionEvent -> {
            begin();
        });

        VBox vBox = ViewUtils.getVBox("-fx-font-size: 18;", 20, Pos.CENTER, checkScore, update, back);
        Scene scene = new Scene(vBox, 500, 400);
        stage.setScene(scene);
    }



    /**
     * 学生查看分数页面
     * @param id : 传入学生ID
     * @param pwd : 点击取消时，将ID和p 传入到student_select方法中
     */
    public static void student_checkScore(String id, String pwd) {
        stage.setTitle("学生查询成绩");

        String encryption_pwd = MD5Utils.md5Encryption(pwd);
        Student student =  studentDao.select_studentByOneself(id, encryption_pwd);

        Label id2 = new Label("学号 :  " + student.getId());
        Label p2 = new Label("密码 :  " + pwd);
        Label name = new Label("姓名 :  " + student.getName());
        Label sex = new Label("性别 :  " + student.getSex());
        Label grade = new Label("年级 :  " + student.getGrade());
        Label chinese = new Label("语文 :  " + student.getChinese());
        Label math = new Label("数学 :  " + student.getMath());
        Label english = new Label("英语 :  " + student.getEnglish());
        Label chemistry = new Label("化学 :  " + student.getChemistry());
        Label political = new Label("政治 :  " + student.getPolitical());
        Label history = new Label("历史 :  " + student.getHistory());
        Label total_score = new Label("总分 :  " + student.getTotal_score());
        Label teacher_id = new Label("教师姓名 :  " + student.getTeacher_id());
        Button back = new Button("返回");

        //点击返回按钮，返回首页面
        back.setOnAction(actionEvent -> {
            student_select(id, pwd);
        });

        //单列面板
        VBox vBox = ViewUtils.getVBox("-fx-font-size: 18;", 10, Pos.CENTER,id2, p2, name, sex, grade,  chinese, math, english, chemistry, political, history, total_score, teacher_id, back);
        Scene scene = new Scene(vBox, 300, 530);
        stage.setScene(scene);
    }



    /**
     * 学生修改自己的信息
     * @param id : 通过ID来搜索到我们要修改的学生
     * @param p : 点击取消时，将ID和p 传入到student_select方法中
     */
    public static void student_modify(String id, String p) {
        stage.setTitle("学生修改信息");
        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        //修改自己数据的时候把数据填充一下
        String encryption_pwd = MD5Utils.md5Encryption(p);
        Student student = studentDao.select_studentByOneself(id, encryption_pwd);

        Label pwd = new Label("学生密码");
        TextField pwdTxt = new TextField(p);
        Label name = new Label("学生姓名");
        TextField nameTxt = new TextField(student.getName());
        Label sex = new Label("性别");
        RadioButton man = new RadioButton("男");
        RadioButton woman = new RadioButton("女");
        if (student.getSex().equals("男")) {
            man.setSelected(true);
        } else if (student.getSex().equals("女")) {
            woman.setSelected(true);
        }
        Label grade = new Label("学生年级");
        TextField gradeTxt = new TextField(String.valueOf(student.getGrade()));
        Label teacher_id = new Label("教师姓名");
        TextField teacher_idTxt = new TextField(student.getTeacher_id());
        Button modify = new Button("修改");
        Button back = new Button("返回");


        //点击修改
        modify.setOnAction(actionEvent -> {
            //数据要符合规范，grade,classSchool,成绩必须是数字的形式，并且1<=grade<=4，0<=成绩<=100，ID必须没有重复；教师必须存在
            boolean isEmpty = !pwdTxt.getText().equals("")  && !nameTxt.getText().equals("") && (man.isSelected() ||
                    woman.isSelected()) && !teacher_idTxt.getText().equals("");
            boolean isNum = CommonUtils.isStrToNum(gradeTxt.getText());
            boolean isSuitable = !gradeTxt.getText().equals("") && CommonUtils.isDataSuitable(Integer.parseInt(gradeTxt.getText()), 1, 4);
            boolean isExists = teacherDao.judge_teacherNameIsExist(teacher_idTxt.getText());

            if (isEmpty && isNum && isSuitable && isExists) {
                String sexDemo = man.isSelected() ? "男" : "女";

                //教师姓名转化为教师ID：studentDao.replaceName_withId(teacher_idTxt.getText())
                studentDao.update_studentByOneself(id, pwdTxt.getText(), nameTxt.getText(), sexDemo, Integer.parseInt(gradeTxt.getText()), studentDao.replaceName_withId(teacher_idTxt.getText()));

                ViewUtils.tips("修改成功");
                student_select(id, pwdTxt.getText());
            }else if (!isExists) {
                ViewUtils.tips("请检查该教师是否存在");
            }else {
                ViewUtils.tips("请检查数据");
            }

        });

        //点击取消按钮，返回首页面
        back.setOnAction(actionEvent -> {
            student_select(id, p);
        });

        //将男，女单选添加到单行面板中
        HBox hBox = new HBox(man, woman);

        gridPane.add(pwd, 0, 0);
        gridPane.add(pwdTxt, 1, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(nameTxt, 1, 1);
        gridPane.add(sex, 0, 2);
        gridPane.add(hBox, 1, 2);
        gridPane.add(grade, 0, 3);
        gridPane.add(gradeTxt, 1, 3);
        gridPane.add(teacher_id, 0, 4);
        gridPane.add(teacher_idTxt, 1, 4);
        gridPane.add(modify, 0, 6);
        gridPane.add(back, 1, 6);

        //设置性别只能选择一个
        ToggleGroup toggleGroup = new ToggleGroup();//将单选按钮添加到到ToggleGroup对象，它将管理它们，使得一次只能选择一个单选按钮。
        man.setToggleGroup(toggleGroup);
        woman.setToggleGroup(toggleGroup);

        Scene scene = new Scene(gridPane, 500, 400);
        stage.setScene(scene);
    }







    /**
     * 教师的相关操作
     * ------------------------------------------------------------------------------------------------------------------------
     * 教师登录
     */
    public static void teacher_log() {
        stage.setTitle("教师登录");
        GridPane gridPane = new GridPane();
        ViewUtils.setGridPane(gridPane);

        Label teacherID = new Label("教师工号");
        TextField teacherIDTxt = new TextField();
        Label pwd = new Label("教师密码");
        PasswordField pwdFld = new PasswordField();
        Button log = new Button("登录");
        Button cancel = new Button("取消");

        //点击登录按钮
        log.setOnAction(actionEvent -> {
            if (teacherDao.teacher_log(teacherIDTxt.getText().trim(), pwdFld.getText().trim())) {
                manageStudent(true, teacherIDTxt.getText().trim());
            }else {
                ViewUtils.tips("请检查数据");
            }
        });

        //点击取消按钮，返回首页面
        cancel.setOnAction(actionEvent -> {
            begin();
        });

        gridPane.add(teacherID, 0, 0);
        gridPane.add(teacherIDTxt, 1, 0);
        gridPane.add(pwd, 0, 1);
        gridPane.add(pwdFld, 1, 1);
        gridPane.add(log, 0, 2);
        gridPane.add(cancel, 1, 2);

        Scene scene = new Scene(gridPane, 500, 400);
        stage.setScene(scene);
    }






    /**
     * 教师，管理员管理学生
     * @param select : true.老师管理学生；false.管理员管理学生（因为老师和管理员的返回界面不同，所以要分两种情况）
     */
    public static void manageStudent(boolean select, String id_t) {
        //学生集合
        ArrayList<Student> students = new ArrayList<>();

        stage.setTitle("管理学生");
        BorderPane borderPane = new BorderPane();

        Button add = new Button("增加");
        Button delete = new Button("删除");
        Button update = new Button("修改");
        Button query = new Button("查询");
        Button refresh = new Button("刷新");
        Button back = new Button("返回");

        //点击增加学生
        add.setOnAction(actionEvent -> {
            if (select) {
                Teacher_operate.addStudent(borderPane, students, id_t);
            }else {
                Manager_operate.addStudent_forManager(borderPane, students);
            }
        });

        //点击删除学生
        delete.setOnAction(actionEvent -> {
            //删除学生和管理员删除老师的界面相同
            //删除学生,true:删除学生，false:删除老师
            if (select) {
                Teacher_operate.deletePerson(borderPane, students, id_t);
            }else {
                Manager_operate.deletePerson(borderPane, students, null, true);
            }
        });

        //点击修改学生
        update.setOnAction(actionEvent -> {
            //先输入ID号码判断是否存在该学生，如果存在就修改学生
            //true.老师或管理员修改学生；false.管理员修改老师
            if (select) {
                Teacher_operate.inputID(borderPane, students, id_t);
            }else {
                Manager_operate. inputID(borderPane, students, null, true);
            }

        });

        //点击查询学生
        query.setOnAction(actionEvent -> {
            if (select) {
                Teacher_operate.queryStudent_byTeacher(borderPane, students, id_t);
            }else {
                Manager_operate.queryStudent_forManager(borderPane, students);
            }
        });

        //点击刷新
        refresh.setOnAction(actionEvent -> {
            if (select) {
                Teacher_operate.refresh_studentTable_forTeacher(borderPane, students, id_t);
            }else {
                Manager_operate.refresh_studentTable_forManager(borderPane, students);
            }

        });

        //点击返回
        back.setOnAction(actionEvent -> {
            //教师退出该页面
            if (select) {
                begin();
            }else  {
                //管理员退出该页面
                Manager_operate.managerSelect();
            }
        });

        //单行面板
        HBox hBox = ViewUtils.getHBox("-fx-font-size: 18;", 40, Pos.CENTER, true, add, delete, update, query, refresh, back);
        //设置borderPane的背景颜色
        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200),null,null)));//设置hBox的背景颜色
        //设置返回按钮的外边距
        hBox.setMargin(back, new Insets(20, 20, 20, 60));

        //将hBox 单行面板 添加到边界布局的顶部
        borderPane.setTop(hBox);

        //从数据库中把学生数据全部填写到students集合中
        if (select) {//教师查询学生
            studentDao.selectAll_studentTable_forTeacher(students, id_t);
        }else {
            studentDao.selectAll_studentTable_forManager(students);
        }

        //展示学生表格
        studentTable(borderPane, students);
        stage.setScene(new Scene(borderPane,1042, 500));
    }







    /**
     * 学生表格(通用：适合教师和管理员对学生的管理)
     * @param borderPane : 边界布局
     * @param students : 学生集合
     */
    public static void studentTable(BorderPane borderPane, ArrayList<Student> students) {
        //学生的信息表格
        TableView<Student> tableView = new TableView<>();

        //TableColumn类 创建列。然后使用TableView 类的 getColumns()方法将创建的列添加到表中。
        TableColumn id = new TableColumn<>("学号");
        TableColumn pwd = new TableColumn<>("密码");
        TableColumn name = new TableColumn<>("姓名");
        TableColumn sex = new TableColumn<>("性别");
        TableColumn grade = new TableColumn<>("年级");
        TableColumn chinese = new TableColumn<>("语文");
        TableColumn math = new TableColumn<>("数学");
        TableColumn english = new TableColumn<>("英语");
        TableColumn chemistry = new TableColumn<>("化学");
        TableColumn political = new TableColumn<>("政治");
        TableColumn history = new TableColumn<>("历史");
        TableColumn total_score = new TableColumn<>("总分");
        TableColumn teacher_id = new TableColumn<>("教师");

        //表格列宽宽度设置
        id.setMinWidth(70);
        pwd.setMinWidth(70);
        name.setMinWidth(60);
        sex.setMinWidth(60);
        grade.setMinWidth(60);
        chinese.setMinWidth(60);
        math.setMinWidth(60);
        english.setMinWidth(60);
        chemistry.setMinWidth(60);
        political.setMinWidth(60);
        history.setMinWidth(60);
        total_score.setMinWidth(60);
        teacher_id.setMinWidth(70);

        //确定数据导入的列
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        pwd.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        chinese.setCellValueFactory(new PropertyValueFactory<>("chinese"));
        math.setCellValueFactory(new PropertyValueFactory<>("math"));
        english.setCellValueFactory(new PropertyValueFactory<>("english"));
        chemistry.setCellValueFactory(new PropertyValueFactory<>("chemistry"));
        political.setCellValueFactory(new PropertyValueFactory<>("political"));
        history.setCellValueFactory(new PropertyValueFactory<>("history"));
        total_score.setCellValueFactory(new PropertyValueFactory<>("total_score"));
        teacher_id.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));

        //将列添加到表格中
        tableView.getColumns().addAll(id, pwd, name, sex, grade, chinese, math, english, chemistry, political, history, total_score, teacher_id);

        //将学生数组集合添加到表格中
        tableView.getItems().addAll(students);

        //将表格添加到边界布局的正中心
        borderPane.setCenter(tableView);
    }



}



