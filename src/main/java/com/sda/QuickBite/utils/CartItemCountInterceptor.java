package com.sda.QuickBite.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CartItemCountInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Retrieve the cart item count from the session attribute
        HttpSession session = request.getSession();
        Integer cartItemCount = (Integer) session.getAttribute("cartItemCount");

        // Add the cart item count to the request attribute
        request.setAttribute("cartItemCount", cartItemCount);

        return true;
    }
}


