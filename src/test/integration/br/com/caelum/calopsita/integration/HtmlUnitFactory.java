package br.com.caelum.calopsita.integration;

import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.htmlunit.HtmlUnitBrowser;

public class HtmlUnitFactory implements BrowserFactory {

    public Browser getBrowser() {
    	String serverHost = System.getProperty("server.host", "localhost");
    	String port = System.getProperty("server.port", "9095");
    	return new HtmlUnitBrowser("http://" + serverHost + ":" + port);
    }

    public void close() {
    }

}
