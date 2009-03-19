package br.com.caelum.calopsita.integration;

import br.com.caelum.seleniumdsl.Browser;

public interface BrowserFactory {

	Browser getBrowser();

	void close();

}