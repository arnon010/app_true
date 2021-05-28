package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 2/16/2017 AD.
 */

public class ApiJobObject {

    @SerializedName("List")
    private ArrayList<ListJob> listJobs;

    public ArrayList<ListJob> getListJobs() {
        return listJobs;
    }

    public void setListJobs(ArrayList<ListJob> listJobs) {
        this.listJobs = listJobs;
    }

    public class ListJob{
        @SerializedName("Id")
        private int jobId;
        @SerializedName("Title")
        private String jobName;

        public int getJobId() {
            return jobId;
        }

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }
    }
}
