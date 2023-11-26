package fontys.metarate.configuration.security.isauthenticated;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class IsAuthenticatedAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(IsAuthenticatedAspect.class);

    @Pointcut("@annotation(fontys.metarate.configuration.security.isauthenticated.IsAuthenticated)")
    public void annotatedMethod() {
    }

    @Pointcut("@within(fontys.metarate.configuration.security.isauthenticated.IsAuthenticated)")
    public void annotatedClass() {
    }

    @Around("(annotatedMethod() || annotatedClass()) && execution(* *(..))")
    public Object interceptMethod(final ProceedingJoinPoint interceptedMethod) throws Throwable {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            LOGGER.error("No security context found. No user authenticated.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final Authentication authentication = context.getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            LOGGER.error("Authentication token null. No user authenticated.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return interceptedMethod.proceed();
    }
}