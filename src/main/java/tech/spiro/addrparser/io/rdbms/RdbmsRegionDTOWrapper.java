package tech.spiro.addrparser.io.rdbms;

import tech.spiro.addrparser.common.RegionDTO;
import tech.spiro.addrparser.common.RegionLevel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Rdbms wrapper of {@link RegionDTO}, add support of read from and write to rdbms.
 * @author Spiro Huang
 * @since 1.0
 */
public class RdbmsRegionDTOWrapper extends RegionDTO {

    private RegionDTO regionDTO;

    public RdbmsRegionDTOWrapper(RegionDTO regionDTO) {
        this.regionDTO = regionDTO;
    }

    public void read(ResultSet rs) throws SQLException {
        this.setCode(rs.getString("code"));
        this.setParentCode(rs.getString("parent_code"));
        this.setName(rs.getString("name"));
        this.setLevel(RegionLevel.valueOf(rs.getInt("level")));
        this.setCenter(rs.getString("center"));
        this.setPolyline(rs.getString("polyline"));
    }

    public void write(PreparedStatement stmt) throws SQLException {
        stmt.setString(    1, this.getCode());
        stmt.setString(    2, this.getParentCode());
        stmt.setString( 3, this.getName());
        stmt.setInt(    4, this.getLevel().ordinal());
        stmt.setString( 5, this.getCenter());
        stmt.setString( 6, this.getPolyline());
    }

    @Override
    public String getParentCode() {
        return this.regionDTO.getParentCode();
    }

    @Override
    public void setParentCode(String parentCode) {
        this.regionDTO.setParentCode(parentCode);
    }

    @Override
    public String getCode() {
        return this.regionDTO.getCode();
    }

    @Override
    public void setCode(String code) {
        this.regionDTO.setCode(code);
    }

    @Override
    public String getName() {
        return this.regionDTO.getName();
    }

    @Override
    public void setName(String name) {
        this.regionDTO.setName(name);
    }

    @Override
    public RegionLevel getLevel() {
        return this.regionDTO.getLevel();
    }

    @Override
    public void setLevel(RegionLevel level) {
        this.regionDTO.setLevel(level);
    }

    @Override
    public String getCenter() {
        return this.regionDTO.getCenter();
    }

    @Override
    public void setCenter(String center) {
        this.regionDTO.setCenter(center);
    }

    @Override
    public String getPolyline() {
        return this.regionDTO.getPolyline();
    }

    @Override
    public void setPolyline(String polyline) {
        this.regionDTO.setPolyline(polyline);
    }


}
