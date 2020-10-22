package tech.spiro.addrparser.tool;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import tech.spiro.addrparser.common.RegionInfo;
import tech.spiro.addrparser.io.RegionDataInput;
import tech.spiro.addrparser.io.rdbms.RdbmsRegionDataInput;
import tech.spiro.addrparser.parser.Location;
import tech.spiro.addrparser.parser.LocationParserEngine;
import tech.spiro.addrparser.parser.ParserEngineException;

public class GetLocationUtil {

    public static void init() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://10.39.52.224:3306/region?useSSL=false&characterEncoding=utf8");
        ds.setPort(3306);
        ds.setPassword("admin");
        ds.setUser("sqladmin");

        // 关系型数据库区域数据输入
        RegionDataInput regionDataInput = new RdbmsRegionDataInput(ds, "region_data");

        // 如果上述命令如果指定了定制的表名，则该处也要指定表名。
        // RegionDataInput regionDataInput = new RdbmsRegionDataInput(ds, customTableName);

        // 创建并初始化位置解析引擎，一般配置为全局单例
        LocationParserEngine engine = new LocationParserEngine(regionDataInput);
        // 初始化，加载数据，比较耗时
        try {
            engine.init();
        } catch (ParserEngineException e) {
            e.printStackTrace();
        }

        // 执行解析操作109.773487,18.80935  109.855053,19.984199

        Location location = engine.parse(109.855053, 19.984199);

        // 获取省市区信息
        RegionInfo provInfo = location.getProv();
        RegionInfo cityInfo = location.getCity();
        RegionInfo districtInfo = location.getDistrict();
        RegionInfo street = location.getStreet();
    }

/*    public static void init() {
        // china-region.json文件作为基础数据
        RegionDataInput regionDataInput = new JSONFileRegionDataInput("D:\\test\\china-region.json");

        // 创建并初始化位置解析引擎，一般配置为全局单例
        LocationParserEngine engine = new LocationParserEngine(regionDataInput);
        // 初始化，加载数据，比较耗时
        try {
            engine.init();
        } catch (ParserEngineException e) {
            e.printStackTrace();
        }

        // 执行解析操作 122.075813,25.626133  110.137228,19.560288

        Location location = engine.parse(110.137228, 19.560288);

        // 获取省市区信息
        RegionInfo provInfo = location.getProv();
        RegionInfo cityInfo = location.getCity();
        RegionInfo districtInfo = location.getDistrict();
    }*/

    public static void main(String[] args) {
        GetLocationUtil.init();
    }

/*    public static String getLocation(double lon, double lat) {
        try {
            Thread.sleep(5L);
        } catch (Exception e) {
            log.error("", e);
        }

        Point p = new Point(lon, lat);
        District d = GC.getTown(p);
        return d.getProvince() + " " + d.getCity() + " " + d.getTown();
    }

    public static String getLocationId(double lon, double lat) {
        try {
            Thread.sleep(5L);
        } catch (Exception e) {
            log.error("", e);
        }

        Point p = new Point(lon, lat);
        District d = GC.getTown(p);
        return d.getProvinceId() + " " + d.getCityId() + " " + d.getTownId();
    }*/
}
