package com.lzj.fruit.servlet;

import com.lzj.fruit.dto.ProductDto;
import com.lzj.fruit.service.ProductService;
import com.lzj.fruit.service.impl.ProductServiceImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/product/upload")
@MultipartConfig()
public class ProductUploadServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part productsFile = req.getPart("productsFile");
        //addProducts(productsFile);

        CSVFormat csvFormat = CSVFormat.Builder.create().setHeader("name", "imagePath", "description", "detail", "price", "unit", "category").setSkipHeaderRecord(true).setTrim(true).build();
        List<ProductDto> products = new ArrayList<>();
        try (CSVParser parser = new CSVParser(new InputStreamReader(productsFile.getInputStream(), "UTF-8"), csvFormat)) {
            for (CSVRecord record : parser) {
                ProductDto product = new ProductDto();
                product.setName(record.get("name"));
                product.setImagePath(record.get("imagePath"));
                product.setDescription(record.get("description"));
                product.setDetail(record.get("detail"));
                product.setUnit(record.get("unit"));
                product.setPrice(new BigDecimal(record.get("price")));
                product.setCategoryId(Long.parseLong(record.get("category")));
                products.add(product);
            }
        }

        // 保存product
        try {
            productService.addProducts(products);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void addProducts(Part productsFile) throws IOException, ServletException {
        BufferedReader br = new BufferedReader(new InputStreamReader(productsFile.getInputStream(), "UTF-8"));
        String line = null;
        boolean firstLine = true;
        String[] headers = null;
        List<ProductDto> products = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (firstLine) {
                headers = line.split(",");
                firstLine = false;
            } else {
                String[] cols = line.split(",");

                ProductDto product = new ProductDto();
                product.setName(cols[0]);
                product.setImagePath(cols[1]);
                product.setDescription(cols[2]);
                product.setDetail(cols[3]);
                product.setUnit(cols[4]);
                product.setPrice(new BigDecimal(cols[5]));
                product.setCategoryId(Long.parseLong(cols[6]));
                products.add(product);

                products.add(product);
            }
        }

        try {
            productService.addProducts(products);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

