package com.cglib.intercepter;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 8/11/15
 */
public class JobsMethodInterceptor implements MethodInterceptor {

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
}
