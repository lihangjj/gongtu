package lz.cm.dao;

import lz.util.SpringUtil;
import lz.util.str.StrUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public interface IDAO<K, V> {
    boolean doCreate(V vo) throws Exception;

    boolean doUpdate(V vo) throws Exception;

    boolean doDelete(V vo) throws Exception;


    V findById(K id);

    default Integer getLastInsertId() {
        Connection connection = SpringUtil.getConnection();
        try {
            PreparedStatement pre = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet resultSet = pre.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {

            }
        }

        return null;
    }
    //更新部分列 格式为：name,memberid
    default boolean updateXxClomuns(String columnsStr, Object v) {
        Class cls = v.getClass();
        String tbName = cls.getSimpleName().substring(0, 1).toLowerCase() + cls.getSimpleName().substring(1);
        StringBuffer buffer = new StringBuffer("UPDATE " + tbName + " SET ");
        Connection connection = SpringUtil.getConnection();
        PreparedStatement pre = null;
        String[] columns = columnsStr.split(",");
        try {

            for (String ss : columns) {
                String filedName = ss;
                Method getMethod = cls.getMethod("get" + StrUtil.initCap(filedName));
                Object o = getMethod.invoke(v);
                if (o instanceof Date) {//如果是日期
                    Date d = (Date) o;
                    String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
                    buffer.append(filedName + "='" + s + "',");
                } else {
                    if (o == null) {
                        buffer.append(filedName + "=null,");

                    } else {
                        buffer.append(filedName + "='" + o.toString() + "',");
                    }
                }
            }
            buffer.delete(buffer.length() - 1, buffer.length());
            Method getM = cls.getMethod("get" + StrUtil.initCap(tbName + "id"));
            String tbid = getM.invoke(v).toString();
            buffer.append(" WHERE " + tbName + "id='" + tbid + "'");
            pre = connection.prepareStatement(buffer.toString());
            return pre.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return false;
    }
    default boolean updateAll(V v) {
        Class cls = v.getClass();
        String tbName = cls.getSimpleName().toLowerCase();
        StringBuffer buffer = new StringBuffer("UPDATE " + tbName + " SET ");
        Connection connection = SpringUtil.getConnection();
        PreparedStatement pre = null;
        String ss = "set memberid='lh',jid=1";
        try {
            pre = connection.prepareStatement("DESC " + tbName);
            ResultSet resultSet = pre.executeQuery();
            while (resultSet.next()) {
                String filedName = resultSet.getString(1);
                Method getMethod = cls.getMethod("get" + StrUtil.initCap(filedName));
                Object o = getMethod.invoke(v);
                if (o instanceof Date) {//如果是日期
                    Date d = (Date) o;
                    String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
                    buffer.append(filedName + "='" + s + "',");
                } else {
                    if (o == null) {
                        buffer.append(filedName + "=null,");

                    } else {
                        buffer.append(filedName + "='" + o.toString() + "',");
                    }
                }
            }
            buffer.delete(buffer.length() - 1, buffer.length());
            Method getM = cls.getMethod("get" + StrUtil.initCap(tbName + "id"));
            String tbid = getM.invoke(v).toString();
            buffer.append(" WHERE " + tbName + "id='" + tbid + "'");
            pre = connection.prepareStatement(buffer.toString());
            return pre.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return false;
    }
    default boolean deleteObj(Object v) {
        Class cls = v.getClass();
        String tbName = cls.getSimpleName().substring(0, 1).toLowerCase() + cls.getSimpleName().substring(1);
        StringBuffer buffer = new StringBuffer("DELETE FROM " + tbName + " WHERE " + tbName + "id='");
        Connection connection = SpringUtil.getConnection();
        PreparedStatement pre = null;
        try {
            Method getM = cls.getMethod("get" + StrUtil.initCap(tbName + "id"));
            String tbid = getM.invoke(v).toString();
            buffer.append(tbid + "'");
            pre = connection.prepareStatement(buffer.toString());
            return pre.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return false;
    }

    //反射获取某个Vo,
    default Object findVoByVo(Object o) {
        Connection connection = SpringUtil.getConnection();
        try {
            Class cls = o.getClass();
            Object vo;
            String tbName = cls.getSimpleName().toLowerCase();
            Field field = cls.getDeclaredField(tbName + "id");
            Method getMthod = cls.getMethod("get" + StrUtil.initCap(tbName + "id"));
            Object val = getMthod.invoke(o);
            String id = val.toString();
            StringBuffer buffer = new StringBuffer("SELECT*FROM " + tbName + " WHERE " + tbName + "id=" + "'" + id + "'");
            PreparedStatement pre = connection.prepareStatement(buffer.toString());
            ResultSet res = pre.executeQuery();
            ResultSetMetaData rsmd = res.getMetaData();
            if (res.next()) {
                vo = cls.getConstructor().newInstance();
                for (int x = 1; x <= rsmd.getColumnCount(); x++) {
                    String columnName = rsmd.getColumnName(x);
                    Method setM = cls.getMethod("set" + StrUtil.initCap(columnName), cls.getDeclaredField(columnName).getType());
                    setM.invoke(vo, res.getObject(x));
                }
                return vo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    default boolean insertAll(V vo) {
        Class cls = vo.getClass();
        String tbName = cls.getSimpleName().toLowerCase();
        StringBuffer buffer = new StringBuffer("INSERT INTO " + tbName + " VALUES(");
        Connection connection = SpringUtil.getConnection();
        try {
            PreparedStatement pre = connection.prepareStatement("DESC " + tbName);
            ResultSet resultSet = pre.executeQuery();
            while (resultSet.next()) {
                String filedName = resultSet.getString(1);
                Method getMethod = cls.getMethod("get" + StrUtil.initCap(filedName));
                Object o = getMethod.invoke(vo);
                if (o instanceof Date) {//如果是日期
                    Date d = (Date) o;
                    String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
                    buffer.append("'" + s + "',");
                } else {
                    if (o == null) {
                        buffer.append("null,");

                    } else {
                        buffer.append("'" + o.toString() + "',");

                    }
                }

            }
            buffer.delete(buffer.length() - 1, buffer.length());
            buffer.append(")");
            pre = connection.prepareStatement(buffer.toString());
            return pre.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return false;
    }

    default V findById(K id, Class cls) {
        V vo;
        String tbName = cls.getSimpleName().toLowerCase();
        StringBuffer buffer = new StringBuffer("SELECT*FROM " + tbName + " WHERE " + tbName + "id=" + "'" + id + "'");
        Connection connection = SpringUtil.getConnection();

        try {
            PreparedStatement pre = connection.prepareStatement(buffer.toString());
            ResultSet res = pre.executeQuery();
            ResultSetMetaData rsmd = res.getMetaData();
            if (res.next()) {
                vo = (V) cls.getConstructor().newInstance();
                for (int x = 1; x <= rsmd.getColumnCount(); x++) {
                    String columnName = rsmd.getColumnName(x);
                    Method setM = cls.getMethod("set" + StrUtil.initCap(columnName), cls.getDeclaredField(columnName).getType());
                    setM.invoke(vo, res.getObject(x));
                }
                return vo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //链接关闭
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 根据某个标记分页
     *
     * @param T
     * @param column
     * @param keyWord
     * @param currentPage
     * @param lineSize
     * @param condition
     * @return
     * @throws Exception
     */
    default Map<String, Object> splitVoByFlag(Class T, String column, String keyWord, Integer currentPage, Integer lineSize, String condition) {
        //1.用来保存查询结果的map,包含所有实体类和allRecorders

        //表名,需要将首字母小写，不然linux不认识会报错
        String tbName = T.getSimpleName().toLowerCase();
        //拼写sql
        StringBuffer buffer = new StringBuffer("SELECT*FROM " + tbName);
        //没有关键字
        boolean noK = keyWord == null || "".equals(keyWord);
        //没有参数值
        boolean noCondition = condition == null || "".equals(condition);
        if (!noK) {//此时有keyWord
            buffer.append(" WHERE ").append(column).append(" LIKE ").append("'%" + keyWord + "%'");//这里需要加"'"
        }

        if (!noCondition) {//此时有额外条件字符串，在服务层拼接好，开头不需要AND！
            if (noK) {//此时没有keyWord
                buffer.append(" WHERE ");
            } else {//有关键字，就有where,就需要AND
                buffer.append(" AND ");
            }
            buffer.append(condition);
        }
        if (!(currentPage == null || "".equals(currentPage) || lineSize == null || "".equals(lineSize))) {//表示有分页限制，不然就全部查询
            buffer.append(" LIMIT " + (currentPage - 1) * lineSize + "," + lineSize);
        }
        String sql = buffer.toString();

//        Map<String, Object> cacheMap = (Map<String, Object>) CacheUtil.getCache(sql);
//        if (cacheMap != null) {//先看是否有缓存，有就直接返回该集合，暂时保存15秒，不开启缓存
//            return cacheMap;
//        }
        //开始数据库操作，最后必须关闭connection
        Connection connection = SpringUtil.getConnection();
        try {
            //这里的connection不能多实例，会卡死
            //先查询所有vo
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet resultSet = pre.executeQuery();
            //获得表的结构，得到表字段的java类型，表字段的值
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //得到一个保存VO的list
            List<V> allVo = new ArrayList<>();
            while (resultSet.next()) {
                //实例化vo
                V vo = (V) T.getConstructor().newInstance();
                for (int x = 1; x <= rsmd.getColumnCount(); x++) {
                    //反射获得成员类型
                    Class<?> type = T.getDeclaredField(rsmd.getColumnName(x)).getType();
                    //获得设置vo成员的方法，set+表字段开头大写，参数为Class.forName(type)
                    Method setM = T.getMethod("set" + StrUtil.initCap(rsmd.getColumnName(x)), type);
                    //获得表字段对应的值
                    //反射调用方法给vo成员赋值
                    setM.invoke(vo, resultSet.getObject(x));
                }
                //将一个赋值完毕后的vo添加到list集合
                allVo.add(vo);
            }
            Map<String, Object> map = new HashMap<>();
            //所有vo赋值完毕，添加到map集合
            map.put("allVo", allVo);
            //开始查询分页数量，改变sql语句，减掉limit,把*换成count(*)就行
            if (!(currentPage == null || "".equals(currentPage) || lineSize == null || "".equals(lineSize))) {//表示有分页限制，不然就全部查询
                buffer.delete(buffer.lastIndexOf("LIMIT"), buffer.length());
            }
            buffer.replace(buffer.indexOf("*"), buffer.indexOf("*") + 1, " COUNT(*) ");
            pre = connection.prepareStatement(buffer.toString());
            resultSet = pre.executeQuery();
            while (resultSet.next()) {
                map.put("allRecorders", resultSet.getInt(1));
            }
            //        CacheUtil.setCache(sql,map);需要，可以缓存
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }




    /**
     *
     * @param columns 查询某几列而得到某个实体类
     * @param condition
     * @param cls
     * @return
     */
    default V   findXxByConditions(String columns, String condition,Class cls) {
        V vo;
        String tbName = cls.getSimpleName().toLowerCase();
        StringBuffer buffer = new StringBuffer("SELECT "+columns+" FROM " + tbName + " WHERE " +  condition);
        Connection connection = SpringUtil.getConnection();
        try {
            PreparedStatement pre = connection.prepareStatement(buffer.toString());
            ResultSet res = pre.executeQuery();
            ResultSetMetaData rsmd = res.getMetaData();
            if (res.next()) {
                vo = (V) cls.getConstructor().newInstance();
                for (int x = 1; x <= rsmd.getColumnCount(); x++) {
                    String columnName = rsmd.getColumnName(x);
                    Method setM = cls.getMethod("set" + StrUtil.initCap(columnName), cls.getDeclaredField(columnName).getType());
                    setM.invoke(vo, res.getObject(x));
                }
                return vo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //链接关闭
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     *
     * @param columns 要查询的列“name,age,sex等,最后不要有尾号”
     * @param T
     * @param column 模糊查询列
     * @param keyWord 关键字
     * @param currentPage
     * @param lineSize
     * @param condition
     * @return
     */
    default Map<String, Object> splitVoByColumns(String columns, Class T, String column, String keyWord, Integer currentPage, Integer lineSize, String condition) {
        //1.用来保存查询结果的map,包含所有实体类和allRecorders

        //表名,需要将首字母小写，不然linux不认识会报错
        String tbName = T.getSimpleName().toLowerCase();
        //拼写sql
        StringBuffer buffer = new StringBuffer("SELECT " + columns + " FROM " + tbName);
        //没有关键字
        boolean noK = keyWord == null || "".equals(keyWord);
        //没有参数值
        boolean noCondition = condition == null || "".equals(condition);
        if (!noK) {//此时有keyWord
            buffer.append(" WHERE ").append(column).append(" like ").append("'%" + keyWord + "%'");//这里需要加"'"
        }

        if (!noCondition) {//此时有额外条件字符串，在服务层拼接好，开头不需要AND！
            if (noK) {//此时没有keyWord
                buffer.append(" WHERE ");
            } else {//有关键字，就有where,就需要AND
                buffer.append(" AND ");
            }
            buffer.append(condition);
        }
        if (!(currentPage == null || "".equals(currentPage) || lineSize == null || "".equals(lineSize))) {//表示有分页限制，不然就全部查询
            buffer.append(" LIMIT " + (currentPage - 1) * lineSize + "," + lineSize);
        }
        String sql = buffer.toString();

//        Map<String, Object> cacheMap = (Map<String, Object>) CacheUtil.getCache(sql);
//        if (cacheMap != null) {//先看是否有缓存，有就直接返回该集合，暂时保存15秒，不开启缓存
//            return cacheMap;
//        }
        //开始数据库操作，最后必须关闭connection
        Connection connection = SpringUtil.getConnection();
        try {
            //这里的connection不能多实例，会卡死
            //先查询所有vo
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet resultSet = pre.executeQuery();
            //获得表的结构，得到表字段的java类型，表字段的值
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //得到一个保存VO的list
            List<V> allVo = new ArrayList<>();
            while (resultSet.next()) {
                //实例化vo
                V vo = (V) T.getConstructor().newInstance();
                for (int x = 1; x <= rsmd.getColumnCount(); x++) {
                    //反射获得成员类型
                    Class<?> type = T.getDeclaredField(rsmd.getColumnName(x)).getType();
                    //获得设置vo成员的方法，set+表字段开头大写，参数为Class.forName(type)
                    Method setM = T.getMethod("set" + StrUtil.initCap(rsmd.getColumnName(x)), type);
                    //获得表字段对应的值
                    //反射调用方法给vo成员赋值
                    setM.invoke(vo, resultSet.getObject(x));
                }
                //将一个赋值完毕后的vo添加到list集合
                allVo.add(vo);
            }
            Map<String, Object> map = new HashMap<>();
            //所有vo赋值完毕，添加到map集合
            map.put("allVo", allVo);
            //开始查询分页数量，改变sql语句，减掉limit,把*换成count(*)就行
            if (!(currentPage == null || "".equals(currentPage) || lineSize == null || "".equals(lineSize))) {//表示有分页限制，不然就全部查询
                buffer.delete(buffer.lastIndexOf("LIMIT"), buffer.length());
            }
            buffer.replace(buffer.indexOf(columns), buffer.indexOf(columns) + columns.length(), " COUNT(*) ");
            pre = connection.prepareStatement(buffer.toString());
            resultSet = pre.executeQuery();
            while (resultSet.next()) {
                map.put("allRecorders", resultSet.getInt(1));
            }
            //        CacheUtil.setCache(sql,map);需要，可以缓存
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

}
