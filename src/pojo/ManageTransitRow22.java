package pojo;

public class ManageTransitRow22 {
    private String route;
    private String transportType;
    private double price;
    private int numConnectedSites;
    private int numTransitLogged;

    public ManageTransitRow22(String route, String transportType, double price, int numConnectedSites, int numTransitLogged) {
        this.route = route;
        this.transportType = transportType;
        this.price = price;
        this.numConnectedSites = numConnectedSites;
        this.numTransitLogged = numTransitLogged;
    }

    public String getRoute() {
        return route;
    }

    public String getTransportType() {
        return transportType;
    }

    public double getPrice() {
        return price;
    }

    public int getNumConnectedSites() {
        return numConnectedSites;
    }

    public int getNumTransitLogged() {
        return numTransitLogged;
    }
}
