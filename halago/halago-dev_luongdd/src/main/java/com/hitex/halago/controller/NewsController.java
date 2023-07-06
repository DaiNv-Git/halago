package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.DAO.NewsDao;
import com.hitex.halago.model.DAO.NewsTypeDao;
import com.hitex.halago.model.News;
import com.hitex.halago.model.NewsLanguage;
import com.hitex.halago.model.NewsType;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.NewsLanguageRepository;
import com.hitex.halago.repository.NewsRepository;
import com.hitex.halago.repository.NewsTypeRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.NewsService;
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
import java.util.Date;
import java.util.List;

@RestController
public class NewsController {
    Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    NewsLanguageRepository newsLanguageRepository;

    @Autowired
    NewsTypeRepository newsTypeRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    private JwtService jwtService;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

    @RequestMapping(value = "/getListNewsPortal", method = RequestMethod.POST)
    public ResponseEntity<?> getListNewsPortal(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
//                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role) ||  Constant.STAFF.equals(role)) {
//                    int idCampaign = jsonNode.get("idCampaign").asInt();
//                    Integer pageSize = jsonNode.get("pageSize").asInt();
//                    Integer pageNumber = jsonNode.get("pageNumber").asInt();
                NewsTypeDao news = newsService.getListNewsPortal();
                ResponseBase responseBase = new ResponseBase();
                responseBase.setSiRes(null);
                responseBase.setData(news);
//                    responseBase.setTotal(approveRepository.getListApprove(idCampaign));
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);

//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
//                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @RequestMapping(value = "/getListNewsFooter", method = RequestMethod.POST)
    public ResponseEntity<?> getListNewsFooter(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
//                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role) ||  Constant.STAFF.equals(role)) {
//                    int idCampaign = jsonNode.get("idCampaign").asInt();
//                    Integer pageSize = jsonNode.get("pageSize").asInt();
//                    Integer pageNumber = jsonNode.get("pageNumber").asInt();
                List< NewsDao> news = newsService.getListNewsFooter();
                ResponseBase responseBase = new ResponseBase();
                responseBase.setSiRes(null);
                responseBase.setData(news);
//                    responseBase.setTotal(approveRepository.getListApprove(idCampaign));
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);

//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
//                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }



