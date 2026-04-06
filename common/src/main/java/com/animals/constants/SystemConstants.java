package com.animals.constants;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SystemConstants {

    //从文件导入
    public static String getChatPrompt()
    {
        try {
            return new String(
                    SystemConstants.class.getClassLoader()
                            .getResourceAsStream("template/chatPrompt.txt")
                            .readAllBytes(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            log.error("读取 prompt.txt 失败", e);
            throw new RuntimeException(e);
        }
    }
    public static String getGamePrompt()
    {
        try {
            return new String(
                    SystemConstants.class.getClassLoader()
                            .getResourceAsStream("template/gamePrompt.txt")
                            .readAllBytes(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            log.error("读取 prompt.txt 失败", e);
            throw new RuntimeException(e);
        }
    }
    //客服提示词函数
    public static String getServicePrompt()
    {
        try {
            return new String(
                    SystemConstants.class.getClassLoader()
                            .getResourceAsStream("template/customerServicePrompt.txt")
                            .readAllBytes(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            log.error("读取 prompt.txt 失败", e);
            throw new RuntimeException(e);
        }
    }
}
