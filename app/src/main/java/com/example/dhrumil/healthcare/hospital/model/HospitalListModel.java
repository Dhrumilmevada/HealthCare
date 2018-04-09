package com.example.dhrumil.healthcare.hospital.model;

/**
 * Created by Dhrumil on 4/3/2018.
 */

public class HospitalListModel {

    private String HospitalName;
    private String HospitalDistance;
    private String HospitalSpeciality;
    private String HospitalTime;
    private int HospitalRating;

    public HospitalListModel() {
    }

    public HospitalListModel(String hospitalName, String hospitalDistance, String hospitalSpeciality, String hospitalTime, int hospitalRating) {
        HospitalName = hospitalName;
        HospitalDistance = hospitalDistance;
        HospitalSpeciality = hospitalSpeciality;
        HospitalTime = hospitalTime;
        HospitalRating = hospitalRating;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getHospitalDistance() {
        return HospitalDistance;
    }

    public void setHospitalDistance(String hospitalDistance) {
        HospitalDistance = hospitalDistance;
    }

    public String getHospitalSpeciality() {
        return HospitalSpeciality;
    }

    public void setHospitalSpeciality(String hospitalSpeciality) {
        HospitalSpeciality = hospitalSpeciality;
    }

    public String getHospitalTime() {
        return HospitalTime;
    }

    public void setHospitalTime(String hospitalTime) {
        HospitalTime = hospitalTime;
    }

    public int getHospitalRating() {
        return HospitalRating;
    }

    public void setHospitalRating(int hospitalRating) {
        HospitalRating = hospitalRating;
    }
}
