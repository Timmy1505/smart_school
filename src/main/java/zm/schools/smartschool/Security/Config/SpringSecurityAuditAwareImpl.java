package zm.schools.smartschool.Security.Config;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.NonNull;
import zm.schools.smartschool.Models.AuthUser;

import java.util.Objects;
import java.util.Optional;

public class SpringSecurityAuditAwareImpl implements AuditorAware<String> {

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        AuthUser customUserDetails = (AuthUser) Objects.requireNonNull(authentication).getPrincipal();

        return Optional.ofNullable(customUserDetails.getEmployeeNumber());
    }
}