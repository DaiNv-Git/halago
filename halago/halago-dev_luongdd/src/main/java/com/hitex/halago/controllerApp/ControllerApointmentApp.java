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
import java.util.ArrayList;
import java.util.List;

@RestController

public class ControllerApointmentApp {


    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);
//    @RequestMapping(value = "/customerCalendar", method = RequestMethod.POST)
//    public ResponseEntity<?> addCalendeCustom(InputStream inputStream) {
//
//        try {
//            responseData = new ResponseData(1, "Lỗi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                String name = "";
//                String date = "";
//                String content = "";
//                String address = "";
//                int idPersonal, idCustomer;
//                if (jsonNode.get("idPersonal") != null && jsonNode.get("idCustomer") != null && jsonNode.get("name") != null && jsonNode.get("address") != null && jsonNode.get("time") != null && jsonNode.get("address") != null && jsonNode.get("content") != null) {
//                    idPersonal = jsonNode.get("idPersonal").asInt();
//                    idCustomer = jsonNode.get("idCustomer").asInt();
//                    name = jsonNode.get("name").asText();
//                    date = jsonNode.get("time").asText();
//                    address = jsonNode.get("address").asText();
//                    content = jsonNode.get("content").asText();
//                    Apointment apointment = apointmentService.addCalendeCustom(idPersonal, idCustomer, name, date, address, content);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, apointment));
//                    NotificationPush notificationPush = new NotificationPush();
//                    List<Device> devices = new ArrayList<>();
//                    devices = deviceRepository.getFcmTokenByIdPersonal(idCustomer);
//                    notificationPush.FunctionPush(devices,"Thông báo lịch hẹn ngày " + date,"Ngày "+date+" bạn có cuộc hẹn "+" tại "+address+":\\r\\n " +content);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/listApointmentIdPersonal", method = RequestMethod.POST)
//    public ResponseEntity<?> listApointmentIdPersonal(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "Lỗi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idPersonal") != null && jsonNode.get("idCustomer") != null) {
//                    int idPersonal = jsonNode.get("idPersonal").asInt();
//                    int idCustomer = jsonNode.get("idCustomer").asInt();
//                    List<ApointmentModel> apointments = apointmentService.ListApointmentbyIdPersonal(idPersonal, idCustomer);
//
//                    responseData = new ResponseData(0, "Thành Công", new ResponseBase(null, apointments));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
}
