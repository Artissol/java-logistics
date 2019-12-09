package com.logistics.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BasicAuthenticationConfig extends BasicAuthenticationEntryPoint {

	@Override
    public void commence(final HttpServletRequest request, 
    		final HttpServletResponse response, 
    		final AuthenticationException authException) throws IOException, ServletException {
    	
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
    	response.setHeader("Access-Control-Allow-Origin", "*");
    	response.setHeader("Access-Control-Allow-Credentials", "true");
    	response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
    	response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
        
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 : " + authException.getMessage());
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Logistics");
        super.afterPropertiesSet();
    }


}
