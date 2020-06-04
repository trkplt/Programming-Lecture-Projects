package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SongQueue {
    private final List<Song> queue;
    private final List<Song> finished;

    public SongQueue() {
        queue = new ArrayList<>();
        finished = new ArrayList<>();
    }

    public int size() {
        return queue.size();
    }

    private boolean falseCopyExists(Song song) {
        boolean exists = false;

        for (Song songInList : queue) {
            if (!song.equals(songInList) && song.getId() == songInList.getId()) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean add(Song song) {
        if (falseCopyExists(song)) {
            return false;
        } else {
            int index = 0;
            for (Song songInQueue : queue) {
                if (song.compareTo(songInQueue) > 0) {
                    index++;
                } else {
                    break;
                }
            }
            queue.add(index, song);
            song.setInQueue(true);
            return true;
        }
    }

    public void remove(int id) {
        Iterator<Song> iterator = queue.iterator();

        while (iterator.hasNext()) {
            Song song = iterator.next();

            if (song.getId() == id) {
                song.setInQueue(false);
                song.setTimeFull();
                iterator.remove();
            }
        }
    }

    private void finishCeremony() {
        Song song = queue.get(0);
        finished.add(song);
        song.setInQueue(false);
        song.setTimeFull();
        queue.remove(0);
    }

    public boolean play(int length) {
        Song song = queue.get(0);
        int remaining;

        if (song != null) {
            remaining = song.play(length);
        } else {
            return true;
        }

        if (remaining > 0) {
            finishCeremony();
            return play(remaining);
        } else if (song.getTime() == 0) {
            finishCeremony();
            return true;
        }
        return false;
    }

    public void skip() {
        queue.remove(0);
    }

    public Song peek() {
        return queue.get(0);
    }

    public List<Song> list() {
        return queue;
    }

    public List<Song> history() {
        return finished;
    }
}
