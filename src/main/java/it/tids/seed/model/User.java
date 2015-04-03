package it.tids.seed.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Rappresenta l'entità persistente di utente applicativo;
 *  
 **/
@Entity
@Table(name="app_users")
public class User implements Serializable {
	private static final long serialVersionUID = 3832626162173359411L;

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(nullable=false,length=32,unique=true)
    private String username;
    
    @Column(nullable=false)
    private String password;	               
    
    @Column(name="first_name",nullable=false, length=64)
    private String firstName;                  
    
    @Column(name="last_name", nullable=false, length=64)
    private String lastName;                   
    
    @Version
    private Integer version;
    
    @ManyToMany(fetch = FetchType.EAGER, targetEntity=Role.class) 
    @JoinTable(
            name="user_roles", 
            joinColumns = { @JoinColumn( name="user_id",referencedColumnName="id", nullable=false, updatable=false) },
            inverseJoinColumns = @JoinColumn( name="role_id",referencedColumnName="id", nullable=false, updatable=true)            
    ) 
    private Set<Role> roles = new LinkedHashSet<Role>();
    
    @Column(name="email", nullable=true,length=256)
    private String email;
    
    @Column(name="pwd_time", nullable=true)
    private Timestamp passwordTime;

    @Column(name="otp", nullable=false)
    private boolean oneTimePassword;
    
    @Column(name="enabled", nullable=false)
    private boolean enabled;   
    
    public Long getId() {
		return id;
	}

    protected void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getPasswordTime() {
		return passwordTime;
	}

	public void setPasswordTime(Timestamp passwordTime) {
		this.passwordTime = passwordTime;
	}

	public boolean isOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(boolean oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
    public String getFullName() {
        return firstName + ' ' + lastName;
    }   

    @Transient
    public String getRolesDescription(){
    	StringBuilder sb = new StringBuilder();

    	for(Role role: roles){
    		sb.append(" "+role.getDescription());
    		sb.append(";");
    	}
    	if (sb.length()>0) {
    		sb.deleteCharAt(sb.length()-1);
    	}
    	
    	return sb.toString();
    }
    
    @Transient
	public boolean isNewEntity(){
		return id==null;
	}
    
    @Transient
    public String getRolesName(){
    	StringBuilder sb = new StringBuilder();

    	for(Role role: roles){
    		sb.append(" "+role.getName());
    		sb.append(";");
    	}
    	sb.deleteCharAt(sb.length()-1);
    	
    	return sb.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (oneTimePassword ? 1231 : 1237);
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((passwordTime == null) ? 0 : passwordTime.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled != other.enabled)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (oneTimePassword != other.oneTimePassword)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (passwordTime == null) {
			if (other.passwordTime != null)
				return false;
		} else if (!passwordTime.equals(other.passwordTime))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", version=" + version + ", roles=" + roles
				+ ", email=" + email + ", passwordTime=" + passwordTime
				+ ", oneTimePassword=" + oneTimePassword + ", enabled="
				+ enabled + "]";
	}   
	   
}