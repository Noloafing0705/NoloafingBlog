package com.noloafing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {
    private User user;
    //权限信息
    private List<String> perms;
    //用户上一次登录时间
    private Date issueAt;

    public LoginUser(User user,List<String> perms){
        this.user = user;
        this.perms = perms;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //这里可以用该方法 封装权限信息 也可以自定义
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
