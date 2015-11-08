package com.cglib.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 8/11/15
 */
public class JobsManager {

    private static List<String> jobs = new ArrayList<String>();

    static {
        jobs.add("job1");
        jobs.add("job2");
        jobs.add("job3");
    }

    public List<String> getAllJobs() {
        return jobs;
    }

    public int getCount() {
        return jobs.size();
    }

    public boolean createJob(String name) {
        return jobs.add(name);
    }


}
