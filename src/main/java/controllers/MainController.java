package controllers;

import constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.gillius.jfxutils.chart.ChartZoomManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AisleUtil;
import utils.ChartUtil;
import utils.FileUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 主界面（main.fxml）对应的controller
 * 负责对主界面进行控制
 */
public class MainController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private TreeView<String> leftTreeView;      // 左侧的树状菜单列表

    @FXML
    private ScrollPane outSideScrollPane;       // 最外层的ScrollPane

    @FXML
    private AnchorPane chartAnchorPane;         // 用于容纳图表的AnchorPane

    @FXML
    private AnchorPane chartContainer;          // 用于存放图表的AnchorPane

    @FXML
    private ScrollPane chartScrollPane;         // 存放作为图表容器的AnchorPane的ScrollPane

    private Stage stage;       // 用于存储Stage

    private static final String fileChooserTitle = "请选择数据文件";      // 文件选择器的标题
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * 初始化Controller
     */
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsDevice graphicsDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = graphicsDevice.getDisplayMode().getWidth() - 200.0;
        outSideScrollPane.setPrefWidth(width);
        chartAnchorPane.setPrefWidth(width);
        chartScrollPane.setPrefWidth(width);
        chartContainer.setPrefWidth(width);
        outSideScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        outSideScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**
     * 对于点击文件-打开菜单做出响应
     * @param event 存储事件内容
     */
    @FXML
    public void onOpenMenuClicked(ActionEvent event) {
        File file = chooseFile();
        List<Short> res = FileUtil.readFile(file);
        addChart(res, ChartUtil.LINE_CHART);
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
}
