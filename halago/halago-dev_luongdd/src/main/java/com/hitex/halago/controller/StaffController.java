package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.*;
import com.hitex.halago.model.dao.FunctionApiDao;
import com.hitex.halago.model.dao.FunctionDao;
import com.hitex.halago.model.dao.RoleAccount;
import com.hitex.halago.model.dao.RoleDao;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.*;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.service.FunctionApiService;
import com.hitex.halago.service.RoleService;
import com.hitex.halago.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StaffController {
    Logger logger = LoggerFactory.getLogger(StaffController.class);
    @Autowired
    ApiRepository apiRepository;

    @Autowired
    FunctionApiRepository functionApiRepository;

    @Autowired
    FunctionRepository functionRepository;

    @Autowired
    FunctionApiService functionApiService;

    @Autowired
    RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleFunctionRepository roleFunctionRepository;


    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);
    @Autowired
    private JwtService jwtService;

    @RequestMapping(value = "/getListApi", method = RequestMethod.POST)
    public ResponseEntity<?> getListApi(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role)) {
                    List<Api> api = apiRepository.getListApi();
                    ResponseBase responseBase = new ResponseBase();
                    responseBase.setSiRes(null);
                    responseBase.setData(api);
                    responseBase.setTotal(api.size());
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

    @RequestMapping(value = "/getListFunction", method = RequestMethod.POST)
    public ResponseEntity<?> getListFunction(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    String name = String.valueOf(jsonNode.get("functionName").asText());
                    int pageNumber = jsonNode.get("pageNumber").asInt();
                    int pageSize = jsonNode.get("pageSize").asInt();
                    List<FunctionDao> functions = functionApiService.getListFunction(StringUtils.isBlank(name) ? "" : name, pageSize, pageNumber, role);
                    ResponseBase responseBase = new ResponseBase();
                    responseBase.setSiRes(null);
                    responseBase.setData(functions);
                    responseBase.setTotal(functions.size());
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

    @RequestMapping(value = "/findFunctionApi", method = RequestMethod.POST)
    public ResponseEntity<?> findFunctionApi(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    FunctionApiDao functions = functionApiService.getListApiByFunctionId(id);
                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, functions));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/insertFunctionApi", method = RequestMethod.POST)
    public ResponseEntity<?> insertFunctionApi(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    String functionName = jsonNode.get("functionName").asText();
                    ArrayList<Integer> listApi = com.hitex.halago.utils.StringUtils.getArrayIntegerJson(jsonNode, "idApi");
                    ArrayList<Integer> tempList = new ArrayList<Integer>();
                    for (Integer obj : listApi) {
                        if (!tempList.contains(obj)) {
                            tempList.add(obj);
                        }
                    }
                    String idApi = com.hitex.halago.utils.StringUtils.convertArraytoInteger(tempList).trim();
                    if (idApi.endsWith(",")) {
                        idApi = idApi.substring(0, idApi.length() - 1) + "";
                    }
                    if (!StringUtils.isEmpty(functionName)) {
                        Function function = new Function();
                        function.setFunctionName(functionName);
                        function.setStatus(Constant.active);
                        Function checkName = functionRepository.checkFunctionName(functionName);
                        if (ObjectUtils.isEmpty(checkName)) {
                            functionRepository.save(function);
                            FunctionApi functionApi = new FunctionApi();
                            functionApi.setIdApi(idApi);
                            functionApi.setIdFunction(function.getId());
                            functionApiRepository.save(functionApi);
                            responseData = new ResponseData(Constant.SUCCESS, "Thêm mới Function thành công", new ResponseBase(null, functionApi));
                        } else {
                            responseData = new ResponseData(Constant.FAILED, "Function Name đã tồn tại", new ResponseBase(null, true));
                        }
                    } else {
                        responseData = new ResponseData(Constant.FAILED, "Function name không hợp lệ", new ResponseBase(null, true));
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm mới Function", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateFunctionApi", method = RequestMethod.POST)
    public ResponseEntity<?> updateFunctionApi(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    String functionName = jsonNode.get("functionName").asText();
                    int id = jsonNode.get("id").asInt();
                    ArrayList<Integer> listApi = com.hitex.halago.utils.StringUtils.getArrayIntegerJson(jsonNode, "idApi");
                    ArrayList<Integer> tempList = new ArrayList<>();
                    for (Integer obj : listApi) {
                        if (!tempList.contains(obj)) {
                            tempList.add(obj);
                        }
                    }
                    String idApi = com.hitex.halago.utils.StringUtils.convertArraytoInteger(tempList).trim();
                    if (idApi.endsWith(",")) {
                        idApi = idApi.substring(0, idApi.length() - 1) + "";
                    }
                    if (!StringUtils.isEmpty(functionName)) {
                        Function function = functionRepository.findFunctionById(id);
                        function.setFunctionName(functionName);
                        Function checkName = functionRepository.checkFunctionName(functionName);
                        if (!ObjectUtils.isEmpty(checkName) && function.getId() != checkName.getId()) {
                            responseData = new ResponseData(Constant.FAILED, "Function Name đã tồn tại", new ResponseBase(null, true));
                        } else {
                            functionRepository.save(function);
                            FunctionApi functionApi = functionApiRepository.findFunctionApi(id);
                            functionApi.setIdApi(idApi);
                            functionApiRepository.save(functionApi);
                            responseData = new ResponseData(Constant.SUCCESS, "Cập nhật Function thành công", new ResponseBase(null, functionApi));
                        }
                    } else {
                        responseData = new ResponseData(Constant.SUCCESS, "Function Name không hợp lệ", new ResponseBase(null, true));
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền cập nhật Function", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/deleteFunctionApi", method = RequestMethod.POST)
    public ResponseEntity<?> deleteFunctionApi(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Có lỗi khi xóa Function", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    int result = functionApiService.deleteFunctionApi(id);
                    if (result == Constant.SUCCESS) {
                        responseData = new ResponseData(Constant.SUCCESS, "Xóa thành công", new ResponseBase(null, true));
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền Xóa", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/getListRole", method = RequestMethod.POST)
    public ResponseEntity<?> getListRole(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    String name = jsonNode.get("name").asText();
                    int pageNumber = jsonNode.get("pageNumber").asInt();
                    int pageSize = jsonNode.get("pageSize").asInt();
                    List<RoleAccount> roleUser = roleService.getListRole(StringUtils.isBlank(name) ? "" : name, pageSize, pageNumber);
                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, roleUser));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/findRoleFunction", method = RequestMethod.POST)
    public ResponseEntity<?> findRoleFunction(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    RoleDao roleDao = roleService.getListFunctionByRoleId(id);
                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, roleDao));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền xem danh sách", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @RequestMapping(value = "/insertRoleFunction", method = RequestMethod.POST)
    public ResponseEntity<?> insertRoleFunction(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Đã xảy ra lỗi trong quá trình thêm mới Role", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    String roleName = jsonNode.get("roleName").asText();
                    ArrayList<Integer> listFunction = com.hitex.halago.utils.StringUtils.getArrayIntegerJson(jsonNode, "idFunction");
                    ArrayList<Integer> tempList = new ArrayList<Integer>();
                    for (Integer obj : listFunction) {
                        if (!tempList.contains(obj)) {
                            tempList.add(obj);
                        }
                    }
                    String idFunction = com.hitex.halago.utils.StringUtils.convertArraytoInteger(tempList).trim();
                    if (idFunction.endsWith(",")) {
                        idFunction = idFunction.substring(0, idFunction.length() - 1) + "";
                    }
                    if (!StringUtils.isEmpty(roleName)) {
                        Role roleUser = new Role();
                        roleUser.setName(roleName);
                        roleUser.setStatus(Constant.active);
                        Role checkName = roleRepository.checkRoleName(roleUser.getName());
                        if (ObjectUtils.isEmpty(checkName)) {
                            roleRepository.save(roleUser);
                            RoleFunction roleFunction = new RoleFunction();
                            roleFunction.setIdRole(roleUser.getIdRole());
                            roleFunction.setIdFunction(idFunction);
                            roleFunctionRepository.save(roleFunction);
                            responseData = new ResponseData(Constant.SUCCESS, "Thêm mới Role thành công", new ResponseBase(null, roleFunction));
                        } else {
                            responseData = new ResponseData(Constant.FAILED, "Role đã tồn tại", new ResponseBase(null, true));
                        }
                    } else {
                        responseData = new ResponseData(Constant.FAILED, "Role không hợp lệ", new ResponseBase(null, true));
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền thêm mới Role", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateRoleFunction", method = RequestMethod.POST)
    public ResponseEntity<?> updateRoleFunction(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Đã xảy ra lỗi trong quá trình cập nhật Role", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    String roleName = jsonNode.get("roleName").asText();
                    int id = jsonNode.get("id").asInt();
                    ArrayList<Integer> listFunction = com.hitex.halago.utils.StringUtils.getArrayIntegerJson(jsonNode, "idFunction");
                    ArrayList<Integer> tempList = new ArrayList<Integer>();
                    for (Integer obj : listFunction) {
                        if (!tempList.contains(obj)) {
                            tempList.add(obj);
                        }
                    }
                    String idFunction = com.hitex.halago.utils.StringUtils.convertArraytoInteger(tempList).trim();
                    if (idFunction.endsWith(",")) {
                        idFunction = idFunction.substring(0, idFunction.length() - 1) + "";
                    }
                    if (!StringUtils.isEmpty(roleName)) {
                        Role roleUser = roleRepository.findRoleById(id);
                        roleUser.setName(roleName);
                        Role checkName = roleRepository.checkRoleName(roleName);
                        if (!ObjectUtils.isEmpty(checkName) && roleUser.getIdRole() != checkName.getIdRole()) {
                            responseData = new ResponseData(Constant.FAILED, "Role đã tồn tại", new ResponseBase(null, true));
                        } else {
                            roleRepository.save(roleUser);
                            RoleFunction roleFunction = roleFunctionRepository.findRoleById(id);
                            roleFunction.setIdFunction(idFunction);
                            roleFunctionRepository.save(roleFunction);
                            responseData = new ResponseData(Constant.SUCCESS, "Cập nhật Role thành công", new ResponseBase(null, roleFunction));
                        }
                    } else {
                        responseData = new ResponseData(Constant.SUCCESS, "Role không hợp lệ", new ResponseBase(null, true));
                    }
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền cập nhật Role", null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    //
    @RequestMapping(value = "/deleteRoleFunction", method = RequestMethod.POST)
    public ResponseEntity<?> deleteRoleFunction(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Có lỗi khi xóa Role", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            String role = jwtService.getUsernameFromToken(baseRequest.getToken());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                if (!Constant.BRAND.equals(role) || !Constant.INFLUENCER.equals(role)) {
                    int id = jsonNode.get("id").asInt();
                    Role roleDelete = roleRepository.findRoleById(id);
                    roleDelete.setStatus(Constant.inactive);
                    roleRepository.save(roleDelete);
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
}
