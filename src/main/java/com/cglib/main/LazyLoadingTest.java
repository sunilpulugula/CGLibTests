package com.cglib.main;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 11/11/15
 */
public class LazyLoadingTest {

    public static void main(String[] args) {

        JobManager jobsManager = new JobManager(1,(Job)Enhancer.create(Job.class,new LazyLoader() {
            @Override
            public Object loadObject() throws Exception {
                return new Job("NewJob","NewJob");
            }
        }));

        System.out.println("job id "+ jobsManager.getJobId());
        System.out.println("job " + jobsManager.getJob().getName());
    }

    static class JobManager {

        private int jobId;

        private Job job;

        JobManager(int jobId, Job job) {
            this.jobId = jobId;
            this.job = job;
        }

        public int getJobId() {
            return jobId;
        }

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public Job getJob() {
            return job;
        }

        public void setJob(Job job) {
            this.job = job;
        }
    }

    static class Job{
        private String name;
        private String desc;

        Job() {
        }

        Job(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}





