package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.Brand;
import com.hitex.halago.model.dao.AccountUser;
import com.hitex.halago.model.dao.BrandDao;
import com.hitex.halago.model.dao.RoleFunction.RoleFunction;
import com.hitex.halago.model.dao.influencer.cms.InfluencerDao;
import com.hitex.halago.model.dao.UserDao;
import com.hitex.halago.model.Influencer;
import com.hitex.halago.model.User;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.BrandRepository;
import com.hitex.halago.repository.InfluencerRepository;
import com.hitex.halago.repository.UserRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.UserSerivce;
import com.hitex.halago.service.impl.MailServiceImpl;
import com.hitex.halago.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserSerivce userSerivce;
    @Autowired
    MailServiceImpl mailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    InfluencerRepository influencerRepository;
    @Autowired
    BrandRepository brandRepository;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<?> logins(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Lỗi", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String username = "";
                String pass = "";
                String result = "";
                if (jsonNode.get("username") != null) {
                    username = jsonNode.get("username").asText();
                }
                if (jsonNode.get("password") != null) {
                    pass = jsonNode.get("password").asText();
                }
                UserDao personal = userSerivce.findUser(username);
                com.hitex.halago.model.dao.User user = userSerivce.findAccount(username);
                if (personal != null) {
                    String passs = personal.getPassword();
                    if (pass.equals(passs)) {
                        result = jwtService.generateTokenLogin(username, String.valueOf(personal.getRole()));
                        RoleFunction roleFunction = userSerivce.getFunctionByRole(personal.getRole());
                        user.setToken(result);
                        user.setFunctionName(roleFunction.getFunctionName());
                        user.setApiName(roleFunction.getApiName());
                        responseData = new ResponseData(Constant.SUCCESS, "Đăng nhập thành công", new ResponseBase(null, user));
                    } else {
                        responseData = new ResponseData(Constant.FAILED, "Sai mật khẩu", null);
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Tài khoản không tồn tại!", null);
                }
            }
        } catch (Exception e) {
           logger.info(e.getMessage(),e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/loginPortal", method = RequestMethod.POST)
    ResponseEntity<?> loginPortal(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Lỗi", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String username = "";
                String pass = "";
                String result = "";
                if (jsonNode.get("username") != null) {
                    username = jsonNode.get("username").asText();
                }
                if (jsonNode.get("password") != null) {
                    pass = jsonNode.get("password").asText();
                }
                UserDao personal = userSerivce.findUserPotal(username);
                com.hitex.halago.model.dao.User user = userSerivce.findAccount(username);
                if (personal != null) {
                    String passs = personal.getPassword();
                    if (pass.equals(passs)) {
                        result = jwtService.generateTokenLogin(username, String.valueOf(personal.getRole()));
                        RoleFunction roleFunction = userSerivce.getFunctionByRole(personal.getRole());
                        user.setToken(result);
                        Brand brand = brandRepository.checkMailBrand(username);
                        BrandDao brandDao = new BrandDao();
                        BeanUtils.copyProperties(brand, brandDao);
                        brandDao.setToken(result);
                        brandDao.setFunctionName(roleFunction.getFunctionName());
                        brandDao.setApiName(roleFunction.getApiName());
                        responseData = new ResponseData(Constant.SUCCESS, "Đăng nhập thành công", new ResponseBase(null, brandDao));
                    } else {
                        responseData = new ResponseData(Constant.FAILED, "Sai mật khẩu", null);
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Tài khoản không tồn tại!", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getListUser", method = RequestMethod.POST)
    public ResponseEntity<?> getListUser(InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không đủ", null);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));

                if (jsonNode.get("username") != null) {
                    String name = jsonNode.get("username").asText();
                    int pageNumber = jsonNode.get("pageNumber").asInt();
                    int pageSize = jsonNode.get("pageSize").asInt();
                    int total = userSerivce.countListUser(StringUtils.isEmpty(name) ? null : name);

                    List<AccountUser> accountUsers = userSerivce.getListUser(StringUtils.isEmpty(name) ? "" : name, pageSize, pageNumber);

                    ResponseBase responseBase = new ResponseBase();
                    responseBase.setSiRes(null);
                    responseBase.setData(accountUsers);
                    responseBase.setTotal(accountUsers.size());
                    responseData = new ResponseData(Constant.SUCCESS, "Thành công", responseBase);
                }

            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    public ResponseEntity<?> findUser(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Có lỗi khi xóa User", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    User user = userRepository.findUserById(id);
                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, user));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền Xóa", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    public ResponseEntity<?> insertUser(InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không đủ", null);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));

                if (jsonNode.get("username") != null && jsonNode.get("password") != null && jsonNode.get("role") != null) {
                    User user = new User();
                    user.setUsername(jsonNode.get("username").asText());
                    user.setPassword(jsonNode.get("password").asText());
                    user.setRole(jsonNode.get("role").asInt());
                    user.setStatus(Constant.active);
                    user.setCreated(new Date());
                    User checkUsername = userRepository.checkUsername(jsonNode.get("username").asText());
                    if (!ObjectUtils.isEmpty(checkUsername)) {
                        responseData = new ResponseData(Constant.FAILED, "Username đã được đăng ký", new ResponseBase(null, user));
                    } else {
                        userSerivce.registerUser(user);
                        responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null, user));
                    }
                }

            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không đủ", null);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));

                if (jsonNode.get("username") != null && jsonNode.get("password") != null && jsonNode.get("role") != null) {
                    User user = new User();
                    user.setId(jsonNode.get("id").asInt());
                    user.setUsername(jsonNode.get("username").asText());
                    user.setPassword(jsonNode.get("password").asText());
                    user.setRole(jsonNode.get("role").asInt());
                    user.setStatus(Constant.active);
                    user.setCreated(new Date());
                    User checkUsername = userRepository.checkUsername(jsonNode.get("username").asText());
                    if (!ObjectUtils.isEmpty(checkUsername) && checkUsername.getId() != user.getId()) {
                        responseData = new ResponseData(Constant.FAILED, "Username đã được đăng ký", new ResponseBase(null, user));
                    } else {
                        userSerivce.registerUser(user);
                        responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null, user));
                    }
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public ResponseEntity<?> deleteUser(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Có lỗi khi xóa User", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    User userDelete = userRepository.findUserById(id);
                    userDelete.setStatus(Constant.inactive);
                    userRepository.save(userDelete);
                    responseData = new ResponseData(Constant.SUCCESS, "Xóa thành công", new ResponseBase(null, true));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền Xóa", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity<?> forgotPassword(InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            responseData = new ResponseData(Constant.FAILED, "Thất bại", null);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("newPassword") != null) {
                    User user = userRepository.findUser(jsonNode.get("email").asText());
                    if (!ObjectUtils.isEmpty(user)) {
                        user.setId(user.getId());
                        user.setPassword(jsonNode.get("newPassword").asText());
                        userSerivce.registerUser(user);
                        responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null, user));
                    }
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

