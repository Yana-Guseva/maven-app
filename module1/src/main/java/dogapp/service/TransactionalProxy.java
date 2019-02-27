package dogapp.service;

import dogapp.JdbcConnectionHolder;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class TransactionalProxy implements InvocationHandler {
    private final Object invocationTarget;
    private final JdbcConnectionHolder connectionHolder;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            connectionHolder.getConnection();
            Object result = method.invoke(invocationTarget, args);
            connectionHolder.commit();
            return result;
        } catch (Exception e) {
            connectionHolder.rollback();
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
    }

    public static Object newInstance(Object invocationTarget, JdbcConnectionHolder connectionHolder) {
        return Proxy.newProxyInstance(invocationTarget.getClass().getClassLoader(),
                invocationTarget.getClass().getInterfaces(),
                new TransactionalProxy(invocationTarget, connectionHolder));
    }
}
