package com.example.demo.service;

import com.example.demo.entity.User;  // Senin User entity'n
import com.example.demo.entity.role;  // Kullanıcı rolleri için entity (varsa)
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    // 1. İçinde sarmalanan gerçek User entity’si
    private final User user;

    // 2. Constructor: dışarıdan User nesnesi alır
    public CustomUserDetails(User user) {
        this.user = user;
    }

    // 3. Kullanıcının rolleri (Yetkileri) Spring Security'nin anlayacağı forma dönüştürülür
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // User entity’sinde rolleri Set<Role> olarak tutuyorsan
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName())) // Role adını GrantedAuthority yapıyoruz
                .collect(Collectors.toSet());
    }

    // 4. Kullanıcının şifresi
    @Override
    public String getPassword() {
        return user.getUser_password();
    }

    // 5. Kullanıcı adı (username)
    @Override
    public String getUsername() {
        return user.getUser_username();
    }

    // 6. Hesap süresi doldu mu? (true = dolmadı, yani aktif)
    @Override
    public boolean isAccountNonExpired() {
        return true;  // Eğer bu alanı entity’de tutmuyorsan hep true bırakabilirsin
    }

    // 7. Hesap kilitli mi?
    @Override
    public boolean isAccountNonLocked() {
        return true;  // Kilit durumu yoksa true
    }

    // 8. Şifre süresi doldu mu?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Dolmadıysa true
    }

    // 9. Kullanıcı aktif mi? (silinmiş, pasif değil mi?)
    @Override
    public boolean isEnabled() {
        return true;  // Aktif kullanıcılar için true
    }

    // (İstersen User nesnesine direkt erişim için getter ekleyebilirsin)
    public User getUser() {
        return user;
    }
}
