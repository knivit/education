package com.tsoft.demo.ignitemessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpringContextAccessor {

    private static final Map<Class, DynamicInvocationhandler> classHandlers = new HashMap<>();
    private static ApplicationContext context;

    @Autowired
    public SpringContextAccessor(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (context == null) {
            return getProxy(clazz);
        }
        return context.getBean(clazz);
    }

    private static <T> T getProxy(Class<T> clazz) {
        DynamicInvocationhandler<T> invocationhandler = new DynamicInvocationhandler<>();
        classHandlers.put(clazz, invocationhandler);
        return (T) Proxy.newProxyInstance(
            clazz.getClassLoader(),
            new Class[]{clazz},
            invocationhandler
        );
    }

    //Use the context to get the actual beans and feed them to the invocationhandlers
    @PostConstruct
    private void init() {
        classHandlers.forEach((clazz, invocationHandler) -> {
            Object bean = context.getBean(clazz);
            invocationHandler.setActualBean(bean);
        });
    }

    static class DynamicInvocationhandler<T> implements InvocationHandler {

        private T actualBean;

        public void setActualBean(T actualBean) {
            this.actualBean = actualBean;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (actualBean == null) {
                throw new RuntimeException("Not initialized yet! :(");
            }
            return method.invoke(actualBean, args);
        }
    }
}
