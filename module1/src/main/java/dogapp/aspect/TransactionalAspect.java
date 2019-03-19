package dogapp.aspect;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationTargetException;

@AllArgsConstructor
public class TransactionalAspect {
    private final PlatformTransactionManager transactionManager;

    public Object transactionalMethodInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object result = joinPoint.proceed();
            transactionManager.commit(transaction);
            return result;
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            if (e instanceof InvocationTargetException) {
                throw ((InvocationTargetException) e).getTargetException();
            }
            throw e;
        }
    }
}
