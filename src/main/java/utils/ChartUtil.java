package utils;

import javafx.scene.chart.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.gillius.jfxutils.chart.ChartZoomManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.gillius.jfxutils.chart.XYChartInfo;

import java.util.List;

/**
 * 通过从文件中读取出的数，或处理得到的数来生成图表的工具类
 * @author Zhenxi Chen
 * @date 2021/4/30
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
        int currentX = 0;      // 用于进行自增，获取当前x的值
        XYChart<Number, Number> chart = createNumberChart(type);
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
        addDoubleClickResetListener(chart);     // 添加双击监听器
        return chart;
    }

    /**
     * 将处理后得到的Integer列表转换为图表
     * @param numbers Integer列表
     * @param type 图表类型
     * @return 生成的图表
     */
    public static XYChart<Number, Number> createIntegerChart(List<Integer> numbers, int type) {
        int currentX = 0;      // 用于进行自增，获取当前x的值
        XYChart<Number, Number> chart = createNumberChart(type);
        XYChart.Series series = new XYChart.Series<Number, Number>();
        for (int num: numbers) {
            series.getData().add(new XYChart.Data<Number, Number>(++currentX, num));
        }
        chart.getData().add(series);
        chart.setPrefWidth(numbers.size() * 10);
        return chart;
    }

    /**
     * 将处理后得到的Integer列表转换为图表
     * @param numbers Integer列表
     * @param type 图表类型
     * @param container 作为图表容器的Pane
     * @return 生成的图表
     */
    public static XYChart<Number, Number> createIntegerChartWithZooming(List<Integer> numbers,
                                                                        int type, Pane container) {
        XYChart<Number, Number> chart = createIntegerChart(numbers, type);     // 调用方法创建图表
        container.setPrefWidth(chart.getPrefWidth());
        container.getChildren().add(chart);
        JFXChartUtil.setupZooming(chart);
        ChartZoomManager chartZoomManager = new ChartZoomManager(container,
                new Rectangle(0, 0), chart);
        chartZoomManager.setZoomAnimated(true);     // 设置缩放动画
        chartZoomManager.setMouseWheelZoomAllowed(true);    // 允许通过鼠标滚轮进行缩放
        addDoubleClickResetListener(chart);
        return chart;
    }

    /**
     * 内部方法，用于建立数字图表
     * @param type
     * @return
     */
    private static XYChart<Number, Number> createNumberChart(int type) {
        final NumberAxis xAxis = new NumberAxis();      // 用于存储x轴的数字
        final NumberAxis yAxis = new NumberAxis();      // 用于存储y轴的数字
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
        return chart;
    }

    /**
     * 为图表添加监听双击的listener
     * 并在监听到双击之后重置图表缩放
     * @param chart 需添加listener的图表
     */
    private static void addDoubleClickResetListener(XYChart<?, ?> chart) {
        chart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                resetRange(chart, event);
                event.consume();
            }
        });
    }

    /**
     * 通过双击重置缩放范围
     * 若用户双击x轴，则只重置x轴；若用户双击y轴，则只重置y轴
     * 若用户双击其他区域，则重置整个图表
     * @param chart 需要进行重置的图表
     */
    private static void resetRange(XYChart<?, ?> chart, MouseEvent event) {
        XYChartInfo info = new XYChartInfo(chart);
        if (!info.getXAxisArea().contains(event.getX(), event.getY())) {    // 若用户未点击x轴
            chart.getYAxis().setAutoRanging(true);      // 重置y轴
        }
        if (!info.getYAxisArea().contains(event.getX(), event.getY())) {    // 若用户未点击y轴
            chart.getXAxis().setAutoRanging(true);      // 重置x轴
        }
    }
}
