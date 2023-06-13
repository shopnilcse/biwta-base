package com.synesisit.biwta.base.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synesisit.biwta.base.exception.ErrorResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());

        ErrorResponse response = new ErrorResponse("Access Denied, User has no permission.", details);

        //response.setMessage("Access Denied");
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
        //httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied, User has no permission/Unauthorized User");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}