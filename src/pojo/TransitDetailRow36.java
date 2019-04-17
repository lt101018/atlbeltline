package pojo;

public class TransitDetailRow36 {

    private String route;
    private String transportType;
    private double price;
    private int numConnectedSites;

    public TransitDetailRow36(String route, String transportType, double price, int numConnectedSites) {
        this.route = route;
        this.transportType = transportType;
        this.price = price;
        this.numConnectedSites = numConnectedSites;
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
}
