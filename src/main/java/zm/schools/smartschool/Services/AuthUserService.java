package zm.schools.smartschool.Services;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import zm.schools.smartschool.DTOs.AuthUserDTO;

public interface AuthUserService extends UserDetailsService {

    AuthUserDTO createAuthUser(AuthUserDTO authUserDTO);

    AuthUserDTO getAuthUserById(Long id);

    AuthUserDTO updateAuthUser(Long id, AuthUserDTO authUserDTO);

    void deleteAuthUser(Long id);

    // The method from UserDetailsService
    UserDetails loadUserByUsername(String employeeNumber);

    List<AuthUserDTO> getAllAuthUsers();
}

