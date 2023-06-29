package com.example.grievancecell.Models;

public class Complaint {

    String CId, grievanceType ,doi ,grievanceDetail ,evidence,userMobNum, status;

    public Complaint(String CId, String grievanceType) {
        this.CId = CId;
        this.grievanceType = grievanceType;
    }

    public Complaint(String CId, String grievanceType, String doi, String grievanceDetail, String userMobNum, String status) {
        this.CId = CId;
        this.grievanceType = grievanceType;
        this.doi = doi;
        this.grievanceDetail = grievanceDetail;
        this.userMobNum = userMobNum;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
