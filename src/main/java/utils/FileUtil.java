package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 用于存储文件操作方法的工具类
 * @author Zhenxi Chen
 * @date 2021/4/18
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final int BUFFER_SIZE = 2048;        // 默认缓冲区的大小

    private static Properties properties;

    private static final String propertyFilePath = "chart.properties";  // 配置文件路径

    private static boolean failedToLoadProperty = false;        // 记录是否无法读取配置文件



    /**
     * 通过静态代码块，在类加载时读取配置文件
     */
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(propertyFilePath));
        } catch (Exception e) {
            logger.error("Failed to load property file.");
            failedToLoadProperty = true;
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
                short[] shortArray = readBufferAsShort(buffer, len);
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
     */
    public static short[] readBufferAsShort(byte[] buffer, int len) {
        if (len < 0) {
            return new short[0];
        }
        int shortBufferLength = (len % 2 == 1) ? len / 2 + 1: len / 2;
        short[] shortBuffer = new short[shortBufferLength];
        ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shortBuffer);
        return shortBuffer;
    }
}
