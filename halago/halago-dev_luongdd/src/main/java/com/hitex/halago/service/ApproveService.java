package com.hitex.halago.service;

import com.hitex.halago.model.Approve;
import com.hitex.halago.model.DAO.ApproveDao;

import java.util.List;

public interface ApproveService {
    List<ApproveDao> getListApprove(int idCampaign,Integer pageSize, Integer pageNumber);
    int approveInfluencer(int id, int status);
    void aprroveAll(int status, int idCampaign);
    Approve insertApprove(Approve approve);
}
