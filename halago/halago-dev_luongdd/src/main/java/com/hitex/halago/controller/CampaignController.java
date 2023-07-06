package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.Campaign;
import com.hitex.halago.model.DAO.CampaignDao;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.CampaignRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.CampaignService;
import com.hitex.halago.utils.DateUtils;
import com.hitex.halago.utils.FileCommon;
import com.hitex.halago.utils.RequestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.hitex.halago.config.Constant.valiemail;

@RestController
public class CampaignController {
    Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    private CampaignService campaignService;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private JwtService jwtService;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

    @RequestMapping(value = "/findCampaignById", method = RequestMethod.POST)
    public ResponseEntity<?> findCampaignById(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("id") != null) {
                    int id = jsonNode.get("id").asInt();
                    CampaignDao campaign = campaignService.findCampaign(id);
                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, campaign));
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

//    @RequestMapping(value = "/findCampaignByName", method = RequestMethod.POST)
//    public ResponseEntity<?> findCampaignByName(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
//                    if (jsonNode.get("id") != null) {
//                        String name = jsonNode.get("name").asText();
//                        CampaignDao campaign = campaignService.findCampaignByName(name);
//                        responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, campaign));
//                    }
//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền tìm kiếm", null);
//                }
//            }
//        } catch (Exception e) {
//            logger.info(e.getMessage(), e.getCause());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

    @RequestMapping(value = "/getListCampaign", method = RequestMethod.POST)
    public ResponseEntity<?> getListCampaign(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
//                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
                String name = jsonNode.get("name").asText();
                Integer idInfluencer = !jsonNode.has("idInfluencer") ? 0 :  jsonNode.get("idInfluencer").asInt();
                String status = jsonNode.get("status").asText();
                Integer pageSize = jsonNode.get("pageSize").asInt();
                Integer pageNumber = jsonNode.get("pageNumber").asInt();
                if (org.apache.commons.lang3.StringUtils.isEmpty(status)) {
                    status = "-1";
                }
                if (StringUtils.isEmpty(name)) {
                    name = null;
                }
                List<CampaignDao> campaigns = campaignService.getListCampaign(name, Integer.parseInt(status), pageSize, pageNumber , idInfluencer);
                ResponseBase responseBase = new ResponseBase();
                responseBase.setSiRes(null);
                responseBase.setData(campaigns);
                responseBase.setTotal(campaignRepository.getListCampaign(name, Integer.parseInt(status)));
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
//                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/insertCampaign", method = RequestMethod.POST)
    public ResponseEntity<?> insertCampaign(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Thêm chiến dịch thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
                    Campaign campaign = new Campaign();
                    campaign.setBrandName(jsonNode.get("brandName").asText());
                    campaign.setCampaignName(jsonNode.get("campaignName").asText());
                    campaign.setDateStart(jsonNode.get("dateStart").asText());
                    campaign.setDateEnd(jsonNode.get("dateEnd").asText());
                    campaign.setDescription(jsonNode.get("description").asText());
                    campaign.setContent(jsonNode.get("content").asText());
                    campaign.setRewards(jsonNode.get("rewards").asText());
                    campaign.setStatus(jsonNode.get("status").asInt());
                    campaign.setCreated(new Date());
                    campaign.setImg(jsonNode.get("img").asText());
                    campaign.setIdBrand(jsonNode.get("idBrand").asInt());
                    campaign.setIndustryId(jsonNode.get("industryId").asInt());
                    String imgProduct = com.hitex.halago.utils.StringUtils.convertHashTagtoString(com.hitex.halago.utils.StringUtils.getArrayStringJson(jsonNode, "imgProduct")).trim();
                    if (imgProduct.endsWith(",")) {
                        imgProduct = imgProduct.substring(0, imgProduct.length() - 1) + "";
                    }
                    campaign.setImgProduct(imgProduct);
                    campaignService.insertCampaign(campaign);
                    responseData = new ResponseData(Constant.SUCCESS, "Thêm chiến dịch thành công", new ResponseBase(null, campaign));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm chiến dịch", null);
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateCampaign", method = RequestMethod.POST)
    public ResponseEntity<?> updateCampaign(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Cập nhật chiến dịch thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
                    Campaign campaign = campaignRepository.findCampaignById(jsonNode.get("id").asInt());
                    campaign.setBrandName(jsonNode.get("brandName").asText());
                    campaign.setCampaignName(jsonNode.get("campaignName").asText());
                    campaign.setDateStart(jsonNode.get("dateStart").asText());
                    campaign.setDateEnd(jsonNode.get("dateEnd").asText());
                    campaign.setDescription(jsonNode.get("description").asText());
                    campaign.setContent(jsonNode.get("content").asText());
                    campaign.setRewards(jsonNode.get("rewards").asText());
//                    campaign.setStatus(jsonNode.get("status").asInt());
                    campaign.setImg(jsonNode.get("img").asText());
                    campaign.setIndustryId(jsonNode.get("industryId").asInt());
                    String imgProduct = com.hitex.halago.utils.StringUtils.convertHashTagtoString(com.hitex.halago.utils.StringUtils.getArrayStringJson(jsonNode, "imgProduct")).trim();
                    if (imgProduct.endsWith(",")) {
                        imgProduct = imgProduct.substring(0, imgProduct.length() - 1) + "";
                    }
                    campaign.setImgProduct(imgProduct);
                    campaignService.updateCampaign(campaign);
                    responseData = new ResponseData(Constant.SUCCESS, "Cập nhật chiến dịch thành công", new ResponseBase(null, campaign));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền cập nhật chiến dịch", null);
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/deleteCampaign", method = RequestMethod.POST)
    public ResponseEntity<?> deleteCampaign(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
                    if (jsonNode.get("id") != null) {
                        int id = jsonNode.get("id").asInt();
                        int status = campaignService.deleteCampaign(id);
                        if (status == Constant.SUCCESS) {
                            responseData = new ResponseData(Constant.SUCCESS, "Xoá chiến dịch Thành công", new ResponseBase(null, true));
                        } else {
                            responseData = new ResponseData(Constant.FAILED, "Xoá chiến dịch thất bại", new ResponseBase(null, true));
                        }
                    }
                } else {
                    responseData = new ResponseData(0, "Bạn không có quyền xoá", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getListCampaignByBrand", method = RequestMethod.POST)
    public ResponseEntity<?> getListCampaignByBrand(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role) || Constant.STAFF.equals(role)) {
                    String status = String.valueOf(jsonNode.get("status").asText());
                    if (org.apache.commons.lang3.StringUtils.isEmpty(status)) {
                        status = "-1";
                    }
                    int idBrand = jsonNode.get("idBrand").asInt();
                    Integer pageSize = jsonNode.get("pageSize").asInt();
                    Integer pageNumber = jsonNode.get("pageNumber").asInt();
                    List<CampaignDao> campaigns = campaignService.getListCampaignByBrand(idBrand, pageSize, pageNumber,Integer.parseInt(status.replace(".0","")));
                    ResponseBase responseBase = new ResponseBase();
                    responseBase.setSiRes(null);
                    responseBase.setData(campaigns);
                    responseBase.setTotal(campaignRepository.getListCampaignByBrand(idBrand));
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
}
