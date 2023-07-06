package com.hitex.halago.controllerApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.DAO.PersonalMod;
import com.hitex.halago.model.DAO.PersonalModel;
import com.hitex.halago.model.Personal;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.PersonalRepository;
//import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.PersonalService;
import com.hitex.halago.service.impl.Mailservice;
import com.hitex.halago.utils.DateUtils;
import com.hitex.halago.utils.RequestUtils;
import com.hitex.halago.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.InputStream;
import java.util.List;

@RestController
public class PersonalControllerApp {
//    @Autowired
//    private DeviceRepository deviceRepository;
//    @Autowired
//    TrainingRepository trainingRepository;
//    @Autowired
//    TrainingService trainingService;
//    @Autowired
//    private RankService rankService;
//    @Autowired
//    JwtService jwtService;
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private PersonalService personalService;
    @Autowired
    private Mailservice mailservice;
    @PersistenceContext
    private EntityManager entityManager;
    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);
    @RequestMapping(value = "/changeName", method = RequestMethod.POST)
    public ResponseEntity<?> updateName(InputStream inputStream) {
        responseData = new ResponseData(1, "loi", null);
        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                String full_name = "";
                int id;
                if (jsonNode.get("full_name") != null) {
                    Personal personal = new Personal();
                    id = personal.setIdPersonal(jsonNode.get("idPersonal").asInt());
                    full_name = personal.setFullName(jsonNode.get("full_name").asText());
                    personalService.updateName(id, full_name);
                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, personal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/changeBirthday", method = RequestMethod.POST)
    public ResponseEntity<?> updatePhone(InputStream inputStream) {
        responseData = new ResponseData(1, "loi", null);
        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                int id;
                if (jsonNode.get("birthday") != null) {
                    id = jsonNode.get("idPersonal").asInt();
                    String birthday = jsonNode.get("birthday").asText();
                    int birth= DateUtils.parseDateToTimeStamp(birthday);
                    personalService.updateBirth(id, birth);
                    responseData = new ResponseData(0, "thanh cong", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/changeGender", method = RequestMethod.POST)
    public ResponseEntity<?> updateGender(InputStream inputStream) {
        responseData = new ResponseData(1, "loi", null);
        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                String gender = "";
                int id;
                if (jsonNode.get("gender") != null) {
                    Personal personal = new Personal();
                    id = personal.setIdPersonal(jsonNode.get("idPersonal").asInt());
                    gender = personal.setGender(jsonNode.get("gender").asText());
                    personalService.updateGender(id, gender);
                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, personal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/changeCard", method = RequestMethod.POST)
    public ResponseEntity<?> updateCard(InputStream inputStream) {
        responseData = new ResponseData(1, "loi", null);
        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                String cmnd;
                int id;
                if (jsonNode.get("cmnd") != null) {
                    Personal personal = new Personal();
                    id = personal.setIdPersonal(jsonNode.get("idPersonal").asInt());
                    cmnd = personal.setCmnd(jsonNode.get("cmnd").asText());
                    personalService.updateCard(id, cmnd);
                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, personal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/changeAccountBank", method = RequestMethod.POST)
    public ResponseEntity<?> updateAccountBank(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "loi", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                String bank_name = "";
                String bank_branch = "";
                String bank_account;
                int id;
                if (!jsonNode.hasNonNull("bankAccount")) {
                    responseData = new ResponseData(1, "không được để trống các ô", null);
                } else if (jsonNode.get("idPersonal") != null && jsonNode.get("bankAccount") != null && jsonNode.get("bankName") != null && jsonNode.get("bankBranch") != null) {
                    Personal personal = new Personal();
                    id = personal.setIdPersonal(jsonNode.get("idPersonal").asInt());
                    bank_account = personal.setBankAccount(jsonNode.get("bankAccount").asText());
                    bank_name = personal.setBankName(jsonNode.get("bankName").asText());
                    bank_branch = personal.setBankBranch(jsonNode.get("bankBranch").asText());
                    personalService.updateAccountBank(id, bank_account, bank_name, bank_branch);
                    responseData = new ResponseData(0, "Cập nhật thành công", new ResponseBase(null, personal));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/changeAvatar", method = RequestMethod.POST)
    public ResponseEntity<?> updateAvatar(InputStream inputStream) {
        responseData = new ResponseData(1, "loi", null);
        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                String avatar = "";
                int id;
                if (jsonNode.get("avatar") != null && jsonNode.get("idPersonal") != null) {
                    Personal personal = new Personal();
                    id = personal.setIdPersonal(jsonNode.get("idPersonal").asInt());
                    avatar = personal.setAvatar(jsonNode.get("avatar").asText().replaceAll(Constant.IMAGE_DOMAIN, ""));
                    personalService.updateAvatar(id, avatar);
                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, personal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/changePass", method = RequestMethod.POST)
    public ResponseEntity<?> updatePass(InputStream inputStream) {
        responseData = new ResponseData(1, "loi", null);
        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                String new_pass = "";
                String old_password = "";
                int id;
                if (jsonNode.get("new_pass") != null && jsonNode.get("idPersonal") != null && jsonNode.get("oldPass") != null) {
                    id = jsonNode.get("idPersonal").asInt();
                    old_password = jsonNode.get("oldPass").asText();
                    new_pass = jsonNode.get("new_pass").asText();
                    String personal1 = String.valueOf(personalRepository.findPass(id));
                    if (!(old_password.equalsIgnoreCase(personal1))) {

                        responseData = new ResponseData(1, "hãy nhập đúng passowrd đang sử dụng", null);
                    } else {
                        if (new_pass.equalsIgnoreCase(personal1)) {
                            responseData = new ResponseData(1, "Hãy sử dụng Password chưa được sử dụng", null);
                        } else {
                            personalService.updatePass(id, new_pass);
                            responseData = new ResponseData(0, "thanh cong", null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/loginApp", method = RequestMethod.POST)
    public ResponseEntity<?> login(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Lỗi", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String phone = "";
                String pass = "";
                String result="";
                if (jsonNode.get("phone") != null) {
                    phone = jsonNode.get("phone").asText();
                }
                if (jsonNode.get("password") != null) {
                    pass = jsonNode.get("password").asText();
                }

                Personal personal = personalService.login(phone);
                if (personal != null) {
                    String passs = personalRepository.findPasswordPersonal(phone);
                    if (pass.equals(passs)) {
//                        result=jwtService.generateTokenLogin(phone);
                        responseData = new ResponseData(0, "OK", new ResponseBase(null,personal));
                        personalService.updateActive(personal.getIdPersonal());
                    } else {
                        responseData = new ResponseData(0, "sai mat khau", null);
                    }
                } else {
                    responseData = new ResponseData(1, "Tài khoản không tồn tại!", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public ResponseEntity<?> register(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Lỗi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                String full_name = "";
//                String phone = "";
//                String email = "";
//                String pass = "";
//                if (jsonNode.get("fullName") != null && jsonNode.get("phone") != null && jsonNode.get("email") != null && jsonNode.get("password") != null) {
//                    full_name = jsonNode.get("fullName").asText();
//                    phone = jsonNode.get("phone").asText();
//                    email = jsonNode.get("email").asText();
//                    pass = jsonNode.get("password").asText();
//                }
//                Personal p = personalRepository.findByEmail(email);
//                if (p != null) {
//                    responseData = new ResponseData(1, "Email đã tồn tại", null);
//                }
//                Personal personals = personalRepository.findByPhone(phone);
//                if (personals != null) {
//                    responseData = new ResponseData(1, "Account đã tồn tại", null);
//                } else {
//                    Personal personal = personalService.register(full_name, phone, email, pass);
//                    if (personal != null) {
//                        responseData = new ResponseData(0, "Thành công", new ResponseBase(null, personal));
//                        Personal personals1 = personalRepository.findByPhone(phone);
//                        NotificationPush notificationPush = new NotificationPush();
//                        List<Device> devices = new ArrayList<>();
//                        devices = deviceRepository.getFcmTokenByIdPersonal(personals.getIdPersonal());
//                        notificationPush.FunctionPush(devices,"Đăng Ký Tài Khoản","Thành Công");
//
//                    } else {
//                        responseData = new ResponseData(1, "lỗi", null);
//                    }
//                }
//            } else {
//                responseData = new ResponseData(1, "Not register Account", null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

    @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    public ResponseEntity<?> forgotpass(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Lỗi", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String email = "";
                if (jsonNode.get("email") != null) {
                    email = jsonNode.get("email").asText();
                    Personal member = personalRepository.findEmail(email);
                    if (member != null) {

                        int a = StringUtils.Radom(100000, 999999);

                        mailservice.sendSimpleMail(member.getEmail(), a);
                        responseData = new ResponseData(0, "Mật khẩu mới đã được gửi về mail của bạn!", null);
                    } else {
                        responseData = new ResponseData(1, "Account không tồn tại", null);
                    }
                }
            } else {
                responseData = new ResponseData(1, "Lỗi rồi", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/memberInfo", method = RequestMethod.POST)
    public ResponseEntity<?> findPersonal(InputStream inputStream) {
        responseData = new ResponseData(1, "loi", null);
        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (jsonNode.get("idPersonal") != null) {
                int id = jsonNode.get("idPersonal").asInt();
                List<PersonalModel> personal = personalService.findPersonalA(id);
                responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, personal));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
//    @RequestMapping(value = "/loginFb", method = RequestMethod.POST)
//    ResponseEntity<?> loginFb(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("token") != null) {
//                    String token = jsonNode.get("token").asText();
//                    FacebookTemplate template = new FacebookTemplate(token);
//                    String[] fields = {"id", "email", "name", "first_name", "last_name"};
//                    User profile = template.fetchObject("me", User.class, fields);
//                    String fbID = profile.getId();
//                    System.out.println("fb :" + fbID);
//
//                    String name = profile.getName();
//                    System.out.println("name: " + name);
//                    String avatar = "https://graph.facebook.com/" + fbID + "/picture?type=large&wΓÇîΓÇïidth=300&height=300 ";
//                    String phone = "";
//
//                    if (phone == "" && fbID != null && name != null && avatar != null) {
//                        Personal findFbId = personalRepository.findByFbID(fbID);
//                        if (findFbId == null) {
//                            Personal p1 = new Personal();
//                            p1.setFbID(fbID);
//                            p1.setFullName(name);
//                            p1.setAvatar(avatar);
//                            String b = String.valueOf(StringUtils.Radom(111111, 99999999));
//                            Personal facebook1 = personalRepository.findIntroduceCode(b);
//                            while (facebook1 != null) {
//                                b = (String.valueOf(StringUtils.Radom(111111, 99999999)));
//                            }
//                            p1.setIntroduceCodeUser(b);
//                            personalRepository.save(p1);
//                            boolean checkphone = true;
//                            Personal personal = personalRepository.findPersonal(p1.getIdPersonal());
//                            if (personal.getPhone() == null || personal.getPhone().isEmpty()) {
//                                checkphone = false;
//                            }
//                            PersonalMod p = new PersonalMod();
//                            p.setIdPersonal(p1.getIdPersonal());
//                            p.setAddress(p1.getAddress());
//                            p.setAvatar(p1.getAvatar());
//                            p.setPhone(false);
//                            p.setIntroduceCodeUser(p1.getIntroduceCodeUser());
//                            p.setFullName(p1.getFullName());
//                            p.setEmail(p1.getEmail());
//                            responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, p));
//                        }
//                        else {
//                            personalRepository.updatePersonalByFbId(fbID, avatar, name, findFbId.getIdPersonal());
//                            System.out.println("phone: " + findFbId.getPhone());
//                            if (findFbId.getPhone() == null || findFbId.getPhone().isEmpty() == true) {
//                                PersonalMod pm = new PersonalMod();
//                                pm.setIdPersonal(findFbId.getIdPersonal());
//                                pm.setAddress(findFbId.getAddress());
//                                pm.setAvatar(findFbId.getAvatar());
//                                pm.setPhone(false);
//                                pm.setIntroduceCodeUser(findFbId.getIntroduceCodeUser());
//                                pm.setFullName(findFbId.getFullName());
//                                pm.setEmail(findFbId.getEmail());
//                                responseData = new ResponseData(0, "da thanh cong", new ResponseBase(null, pm));
//                            } else {
//                                PersonalModel pm = new PersonalModel();
//                                pm.setIdPersonal(findFbId.getIdPersonal());
//                                pm.setAddress(findFbId.getAddress());
//                                pm.setAvatar(findFbId.getAvatar());
//                                pm.setIntroduceCodeUser(findFbId.getIntroduceCodeUser());
//                                pm.setFullName(findFbId.getFullName());
//                                pm.setEmail(findFbId.getEmail());
//                                pm.setPhone(findFbId.getPhone());
//                                responseData = new ResponseData(0, "da thanh cong co so dt", new ResponseBase(null, pm));
////
//                            }
//
//                        }
//
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

    @RequestMapping(value = "/changePhone", method = RequestMethod.POST)
    public ResponseEntity<?> changePhone(InputStream inputStream) {
        responseData = new ResponseData(1, "loi", null);
        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (jsonNode.get("phone") != null && jsonNode.get("idPersonal") != null) {
                    String phone = jsonNode.get("phone").asText();
                    int id = jsonNode.get("idPersonal").asInt();
                    personalRepository.updatePhone(id, phone);
                    responseData = new ResponseData(0, "thanh cong", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateActiveOff", method = RequestMethod.POST)
    public ResponseEntity<?> updateActiveOff(InputStream inputStream) {
        responseData = new ResponseData(1, "loi", null);
        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (jsonNode.get("idPersonal") != null) {
                    int id = jsonNode.get("idPersonal").asInt();
                    personalService.updateActiveOff(id);
                    responseData = new ResponseData(0, null/**/, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
    @RequestMapping(value = "/setIntroduceCode", method = RequestMethod.POST)
    ResponseEntity<?> setIntroduceCode(InputStream inputStream) {
        try {
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            responseData = new ResponseData(1, "loi", null);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("introduceCode") != null && jsonNode.get("idPersonal") != null) {
                    int id = jsonNode.get("idPersonal").asInt();
                    String introduceCode = jsonNode.get("introduceCode").asText();
                    Personal getIntroduce = personalRepository.findIntroduceCode(introduceCode);
                    if (getIntroduce != null) {
                        Personal personal = new Personal();
                        int referenceID = personalRepository.findIntroduce(introduceCode);
                        personal.setIdPersonal(id);
                        personal.setReferenceId(referenceID);
                        personalService.updateReferenceId(id, referenceID);
                        responseData = new ResponseData(0, "Success", new ResponseBase(null, personal));
                    } else {
                        responseData = new ResponseData(0, "ma gioi thieu khong dung", null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
//    @RequestMapping(value = "/findAllRank", method = RequestMethod.POST)
//    public ResponseEntity<?> findAllRank(InputStream inputStream) {
//
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//
//                List<Rank> ranks = rankService.findAll();
//                responseData = new ResponseData(0, "Tìm kiếm thành công", new ResponseBase(null, ranks));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//    @RequestMapping(value = "/findOnline", method = RequestMethod.POST)
//    public ResponseEntity<?> findOnline(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                List<Training> tranning = trainingService.findTypeOnline();
//                responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, tranning));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

//    @RequestMapping(value = "/findOffline", method = RequestMethod.POST)
//    public ResponseEntity<?> findOffline(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                List<Training> tranning = trainingService.findTypeOffline();
//                responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, tranning));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
}
