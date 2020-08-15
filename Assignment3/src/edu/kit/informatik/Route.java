package edu.kit.informatik;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

public class Route implements Comparable<Route> {

    private final LinkedList<Planet> route;

    public Route(Collection<Planet> route) {
        this.route = new LinkedList<>(route);
    }

    public Route() {
        this.route = new LinkedList<>();
    }

    public Collection<Planet> getRoute() {
        return route;
    }

    public int getNotSearchedCount() {
        int notSearched = 0;

        for (Planet planet : route) {
            if (!planet.isSearched()) {
                notSearched++;
            }
        }
        return notSearched;
    }

    @Override
    public int compareTo(Route other) {
        int compare = (-1) * Integer.compare(this.getNotSearchedCount(), other.getNotSearchedCount());

        if (compare == 0) {
            compare = (new Random().nextInt(2) == 0 ? -1 : 1);
        }
        return compare;
    }

    public void add(Planet planet) {
        route.add(planet);
    }

    public Planet getLast() {
        return route.getLast();
    }

    public void refreshPlanet(Planet planet) {
        route.set(route.indexOf(planet), planet);
    }
}
