package br.com.caelum.calopsita.infra.vraptor.views;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

public class ViewRedirector implements Redirector {
    private static final Logger logger = Logger.getLogger(ViewRedirector.class);

    // TODO how to get this from vraptor.xml?
    private static final String REGEX_LOCATION = "/WEB-INF/jsp/$component/$logic.$result.jsp";
    private final String view;

    public ViewRedirector(String view) {
        if (view == null || view.trim().equals(""))
            throw new IllegalArgumentException("Not a valid view for redirection: " + view);

        this.view = view;
        if (logger.isTraceEnabled())
            logger.trace("Creating ViewRedirector to " + view);
    }

    @Override
    public void redirect(LogicRequest request) {

        String thisLogicName = request.getRequestInfo().getLogicName();
        String thisComponentName = request.getRequestInfo().getComponentName();

        String filename = getFileName(thisComponentName, thisLogicName);
        if (logger.isDebugEnabled())
            logger.debug("Redirecting to JSP: " + filename);

        try {
            request.getRequest().getRequestDispatcher(filename).forward(request.getRequest(),
                    request.getResponse());
        } catch (Exception e) {
            logger.error(e);
        }
    }

    String getFileName(String thisComponentName, String thisLogicName) {
        String result, logic, component;

        // "result"
        int lastDot = view.lastIndexOf('.');
        if (lastDot == -1) {
            result = view;
            logic = thisLogicName;
            component = thisComponentName;
        } else {
            result = view.substring(lastDot + 1);
            int firstDot = view.lastIndexOf('.', lastDot - 1);

            // "logic.result"
            if (firstDot == -1) {
                logic = view.substring(0, lastDot);
                component = thisComponentName;
            } else {
                // "component.logic.result"
                logic = view.substring(firstDot + 1, lastDot);
                component = view.substring(0, firstDot);
            }
        }

        if (logger.isDebugEnabled())
            logger.debug("Found view redirection to: " + component + "." + logic + "." + result);

        return REGEX_LOCATION.replaceFirst("\\$component", component).replaceFirst("\\$logic",
                logic).replaceFirst("\\$result", result);
    }

}
