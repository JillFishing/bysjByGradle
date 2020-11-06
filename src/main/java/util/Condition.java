package util;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

public class Condition {
    public static String toWhereClause(String condition_json){
        //将json解析为condition集合类对象
        System.out.println(condition_json);
        List<Condition> conditionList = JSONArray.parseArray(condition_json,Condition.class);
        StringBuilder builder = new StringBuilder("WHERE ");
        for (Condition c:conditionList){
            builder.append(c.getKey()).append(" ").append(c.getOperator()).append(" ");
            String value = c.getValue();
            //like做操作符时自动模糊匹配
            if("like".equalsIgnoreCase(c.getOperator())){
                value = "%" + value + "%";
            }
            //去除最后的“ and ”
            value = TxtHelper.quoteMarked(value);
            builder.append(value);
            builder.append(" AND ");
        }

        TxtHelper.truncEnding(builder,5);
        return builder.toString();
    }
    //避免json创建对象
    public Condition(String key, String operator, String value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    private String key;
    private String operator;
    private String value;
}
