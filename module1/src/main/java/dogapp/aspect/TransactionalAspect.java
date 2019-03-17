package dogapp.aspect;

import dogapp.JdbcConnectionHolder;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.InvocationTargetException;

@AllArgsConstructor
public class TransactionalAspect {
    private final JdbcConnectionHolder connectionHolder;

    public Object transactionalMethodInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            connectionHolder.getConnection();
            Object result = joinPoint.proceed();
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
}
