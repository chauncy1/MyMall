package com.capgemini.man.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SpringSecurity需要的用户详情
 * Created by macro on 2018/4/26.
 */
@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class MyUserDetails implements UserDetails {
    private UserInfo userInfo;
    private List<UserRole> roleList;
//    private List<SimpleGrantedAuthority> authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
    	return roleList;
//    	return roleList.stream()
//                .filter(role -> role.getRoleName()!=null)
//                .map(role ->new SimpleGrantedAuthority(role.getRoleName()))
//                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userInfo.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUserName();
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
