package ru.javawebinar.topjava.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Valk on 19.10.15.
 */
public class Interceptor extends HandlerInterceptorAdapter {

    @Autowired
    LocaleChangeInterceptor localeChangeInterceptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequest newRequest = request;
        String factLanguage = request.getParameter("language");
        if (!("ru".equals(factLanguage)) &&
                !("en".equals(factLanguage))) {
            factLanguage = "ru";
            newRequest = new HttpServletRequestWrapper(request){
              /*org.springframework.web.servlet.i18n.LocaleChangeInterceptor использует
              String newLocale = request.getParameter(this.paramName);
              поэтому подменяем getParameter*/
                @Override
                public String getParameter(String name) {
                    if ("language".equals(name)){
                        return "ru";
                    }
                    return super.getParameter(name);
                }
            };
        }
        localeChangeInterceptor.preHandle(newRequest, response, null);
        newRequest.setAttribute("language",factLanguage);
        return super.preHandle(newRequest, response, handler);
    }
}
