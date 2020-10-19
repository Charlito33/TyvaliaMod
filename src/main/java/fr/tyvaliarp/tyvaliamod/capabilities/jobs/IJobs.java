package fr.tyvaliarp.tyvaliamod.capabilities.jobs;

import java.util.List;

public interface IJobs {
    default public void addJob(String job) {
        List<String> jobs = this.getJobs();
        jobs.add(job);
        this.setJobs(jobs);
    }

    default public void removeJob(String job) {
        List<String> jobs = this.getJobs();
        jobs.remove(job);
        this.setJobs(jobs);
    }

    public void setJobs(List<String> jobs);

    public List<String> getJobs();
}
