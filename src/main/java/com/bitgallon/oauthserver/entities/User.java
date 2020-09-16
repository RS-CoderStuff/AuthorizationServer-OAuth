package com.bitgallon.oauthserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "OAUTH_USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME" }) })
public class User implements UserDetails, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACCOUNT_EXPIRED")
    private boolean accountExpired;

    @Column(name = "ACCOUNT_LOCKED")
    private boolean accountLocked;

    @Column(name = "CREDENTIALS_EXPIRED")
    private boolean credentialsExpired;

    @Column(name = "ENABLED")
    private boolean enabled;
    
    @Column(name = "client_id")
    private String clientId;
 

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
    @OrderBy
    @JsonIgnore
    private Collection<Authority> authorities;

    
    public boolean isAccountNonExpired() {
        return isAccountNonExpired();
    }

    
    public boolean isAccountNonLocked() {
        return isAccountNonLocked();
    }

    
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired();
    }

	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	
	public String getPassword() {
		return password;
	}

	
	public String getUsername() {
		return username;
	}

	
	public boolean isEnabled() {
		return enabled;
	}

	public String getClientId() {
		return clientId;
	}
}
