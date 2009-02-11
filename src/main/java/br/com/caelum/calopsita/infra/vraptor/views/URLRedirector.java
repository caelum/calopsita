package br.com.caelum.calopsita.infra.vraptor.views;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

import br.com.caelum.vraptor.plugin.niceurl.vraptor.RedirectComponent;

public class URLRedirector implements Redirector {

    private static final Logger logger = Logger.getLogger(URLRedirector.class);

    private final String url;

    public URLRedirector(String url) {
        this.url = url;
    }

    public void redirect(LogicRequest request) {

        if (logger.isTraceEnabled())
            logger.trace("Redirecting to URL " + url);

        try {
            new RedirectComponent(request.getRequest(), request.getResponse())
                    .movedTemporarily(url);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
