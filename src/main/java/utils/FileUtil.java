package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于存储文件操作方法的工具类
 * @author Zhenxi Chen
 * @date 2021/4/18
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 以16位数字对文件进行读取
     * @param file 需要进行读取的文件
     * @return 返回值，以List存储
     */
    public static List<Integer> readFile(File file) {
        List<Integer> resultList = new ArrayList<Integer>();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("File {} not found", file.getName());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("Failed to close FileInputStream");
            }
        }
        return resultList;
    }
}
