package com.reallylastone.cli.command.phrase;

import picocli.CommandLine;

@CommandLine.Command(name = "phrase", subcommands = {AddPhraseCommand.class, GetAllPhrasesCommand.class})
public class PhraseCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("Phrase Command");
    }
}

