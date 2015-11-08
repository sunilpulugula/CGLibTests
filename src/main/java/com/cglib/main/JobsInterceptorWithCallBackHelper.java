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
public class JobsInterceptorWithCallBackHelper {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(JobsManager.class);

        CallbackHelper callbackHelper = new CallbackHelper(JobsManager.class, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                if (method.getDeclaringClass().equals(Object.class)) {
                    return NoOp.INSTANCE;
                }
                if (method.getName().equals("getAllJobs")) {
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            return Arrays.asList(new String[]{"job5", "job6", "job7"});
                        }
                    };
                }
                if (method.getName().equals("getCount") || method.getName().equals("createJob")) {
                    return new MethodInterceptor() {
                        @Override
                        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                            return methodProxy.invokeSuper(obj, args);
                        }
                    };
                }
                return NoOp.INSTANCE;
            }
        };

        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());

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


