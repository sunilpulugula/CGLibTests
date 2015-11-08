package com.cglib.main;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Assert;

import com.cglib.model.JobsManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 8/11/15
 */
public class JobsMethodInterceptorDemo {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(JobsManager.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                // redirecting all object class method calls to its super
                if (method.getDeclaringClass().equals(Object.class)) {
                    return NoOp.INSTANCE;
                }

                // returning some static jobs list
                if (method.getName().equals("getAllJobs")) {
                    return Arrays.asList(new String[]{"job5", "job6", "job7"});
                }

                // returning some static jobs count
                if (method.getName().equals("getCount")) {
                    return 555;
                }

                // redirecting it its super class
                if (method.getName().equals("createJob")) {
                    return methodProxy.invokeSuper(obj, args);
                }

                // NoOp Instance
                return NoOp.INSTANCE;
            }
        });

        // jobsManager is proxified class object
        JobsManager jobsManager = (JobsManager) enhancer.create();

        // jobs list will not equal with  proxy method call
        Assert.assertNotEquals(new JobsManager().getAllJobs().get(0), jobsManager.getAllJobs().get(0));

        // jobs count will not equal with  proxy method call
        Assert.assertNotEquals(new JobsManager().getCount(), jobsManager.getCount());

        // here both should be true.
        Assert.assertEquals(true, jobsManager.createJob("job4"));
    }
}
