package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.Brand;
import com.hitex.halago.model.DAO.BrandDao;
import com.hitex.halago.model.User;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.BrandRepository;
import com.hitex.halago.repository.UserRepository;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.BrandService;
import com.hitex.halago.service.impl.MailServiceImpl;
import com.hitex.halago.utils.RequestUtils;
import com.hitex.halago.utils.ValidateUltils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
public class BrandController {
    Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    BrandService brandService;
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    MailServiceImpl mailService;

    @Autowired
    private JwtService jwtService;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

    @RequestMapping(value = "/findBrandById", method = RequestMethod.POST)
    public ResponseEntity<?> findPersonalById(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role) || Constant.STAFF.equals(role)) {
                    if (jsonNode.get("id") != null) {
                        int id = jsonNode.get("id").asInt();
                        Brand brand = brandService.findBrandById(id);
                        responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, brand));
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

//    @RequestMapping(value = "/findBrandByName", method = RequestMethod.POST)
//    public ResponseEntity<?> findPersonalByName(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
//                    String name = jsonNode.get("brandName").asText();
//                    String email = jsonNode.get("brandEmail").asText();
//                    String phone = jsonNode.get("brandPhone").asText();
//                    Brand brand = brandService.findBrandByName(name, email, phone);
//                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, brand));
//                } else {
//                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền tìm kiếm", null);
//                }
//            }
//        } catch (Exception e) {
//            logger.info(e.getMessage(), e.getCause());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

    @RequestMapping(value = "/getListBrand", method = RequestMethod.POST)
    public ResponseEntity<?> findAllPersonal(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role) || Constant.STAFF.equals(role)) {
                    String name = jsonNode.get("name").asText();
                    String status = jsonNode.get("status").asText();
                    Integer pageSize = jsonNode.get("pageSize").asInt();
                    Integer pageNumber = jsonNode.get("pageNumber").asInt();
                    if (StringUtils.isEmpty(status)) {
                        status = "-1";
                    }
                    if (StringUtils.isEmpty(name)) {
                        name = null;
                    }
                    List<Brand> brands = brandService.getListBrand(name, Integer.parseInt(status), pageSize, pageNumber);
                    ResponseBase responseBase = new ResponseBase();
                    responseBase.setSiRes(null);
                    responseBase.setData(brands);
                    responseBase.setTotal(brandRepository.getListBrand(name, Integer.parseInt(status)));
                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/insertBrand", method = RequestMethod.POST)
    public ResponseEntity<?> insertPersonal(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Thêm nhãn hiệu thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
                    String password = jsonNode.get("password").asText();
                    String email = jsonNode.get("brandEmail").asText();
                    boolean validEmail = ValidateUltils.validateEmail(email);
                    if (!validEmail) {
                        responseData = new ResponseData(Constant.FAILED, "Email không hợp lệ", null);
                    } else {
                        Brand brand = new Brand();
                        brand.setBrandName(jsonNode.get("brandName").asText());
                        brand.setBrandEmail(jsonNode.get("brandEmail").asText());
                        brand.setBrandPhone(jsonNode.get("brandPhone").asText());
                        brand.setDescription(jsonNode.get("description").asText());
                        brand.setWebsite(jsonNode.get("website").asText());
                        brand.setStatus(Constant.active);
                        brand.setRepresentativeName(jsonNode.get("representativeName").asText());
                        brand.setCreated(new Date());
                        brand.setLogo(jsonNode.get("logo").asText());
                        Brand mail = brandRepository.checkMailBrand(jsonNode.get("brandEmail").asText());
                        if (ObjectUtils.isEmpty(mail)) {
                            User user = new User();
                            user.setEmail(jsonNode.get("brandEmail").asText());
                            user.setRole(Integer.parseInt(Constant.BRAND));
                            user.setCreated(new Date());
                            user.setStatus(Constant.active);
                            user.setPassword(password);
                            user.setUsername(jsonNode.get("brandEmail").asText());
                            userRepository.save(user);
                            brandService.insertBrand(brand);

                            mailService.sendEmail("Nhãn hàng đăng ký mới trên Halago",
                                    "Nhãn hàng " + brand.getBrandName() +"\n" +
                                            "Mail :" + brand.getBrandEmail() + "\n" +
                                            "Số điện thoại :" + brand.getBrandPhone() +"\n" +
                                            "Người đăng ký : " + brand.getRepresentativeName());
                            responseData = new ResponseData(Constant.SUCCESS, "Thêm nhãn hiệu thành công", new ResponseBase(null, brand));
                        } else {
                            responseData = new ResponseData(Constant.FAILED, "Email Brand đã tồn tại", new ResponseBase(null, true));
                        }
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm nhãn hiệu", null);
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/insertBrandPortal", method = RequestMethod.POST)
    public ResponseEntity<?> insertBrandPortal(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Thêm nhãn hiệu thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String password = jsonNode.get("password").asText();
                String email = jsonNode.get("brandEmail").asText();
                boolean validEmail = ValidateUltils.validateEmail(email);
                if (!validEmail) {
                    responseData = new ResponseData(Constant.FAILED, "Email không hợp lệ", null);
                } else {
                    Brand brand = new Brand();
                    brand.setBrandName(jsonNode.get("brandName").asText());
                    brand.setBrandEmail(jsonNode.get("brandEmail").asText());
                    brand.setBrandPhone(jsonNode.get("brandPhone").asText());
                    brand.setDescription(jsonNode.get("description").asText());
                    brand.setWebsite(jsonNode.get("website").asText());
                    brand.setStatus(Constant.active);
                    brand.setRepresentativeName(jsonNode.get("representativeName").asText());
                    brand.setCreated(new Date());
                    if (jsonNode.has("logo")){
                        brand.setLogo(jsonNode.get("logo").asText());
                    }else {
                        brand.setLogo("");
                    }
                    Brand mail = brandRepository.checkMailBrand(jsonNode.get("brandEmail").asText());
                    if (ObjectUtils.isEmpty(mail)) {
                        User user = new User();
                        user.setEmail(jsonNode.get("brandEmail").asText());
                        user.setRole(Integer.parseInt(Constant.BRAND));
                        user.setCreated(new Date());
                        user.setStatus(Constant.active);
                        user.setPassword(password);
                        user.setUsername(jsonNode.get("brandEmail").asText());
                        userRepository.save(user);
                        brandService.insertBrand(brand);
                        BrandDao brandDao = new BrandDao();
                        BeanUtils.copyProperties(brand, brandDao);
                        String accessToken = jwtService.generateTokenLogin(brand.getBrandName(), Constant.BRAND);
                        brandDao.setToken(accessToken);
                        mailService.sendEmail("Nhãn hàng đăng ký mới trên Halago",
                                "Nhãn hàng " + brand.getBrandName() +"\n" +
                                        "Mail :" + brand.getBrandEmail() + "\n" +
                                        "Số điện thoại :" + brand.getBrandPhone() +"\n" +
                                        "Người đăng ký : " + brand.getRepresentativeName());
                        responseData = new ResponseData(Constant.SUCCESS, "Thêm nhãn hiệu thành công", new ResponseBase(null, brandDao));
                    } else {
                        responseData = new ResponseData(Constant.FAILED, "Email Brand đã tồn tại", new ResponseBase(null, true));
                    }
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateBrand", method = RequestMethod.POST)
    public ResponseEntity<?> updatePersonal(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Cập nhật nhãn hiệu thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                String email = jsonNode.get("brandEmail").asText();
                boolean validEmail = ValidateUltils.validateEmail(email);
                if (!validEmail) {
                    responseData = new ResponseData(Constant.FAILED, "Email không hợp lệ", null);
                } else {
                    if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
                        Brand brand = brandRepository.findBrand(jsonNode.get("id").asInt());
//                    brand.setId(jsonNode.get("id").asInt());
                        brand.setBrandName(jsonNode.get("brandName").asText());
                        brand.setBrandPhone(jsonNode.get("brandPhone").asText());
                        brand.setDescription(jsonNode.get("description").asText());
                        brand.setLogo(jsonNode.get("logo").asText());
                        brand.setWebsite(jsonNode.get("website").asText());
                        brand.setStatus(Constant.active);
                        brand.setRepresentativeName(jsonNode.get("representativeName").asText());
//                    brand.setCreated(new Date());
                        Brand mail = brandRepository.checkMailBrand(brand.getBrandEmail());
                        if (!ObjectUtils.isEmpty(mail) && mail.getId() != brand.getId()) {
                            responseData = new ResponseData(Constant.FAILED, "Email Brand đã tồn tại", new ResponseBase(null, true));
                        } else {
                            User user = userRepository.findUser(brand.getBrandEmail());
                            if (!ObjectUtils.isEmpty(user)) {
                                user.setUsername(jsonNode.get("brandEmail").asText());
                                user.setEmail(jsonNode.get("brandEmail").asText());
                                userRepository.save(user);
                            }
                            brand.setBrandEmail(email);
                            brandService.updateBrand(brand);
                            responseData = new ResponseData(Constant.SUCCESS, "Cập nhật nhãn hiệu thành công", new ResponseBase(null, brand));
                        }
                    } else {
                        responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền cập nhật nhãn hiệu", null);
                    }
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/deleteBrand", method = RequestMethod.POST)
    public ResponseEntity<?> deletePersonal(InputStream inputStream) {
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
                        int status = brandService.deleteBrand(id);
                        if (status == Constant.SUCCESS) {
                            responseData = new ResponseData(Constant.SUCCESS, "Xoá nhãn hiệu Thành công", new ResponseBase(null, true));
                        } else {
                            responseData = new ResponseData(Constant.FAILED, "Xoá nhãn hiệu thất bại", new ResponseBase(null, true));
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
}

