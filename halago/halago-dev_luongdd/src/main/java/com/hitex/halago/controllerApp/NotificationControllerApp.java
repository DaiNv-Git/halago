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
public class NotificationControllerApp {
//    @Autowired
//    private NotificationRepository notificationRepository;
//    @Autowired
//    private NotificationService notificationService;
    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);
//    @RequestMapping(value = "/getListNotification", method = RequestMethod.POST)
//    public ResponseEntity<?> findNotification(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Lỗi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idPersonal") != null) {
//                    int id = jsonNode.get("idPersonal").asInt();
//                    List<Notification> notification = notificationService.findNotificationA(id);
//                    responseData = new ResponseData(0, "thành công", new ResponseBase(null, notification));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/updateStatusNotifi", method = RequestMethod.POST)
//    public ResponseEntity<?> updateNotifi(InputStream inputStream) {
//        try {
//
//
//            responseData = new ResponseData(1, "khong the update", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idNotification") != null) {
//                    int id = jsonNode.get("idNotification").asInt();
//                    notificationService.updateStatus(id);
//                    responseData = new ResponseData(0, "Cập nhật thành công", null);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/countNotifi", method = RequestMethod.POST)
//    public ResponseEntity<?> findCountNotifi(InputStream inputStream) {
//        try {
//
//
//            responseData = new ResponseData(1, "khong co idPersonal", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idPersonal") != null) {
//                    int id = jsonNode.get("idPersonal").asInt();
//                    List<NotificationModel> notification = notificationService.findCountNotifi(id);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, notification));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//    @RequestMapping(value = "/findNotificationByIds", method = RequestMethod.POST)
//    public ResponseEntity<?> findNotificationById(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                if (jsonNode.get("idNotification") != null) {
//                    int id = jsonNode.get("idNotification").asInt();
//                    Notification notification = notificationService.findNotification(id);
//                    responseData = new ResponseData(0, "Tìm kiếm thành Công", new ResponseBase(null, notification));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

}
