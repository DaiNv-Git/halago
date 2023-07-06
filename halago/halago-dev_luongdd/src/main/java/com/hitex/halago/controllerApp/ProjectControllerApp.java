package com.hitex.halago.controllerApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.PersonalRepository;

import com.hitex.halago.service.*;
import com.hitex.halago.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.stream.Collectors;

@RestController

public class ProjectControllerApp {

    @Autowired
    private PersonalService personalService;
    @Autowired
    private PersonalRepository personalRepository;


    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

//    @RequestMapping(value = "/getListProductType", method = RequestMethod.POST)
//    public ResponseEntity<?> getListProductType(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                List<ProductType> list = productTypeService.findAll();
//                responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

//    @RequestMapping(value = "/getListAreaForSearch", method = RequestMethod.POST)
//    public ResponseEntity<?> getListArea(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                List<Area> ares = projectService.findProvice();
//                responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, ares));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

//    @RequestMapping(value = "/countApartment", method = RequestMethod.POST)
//    public ResponseEntity<?> countApartment(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idProject") != null) {
//                    int idProject = jsonNode.get("idProject").asInt();
//                    int idSector = jsonNode.get("idSector").asInt();
////                    int idBuilding=jsonNode.get("idBuilding").asInt();
//                    List<BuildingDaoModel> list = buildingService.findAllApartment(idProject, idSector);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

//    @RequestMapping(value = "/getListSegmentPrice", method = RequestMethod.POST)
//    public ResponseEntity<?> getListSegment(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                List<SegmentPrice> list = segmentService.findAll();
//                responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/getSalesPolicy", method = RequestMethod.POST)
//    ResponseEntity<?> getSalesPolicy(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
////            responseData = new ResponseData(1, "khong tim thay ",new ResponseBase(null,null));
//
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idProject") != null) {
//                    int id = jsonNode.get("idProject").asInt();
//                    SalePolicy list = salePolicyService.findByIdProject(id);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                }
//            } else {
//                responseData = new ResponseData(0, "khong tim thay", new ResponseBase(null, null));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

//    @RequestMapping(value = "/getDetailProject", method = RequestMethod.POST)
//    ResponseEntity<?> getDetailProject(InputStream inputStream) {
//        try {
//
//
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idProject") != null) {
//                    int id = jsonNode.get("idProject").asInt();
//                    ProjectModel list = projectService.getDetailProject(id);
//
//                    int idPersonal = jsonNode.get("idPersonal").asInt();
//                    String a = personalRepository.findProject(idPersonal);
//                    if (a == null || a.isEmpty()) {
//                        String b = Integer.toString(id);
//                        personalService.updateIdProject(b, idPersonal);
//                    } else {
//                        List<Integer> l = Arrays.stream(a.split(",")).map(Integer::parseInt)
//                                .collect(Collectors.toList());
//                        System.out.println("lll" + l);
//                        List<Integer> li = new ArrayList<>();
//                        li.add(id);
//                        l.removeAll(li);
//                        l.add(id);
//                        String b = Joiner.on(",").join(l);
//                        personalService.updateIdProject(b, idPersonal);
//                    }
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

//    @RequestMapping(value = "/getListDocumentSales", method = RequestMethod.POST)
//    public ResponseEntity<?> getListDocument(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idProject") != null) {
//                    int id = jsonNode.get("idProject").asInt();
//                    List<SaleDocument> list = saleDocumentService.findByIdProject(id);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

//    @RequestMapping(value = "/getListSectorOfProject", method = RequestMethod.POST)
//    public ResponseEntity<?> getListSector(InputStream inputStream) {
//        try {
//            responseData = new ResponseData(1, "loi", null);
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                if (jsonNode.get("idProject") != null) {
//                    int id = jsonNode.get("idProject").asInt();
//                    List<SectorModel> list = sectorService.findSectorByiDProjectOfBuilding(id);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

//    @RequestMapping(value = "/getListVilla", method = RequestMethod.POST)
//    public ResponseEntity<?> getListVilla(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                System.out.println(baseRequest);
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//
//                if (jsonNode.get("idProject") != null && jsonNode.get("idSector") != null && jsonNode.get("limit") != null && jsonNode.get("page") != null && jsonNode.get("status") != null) {
//                    int idProject = jsonNode.get("idProject").asInt();
//                    int idSector = jsonNode.get("idSector").asInt();
//                    int limit = jsonNode.get("limit").asInt();
//                    int page = jsonNode.get("page").asInt();
//                    int status = jsonNode.get("status").asInt();
//                    System.out.println("--------------");
//                    if (status == -1) {
//                        List<Villa> list = villaService.findVillaByIdSector(idProject, idSector, limit, page);
//                        responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                    } else {
//                        List<Villa> list = villaService.findVillaBySatatus(idProject, idSector, limit, page, status);
//                        responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                    }
//
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

//    @RequestMapping(value = "/getVillaById", method = RequestMethod.POST)
//    public ResponseEntity<?> getVilla(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idVilla") != null) {
//                    int id = jsonNode.get("idVilla").asInt();
//                    Villa list = villaService.findVillaById(id);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, list));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }

}
