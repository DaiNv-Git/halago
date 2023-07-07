package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.Approve;
import com.hitex.halago.model.dao.ApproveDao;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.ApproveRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.ApproveService;
import com.hitex.halago.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
public class ApproveController {
    Logger logger = LoggerFactory.getLogger(ApproveController.class);


    @Autowired
    ApproveService approveService;
    @Autowired
    ApproveRepository approveRepository;

    @Autowired
    private JwtService jwtService;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

    @RequestMapping(value = "/getListAppprove", method = RequestMethod.POST)
    public ResponseEntity<?> getListAppprove(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role) ||  Constant.STAFF.equals(role)) {
                        int idCampaign = jsonNode.get("idCampaign").asInt();
                        Integer pageSize = jsonNode.get("pageSize").asInt();
                        Integer pageNumber = jsonNode.get("pageNumber").asInt();
                        List<ApproveDao> brands = approveService.getListApprove(idCampaign, pageSize, pageNumber);
                        ResponseBase responseBase = new ResponseBase();
                        responseBase.setSiRes(null);
                        responseBase.setData(brands);
                        responseBase.setTotal(approveRepository.getListApprove(idCampaign));
                        responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);

                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @RequestMapping(value = "/approveInfluencer", method = RequestMethod.POST)
    public ResponseEntity<?> approveInfluencer(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    int status = jsonNode.get("status").asInt();
                    approveService.approveInfluencer(id,status);
                    responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null,true));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền phê duyệt", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/approveAll", method = RequestMethod.POST)
    public ResponseEntity<?> approveAll(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
                    int idCampaign = jsonNode.get("idCampaign").asInt();
                    int status = jsonNode.get("status").asInt();
                    approveService.aprroveAll(status,idCampaign);
                    responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null,true));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền phê duyệt", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @RequestMapping(value = "/insertApprove", method = RequestMethod.POST)
    public ResponseEntity<?> insertApprove(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                    int idCampaign = jsonNode.get("idCampaign").asInt();
                    int idInfluencer = jsonNode.get("idInfluencer").asInt();
                    Approve checkApprove = approveRepository.checkApprove(idCampaign,idInfluencer);
                    if(ObjectUtils.isEmpty(checkApprove)) {
                        Approve approve = new Approve();
                        approve.setIdCampaign(idCampaign);
                        approve.setIdInfluencer(idInfluencer);
                        approve.setStatus(Constant.WAITING_APPROVE);
                        approve.setCreated(new Date());
                        approveRepository.save(approve);
                    }
                    responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null,true));
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

}
