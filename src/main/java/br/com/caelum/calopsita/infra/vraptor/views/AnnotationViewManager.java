package br.com.caelum.calopsita.infra.vraptor.views;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.Viewless;
import org.vraptor.view.ViewException;
import org.vraptor.view.ViewManager;

public class AnnotationViewManager implements ViewManager {

    private static final Logger logger = Logger.getLogger(AnnotationViewManager.class);

    @Override
    public void forward(LogicRequest request, String result) throws ViewException {
        if (logger.isTraceEnabled())
            logger.trace("forward request to " + result);

        Method method = request.getLogicDefinition().getLogicMethod().getMetadata();
        if (method.isAnnotationPresent(Viewless.class))
            return;

        Redirects redirects = method.getAnnotation(Redirects.class);
        Redirector redirector = null;

        if (redirects != null) {
            redirector = getRedirector(result, redirects.value());
        } else {
            RedirectTo redirectTo = method.getAnnotation(RedirectTo.class);
            if (redirectTo != null) {
                redirector = getRedirector(result, redirectTo);
            }
        }

        // didn't find a redirector, go to view with the result name
        if (redirector == null) {
            if (logger.isTraceEnabled())
                logger.trace("No redirector found, going to view " + result);
            redirector = new ViewRedirector(result);
        }

        redirector.redirect(request);
    }

    private Redirector getRedirector(String result, RedirectTo... redirectToArray) {
        for (RedirectTo redirectTo : redirectToArray) {
            if (redirectTo.when().equals(result)) {

                // choose the right redirector based on annotation attributes
                if (!redirectTo.url().equals(""))
                    return new URLRedirector(redirectTo.url());
                else if (!redirectTo.view().equals(""))
                    return new ViewRedirector(redirectTo.view());
                else if (!redirectTo.logic().equals(""))
                    return new LogicRedirector(redirectTo.logic());
                else if (!redirectTo.file().equals(""))
                    return new FileRedirector(redirectTo.file());

            }
        }
        return null;
    }

    @Override
    public void directForward(LogicRequest arg0, String arg1, String arg2) throws ViewException {
        logger.error("Quem foi o animal que chamou esse método?");
        throw new RuntimeException("NaoSeiPraQueServeEssaPorraException");
    }

    @Override
    public void redirect(LogicRequest arg0, String arg1) throws ViewException {
        logger.error("Quem foi o animal que chamou esse método?");
        throw new RuntimeException("NaoSeiPraQueServeEssaPorraException");
    }

}
