package br.com.caelum.calopsita.tasks;

public class Context {

    private String war;

    private String base;

    private String lib;

    private String classes;

    private String context;

    public void setBase(String baseDir) {
        this.base = baseDir;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setLib(String libDir) {
        this.lib = libDir;
    }

    public String getWar() {
        return war;
    }

    public void setWar(String war) {
        this.war = war;
    }

    public String getContext() {
        return context;
    }

}
