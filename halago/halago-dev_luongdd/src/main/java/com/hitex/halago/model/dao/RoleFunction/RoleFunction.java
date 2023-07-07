package com.hitex.halago.model.dao.RoleFunction;

import java.util.ArrayList;

public class RoleFunction {
    private int id;
    private ArrayList<Integer> idFunction;
    private int idRole;
    private ArrayList<String> functionName;
    private ArrayList<String> apiName;

    public ArrayList<String> getApiName() {
        return apiName;
    }

    public void setApiName(ArrayList<String> apiName) {
        this.apiName = apiName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getIdFunction() {
        return idFunction;
    }

    public void setIdFunction(ArrayList<Integer> idFunction) {
        this.idFunction = idFunction;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public ArrayList<String> getFunctionName() {
        return functionName;
    }

    public void setFunctionName(ArrayList<String> functionName) {
        this.functionName = functionName;
    }
}
