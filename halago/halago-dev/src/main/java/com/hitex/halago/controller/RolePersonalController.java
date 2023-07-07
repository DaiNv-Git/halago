package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.model.RolePersonal;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.RolePersonalRepository;
import com.hitex.halago.service.RolePersonalService;
import com.hitex.halago.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.List;

@RestController
public class RolePersonalController {
    @Autowired
    RolePersonalRepository rolePersonalRepository;
    @Autowired
    RolePersonalService rolePersonalService;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);


    @RequestMapping(value = "/findAllRolePersonal", method = RequestMethod.POST)
    public ResponseEntity<?> findAllRolePersonal(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                System.out.println(jsonNode);
                List<RolePersonal> rolePersonals = rolePersonalRepository.findAll();
                responseData = new ResponseData(0, "Tìm Kiếm thành công", new ResponseBase(null, rolePersonals));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

//    @RequestMapping(value = "/updateRolePersonal", method = RequestMethod.POST)
//    public ResponseEntity<?> updateRolePersonal(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                if (jsonNode.get("idRolePersonal") != null && jsonNode.get("idRole") != null && jsonNode.get("idPersonal") != null) {
//                    RolePersonal rolePersonal = new RolePersonal();
//                    rolePersonal.setIdRolePersonal(jsonNode.get("idRolePersonal").asInt());
//                    rolePersonal.setIdRole(jsonNode.get("idRole").asInt());
//                    rolePersonal.setIdPersonal(jsonNode.get("idPersonal").asInt());
//                    RolePersonal rolePersonal1 = rolePersonalService.updateRole(rolePersonal);
//                    responseData = new ResponseData(0, "Sửa thành công", new ResponseBase(null, rolePersonal));
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }


    @RequestMapping(value = "/findRolePersonalById", method = RequestMethod.POST)
    public ResponseEntity<?> findRolePersonalById(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                System.out.println(jsonNode);
                if (jsonNode.get("idRolePersonal") != null) {
                    int id = jsonNode.get("idRolePersonal").asInt();
                    RolePersonal rolePersonals = rolePersonalRepository.findRolePersonal(id);
                    responseData = new ResponseData(0, "Tìm Kiếm thành công", new ResponseBase(null, rolePersonals));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
