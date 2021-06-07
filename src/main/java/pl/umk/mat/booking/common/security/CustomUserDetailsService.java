package pl.umk.mat.booking.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.umk.mat.booking.model.Employee;
import pl.umk.mat.booking.model.UserRole;
import pl.umk.mat.booking.repository.EmployeeRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (!employeeRepository.existsByEmail(email))
            throw new UsernameNotFoundException("User not found");
        var employee = employeeRepository.findByEmail(email);
        return new User(
                employee.getEmail(),
                employee.getPassword(),
                convertAuthorities(employee.getRoles())
        );
    }

    private Set<GrantedAuthority> convertAuthorities(Set<UserRole> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(userRole -> authorities.add(new SimpleGrantedAuthority(userRole.getRole())));
        return authorities;
    }
}
