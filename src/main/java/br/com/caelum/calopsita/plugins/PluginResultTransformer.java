package br.com.caelum.calopsita.plugins;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

public class PluginResultTransformer implements ResultTransformer {

	private final Session session;
	private final List<Transformer> transformers;
	private final Class<?> type;

	public PluginResultTransformer(Session session, List<Transformer> transformers, Class<?> type) {
		this.session = session;
		this.transformers = transformers;
		this.type = type;
	}
	public List transformList(List list) {
		for (Transformer<?> transformer : transformers) {
			if (transformer.accepts(type)) {
				list = transformer.transform(list, session);
			}
		}
		return list;
	}

	public Object transformTuple(Object[] arg0, String[] arg1) {
		return arg0[0];
	}

}
