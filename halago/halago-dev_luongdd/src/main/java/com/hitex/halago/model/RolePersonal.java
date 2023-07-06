package com.hitex.halago.model;

import javax.persistence.*;

@Entity
@Table(name = "tbl_role_personal")
public class RolePersonal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idRolePersonal;
    @Column(name = "id_personal")
    private int idPersonal;
    @Column(name = "id_role")
    private int idRole;
    @Column(name = "status")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdRolePersonal() {
        return idRolePersonal;
    }

    public void setIdRolePersonal(int idRolePersonal) {
        this.idRolePersonal = idRolePersonal;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
