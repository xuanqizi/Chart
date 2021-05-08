import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

/**
 * 主类，继承Application类，用于启动应用程序
 * @author Zhenxi Chen
 * @date 2021/04/17
 */
public class MainApplication extends Application {

    private static final String appTitle = "Chart";     // 应用程序的标题
    private static int appWidth = 1920;       // 应用程序宽度
    private static int appHeight = 1000;      // 应用程序高度
    private static final String fxmlFileName = "main.fxml";     // 主程序的fxml文件名
    private static final int SYSTEM_HEIGHT = 80;    // 为系统任务栏和边框预留的高度

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        initSize();
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFileName));
            Scene scene = new Scene(root, appWidth, appHeight);
            primaryStage.setTitle(appTitle);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            throw e;
        }
    }

    private void initSize() {
        GraphicsDevice graphicsDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        appWidth = graphicsDevice.getDisplayMode().getWidth();
        appHeight = graphicsDevice.getDisplayMode().getHeight() - SYSTEM_HEIGHT;
    }
}
