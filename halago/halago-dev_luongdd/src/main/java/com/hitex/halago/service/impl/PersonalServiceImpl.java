package com.hitex.halago.service.impl;


import com.hitex.halago.model.dao.*;
import com.hitex.halago.model.Personal;

import com.hitex.halago.model.*;
import com.hitex.halago.repository.*;

import com.hitex.halago.service.PersonalService;
import com.hitex.halago.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.hitex.halago.config.Constant.*;
import static com.hitex.halago.config.Constant.NUMBER;

@Service
public class PersonalServiceImpl implements PersonalService {


    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RolePersonalRepository rolePersonalRepository;
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    PersonalServiceImpl personalServiceImpl;

    @Autowired
    private Mailservice mailservice;

    @Autowired
    private fbRepository fbRepository;

    public Personal findIdPersonal(int id) {
        return personalRepository.findPersonal(id);
    }

    @Override
    public Personal findPersonal(int id) {
        StringBuilder builder = new StringBuilder();
        builder.append("select p.idPersonal,p.avatar,p.fullName,p.introduceCodeUser,p.birthDay,p.gender,p.address,p.phone,p.email,p.cmnd,p.joinDate,p.bankAccount,p.bankName,p.bankBranch,p.status,p.password,p.regency,p.referenceId,p.fbID,p.active,p.groupProject,p.idRole,p.imageCmnd ");
        builder.append("from Personal p ");
        builder.append("where p.idPersonal=:id ");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("id", id);

        List<Object[]> results = query.getResultList();
        Personal personal = new Personal();
        for (Object[] obj : results) {
            personal.setIdPersonal(Integer.valueOf((Integer) obj[0]));
            if (obj[1] != null) {
                personal.setAvatar(String.valueOf(obj[1]));
            } else {
                personal.setAvatar("");
            }
            if (obj[2] != null) {
                personal.setFullName(String.valueOf(obj[2]));
            } else {
                personal.setFullName("");
            }
            if (obj[3] != null) {
                personal.setIntroduceCodeUser(String.valueOf(obj[3]));
            } else {
                personal.setIntroduceCodeUser("");
            }
            if (obj[4] != null) {
                personal.setBirthDay(DateUtils.parseTimestampToDate(Integer.parseInt(obj[4].toString())));
            } else {
                personal.setBirthDay(DateUtils.parseTimestampToDate(1989 - 01 - 01));
            }
            if (obj[5] != null) {
                personal.setGender(String.valueOf(obj[5]));
            } else {
                personal.setGender("");
            }
            if (obj[6] != null) {
                personal.setAddress(String.valueOf(obj[6]));
            } else {
                personal.setAddress("");
            }
            if (obj[7] != null) {
                personal.setPhone(String.valueOf(obj[7]));
            } else {
                personal.setPhone("");
            }
            if (obj[8] != null) {
                personal.setEmail(String.valueOf(obj[8]));
            } else {
                personal.setEmail("");
            }
            if (obj[9] != null) {
                personal.setCmnd(String.valueOf(obj[9]));
            } else {
                personal.setCmnd("");
            }
            if (obj[10] != null) {
                personal.setJoinDate(String.valueOf(obj[10]));
            } else {
                personal.setJoinDate("");
            }
            if (obj[11] != null) {
                personal.setBankAccount(String.valueOf(obj[11]));
            } else {
                personal.setBankAccount("");
            }
            if (obj[12] != null) {
                personal.setBankName(String.valueOf(obj[12]));
            } else {
                personal.setBankName("");
            }
            if (obj[13] != null) {
                personal.setBankBranch(String.valueOf(obj[13]));
            } else {
                personal.setBankBranch("");
            }
            if (obj[14] != null) {
                personal.setStatus(Integer.valueOf((Integer) obj[14]));
            } else {
                personal.setStatus(0);
            }
            if (obj[15] != null) {
                personal.setPassword(String.valueOf(obj[15]));
            } else {
                personal.setPassword("null");
            }
            if (obj[16] != null) {
                personal.setRegency(String.valueOf(obj[16]));
            } else {
                personal.setRegency("");
            }
            if (obj[17] != null) {
                personal.setReferenceId(Integer.valueOf((Integer) obj[17]));
            } else {
                personal.setReferenceId(0);
            }
            if (obj[18] != null) {
                personal.setFbID(String.valueOf(obj[18]));
            } else {
                personal.setFbID("");
            }
            if (obj[19] != null) {
                personal.setActive(Integer.valueOf((Integer) obj[19]));
            } else {
                personal.setActive(0);
            }
            if (obj[20] != null) {
                personal.setGroupProject(String.valueOf(obj[20]));
            } else {
                personal.setGroupProject("");
            }
            if (obj[21] != null) {
                personal.setIdRole(Integer.valueOf((Integer) obj[21]));
            } else {
                personal.setIdRole(7);
            }
            personal.setImageCmnd(String.valueOf(obj[22]));
        }
        return personal;
    }


