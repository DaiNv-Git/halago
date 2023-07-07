package com.hitex.halago.model.dao;

import com.hitex.halago.config.Constant;
import com.hitex.halago.utils.StringUtils;

import javax.persistence.Transient;
import java.util.ArrayList;

public class PersonalLoginModel {


    private int idPersonal;

    private String avatar;

    private String fullName;


    private String introduceCodeUser;


    private String birthDay;


    private String gender;


    private String address;


    private String phone;

    private String email;

    private String bankAccount;

    private String bankBranch;

    private String bankName;

    private String regency;

    private String cmnd;

    private int scorePersonal;
    private int scoreRank;
    private String nameRank;
    private int nextScoreRank;
    private int nextScoreSecond;
    private int idRole;
    private String imageCmnd;

    @Transient
    private ArrayList<String> imageCmndList;

    public ArrayList<String> getImageCmndList() {
        return StringUtils.stringToStringArray(imageCmnd);
    }

    public String getImageCmnd() {
        return imageCmnd;
    }

    public String setImageCmnd(String imageCmnd) {
        this.imageCmnd = imageCmnd;
        return imageCmnd;
    }

    //    private String security;

//    public String getSecurity() {
//        return security;
//    }
//
//    public void setSecurity(String security) {
//        this.security = security;
//    }


    public int getNextScoreSecond() {
        return nextScoreSecond;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public void setNextScoreSecond(int nextScoreSecond) {
        this.nextScoreSecond = nextScoreSecond;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public int setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
        return idPersonal;
    }

    public String getAvatar() {
        if (avatar != null) {
            return Constant.IMAGE_DOMAIN + avatar;
        } else return null;
    }

    public String setAvatar(String avatar) {
        this.avatar = avatar;
        return avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public String setFullName(String fullName) {
        this.fullName = fullName;
        return fullName;
    }

    public String getIntroduceCodeUser() {
        return introduceCodeUser;
    }

    public void setIntroduceCodeUser(String introduceCodeUser) {
        this.introduceCodeUser = introduceCodeUser;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String setBirthDay(String birthDay) {
        this.birthDay = birthDay;
        return birthDay;
    }

    public String getGender() {
        return gender;
    }

    public String setGender(String gender) {
        this.gender = gender;
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String setAddress(String address) {
        this.address = address;
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String setPhone(String phone) {
        this.phone = phone;
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String setEmail(String email) {
        this.email = email;
        return email;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return bankAccount;
    }

    public String getRegency() {
        return regency;
    }

    public void setRegency(String regency) {
        this.regency = regency;
    }

    public String getCmnd() {
        return cmnd;
    }

    public String setCmnd(String cmnd) {
        this.cmnd = cmnd;
        return cmnd;
    }

    public int getScorePersonal() {
        return scorePersonal;
    }

    public void setScorePersonal(int scorePersonal) {
        this.scorePersonal = scorePersonal;
    }

    public int getScoreRank() {
        return scoreRank;
    }

    public void setScoreRank(int scoreRank) {
        this.scoreRank = scoreRank;
    }

    public String getNameRank() {
        return nameRank;
    }

    public void setNameRank(String nameRank) {
        this.nameRank = nameRank;
    }

    public int getNextScoreRank() {
        return nextScoreRank;
    }

    public void setNextScoreRank(int nextScoreRank) {
        this.nextScoreRank = nextScoreRank;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public String setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
        return bankBranch;
    }

    public String getBankName() {
        return bankName;
    }

    public String setBankName(String bankName) {
        this.bankName = bankName;
        return bankName;
    }
}
