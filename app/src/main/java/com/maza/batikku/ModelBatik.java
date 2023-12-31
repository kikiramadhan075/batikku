package com.maza.batikku;

public class ModelBatik {
    private String NoId;
    private String Batik;
    private String Asal;
    private String Makna;
    private String key;

    public ModelBatik(){

    }

    public ModelBatik(String noId, String batik, String asal, String makna) {
        NoId = noId;
        Batik = batik;
        Asal = asal;
        Makna = makna;
    }

    public String getNoId() {
        return NoId;
    }

    public void setNoId(String noId) {
        NoId = noId;
    }

    public String getBatik() {
        return Batik;
    }

    public void setBatik(String batik) {
        Batik = batik;
    }

    public String getAsal() {
        return Asal;
    }

    public void setAsal(String asal) {
        Asal = asal;
    }

    public String getMakna() {
        return Makna;
    }

    public void setMakna(String makna) {
        Makna = makna;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
