package com.hitex.halago.model.dao;


import java.util.ArrayList;

public class FunctionApiDao {
    private int id;

    private ArrayList<Integer> idApi;

    private int idFunction;

    private String  nameFunction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getIdApi() {
        return idApi;
    }

    public void setIdApi(ArrayList<Integer> idApi) {
        this.idApi = idApi;
    }

    public int getIdFunction() {
        return idFunction;
    }

    public void setIdFunction(int idFunction) {
        this.idFunction = idFunction;
    }

    public String getNameFunction() {
        return nameFunction;
    }

    public void setNameFunction(String nameFunction) {
        this.nameFunction = nameFunction;
    }
}
