# Gephi CLI
A command-line interface of [Gephi](https://gephi.org/).

# Usage
```sh
java -jar gephi-cli.jar force-atlas-2 -i data/karate.graphml -o data/karate.out.graphml
```

# Parameters
Each subcommand has different set of parameters

## force-atlas-2
```
  -h, --help                Show this help message and exit.
  -V, --version             Print version information and exit.
  -i, --in=FILE             The input file path.
  -o, --out=FILE            The output file path.
      --max-iters=INT       The number of maximum iterations for layout computation.
      --thread-count=INT    The number of threads for layout computation.
      --log-perf            Log layout performance measures. Currently, only wall clock seconds will be logged.
      --scaling-ratio=NUM   Scaling ratio parameter (default: 2.0 if |V| >= 100 else 10.0).
      --strong-gravity=BOOL Use strong gravity mode (default: false).
      --gravity=NUM         Gravity parameter (default: 1.0).
      --out-bound-attr-dist=BOOL
                            Outbound attraction distribution parameter (default: false)
      --lin-log=BOOL        Use LinLog mode (default: false).
      --adjust-sizes=BOOL   Adjust sizes parameter (default: false).
      --edge-weight-influence=NUM
                            Edge weight influence parameter (default: 1.0).
      --jitter-tol=NUM      Jitter tolerance parameter (default: 1.0).
      --barnes-hut=BOOL     Use Barnes-Hut optimization (default: true if |V| >= 1000 else false).
      --barnes-hut-theta=NUM
                            Barnes-Hut theta parameter (default: 1.2).

```
