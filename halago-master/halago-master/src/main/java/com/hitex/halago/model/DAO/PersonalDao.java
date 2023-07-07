package com.hitex.halago.model.DAO;

import com.hitex.halago.config.Constant;

public class PersonalDao {

    private int idPersonal;
    private String name;
    private String phone;
    private String avatar;
//    private List<MemberOfPersonal> infoTeam;

//    public List<MemberOfPersonal> getInfoTeam() {
//        return infoTeam;
//    }

//    public void setInfoTeam(List<MemberOfPersonal> infoTeam) {
//        this.infoTeam = infoTeam;
//    }

    public String getAvatar() {
        if (avatar != null) {
            return Constant.IMAGE_DOMAIN + avatar;
        } else return null;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
