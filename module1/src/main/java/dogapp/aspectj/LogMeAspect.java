package dogapp.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LogMeAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogMeAspect.class);

    @Around("@annotation(dogapp.aspectj.LogMe)")
    public Object transactionalMethodInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.info(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + " method is called");
        return joinPoint.proceed();
    }
}
