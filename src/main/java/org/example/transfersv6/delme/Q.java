package org.example.transfersv6.delme;/*

In Datadog, the live tail feature shows streaming logs which match a query entered by the user. Given a stream of queries and logs, determine which logs match a previously received query. Queries are given unique IDs and successful matches indicate which query IDs matched so that they can be sent back to the correct user.
 * Click `Run` to execute the snippet below!
 */

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */
// var livetailStream = List.of(
//     "Q: database",
//     "Q: Stacktrace",
//     "Q: loading failed",
//     "L: Database service started",
//     "Q: snapshot loading",
//     "Q: fail",
//     "L: Started processing events",
//     "L: Loading main DB snapshot",
//     "L: Loading snapshot failed no stacktrace available"
// );

// var livetailOutput = List.of(
//     "ACK: database; ID=1",
//     "ACK: Stacktrace; ID=2",
//     "ACK: loading failed; ID=3",
//     "M: Database service started; Q=1",
//     "ACK: snapshot loading; ID=4",
//     "ACK: fail; ID=5",
//     "M: Loading main DB snapshot; Q=4",
//     "M: Loading snapshot failed no stacktrace available; Q=2,3,4"
// );

class Solution {
    public static void main(String[] args) {
        var livetailStream = List.of(
                "Q: database service started blah",
                "Q: database service",
                "Q: loading failed",
                "L: Database service started",
                "Q: snapshot loading",
                "Q: fail",
                "L: Started processing events",
                "L: Loading main DB snapshot",
                "L: Loading snapshot failed no stacktrace available");

        processQueriesAndLogLines(livetailStream);
    }

    //Map<String, List<(UUID, Integer)>> wordsToQueryIds

    private static void processQueriesAndLogLines(List<String> queriesAndLogs) {
        List<Query> queries = new ArrayList<>();
        for (String nextLine : queriesAndLogs) {
            if (nextLine.startsWith("Q")) {
                String query = nextLine.substring(3);
                queries.add(new Query(query));
            } else {
                String logLine = nextLine.substring(3);

                for (Query nextQuery : queries) {
                    nextQuery.match(logLine);
                }

            }
        }
    }

    public static class Query {
        private final UUID id = UUID.randomUUID();
        private final List<String> wordsInQuery;
        private final String originalQuery;

        public Query(String originalQuery) {
            this.originalQuery = originalQuery;
            this.wordsInQuery = List.of(originalQuery.toUpperCase().split(" "));
            System.out.println("ACK: " + originalQuery + "; ID=" + id);
        }

        public String toString() {
            return "originalQuery: " + originalQuery + ", wordsInQuery: " + wordsInQuery + ", id: " + id;
        }

        public void match(String logLine) {
            String upperCasedLogLine = logLine.toUpperCase();
            List<String> upperCasedLogLineWords = List.of(upperCasedLogLine.split(" "));
            for (String word : wordsInQuery) {
                if (!upperCasedLogLineWords.contains(word)) {
                    // System.out.println("log line: " + logLine + " does not have word: " + word);
                    return;
                }
            }
            System.out.println("M: " + logLine + "; Q=" + id);

        }
    }
}
