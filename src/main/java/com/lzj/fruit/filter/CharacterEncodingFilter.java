package com.lzj.fruit.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.util.Objects;

/**
 * 设置ServletRequest，ServletResponse的字符编码。
 *
 */
@WebFilter(filterName = "CharacterEncodingFilter",
        initParams = @WebInitParam(name="encoding", value= "UTF-8"))
public class CharacterEncodingFilter implements Filter {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private String encoding = DEFAULT_ENCODING;

    public void init(FilterConfig config) throws ServletException {
        String encoding = config.getInitParameter("encoding");
        if(Objects.nonNull(encoding) && !encoding.isEmpty()) {
            this.encoding = encoding;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding); // 一定要在getWriter之前调用
        chain.doFilter(request, response); // 继续调用过滤链或者处理程序
    }
}
