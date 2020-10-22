package tech.spiro.addrparser.common;

/**
 * Region data object for I/O.
 * @author Spiro Huang
 * @since 1.0
 */
public class RegionDTO {
    private String parentCode;
    private String code;
    private String name;

    /**
     * Available value: 1: "province", 2: "city", 3: "district", 4: "street"
     */
    private RegionLevel level;

    /**
     * Format:  "[longitude],[latitude]"
     */
    private String center;

    /**
     * Maybe represent multi polygons separated by "|".
     * Format:  "[longitude1],[latitude1];[longitude2],[latitude2];[longitude3],[latitude3]|[longitude4],[latitude4];..."
     */
    private String polyline;

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegionLevel getLevel() {
        return level;
    }

    public void setLevel(RegionLevel level) {
        this.level = level;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    @Override
    public String toString() {
        return "RegionDTO{" +
                "parentCode=" + parentCode +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", center='" + center + '\'' +
                ", polyline='...'" +
                '}';
    }
}
