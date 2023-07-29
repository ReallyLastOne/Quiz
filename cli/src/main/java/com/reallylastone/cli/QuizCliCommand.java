package com.reallylastone.cli;

import com.reallylastone.cli.command.auth.LoginCommand;
import com.reallylastone.cli.command.phrase.PhraseCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "quiz", description = "...", subcommands = {PhraseCommand.class, LoginCommand.class},
        mixinStandardHelpOptions = true)
public class QuizCliCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(QuizCliCommand.class, args);
    }

    @Override
    public void run() {
        System.out.println("QuizCliCommand called");

        if (verbose) {
            System.out.println("Verbose flag supplied!");
        }
    }
}
