package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a queue for songs. Contains methods to manipulate it and
 * to retrieve information about it.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class SongQueue {
    private final List<Song> queue;
    private final List<Song> finished;

    /**
     * Constructs a SongQueue object with no songs in it.
     */
    public SongQueue() {
        queue = new ArrayList<>();
        finished = new ArrayList<>();
    }

    /**
     * To get the size of the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return queue.size();
    }

    //checks if a song with the same id but different attributes exists in the queue
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

    /**
     * To add the song to this queue.
     *
     * @param song the song to be added
     * @return true if the operation successful, false if a song with the same id
     * but different attributes already exist in the queue
     */
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

    /**
     * To remove the song with the given ID.
     *
     * @param id the ID of the song to be removed
     */
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

    //helper method to take care of operations in case of a complete simulation of the song at the head of the queue
    private void finishCeremony() {
        Song song = queue.get(0);
        finished.add(song);
        song.setInQueue(false);
        song.setTimeFull();
        queue.remove(0);
    }

    /**
     * To simulate the queue.
     *
     * @param length the length of the simulation in seconds
     * @return true if the queue completely simulated within the given length
     */
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

    /**
     * To skip the next song in the queue.
     */
    public void skip() {
        queue.remove(0);
    }

    /**
     * To get the song which is the head of this queue without removing it from the queue.
     *
     * @return the song which is the head of the queue
     */
    public Song peek() {
        return queue.get(0);
    }

    /**
     * To get a list of the songs in this queue.
     *
     * @return the list of the songs in this queue
     */
    public List<Song> list() {
        return queue;
    }

    /**
     * To get the list of the songs which are completely simulated.
     *
     * @return the list of the songs which are completely simulated
     */
    public List<Song> history() {
        return finished;
    }
}
