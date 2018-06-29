package com.ztech.ui;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.ztech.MainProcessor;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class InputReader {

    public static void main(String[] args) {
        Options options = buildCommandLineArgs();

        if (args.length <= 0) {
            // automatically generate the help statement
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Bowling", options);
            System.exit(0);
        }
        // create the parser
        CommandLineParser parser = new BasicParser();
        CommandLine line = null;
        try {
            // parse the command line arguments
            line = parser.parse(options, args);
        } catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }

        new InputReader().run(line);

    }

    private void run(CommandLine line) {

        // initialise the member variable
        if (line.hasOption("bowlingFile")) {
            String bowlingFile = line.getOptionValue("bowlingFile");
            Map<String, List<Integer>> bowlingData = new HashMap<>();
            try
                    (
                            Stream<String> stream = (Files.lines(Paths.get(bowlingFile)))) {
                // Supplier<List<String>> supplier = () ->  new LinkedList<String>();
                List<String> bowlingList = stream.map(s -> s.replace("F", "-1")).collect(Collectors.toCollection(LinkedList::new));
                ListMultimap<String, Integer> multimap = LinkedListMultimap.create();

                bowlingList.stream().forEach(s -> {
                    String[] strAray = StringUtils.split(s.trim(), " ");
                    if (strAray != null && strAray.length == 2) {
                        try {
                            multimap.put(strAray[0], Integer.parseInt(strAray[1]));
                        } catch (NumberFormatException ex) {
                            System.out.println("Please check your pinfall values.");
                        }
                    }
                });


                MainProcessor main = new MainProcessor();
                main.setBowlingData(multimap);
                main.process();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static Options buildCommandLineArgs() {

        Option bowlingFile = OptionBuilder
                .withDescription("Provide path to bowling-score file").hasArg().create("bowlingFile");
        Options options = new Options();
        options.addOption(bowlingFile);

        return options;

    }


}
