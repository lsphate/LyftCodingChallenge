/*
 * Calculate the detour distance between two different rides. Given four latitude
 * longitude pairs, where driver one is traveling from point A to point B and driver
 * two is traveling from point C to point D, write a function (in your language of choice)
 * to calculate the shorter of the detour distances the drivers would need to take to
 * pick-up and drop-off the other driver.
 *
 * Idea:    1. Get all permutations of all 4 stops.
 *          2. Check the validation of permutations.
 *          3. Calculate the minimum of valid permutations.
 *
 * Assuming: I assume the driver can change in the mid-way,
 *           i.e. A -> C -> B -> D,
 *           Driver one gets off at C and hands the car to Driver two.
 */
import java.util.*;

public class detour {
    static class point {
        private char tag;
        private double x, y;

        public point(char t, double a, double b) {
            this.tag = t;
            this.x = a;
            this.y = b;
        }

        void setX(double a) {
            this.x = a;
        }

        void setY(double b) {
            this.y = b;
        }

        double getX(){
            return this.x;
        }

        double getY(){
            return this.y;
        }

        @Override
        public String toString(){
            return Character.toString(this.tag);
        }
    }

    static List<point> stops = new ArrayList<>();
    static Map<point, point> trips = new HashMap<>();
    static List<List<point>> routes = new ArrayList<>();

    /* Following two functions are used to generate permutations(routes) */
    static void generateRoute(){
        backtracking(stops, new ArrayList<point>());
    }
    static void backtracking(List<point> rests, List<point> current) {
        if (rests.isEmpty()) {
            routes.add(current);
            return;
        }

        for (int i = 0; i < rests.size(); i++) {
            ArrayList<point> next = new ArrayList<>(current);
            next.add(rests.get(i));
            ArrayList<point> remaining = new ArrayList<>(rests);
            remaining.remove(i);
            backtracking(remaining, next);
        }
    }

    /* Check the route is valid or not */
    static boolean checkVaild(List<point> l) {
        Stack<point> stack = new Stack<>();
        for (point s : l) {
            if (trips.containsKey(s)) {
                stack.push(s);
            } else if (trips.containsValue(s)) {
                if (stack.size() >= trips.size())
                    break;
                else
                    return false;
            }
        }
        return true;
    }

    /* In order to reduce the complexity, use Euclidean distance */
    static double calculateDistance(point a, point b) {
        return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }

    /* Use for check result */
    static void printRoute(){
        for (int i = 0; i < routes.size(); i++) {
            if (checkVaild(routes.get(i))) {
                for (int j = 0; j < routes.get(i).size(); j++) {
                    System.out.print(routes.get(i).get(j).toString());
                }
                System.out.println("");
            }
        }
    }

    /* Calculate the shortest route */
    static void shortestDetour() {
        double currDist = 0;
        double minDist = Double.MAX_VALUE;
        List<point> minRoute = new ArrayList<>();
        for (List<point> l : routes) {
            currDist = 0;
            if (checkVaild(l)) {
                for (int i = 0; i < l.size() - 1; i++) {
                    currDist += calculateDistance(l.get(i), l.get(i + 1));
                }
                if (currDist < minDist) {
                    minDist = currDist;
                    minRoute = l;
                }
            }
        }
        System.out.print("Shortest detour is: ");
        for (int j = 0; j < minRoute.size(); j++) {
            System.out.print(minRoute.get(j).toString());
        }
        System.out.println(" in the distance of: " + minDist);

    }

    public static void main(String[] args) {
        point A = new point('A', 25, 75);
        point B = new point('B', 35, 60);
        point C = new point('C', 25, 100);
        point D = new point('D', 35, 100);
        stops.add(A);
        stops.add(B);
        stops.add(C);
        stops.add(D);
        trips.put(A, B);
        trips.put(C, D);

        generateRoute();
        //printRoute();
        shortestDetour();

    }

}