    @Override
    public PersonalLoginModel findPersonalById(int id) {
        StringBuilder builder = new StringBuilder();
        builder.append("select p.idPersonal,p.avatar,p.fullName,p.introduceCodeUser,p.birthDay,p.gender,p.address,p.phone,p.email,p.cmnd,p.bankAccount,p.bankName,p.bankBranch,p.regency,rp.score,r.name,r.scoreRank,p.imageCmnd,p.idRole  ");
        builder.append("from Personal p , RankPersonal rp, Rank r ");
        builder.append("where p.idPersonal=:id ");
        builder.append("and r.idRank = rp.idRank and p.idPersonal = rp.idPersonal ");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("id", id);

        List<Object[]> results = query.getResultList();
        PersonalLoginModel personal = new PersonalLoginModel();
        for (Object[] obj : results) {
            personal.setIdPersonal(Integer.valueOf((Integer) obj[0]));
            if (obj[1] != null) {
                personal.setAvatar(String.valueOf(obj[1]));
            } else {
                personal.setAvatar("");
            }
            if (obj[2] != null) {
                personal.setFullName(String.valueOf(obj[2]));
            } else {
                personal.setFullName("");
            }
            personal.setIntroduceCodeUser(String.valueOf(obj[3]));
            if (obj[4] != null) {
                personal.setBirthDay(DateUtils.parseTimestampToDate(Integer.parseInt(obj[4].toString())));
            } else {
                personal.setBirthDay(DateUtils.parseTimestampToDate(1989 - 01 - 01));
            }
            if (obj[5] != null) {
                personal.setGender(String.valueOf(obj[5]));
            } else {
                personal.setGender("");
            }
            if (obj[6] != null) {
                personal.setAddress(String.valueOf(obj[6]));
            } else {
                personal.setAddress("");
            }
            if (obj[7] != null) {
                personal.setPhone(String.valueOf(obj[7]));
            } else {
                personal.setPhone("");
            }
            if (obj[8] != null) {
                personal.setEmail(String.valueOf(obj[8]));
            } else {
                personal.setEmail("");
            }
            if (obj[9] != null) {
                personal.setCmnd(String.valueOf(obj[9]));
            } else {
                personal.setCmnd("");
            }
            if (obj[10] != null) {
                personal.setBankAccount(String.valueOf(obj[10]));
            } else {
                personal.setBankAccount("");
            }
            if (obj[11] != null) {
                personal.setBankName(String.valueOf(obj[11]));
            } else {
                personal.setBankName("");
            }
            if (obj[12] != null) {
                personal.setBankBranch(String.valueOf(obj[12]));
            } else {
                personal.setBankBranch("");
            }
            if (obj[13] != null) {
                personal.setRegency(String.valueOf(obj[13]));
            } else {
                personal.setRegency("");
            }
            personal.setScorePersonal(Integer.valueOf((Integer) obj[14]));
            personal.setNameRank(String.valueOf(obj[15]));
            personal.setScoreRank(Integer.valueOf((Integer) obj[16]));
            if (personal.getScorePersonal() < personalServiceImpl.sliver()) {
                personal.setNextScoreRank(personalServiceImpl.sliver());
                personal.setNextScoreSecond(personalServiceImpl.gold());
            } else if (personal.getScorePersonal() >= personalServiceImpl.sliver() && personal.getScorePersonal() < personalServiceImpl.gold()) {
                personal.setNextScoreRank(personalServiceImpl.gold());
                personal.setNextScoreSecond(personalServiceImpl.diamond());
            } else if (personal.getScorePersonal() < personalServiceImpl.diamond()) {
                personal.setNextScoreRank(personalServiceImpl.diamond());
                personal.setNextScoreSecond(personalServiceImpl.diamond());
            } else {
                personal.setNextScoreRank(personalServiceImpl.diamond());
            }
            personal.setImageCmnd(String.valueOf(obj[17]));
            personal.setIdRole(Integer.valueOf((Integer) obj[18]));
        }
        return personal;
    }


    @Override
    public Personal sendCode(int introduceCodeUser, int idPersonal) {
        return null;
    }



    @Override
    public List<Integer> getListIdPersonal(int id) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT p.id_personal,p.full_name ");
        builder.append("FROM tbl_personal p ");
        builder.append("WHERE p.reference_id=:idPersonal and (p.status = 1 or p.status = 0) ");
        Query query = entityManager.createNativeQuery(builder.toString());
        query.setParameter("idPersonal", id);
        List<Object[]> resultsList = query.getResultList();
        List<Integer> lists = new ArrayList<>();
        for (Object[] obj : resultsList) {
            Personal personalDao = new Personal();
            int idPersonal = personalDao.setIdPersonal(Integer.valueOf(obj[0].toString()));
            lists.add(idPersonal);
        }
        return lists;
    }
