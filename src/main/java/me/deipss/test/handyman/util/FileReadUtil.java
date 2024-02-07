package me.deipss.test.handyman.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Slf4j
public class FileReadUtil {

    /**
     * 使用类的加载器路径来获取文件内容
     */
    public static String getResourceFile(String path) {
        String configString = "";
        URL url = FileReadUtil.class.getClassLoader().getResource(path);
        assert url != null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            configString = result.toString();
        } catch (IOException e) {
            log.error("读取源文件异常，", e);
        }
        return configString;
    }

    /**
     * 使用当前执行线程来获取文件内容
     */
    public static String readResourceFile(String path) {
        String configString = "";
        try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            configString = result.toString();
        } catch (IOException e) {
            log.error("读取源文件异常，", e);
        }
        return configString;
    }
}
