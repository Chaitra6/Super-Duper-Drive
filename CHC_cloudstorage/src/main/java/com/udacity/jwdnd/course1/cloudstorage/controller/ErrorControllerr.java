package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ErrorControllerr implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = "/error")
    public String errorHandler(HttpServletRequest req){

        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

//        if (status == null){
//            return "error";
//        }
        if (status != null){
            Integer stsCode = Integer.valueOf(status.toString());

            if(stsCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){

               // opens error-505.html
                return "error-500";
            }
            else if (stsCode == HttpStatus.NOT_FOUND.value()){
               // opens error-404 page
                return "error-404";
            }
        }

            return "error";
    }


    @Override
    public String getErrorPath() {
        return PATH;
    }
}
