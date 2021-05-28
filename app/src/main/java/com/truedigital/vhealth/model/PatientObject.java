package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class PatientObject {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("UserId")
    private int userId;
    @SerializedName("Username")
    private String username;
    @SerializedName("Email")
    private String email;
    @SerializedName("Telephone")
    private String telephone;
    @SerializedName("ProfileImage")
    private String profileImage;
    @SerializedName("Name")
    private String name;

    public int getPatientId() { return patientId;  }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getUserId() { return userId;  }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() { return username;  }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() { return email;  }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() { return telephone;  }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProfileImage() { return profileImage;  }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() { return name;  }

    public void setName(String name) {
        this.name = name;
    }


}
