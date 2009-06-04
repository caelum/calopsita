package br.com.caelum.calopsita.infra.sitemesh;

import java.io.IOException;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;

public class MyDecoratorMapper implements DecoratorMapper {

	private DecoratorMapper parent;
	private Properties excludes;

	public void init(Config config, Properties properties, DecoratorMapper parent) throws InstantiationException {
		excludes = new Properties();
		try {
			excludes.load(MyDecoratorMapper.class.getResourceAsStream("/excludes.properties"));
		} catch (IOException e) {
			throw new InstantiationException(e.getMessage());
		}
		this.parent = parent;
	}

	public Decorator getDecorator(HttpServletRequest request, Page page) {
		if (shouldExclude(request)) {
			return null;
		}
		return parent.getDecorator(request, page);
	}

	private boolean shouldExclude(HttpServletRequest request) {
		String method = request.getParameter("_method");
		if (method == null) {
			method = request.getMethod();
		}
		String uri = request.getRequestURI().replaceFirst(request.getContextPath(), "");
		for (Entry<Object, Object> exclude : excludes.entrySet()) {
			if(uri.matches((String) exclude.getKey()) && method.equals(exclude.getValue())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Decorator getNamedDecorator(HttpServletRequest request, String decorator) {
		if (shouldExclude(request)) {
			return null;
		}
		return parent.getNamedDecorator(request, decorator);
	}
}
