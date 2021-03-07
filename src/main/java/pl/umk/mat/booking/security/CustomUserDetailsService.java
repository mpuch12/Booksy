package pl.umk.mat.booking.security;

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
import java.util.Optional;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private EmployeeRepository employeeRepository;

    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if(employee.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new User(
                employee.get().getEmail(),
                employee.get().getPassword(),
                convertAuthorities(employee.get().getRoles())
        );
    }

    private Set<GrantedAuthority> convertAuthorities(Set<UserRole> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(userRole -> authorities.add(new SimpleGrantedAuthority(userRole.getRole())));
        return authorities;
    }
}
