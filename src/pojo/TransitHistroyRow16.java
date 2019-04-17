package pojo;

public class TransitHistroyRow16 {
    private String date;
    private String route;
    private String transport;
    private double price;

    public TransitHistroyRow16(String date, String route, String transport, double price) {
        this.date = date;
        this.route = route;
        this.transport = transport;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getRoute() {
        return route;
    }

    public String getTransport() {
        return transport;
    }

    public double getPrice() {
        return price;
    }
}