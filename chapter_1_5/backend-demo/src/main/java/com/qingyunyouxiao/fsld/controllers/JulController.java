package com.qingyunyouxiao.fsld.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

@RestController
public class JulController {
    private static final Logger logger = Logger.getLogger(JulController.class.getName());

    // JUL 日志示例
    @RequestMapping("/log/jul")
    public String logMessage() {
        logger.info("This is a log message from JUL");
        logger.warning("This is a warning message from JUL");
        logger.severe("This is an error message from JUL");
        return "Log message sent to JUL";
    }
    
}
