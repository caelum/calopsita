package br.com.caelum.calopsita.infra.vraptor.views;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

public class LogicRedirector implements Redirector {

    private static final Logger logger = Logger
            .getLogger(LogicRedirector.class);
    private final String logicName;

    public LogicRedirector(String logic) {
        if (logic == null || logic.trim().equals(""))
            throw new IllegalArgumentException(
                    "Not a valid logic for redirection: " + logic);

        this.logicName = logic;

        if (logger.isTraceEnabled())
            logger.trace("Creating LogicRedirector to " + logic);
    }

    public void redirect(LogicRequest request) {
        String logic, component;

        int dot = logicName.indexOf('.');

        // logic in the same component
        if (dot == -1) {
            logic = logicName;
            component = request.getRequestInfo().getComponentName();
        } else {
            logic = logicName.substring(dot + 1);
            component = logicName.substring(0, dot);
        }

        // dispatches
        if (logger.isDebugEnabled())
            logger.debug("Dispatching to " + component + "." + logic);

        try {
            // TODO what if we need a different URL suffix?
            request.getRequest().getRequestDispatcher(
                    "/" + component + "." + logic + ".logic").forward(
                    request.getRequest(), request.getResponse());
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
