package net.kwonoh.gephi.cli;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.kwonoh.gephi.cli.command.ForceAtlas2Layout;
import picocli.CommandLine;
import picocli.CommandLine.Command;


@Command(name = "gephi-cli",
        sortOptions = false,
        mixinStandardHelpOptions = true,
        version = "gephi-cli 0.1",
        subcommands = {ForceAtlas2Layout.class})
public class GephiCLI implements Callable<Void> {
    @Override
    public Void call() throws Exception {
        CommandLine.usage(this, System.out);
        return null;
    }

    public static void main(String[] args) throws Exception {
        Logger.getLogger("").setLevel(Level.SEVERE);
        // System.setProperty("picocli.usage.width", "1000");
        CommandLine.call(new GephiCLI(), args);
    }
}
