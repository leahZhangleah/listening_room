package com.example.listeningroom;

public class Waitmsg {

    private String fjmc;
    private String brxm;
    private String pdhm;

    public Waitmsg(String fjmc, String brxm, String pdhm) {
        this.fjmc = fjmc;
        this.brxm = brxm;
        this.pdhm = pdhm;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }
    public String getFjmc() {
        return fjmc;
    }

    public void setBrxm(String brxm) {
        this.brxm = brxm;
    }
    public String getBrxm() {
        return brxm;
    }

    public void setPdhm(String pdhm) {
        this.pdhm = pdhm;
    }
    public String getPdhm() {
        return pdhm;
    }

}