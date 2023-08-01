package com.project.sioscms.common.utils.common.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HtmlParseUtil {

    public static String escapeHtmlTag(String inputStr){
        if(inputStr != null && !inputStr.isEmpty()){
            String outputStr = null;
            outputStr = inputStr.replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
            log.info("HtmlTagEscape String ::: " + outputStr);
            return outputStr;
        }
        return null;
    }
}
