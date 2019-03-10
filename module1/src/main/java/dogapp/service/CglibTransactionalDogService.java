package dogapp.service;

import dogapp.JdbcConnectionHolder;
import dogapp.dao.DogDao;
import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class CglibTransactionalDogService implements InvocationHandler {
    private final Object invocationTarget;
    private final JdbcConnectionHolder connectionHolder;
    private DogDao dogDao;

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        try {
            connectionHolder.getConnection();
            Object result = method.invoke(invocationTarget, objects);
            connectionHolder.commit();
            return result;
        } catch (Exception e) {
            connectionHolder.rollback();
            if (e instanceof InvocationTargetException) {
                throw ((InvocationTargetException) e).getTargetException();
            }
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
    }

    public static Object newInstance(Object invocationTarget, JdbcConnectionHolder connectionHolder, DogDao dogDao) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(invocationTarget.getClass());
        enhancer.setCallback(new CglibTransactionalDogService(invocationTarget, connectionHolder, dogDao));
        return enhancer.create(new Class[]{DogDao.class}, new Object[]{dogDao});
    }
}