//    @RequestMapping(value = "/checkMail", method = RequestMethod.POST)
//    public ResponseEntity<?> checkMail(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(Constant.FAILED, "Mail không tồn tại", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                User user = userRepository.findUser(jsonNode.get("email").asText());
//                if (!ObjectUtils.isEmpty(user)) {
//                    responseData = new ResponseData(Constant.SUCCESS, "Thành công", new ResponseBase(null, true));
//                }
//            }
//        } catch (Exception e) {
//            logger.info(e.getMessage(), e);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

    @RequestMapping(value = "/loginFbInfluencer", method = RequestMethod.POST)
    ResponseEntity<?> loginFbInfluencer(InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);https://imgyousim.semob.info/img/logoPartner.jpg
            responseData = new ResponseData(Constant.FAILED, "Thất bại", null);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("token") != null) {
                    String token = jsonNode.get("token").asText();
                    String fbId = !jsonNode.has("fbId") ? null : jsonNode.get("fbId").asText();
                    String linkfb = !jsonNode.has("urlFb") ? "" : jsonNode.get("urlFb").asText();
                    FacebookTemplate template = new FacebookTemplate(token);
                    String[] fields = {"id", "email", "name", "first_name", "last_name","link"};

                    org.springframework.social.facebook.api.User profile = template.fetchObject("me", org.springframework.social.facebook.api.User.class);
                    String fbID = profile.getId();
                    fbId = StringUtils.isEmpty(fbId) ? fbID : fbId;
//                    org.springframework.social.facebook.api.User profileUser = template.userOperations().getUserProfile();
                    String link = "https://www.facebook.com/"+ fbId ;
                    String avatar =  "https://graph.facebook.com/"+fbID+"/picture";
                    String accessToken = jwtService.generateTokenLogin(fbID, Constant.INFLUENCER);
                    Influencer influencerByFbId = influencerRepository.findInfluencerByFbId(fbID);
                    com.hitex.halago.model.dao.User user = new com.hitex.halago.model.dao.User();
                    if (ObjectUtils.isEmpty(influencerByFbId)) {
                        Influencer influencer = new Influencer();
                        influencer.setFbId(fbID);
                        influencer.setStatus(Constant.NOT_REGISTER);
                        influencer.setCreated(new Date());
                        influencer.setAvatar(avatar);
                        influencer.setUrlFb(linkfb);
                        influencer.setEmail(profile.getEmail());
                        influencerRepository.save(influencer);
                        InfluencerDao influencerDao = new InfluencerDao();
                        BeanUtils.copyProperties(influencer,influencerDao);
                        user.setToken(accessToken);
                        user.setStatus(Constant.NOT_REGISTER);
                        mailService.sendEmail("Influencer đăng ký mới trên Halago",
                                "Influencer : " + profile.getName() +"\n" +
                                        "Mail : " + profile.getEmail() + "\n");
                        responseData = new ResponseData(Constant.SUCCESS, "Thành Công", new ResponseBase(null, influencerDao));
                    } else {
                        InfluencerDao influencerDao = new InfluencerDao();
                        if(!StringUtils.equals(influencerByFbId.getFbId(),fbId)){
                            influencerByFbId.setFbId(fbId);
                            influencerRepository.save(influencerByFbId);
                        }
                        BeanUtils.copyProperties(influencerByFbId, influencerDao);
                        influencerDao.setToken(accessToken);

                        responseData = new ResponseData(Constant.SUCCESS, "Thành Công", new ResponseBase(null, influencerDao));
                    }

                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/loginFbBrand", method = RequestMethod.POST)
    ResponseEntity<?> loginFbBrand(InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            responseData = new ResponseData(Constant.FAILED, "Thất bại", null);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("token") != null) {
                    String token = jsonNode.get("token").asText();
                    FacebookTemplate template = new FacebookTemplate(token);
                    String[] fields = {"id", "email", "name", "first_name", "last_name"};
                    org.springframework.social.facebook.api.User profile = template.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
                    String fbID = profile.getId();

                    Brand brandByFbId = brandRepository.findBrandByFbId(fbID);
                    if (ObjectUtils.isEmpty(brandByFbId)) {
                        Brand brand = new Brand();
                        brand.setFbId(fbID);
                        brand.setStatus(Constant.NOT_REGISTER);
                        brandRepository.save(brand);
                        responseData = new ResponseData(Constant.FIRST_LOGIN_FB, "Thành Công", new ResponseBase(null, brand));
                    } else {
                        responseData = new ResponseData(Constant.SUCCESS, "Thành Công", new ResponseBase(null, brandByFbId));
                    }

                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
