package ru.javawebinar.topjava.web;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Valk on 14.09.15.
 */
public class ServletUtil {
    public static void returnResult(HttpServletRequest request, HttpServletResponse response, JsonSerializer serializer, Object object) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        if (serializer != null) {
            SimpleModule module = new SimpleModule();
            Class clazz = (Class) ((ParameterizedType) serializer.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            module.addSerializer(clazz, serializer);
            objectMapper.registerModule(module);
        }
        objectMapper.writeValue(printWriter, object);
    }
}
