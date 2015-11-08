package com.cglib.main;

import com.cglib.intercepter.JobsMethodInterceptor;
import com.cglib.model.JobsManager;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 8/11/15
 */
public class JobsDemo {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(JobsManager.class);
        enhancer.setCallback(new JobsMethodInterceptor());

        // jobsManager is proxified class object
        JobsManager jobsManager = (JobsManager)enhancer.create();

        // prints list of jobs from Interceptor
        System.out.println("List of jobs : " + jobsManager.getAllJobs());

        // prints jobs count from Interceptor
        System.out.println("Job count : " + jobsManager.getCount());

        // prints list of jobs from actual class
        System.out.println("Create Job : " + jobsManager.createJob("job4"));
    }
}
