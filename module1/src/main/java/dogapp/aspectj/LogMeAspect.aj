package dogapp.aspectj;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public aspect LogMeAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogMeAspect.class);

    pointcut logMethodInvocation(): @annotation(dogapp.aspectj.LogMe);

    Object around(): logMethodInvocation() {
        LOG.info(thisJoinPoint.getTarget().getClass().getName() + "." + thisJoinPoint.getSignature().getName() + " method is called");
        return proceed();
    }
}
