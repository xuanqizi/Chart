package controllers;

import constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AisleUtil;
import utils.ChartUtil;
import utils.FileUtil;

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
    private Menu fileMenu;     // 菜单的"文件"按钮

    @FXML
    private MenuItem openMenuItem;     // 菜单的"文件-打开"按钮

    @FXML
    private TreeView<String> leftTreeView;      // 左侧的树状菜单列表

    @FXML
    private AnchorPane chartAnchorPane;         // 用于容纳图表的AnchorPane

    private Stage stage;       // 用于存储Stage
    private int chartNumber;    // 画面当中显示的图表数量
    List<ScrollPane> containers;       // 用于存储各通道的ScrollPane

    private static final String fileChooserTitle = "请选择数据文件";      // 文件选择器的标题
    private static final String AISLE_PROPERTY_KEY = "chart.aisle";     // 通道数量的配置key
    private static final int DEFAULT_CHART_NUMBER = 2;      // 默认显示的图表数量
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * 初始化Controller
     */
    public void initialize(URL location, ResourceBundle resources) {
        Properties properties = new Properties();
        try {
            InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(Constants.PROPERTY_FILE_PATH);
            properties.load(in);
            chartNumber = Integer.parseInt(properties.getProperty(AISLE_PROPERTY_KEY));
        } catch (IOException e) {
            chartNumber = DEFAULT_CHART_NUMBER;
            logger.error("Failed to load property file. {}", e.getMessage());
        } catch (NumberFormatException e) {
            chartNumber = DEFAULT_CHART_NUMBER;
            logger.error("Failed to load aisle number because NumberFormatError. {}", e.getMessage());
        }
        initAisle();
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
        Chart chart = ChartUtil.createChart(list, type);
        // AnchorPane anchorPane = AisleUtil.putChartIntoAnchorPane(chart);
        chartAnchorPane.getChildren().add(chart);
    }

    /**
     * 初始化加载通道
     */
    private void initAisle() {

    }
}
