package net.kwonoh.gephi.cli.command;

import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

import java.io.File;

public abstract class AbstractGephiCommand {
    protected ProjectController projectController = null;
    protected Workspace workspace = null;

    protected void initProjectController() {
        if (projectController == null) {
            projectController = Lookup.getDefault().lookup(ProjectController.class);
            projectController.newProject();
        }
    }

    protected void initWorkspace() {
        initProjectController();

        if (workspace == null) {
            workspace = projectController.getCurrentWorkspace();
        }
    }

    protected GraphModel importGraph(String filePath, boolean allowAutoNode) throws Exception {
        initWorkspace();

        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        Container container;
        File file = new File(filePath);
        container = importController.importFile(file);

        // Don't create missing nodes
        container.getLoader().setAllowAutoNode(allowAutoNode);
        importController.process(container, new DefaultProcessor(), workspace);

        return graphModel;
    }

    protected GraphModel importGraph(String filePath) throws Exception {
        return importGraph(filePath, false);
    }

    protected void exportGraph(String filePath) throws Exception {
        ExportController ec = Lookup.getDefault().lookup(ExportController.class);
        ec.exportFile(new File(filePath));
    }
}
