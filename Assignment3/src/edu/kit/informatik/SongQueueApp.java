package edu.kit.informatik;

import java.util.List;

public class SongQueueApp {

    private static final String IDERROR = "ID must be an integer in interval (0, 10^5)!";
    private static final String LENGTHERROR = "length must be given in seconds as an integer in interval (0, 2^31)!";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜẞ abcdefghijklmnopqrstuvwxyzäöüß";
    private static final SongQueue QUEUE = new SongQueue();

    private static boolean isAlphabet(String word) {
        for (char c : word.toCharArray()) {
            String letter = String.valueOf(c);

            if (!ALPHABET.contains(letter)) {
                return false;
            }
        }
        return true;
    }

    private static int intParser(String intString, int min, int max, String errorMess) throws NumberFormatException {
        int parsed;
        try {
            parsed = Integer.parseInt(intString);

            if (parsed <= min || parsed >= max) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Terminal.printError(errorMess);
            throw new NumberFormatException();
        }
        return parsed;
    }

    private static boolean alphabetCorrect(String word, String checkedName) {
        if (!isAlphabet(word)) {
            Terminal.printError(checkedName + " must be given using German alphabet!");
            return false;
        }
        return true;
    }

    private static void add(String[] input) {
        if (input.length == 1) {
            Terminal.printError("song attributes must be specified!");
            return;
        }

        String attr = "";
        for (int i = 1; i < input.length; i++) {
            attr += input[i] + " ";
        }
        attr = attr.substring(0, attr.length() - 1);

        String[] songAttr = attr.split(":");

        if (songAttr.length != 5) {
            Terminal.printError("song attributes must be specified as following: "
                    + "<id>:<artist>:<title>:<length>:<priority>");
            return;
        }

        int id;
        try {
            id = intParser(songAttr[0], 0, 100000, IDERROR);
        } catch (NumberFormatException e) {
            return;
        }

        if (!alphabetCorrect(songAttr[1], "artist")) {
            return;
        }

        if (!alphabetCorrect(songAttr[2], "title")) {
            return;
        }

        int length;
        try {
            length = intParser(songAttr[3], 0, Integer.MAX_VALUE, LENGTHERROR);
        } catch (NumberFormatException e) {
            return;
        }

        int priority;
        try {
            priority = intParser(songAttr[4], -1, 6, "priority must be an integer in interval [0, 5]!");
        } catch (NumberFormatException e) {
            return;
        }

        if (!QUEUE.add(new Song(id, songAttr[1], songAttr[2], length, priority))) {
            Terminal.printError("a song with the same ID but different attributes is in the queue, "
                    + "either exactly the same song or a song with a different ID must be given!");
        }
    }

    private static void remove(String[] input) {
        if (!(input.length == 2)) {
            Terminal.printError("ID of the song to be removed must be given as following: remove <id>");
            return;
        }

        int id;
        try {
            id = intParser(input[1], 0, 100000, IDERROR);
        } catch (NumberFormatException e) {
            return;
        }

        int before = QUEUE.size();
        QUEUE.remove(id);

        int after = QUEUE.size();
        int diff = before - after;

        if (diff > 0) {
            Terminal.printLine("Removed " + diff + " songs");
        }
    }

    private static void play(String[] input) {
        if (input.length != 2) {
            Terminal.printError("length of the simulation must be given as following: play <length>");
            return;
        }

        int length;
        try {
            length = intParser(input[1], 0, Integer.MAX_VALUE, LENGTHERROR);
        } catch (NumberFormatException e) {
            return;
        }

        QUEUE.play(length);
    }

    private static void skip(String[] input) {
        if (input.length != 1) {
            Terminal.printError("skip command shall not be followed by anything!");
            return;
        }
        QUEUE.skip();
    }

    private static void peek(String[] input) {
        if (input.length != 1) {
            Terminal.printError("peek command shall not be followed by anything!");
            return;
        }

        Song head = QUEUE.peek();
        if (head != null) {
            Terminal.printLine(head.toString() + ":" + head.getTime());
        }
    }

    private static void list(String[] input) {
        if (input.length != 1) {
            Terminal.printError("list command shall not be followed by anything!");
            return;
        }

        List<Song> list = QUEUE.list();

        for (Song song : list) {
            Terminal.printLine(song.toString());
        }
    }

    private static void history(String[] input) {
        if (input.length != 1) {
            Terminal.printError("history command shall not be followed by anything!");
            return;
        }

        List<Song> finished = QUEUE.history();

        for (Song song : finished) {
            Terminal.printLine(song.toString());
        }
    }

    private static boolean quitValid(String[] input) {
        if (input.length != 1) {
            Terminal.printError("quit command shall not be followed by anything!");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

        boolean go = true;
        String[] input = Terminal.readLine().split(" ");

        if (input.length == 0) {
            Terminal.printError("no command has been given!");
            main(new String[0]);
            return;
        }

        switch (input[0]) {
            case "add":
                add(input);
                break;
            case "remove":
                remove(input);
                break;
            case "play":
                play(input);
                break;
            case "skip":
                skip(input);
                break;
            case "peek":
                peek(input);
                break;
            case "list":
                list(input);
                break;
            case "history":
                history(input);
                break;
            case "quit":
                go = !quitValid(input);
                break;
            default:
                Terminal.printError("the given command is not supported!");
        }

        //BİR DEFA DOĞRU ÇALIŞTI BİR DEFA ÇALIŞMADI KONTROL ET
        if (go) {
            main(new String[0]);
        }
    }
}
