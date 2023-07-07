package com.hitex.halago.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.AboutUs;
import com.hitex.halago.model.AboutUsLanguage;
import com.hitex.halago.model.dao.AboutUs.AboutUsDao;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.AboutUsLanguageRepository;
import com.hitex.halago.repository.AboutUsRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.AboutUsService;
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
public class AboutUsController {
    @Autowired
    AboutUsService aboutUsService;
    @Autowired
    AboutUsRepository aboutUsRepository;
    @Autowired
    AboutUsLanguageRepository aboutUsLanguageRepository;

    @Autowired
    private JwtService jwtService;


    Logger logger = LoggerFactory.getLogger(AboutUsController.class);

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

    @RequestMapping(value = "/getAboutUs", method = RequestMethod.POST)
    public ResponseEntity<?> getListAppprove(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                ResponseBase responseBase = new ResponseBase();
                String language;
                if (jsonNode.has("language")) {
                    language = jsonNode.get("language").asText();
                    language = StringUtils.isEmpty(language) ? "vn" : language;
                    AboutUsDao aboutUsDao = aboutUsService.getAboutUs(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(aboutUsDao);
                } else {
                    language = null;
                    AboutUsDao aboutUsDao = aboutUsService.getAboutUs(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(aboutUsDao);
                }

                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateAboutUsAndVision", method = RequestMethod.POST)
    public ResponseEntity<?> updateAboutUsAndVision(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            if (baseRequest.getWsRequest() != null) {
                if (Constant.ADMIN.equals(role) || Constant.STAFF.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    AboutUs about = aboutUsRepository.findAbout(id);
                    AboutUsLanguage aboutLang = aboutUsLanguageRepository.findAboutLanguage(id);
                    String title = !jsonNode.has("title") ? about.getTitle() : jsonNode.get("title").asText();
                    String titleEn = !jsonNode.has("title_En") ? aboutLang.getTitleEn() : jsonNode.get("title_En").asText();
                    String content = !jsonNode.has("content") ? about.getTitle() : jsonNode.get("content").asText();
                    String contentEn = !jsonNode.has("content_En") ? aboutLang.getContentEn() : jsonNode.get("content_En").asText();
                    about.setTitle(title);
                    about.setContent(content);
                    aboutLang.setTitleEn(titleEn);
                    aboutLang.setContentEn(contentEn);
                    aboutUsRepository.save(about);
                    aboutUsLanguageRepository.save(aboutLang);
                    ResponseBase responseBase = new ResponseBase();
                    responseBase.setSiRes(null);
                    responseBase.setData(about);
                    responseData = new ResponseData(Constant.SUCCESS, "Cập nhật thành công", responseBase);

                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền update", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getVision", method = RequestMethod.POST)
    public ResponseEntity<?> getVision(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                ResponseBase responseBase = new ResponseBase();
                String language;
                if (jsonNode.has("language")) {
                    language = jsonNode.get("language").asText();
                    language = StringUtils.isEmpty(language) ? "vn" : language;
                    AboutUsDao aboutUsDao = aboutUsService.getVision(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(aboutUsDao);
                } else {
                    language=null;
                    AboutUsDao aboutUsDao = aboutUsService.getVision(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(aboutUsDao);
                }
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
