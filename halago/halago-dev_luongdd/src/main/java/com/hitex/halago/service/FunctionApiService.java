package com.hitex.halago.service;

import com.hitex.halago.model.dao.FunctionApiDao;
import com.hitex.halago.model.dao.FunctionDao;

import java.util.List;

public interface FunctionApiService {
    List<FunctionApiDao> getFunctionApi();
    List<FunctionDao> getListFunction(String name, Integer pageSize, Integer pageNumber, String role);
    FunctionApiDao getListApiByFunctionId(int id);
    int deleteFunctionApi(int id);
}
