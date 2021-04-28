package utils;

import constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 用于存储文件操作方法的工具类
 * @author Zhenxi Chen
 * @date 2021/4/18
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final int BUFFER_SIZE = 2048;        // 默认缓冲区的大小

    private static Properties properties;

    private static boolean failedToLoadProperty = false;        // 记录是否无法读取配置文件

    private static ByteOrder order;     // 设置顺序为大端或小端

    private static final String ENDIAN_PROPERTY_KEY = "chart.endian";     // 配置文件中设置大端或小端的key

    // 配置文件当中大端和小端对应的值
    private static final String BIG_ENDIAN_PROPERTY_VALUE = "BIG_ENDIAN";
    private static final String LITTLE_ENDIAN_PROPERTY_VALUE = "LITTLE_ENDIAN";

    /**
     * 通过静态代码块，在类加载时读取配置文件
     */
    static {
        String endian = null;
        try {
            InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(Constants.PROPERTY_FILE_PATH);
            properties = new Properties();
            properties.load(in);
            endian = properties.getProperty(ENDIAN_PROPERTY_KEY);
        } catch (Exception e) {
            logger.error("Failed to load property file. {}", e.getMessage());
            failedToLoadProperty = true;
        }
        if (endian != null && endian.equals(BIG_ENDIAN_PROPERTY_VALUE)) {
            order = ByteOrder.BIG_ENDIAN;
        } else if (endian != null && endian.equals(LITTLE_ENDIAN_PROPERTY_VALUE)) {
            order = ByteOrder.LITTLE_ENDIAN;
        } else {
            failedToLoadProperty = true;
            order = ByteOrder.BIG_ENDIAN;        // 默认为大端
        }
    }

    /**
     * 以16位数字对文件进行读取
     * @param file 需要进行读取的文件
     * @return 返回值，以List存储
     */
    public static List<Short> readFile(File file) {
        List<Short> resultList = new ArrayList<Short>();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while (len >= 0) {
                len = inputStream.read(buffer);     // 如果数据读取结束，read函数会返回-1
                if (len == -1) { break; }
                short[] shortArray = readBufferAsShort(buffer, len, order);
                for (short num: shortArray) {
                    resultList.add(num);
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("File {} not found", fileNotFoundException);
        } catch (IOException e) {
            logger.error("Failed to read file {}", file.getName());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("Failed to close FileInputStream");
            }
        }
        return resultList;
    }

    /**
     * 将buffer中的内容以Short形式读取到数组当中
     * @param buffer 存储二进制数的buffer
     * @param len buffer当中存储的byte的数量
     * @param order ByteOrder类，标识顺序为大端或小端
     * @return
     */
    public static short[] readBufferAsShort(byte[] buffer, int len, ByteOrder order) {
        if (len < 0) {
            return new short[0];
        }
        int shortBufferLength = (len % 2 == 1) ? len / 2 + 1: len / 2;
        short[] shortBuffer = new short[shortBufferLength];
        ByteBuffer.wrap(buffer).order(order).asShortBuffer().get(shortBuffer);
        return shortBuffer;
    }
}
