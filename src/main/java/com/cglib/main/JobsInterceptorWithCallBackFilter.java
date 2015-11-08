package com.cglib.main;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Assert;

import com.cglib.model.JobsManager;
import net.sf.cglib.proxy.*;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 8/11/15
 */
public class JobsInterceptorWithCallBackFilter {

    public static void main(final String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(JobsManager.class);
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                // redirecting all object class method calls to its super
                if (method.getDeclaringClass().equals(Object.class)) {
                    return 0;
                }

                // returning some static jobs list
                if (method.getName().equals("getAllJobs")) {
                    return 1;
                }

                // returning some static jobs count
                if (method.getName().equals("getCount") || method.getName().equals("createJob")) {
                    return 2;
                }

                return 0;
            }
        });

        enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE, new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return Arrays.asList(new String[]{"job5", "job6", "job7"});
            }
        }, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                return methodProxy.invokeSuper(obj, args);
            }
        }});

        // jobsManager is proxified class object
        JobsManager jobsManager = (JobsManager) enhancer.create();

        // jobs list will not equal with  proxy method call
        Assert.assertNotEquals(new JobsManager().getAllJobs().get(0), jobsManager.getAllJobs().get(0));

        // jobs count will be equal with  proxy method call
        Assert.assertEquals(new JobsManager().getCount(), jobsManager.getCount());

        // here both should be true.
        Assert.assertEquals(true, jobsManager.createJob("job4"));


    }
}
