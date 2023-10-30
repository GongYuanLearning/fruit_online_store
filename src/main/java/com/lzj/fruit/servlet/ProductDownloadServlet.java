package com.lzj.fruit.servlet;

import com.lzj.fruit.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;

// http://localhost:8080/fruit/product/download?name=
@WebServlet("/product/download")
public class ProductDownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("name");

        // 如果文件名含有中文，必须调用URLEncoder.encode进行编码
        resp.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(fileName, "utf-8"));
        resp.setHeader("Content-Type", "application/octet-stream");

        String fileDir = getFileDir(); // 获取文件所在目录
        fileName = fileDir + fileName;

//        BufferedReader br = new BufferedReader(new FileReader(fileName));
//        String line = null;
//        while((line = br.readLine()) != null) {
//            resp.getOutputStream().println(line);
//        }
        try (InputStream in = new FileInputStream(fileName);) {
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = in.read(buffer)) > 0) {
                resp.getOutputStream().write(buffer, 0, len);
            }
        }
    }

    public String getFileDir() throws IOException {
        //return getServletContext().getInitParameter("fileDir");
        InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(in);
        String fileDir = properties.getProperty("file.dir");

        if(fileDir.lastIndexOf("/") != fileDir.length() - 1) {
            fileDir += "/";
        }
        return fileDir;
    }
}
