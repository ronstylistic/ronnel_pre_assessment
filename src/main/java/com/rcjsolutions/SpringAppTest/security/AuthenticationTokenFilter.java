package com.rcjsolutions.SpringAppTest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcjsolutions.SpringAppTest.model.response.RestError;
import com.rcjsolutions.SpringAppTest.model.response.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httResponse = (HttpServletResponse) response;
        try {
            String authToken = null;
            String userName = null;

            // If Regular CRM User
            String header = httpRequest.getHeader("Authorization");
            if (header != null) {
                if (header.startsWith("Bearer ")) {
                    authToken = header.substring(7);
                    userName = tokenUtils.getUserNameFromToken(authToken);
                }
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if (this.tokenUtils.validateToken(authToken, userDetails) && userDetails.isEnabled()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);
        }catch (Exception ex){
            RestResponse res = new RestResponse(false);
            res.setError(new RestError(401,"Access Denied ["+ex.getMessage()+"]"));
            httResponse.setContentType("application/json");
            httResponse.getWriter().append(objectMapper.writeValueAsString(res));
            httResponse.setStatus(200);
        }
    }
}
