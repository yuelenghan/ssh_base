package com.ghtn.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理异常信息和返回处理成功的信息
 * User: Administrator
 * Date: 13-11-28
 * Time: 上午8:58
 *
 * @author lh
 */
public class BaseController {

    private static Log log = LogFactory.getLog(BaseController.class);

    /**
     * 异常信息处理的方法
     *
     * @param e 异常
     * @return 处理结果,{success:false, msg:错误信息, caused by:引起错误的原因}
     */
    @ExceptionHandler
    @ResponseBody
    public Map<String, Object> handleException(Exception e) {
        log.error("------------发生异常！----------------");
        log.error("Caused by : " + e.getCause());
        log.error("Message : " + e.getMessage());
        log.error("Exception : ", e);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("success", false);
        returnMap.put("msg", "操作失败!");
        returnMap.put("caused by", e.getCause());
        return returnMap;
    }

    /**
     * 返回处理成功的结果
     *
     * @return 操作结果,{success:true, msg:成功信息}
     */
    public Map<String, Object> operationSuccess() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("success", true);
        returnMap.put("msg", "操作成功!");
        return returnMap;
    }

}