    @RequestMapping(value = "/getListNews", method = RequestMethod.POST)
    public ResponseEntity<?> getListNews(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
//                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role) ||  Constant.STAFF.equals(role)) {
                String title = !jsonNode.has("title") || StringUtils.isEmpty(jsonNode.get("title").asText()) ? null : jsonNode.get("title").asText();
                String status = !jsonNode.has("status") ? null : StringUtils.isEmpty(java.lang.String.valueOf(jsonNode.get("status").asText())) ? null : jsonNode.get("status").asText();
                Integer pageSize = !jsonNode.has("pageSize") ? 10 : jsonNode.get("pageSize").asInt();
                Integer pageNumber = !jsonNode.has("pageNumber") ? 1 : jsonNode.get("pageNumber").asInt();
                List<NewsDao> news = newsService.getListNewsCms(title, status, pageSize, pageNumber);
                ResponseBase responseBase = new ResponseBase();
                responseBase.setSiRes(null);
                responseBase.setData(news);
                responseBase.setTotal(newsService.countListNews(title, status, pageSize, pageNumber));
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);

//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
//                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getNewsType", method = RequestMethod.POST)
    public ResponseEntity<?> getNewsType(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Tìm kiếm thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                List<NewsType> news = newsTypeRepository.getListNewsType();
                ResponseBase responseBase = new ResponseBase();
                responseBase.setSiRes(null);
                responseBase.setData(news);
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getNews", method = RequestMethod.POST)
    public ResponseEntity<?> getNews(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
//                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role) ||  Constant.STAFF.equals(role)) {
                int idNews = !jsonNode.has("idNews") ? -1 : jsonNode.get("idNews").asInt();
                NewsDao news = newsService.findNewsById(idNews);
                ResponseBase responseBase = new ResponseBase();
                responseBase.setSiRes(null);
                responseBase.setData(news);
//                    responseBase.setTotal(approveRepository.getListApprove(idCampaign));
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);

//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
//                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/insertNews", method = RequestMethod.POST)
    public ResponseEntity<?> insertNews(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Thêm tin tức thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (!Constant.BRAND.equals(role) && !Constant.INFLUENCER.equals(role)) {
                    News news = new News();
                    news.setThumbnail(jsonNode.get("thumbnail").asText());
                    news.setType(jsonNode.get("type").asInt());
                    news.setStatus(jsonNode.get("status").asInt());
                    news.setCreated(new Date());
                    news.setTitleSeo(!jsonNode.has("titleSeo") ? "" : jsonNode.get("titleSeo").asText());
                    news.setLinkPapers(!jsonNode.has("linkPapers") ? "" : jsonNode.get("linkPapers").asText());
                    newsService.insertNews(news);
                    NewsLanguage newsLanguage = new NewsLanguage();
                    newsLanguage.setIdNews(news.getIdNews());
                    newsLanguage.setContent( !jsonNode.has("content")? "" : jsonNode.get("content").asText());
                    newsLanguage.setDescription(!jsonNode.has("description") ? "" : jsonNode.get("description").asText());
                    newsLanguage.setTitle(!jsonNode.has("title") ? "" :jsonNode.get("title").asText());
                    newsLanguage.setLanguage("vn");
                    newsLanguageRepository.save(newsLanguage);
                    if (jsonNode.has("contentEn") || jsonNode.has("titleEn") || jsonNode.has("descriptionEn")) {
                        NewsLanguage newsLanguageEn = new NewsLanguage();
                        newsLanguageEn.setIdNews(news.getIdNews());
                        newsLanguageEn.setContent(!jsonNode.has("contentEn") ? "" : jsonNode.get("contentEn").asText());
                        newsLanguageEn.setDescription(!jsonNode.has("descriptionEn") ? "" :jsonNode.get("descriptionEn").asText());
                        newsLanguageEn.setTitle(!jsonNode.has("titleEn") ? "" :jsonNode.get("titleEn").asText());
                        newsLanguageEn.setLanguage("en");
                        newsLanguageRepository.save(newsLanguageEn);
                    }
                    responseData = new ResponseData(Constant.SUCCESS, "Thêm tin tức thành công", new ResponseBase(null, news));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm tin tức", null);
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateNews", method = RequestMethod.POST)
    public ResponseEntity<?> updateNews(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Thêm tin tức thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (!Constant.BRAND.equals(role) && !Constant.INFLUENCER.equals(role)) {
                    int idNews = jsonNode.get("idNews").asInt();
                    News news = newsRepository.getNews(idNews);
                    news.setThumbnail(jsonNode.get("thumbnail").asText());
                    news.setType(jsonNode.get("type").asInt());
                    news.setStatus(jsonNode.get("status").asInt());
                    news.setCreated(new Date());
                    news.setTitleSeo(!jsonNode.has("titleSeo") ? "" : jsonNode.get("titleSeo").asText());
                    news.setLinkPapers(!jsonNode.has("linkPapers") ? "" : jsonNode.get("linkPapers").asText());
                    for (NewsLanguage language : news.getLanguage()) {
                        if (Constant.LANGUAGE_VN.equals(language.getLanguage())) {
                            language.setContent(jsonNode.get("content").asText());
                            language.setDescription(jsonNode.get("description").asText());
                            language.setTitle(jsonNode.get("title").asText());
                        }
                        if (Constant.LANGUAGE_EN.equals(language.getLanguage())) {
                            language.setContent(jsonNode.get("contentEn").asText());
                            language.setDescription(jsonNode.get("descriptionEn").asText());
                            language.setTitle(jsonNode.get("titleEn").asText());
                        }
                        newsLanguageRepository.save(language);
                    }
                    newsService.insertNews(news);
//                    NewsLanguage newsLanguage = new NewsLanguage();
//                    newsLanguage.setIdNews(news.getIdNews());
//                    newsLanguage.setContent(jsonNode.get("content").asText());
//                    newsLanguage.setDescription(jsonNode.get("description").asText());
//                    newsLanguage.setTitle(jsonNode.get("title").asText());
//                    newsLanguage.setLanguage("vn");
//                    newsLanguageRepository.save(newsLanguage);
//                    if(jsonNode.has("contentEn") || jsonNode.has("titleEn") || jsonNode.has("descriptionEn")){
//                        NewsLanguage newsLanguageEn = new NewsLanguage();
//                        newsLanguageEn.setIdNews(news.getIdNews());
//                        newsLanguageEn.setContent(jsonNode.get("contentEn").asText());
//                        newsLanguageEn.setDescription(jsonNode.get("descriptionEn").asText());
//                        newsLanguageEn.setTitle(jsonNode.get("titleEn").asText());
//                        newsLanguageEn.setLanguage("en");
//                        newsLanguageRepository.save(newsLanguageEn);
//                    }
                    responseData = new ResponseData(Constant.SUCCESS, "Cập nhật tin tức thành công", new ResponseBase(null, news));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm tin tức", null);
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
