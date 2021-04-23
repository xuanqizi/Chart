package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtil;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 主界面（main.fxml）对应的controller
 */
public class MainController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Menu fileMenu;     // 菜单的"文件"按钮

    @FXML
    private MenuItem openMenuItem;     // 菜单的"文件-打开"按钮

    private Stage stage;       // 用于存储Stage

    private static final String fileChooserTitle = "请选择数据文件";      // 文件选择器的标题
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * 初始化Controller
     */
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * 对于点击文件-打开菜单做出响应
     * @param event 存储事件内容
     */
    @FXML
    public void onOpenMenuClicked(ActionEvent event) {
        File file = chooseFile();
        List<Short> res = FileUtil.readFile(file);
        for (short shortNum: res) {
            System.out.println(shortNum);
        }
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
}
