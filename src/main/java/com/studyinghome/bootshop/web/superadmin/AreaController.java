package com.studyinghome.bootshop.web.superadmin;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyinghome.bootshop.dto.AreaExecution;
import com.studyinghome.bootshop.entity.Area;
import com.studyinghome.bootshop.entity.ConstantForSuperAdmin;
import com.studyinghome.bootshop.enums.AreaStateEnum;
import com.studyinghome.bootshop.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/superadmin")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @PostMapping("listareas")
    private Map<String, Object> listAreas() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            List<Area> list = areaService.getAreaList();
            modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, list);
            modelMap.put(ConstantForSuperAdmin.TOTAL, list.size());

        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    @PostMapping("/addarea")
    private Map<String, Object> addArea(String areaStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        Area area = null;
        try {
            area = mapper.readValue(areaStr, Area.class);
            // decode可能有中文的地方
            area.setAreaName((area.getAreaName() == null) ? null : URLDecoder
                    .decode(area.getAreaName(), "UTF-8"));
            area.setAreaDesc((area.getAreaDesc() == null) ? null : (URLDecoder
                    .decode(area.getAreaDesc(), "UTF-8")));
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (area != null && area.getAreaName() != null) {
            try {
                AreaExecution ae = areaService.addArea(area);
                if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入区域信息");
        }
        return modelMap;
    }

    @PostMapping("/modifyarea")
    private Map<String, Object> modifyArea(String areaStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        Area area = null;
        try {
            area = mapper.readValue(areaStr, Area.class);
            area.setAreaName((area.getAreaName() == null) ? null : URLDecoder
                    .decode(area.getAreaName(), "UTF-8"));
            area.setAreaDesc((area.getAreaDesc() == null) ? null : URLDecoder
                    .decode(area.getAreaDesc(), "UTF-8"));
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (area != null && area.getAreaId() != null) {
            try {
                AreaExecution ae = areaService.modifyArea(area);
                if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入区域信息");
        }
        return modelMap;
    }

    @PostMapping("/removearea")
    private Map<String, Object> removeArea(Long areaId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (areaId != null && areaId > 0) {
            try {
                AreaExecution ae = areaService.removeArea(areaId);
                if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入区域信息");
        }
        return modelMap;
    }

    @PostMapping("/removeareas")
    private Map<String, Object> removeAreas(String areaIdListStr) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Long.class);
        List<Long> areaIdList = null;
        try {
            areaIdList = mapper.readValue(areaIdListStr, javaType);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        if (areaIdList != null && areaIdList.size() > 0) {
            try {
                AreaExecution ae = areaService.removeAreaList(areaIdList);
                if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入区域信息");
        }
        return modelMap;
    }
}
