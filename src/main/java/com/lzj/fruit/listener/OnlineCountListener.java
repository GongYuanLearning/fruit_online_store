package com.lzj.fruit.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 定义一个监听器，统计网站在线人数，即统计session
 */
public class OnlineCountListener implements HttpSessionListener {
    //创建session监听，一旦创建session就触发一次这个事件
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println(se.getSession().getId());

        ServletContext servletContext = se.getSession().getServletContext();
        Integer onlineCount = (Integer) servletContext.getAttribute("onlineCount");
        if (null == onlineCount) {
            onlineCount = 1;
        } else {
            onlineCount++;
            System.out.println("创建session后，onlineCount的值：" + onlineCount);
        }
        servletContext.setAttribute("onlineCount", onlineCount);
    }

    //销毁session监听，一旦销毁session就会触发一次这个事件
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        Integer onlineCount = (Integer) servletContext.getAttribute("onlineCount");
        if (1 == onlineCount) {
            onlineCount = 0;
        } else {
            onlineCount--;
            System.out.println("销毁session后，onlineCount的值：" + onlineCount);
        }
        servletContext.setAttribute("onlineCount", onlineCount);
    }
}

