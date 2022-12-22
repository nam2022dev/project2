package com.codede.project2.coltroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;
import java.sql.SQLException;

@ControllerAdvice(basePackages = "com.codede.project2.coltroller")
public class ExceptionController {
    //log
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({Exception.class})
    public String noResult(NoResultException ex) {
        logger.info("sql ex : ", ex);
        return "404.html"; // view
    }

    @ExceptionHandler({Exception.class})
    public String exception(SQLException ex) {
        logger.error("sql ex : ", ex);
        return "404.html";
    }

}
