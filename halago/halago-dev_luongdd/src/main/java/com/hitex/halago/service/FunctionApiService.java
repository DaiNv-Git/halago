package com.hitex.halago.service;

import com.hitex.halago.model.DAO.FunctionApiDao;
import com.hitex.halago.model.DAO.FunctionDao;
import com.hitex.halago.model.Function;

import java.util.List;

public interface FunctionApiService {
    List<FunctionApiDao> getFunctionApi();
    List<FunctionDao> getListFunction(String name, Integer pageSize, Integer pageNumber, String role);
    FunctionApiDao getListApiByFunctionId(int id);
    int deleteFunctionApi(int id);
}
