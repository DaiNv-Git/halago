package com.hitex.halago.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.DAO.introduce.IntroduceDao;
import com.hitex.halago.model.DAO.introduce.IntroduceId;
import com.hitex.halago.model.Introduce;
import com.hitex.halago.model.IntroduceLanguage;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.IntroduceLanguageRepository;
import com.hitex.halago.repository.IntroduceRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.IntroduceService;
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
public class IntroduceController {
    Logger logger = LoggerFactory.getLogger(IntroduceController.class);
    @Autowired
    IntroduceService introduceService;
    @Autowired
    IntroduceRepository introduceRepository;
    @Autowired
    IntroduceLanguageRepository introduceLanguageRepository;
    @Autowired
    private JwtService jwtService;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);


    @RequestMapping(value = "/getListIntroduce", method = RequestMethod.POST)
    public ResponseEntity<?> getListIntroduce(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
//                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
//                    String tilte = jsonNode.get("tilte").asText();
//                    String status = jsonNode.get("status").asText().replaceAll(".0","");
//                    Integer pageSize = jsonNode.get("pageSize").asInt();
//                    Integer pageNumber = jsonNode.get("pageNumber").asInt();
//                    if (StringUtils.isEmpty(status)) {
//                        status = "-1";
//                    }
//                    if (StringUtils.isEmpty(tilte)) {
//                        tilte = null;
//                    }
                ResponseBase responseBase = new ResponseBase();
                String language;
                if (jsonNode.has("language")) {
                    language = jsonNode.get("language").asText();
                    language = StringUtils.isEmpty(language) ? "en" : language;
                    IntroduceDao introduceDaos = introduceService.getListIntroducePortal(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(introduceDaos);
                    responseBase.setTotal(0);
                } else {
                    language = "en";
                    IntroduceDao introduceDaos = introduceService.getListIntroduce(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(introduceDaos);
                    responseBase.setTotal(0);
                }

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

    @RequestMapping(value = "/updateIntroduce", method = RequestMethod.POST)
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
                    String language;
                    if (jsonNode.has("language")) {
                        language = jsonNode.get("language").asText();
                        language = StringUtils.isEmpty(language) ? "en" : language;
                    } else {
                        language = "en";
                    }
                    Introduce introduce = introduceRepository.findIntroduceById(id);
                    IntroduceLanguage lang = introduceLanguageRepository.findIntroLanguage(id,language);
                    if (introduce.getType() == Constant.HEADER) {
                        updateLanguageIntroduce(jsonNode, introduce, lang);
                        introduce.setImgSecond(setImgSeo(jsonNode.get("imgSecond").get("img").asText(),jsonNode.get("imgSecond").get("title").asText()));
                        introduce.setImgThird(setImgSeo(jsonNode.get("imgThird").get("img").asText(),jsonNode.get("imgThird").get("title").asText()));
                        introduce.setImgFour(setImgSeo(jsonNode.get("imgFour").get("img").asText(),jsonNode.get("imgFour").get("title").asText()));
                        introduce.setImgFive(setImgSeo(jsonNode.get("imgFive").get("img").asText(),jsonNode.get("imgFive").get("title").asText()));
                        introduce.setContent(jsonNode.get("content").asText());
                        introduce.setContentSecond(jsonNode.get("contentSecond").asText());
                        introduce.setStatus(jsonNode.get("status").asInt());
                        lang.setContentSecond(jsonNode.get("contentSecond_en").asText());
                    } else if (introduce.getType() == Constant.BODY) {
                        updateLanguageIntroduce(jsonNode, introduce, lang);
                        introduce.setContent(jsonNode.get("content").asText());
                        introduce.setStatus(jsonNode.get("status").asInt());
                    } else if (introduce.getType() == Constant.REASON) {
                        //set value introduce
                        introduce.setImg(setImgSeo(jsonNode.get("img").get("img").asText(),jsonNode.get("img").get("title").asText()));
                        introduce.setTitle(jsonNode.get("title").asText());
                        introduce.setDescription(jsonNode.get("description").asText());
                        introduce.setContentFirst(jsonNode.get("contentFirst").asText());
                        introduce.setContentSecond(jsonNode.get("contentSecond").asText());
                        introduce.setStatus(jsonNode.get("status").asInt());
                        //set value language
                        lang.setTitle(jsonNode.get("title_en").asText());
                        lang.setDescription(jsonNode.get("description_en").asText());
                        lang.setContentFirst(jsonNode.get("contentFirst_en").asText());
                        lang.setContentSecond(jsonNode.get("contentSecond_en").asText());
                    } else if (introduce.getType() == Constant.FOOTER) {
                        introduce.setImg(setImgSeo(jsonNode.get("img").get("img").asText(),jsonNode.get("img").get("title").asText()));
                        introduce.setContent(jsonNode.get("content").asText());
                        introduce.setStatus(jsonNode.get("status").asInt());
                        introduce.setTotalInfluencer(jsonNode.get("totalInfluencer").asText());
                        introduce.setTotalKols(jsonNode.get("totalKols").asText());
                        introduce.setTotalStar(jsonNode.get("totalStar").asText());

                        lang.setContent(jsonNode.get("content_en").asText());
                    } else if (introduce.getType() == Constant.BRAND_DEPLOYMENT) {
                        lang.setTitle(jsonNode.get("title_en").asText());
                        lang.setDescription(jsonNode.get("description_en").asText());

                        introduce.setTitle(jsonNode.get("title").asText());
                        introduce.setDescription(jsonNode.get("description").asText());
                        introduce.setStatus(jsonNode.get("status").asInt());
                        introduce.setImg(setImgSeo(jsonNode.get("img").get("img").asText(),jsonNode.get("img").get("title").asText()));
                        introduce.setImgSecond(setImgSeo(jsonNode.get("imgSecond").get("img").asText(),jsonNode.get("imgSecond").get("title").asText()));
                        introduce.setImgThird(setImgSeo(jsonNode.get("imgThird").get("img").asText(),jsonNode.get("imgThird").get("title").asText()));
                        introduce.setImgFour(setImgSeo(jsonNode.get("imgFour").get("img").asText(),jsonNode.get("imgFour").get("title").asText()));
                        introduce.setImgFive(setImgSeo(jsonNode.get("imgFive").get("img").asText(),jsonNode.get("imgFive").get("title").asText()));
                        introduce.setImgSix(setImgSeo(jsonNode.get("imgSix").get("img").asText(),jsonNode.get("imgSix").get("title").asText()));
                        introduce.setImgSeven(setImgSeo(jsonNode.get("imgSeven").get("img").asText(),jsonNode.get("imgSeven").get("title").asText()));
                        introduce.setImgEight(setImgSeo(jsonNode.get("imgEight").get("img").asText(),jsonNode.get("imgEight").get("title").asText()));
                        introduce.setImgNine(setImgSeo(jsonNode.get("imgNine").get("img").asText(),jsonNode.get("imgNine").get("title").asText()));
                        introduce.setImgTen(setImgSeo(jsonNode.get("imgTen").get("img").asText(),jsonNode.get("imgTen").get("title").asText()));
                        introduce.setImgEleven(setImgSeo(jsonNode.get("imgEleven").get("img").asText(),jsonNode.get("imgEleven").get("title").asText()));
                        introduce.setImgTwelve(setImgSeo(jsonNode.get("imgTwelve").get("img").asText(),jsonNode.get("imgTwelve").get("title").asText()));
                    }else{
                        introduce.setTitle(jsonNode.get("title").asText());
                        introduce.setContent(jsonNode.get("content").asText());
                        lang.setTitle(jsonNode.get("title_en").asText());
                        lang.setContent(jsonNode.get("content_en").asText());
                        introduce.setStatus(jsonNode.get("status").asInt());
                    }
                    introduceRepository.save(introduce);
                    introduceLanguageRepository.save(lang);
                    responseData = new ResponseData(Constant.SUCCESS, "Cập nhật thông tin thành công", new ResponseBase(null, introduce));
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

    private void updateLanguageIntroduce(JsonNode jsonNode, Introduce introduce, IntroduceLanguage lang) {
        lang.setTitle(jsonNode.get("title_en").asText());
        lang.setDescription(jsonNode.get("description_en").asText());
        lang.setContent(jsonNode.get("content_en").asText());

        introduce.setTitle(jsonNode.get("title").asText());
        introduce.setDescription(jsonNode.get("description").asText());
        introduce.setImg(jsonNode.get("img").asText());
    }

private String setImgSeo(String img,String title){
        StringBuilder builder = new StringBuilder();
        builder.append(img).append("#").append(title);
    return builder.toString();
}

}
