package com.apocalypse.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

@Slf4j
public class ResponseUtil {

    public static void sendError(HttpServletResponse response, int sc, String message) {
        try {
            response.sendError(sc, message);
        } catch (IOException e) {
            log.error("发送错误失败", e);
        }
    }

    public static void sendError(HttpServletResponse response) {
        try {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            response.setCharacterEncoding(Charset.forName("UTF-8").name());
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            PrintWriter writer = response.getWriter();
//            ResponseMessage<Object> responseMessage = new ResponseMessage<>();
//            responseMessage.setSuccess(false)
//                    .setCode(ResultCode.SYSTEM_BUSY.getCode())
//                    .setMsg(ResultCode.SYSTEM_BUSY.getI18NContent());
//            writer.print(JSONObject.toJSON(responseMessage));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            log.error("发送错误失败", e);
        }
    }
}
