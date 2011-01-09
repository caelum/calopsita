package br.com.caelum.calopsita.plugins.kanban;

import static br.com.caelum.vraptor.view.Results.logic;
import br.com.caelum.calopsita.model.Column;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;

@Resource
public class KanbanController {

	private final Result result;
    private final Validator validator;

    public KanbanController(Result result, Validator validator) {
        this.result = result;
		this.validator = validator;
    }

    @Path("/projects/{project.id}/kanban_configuration") @Get
    public void list(Project project) {
		Project projectLoaded = project.load();
		this.result.include("project", projectLoaded);
		this.result.include("kanbanColumnList", projectLoaded.getColumns());
    }

    @Path("/projects/{column.project.id}/kanban_configuration") @Post
	public void save(Column column) {
    	column.save();
		result.use(logic()).redirectTo(KanbanController.class).list(column.getProject());
	}

}
