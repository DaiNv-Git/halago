package com.hitex.halago.controller;

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
public class MessageController {
//    @Autowired
//    private NotificationPush notificationPush;
//    @Autowired
//    private MessageService messageService;
//    ResponseData responseData = new ResponseData(1, "loi", null);
//
//    @RequestMapping(value = "/createMessage", method = RequestMethod.POST)
//    public ResponseEntity<?> createMessage(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);    NotificationPush notificationPush = new NotificationPush();
////                    notificationPush.pushNotificationCreateMessage(idCreate, idTeam, content);
//                if (jsonNode.get("idCreate") != null && jsonNode.get("idTeam") != null && jsonNode.get("content") != null) {
//                    int idCreate = jsonNode.get("idCreate").asInt();
//                    int idTeam = jsonNode.get("idTeam").asInt();
//                    String content = jsonNode.get("content").asText();
//                    Message create = messageService.createMessage(idCreate, content, idTeam);
////
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, create));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/getListMessger", method = RequestMethod.POST)
//    public ResponseEntity<?> getListMessage(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                if (jsonNode.get("idTeam") != null && jsonNode.get("idPersonal") != null && jsonNode.get("limit") != null && jsonNode.get("page") != null) {
//                    int idTeam = jsonNode.get("idTeam").asInt();
//                    int idPersona = jsonNode.get("idPersonal").asInt();
//                    int limit = jsonNode.get("limit").asInt();
//                    int page = jsonNode.get("page").asInt();
//                    List<MessageModel> list = messageService.findAllMessage(idTeam, page, limit, idPersona);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
}
