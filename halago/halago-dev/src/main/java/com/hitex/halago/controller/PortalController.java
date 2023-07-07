package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.dao.footer.FooterLanguage;
import com.hitex.halago.model.Footer;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.FooterLanguageRepository;
import com.hitex.halago.repository.FooterRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.FooterService;
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
import java.util.List;

@RestController
public class PortalController {
    Logger logger = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    FooterRepository footerRepository;
    @Autowired
    FooterLanguageRepository footerLanguageRepository;
    @Autowired
    FooterService footerService;
    @Autowired
    private JwtService jwtService;

    ResponseData responseData = new ResponseData(1, "Có lỗi khi gọi api", null);

    @RequestMapping(value = "/getListFooter", method = RequestMethod.POST)
    public ResponseEntity<?> getListFooter(InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);

            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                ResponseBase responseBase = new ResponseBase();
                String language;
                if (jsonNode.has("language")) {
                    language = jsonNode.get("language").asText();
                    language = StringUtils.isEmpty(language) ? "en" : language;
                    List<FooterLanguage> footers = footerService.getFooterPortal(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(footers);
                    responseBase.setTotal(footers.size());
                }else{
                    List<FooterLanguage> footers = footerService.getFooterCMS();
                    responseBase.setSiRes(null);
                    responseBase.setData(footers);
                    responseBase.setTotal(footers.size());
                }
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/findFooterById", method = RequestMethod.POST)
    public ResponseEntity<?> findFooterById(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (Constant.INFLUENCER.equals(role) || Constant.ADMIN.equals(role)) {
                    if (jsonNode.get("id") != null) {
                        int id = jsonNode.get("id").asInt();
                        Footer footer = footerRepository.findFooterById(id);
                        responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, footer));
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

//    @RequestMapping(value = "/insertFooter", method = RequestMethod.POST)
//    public ResponseEntity<?> insertFunctionApi(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//            if (baseRequest.getWsRequest() != null) {
//                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
//                    String address = jsonNode.get("address").asText();
//                    String email = jsonNode.get("email").asText();
//                    String hotline = jsonNode.get("hotline").asText();
//                    if (!StringUtils.isEmpty(address) && !StringUtils.isEmpty(email) && !StringUtils.isEmpty(hotline)) {
//                        Footer footer = new Footer();
//                        footer.setAddress(address);
//                        footer.setEmail(email);
//                        footer.setHotline(hotline);
//                        footerRepository.save(footer);
//                        responseData = new ResponseData(Constant.SUCCESS, "Thêm mới footer thành công", new ResponseBase(null, footer));
//                    }
//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm mới Footer", null);
//                }
//            } else {
//                responseData = new ResponseData(Constant.SUCCESS, "Dữ liệu đầu vào không hợp lệ", new ResponseBase(null, true));
//            }
//        } catch (
//                Exception e) {
//            logger.info(e.getMessage(), e);
//        }
//        return ResponseEntity.status(HttpStatus.OK).
//
//                body(responseData);
//
//    }

    @RequestMapping(value = "/updateFooter", method = RequestMethod.POST)
    public ResponseEntity<?> updateFooter(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                        Footer footer = footerRepository.findFooterById(id);
                        com.hitex.halago.model.FooterLanguage lang = footerLanguageRepository.findFooterLanguage(id);
                        String address = !jsonNode.has("address") ? footer.getAddress() : jsonNode.get("address").asText();
                        String address_en = !jsonNode.has("address_en") ? lang.getAddress() : jsonNode.get("address_en").asText();
                        String email = !jsonNode.has("email") ? footer.getEmail() : jsonNode.get("email").asText();
                        String hotline = !jsonNode.has("hotline") ? footer.getHotline() : jsonNode.get("hotline").asText();
                        String fb = !jsonNode.has("facebook") ? footer.getFacebook() : jsonNode.get("facebook").asText();
                        String zalo = !jsonNode.has("zalo") ? footer.getZalo() : jsonNode.get("zalo").asText();
                        String company = !jsonNode.has("company") ? footer.getCompany() : jsonNode.get("company").asText();
                        String youtube = !jsonNode.has("youtube") ? footer.getYoutube() : jsonNode.get("youtube").asText();
                        String tiktok = !jsonNode.has("tiktok") ? footer.getTiktok() : jsonNode.get("tiktok").asText();
                        String instagram = !jsonNode.has("instagram") ? footer.getInstagram() : jsonNode.get("instagram").asText();
                        String taxCode = !jsonNode.has("taxCode") ? footer.getTaxCode() : jsonNode.get("taxCode").asText();
                        footer.setAddress(address);
                        lang.setAddress(address_en);
                        footer.setEmail(email);
                        footer.setHotline(hotline);
                        footer.setFacebook(fb);
                        footer.setZalo(zalo);
                        footer.setCompany(company);
                        footer.setYoutube(youtube);
                        footer.setTiktok(tiktok);
                        footer.setInstagram(instagram);
                        footer.setTaxCode(taxCode);
                        footerRepository.save(footer);
                        footerLanguageRepository.save(lang);
                        responseData = new ResponseData(Constant.SUCCESS, "Cập nhật footer thành công", new ResponseBase(null, footer));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm mới Footer", null);
                }
            } else {
                responseData = new ResponseData(Constant.SUCCESS, "Dữ liệu đầu vào không hợp lệ", new ResponseBase(null, true));
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

//    @RequestMapping(value = "/deleteFooter", method = RequestMethod.POST)
//    public ResponseEntity<?> deleteFunctionApi(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Có lỗi khi xóa Function", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//            if (baseRequest.getWsRequest() != null) {
//                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
//                    int id = jsonNode.get("id").asInt();
//                    Footer footer = footerRepository.findFooterById(id);
//                    footerRepository.delete(footer);
//                    responseData = new ResponseData(Constant.SUCCESS, "Xóa thành công", new ResponseBase(null, true));
//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền Xóa", null);
//                }
//            }
//        } catch (Exception e) {
//            logger.info(e.getMessage(), e);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }


}
