package com.student_manageSystem.utils;

import com.student_manageSystem.Main;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;



/**
 * 视图工具类
 */
public class ViewUtils {

    /**
     * 设置GridPane布局的一些属性
     * @param gridPane : 网格布局
     */
    public static void setGridPane(GridPane gridPane) {
        //设置每个组件之间的间距
        gridPane.setHgap(10);//设置节点间的水平间距，也就是列与列的间距（节点就是组件）
        gridPane.setVgap(10);//节点间的垂直间距，也就是行与行的间距
        //设置组件在中间分布
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-font-size: 18;");
    }


    /**
     * 设置VBox单列面板布局的一些属性
     * @param style : 字体类型
     * @param spacing : 间距
     * @param value : 设置组件的位置
     * @param children : 组件
     * @return : VBox
     */
    public static VBox getVBox(String style, int spacing, Pos value, Node... children) {
        VBox vBox = new VBox(children);
        //字体类型
        vBox.setStyle(style);
        //间距
        vBox.setSpacing(spacing);
        //设置组件的位置
        vBox.setAlignment(value);
        return vBox;
    }


    /**
     * 设置HBox单行面板布局的一些属性
     * @param style : 字体类型
     * @param spacing : 间距
     * @param value : 设置组件的位置
     * @param isSetBackground : true：说明需要设置背景颜色，是表格；false：不需要设置背景颜色
     * @param children : 组件
     * @return : HBox
     */
    public static HBox getHBox(String style, int spacing, Pos value, boolean isSetBackground, Node... children) {
        HBox hBox = new HBox(children);
        //字体类型
        hBox.setStyle(style);
        //间距
        hBox.setSpacing(spacing);
        //设置组件的位置
        hBox.setAlignment(value);

        //设置hBox的背景颜色
        if (isSetBackground) {
            hBox.setBackground(new Background(new BackgroundFill(Color.rgb(119, 168, 157), null, null)));
        }
        return hBox;
    }




    /**
     * 设置顶层窗口的一些属性
     * @param str : 标题的内容
     * @param tipStage : 需要添加属性的窗口
     */
    public static void setTopTip(String str, Stage tipStage) {
        tipStage.setResizable(false);//不能调整大小
        tipStage.setTitle(str);//设置标题
        //只能操作当前的提示框
        tipStage.initOwner(Main.stage);
        tipStage.initModality(Modality.WINDOW_MODAL);
        //设置该提示框永远在最上层
        tipStage.setAlwaysOnTop(true);
    }



    /**
     * 提示框
     * @param content : 提示的信息
     */
    public static void tips(String content) {
        //创建提示框窗口对象
        Stage tipStage = new Stage();
        //调用顶层窗口函数设置一些必要的属性
        setTopTip(content, tipStage);

        Label label = new Label(content);
        Button back = new Button("返回");

        //点击返回按钮，关闭提示框
        back.setOnAction(actionEvent -> {
            tipStage.close();
        });

        //单列面板
        VBox vBox = new VBox(label, back);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 180, 130);
        tipStage.setScene(scene);
        tipStage.show();
    }




}