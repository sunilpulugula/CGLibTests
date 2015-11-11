package com.cglib.main;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Assert;

import com.cglib.model.JobsManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.NoOp;

/**
 * @author <a href="mailto:psunil1278@gmail.com">Sunil Kumar</a>
 * @since 8/11/15
 */
public class JobsInvocationHandlerDemo {

    public static void main(final String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(JobsManager.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
                // redirecting all object class method calls to its super
                if (method.getDeclaringClass().equals(Object.class)) {
                    return NoOp.INSTANCE;
                }
                // returning static jobs list
                if (method.getName().equals("getAllJobs")) {
                    return Arrays.asList(new String[]{"job5", "job6", "job7"});
                }
                // returning static jobs count
                if (method.getName().equals("getCount")) {
                    return 555;
                }

                // which will create end less loop, you should be more care full in using invocation handler.
                //if(method.getName().equals("createJob"))
                //return  method.invoke(obj,args);

                return null;

            }
        });

        // jobsManager is proxified class object
        JobsManager jobsManager = (JobsManager) enhancer.create();

        // jobs list will not equal with  proxy method call
        Assert.assertNotEquals(new JobsManager().getAllJobs().get(0), jobsManager.getAllJobs().get(0));

        // jobs count will not equal with  proxy method call
        Assert.assertNotEquals(new JobsManager().getCount(), jobsManager.getCount());

         //which will go end less loop if you uncomment above method.invoke
        //Assert.assertEquals(true, jobsManager.createJob("job4"));



    }
}
