package com.hitex.halago.controllerApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
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

public class TeamControllerApp {

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);
//    @RequestMapping(value = "/getListTeamGroup", method = RequestMethod.POST)
//    public ResponseEntity<?> getList(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "loi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idPersonal") != null) {
//                    int id = jsonNode.get("idPersonal").asInt();
//                    List<Team> list = teamService.findByid(id);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//    @RequestMapping(value = "/ListMemberGroup", method = RequestMethod.POST)
//    public ResponseEntity<?> findMemberGroup(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", new ResponseBase(null, null));
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idTeam") != null) {
//                    int idPersonal = jsonNode.get("idTeam").asInt();
//
//                    List<TeamMemberModel> list1 = teamMemberService.findTeampersonal(idPersonal);
//                    responseData = new ResponseData(0, "Thành công", new ResponseBase(null, list1));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/InfoPersonalGroup", method = RequestMethod.POST)
//    public ResponseEntity<?> findInfo(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "loi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idPersonal") != null) {
//                    int idPersonal = jsonNode.get("idPersonal").asInt();
//                    TeamMemberModel personal = teamMemberService.findPersonalTeam(idPersonal);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, personal));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
}
