import javafx.application.Application;

public class Main {
    private static final String ALLOW_HIDPI_KEY = "prism.allowhidpi";   // 配置是否允许高DPI缩放的系统配置key

    public static void main(String[] args) {
        System.setProperty(ALLOW_HIDPI_KEY, "false");
        Application.launch(MainApplication.class, args);
    }
}
