package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.dao.FunctionApiDao;
import com.hitex.halago.model.dao.FunctionDao;
import com.hitex.halago.model.Function;
import com.hitex.halago.repository.FunctionRepository;
import com.hitex.halago.service.FunctionApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class FunctionApiImpl implements FunctionApiService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    FunctionRepository functionRepository;

    @Override
    public List<FunctionApiDao> getFunctionApi() {
        return null;
    }

    @Override
    public List<FunctionDao> getListFunction(String name, Integer pageSize, Integer pageNumber, String role) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select fun ");
        builder.append("from Function fun Where (:name IS '' OR lower(fun.functionName) LIKE lower(concat('%', concat(:name, '%'))))");
        if (!Constant.ADMIN.equals(role)) {
            builder.append(" and fun.status = 1");
        }
        Query query = entityManager.createQuery(builder.toString(), Function.class);
        query.setParameter("name", name);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Function> resultList = query.getResultList();
        List<FunctionDao> functionDaos = new ArrayList<>();
        for (Function function : resultList) {
            FunctionDao functionDao = new FunctionDao();
            BeanUtils.copyProperties(function, functionDao);
            if (functionDao.getStatus() == 1) {
                functionDao.setStatusName("Active");
            } else {
                functionDao.setStatusName("InActive");
            }
            functionDaos.add(functionDao);
        }
        return functionDaos;
    }

    @Override
    public FunctionApiDao getListApiByFunctionId(int id) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select funApi.id,funApi.idApi, funApi.idFunction, fun.functionName ");
        builder.append("from Function fun ,FunctionApi funApi where funApi.idFunction=:idFunction and fun.id = funApi.idFunction ");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("idFunction", id);
        List<Object[]> resultList = query.getResultList();
        FunctionApiDao functionApiDao = new FunctionApiDao();
        for (Object[] objects : resultList) {
            functionApiDao.setId((Integer) objects[0]);
            functionApiDao.setIdApi(com.hitex.halago.utils.StringUtils.hashTagToIntArray(String.valueOf(objects[1])));
            functionApiDao.setIdFunction((Integer) objects[2]);
            functionApiDao.setNameFunction(String.valueOf(objects[3]));
        }
        return functionApiDao;
    }

    @Override
    public int deleteFunctionApi(int id) {
        Function function = functionRepository.findFunctionById(id);
        if (ObjectUtils.isEmpty(function)) {
            return Constant.FAILED;
        }
        function.setStatus(Constant.inactive);
        functionRepository.save(function);
        return Constant.SUCCESS;
    }
}
