package net.kwonoh.gephi.cli.command;

import java.util.concurrent.Callable;

import org.gephi.graph.api.GraphModel;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


@Command(name = "force-atlas-2",
        sortOptions = false,
        mixinStandardHelpOptions = true,
        version = "force-atlas-2 0.1")
public class ForceAtlas2Layout extends AbstractGephiCommand implements Callable<Void> {
    @Option(names = {"-i", "--in"},
            paramLabel = "FILE",
            required = true,
            description = "The input file path.")
    protected String inputFilePath;

    @Option(names = {"-o", "--out"},
            paramLabel = "FILE",
            required = true,
            description = "The output file path.")
    protected String outputFilePath;

    @Option(names = "--max-iters",
            paramLabel = "INT",
            description = "The number of maximum iterations for layout computation.")
    protected int maxIters = 100;

    @Option(names = "--thread-count",
            paramLabel = "INT",
            description = "The number of threads for layout computation.")
    protected Integer threadCount = null;

    @Option(names = "--log-perf",
            description = "Log layout performance measures. Currently, only wall clock seconds will be logged.")
    protected boolean logPerf = false;

    @Option(names = "--scaling-ratio",
            paramLabel = "NUM",
            description = "Scaling ratio parameter (default: 2.0 if |V| >= 100 else 10.0).")
    protected Double scalingRatio = null;

    @Option(names = "--strong-gravity",
            paramLabel = "BOOL",
            arity = "1",
            description = "Use strong gravity mode (default: false).")
    protected Boolean strongGravityMode = null;

    @Option(names = "--gravity",
            paramLabel = "NUM",
            description = "Gravity parameter (default: 1.0).")
    protected Double gravity = null;

    @Option(names = "--outbound-attr-dist",
            paramLabel = "BOOL",
            arity = "1",
            description = "Outbound attraction distribution parameter (default: false)")
    protected Boolean setOutboundAttractionDistribution = null;

    @Option(names = "--linlog",
            paramLabel = "BOOL",
            arity = "1",
            description = "Use LinLog mode (default: false).")
    protected Boolean linLogMode;

    @Option(names = "--adjust-sizes",
            paramLabel = "BOOL",
            arity = "1",
            description = "Adjust sizes parameter (default: false).")
    protected Boolean adjustSizes;

    @Option(names = "--edge-weight-influence",
            paramLabel = "NUM",
            description = "Edge weight influence parameter (default: 1.0).")
    protected Double edgeWeightInfluence = null;

    @Option(names = "--jitter-tol",
            paramLabel = "NUM",
            description = "Jitter tolerance parameter (default: 1.0).")
    protected Double jitterTolerance = null;

    @Option(names = "--barnes-hut",
            paramLabel = "BOOL",
            arity = "1",
            description = "Use Barnes-Hut optimization (default: true if |V| >= 1000 else false).")
    protected Boolean barnesHutOptimize;

    @Option(names = "--barnes-hut-theta",
            paramLabel = "NUM",
            description = "Barnes-Hut theta parameter (default: 1.2).")
    protected Double barnesHutTheta;


    @Override
    public Void call() throws Exception {
        GraphModel graphModel = importGraph(inputFilePath);
        computeLayout(graphModel);
        exportGraph(outputFilePath);
        return null;
    }

    protected void computeLayout(GraphModel graphModel) {
        ForceAtlas2 layout = new ForceAtlas2(null);
        layout.setGraphModel(graphModel);
        setLayoutProperties(layout);

        long layoutStart = System.nanoTime();

        layout.initAlgo();
        for (int i = 0; i < maxIters && layout.canAlgo(); i++) {
            layout.goAlgo();
        }
        layout.endAlgo();

        long layoutEnd = System.nanoTime();

        long layoutDuration = layoutEnd - layoutStart;
        double layoutWallSecs = ((double) layoutDuration) / 1E9;
        if (logPerf) {
            System.out.printf("force-atlas-2 wall seconds: %f\n", layoutWallSecs);
        }
    }

    protected void setLayoutProperties(ForceAtlas2 layout) {
        layout.resetPropertiesValues();

        if (threadCount != null)
            layout.setThreadsCount(threadCount);

        if (scalingRatio != null)
            layout.setScalingRatio(scalingRatio);

        if (strongGravityMode != null)
            layout.setStrongGravityMode(strongGravityMode);

        if (gravity != null)
            layout.setGravity(gravity);

        if (setOutboundAttractionDistribution != null)
            layout.setOutboundAttractionDistribution(setOutboundAttractionDistribution);

        if (linLogMode != null)
            layout.setLinLogMode(linLogMode);

        if (adjustSizes != null)
            layout.setAdjustSizes(adjustSizes);

        if (edgeWeightInfluence != null)
            layout.setEdgeWeightInfluence(edgeWeightInfluence);

        if (jitterTolerance != null)
            layout.setJitterTolerance(jitterTolerance);

        if (barnesHutOptimize != null)
            layout.setBarnesHutOptimize(barnesHutOptimize);

        if (barnesHutTheta != null)
            layout.setBarnesHutTheta(barnesHutTheta);
    }
}
