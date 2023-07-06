package com.hitex.halago.model.DAO;

import java.util.ArrayList;

public class RoleDao {
    private int id;

    private ArrayList<Integer> idFunction;

    private int idRole;

    private String roleName;

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
