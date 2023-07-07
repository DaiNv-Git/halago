package com.hitex.halago.service;

import com.hitex.halago.model.DAO.*;
import com.hitex.halago.model.Facebook;
import com.hitex.halago.model.Personal;

import java.util.List;

public interface PersonalService {
    Personal findPersonal(int id);

    Personal findPersonalByEmailOrPhone(String user);

    Personal sendCode(int introduceCodeUser,int idPersonal);
//    Personal insertPersonal(Personal personal);

    Personal updatePersonal(Personal personal);

    String AutoGenCode();

    int deletePersonal(int id);

    List<Integer> getListIdPersonal (int id);

    String findPasswordPersonal(String phone);

    List<PersonalDao> getListInferiority (int id);

    PersonalLoginModel mglogin(String phone, String password);

    String getPass(int id);

    List<Personal> findAll();

//    List<getInfoProject> getListProjectJoin(int id);

//    List<MemberOfPersonal> getPersonalByIntroduceCode(int id);

//    List<infoPersonal> getInfoMemberPersonal(int id);

//    List<getInfoProject> getlistProjectAttention(int id, int page);

//    List<CustomerStateOfCare> getListCustomerStateCare(int id);

    void updateAvatar(int id, String image);

    void updateName(int id, String full_name);

    void updateBirth(int id, int birthday);

    void updateGender(int id, String gender);

    void updateCard(int id, String cmnd);

    Personal findEmail(String email);

    Personal findBankAccount(String bankAccount);

    Personal findByPhone(String phone);

    PersonalLoginModel findPersonalById(int id);

    void updatePersonalInfo(int id, String fullName, String avatar, String birthDay, String gender, String address, String email, String phone, String cmnd, String bankAccount, String bankBranch, String bankName,String imageCmnd );
//    void updatePersonalInfo(int id, String fullName, String avatar, String birthDay, String gender, String address, String email, String phone, String cmnd, String bankAccount, String bankBranch, String bankName );
    String findPassword(String phone);

    Personal findByCmnd(String cmnd);

    void updatePass(int id, String new_pass);

    void updateAccountBank(int id, String bank_account, String bank_name, String bank_branch);

    void forgotPassword(String mail,int password);

    Personal login(String phone);

    Personal register(String full_name, String phone, String email, String password);

    List<PersonalModel> findPersonalA(int id);

    void updateReferenceId(int id, int referenceID);

    List<PersonalModel> findIntroduceCode(String introduceCode);

    Personal loginfb(String token);


    void updateActive(int idPersonal);
    Personal loginFB(String fbID,String name,String photo,String email,String phone);
    Personal loginFbByEmail(String fbID,String name,String photo,String email);
    Personal loginFbByPhone(String fbID,String name,String photo,String phone);
    void updateActiveOff(int idPersonal);

    void updateIdProject(String idProject,int idPersonal);
}
