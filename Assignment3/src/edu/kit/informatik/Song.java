package edu.kit.informatik;

/**
 * Represents a song. Contains methods to manipulate it and to retrieve information about it.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Song implements Comparable<Song> {

    private final int id;
    private final String artist;
    private final String title;
    private final int length;
    private int time;
    private final int priority;
    private boolean inQueue;

    /**
     * Constructs a Song object with the given parameters.
     *
     * @param id       ID of the song
     * @param artist   interpreter of the song
     * @param title    title of the song
     * @param length   length of the song in seconds
     * @param priority priority of the song in the queue
     */
    public Song(int id, String artist, String title, int length, int priority) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.time = length;
        this.priority = priority;
        this.inQueue = false;
    }

    /**
     * To compare this song to the other.
     *
     * @param other the other song
     * @return a negative integer if this song is prior to the other song, if this song is partially simulated
     * or if the priorities of the songs are equal, this song is in the queue and the other is not,
     * a positive integer if other song is prior to this song, if the other song is partially simulated
     * or if the priorities of the songs are equal, other song is in the queue and this song is not
     */
    @Override
    public int compareTo(Song other) {
        int comp = Integer.compare(this.priority, other.priority);

        if (this.onAir()) {
            comp = -1;
        } else if (other.onAir()) {
            comp = 1;
        } else if (comp == 0 && this.inQueue) {
            comp = -1;
        } else if (comp == 0 && other.inQueue) {
            comp = 1;
        }
        return comp;
    }

    /**
     * To get a hashcode of the song.
     *
     * @return a hashcode of the song
     */
    @Override
    public int hashCode() {
        int result = 0;

        result += this.id;
        result += this.artist.hashCode();
        result += this.title.hashCode();
        result += this.length;
        result += this.priority;

        return result;
    }

    /**
     * Checks if this object is equal to the other.
     *
     * @param obj the other object
     * @return true if ID, artist, title, length and priority of the songs are equal,
     * false if one of those attributes are not equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !obj.getClass().equals(this.getClass())) {
            return false;
        }

        Song other = (Song) obj;
        return this.id == other.id && this.artist.equals(other.artist) && this.title.equals(other.title)
                && this.length == other.length && this.priority == other.priority;
    }

    /**
     * To get a string representation of the song. It has the form <id>:<artist>:<title>:<length>.
     *
     * @return the string representation of the song
     */
    @Override
    public String toString() {
        String idString = id + ":";
        while (idString.length() <= 5) {
            idString = "0" + idString;
        }

        return idString + this.artist + ":" + this.title + ":" + this.length;
    }

    /**
     * Checks if this song is partially simulated.
     *
     * @return true if the song is partially simulated
     */
    public boolean onAir() {
        return this.time < this.length;
    }

    /**
     * To get the id of the song.
     *
     * @return ID of the song
     */
    public int getId() {
        return id;
    }

    /**
     * To get the name of the interpreter of the song.
     *
     * @return the name of the interpreter
     */
    public String getArtist() {
        return artist;
    }

    /**
     * To get the title of the song.
     *
     * @return the title of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * To get the length of the song in seconds.
     *
     * @return the length of the song in seconds
     */
    public int getLength() {
        return length;
    }

    /**
     * To get the remaining simulation time of the song in seconds.
     *
     * @return the remaining simulation time of the song in seconds
     */
    public int getTime() {
        return time;
    }

    /**
     * To get the priority of the song in range [0, 5]
     *
     * @return the priority of the song
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Checks if the song is in the queue.
     *
     * @return true if the song is in the queue, false otherwise
     */
    public boolean isInQueue() {
        return inQueue;
    }

    /**
     * Simulates the song for the given time in seconds.
     *
     * @param playTime the simulation time in seconds
     * @return true if the song is completely simulated within the given time
     */
    public int play(int playTime) {
        int remaining = playTime - this.time;
        this.time -= playTime;

        if (remaining < 0) {
            remaining = 0;
        } else {
            this.time = 0;
        }

        return remaining;
    }

    /**
     * To set the inQueue attribute of the song.
     *
     * @param inQueue new value of the inQueue attribute of the song
     */
    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    /**
     * To set the simulation time of the song so that as if it has never been simulated.
     */
    public void setTimeFull() {
        this.time = this.length;
    }
}
