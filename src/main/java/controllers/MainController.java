package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import process.DifferentialProcess;
import process.IntegralProcess;
import process.Process;
import utils.ChartUtil;
import utils.FileUtil;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 主界面（main.fxml）对应的controller
 * 负责对主界面进行控制
 * @author Zhenxi Chen
 * @date 2021/4/30
 */
public class MainController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private TreeView<String> treeView;          // 左侧功能栏

    @FXML
    private ScrollPane outSideScrollPane;       // 最外层的ScrollPane

    @FXML
    private AnchorPane chartAnchorPane;         // 用于容纳图表的AnchorPane

    @FXML
    private AnchorPane chartContainer;          // 用于存放图表的AnchorPane

    @FXML
    private ScrollPane chartScrollPane;         // 存放作为图表容器的AnchorPane的ScrollPane

    @FXML
    private ScrollPane calculatedChartScrollBar;    // 用于存放操作后的图表的AnchorPane的ScrollPane

    @FXML
    private AnchorPane calculatedChartContainer;    // 用于存放操作后的图表的AnchorPane

    private Stage stage;       // 用于存储Stage
    private List<Short> data;   // 用于存储读取的文件数据

    private static final String fileChooserTitle = "请选择数据文件";      // 文件选择器的标题
    private static final String DIFFERENTIAL_VALUE = "微分";       // 微分功能键的值
    private static final String INTEGRAL_VALUE = "积分";       // 积分功能键的值
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * 初始化Controller
     */
    public void initialize(URL location, ResourceBundle resources) {
        // 动态设置各个部分的宽度
        initWidth();
        // 设置左侧功能栏的监听
        initTreeView();
    }

    /**
     * 对于点击文件-打开菜单做出响应
     * @param event 存储事件内容
     */
    @FXML
    public void onOpenMenuClicked(ActionEvent event) {
        File file = chooseFile();
        data = FileUtil.readFile(file);
        addChart(data, ChartUtil.LINE_CHART);
    }

    /**
     * 打开文件选择器并选择文件
     * @return 返回所选文件的File对象
     */
    private File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooserTitle);
        if (stage == null) {
            Scene scene = root.getScene();
            stage = (Stage) scene.getWindow();
        }
        return fileChooser.showOpenDialog(stage);
    }

    /**
     * 向通道当中添加图表
     */
    private void addChart(List<Short> list, int type) {
        chartContainer.getChildren().clear();
        ChartUtil.createChartWithZooming(list, type, chartContainer);
    }

    /**
     * 根据屏幕大小动态设置宽度
     */
    private void initWidth() {
        GraphicsDevice graphicsDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = graphicsDevice.getDisplayMode().getWidth() - 200.0;
        outSideScrollPane.setPrefWidth(width);
        chartAnchorPane.setPrefWidth(width);
        chartScrollPane.setPrefWidth(width);
        chartContainer.setPrefWidth(width);
        outSideScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        outSideScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        calculatedChartScrollBar.setPrefWidth(width);
        calculatedChartContainer.setPrefWidth(width);
    }

    /**
     * 初始化左侧功能栏的点击响应事件
     */
    private void initTreeView() {
        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
                String text = item.getValue();
                switch (text) {
                    case DIFFERENTIAL_VALUE: {
                        handleProcess(new DifferentialProcess());
                        break;
                    }
                    case INTEGRAL_VALUE: {
                        handleProcess(new IntegralProcess());
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        });
    }

    /**
     * 处理数据操作请求
     */
    private void handleProcess(Process process) {
        if (data == null) {
            return;
        }
        List<Integer> chartData = process.process(data);
        calculatedChartContainer.getChildren().clear();
        ChartUtil.createIntegerChartWithZooming(chartData, ChartUtil.LINE_CHART, calculatedChartContainer);
    }
}
