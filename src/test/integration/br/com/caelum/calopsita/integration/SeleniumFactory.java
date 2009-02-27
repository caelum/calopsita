package br.com.caelum.calopsita.integration;

import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.DefaultBrowser;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumLogLevels;

public class SeleniumFactory {

    private final Selenium selenium;

    public SeleniumFactory() {
        String seleniumPort = System.getProperty("selenium.port", "4444");
        String browser = System
                .getProperty("seleniumBrowserString", "*firefox");
        String serverHost = System.getProperty("server.host", "localhost");
        String serverTimeout = System.getProperty("server.timeout", "5000");
        String port = System.getProperty("server.port", "9095");
        selenium = new DefaultSelenium("localhost", Integer
                .valueOf(seleniumPort), browser, "http://" + serverHost + ":"
                + port);
        selenium.start();
        selenium.setContext("Calopsita");
        selenium.setBrowserLogLevel(SeleniumLogLevels.WARN);
        selenium.windowMaximize();
        selenium.setTimeout(serverTimeout);
    }

    public Browser getBrowser() {
        return new DefaultBrowser(selenium);
    }

    public void close() {
        selenium.stop();
    }

}
