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

public class CustomerControllerApp {
//    @Autowired
//    private CustomerRepository customerRepository;
//    @Autowired
//    private CustomerService customerService;

    ResponseData responseData = new ResponseData(1, "Lỗi", null);
//
//    @RequestMapping(value = "/registerCustomer", method = RequestMethod.POST)
//    public ResponseEntity<?> addCustom(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "loi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            System.out.println("------");
//            System.out.println(baseRequest);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                int id;
//                String name = "";
//                String phone = "";
//                String email = "";
//                int idArea;
//                if (jsonNode.get("idPersonal") != null && jsonNode.get("name") != null && jsonNode.get("phone") != null && jsonNode.get("idArea") != null && jsonNode.get("email") != null) {
//
//
//                    id = jsonNode.get("idPersonal").asInt();
//                    name = jsonNode.get("name").asText();
//                    phone = jsonNode.get("phone").asText();
//                    email = jsonNode.get("email").asText();
//                    idArea = jsonNode.get("idArea").asInt();
//                    Customer custom1 = customerService.addCustom(id, name, phone, email, idArea);
//                    System.out.println("abc");
//                    responseData = new ResponseData(0, "thành công", new ResponseBase(null, custom1));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//    @RequestMapping(value = "/searchCustomer", method = RequestMethod.POST)
//    public ResponseEntity<?> searchCustomer(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "loi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                String name = "";
//                int idPersonal=jsonNode.get("idPersonal").asInt();
//                int limit=10;
//                int page =1;
//                List<Integer> idArea=new ArrayList<>();
//
//
//                if (jsonNode.get("idPersonal")!=null){
//                    idPersonal=jsonNode.get("idPersonal").asInt();
//                }
//                if (jsonNode.get("name")!=null) {
//                    name=jsonNode.get("name").asText();
//                }
//                if (jsonNode.get("limit")!=null && jsonNode.get("limit").asInt()>0){
//                    limit =jsonNode.get("limit").asInt();
//                }
//                if (jsonNode.get("page")!=null && jsonNode.get("page").asInt()>0){
//                    page=jsonNode.get("page").asInt();
//                }
//                if (jsonNode.has("idArea") && !jsonNode.get("idArea").asText().equals("null")){
//                    System.out.println(jsonNode.get("idArea").asText());
//                    JsonNode jsonNodeArea = jsonNode.get("idArea");
//
//                    for (JsonNode node : jsonNodeArea) {
//                        idArea.add(node.asInt());
//                    }
//                    if (idArea.isEmpty()){
//                        List<Integer>li=customerRepository.getByIdArea(idPersonal);
//                        if (li.size()<=0){
//                            idArea =customerService.getAllIdArea();
//                        }
//                        else {
//                            for (int a=0;a<li.size();a++){
//                                idArea.add(li.get(a));
//                            }
//                        }
//                        System.out.println("idArea"+idArea);
//                    }
//                }
//                List<Integer> idStatusState=new ArrayList<>();
//                if (jsonNode.has("idStatusState") && !jsonNode.get("idStatusState").asText().equals("null")){
//
//                    JsonNode jsonNodeId = jsonNode.get("idStatusState");
//
//                    for (JsonNode node : jsonNodeId) {
//                        idStatusState.add(node.asInt());
//                    }
//                    if (idStatusState.isEmpty()) {
//                        List<Integer> li = customerRepository.getAllIdStatusState(idPersonal);
//                        if (li.isEmpty()) {
//                            idStatusState.add(1);
//                            idStatusState.add(2);
//                            idStatusState.add(3);
//                        } else {
//                            for (int a = 0; a < li.size(); a++) {
//                                idStatusState.add(li.get(a));
//                            }
//                        }
//                    }
//                }
//                List<CustomerModel> list=customerService.searchCustom(idPersonal,name,limit,page,idStatusState,idArea);
//                responseData=new ResponseData(0,"tim kiem thanh cong customer",new ResponseBase(null,list));
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/customerInfo", method = RequestMethod.POST)
//    public ResponseEntity<?> infoCustomer(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "loi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idCustomer") != null && jsonNode.get("idPersonal") != null) {
//                    int idCustomer = jsonNode.get("idCustomer").asInt();
//                    int idPersonal = jsonNode.get("idPersonal").asInt();
//                    List<Customer> customer = customerService.inforCustomer(idPersonal, idCustomer);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, customer));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
}
