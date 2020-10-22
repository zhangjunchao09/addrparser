package tech.spiro.addrparser.parser;

import java.io.*;
import java.util.*;

import tech.spiro.addrparser.common.*;
import tech.spiro.addrparser.io.RegionDataInput;

/**
 * The engine to parse {@link Point} or longitude/latitude pair to {@link Location}.
 *
 * @author Spiro Huang
 * @since 1.0
 */
public class LocationParserEngine {

    private RegionDataInput regionDataInput;

    /**
     * Province region info set
     */
    private Set<RegionInfo> provinceSet = new HashSet<>();

    /**
     * Province and city set mapping
     * Key: province code  Value: province provCitySetMap
     */
    private Map<String, Set<RegionInfo>> provCitySetMap = new HashMap<>();

    /**
     * City and district or street set mapping
     * Key: city code Value: district or street
     */
    private Map<String, Set<RegionInfo>> cityDistrictSetMap = new HashMap<>();

    private Map<String, Set<RegionInfo>> districtStreetSetMap = new HashMap<>();

    public LocationParserEngine(RegionDataInput regionDataInput) {
        this.regionDataInput = regionDataInput;
    }

    public void init() throws ParserEngineException {
        try {
            regionDataInput.init();
        } catch (IOException e) {
            throw new ParserEngineException(e.getMessage(), e);
        }

        try {
            while (true) {
                RegionDTO regionDTO = regionDataInput.read();
                if (regionDTO == null) {
                    break;
                }
                sortThrough(regionDTO);
            }
        } catch (IOException e) {
            throw new ParserEngineException(e.getMessage(), e);
        } finally {
            try {
                regionDataInput.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * SortThrough region info.
     */
    private void sortThrough(RegionDTO regionDTO) {
        RegionInfo regionInfo = RegionConverter.convert(regionDTO);
        RegionLevel regionLevel = regionInfo.getLevel();

        if (regionLevel == RegionLevel.PROVINCE) {
            provinceSet.add(regionInfo);
            return;
        }

        if (regionLevel == RegionLevel.CITY) {
            String provCode = regionInfo.getParentCode();
            Set<RegionInfo> provCitySet = provCitySetMap.get(provCode);
            if (provCitySet == null) {
                provCitySet = new HashSet<>();
            }
            provCitySet.add(regionInfo);
            provCitySetMap.put(provCode, provCitySet);
            return;
        }

        if (regionLevel == RegionLevel.DISTRICT) {
            String cityCode = regionInfo.getParentCode();
            Set<RegionInfo> cityDistrictSet = cityDistrictSetMap.get(cityCode);
            if (cityDistrictSet == null) {
                cityDistrictSet = new HashSet<>();
            }
            cityDistrictSet.add(regionInfo);
            cityDistrictSetMap.put(cityCode, cityDistrictSet);
        }

        if (regionLevel == RegionLevel.STREET) {
            String districtCode = regionInfo.getParentCode();
            Set<RegionInfo> districtStreetSet = districtStreetSetMap.get(districtCode);
            if (districtStreetSet == null) {
                districtStreetSet = new HashSet<>();
            }
            districtStreetSet.add(regionInfo);
            districtStreetSetMap.put(districtCode, districtStreetSet);
        }
    }

    /**
     * Get location info by {@link Point}
     *
     * @param point {@link Point} to parse.
     * @return location info
     */
    public Location parse(Point point) {

        if (point == null) {
            throw new IllegalArgumentException("<point> is null.");
        }

        RegionInfo prov = null;
        RegionInfo city = null;
        RegionInfo district = null;
        RegionInfo street = null;

        for (RegionInfo regionInfo : provinceSet) {
            if (regionInfo.contain(point)) {
                prov = regionInfo;
                break;
            }
        }

        if (prov == null) {
            return null;
        }

        String provCode = prov.getCode();
        Set<RegionInfo> citySet = provCitySetMap.get(provCode);

        if (citySet == null) {
            return new Location(prov, null, null, null);
        }

        for (RegionInfo regionInfo : citySet) {
            if (regionInfo.contain(point)) {
                city = regionInfo;
                break;
            }
        }

        if (city == null) {
            return new Location(prov, null, null, null);
        }

        String cityCode = city.getCode();
        Set<RegionInfo> districtSet = cityDistrictSetMap.get(cityCode);

        if (districtSet == null) {
            return new Location(prov, city, null, null);
        }

        for (RegionInfo regionInfo : districtSet) {
            if (regionInfo.contain(point)) {
                district = regionInfo;
                break;
            }
        }
        String districtCode = district.getCode();
        Set<RegionInfo> streetSet = districtStreetSetMap.get(districtCode);

        if (streetSet == null) {
            return new Location(prov, city, district, null);
        }

        for (RegionInfo regionInfo : streetSet) {
            if (regionInfo.contain(point)) {
                street = regionInfo;
                break;
            }
        }

        return new Location(prov, city, district, street);
    }

    /**
     * Get location info by longitude/latitude
     *
     * @param lon longitude
     * @param lat latitude
     * @return location info
     */
    public Location parse(double lon, double lat) {
        Point point = new Point(lon, lat);
        return parse(point);
    }


    /**
     * Get region info by {@code region.code}
     *
     * @param code region code
     * @return region info
     */
    public RegionInfo getRegionInfo(int code) {
        throw new NotImplementedException();
    }

    /**
     * Get region info by {@code region.name}
     *
     * @param name region name
     * @return region info
     */
    public RegionInfo getRegionInfo(String name) {
        throw new NotImplementedException();
    }

    /**
     * Get sub region info list by {@code region.code}
     *
     * @param code region code
     * @return region info
     */
    public List<RegionInfo> getSubRegionInfoList(int code) {
        throw new NotImplementedException();
    }
}
