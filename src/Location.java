public class Location {
    private double lat;
    private double longg;
    private boolean visited;

    public Location (double lat, double longg) {
        this.lat = lat;
        this.longg = longg;
    }

    public double getLat() {
        return lat;
    }

    public double getLongg() {
        return longg;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
