package com.hitex.halago.service.impl;


import com.hitex.halago.config.Constant;
import com.hitex.halago.model.Approve;
import com.hitex.halago.model.dao.ApproveDao;
import com.hitex.halago.repository.ApproveRepository;
import com.hitex.halago.service.ApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApproveServiceImpl implements ApproveService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    ApproveRepository approveRepository;

    @Override
    public List<ApproveDao> getListApprove(int idCampaign, Integer pageSize, Integer pageNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select approve.id,approve.idCampaign,approve.idInfluencer,approve.status,approve.created,influ.name  ");
        builder.append("from Approve approve, Influencer influ ");
        builder.append("where approve.idCampaign=:idCampaign and influ.id = approve.idInfluencer");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("idCampaign", idCampaign);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Object[]> approveList = query.getResultList();
        List<ApproveDao> approveDaos = new ArrayList<>();
        for (Object[] approve : approveList) {
            ApproveDao approveDao = new ApproveDao();
            approveDao.setId((Integer) approve[0]);
            approveDao.setIdCampaign((Integer) approve[1]);
            approveDao.setIdInfluencer((Integer) approve[2]);
            approveDao.setStatus((Integer) approve[3]);
            approveDao.setCreated((Date) approve[4]);
            approveDao.setInfluencerName(String.valueOf(approve[5]));
            if (approveDao.getStatus() == Constant.APPROVE) {
                approveDao.setStatusName("Đã Phê duyệt");
            } else if (approveDao.getStatus() == Constant.WAITING_APPROVE) {
                approveDao.setStatusName("Chờ Phê duyệt");
            } else {
                approveDao.setStatusName("Từ chối");
            }
            approveDaos.add(approveDao);
        }
        return approveDaos;
    }

    @Override
    public int approveInfluencer(int id, int status) {
        Approve approve = approveRepository.findApprove(id);
        if (approve != null) {
            approve.setStatus(status);
            approveRepository.save(approve);
            return Constant.SUCCESS;
        } else {
            return Constant.FAILED;
        }
    }

    @Override
    public void aprroveAll(int status, int idCampaign) {
        approveRepository.updateStatusApprove(idCampaign, status);
    }

    @Override
    public Approve insertApprove(Approve approve) {
        return approveRepository.save(approve);
    }
}
