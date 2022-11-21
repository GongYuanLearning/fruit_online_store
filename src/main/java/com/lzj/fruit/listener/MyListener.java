package com.lzj.fruit.listener;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebListener
public class MyListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public MyListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext对象被创建了");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext对象被销毁了");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session对象被创建了");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session对象被销毁了");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        System.out.println(String.format("Session属性添加：%s=%s", sbe.getName(), sbe.getValue().toString()));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        System.out.println(String.format("Session属性添加：%s", sbe.getName()));
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {

    }
}
