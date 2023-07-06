package com.hitex.halago.filter;

import com.google.gson.Gson;

import com.hitex.halago.model.logTransaction.BaseRequestDataLog;
import com.hitex.halago.model.logTransaction.BaseResponseDataLog;
import com.hitex.halago.model.logTransaction.LogTransactionBase;
import com.hitex.halago.utils.LogTransactionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class FilterBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterBase.class);

    public void doFilterAndLog(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain,
                               CrudRepository crudRepository, LogTransactionBase logTransactionBase, Integer isWriteLogTransaction) {
        try {
            if (isWriteLogTransaction == null || isWriteLogTransaction != 0) {
                Date startTime = new Date();
                HttpServletRequest request = (HttpServletRequest) servletRequest;
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
                MultiReadHttpServletResponse wrappedResponse = new MultiReadHttpServletResponse(response);
                request = wrappedRequest;
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                filterChain.doFilter(request, wrappedResponse);
                saveLog(wrappedRequest, wrappedResponse, startTime, logTransactionBase, crudRepository);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
    }

    public void destroy() {
        // TODO: 7/4/18
    }


    private void saveLog(MultiReadHttpServletRequest request, MultiReadHttpServletResponse response, Date startTime, LogTransactionBase logTransactionBase, CrudRepository crudRepository) {
        try {
            logTransactionBase.setStatus(1L);
            logTransactionBase.setUri(request.getRequestURL().toString());
            logTransactionBase.setStartTime(startTime);
            String wsRequest = IOUtils.toString(request.getReader());
            logTransactionBase.setRequest(wsRequest);
            Gson gson = new Gson();
            BaseRequestDataLog baseRequestDataLog = gson.fromJson(wsRequest, BaseRequestDataLog.class);
            LogTransactionUtils logTransactionUtils = new LogTransactionUtils();
            logTransactionUtils.copyProperties(baseRequestDataLog, logTransactionBase);
            byte[] copy = response.getCopy();
            String wsResponse = new String(copy, response.getCharacterEncoding());
            logTransactionBase.setResponse(wsResponse);
            BaseResponseDataLog baseResponseDataLog = gson.fromJson(wsResponse, BaseResponseDataLog.class);
            logTransactionUtils.copyProperties(baseResponseDataLog, logTransactionBase);
            logTransactionBase.setIp(request.getRemoteHost());
            logTransactionBase.setEndTime(new Date());
            logTransactionBase.setTimeRun(logTransactionBase.getEndTime().getTime() - logTransactionBase.getStartTime().getTime());
            crudRepository.save(logTransactionBase);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }


}