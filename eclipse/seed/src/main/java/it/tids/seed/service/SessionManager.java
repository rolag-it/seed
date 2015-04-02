package it.tids.seed.service;

import it.tids.seed.model.Session;
import it.tids.seed.model.User;
import it.tids.seed.util.ParseUtil;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Definisce i metodi di business-logic per la gestione dell'entità di business
 * "Session" corrispondente ai dati di una sessione applicativa
 *
 */
@Service("sessionDetailManager")
@Transactional
public class SessionManager {
	protected final Log log = LogFactory.getLog(getClass());
	private final String ipAddress;

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public SessionManager() {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (Exception e) {
			inetAddress = null;
		}

		ipAddress = inetAddress != null ? inetAddress.getHostAddress() : Long
				.toString(System.currentTimeMillis());
	}	

	public void register(String sessionId, String clientIP, User user) {
		Session session = new Session();
		session.setSessionId(sessionId);
		session.setServerIP(ipAddress);
		session.setUser(user);

		hibernateTemplate.saveOrUpdate(session);

	}

	public void registerClientIP(String sessionId, String clientIP) {
		Session session = getBySessionId(sessionId, ipAddress);
		if (session != null) {
			session.setClientIP(clientIP);
		} else {
			session = new Session();
			session.setSessionId(sessionId);
			session.setServerIP(ipAddress);
		}

		hibernateTemplate.saveOrUpdate(session);
	}

	public void registerLogon(String sessionId, User user) {
		Session session = getBySessionId(sessionId, ipAddress);
		if (session != null) {
			session.setUser(user);
		} else {
			session = new Session();
			session.setSessionId(sessionId);
			session.setServerIP(ipAddress);
			session.setUser(user);
		}

		hibernateTemplate.saveOrUpdate(session);
	}

	public void registerLogout(String sessionId) {
		Session session = getBySessionId(sessionId, ipAddress);

		if (session != null) {
			session.setUser(null);
		} else {
			session = new Session();
			session.setSessionId(sessionId);
			session.setServerIP(ipAddress);
		}

		hibernateTemplate.saveOrUpdate(session);

	}

	public void unregister(String sessionId) {
		Session session = getBySessionId(sessionId, ipAddress);
		if (session != null) {
			hibernateTemplate.delete(session);
		}
	}

	public void clear() {
		List<Session> sessions = new ArrayList<Session>();
		sessions.addAll(getListByParams(ipAddress, null));
		for (Session session : sessions) {
			hibernateTemplate.delete(session);
		}

	}

	public Long getAuthenticatedSessions() {
		return getCountByParams(ipAddress, Boolean.FALSE);
	}

	public Long getAnonymousSessions() {
		return getCountByParams(ipAddress, Boolean.TRUE);
	}

	public List<User> getLoggedUsersList() {
		List<Session> sessions = new ArrayList<Session>();
		sessions.addAll(getListByParams(null, Boolean.FALSE));

		Set<User> users = new HashSet<User>();
		for (Session session : sessions) {
			users.add(session.getUser());
		}

		return new ArrayList<User>(users);
	}

	@SuppressWarnings("unchecked")
	private Session getBySessionId(String sessionId, String serverIP) {
		List<Session> listSession = (List<Session>) hibernateTemplate.find(
				"from Session sd where sd.sessionId=? and sd.serverIP=?",
				sessionId, serverIP);
		return (listSession.isEmpty()) ? null : (Session) listSession.get(0);
	}

	@SuppressWarnings("unchecked")
	private List<Session> getListByParams(String node, Boolean anonymous) {
		StringBuilder queryString = new StringBuilder(
				"from Session sd where sd.id is not null ");
		if (ParseUtil.isNotBlank(node)) {
			queryString.append("and sd.serverIP='").append(node).append("' ");
		}

		if (Boolean.TRUE.equals(anonymous)) {
			queryString.append("and sd.user is null");
		} else if (Boolean.FALSE.equals(anonymous)) {
			queryString.append("and sd.user is not null");
		}

		return (List<Session>) hibernateTemplate.find(queryString.toString());
	}

	private Long getCountByParams(String node, Boolean anonymous) {
		StringBuilder queryString = new StringBuilder(
				"select count(sd.id) from Session sd where sd.id is not null ");
		if (ParseUtil.isNotBlank(node)) {
			queryString.append("and sd.serverIP='").append(node).append("' ");
		}

		if (Boolean.TRUE.equals(anonymous)) {
			queryString.append("and sd.user is null");
		} else if (Boolean.FALSE.equals(anonymous)) {
			queryString.append("and sd.user is not null");
		}

		return (Long) hibernateTemplate.getSessionFactory().getCurrentSession()
				.createQuery(queryString.toString()).uniqueResult();
	}
}