package br.com.caelum.calopsita.infra.vraptor;

import java.io.File;
import java.io.IOException;

public interface JarParser {

	public abstract void parse(File file);

}