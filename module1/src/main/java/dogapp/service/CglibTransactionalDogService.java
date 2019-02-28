package dogapp.service;

import dogapp.JdbcConnectionHolder;
import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

@AllArgsConstructor
public class CglibTransactionalDogService implements InvocationHandler {
    private final Object invocationTarget;
    private final JdbcConnectionHolder connectionHolder;

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        try {
            connectionHolder.getConnection();
            Object result = method.invoke(invocationTarget, objects);
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
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(invocationTarget.getClass());
        enhancer.setCallback(new CglibTransactionalDogService(invocationTarget, connectionHolder));
        return enhancer.create();
    }
}
