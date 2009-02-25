package br.com.caelum.calopsita.infra.vraptor.views;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

public class URLRedirector implements Redirector {

    private static final Logger logger = Logger.getLogger(URLRedirector.class);

    private final String url;

    public URLRedirector(String url) {
        this.url = url;
    }

    @Override
    public void redirect(LogicRequest request) {

        if (logger.isTraceEnabled())
            logger.trace("Redirecting to URL " + url);

        try {
            new org.vraptor.plugin.niceurls.component.RedirectComponent(request.getRequest(),
                    request.getResponse()).movedTemporarily(url);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
