package aca.project.ftc.model.constants;

public enum Region {

    ARAGATSOTN("Aragatsotn"),
    ARARAT("Ararat"),
    ARMAVIR("Armavir"),
    GEGHARQUNIQ("Gegharquniq"),
    //TODO::CHAGE LORY TO LORI FROM FRONT ENT
    LORI("Lori"),
    KOTAYQ("Kotayq"),
    SHIRAK("Shirak"),
    SYUNIQ("Syunik"),
    VAYOTSDZOR("Vayots Dzor"),
    TAVUSH("Tavush"),
    YEREVAN("Yerevan");

    private final String key;

    Region(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


}
