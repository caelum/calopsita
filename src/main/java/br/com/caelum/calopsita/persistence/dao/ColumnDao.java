package br.com.caelum.calopsita.persistence.dao;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Column;
import br.com.caelum.calopsita.repository.Columns;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class ColumnDao implements Columns{
	
	final private Session session;

	public ColumnDao(Session session) {
        this.session = session;
    }

	public void add(Column Column) {
        this.session.save(Column);
    }
}
