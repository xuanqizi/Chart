package utils;

import javafx.scene.chart.*;

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
     * @param numbers 16位
     * @param type 要生成的图表类型
     * @return
     */
    public static Chart createChart(List<Short> numbers, int type) {
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
        return chart;
    }
}
