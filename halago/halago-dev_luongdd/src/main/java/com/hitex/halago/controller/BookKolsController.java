package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.BookKols;
import com.hitex.halago.model.BookKolsLanguage;
import com.hitex.halago.model.dao.bookKols.BookKolsDao;
import com.hitex.halago.model.dao.bookKols.KolsDao;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.BookKolsLanguageRepository;
import com.hitex.halago.repository.KolsRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.BookKolsService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class BookKolsController {
    Logger logger = LoggerFactory.getLogger(BookKolsController.class);

    @Autowired
    private JwtService jwtService;
    @Autowired
    private BookKolsService bookKolsService;
    @Autowired
    private KolsRepository kolsRepository;
    @Autowired
    private BookKolsLanguageRepository bookKolsLanguageRepository;
    ResponseData responseData = new ResponseData(1, "Không có dữ liệu", null);

    @RequestMapping(value = "/findBookKolsById", method = RequestMethod.POST)
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
                        String language;
                        if (jsonNode.has("language")) {
                            language = jsonNode.get("language").asText();
                            language = StringUtils.isEmpty(language) ? "en" : language;
                        } else {
                            language = "en";
                        }
                        BookKolsDao bookKolsDao = bookKolsService.findKols(id, language);
                        responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, bookKolsDao));
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

    @RequestMapping(value = "/getListBookKols", method = RequestMethod.POST)
    public ResponseEntity<?> getListIntroduce(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            if (baseRequest.getWsRequest() != null) {
                ResponseBase responseBase = new ResponseBase();
                String language;
                if (jsonNode.has("language")) {
                    language = jsonNode.get("language").asText();
                    language = StringUtils.isEmpty(language) ? "vn" : language;
                    KolsDao kolsDao = bookKolsService.getListKolsPortal(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(kolsDao);
                    responseBase.setTotal(0);
                } else {
                    language = "en";
                    KolsDao kolsDao = bookKolsService.getListKols(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(kolsDao);
                    responseBase.setTotal(0);
                }

                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateBookKols", method = RequestMethod.POST)
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
                    BookKols bookKols = kolsRepository.findBookKolsById(id);
                    List<BookKolsLanguage> lang = bookKolsLanguageRepository.findBookKolsLanguageV2(id);
                    HashMap<String,BookKolsLanguage> bookKolsLanguageHashMap = new HashMap<>();
                    for (BookKolsLanguage bookKolsLanguage : lang) {
                        bookKolsLanguageHashMap.put(bookKolsLanguage.getLanguageKey(),bookKolsLanguage);
                    }
                    ArrayList<String> listBanner = new ArrayList<>();
                    if (Constant.HEADER == bookKols.getType()) {
                        String banner = com.hitex.halago.utils.StringUtils.convertHashTagtoStringSeoImg(com.hitex.halago.utils.StringUtils.getArrayStringJson(jsonNode, "banner")).trim();
                        if (banner.endsWith(",")) {
                            banner = banner.substring(0, banner.length() - 1) + "";
                        }
//                        for (JsonNode banner :jsonNode.withArray("banner")) {
//                          String strBanner = com.hitex.halago.utils.StringUtils.setImgSeo(banner.get("img").asText(),banner.get("title").asText());
//                            listBanner.add(strBanner);
//                        }
//                        String banner = com.hitex.halago.utils.StringUtils.convertHashTagtoString(listBanner);
                        bookKols.setBanner(banner);
                    } else if (Constant.BODY == bookKols.getType()) {
                        bookKols= updateKols(jsonNode, bookKols, bookKolsLanguageHashMap);
                        bookKols.setPoster(jsonNode.get("poster").get("img").asText()+"#"+jsonNode.get("poster").get("title").asText());
                    } else if (Constant.FOOTER == bookKols.getType()) {
                        bookKols=  updateKols(jsonNode, bookKols, bookKolsLanguageHashMap);
                        String img = com.hitex.halago.utils.StringUtils.convertHashTagtoStringSeoImg(com.hitex.halago.utils.StringUtils.getArrayStringJson(jsonNode, "img")).trim();
                        if (img.endsWith(",")) {
                            img = img.substring(0, img.length() - 1) + "";
                        }
                        bookKols.setImg(img);
                        bookKols.setPoster(jsonNode.get("poster").get("img").asText()+"#"+jsonNode.get("poster").get("title").asText());
                    } else if (Constant.KOLS_MARKETING == bookKols.getType()) {
                        bookKols.setTitle(jsonNode.get("title").asText());
                        bookKols.setDescription(jsonNode.get("description").asText());
                        bookKolsLanguageHashMap.get(Constant.LANGUAGE_EN).setTitle(jsonNode.get("title_en").asText());
                        bookKolsLanguageHashMap.get(Constant.LANGUAGE_EN).setDescription(jsonNode.get("description_en").asText());
                        bookKolsLanguageHashMap.get(Constant.LANGUAGE_VN).setTitle(jsonNode.get("title").asText());
                        bookKolsLanguageHashMap.get(Constant.LANGUAGE_VN).setDescription(jsonNode.get("description").asText());
                    } else {
                        bookKols.setTitle(jsonNode.get("title").asText());
                        bookKols.setDescription(jsonNode.get("description").asText());
                        bookKolsLanguageHashMap.get(Constant.LANGUAGE_EN).setTitle(jsonNode.get("title_en").asText());
                        bookKolsLanguageHashMap.get(Constant.LANGUAGE_EN).setDescription(jsonNode.get("description_en").asText());
                        bookKolsLanguageHashMap.get(Constant.LANGUAGE_VN).setTitle(jsonNode.get("title").asText());
                        bookKolsLanguageHashMap.get(Constant.LANGUAGE_VN).setDescription(jsonNode.get("description").asText());
                    }
                    kolsRepository.save(bookKols);
                    bookKolsLanguageRepository.save(bookKolsLanguageHashMap.get(Constant.LANGUAGE_VN));
                    bookKolsLanguageRepository.save(bookKolsLanguageHashMap.get(Constant.LANGUAGE_EN));
                    responseData = new ResponseData(Constant.SUCCESS, "Cập nhật thông tin thành công", new ResponseBase(null, bookKols));
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

    private BookKols updateKols(JsonNode jsonNode, BookKols bookKols, HashMap<String,BookKolsLanguage> lang) {
        lang.get(Constant.LANGUAGE_EN).setTitle(jsonNode.get("title_en").asText());
        lang.get(Constant.LANGUAGE_EN).setDescription(jsonNode.get("description_en").asText());
        lang.get(Constant.LANGUAGE_VN).setTitle(jsonNode.get("title").asText());
        lang.get(Constant.LANGUAGE_VN).setDescription(jsonNode.get("description").asText());
        bookKols.setTitle(jsonNode.get("title").asText());
        bookKols.setDescription(jsonNode.get("description").asText());
//        bookKols.setPoster(jsonNode.get("poster").get("img").asText()+"#"+jsonNode.get("poster").get("title").asText());
        bookKols.setPoster(jsonNode.get("poster").asText()+"#a");
        bookKols.setTotalRegister(jsonNode.get("totalRegister").asText());
        bookKols.setTotalView(jsonNode.get("totalView").asText());
        bookKols.setTotalDurationView(jsonNode.get("totalDurationView").asText());
        return bookKols;
    }

    @RequestMapping(value = "/deleteBookKols", method = RequestMethod.POST)
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
                        BookKols bookKols = kolsRepository.findBookKolsById(id);
                        bookKols.setStatus(Constant.inactive);
                        kolsRepository.save(bookKols);
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
