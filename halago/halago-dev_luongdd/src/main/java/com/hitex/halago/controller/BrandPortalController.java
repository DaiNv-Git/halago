package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.dao.brandPortal.BrandPortal;
import com.hitex.halago.model.dao.brandPortal.BrandPortalDao;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.BrandPortalLanguageRepository;
import com.hitex.halago.repository.BrandPortalRepository;
import com.hitex.halago.repository.CampaignRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.BrandPortalService;
import com.hitex.halago.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
public class BrandPortalController {
    Logger logger = LoggerFactory.getLogger(BookKolsController.class);
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BrandPortalService brandPortalService;
    @Autowired
    private BrandPortalRepository brandPortalRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private BrandPortalLanguageRepository brandPortalLanguageRepository;
    ResponseData responseData = new ResponseData(1, "Không có dữ liệu", null);

    @RequestMapping(value = "/findBrandPortalById", method = RequestMethod.POST)
    public ResponseEntity<?> findIntroduceById(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    if (jsonNode.get("id") != null) {
                        int id = jsonNode.get("id").asInt();
                        BrandPortal brandPortal = brandPortalService.findBrandPortal(id);
                        responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, brandPortal));
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền tìm kiếm", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getListBrandPortal", method = RequestMethod.POST)
    public ResponseEntity<?> getListIntroduce(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ResponseBase responseBase = new ResponseBase();
            String language;
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.has("language")) {
                    language = jsonNode.get("language").asText();
                    language = StringUtils.isEmpty(language) ? "en" : language;
                    BrandPortalDao brandPortalDao = brandPortalService.getListBrandPortal(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(brandPortalDao);
                    responseBase.setTotal(0);
                } else {
                    language = "en";
                    BrandPortalDao brandPortalDao = brandPortalService.getListBrandPortalCMS();
                    responseBase.setSiRes(null);
                    responseBase.setData(brandPortalDao);
                    responseBase.setTotal(0);
                }
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateBrandPortal", method = RequestMethod.POST)
    public ResponseEntity<?> updateIntroduce(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Cập nhật thông tin thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    String title;
                    String title_en;
                    com.hitex.halago.model.BrandPortal brandPortal = brandPortalRepository.findBrandPortalById(id);
                    com.hitex.halago.model.BrandPortalLanguage brandPortalLanguage = brandPortalLanguageRepository.findBrandPortalLanguage(id);
                    if (jsonNode.has("title")) {
                        title = jsonNode.get("title").asText();
                    } else {
                        title = "";
                    }
                    if (jsonNode.has("title_en")) {
                        title_en = jsonNode.get("title_en").asText();
                    } else {
                        title_en = "";
                    }
                    brandPortal.setTitle(title);
                    brandPortal.setDescription(jsonNode.get("description").asText());
                    brandPortalLanguage.setTitle(title_en);
                    brandPortalLanguage.setDescription(jsonNode.get("description_en").asText());
                    brandPortal.setImg(jsonNode.get("img").asText());
                    brandPortal.setTitleSeo(!jsonNode.has("titleSeo") ? brandPortal.getTitleSeo() : jsonNode.get("titleSeo").asText());
                  if (Constant.BODY == brandPortal.getType()) {
                        brandPortal.setTotalLike(jsonNode.get("totalLike").asText());
                        brandPortal.setTotalComment(jsonNode.get("totalComment").asText());
                        brandPortal.setTotalShare(jsonNode.get("totalShare").asText());
                    } else if (Constant.FOOTER == brandPortal.getType()) {
                        brandPortal.setCusName(jsonNode.get("cusName").asText());
                        brandPortal.setPosition(jsonNode.get("position").asText());
                        brandPortalLanguage.setCusName(jsonNode.get("cusName_en").asText());
                        brandPortalLanguage.setPosition(jsonNode.get("position_en").asText());
                    }
                    brandPortalRepository.save(brandPortal);
                    brandPortalLanguageRepository.save(brandPortalLanguage);
                    responseData = new ResponseData(Constant.SUCCESS, "Cập nhật thông tin thành công", new ResponseBase(null, brandPortal));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm thông tin", null);
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/deleteBrandPortal", method = RequestMethod.POST)
    public ResponseEntity<?> deleteIntroduce(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    if (jsonNode.get("id") != null) {
                        int id = jsonNode.get("id").asInt();
                        com.hitex.halago.model.BrandPortal brandPortal = brandPortalRepository.findBrandPortalById(id);
                        brandPortal.setStatus(Constant.inactive);
                        brandPortalRepository.save(brandPortal);
                        responseData = new ResponseData(Constant.SUCCESS, "Xoá thông tin Thành công", new ResponseBase(null, true));

                    }
                } else {
                    responseData = new ResponseData(0, "Bạn không có quyền xóa", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
