package com.apocalypse.common.mybatis.type;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jingkaihui
 * @since 2018/12/21
 */
public class JSONArrayTypeHandler extends BaseTypeHandler<JSONArray> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONArray parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toJSONString());
    }

    @Override
    public JSONArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String jsonStr = rs.getString(columnName);
        if (StrUtil.isNotEmpty(jsonStr)) {
            return (JSONArray) JSONObject.parse(jsonStr);
        }
        return null;
    }

    @Override
    public JSONArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String jsonStr = rs.getString(columnIndex);
        if (StrUtil.isNotEmpty(jsonStr)) {
            return (JSONArray) JSONObject.parse(jsonStr);
        }
        return null;
    }

    @Override
    public JSONArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String jsonStr = cs.getString(columnIndex);
        if (StrUtil.isNotEmpty(jsonStr)) {
            return (JSONArray) JSONObject.parse(jsonStr);
        }
        return null;
    }
}
