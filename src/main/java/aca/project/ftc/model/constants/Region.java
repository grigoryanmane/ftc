package aca.project.ftc.model.constants;

import lombok.Getter;

@Getter
public enum Region {

    ARAGATSOTN("Aragatsotn"),
    ARARAT("Ararat"),
    ARMAVIR("Armavir"),
    GEGHARQUNIQ("Gegharquniq"),
    LORI("Lori"),
    KOTAYQ("Kotayq"),
    SHIRAK("Shirak"),
    SYUNIQ("Syuniq"),
    VAYOTSDZOR("Vayots Dzor"),
    TAVUSH("Tavush"),
    YEREVAN("Yerevan");

    private final String key;

    Region(String key) {
        this.key = key;
    }

}
