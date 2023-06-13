package com.synesisit.biwta.base.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synesisit.biwta.base.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.*;

@Getter @Setter
public class UserPrincipal implements UserDetails {
    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user){

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + "ADMIN");

        return new UserPrincipal(user.getId(), user.getName(), user.getUsername(),user.getEmail(), user.getPassword(), grantedAuthorities );

        /*
        // find user roles for role based authority
        Set<GrantedAuthority> roles = getRoles(user);
        // find permissions for get user authorities
        Set<GrantedAuthority> authorities = getAuthorities(user);
        //set Roles into granted authority
        if(!ObjectUtils.isEmpty(roles)){
            authorities.addAll(roles);
        }*/
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

    //NEED To Create Permission entity for set Permission
    /*public static Set<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet();
        if (user != null) {
            Set<Role> roleSet = user.getRoles();
            if (roleSet != null && roleSet.size() > 0) {
                Iterator roleIterator = roleSet.iterator();
                while (roleIterator.hasNext()) {
                    Role role = (Role) roleIterator.next();
                    if (role != null) {
                        Set<Permission> permissions = role.getPermissions();
                        Iterator permissionIterator = permissions.iterator();
                        while (permissionIterator.hasNext()) {
                            Permission permission = (Permission) permissionIterator.next();
                            if (permission != null) {
                                authorities.add(new SimpleGrantedAuthority(permission.getValue()));
                            }
                        }
                    }
                }
            }
        }
        return  authorities;
    }*/

    //NEED To Create Role entity for set Role
    /*
    public static Set<GrantedAuthority> getRoles(User user) {
        Set<GrantedAuthority> roles = new HashSet();
        if (user != null) {
            Set<Role> roleSet = user.getRoles();
            if (roleSet != null && roleSet.size() > 0) {
                Iterator roleIterator = roleSet.iterator();
                while (roleIterator.hasNext()) {
                    Role role = (Role) roleIterator.next();
                    if (role != null) {
                        String name = role.getName();
                        if(!ObjectUtils.isEmpty(name)){
                            roles.add(new SimpleGrantedAuthority("ROLE_"+name.toUpperCase()));
                        }
                    }
                }
            }
        }
        return  roles;
    }*/

    /**
     * @author Tomal Mahdi by 13/06/2021
     * @param authorityList - array list
     * @return Set - granted authority with set
     */
    public static Set<GrantedAuthority> convertToGrantedAuthority(ArrayList authorityList) {
        Set<GrantedAuthority> authorities = new HashSet();
        if (!ObjectUtils.isEmpty(authorityList)) {
            for (Object entry : authorityList) {
                Map<String,String> value = (Map<String,String>) entry;
                authorities.add(new SimpleGrantedAuthority(value.get("authority")));
            }
        }
        return  authorities;
    }
}
