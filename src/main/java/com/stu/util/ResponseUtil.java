package com.stu.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {
	 /**
     * 工具方法，返回JSON数据
     * @param data
     * @param response
     * @throws IOException
     */
    public static void returnJson(Object data, HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setHeader("cache-control", "no-cache");
        response.getWriter().write(JSON.toJSONString(data));
    }
}
