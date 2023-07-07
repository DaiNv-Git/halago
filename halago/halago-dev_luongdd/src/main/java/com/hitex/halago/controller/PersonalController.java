package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.model.dao.*;
import com.hitex.halago.model.Personal;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.PersonalRepository;
import com.hitex.halago.repository.RolePersonalRepository;
import com.hitex.halago.service.PersonalService;
import com.hitex.halago.service.impl.Mailservice;
import com.hitex.halago.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonalController {
    @Autowired
    private RolePersonalRepository rolePersonalRepository;
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private PersonalService personalService;
    @Autowired
    private Mailservice mailservice;
    @PersistenceContext
    private EntityManager entityManager;
    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);



    @RequestMapping(value = "/findSale",method = RequestMethod.POST)
    public ResponseEntity<?> findSale(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                System.out.println(jsonNode);
                    List<Personal> personal = personalRepository.findSale();
                    responseData = new ResponseData(0, "Tìm kiếm thành công", new ResponseBase(null, personal));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


//    @RequestMapping(value = "/getPersonalByIntroduceCode",method = RequestMethod.POST)
//    public ResponseEntity<?> getPersonalByIntroduceCode(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                if (jsonNode.get("idPersonal") != null) {
//                    int id = jsonNode.get("idPersonal").asInt();
////                    int page = jsonNode.get("page").asInt();
//                    List<MemberOfPersonal> personal = personalService.getPersonalByIntroduceCode(id);
//                    responseData = new ResponseData(0, "Tìm kiếm thành công", new ResponseBase(null, personal));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

    @RequestMapping(value = "/getInfoMemberPersonal",method = RequestMethod.POST)
    public ResponseEntity<?> getInfoMemberPersonal(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                System.out.println(jsonNode);
                if (jsonNode.get("idPersonal") != null) {
                    int id = jsonNode.get("idPersonal").asInt();
                    List personal = new ArrayList();
                    responseData = new ResponseData(0, "Tìm kiếm thành công", new ResponseBase(null, personal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getListInferiority",method = RequestMethod.POST)
    public ResponseEntity<?> getListInferiority(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("idPersonal") != null) {
                    int id = jsonNode.get("idPersonal").asInt();
//                    int page = jsonNode.get("page").asInt();
                    List<PersonalDao> personal = personalService.getListInferiority(id);
                    responseData = new ResponseData(0, "Tìm kiếm thành công", new ResponseBase(null, personal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @RequestMapping(value = "/getListProjectJoin",method = RequestMethod.POST)
    public ResponseEntity<?> getListProjectJoin(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("idPersonal") != null) {
                    int id = jsonNode.get("idPersonal").asInt();
//                    int page = jsonNode.get("page").asInt();
                    List personal = new ArrayList();
                    responseData = new ResponseData(0, "Tìm kiếm thành công", new ResponseBase(null, personal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @RequestMapping(value = "/getListCustomerStateCare",method = RequestMethod.POST)
    public ResponseEntity<?> getListCustomerStateCare(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("idPersonal") != null) {
                    int id = jsonNode.get("idPersonal").asInt();
//                    int page = jsonNode.get("page").asInt();
                    List personal = new ArrayList();
                    responseData = new ResponseData(0, "Tìm kiếm thành công", new ResponseBase(null, personal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @RequestMapping(value = "/findPersonalById",method = RequestMethod.POST)
    public ResponseEntity<?> findPersonalById(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("idPersonal") != null) {
                    int id = jsonNode.get("idPersonal").asInt();
                    Personal personal = personalService.findPersonal(id);
                    responseData = new ResponseData(0, "Tìm kiếm thành công", new ResponseBase(null, personal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/findAllPersonal",method = RequestMethod.POST)
    public ResponseEntity<?> findAllPersonal(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                List<Personal> personals = personalService.findAll();
                responseData = new ResponseData(0, "Tìm kiếm thành công", new ResponseBase(null, personals));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

//    @RequestMapping(value = "/insertPersonal",method = RequestMethod.POST)
//    public ResponseEntity<?> insertPersonal(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Thêm người dùng không thành công", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                String phone = "";
//                String email = "";
//                String bankAccount = "";
//                String date;
//                String cmnd = "";
//                String code = "";
//                if (rolePersonalRepository.getRolePersonal(baseRequest.getPersonalID()) == 1
//                        || rolePersonalRepository.getRolePersonal(baseRequest.getPersonalID()) == 4) {
//                    if (jsonNode.get("phone") != null) {
//                        phone = jsonNode.get("phone").asText();
//                        Personal ps = personalService.findByPhone(phone);
//                        if (phone.length() > 11) {
//                            responseData = new ResponseData(1, "Số điện thoại sai định dạng : " + phone, null);
//                        } else if (ps != null) {
//                            responseData = new ResponseData(1, "Số điện thoại đã tồn tại : " + phone, null);
//                        } else if (jsonNode.get("cmnd") != null) {
//                            cmnd = jsonNode.get("cmnd").asText();
//                            Personal ps0 = personalService.findByCmnd(cmnd);
//                            if (cmnd.length() > 12) {
//                                System.out.println(cmnd.length());
//                                responseData = new ResponseData(1, "Số cmnd không hợp lệ : " + cmnd, null);
//                            } else if (ps0 != null) {
//                                System.out.println("---=-=----");
//                                responseData = new ResponseData(1, "Số cmnd đã tồn tại : " + cmnd, null);
//                            } else if (jsonNode.get("bankAccount") != null) {
//                                bankAccount = jsonNode.get("bankAccount").asText();
//                                Personal ps2 = personalService.findBankAccount(bankAccount);
//                                if (bankAccount.length() > 15) {
//                                    responseData = new ResponseData(1, "Số tài khoản ngân hàng không hợp lệ: " + bankAccount, null);
//                                } else if (ps2 != null) {
//                                    responseData = new ResponseData(1, "Số tài khoản ngân hàng đã tồn tại : " + bankAccount, null);
//                                } else if (jsonNode.get("email") != null) {
//                                    email = jsonNode.get("email").asText();
//                                    Personal ps3 = personalRepository.findEmail(email);
//                                    if (!email.matches(valiemail)) {
//                                        responseData = new ResponseData(1, "Email sai định dạng : " + email, null);
//                                    } else if (ps3 != null) {
//                                        responseData = new ResponseData(1, "Email đã có người dùng : " + email, null);
//                                    } else if (jsonNode.get("introduceCodeUser") != null) {
//                                        code = personalService.AutoGenCode();
//                                        Personal ps4 = personalRepository.findCode(code);
//                                        if (ps4 != null) {
//                                            responseData = new ResponseData(1, "Mã code  đã tồn tại : " + code, null);
//                                        } else if (jsonNode.get("avatar") != null && jsonNode.get("regency") != null && jsonNode.get("fullName") != null && jsonNode.get("introduceCodeUser") != null && jsonNode.get("birthDay") != null && jsonNode.get("gender") != null && jsonNode.get("address") != null && jsonNode.get("phone") != null && jsonNode.get("email") != null && jsonNode.get("cmnd") != null && jsonNode.get("joinDate") != null && jsonNode.get("bankAccount") != null && jsonNode.get("bankName") != null && jsonNode.get("bankBranch") != null && jsonNode.get("password") != null && jsonNode.get("status") != null) {
//                                            Personal personal = new Personal();
//                                            RolePersonal rolePersonal = new RolePersonal();
//                                            personal.setAvatar(jsonNode.get("avatar").asText().replaceAll(Constant.IMAGE_DOMAIN, ""));
//                                            personal.setFullName(jsonNode.get("fullName").asText());
//                                            personal.setIntroduceCodeUser(personalService.AutoGenCode());
//                                            String time = jsonNode.get("birthDay").asText();
//                                            personal.setBirthDay(String.valueOf(DateUtils.parseDateToTimeStamp(time)));
//                                            personal.setGender(jsonNode.get("gender").asText());
//                                            personal.setAddress(jsonNode.get("address").asText());
//                                            personal.setPhone(jsonNode.get("phone").asText());
//                                            personal.setEmail(jsonNode.get("email").asText());
//                                            personal.setCmnd(jsonNode.get("cmnd").asText());
//                                            date = jsonNode.get("joinDate").asText();
//                                            personal.setJoinDate(date);
//                                            personal.setBankAccount(jsonNode.get("bankAccount").asText());
//                                            personal.setBankName(jsonNode.get("bankName").asText());
//                                            personal.setBankBranch(jsonNode.get("bankBranch").asText());
//                                            personal.setPassword(jsonNode.get("password").asText());
//                                            personal.setStatus(jsonNode.get("status").asInt());
//                                            personal.setRegency(jsonNode.get("regency").asText());
//                                            personal.setIdRole(jsonNode.get("idRole").asInt());
//                                            personal.setImageCmnd(jsonNode.get("imageCmnd").asText().replaceAll(Constant.IMAGE_DOMAIN, ""));
////                                            Personal personal3 = personalService.insertPersonal(personal);
//                                            responseData = new ResponseData(0, "Thêm người dùng thành công", new ResponseBase(null, personal));
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            } else {
//                responseData = new ResponseData(0, "Bạn không có quyền thêm", null);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/updatePersonal", method = RequestMethod.POST)
//    public ResponseEntity<?> updatePersonal(InputStream inputStream) {
//
//        try {
//            responseData = new ResponseData(1, "Sửa không thành công", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                String date;
//                String bankAccount = "";
//                String cmnd = "";
//                String phone = "";
//                String email = "";
//                if (rolePersonalRepository.getRolePersonal(baseRequest.getPersonalID()) == 1
//                        || rolePersonalRepository.getRolePersonal(baseRequest.getPersonalID()) == 4) {
//                    if (jsonNode.get("phone") != null) {
//                        phone = jsonNode.get("phone").asText();
//                        if (phone.length() > 11) {
//                            responseData = new ResponseData(1, "Số điện thoại sai định dạng : " + phone, null);
//                        } else if (jsonNode.get("cmnd") != null) {
//                            cmnd = jsonNode.get("cmnd").asText();
//                            if (cmnd.length() > 12) {
//                                System.out.println(cmnd.length());
//                                responseData = new ResponseData(1, "Số cmnd không hợp lệ : " + cmnd, null);
//                            } else if (jsonNode.get("bankAccount") != null) {
//                                bankAccount = jsonNode.get("bankAccount").asText();
//                                if (bankAccount.length() > 15) {
//                                    responseData = new ResponseData(1, "Số tài khoản ngân hàng nhỏ hơn 15 số: " + bankAccount, null);
//                                } else if (jsonNode.get("email") != null) {
//                                    email = jsonNode.get("email").asText();
//                                    System.out.println(email);
//                                    if (!email.matches(valiemail)) {
//                                        responseData = new ResponseData(1, "email sai định dạng : " + email, null);
//                                    } else if (jsonNode.get("idPersonal") != null && jsonNode.get("regency") != null && jsonNode.get("avatar") != null && jsonNode.get("fullName") != null && jsonNode.get("introduceCodeUser") != null && jsonNode.get("birthDay") != null && jsonNode.get("gender") != null && jsonNode.get("address") != null && jsonNode.get("phone") != null && jsonNode.get("email") != null && jsonNode.get("cmnd") != null && jsonNode.get("joinDate") != null && jsonNode.get("bankAccount") != null && jsonNode.get("bankName") != null && jsonNode.get("bankBranch") != null && jsonNode.get("password") != null && jsonNode.get("status") != null) {
//                                        Personal personal = new Personal();
//                                        personal.setIdPersonal(jsonNode.get("idPersonal").asInt());
//                                        personal.setAvatar(jsonNode.get("avatar").asText().replaceAll(Constant.IMAGE_DOMAIN, ""));
//                                        personal.setFullName(jsonNode.get("fullName").asText());
//                                        personal.setIntroduceCodeUser(jsonNode.get("introduceCodeUser").asText());
//                                        personal.setBirthDay(String.valueOf((DateUtils.parseDateToTimeStamp(jsonNode.get("birthDay").asText()))));
//                                        personal.setGender(jsonNode.get("gender").asText());
//                                        personal.setAddress(jsonNode.get("address").asText());
//                                        personal.setPhone(jsonNode.get("phone").asText());
//                                        personal.setEmail(jsonNode.get("email").asText());
//                                        personal.setCmnd(jsonNode.get("cmnd").asText());
//                                        date = jsonNode.get("joinDate").asText();
//                                        personal.setJoinDate(date);
//                                        personal.setBankAccount(jsonNode.get("bankAccount").asText());
//                                        personal.setBankName(jsonNode.get("bankName").asText());
//                                        personal.setBankBranch(jsonNode.get("bankBranch").asText());
//                                        personal.setPassword(jsonNode.get("password").asText());
//                                        personal.setStatus(jsonNode.get("status").asInt());
//                                        personal.setRegency(jsonNode.get("regency").asText());
//                                        personal.setIdRole(jsonNode.get("idRole").asInt());
//                                        personal.setImageCmnd(jsonNode.get("imageCmnd").asText().replaceAll(Constant.IMAGE_DOMAIN, ""));
//                                        Personal personal1 = personalService.updatePersonal(personal);
//                                        responseData = new ResponseData(0, "Sửa thành công", new ResponseBase(null, personal));
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            } else {
//                responseData = new ResponseData(0, "Bạn không có quyền sửa", null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/deletePersonal", method = RequestMethod.POST)
//    public ResponseEntity<?> deletePersonal(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Xoá không thành công", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                if (rolePersonalRepository.getRolePersonal(baseRequest.getPersonalID()) == 1
//                        || rolePersonalRepository.getRolePersonal(baseRequest.getPersonalID()) == 4) {
//                    if (jsonNode.get("idPersonal") != null) {
//                        int id = jsonNode.get("idPersonal").asInt();
//                        System.out.println("Đã xoá person" + id);
//                        int status = personalService.deletePersonal(id);
//                        if (status > 0) {
//                            responseData = new ResponseData(0, "Xoá Thành công", new ResponseBase(null, true));
//                        } else {
//                            responseData = new ResponseData(1, "Không tìm thấy", new ResponseBase(null, true));
//                        }
//                    }
//                } else {
//                    responseData = new ResponseData(0, "Bạn không có quyền thêm", null);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/mglogin", method = RequestMethod.POST)
//    public ResponseEntity<?> mglogin(InputStream inputStream) {
//        responseData = new ResponseData(1, "Không có kết quả trả về", null);
//        BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//            System.out.println(jsonNode);
//            String phone = "";
//            String pass = "";
//            if (jsonNode.get("phone") != null) {
//                phone = jsonNode.get("phone").asText();
//                Personal ps = personalService.findPersonalByEmailOrPhone(phone);
//                if (ps == null) {
//                    responseData = new ResponseData(1, "Tài khoản không tồn tại : " + phone, null);
//                } else if (jsonNode.get("password") != null) {
//                    pass = jsonNode.get("password").asText();
//                    String ps1 = personalService.findPasswordPersonal(phone);
//                    if (ps1.equals(pass)) {
//                        PersonalLoginModel personal = personalService.mglogin(phone, pass);
//                        if (personal != null) {
//                            responseData = new ResponseData(0, "OK", new ResponseBase(null, personal));
//                        } else {
//                            responseData = new ResponseData(1, "Tài khoản không tồn tại!", null);
//                        }
//                    } else {
//                        responseData = new ResponseData(1, "Sai pass", null);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/updateInfoPersonal", method = RequestMethod.POST)
//    public ResponseEntity<?> updateInfoPersonal(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Sửa không thành công", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                String fullName, avatar, birthDay, gender, address, bankAccount, cmnd, phone, email, bankBranch, bankName, imageCmnd, IntroduceCodeUser;
//                int id;
//                if (jsonNode.get("phone") != null) {
//                    phone = jsonNode.get("phone").asText();
//                    if (phone.length() > 11) {
//                        responseData = new ResponseData(1, "Số điện thoại sai định dạng : " + phone, null);
//                    } else if (jsonNode.get("cmnd") != null) {
//                        cmnd = jsonNode.get("cmnd").asText();
//                        if (cmnd.length() > 12) {
//                            responseData = new ResponseData(1, "Số cmnd không hợp lệ : " + cmnd, null);
//                        } else if (jsonNode.get("bankAccount") != null) {
//                            bankAccount = jsonNode.get("bankAccount").asText();
//                            if (bankAccount.length() > 15) {
//                                responseData = new ResponseData(1, "Số tài khoản ngân hàng nhỏ hơn 15 số: " + bankAccount, null);
//                            } else if (jsonNode.get("email") != null) {
//                                email = jsonNode.get("email").asText();
//                                if (!email.matches(valiemail)) {
//                                    responseData = new ResponseData(1, "email sai định dạng : " + email, null);
//                                } else if (jsonNode.get("idPersonal") != null && jsonNode.get("avatar") != null && jsonNode.get("fullName") != null && jsonNode.get("birthDay") != null && jsonNode.get("gender") != null && jsonNode.get("address") != null && jsonNode.get("phone") != null && jsonNode.get("email") != null && jsonNode.get("cmnd") != null && jsonNode.get("bankAccount") != null && jsonNode.get("bankBranch") != null && jsonNode.get("bankName") != null) {
//                                    PersonalLoginModel personalModel = new PersonalLoginModel();
//                                    id = personalModel.setIdPersonal(jsonNode.get("idPersonal").asInt());
//                                    avatar = personalModel.setAvatar(jsonNode.get("avatar").asText().replaceAll(Constant.IMAGE_DOMAIN, ""));
//                                    fullName = personalModel.setFullName(jsonNode.get("fullName").asText());
//                                    String time = jsonNode.get("birthDay").asText();
//                                    birthDay = personalModel.setBirthDay(String.valueOf(DateUtils.parseDateToTimeStamp((time))));
//                                    gender = personalModel.setGender(jsonNode.get("gender").asText());
//                                    address = personalModel.setAddress(jsonNode.get("address").asText());
//                                    phone = personalModel.setPhone(jsonNode.get("phone").asText());
//                                    email = personalModel.setEmail(jsonNode.get("email").asText());
//                                    cmnd = personalModel.setCmnd(jsonNode.get("cmnd").asText());
//                                    bankAccount = personalModel.setBankAccount(jsonNode.get("bankAccount").asText());
//                                    bankBranch = personalModel.setBankBranch(jsonNode.get("bankBranch").asText());
//                                    bankName = personalModel.setBankName(jsonNode.get("bankName").asText());
//                                    imageCmnd = personalModel.setImageCmnd(StringUtils.convertArraytoString(StringUtils.getArrayStringJson(jsonNode, "imageCmnd")));
//                                    personalService.updatePersonalInfo(id, fullName, avatar, birthDay, gender, address, email, phone, cmnd, bankAccount, bankBranch, bankName,imageCmnd);
//                                    PersonalLoginModel personalLoginModel = personalService.findPersonalById(id);
//                                    responseData = new ResponseData(0, "Sửa thành công", new ResponseBase(null, personalLoginModel));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
}
