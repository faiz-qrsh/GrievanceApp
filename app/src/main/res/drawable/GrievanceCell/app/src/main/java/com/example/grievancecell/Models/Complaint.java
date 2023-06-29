package com.example.grievancecell.Models;

public class Complaint {

    String CId, grievanceType ,doi ,grievanceDetail ,evidence,userMobNum;

    public Complaint(String CId, String grievanceType, String doi, String grievanceDetail, String userMobNum) {
        this.CId = CId;
        this.grievanceType = grievanceType;
        this.doi = doi;
        this.grievanceDetail = grievanceDetail;
        this.userMobNum = userMobNum;
    }

    public void setUserMobNum(String userMobNum) {
        this.userMobNum = userMobNum;
    }

    public String getUserMobNum() {
        return userMobNum;
    }

    public Complaint() {

    }

    public String getCId() {
        return CId;
    }

    public String getGrievanceType() {
        return grievanceType;
    }

    public String getDoi() {
        return doi;
    }

    public String getGrievanceDetail() {
        return grievanceDetail;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public void setGrievanceType(String grievanceType) {
        this.grievanceType = grievanceType;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setGrievanceDetail(String grievanceDetail) {
        this.grievanceDetail = grievanceDetail;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
}
