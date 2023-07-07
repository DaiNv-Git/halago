package com.hitex.halago.service.impl;

import com.hitex.halago.model.dao.PersonalToken;
import com.hitex.halago.service.PersonalTokenService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
@Service
public class PersonalTokenServiceImp implements PersonalTokenService {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public PersonalToken findByPhone(String phone) {
        StringBuilder builder=new StringBuilder();
        builder.append("select p.idPersonal,p.fullName,p.avatar,p.introduceCodeUser,p.birthDay,p.gender,p.address,p.phone" +
                ",p.email,p.cmnd,p.joinDate,p.bankAccount,p.bankName,p.bankBranch,p.status,p.password,p.regency," +
                "p.referenceId,p.fbID,p.active,p.groupProject,r.name");
        builder.append(" from Personal p,RolePersonal pr,Roles r ");
        builder.append(" where p.phone=:phone");
        builder.append(" and pr.idRole=r.idRole ");
        builder.append(" and p.idPersonal=pr.idPersonal ");
        Query query=entityManager.createQuery(builder.toString());
        query.setParameter("phone",phone);
        List<Object[]> results = query.getResultList();
        Object[] o=results.get(0);
        PersonalToken personalToken=new PersonalToken();
        personalToken.setIdPersonal(Integer.parseInt(o[0].toString()));
        personalToken.setFullName(String.valueOf(o[1]));
        personalToken.setAvatar(String.valueOf(o[2]));
        personalToken.setIntroduceCodeUser(String.valueOf(o[3]));
        personalToken.setBirthDay(String.valueOf(o[4]));
        personalToken.setGender(String.valueOf(o[5]));
        personalToken.setAddress(String.valueOf(o[6]));
        personalToken.setPhone(String.valueOf(o[7]));
        personalToken.setEmail(String.valueOf(o[8]));
        personalToken.setCmnd(String.valueOf(o[9]));
        personalToken.setJoinDate(String.valueOf(o[10]));
        personalToken.setBankAccount(String.valueOf(o[11]));
        personalToken.setBankName(String.valueOf(o[12]));
        personalToken.setBankBranch(String.valueOf(o[13]));
        personalToken.setStatus(Integer.parseInt(o[14].toString()));
        personalToken.setPassword(String.valueOf(o[15]));
        personalToken.setRegency(String.valueOf(o[16]));
        personalToken.setReferenceId(Integer.parseInt(o[17].toString()));
        personalToken.setFbID(String.valueOf(o[18]));
        personalToken.setActive(Integer.parseInt(o[19].toString()));
        personalToken.setGroupProject(String.valueOf(o[20]));
        personalToken.setRoles(new String[]{String.valueOf(o[21])});
        return personalToken;
    }
}
