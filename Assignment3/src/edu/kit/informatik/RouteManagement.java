package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public class RouteManagement {

    private static boolean[] planetsIncluded;
    private final TreeSet<Planet> planets;
    private final Planet[] planetsIndexed;
    private TreeSet<Route> routes;

    public RouteManagement(int[][] input) {
        routes = new TreeSet<>();
        planets = new TreeSet<>();
        planetsIndexed = new Planet[input[0][0]];
        planetsIncluded = new boolean[input[0][0]];

        for (int i = 1; i < input.length; i++) {
            Planet planet = new Planet(i - 1, input[i][0] != 0);

            for (int j = 1; j < input[i].length; j++) {
                planet.addPortalExit(input[i][j]);
            }
            planets.add(planet);
            planetsIndexed[i - 1] = planet;
        }

        for (Planet planet : planets) {
            if (planet.getPortalEndCount() < 2 && !planetsIncluded[planet.getIndex()]) {
                Route route = new Route();
                route.add(planet);
                this.routes.addAll(routeFinder(planetsIndexed, route));
            }
        }
    }

    private static Collection<Route> routeFinder(Planet[] planets, Route start) {
        List<Route> routes = new ArrayList<>();
        planetsIncluded[start.getLast().getIndex()] = true;

        if (start.getLast().getPortalEnds() == null) {
            routes.add(start);
            return routes;
        }

        for (int planetIndex : start.getLast().getPortalEnds()) {
            Planet planet = planets[planetIndex];
            Route route = new Route(start.getRoute());
            route.add(planet);
            planetsIncluded[planetIndex] = true;

            if (planet.getPortalEndCount() == 0) {
                routes.add(route);
            } else {
                routes.addAll(routeFinder(planets, route));
            }
        }
        return routes;
    }

    private boolean notFinished() {
        for (Planet planet : planetsIndexed) {
            if (!planet.isSearched()) {
                return true;
            }
        }
        return false;
    }

    private void refreshRoutes() {
        for (Route route : routes) {
            for (Planet planet : route.getRoute()) {
                route.refreshPlanet(planetsIndexed[planet.getIndex()]);
            }
        }
    }

    public int getMinimumDroidCount() {
        int droidCounter = 0;

        while (droidCounter <= planetsIndexed.length) {
            if (notFinished()) {
                TreeSet<Route> newRoutes = new TreeSet<>();
                routes.forEach((route) -> {
                    newRoutes.add(route);
                });
                routes = newRoutes;
                Route route = routes.pollFirst();

                if (route == null) {
                    return droidCounter;
                }

                for (Planet planet : route.getRoute()) {
                    planetsIndexed[planet.getIndex()].setSearched();
                }

                //refreshRoutes();
                droidCounter++;
            } else {
                break;
            }
        }
        return droidCounter;
    }
}
