package br.com.caelum.calopsita.infra.vraptor.views;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

public class FileRedirector implements Redirector {

    private static final Logger logger = Logger.getLogger(FileRedirector.class);
    private final String fileName;

    public FileRedirector(String fileName) {
        this.fileName = fileName;
    }

    public void redirect(LogicRequest request) {
        try {
            request.getRequest().getRequestDispatcher(fileName).forward(
                    request.getRequest(), request.getResponse());
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