//    @Override
//    public List<PersonalDao> getListIdPersonal(int id) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Select p.id_personal,1 ");
//        builder.append("from tbl_personal p ");
//        builder.append("Where p.id_personal=:idPersonal ");
//        builder.append("and (p.status = 1 or p.status = 0)");
//        Query query = entityManager.createNativeQuery(builder.toString());
//        query.setParameter("idPersonal", id);
//        System.out.println("query = " + query);
//        List<Object[]> result = query.getResultList();
//        List<PersonalDao> list = new ArrayList<>();
//        for (Object[] obj : result) {
//            PersonalDao personal = new PersonalDao();
//            System.out.println("obj[0].toString() = " + obj[0].toString());
//            personal.setIdPersonal(Integer.valueOf(obj[0].toString()));
////            personal.setPhone(String.valueOf(obj[1]));
//            list.add(personal);
//        }
//        return list;
//    }

    @Override
    public List<PersonalDao> getListInferiority(int id) {
//        List<Integer> listPerson = getListIdPersonal(id);
//        StringBuilder builder = new StringBuilder();
//        builder.append("SELECT tbl_personal.id_personal,tbl_personal.full_name,tbl_personal.phone,tbl_personal.avatar ");
//        builder.append("FROM tbl_personal ");
//        builder.append("WHERE tbl_personal.reference_id=:idPersonal and (tbl_personal.status = 0 or tbl_personal.status = 1) ");
//        Query query = entityManager.createNativeQuery(builder.toString());
//        query.setParameter("idPersonal", id);
//        List<PersonalDao> list1 = new ArrayList<>();
//        List<Object[]> results = query.getResultList();
////        query.setFirstResult((page - 1) * 8);
////        query.setMaxResults(8);
//        for (int i = 0; i < listPerson.size(); i++) {
//            int idPerson = listPerson.get(i);
//            System.out.println("idPerson = " + idPerson);
//            List<Object[]> result = entityManager.createQuery("select p.idPersonal,p.fullName,p.phone from Personal p where (p.status=1 or p.status=0) and p.referenceId=:id").setParameter("id", idPerson).getResultList();
//            for (Object[] obj1 : result) {
//                System.out.println(" = " + 02);
//                MemberOfPersonal member = new MemberOfPersonal();
//                member.setIdPersonal(Integer.valueOf((Integer) obj1[0]));
//                member.setNameMember(String.valueOf(obj1[1]));
//                member.setPhone(String.valueOf(obj1[2]));
//                member.setRevenue(0);
//                member.setTotalMember(0);
//                list.add(member);
//            }
//
//        }
//        for (Object[] obj : results) {
//            PersonalDao personalDao = new PersonalDao();
//            System.out.println(" = " + 01);
//            personalDao.setIdPersonal(Integer.valueOf((Integer) obj[0]));
//            personalDao.setName(String.valueOf(obj[1]));
//            personalDao.setPhone(String.valueOf(obj[2]));
//            personalDao.setAvatar(String.valueOf(obj[3]));
//            personalDao.setInfoTeam(list);
//            list1.add(personalDao);
//        }
        return null;
    }


    //    @Override
    public List<Integer> getPersonalByIntroduceCode(int id) {
        StringBuilder builder = new StringBuilder();
        builder.append("select p.idPersonal,p.fullName,p.phone from Personal p where (p.status=1 or p.status=0) and p.referenceId=:id");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("id", id);
        List<Object[]> result = query.getResultList();
        List<Integer> list1 = new ArrayList<>();
        for (Object[] obj : result) {
            int idPersonal = Integer.valueOf((Integer) obj[0]);
            list1.add(idPersonal);
        }
        return list1;
    }


    @Override
    public int deletePersonal(int id) {
        Personal personal = findIdPersonal(id);
        if (personal != null) {
            personal.setIdPersonal(id);
            personal.setStatus(2);
            personalRepository.save(personal);
            RolePersonal rolePersonal = rolePersonalRepository.findPersonal(id);
            rolePersonal.setIdRolePersonal(rolePersonal.getIdRolePersonal());
            rolePersonal.setStatus(2);
            rolePersonalRepository.save(rolePersonal);
//            TeamMember teamMember = teamMemberRepository.findPersonal(id);
//            teamMember.setIdTeamMember(teamMember.getIdTeamMember());
//            teamMember.setStatus(2);
//            teamMemberRepository.save(teamMember);
            return tontai;

        }
        return khongtontai;
    }

    @Override
    public PersonalLoginModel mglogin(String phone, String password) {
        int sliver = sliver();
        int gold = gold();
        int diamond = diamond();
//        List<Object[]> results = entityManager.createQuery("select p.idPersonal,p.fullName,p.avatar,p.regency,p.birthDay,p.gender,p.address,p.email,p.phone,p.cmnd,p.introduceCodeUser,p.bankAccount,p.bankBranch,p.bankName,rp.score,r.name,r.scoreRank,rop.idRole,p.imageCmnd from Personal p , RankPersonal rp, Rank r, Role ro,RolePersonal rop where r.idRank = rp.idRank and p.idPersonal = rp.idPersonal and (p.phone=:phone or p.email=:phone ) and p.password=:password and p.idPersonal= rop.idPersonal and ro.idRole = rop.idRole and   (p.status =1 or p.status =0)").setParameter("phone", phone).setParameter("password", password).getResultList();
        PersonalLoginModel personalLoginModel = new PersonalLoginModel();
//        for (Object[] obj : results) {
////            System.out.println("ssssss");
//            personalLoginModel.setIdPersonal(Integer.valueOf((Integer) obj[0]));
//            if (obj[1] != null) {
//                personalLoginModel.setFullName(String.valueOf(obj[1]));
//            } else {
//                personalLoginModel.setFullName("");
//            }
//            if (obj[2] != null) {
//                personalLoginModel.setAvatar(String.valueOf(obj[2]));
//            } else {
//                personalLoginModel.setAvatar("");
//            }
//            if (obj[3] != null) {
//                personalLoginModel.setRegency(String.valueOf(obj[3]));
//            } else {
//                personalLoginModel.setRegency("");
//            }
//            if (obj[4] != null) {
//                personalLoginModel.setBirthDay(DateUtils.parseTimestampToDate(Integer.parseInt(obj[4].toString())));
//            } else {
//                personalLoginModel.setBirthDay(DateUtils.parseTimestampToDate(1989 - 01 - 01));
//            }
//            if (obj[5] != null) {
//                personalLoginModel.setGender(String.valueOf(obj[5]));
//            } else {
//                personalLoginModel.setGender("");
//            }
//            if (obj[6] != null) {
//                personalLoginModel.setAddress(String.valueOf(obj[6]));
//            } else {
//                personalLoginModel.setAddress("");
//            }
//            if (obj[7] != null) {
//                personalLoginModel.setEmail(String.valueOf(obj[7]));
//            } else {
//                personalLoginModel.setEmail("");
//            }
//            if (obj[8] != null) {
//                personalLoginModel.setPhone(String.valueOf(obj[8]));
//            } else {
//                personalLoginModel.setPhone("");
//            }
//
//            if (obj[9] != null) {
//                personalLoginModel.setCmnd(String.valueOf(obj[9]));
//            } else {
//                personalLoginModel.setCmnd("");
//            }
//            if (obj[10] != null) {
//                personalLoginModel.setIntroduceCodeUser(String.valueOf(obj[10]));
//            } else {
//                personalLoginModel.setIntroduceCodeUser("");
//            }
//            if (obj[11] != null) {
//                personalLoginModel.setBankAccount(String.valueOf(obj[11]));
//            } else {
//                personalLoginModel.setBankAccount("");
//            }
//            if (obj[12] != null) {
//                personalLoginModel.setBankBranch(String.valueOf(obj[13]));
//            } else {
//                personalLoginModel.setBankBranch("");
//            }
//            if (obj[13] != null) {
//                personalLoginModel.setBankName(String.valueOf(obj[12]));
//            } else {
//                personalLoginModel.setBankName("");
//            }
//            if (obj[17] != null) {
//                personalLoginModel.setIdRole(Integer.valueOf((Integer) obj[17]));
//            } else {
//                personalLoginModel.setIdRole(7);
//            }
//            personalLoginModel.setScorePersonal(Integer.valueOf((Integer) obj[14]));
//            personalLoginModel.setNameRank(String.valueOf(obj[15]));
//            personalLoginModel.setScoreRank(Integer.valueOf((Integer) obj[16]));
//            if (personalLoginModel.getScorePersonal() < sliver) {
//                personalLoginModel.setNextScoreRank(sliver);
//                personalLoginModel.setNextScoreSecond(gold);
//            } else if (personalLoginModel.getScorePersonal() >= sliver && personalLoginModel.getScorePersonal() < gold) {
//                personalLoginModel.setNextScoreRank(gold);
//                personalLoginModel.setNextScoreSecond(diamond);
//            } else if (personalLoginModel.getScorePersonal() < diamond) {
//                personalLoginModel.setNextScoreRank(diamond);
//                personalLoginModel.setNextScoreSecond(diamond);
//            } else {
//                personalLoginModel.setNextScoreRank(diamond);
//            }
//            personalLoginModel.setImageCmnd(String.valueOf(obj[18]));
//        }
        return personalLoginModel;
    }

    public int sliver() {
        int scoreSliver = (int) entityManager.createNativeQuery("select tbl_rank.score_rank FROM tbl_rank WHERE tbl_rank.id_rank=3").getSingleResult();
        int score = Integer.valueOf(scoreSliver);
        return score;
    }

    public int gold() {
        int scoreGold = (int) entityManager.createNativeQuery("select tbl_rank.score_rank FROM tbl_rank WHERE tbl_rank.id_rank=5").getSingleResult();
        int score = Integer.valueOf(scoreGold);
        return score;
    }

    public int diamond() {
        int scoreDiamond = (int) entityManager.createNativeQuery("select tbl_rank.score_rank FROM tbl_rank WHERE tbl_rank.id_rank=6").getSingleResult();
        int score = Integer.valueOf(scoreDiamond);
        return score;
    }


    @Override
    public String getPass(int id) {
//        Object sql = entityManager.createQuery("select p.password from Personal p where p.idPersonal=:id").setParameter("id", id).getSingleResult();
        return null;
    }

    @Override
    public Personal updatePersonal(Personal personal) {
        personalRepository.save(personal);
        Personal personal4 = personalRepository.findByPhone(personal.getPhone());
        RolePersonal rolePersonal = rolePersonalRepository.findPersonal(personal.getIdPersonal());
        rolePersonal.setIdPersonal(personal4.getIdPersonal());
        rolePersonal.setIdRole(personal4.getIdRole());
        rolePersonalRepository.save(rolePersonal);
//        if (saleRepository.findByPhone(personal.getPhone()) != null) {
//            Sale sale = saleRepository.findByPhone(personal.getPhone());
//            sale.setName(personal.getFullName());
//            sale.setAddress(personal.getAddress());
//            sale.setDate(personal.getJoinDate());
//            sale.setBankAccount(personal.getBankAccount());
//            sale.setBankName(personal.getBankName());
//            sale.setBankBrand(personal.getBankBranch());
//            sale.setPhone(personal.getPhone());
//            sale.setCmnd(personal.getCmnd());
//            sale.setStatus(personal.getStatus());
//            sale.setEmail(personal.getEmail());
//            sale.setPassword(personal.getPassword());
//            saleRepository.save(sale);
//        }
        return personal;
    }


    @Override
    public List<Personal> findAll() {
        StringBuilder builder = new StringBuilder();
        builder.append("select p.idPersonal,p.avatar,p.fullName,p.introduceCodeUser,p.birthDay,p.gender,p.address,p.phone,p.email,p.cmnd,p.joinDate,p.bankAccount,p.bankName,p.bankBranch,p.status,p.password,p.regency,p.referenceId,p.fbID,p.active,p.groupProject,p.idRole,p.imageCmnd ");
        builder.append("from Personal p ");
        builder.append("where p.status=1 or p.status =0 order by p.idPersonal desc");
        Query query = entityManager.createQuery(builder.toString());

        List<Object[]> results = query.getResultList();
        List<Personal> list = new ArrayList<>();
        for (Object[] obj : results) {
            Personal personal = new Personal();
            personal.setIdPersonal(Integer.valueOf((Integer) obj[0]));
            if (obj[1] != null) {
                personal.setAvatar(String.valueOf(obj[1]));
            } else {
                personal.setAvatar("");
            }
            if (obj[2] != null) {
                personal.setFullName(String.valueOf(obj[2]));
            } else {
                personal.setFullName("");
            }
            if (obj[3] != null) {
                personal.setIntroduceCodeUser(String.valueOf(obj[3]));
            } else {
                personal.setIntroduceCodeUser("");
            }
            if (obj[4] != null) {
                personal.setBirthDay(DateUtils.parseTimestampToDate(Integer.parseInt(obj[4].toString())));
            } else {
                int time = 1989 - 01 - 01;
                personal.setBirthDay(DateUtils.parseTimestampToDate(time));
            }
            if (obj[5] != null) {
                personal.setGender(String.valueOf(obj[5]));
            } else {
                personal.setGender("");
            }
            if (obj[6] != null) {
                personal.setAddress(String.valueOf(obj[6]));
            } else {
                personal.setAddress("");
            }
            if (obj[7] != null) {
                personal.setPhone(String.valueOf(obj[7]));
            } else {
                personal.setPhone("");
            }
            if (obj[8] != null) {
                personal.setEmail(String.valueOf(obj[8]));
            } else {
                personal.setEmail("");
            }
            if (obj[9] != null) {
                personal.setCmnd(String.valueOf(obj[9]));
            } else {
                personal.setCmnd("");
            }
            if (obj[10] != null) {
                personal.setJoinDate(String.valueOf(obj[10]));
            } else {
                personal.setJoinDate("");
            }
            if (obj[11] != null) {
                personal.setBankAccount(String.valueOf(obj[11]));
            } else {
                personal.setBankAccount("");
            }
            if (obj[12] != null) {
                personal.setBankName(String.valueOf(obj[12]));
            } else {
                personal.setBankName("");
            }
            if (obj[13] != null) {
                personal.setBankBranch(String.valueOf(obj[13]));
            } else {
                personal.setBankBranch("");
            }
            if (obj[14] != null) {
                personal.setStatus(Integer.valueOf((Integer) obj[14]));
            } else {
                personal.setStatus(0);
            }
            if (obj[15] != null) {
                personal.setPassword(String.valueOf(obj[15]));
            } else {
                personal.setPassword("null");
            }
            if (obj[16] != null) {
                personal.setRegency(String.valueOf(obj[16]));
            } else {
                personal.setRegency("");
            }
            if (obj[17] != null) {
                personal.setReferenceId(Integer.valueOf((Integer) obj[17]));
            } else {
                personal.setReferenceId(0);
            }
            if (obj[18] != null) {
                personal.setFbID(String.valueOf(obj[18]));
            } else {
                personal.setFbID("");
            }
            if (obj[19] != null) {
                personal.setActive(Integer.valueOf((Integer) obj[19]));
            } else {
                personal.setActive(0);
            }
            if (obj[20] != null) {
                personal.setGroupProject(String.valueOf(obj[20]));
            } else {
                personal.setGroupProject("");
            }
            if (obj[21] != null) {
                personal.setIdRole(Integer.valueOf((Integer) obj[21]));
            } else {
                personal.setIdRole(7);
            }
            personal.setImageCmnd(String.valueOf(obj[22]));
            list.add(personal);
        }
        return list;
    }

    @Override
    @Transactional
    public Personal login(String phone) {
        return personalRepository.login(phone);
    }

    @Override
    public List<PersonalModel> findPersonalA(int id) {
//        List<Object[]> result = entityManager.createQuery("SELECT p.fullName,p.cmnd,p.avatar,p.introduceCodeUser,p.birthDay,p.gender,p.address,p.phone,p.bankAccount,p.bankName,p.bankBranch,p.email,p.password,p.idPersonal FROM  Personal p WHERE  p.idPersonal=:id and (p.status=0 or p.status=1)").setParameter("id", id).getResultList();
//        System.out.println(result.toString());
//        List<PersonalModel> list = new ArrayList<>();
//        PersonalModel apointment = new PersonalModel();
//        String score = personalRepository.findScore(id);
//        System.out.println("score " + score);
//        String nameRank = personalRepository.findNameRank(id);
//        String scoreRank = personalRepository.findScoreRank(id);
//        List<String> nameProject = personalRepository.findNameProject(id);
//        for (Object[] obj : result) {
//
//            apointment.setIdPersonal(Integer.valueOf((Integer) obj[13]));
//            apointment.setCmnd(String.valueOf(obj[1]));
//            if (obj[2] == null) {
//                apointment.setAvatar(null);
//            } else {
//                apointment.setAvatar(String.valueOf(obj[2]));
//            }
//            if (obj[3] == null) {
//                apointment.setIntroduceCodeUser(null);
//            } else {
//                apointment.setIntroduceCodeUser(String.valueOf(obj[3]));
//            }
//            if (obj[4] == null) {
//                apointment.setBirthDay(null);
//            } else {
//                apointment.setBirthDay(String.valueOf(Integer.parseInt(obj[4].toString())));
//            }
//            if (obj[5] == null) {
//                apointment.setGender(null);
//            } else {
//                apointment.setGender(String.valueOf(obj[5]));
//            }
//            if (obj[6] == null) {
//                apointment.setAddress(null);
//            } else {
//                apointment.setAddress(String.valueOf(obj[6]));
//            }
//
//            apointment.setPhone(String.valueOf(obj[7]));
//            if (obj[8] == null) {
//                apointment.setBankAccount(null);
//            } else {
//                apointment.setBankAccount(String.valueOf(obj[8]));
//            }
//            if (obj[9] == null) {
//                apointment.setBankName(null);
//            } else {
//                apointment.setBankName(String.valueOf(obj[9]));
//            }
//            if (obj[10] == null) {
//                apointment.setBankBranch(null);
//            } else {
//                apointment.setBankBranch(String.valueOf(obj[10]));
//            }
//            apointment.setFullName(String.valueOf(obj[0]));
//            apointment.setPassword(String.valueOf(obj[12]));
//            apointment.setEmail(String.valueOf(obj[11]));
//            if (score == null) {
//                apointment.setScore(0);
//            } else {
//                apointment.setScore(Integer.parseInt(String.valueOf(score)));
//            }
//            if (scoreRank == null) {
//                apointment.setScoreRank(0);
//            } else {
//                apointment.setScoreRank(Integer.parseInt(String.valueOf(scoreRank)));
//            }
//            if (nameProject.isEmpty()) {
//                apointment.setHistoryProject(null);
//            } else {
//                apointment.setHistoryProject(nameProject);
//            }
//            if (nameRank == null) {
//                apointment.setNameRank("thường");
//            } else {
//                apointment.setNameRank(String.valueOf(nameRank));
//            }
//            list.add(apointment);
//        }
        return null;
    }

    @Override
    @Transactional
    public void updateReferenceId(int id, int referenceID) {
        personalRepository.updateReferenceId(id, referenceID);
    }

    @Override
    public List<PersonalModel> findIntroduceCode(String introduceCode) {
//        List<Object[]> result = entityManager.createQuery("select p.idPersonal,p.fullName from Personal p where p.introduceCodeUser=:introduceCode").setParameter("introduceCode", introduceCode).getResultList();
        List<PersonalModel> list = new ArrayList<>();
//        Object[] obj = result.get(0);
        return null;
    }

    @Override
    public Personal loginfb(String token) {
        String accessToken = token;
//        Facebook facebook = new FacebookTemplate(accessToken, "myapp");

//
        return null;
    }

    @Override
    @Transactional
    public void updateActive(int idPersonal) {
        personalRepository.updateActive(idPersonal);
    }

    @Override
    @Transactional
    public Personal loginFB(String fbID, String name, String photo, String email, String phone) {
//        Personal personal = findByPhone(phone);
//
//        if (personal == null) {
//            Personal emailPersonal = findEmail(email);
//            if (emailPersonal == null) {
//                Personal facebook = new Personal();
//                facebook.setFbID(fbID);
//                facebook.setFullName(name);
//                facebook.setAvatar(photo);
//                facebook.setPhone(phone);
//                String b = String.valueOf(StringUtils.Radom(111111, 99999999));
//                Personal facebook1 = personalRepository.findByFbID(b);
//                if (facebook1 != null) {
//                    b = (String.valueOf(StringUtils.Radom(111111, 99999999)));
//                }
//                facebook.setIntroduceCodeUser(b);
//                facebook.setEmail(email);
//                personalRepository.save(facebook);
//                String c = fbID;
//                System.out.println("mmm" + c);
//                int a = Integer.parseInt(c);
//                System.out.println("aaaa" + a);
//                Personal personal1 = personalRepository.findByFbID(personal.getFbID());
//                RankPersonal rankPersonal = new RankPersonal();
//                rankPersonal.setIdPersonal(personal1.getIdPersonal());
//                rankPersonal.setScore(0);
//                rankPersonal.setIdRank(1);
//                rankPersonalRespository.save(rankPersonal);
//                HistoryScore historyScore = new HistoryScore();
//                historyScore.setScore(0);
//                historyScore.setIdPersonal(personal1.getIdPersonal());
//                historyScore.setUpdateDate(DateUtils.parseTimeStamp(DateUtils.getCurrentTime()));
//                historyScoreRepository.save(historyScore);
//                Notification notification = new Notification();
//                notification.setIdPersonal(personal1.getIdPersonal());
//                notification.setContent("ban vua dang ki thanh cong");
//                notification.setTitle("thong bao tai khoan");
//                notification.setStatus(0);
//                notificationRepository.save(notification);
//            } else {
//                int idPersonal = emailPersonal.getIdPersonal();
//                personalRepository.updatefbIDByEmail(fbID, idPersonal);
//            }
//        } else {
//            int idPersonal = personal.getIdPersonal();
//            personalRepository.updateFbID(fbID, idPersonal);
//
//        }
        return personalRepository.findByFbID(fbID);
    }

    @Override
    public Personal loginFbByEmail(String fbID, String name, String photo, String email) {
        Personal emailPersonal = findEmail(email);
//        if (emailPersonal == null) {
//            Personal facebook = new Personal();
//            facebook.setFbID(fbID);
//            facebook.setFullName(name);
//            facebook.setAvatar(photo);
//            String b = String.valueOf(StringUtils.Radom(111111, 99999999));
//            Personal facebook1 = personalRepository.findByFbID(b);
//            if (facebook1 != null) {
//                b = (String.valueOf(StringUtils.Radom(111111, 99999999)));
//            }
//            facebook.setIntroduceCodeUser(b);
//            facebook.setEmail(email);
//            personalRepository.save(facebook);
//            String c = fbID;
//            System.out.println("mmm" + c);
//            int a = Integer.parseInt(c);
//            System.out.println("aaaa" + a);
//            RankPersonal rankPersonal = new RankPersonal();
//            rankPersonal.setIdPersonal(a);
//            rankPersonal.setScore(0);
//            rankPersonal.setIdRank(1);
//            rankPersonalRespository.save(rankPersonal);
//            HistoryScore historyScore = new HistoryScore();
//            historyScore.setScore(0);
//            historyScore.setIdPersonal(a);
//            historyScore.setUpdateDate(DateUtils.parseTimeStamp(DateUtils.getCurrentTime()));
//            historyScoreRepository.save(historyScore);
//            Notification notification = new Notification();
//            notification.setIdPersonal(a);
//            notification.setContent("ban vua dang ki thanh cong");
//            notification.setTitle("thong bao tai khoan");
//            notification.setStatus(0);
//            notificationRepository.save(notification);
//        } else {
//            int idPersonal = emailPersonal.getIdPersonal();
//            personalRepository.updatefbIDByEmail(fbID, idPersonal);
//        }
        return personalRepository.findByFbID(fbID);
    }

    @Override
    public Personal loginFbByPhone(String fbID, String name, String photo, String phone) {
        Personal emailPersonal = findEmail(phone);
//        if (emailPersonal == null) {
//            Personal facebook = new Personal();
//            facebook.setFbID(fbID);
//            facebook.setFullName(name);
//            facebook.setAvatar(photo);
//            String b = String.valueOf(StringUtils.Radom(111111, 99999999));
//            Personal facebook1 = personalRepository.findByFbID(b);
//            if (facebook1 != null) {
//                b = (String.valueOf(StringUtils.Radom(111111, 99999999)));
//            }
//            facebook.setIntroduceCodeUser(b);
//            facebook.setPhone(phone);
//            personalRepository.save(facebook);
//            String c = fbID;
//            System.out.println("mmm" + c);
//            int a = Integer.parseInt(c);
//            System.out.println("aaaa" + a);
//            RankPersonal rankPersonal = new RankPersonal();
//            rankPersonal.setIdPersonal(a);
//            rankPersonal.setScore(0);
//            rankPersonal.setIdRank(1);
//            rankPersonalRespository.save(rankPersonal);
//            HistoryScore historyScore = new HistoryScore();
//            historyScore.setScore(0);
//            historyScore.setIdPersonal(a);
//            historyScore.setUpdateDate(DateUtils.parseTimeStamp(DateUtils.getCurrentTime()));
//            historyScoreRepository.save(historyScore);
//            Notification notification = new Notification();
//            notification.setIdPersonal(a);
//            notification.setContent("ban vua dang ki thanh cong");
//            notification.setTitle("thong bao tai khoan");
//            notification.setStatus(0);
//            notificationRepository.save(notification);
//        } else {
//            int idPersonal = emailPersonal.getIdPersonal();
//            personalRepository.updatefbIDByEmail(fbID, idPersonal);
//        }
        return personalRepository.findByFbID(fbID);
    }

    @Override
    @Transactional
    public void updateActiveOff(int idPersonal) {
        personalRepository.updateActiveOff(idPersonal);
    }

    @Override
    @Transactional
    public void updateIdProject(String idProject, int idPersonal) {
        personalRepository.updateProject(idProject, idPersonal);

    }


    @Override
    public Personal register(String full_name, String phone, String email, String password) {
        Personal personal = new Personal();
//        String introducrCode = String.valueOf(StringUtils.Radom(111111, 99999999));
//        Personal personal9 = personalRepository.findIntroduceCode(introducrCode);
//        if (personal9 != null) {
//            introducrCode = String.valueOf(StringUtils.Radom(111111, 99999999));
//        }
//        personal.setIntroduceCodeUser(introducrCode);
//        personal.setFullName(full_name);
//        personal.setPhone(phone);
//        personal.setEmail(email);
//        personal.setPassword(password);
//        personalRepository.save(personal);
//
//        Personal personal1 = personalRepository.findByPhone(phone);
//        RankPersonal rankPersonal = new RankPersonal();
//        rankPersonal.setIdPersonal(personal1.getIdPersonal());
//        rankPersonal.setScore(0);
//        rankPersonal.setIdRank(1);
//        rankPersonal.setUpdateDate(DateUtils.parseTimeStamp(DateUtils.getCurrentTime()));
//        rankPersonalRespository.save(rankPersonal);
//
//        Personal personal2 = personalRepository.findByPhone(phone);
//        HistoryScore historyScore = new HistoryScore();
//        historyScore.setIdPersonal(personal2.getIdPersonal());
//        historyScore.setScore(0);
//        historyScore.setUpdateDate(DateUtils.parseTimeStamp(DateUtils.getCurrentTime()));
//        historyScoreRepository.save(historyScore);
//
//        Personal personal3 = personalRepository.findByPhone(phone);
//        Notification notification = new Notification();
//        notification.setIdPersonal(personal3.getIdPersonal());
//        notification.setContent("ban vua dang ki thanh cong");
//        notification.setTitle("thong bao tai khoan");
//        notification.setStatus(0);
//        notificationRepository.save(notification);
        return personal;
    }


    @Override
    @Transactional
    public void forgotPassword(String mail, int password) {
        System.out.println("==========aaa");
        mailservice.sendSimpleMail(mail, password);
    }

    @Override
    @Transactional
    public void updateAvatar(int id, String avatar) {
        personalRepository.updateAvatar(id, avatar);
    }

    @Override
    public String AutoGenCode() {
        String randomchuoi = UPPER_CASE + LOWER_CASE + NUMBER;
        String ketqua = "";
        for (int i = 0; i < 6; i++) {
            int temp = (int) Math.round(Math.random() * randomchuoi.length());
            ketqua += randomchuoi.charAt(temp);
        }
        return ketqua;
    }

    @Override
    @Transactional
    public void updateName(int id, String full_name) {
        personalRepository.updateName(id, full_name);
    }

    @Override
    @Transactional
    public void updateBirth(int id, int birthday) {
        entityManager.createNativeQuery("update tbl_personal p set p.birthday=:birthday where p.id_personal=:id").setParameter("birthday", birthday).setParameter("id", id).executeUpdate();
    }

    @Override
    @Transactional
    public void updateGender(int id, String gender) {
        personalRepository.updateGender(id, gender);
    }

    @Override
    @Transactional
    public void updateCard(int id, String cmnd) {

        personalRepository.updateCard(id, cmnd);
    }

    @Override
    public Personal findPersonalByEmailOrPhone(String user) {
        return personalRepository.findByPhoneOrEmail(user);
    }

    @Override
    public Personal findEmail(String email) {
       return personalRepository.findEmail(email);
    }

    @Override
    public Personal findBankAccount(String bankAccount) {
        return personalRepository.findByBankAccount(bankAccount);
    }

    @Override
    public Personal findByPhone(String phone) {
        return personalRepository.findByPhone(phone);
    }

    @Override
    @Transactional
    public void updatePersonalInfo(int id, String fullName, String avatar, String birthDay, String gender, String address, String email, String phone, String cmnd, String bankAccount, String bankBranch, String bankName, String imageCmnd) {
        personalRepository.changeInfoPersonal(id, fullName, avatar, birthDay, gender, address, email, phone, cmnd, bankAccount, bankBranch, bankName, imageCmnd);
    }

    //    @Override
//    @Transactional
//    public void updatePersonalInfo(int id, String fullName, String avatar, String birthDay, String gender, String address, String email, String phone, String cmnd, String bankAccount, String bankBranch, String bankName) {
//        personalRepository.changeInfoPersonal(id, fullName, avatar, birthDay, gender, address, email, phone, cmnd, bankAccount, bankBranch, bankName);
//    }
    @Override
    public String findPassword(String phone) {
//        List<Object[]> password = entityManager.createQuery("select p.password from Personal p where p.phone=:phone and (p.status =1 or p.status=0)").set
        return personalRepository.findPassword(phone);
    }

    @Override
    public String findPasswordPersonal(String phone) {
        return personalRepository.findPasswordPersonal(phone);
    }

    @Override
    public Personal findByCmnd(String cmnd) {
        return personalRepository.findByCmnd(cmnd);
    }

    @Override
    @Transactional
    public void updatePass(int id, String new_pass) {
        personalRepository.updatePassword(id, new_pass);
    }

    @Override
    @Transactional
    public void updateAccountBank(int id, String bank_account, String bank_name, String bank_branch) {
        personalRepository.updateAcountBank(id, bank_account, bank_name, bank_branch);
    }

}
