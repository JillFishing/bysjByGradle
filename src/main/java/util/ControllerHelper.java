package util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by STGLJY@126.COM on 2017/12/10.
 */
public class ControllerHelper {
    /**
     * 获得代表查询条件的List对象
     * @param req_jsonObj 请求body对应的JSONObject对象
     * @return 代表查询条件的List对象
     */
    public static String getConditions(JSONObject req_jsonObj) {
        JSONArray conditionList_jsonArray = req_jsonObj.getJSONArray("condition");
        return conditionList_jsonArray.toString();
    }
    /**
     * 获得代表查询条件的Pagination对象
     * @param req_jsonObj 请求body对应的JSONObject对象
     * @return 代表查询条件的Pagination对象
     */
    public static Pagination getPagination(JSONObject req_jsonObj) {
        JSONObject pagination_jsonObj =
                req_jsonObj.getJSONObject("pagination");
        System.out.println(pagination_jsonObj);
        //分页对象
        Pagination pagination = null;
        //如果有“pagination”键
        if (pagination_jsonObj != null) {
            //将描述分页的 JSONObject 对象转换为 Java 对象
            pagination = pagination_jsonObj
                    .toJavaObject(Pagination.class);
        }
        System.out.println(pagination.toString());
        return pagination;
    }

    public static String getContextPath(HttpServletRequest request) throws ServletException{
        return request.getContextPath();
    }
}
