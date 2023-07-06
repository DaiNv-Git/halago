package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.model.RoleProject;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.RoleProjectRepository;
import com.hitex.halago.service.RoleProjectService;
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
public class RoleProjectController {
    @Autowired
    private RoleProjectRepository roleProjectRepository;
    @Autowired
    private RoleProjectService roleProjectService;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

    @RequestMapping(value = "/findAllRoleProject", method = RequestMethod.POST)
    public ResponseEntity<?> findAllRoleProject(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                System.out.println(jsonNode);
                List<RoleProject> role = roleProjectService.findAll();
                responseData = new ResponseData(0, "Tìm Kiếm thành công", new ResponseBase(null, role));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/findRoleProject", method = RequestMethod.POST)
    public ResponseEntity<?> findRoleProject(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                System.out.println(jsonNode);
                if (jsonNode.get("idRoleProject") != null) {
                    int id = jsonNode.get("idRoleProject").asInt();
                    RoleProject role = roleProjectService.findRole(id);
                    responseData = new ResponseData(0, "Tìm Kiếm thành công", new ResponseBase(null, role));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/insertRoleProject", method = RequestMethod.POST)
    public ResponseEntity<?> insertRoleProject(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                System.out.println(jsonNode);
                if (jsonNode.get("name") != null) {
                    RoleProject roleProject = new RoleProject();
                    roleProject.setName(jsonNode.get("name").asText());
                    RoleProject role = roleProjectService.insertRoleProject(roleProject);
                    responseData = new ResponseData(0, "Tìm Kiếm thành công", new ResponseBase(null, role));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/updateRoleProject", method = RequestMethod.POST)
    public ResponseEntity<?> updateRoleProject(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                System.out.println(jsonNode);
                if (jsonNode.get("idRoleProject") != null && jsonNode.get("name") != null) {
                    RoleProject roleProject = new RoleProject();
                    roleProject.setIdRoleProject(jsonNode.get("idRoleProject").asInt());
                    roleProject.setName(jsonNode.get("name").asText());
                    RoleProject role = roleProjectService.updateRoleProject(roleProject);
                    responseData = new ResponseData(0, "Tìm Kiếm thành công", new ResponseBase(null, role));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

}
