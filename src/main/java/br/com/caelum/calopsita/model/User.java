package br.com.caelum.calopsita.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.Email;
import org.hibernate.validator.NotNull;

import br.com.caelum.calopsita.repository.UserRepository;

@Entity
public class User implements Identifiable {
    @Id
    @NotNull
    private String login;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @Email
    @NotNull
    private String email;

    private boolean newbie;

    @Transient
    private UserRepository repository;

    public User(UserRepository repository) {
		this.repository = repository;
	}

    public User() {
	}

    public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

    private UserRepository getRepository() {
    	if (repository == null) {
    		throw new IllegalStateException("Repository was not set. You should inject it first");
    	}
		return repository;
	}

    public List<Project> getProjects() {
    	return getRepository().listAllFrom(this);
    }

    public User load() {
    	return getRepository().find(this.login);
    }
    public void save() {
    	getRepository().add(this);
    }
    public boolean isNewbie() {
		return newbie;
	}

    public void setNewbie(boolean newbie) {
		this.newbie = newbie;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String hashPassword(String password) {
        String hashword = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            hashword = hash.toString(16);
        } catch (NoSuchAlgorithmException nsae) {
            throw new IllegalStateException(nsae);
        }
        return hashword;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		return true;
	}

	public String getId() {
		return login;
	}

	public void toggleNewbie() {
		this.newbie = !this.newbie;
	}
}
