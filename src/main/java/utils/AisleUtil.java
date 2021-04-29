package utils;

import javafx.event.EventHandler;
import javafx.scene.chart.Chart;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin.javascript.navig.Anchor;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于对通道进行初始化的工具
 * @author Zhenxi Chen
 * @date 2021/4/29
 */
public class AisleUtil {
    private static final double MAIN_CONTAINER_HEIGHT = 970.0;
    private static final double MAIN_CONTAINER_WIDTH = 1720.0;

    private static Logger logger = LoggerFactory.getLogger(AisleUtil.class);

    /**
     * 用于生成初始显示的通道
     * @param mainContainer 用于容纳通道的AnchorPane
     * @param number 要显示的通道数
     * @return 返回存储了ScrollPane的列表
     */
    public static List<ScrollPane> createAisles(AnchorPane mainContainer,
                                                int number) {
        List<ScrollPane> containers = new ArrayList<ScrollPane>();
        double aisleHeight = MAIN_CONTAINER_HEIGHT / number;
        for (int i = 0; i < number; i++) {
            // 新建一个ScrollPane并加入到AnchorPane当中
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefWidth(MAIN_CONTAINER_WIDTH);      // 宽度同容器大小
            scrollPane.setPrefHeight(aisleHeight);      // 高度根据通道数计算获得
            mainContainer.getChildren().add(scrollPane);
            scrollPane.setVisible(true);
            // 将新建的ScrollPane存储到List当中
            containers.add(scrollPane);
        }
        return containers;
    }

    public static AnchorPane putChartIntoAnchorPane(Chart chart) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(chart.getPrefHeight());
        anchorPane.setPrefWidth(chart.getPrefWidth());
        anchorPane.getChildren().add(chart);
        return anchorPane;
    }
}
