package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

public class Planet implements Comparable<Planet> {
    private final int index;
    private final TreeSet<Integer> portalEnds;
    private boolean searched;

    public Planet(int index, boolean portalEndsExist) {
        this.index = index;
        this.searched = false;

        if (portalEndsExist) {
            this.portalEnds = new TreeSet<>();
        } else {
            this.portalEnds = null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Planet planet = (Planet) other;
        return this.getIndex() == planet.getIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public int compareTo(Planet other) {
        int compare = Integer.compare(this.getPortalEndCount(), other.getPortalEndCount());

        if (compare == 0) {
            compare = Integer.compare(this.getIndex(), other.getIndex());
        }
        return compare;
    }

    public int getIndex() {
        return index;
    }

    public Collection<Integer> getPortalEnds() {
        if (portalEnds == null) {
            return null;
        } else {
            return new ArrayList<>(portalEnds);
        }
    }

    public int getPortalEndCount() {
        if (portalEnds == null) {
            return 0;
        } else {
            return this.portalEnds.size();
        }
    }

    public boolean isSearched() {
        return searched;
    }

    public void setSearched() {
        this.searched = true;
    }

    public boolean addPortalExit(int planetIndex) {
        if (portalEnds == null) {
            return false;
        } else {
            return portalEnds.add(planetIndex);
        }
    }
}
