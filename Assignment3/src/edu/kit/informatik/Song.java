package edu.kit.informatik;

public class Song implements Comparable<Song> {

    private final int id;
    private final String artist;
    private final String title;
    private final int length;
    private int time;
    private final int priority;
    private boolean inQueue;

    public Song(int id, String artist, String title, int length, int priority) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.time = length;
        this.priority = priority;
        this.inQueue = false;
    }

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

    @Override
    public String toString() {
        String idString = id + ":";
        while (idString.length() <= 5) {
            idString = "0" + idString;
        }

        return idString + this.artist + ":" + this.title + ":" + this.length;
    }

    public boolean onAir() {
        return this.time < this.length;
    }

    public int getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public int getTime() {
        return time;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isInQueue() {
        return inQueue;
    }

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

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    public void setTimeFull() {
        this.time = this.length;
    }
}
