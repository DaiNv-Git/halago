package com.hitex.halago.model;


import javax.persistence.*;

@Entity
@Table(name = "role_function")
public class RoleFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "id_function")
    private String idFunction;
    @Column(name = "id_role")
    private int idRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdFunction() {
        return idFunction;
    }

    public void setIdFunction(String idFunction) {
        this.idFunction = idFunction;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
