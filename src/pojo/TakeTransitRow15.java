package pojo;

public class TakeTransitRow15 {
    private String route;
    private String type;
    private double price;
    private int numSite;

    public TakeTransitRow15(String route, String type, double price, int numSite) {
        this.route = route;
        this.type = type;
        this.price = price;
        this.numSite = numSite;
    }

    public String getRoute() {
        return route;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getNumSite() {
        return numSite;
    }
}