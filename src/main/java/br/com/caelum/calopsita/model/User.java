package br.com.caelum.calopsita.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.validator.Email;
import org.hibernate.validator.NotNull;

@Entity
public class User {
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

    @ManyToMany
    private List<Project> projects;

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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
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
}
