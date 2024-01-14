package com.charlesedu.saleapi.web.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.charlesedu.saleapi.services.SellerService;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterSaleAuth extends OncePerRequestFilter {

    @Autowired
    private SellerService sellerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/sales/")) {
            if ("OPTIONS".equals(request.getMethod())) {
                filterChain.doFilter(request, response);

                return;
            }

            var authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Basic")) {
                response.sendError(401);
                return;
            }

            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecoded);

            String[] credentials = authString.split(":");

            if (credentials.length == 2) {
                String sellerName = credentials[0];
                String password = credentials[1];

                var seller = sellerService.findByUsername(sellerName);

                if (seller == null) {
                    response.sendError(401);
                } else {
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), seller.getPassword());

                    if (passwordVerify.verified) {
                        request.setAttribute("sellerId", seller.getId());

                        filterChain.doFilter(request, response);
                    } else {
                        response.sendError(401);
                    }
                }
            } else {
                response.sendError(401);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
