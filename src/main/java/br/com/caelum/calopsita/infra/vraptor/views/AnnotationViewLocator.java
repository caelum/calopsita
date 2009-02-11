package br.com.caelum.calopsita.infra.vraptor.views;

import org.apache.log4j.Logger;
import org.vraptor.LogicException;
import org.vraptor.component.LogicMethod;
import org.vraptor.http.VRaptorServletRequest;
import org.vraptor.url.InvalidURLException;
import org.vraptor.url.ViewLocator;
import org.vraptor.view.ViewManager;

public class AnnotationViewLocator implements ViewLocator {

    private static final Logger logger = Logger
            .getLogger(AnnotationViewLocator.class);
    private static final AnnotationViewManager ANNOTATION_VIEW_MANAGER = new AnnotationViewManager();

    public ViewManager locate(VRaptorServletRequest arg0, LogicMethod arg1,
            ViewManager arg2) throws InvalidURLException, LogicException {
        if (logger.isTraceEnabled())
            logger.trace("Returning our hacking AnnotationViewManager");

        return ANNOTATION_VIEW_MANAGER;
    }
}
