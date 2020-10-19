package fr.tyvaliarp.tyvaliamod.capabilities.jobs;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class DefaultJobs implements IJobs {
    protected List<String> jobs;

    public DefaultJobs() {
        this.jobs = Lists.newArrayList("jobs.unemployed");
    }

    @Override
    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    @Override
    public List<String> getJobs() {
        return this.jobs;
    }
}
