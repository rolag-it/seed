package it.tids.seed.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
/**
 * Rappresenta l'entità  persistente delle sessioni applicative degli utenti anonimi e non
 * per il conteggio degli accessi storici e real-time
 **/
@Entity
@Table(name="sessions")
public class Session implements Serializable {
	private static final long serialVersionUID = 894851853927247672L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;

	@Column(nullable=true, name="client_ip", length=16)
	private String clientIP;
		
	@ManyToOne(optional=true, fetch=FetchType.EAGER) 
    @JoinColumn(name="app_user", nullable=true, updatable=true)
	private User user;

	@Column(nullable=false, name="server_ip", length=32)
	@Index(name="serverIP_idx", columnNames="server_ip")
	private String serverIP;

	@Column(nullable=false, name="container_id", length=64)
	@Index(name="sessionId_idx", columnNames="container_id")
	private String sessionId;	
		
	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
		
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}	
	
	@Transient
	public boolean isNewEntity(){
		return id!=null;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", clientIP=" + clientIP + ", user="
				+ user + ", serverIP=" + serverIP + ", sessionId=" + sessionId
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientIP == null) ? 0 : clientIP.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((serverIP == null) ? 0 : serverIP.hashCode());
		result = prime * result
				+ ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Session other = (Session) obj;
		if (clientIP == null) {
			if (other.clientIP != null)
				return false;
		} else if (!clientIP.equals(other.clientIP))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (serverIP == null) {
			if (other.serverIP != null)
				return false;
		} else if (!serverIP.equals(other.serverIP))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}