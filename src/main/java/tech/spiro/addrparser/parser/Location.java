package tech.spiro.addrparser.parser;

import tech.spiro.addrparser.common.RegionInfo;

/**
 * Location information as result of parsing.
 *
 * @author Spiro Huang
 * @since 1.0
 */
public class Location {
    private final RegionInfo prov;
    private final RegionInfo city;
    private final RegionInfo district;
    private final RegionInfo street;

    public Location(RegionInfo prov, RegionInfo city, RegionInfo district, RegionInfo street) {
        this.prov = prov;
        this.city = city;
        this.district = district;
        this.street = street;
    }
    public RegionInfo getProv() {
        return prov;
    }

    public RegionInfo getCity() {
        return city;
    }

    public RegionInfo getDistrict() {
        return district;
    }

    public RegionInfo getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "Location{" +
                "prov=" + prov +
                ", city=" + city +
                ", district=" + district +
                '}';
    }
}
