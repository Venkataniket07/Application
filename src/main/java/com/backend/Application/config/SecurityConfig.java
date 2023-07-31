package com.backend.Application.config;

//@Configuration
//@EnableMethodSecurity
public class SecurityConfig {

//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults());
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("autotest"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
}
