package com.studyinghome.bootshop.web.frontend;

import com.studyinghome.bootshop.dto.UserShopMapExecution;
import com.studyinghome.bootshop.entity.UserShopMap;
import com.studyinghome.bootshop.service.UserShopMapService;
import com.studyinghome.bootshop.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/frontend")
public class MyShopPointController {
    @Autowired
    private UserShopMapService userShopMapService;

    @GetMapping(value = "/listusershopmapsbycustomer")
    private Map<String, Object> listUserShopMapsByCustomer(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Long userId = 1L;
        if ((pageIndex > -1) && (pageSize > -1) && (userId != null)) {
            UserShopMap userShopMapCondition = new UserShopMap();
            userShopMapCondition.setUserId(userId);
            long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            if (shopId > -1) {
                userShopMapCondition.setShopId(shopId);
            }
            UserShopMapExecution ue = userShopMapService.listUserShopMap(userShopMapCondition, pageIndex, pageSize);
            modelMap.put("userShopMapList", ue.getUserShopMapList());
            modelMap.put("count", ue.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }
}
