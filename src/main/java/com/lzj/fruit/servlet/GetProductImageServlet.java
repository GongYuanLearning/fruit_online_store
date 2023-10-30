package com.lzj.fruit.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;

@WebServlet("/product/image")
public class GetProductImageServlet extends HttpServlet {
    private String imageDir;
    private static final String UPLOAD_DIRECTORY = "admin/images";
    @Override
    public void init(ServletConfig config) throws ServletException {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader()
                    .getResourceAsStream("/application.properties"));
            imageDir = properties.getProperty("product.image.dir");
            if(imageDir.isEmpty()) {
                imageDir = UPLOAD_DIRECTORY;
            }
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("path");
        resp.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(path, "UTF-8"));
        resp.setHeader("Content-Type", "application/octet-stream");
        //resp.setHeader("Content-Type", "image/jpeg");

        try(InputStream in = new FileInputStream(imageDir + File.separator +path)) {
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = in.read(buffer)) > 0) {
                resp.getOutputStream().write(buffer, 0, len);
            }
        }
    }
}

