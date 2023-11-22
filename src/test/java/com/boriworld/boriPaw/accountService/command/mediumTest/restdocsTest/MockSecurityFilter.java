package com.boriworld.boriPaw.accountService.command.mediumTest.restdocsTest;

import jakarta.servlet.*;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;

public class MockSecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    public void getFilters(MockHttpServletRequest mockHttpServletRequest) {

    }
}
