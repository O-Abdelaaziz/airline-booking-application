package com.airline.booking.application.api.security;

import com.airline.booking.application.api.entity.User;
import com.airline.booking.application.api.exception.ResourceNotFoundException;
import com.airline.booking.application.api.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Created 26/08/2025 - 15:07
 * @Package com.airline.booking.application.api.security
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return ApplicationUserDetails.builder()
                .user(user)
                .build();
    }
}
