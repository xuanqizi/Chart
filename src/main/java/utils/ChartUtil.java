package utils;

import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.gillius.jfxutils.chart.ChartZoomManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import java.util.List;

/**
 * 通过从文件中读取出的数，或处理得到的数来生成图表的工具类
 * @author Zhenxi Chen
 * @date 2021/4/28
 */
public class ChartUtil {
    public static final int LINE_CHART = 0;
    public static final int AREA_CHART = 1;


    /**
     * 将从文件读取出的16位数字转换为图表
     * @param numbers 16位数字列表
     * @param type 要生成的图表类型
     * @return 返回生成的图表
     */
    public static XYChart<Number, Number> createChart(List<Short> numbers, int type) {
        final NumberAxis xAxis = new NumberAxis();      // 用于存储x轴的数字
        final NumberAxis yAxis = new NumberAxis();      // 用于存储y轴的数字
        int currentX = 0;      // 用于进行自增，获取当前x的值
        XYChart chart;
        switch (type) {
            case LINE_CHART: {
                chart = new LineChart<Number, Number>(xAxis, yAxis);
                break;
            }
            case AREA_CHART: {
                chart = new AreaChart<Number, Number>(xAxis, yAxis);
                break;
            }
            default: {
                throw new IllegalArgumentException("Type should not be " + type);
            }
        }
        XYChart.Series series = new XYChart.Series<Number, Number>();
        for (short num: numbers) {
            series.getData().add(new XYChart.Data<Number, Number>(++currentX, num));
        }
        chart.getData().add(series);
        chart.setPrefWidth(numbers.size() * 10);
        return chart;
    }

    /**
     * 将从文件读取出的16位数字转换为图表
     * 并通过JFXUtils库为其附加缩放功能
     * @param numbers 16位数字列表
     * @param type 要生成的图表类型
     * @param container 作为图表容器的Pane
     * @return 要返回的图表
     */
    public static XYChart<Number, Number> createChartWithZooming(List<Short> numbers, int type, Pane container) {
        XYChart<Number, Number> chart = createChart(numbers, type);     // 调用方法创建图表
        container.setPrefWidth(chart.getPrefWidth());
        container.getChildren().add(chart);
        JFXChartUtil.setupZooming(chart);
        ChartZoomManager chartZoomManager = new ChartZoomManager(container,
                new Rectangle(0, 0), chart);
        chartZoomManager.setZoomAnimated(true);     // 设置缩放动画
        chartZoomManager.setMouseWheelZoomAllowed(true);    // 允许通过鼠标滚轮进行缩放
        return chart;
    }
}
