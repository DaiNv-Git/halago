package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.*;
import com.hitex.halago.model.dao.influencer.cms.InfluencerApproveCampaignDao;
import com.hitex.halago.model.dao.influencer.cms.InfluencerDao;
import com.hitex.halago.model.dao.influencer.portal.InfluencerPortalDao;
import com.hitex.halago.model.dao.influencer.portal.InfluencerPortalId;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.*;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.InfluencerService;
import com.hitex.halago.service.impl.MailServiceImpl;
import com.hitex.halago.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
public class InfluencerController {
    Logger logger = LoggerFactory.getLogger(InfluencerController.class);
    @Autowired
    InfluencerService influencerService;
    @Autowired
    MailServiceImpl mailService;
    @Autowired
    InfluencerRepository influencerRepository;
    @Autowired
    InfluencerPortalLanguageRepository influencerPortalLanguageRepository;
    @Autowired
    InfluencerPortalRepository influencerPortalRepository;
    @Autowired
    ChannelInteractionRepository channelInteractionRepository;
    @Autowired
    TypesInteractionRepository typesInteractionRepository;
    @Autowired
    IndustryRepository industryRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CareerRepository careerRepository;
    @Autowired
    private JwtService jwtService;
    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);


    @RequestMapping(value = "/findInfluencerById", method = RequestMethod.POST)
    public ResponseEntity<?> findInfluencerById(@RequestBody InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (Constant.INFLUENCER.equals(role) || Constant.ADMIN.equals(role) || Constant.STAFF.equals(role)) {
                    if (jsonNode.get("id") != null) {
                        int id = jsonNode.get("id").asInt();
                        InfluencerDao Influencer = influencerService.findInfluencerById(id);
                        responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, Influencer));
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

    @RequestMapping(value = "/findInfluencerByName", method = RequestMethod.POST)
    public ResponseEntity<?> findPersonalByName(@RequestBody InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (Constant.INFLUENCER.equals(role) || Constant.ADMIN.equals(role)) {
                    String name = jsonNode.get("InfluencerName").asText();
                    List<Influencer> Influencer = influencerService.findInfluencerByName(name);
                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, Influencer));

                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền tìm kiếm", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getListInfluencer", method = RequestMethod.POST)
    public ResponseEntity<?> findAllPersonal( InputStream inputStream,@RequestBody String input) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (Constant.INFLUENCER.equals(role) || Constant.ADMIN.equals(role) || Constant.STAFF.equals(role)) {
                    String name = jsonNode.get("name").asText();
                    String status = jsonNode.get("status").asText();
                    int pageSize = jsonNode.get("pageSize").asInt();
                    int pageNumber = jsonNode.get("pageNumber").asInt();
                    if (org.apache.commons.lang3.StringUtils.isEmpty(status)) {
                        status = "-1";
                    }
                    if (StringUtils.isEmpty(name)) {
                        name = null;
                    }
                    List<InfluencerDao> Influencers = influencerService.getListInfluencer(name, Integer.parseInt(status), pageSize, pageNumber);
                    ResponseBase responseBase = new ResponseBase();
                    responseBase.setSiRes(null);
                    responseBase.setData(Influencers);
                    responseBase.setTotal(influencerRepository.getListInfluencer(name, Integer.parseInt(status)));
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

    @RequestMapping(value = "/insertInfluencer", method = RequestMethod.POST)
    public ResponseEntity<?> insertPersonal(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Thêm Influencer thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (Constant.INFLUENCER.equals(role) || Constant.ADMIN.equals(role)) {
                    Influencer influencer = new Influencer();
                    influencer.setName(jsonNode.get("name").asText());
                    influencer.setAddress(jsonNode.get("address").asText());
                    influencer.setBankAccount(jsonNode.get("bankAccount").asText());
                    influencer.setBankName(jsonNode.get("bankName").asText());
                    influencer.setDescription(jsonNode.get("description").asText());
                    influencer.setBirthday(jsonNode.get("birthday").asText());
                    influencer.setCareer(jsonNode.get("career").asInt());
                    influencer.setCity(jsonNode.get("city").asInt());
                    influencer.setEmail(jsonNode.get("email").asText());
                    influencer.setMaritalStatus(jsonNode.get("maritalStatus").asInt());
                    influencer.setPhone(jsonNode.get("phone").asText());
                    influencer.setSex(jsonNode.get("sex").asInt());
                    influencer.setStatus(Constant.active);
                    influencer.setUrlFb(jsonNode.get("urlFb").asText());
                    influencer.setSaleExpertience(jsonNode.get("saleExpertience").asText());
                    influencer.setCreated(new Date());
                    String industryId = com.hitex.halago.utils.StringUtils.convertArraytoInteger(com.hitex.halago.utils.StringUtils.getArrayIntegerJson(jsonNode, "industryId")).trim();
                    if (industryId.endsWith(",")) {
                        industryId = industryId.substring(0, industryId.length() - 1) + "";
                    }
                    String channelInteraction = com.hitex.halago.utils.StringUtils.convertArraytoInteger(com.hitex.halago.utils.StringUtils.getArrayIntegerJson(jsonNode, "channelInteraction")).trim();
                    if (channelInteraction.endsWith(",")) {
                        channelInteraction = channelInteraction.substring(0, channelInteraction.length() - 1) + "";
                    }
                    String typesInteraction = com.hitex.halago.utils.StringUtils.convertArraytoInteger(com.hitex.halago.utils.StringUtils.getArrayIntegerJson(jsonNode, "typesInteraction")).trim();
                    if (typesInteraction.endsWith(",")) {
                        typesInteraction = typesInteraction.substring(0, typesInteraction.length() - 1) + "";
                    }
                    influencer.setIndustryId(industryId);
                    influencer.setAverageInteract(jsonNode.get("averageInteract").asText());
                    influencer.setChannelInteraction(channelInteraction);
                    influencer.setTypesInteraction(typesInteraction);
                    influencer.setCreated(new Date());
                    influencer.setAvatar(jsonNode.get("avatar").asText());
                    Influencer influencerMail = influencerRepository.checkMailInfluencer(influencer.getEmail());
                    if (ObjectUtils.isEmpty(influencerMail)) {
                        influencerService.insertInfluencer(influencer);
                        mailService.sendEmail("Influencer đăng ký mới trên Halago",
                                "Influencer " + influencer.getName() + " vừa đăng ký mới trên halago.vn" );
                        responseData = new ResponseData(Constant.SUCCESS, "Thêm Influencer thành công", new ResponseBase(null, influencer));
                    } else {
                        responseData = new ResponseData(Constant.FAILED, "Email đã tồn tại", new ResponseBase(null, true));
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm Influencer", null);
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateInfluencer", method = RequestMethod.POST)
    public ResponseEntity<?> updatePersonal(@RequestBody InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Cập nhật Influencer thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (Constant.INFLUENCER.equals(role) || Constant.ADMIN.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    Influencer influencer = influencerRepository.findInfluencer(id);
                    if (!ObjectUtils.isEmpty(influencer)) {
                        influencer.setName(jsonNode.get("name").asText());
                        influencer.setAddress(jsonNode.get("address").asText());
                        influencer.setBankAccount(jsonNode.get("bankAccount").asText());
                        influencer.setBankName(jsonNode.get("bankName").asText());
                        influencer.setDescription(jsonNode.get("description").asText());
                        influencer.setBirthday(jsonNode.get("birthday").asText());
                        influencer.setCareer(jsonNode.get("career").asInt());
                        influencer.setCity(jsonNode.get("city").asInt());
                        influencer.setEmail(jsonNode.get("email").asText());
                        influencer.setMaritalStatus(jsonNode.get("maritalStatus").asInt());
                        influencer.setPhone(jsonNode.get("phone").asText());
                        influencer.setSex(jsonNode.get("sex").asInt());
//                    influencer.setStatus(jsonNode.get("status").asInt());
                        influencer.setUrlFb(jsonNode.get("urlFb").asText());
                        influencer.setSaleExpertience(jsonNode.get("saleExpertience").asText());
                        String industryId = com.hitex.halago.utils.StringUtils.convertHashTagtoString(com.hitex.halago.utils.StringUtils.getArrayHashTagJson(jsonNode, "industryId")).trim();
                        if (industryId.endsWith(",")) {
                            industryId = industryId.substring(0, industryId.length() - 1) + "";
                        }
                        String channelInteraction = com.hitex.halago.utils.StringUtils.convertHashTagtoString(com.hitex.halago.utils.StringUtils.getArrayHashTagJson(jsonNode, "channelInteraction")).trim();
                        if (channelInteraction.endsWith(",")) {
                            channelInteraction = channelInteraction.substring(0, channelInteraction.length() - 1) + "";
                        }
                        String typesInteraction = com.hitex.halago.utils.StringUtils.convertHashTagtoString(com.hitex.halago.utils.StringUtils.getArrayHashTagJson(jsonNode, "typesInteraction")).trim();
                        if (typesInteraction.endsWith(",")) {
                            typesInteraction = typesInteraction.substring(0, typesInteraction.length() - 1) + "";
                        }
                        influencer.setIndustryId(industryId);
                        influencer.setAverageInteract(jsonNode.get("averageInteract").asText());
                        influencer.setChannelInteraction(channelInteraction);
                        influencer.setTypesInteraction(typesInteraction);
                        influencer.setAvatar(jsonNode.get("avatar").asText());
                        Influencer influencerMail = influencerRepository.checkMailInfluencer(influencer.getEmail());
                        if (!ObjectUtils.isEmpty(influencerMail) && influencerMail.getId() != influencer.getId()) {
                            responseData = new ResponseData(Constant.FAILED, "Email đã tồn tại", new ResponseBase(null, true));
                        } else {
                            influencerService.updateInfluencer(influencer);
                            responseData = new ResponseData(Constant.SUCCESS, "Cập nhật Influencer thành công", new ResponseBase(null, influencer));
                        }
                    } else {
                        responseData = new ResponseData(Constant.FAILED, "Dữ liệu không tồn tại", new ResponseBase(null, true));
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền cập nhật Influencer", null);
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateInfluencerPortal", method = RequestMethod.POST)
    public ResponseEntity<?> updateInfluencerPortal(@RequestBody InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Cập nhật Influencer thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                int id = jsonNode.get("id").asInt();
                Influencer influencer = influencerRepository.findInfluencer(id);
                if (ObjectUtils.isEmpty(influencer)) {
                    responseData = new ResponseData(Constant.FAILED, "Không tìm thấy Influencer", null);
                }
                influencer.setName(jsonNode.get("name").asText());
                influencer.setAddress(jsonNode.get("address").asText());
                influencer.setBankAccount(jsonNode.get("bankAccount").asText());
                influencer.setBankName(jsonNode.get("bankName").asText());
                influencer.setDescription(!jsonNode.has("description") ? "" : jsonNode.get("description").asText());
                influencer.setBirthday(jsonNode.get("birthday").asText());
                influencer.setCareer(jsonNode.get("career").asInt());
                influencer.setCity(jsonNode.get("city").asInt());
                influencer.setEmail(jsonNode.get("email").asText());
                influencer.setMaritalStatus(jsonNode.get("maritalStatus").asInt());
                influencer.setPhone(jsonNode.get("phone").asText());
                influencer.setSex(jsonNode.get("sex").asInt());
                influencer.setStatus(Constant.active);
//                influencer.setUrlFb(jsonNode.get("urlFb").asText());
                influencer.setSaleExpertience(!jsonNode.has("saleExpertience") ? "" :jsonNode.get("saleExpertience").asText());
                String industryId = com.hitex.halago.utils.StringUtils.convertHashTagtoString(com.hitex.halago.utils.StringUtils.getArrayHashTagJson(jsonNode, "industryId")).trim();
                if (industryId.endsWith(",")) {
                    industryId = industryId.substring(0, industryId.length() - 1) + "";
                }
                String channelInteraction = com.hitex.halago.utils.StringUtils.convertHashTagtoString(com.hitex.halago.utils.StringUtils.getArrayHashTagJson(jsonNode, "channelInteraction")).trim();
                if (channelInteraction.endsWith(",")) {
                    channelInteraction = channelInteraction.substring(0, channelInteraction.length() - 1) + "";
                }
                String typesInteraction = com.hitex.halago.utils.StringUtils.convertHashTagtoString(com.hitex.halago.utils.StringUtils.getArrayHashTagJson(jsonNode, "typesInteraction")).trim();
                if (typesInteraction.endsWith(",")) {
                    typesInteraction = typesInteraction.substring(0, typesInteraction.length() - 1) + "";
                }
                influencer.setIndustryId(industryId);
                influencer.setAverageInteract(jsonNode.get("averageInteract").asText());
                influencer.setChannelInteraction(channelInteraction);
                influencer.setTypesInteraction(typesInteraction);
                influencer.setCreated(new Date());
                influencer.setAvatar(jsonNode.get("avatar").asText());
                Influencer influencerMail = influencerRepository.checkMailInfluencer(influencer.getEmail());
                if (!ObjectUtils.isEmpty(influencerMail) && influencerMail.getId() != influencer.getId()) {
                    responseData = new ResponseData(Constant.FAILED, "Email đã tồn tại", new ResponseBase(null, true));
                } else {
                    influencerService.updateInfluencer(influencer);
                    responseData = new ResponseData(Constant.SUCCESS, "Cập nhật Influencer thành công", new ResponseBase(null, jwtService.generateTokenLogin(jsonNode.get("name").asText(), Constant.INFLUENCER)));
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/deleteInfluencer", method = RequestMethod.POST)
    public ResponseEntity<?> deletePersonal(@RequestBody InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Dữ liệu đầu vào không hợp lệ", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (Constant.INFLUENCER.equals(role) || Constant.ADMIN.equals(role)) {
                    if (jsonNode.get("id") != null) {
                        int id = jsonNode.get("id").asInt();
                        int status = influencerService.deleteInfluencer(id);
                        if (status == Constant.SUCCESS) {
                            responseData = new ResponseData(Constant.SUCCESS, "Xoá Influencer Thành công", new ResponseBase(null, true));
                        } else {
                            responseData = new ResponseData(Constant.FAILED, "Xoá Influencer thất bại", new ResponseBase(null, true));
                        }
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

    @RequestMapping(value = "/listCity", method = RequestMethod.POST)
    public ResponseEntity<?> ListCity(@RequestBody InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                List<City> cityList = cityRepository.cityList();
                responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null, cityList));
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/listCareer", method = RequestMethod.POST)
    public ResponseEntity<?> ListCareer(@RequestBody InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                List<Career> careers = careerRepository.listCareer();
                responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null, careers));
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/listChannel", method = RequestMethod.POST)
    public ResponseEntity<?> ListChannel(@RequestBody InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                List<ChannelInteraction> channelInteractions = channelInteractionRepository.listChannel();
                responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null, channelInteractions));
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/listTypesInteraction", method = RequestMethod.POST)
    public ResponseEntity<?> listTypesInteraction(@RequestBody InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                List<TypesInteraction> typesInteractions = typesInteractionRepository.listType();
                responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null, typesInteractions));
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/listIndustry", method = RequestMethod.POST)
    public ResponseEntity<?> listIndustry(@RequestBody InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                List<Industry> industryList = industryRepository.listIndustry();
                responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null, industryList));
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/findInfluencerPortal", method = RequestMethod.POST)
    public ResponseEntity<?> findInfluencerPortal(@RequestBody InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                int industry = jsonNode.get("industryId").asInt();
                int fromAge = jsonNode.get("fromAge").asInt();
                int toAge = jsonNode.get("toAge").asInt();
                int sex = jsonNode.get("sex").asInt();
                int cityId = jsonNode.get("cityId").asInt();
                int pageSize = jsonNode.get("pageSize").asInt();
                int pageNumber = jsonNode.get("pageNumber").asInt();
                List<InfluencerDao> Influencers = influencerService.findInfluencer(industry, fromAge, toAge, sex, cityId, pageSize, pageNumber);
                ResponseBase responseBase = new ResponseBase();
                responseBase.setSiRes(null);
                responseBase.setData(Influencers);
                responseBase.setTotal(influencerRepository.countInfluencer(industry, fromAge, toAge, sex, cityId));
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getListCampaignApply", method = RequestMethod.POST)
    public ResponseEntity<?> getListCampaignApply(@RequestBody InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (Constant.INFLUENCER.equals(role) || Constant.ADMIN.equals(role)) {
                    int idInfluencer = jsonNode.get("idInfluencer").asInt();
                    int pageSize = jsonNode.get("pageSize").asInt();
                    int pageNumber = jsonNode.get("pageNumber").asInt();
                    List<InfluencerApproveCampaignDao> Influencers = influencerService.getListCampaignByInfluencer(idInfluencer, pageSize, pageNumber);
                    ResponseBase responseBase = new ResponseBase();
                    responseBase.setSiRes(null);
                    responseBase.setData(Influencers);
                    responseBase.setTotal(influencerService.countGetListCampaignByInfluencer(idInfluencer));
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

    @RequestMapping(value = "/getListInfluencerPgae", method = RequestMethod.POST)
    public ResponseEntity<?> getListInfluencerPgae(@RequestBody InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                ResponseBase responseBase = new ResponseBase();
                String language;
                if (jsonNode.has("language")) {
                    language = jsonNode.get("language").asText();
                    language = StringUtils.isEmpty(language) ? "en" : language;
                    InfluencerPortalDao influencerPortalDao = influencerService.getListInfluencerPortal(language);
                    responseBase.setSiRes(null);
                    responseBase.setData(influencerPortalDao);
                    responseBase.setTotal(0);
                }else{
                    InfluencerPortalDao influencerPortalDao = influencerService.getListInfluencerPortalCMS();
                    responseBase.setSiRes(null);
                    responseBase.setData(influencerPortalDao);
                    responseBase.setTotal(0);
                }
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
//                }
            }
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateInfluencerPage", method = RequestMethod.POST)
    public ResponseEntity<?> updateInfluencerPage(@RequestBody InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Cập nhật thông tin thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    InfluencerPortal influencerPortal = influencerPortalRepository.findInfluencerPortalById(id);
                    InfluencerPortalLanguage lang = influencerPortalLanguageRepository.findInfluencerPortalLanguage(id);
                    if (influencerPortal.getType() == Constant.HEADER) {
                        influencerPortal.setTitle(jsonNode.get("title").asText());
                        lang.setTitle(jsonNode.get("title_en").asText());
                        influencerPortal.setImg(jsonNode.get("img").asText());
                        influencerPortal.setContent(jsonNode.get("content").asText());
                        lang.setContent(jsonNode.get("content_en").asText());
                        influencerPortal.setStatus(jsonNode.get("status").asInt());
                        influencerPortal.setTitleSeo(jsonNode.get("titleSeo").asText());
                    } else if (influencerPortal.getType() == Constant.BODY) {
                        influencerPortal.setImg(jsonNode.get("img").asText());
                        influencerPortal.setContent(jsonNode.get("content").asText());
                        lang.setContent(jsonNode.get("content_en").asText());
                        influencerPortal.setStatus(jsonNode.get("status").asInt());
                        influencerPortal.setTitleSeo(jsonNode.get("titleSeo").asText());
                    } else {
                        influencerPortal.setTitle(jsonNode.get("title").asText());
                        influencerPortal.setImg(jsonNode.get("img").asText());
                        influencerPortal.setContent(jsonNode.get("content").asText());
                        lang.setContent(jsonNode.get("content_en").asText());
                        influencerPortal.setContentSecond(jsonNode.get("contentSecond").asText());
                        lang.setContentSecond(jsonNode.get("contentSecond_en").asText());
                        influencerPortal.setContentThird(jsonNode.get("contentThird").asText());
                        lang.setContentThird(jsonNode.get("contentThird_en").asText());
                        influencerPortal.setStatus(jsonNode.get("status").asInt());
                        influencerPortal.setTitleSeo(jsonNode.get("titleSeo").asText());
                    }
                    influencerPortalRepository.save(influencerPortal);
                    influencerPortalLanguageRepository.save(lang);
                    responseData = new ResponseData(Constant.SUCCESS, "Cập nhật thông tin thành công", new ResponseBase(null, influencerPortal));
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

    @RequestMapping(value = "/findInfluencerPortalId", method = RequestMethod.POST)
    public ResponseEntity<?> findInfluencerPortalId(@RequestBody InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                int id = jsonNode.get("id").asInt();
                InfluencerPortalId Influencers = influencerService.findInfluencerPortalId(id);
                ResponseBase responseBase = new ResponseBase();
                responseBase.setSiRes(null);
                responseBase.setData(Influencers);
                responseBase.setTotal(1);
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
