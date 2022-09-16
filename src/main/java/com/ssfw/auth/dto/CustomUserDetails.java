package com.ssfw.auth.dto;

import com.ssfw.auth.entity.AuthRoleEntity;
import com.ssfw.auth.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author a
 * @date 2022/9/16
 * @since 2.7.3
 */
@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String status;
    private String nickname;
    private Integer tenantId;

    private List<String> roles;


    public CustomUserDetails(@NotNull UserEntity user,@NotNull List<String> roles) {
        this.id = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getUseStatus();
        this.nickname = user.getNickname();
        this.tenantId = user.getTenantId();
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //重写getAuthorities方法，将用户的角色作为权限
        return this.getRoles().stream().map((Function<String, GrantedAuthority>) SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !UserEntity.STATUS_SUSPEND.equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserEntity.STATUS_VALID.equals(status);
    }
}
