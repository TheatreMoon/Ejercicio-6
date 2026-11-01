public class Logistics extends API implements Costable, Queryable, Documentable{
    private double costPerKm;
    private int documents; //Mantiene cuenta de los docs
    private String shippingStatus; //Guarda el estado, que avanzo con un boton en main. Velocidad

    Logistics(String id, String name, boolean connected, double latency, double costPerKm){
        super(id, name, connected, latency);
        this.costPerKm = costPerKm;
        this.documents = 0;
        this.shippingStatus = "Pending";
    }

    @Override
    public double calcCost(double distance){
        if(this.connected == true && distance > 0){ //Revisa que la distancia sea positiva y que la API este.
            double cost = distance * this.costPerKm; //Distancia por costo. Velocidad (Km/Q)
            return cost;
        }
        else{
            return -1;
        }
    }
    @Override
    public String status(){ //Regresa el estado de el pedido asosiado con la API
        if(this.connected == true){
            return shippingStatus;
        }
        else{
            return "API disconnected. Cannot check shipping status.";
        }
    }


    @Override
    public String genDoc(){ //Genera un documento
        if(this.connected == true){
            this.documents++;
            return "Simulated shipping guide: " + this.id + "-" + this.documents + " | Provider: " + this.name;
        }
        else{
            return "API disconnected. Document not generated.";
        }
    }
    //G/Setter
    public double getCostPerKm() {
        return costPerKm;
    }
    public void setCostPerKm(double costPerKm) {
        this.costPerKm = costPerKm;
    }
    public void setShippingStatus(String shippingStatus){
        this.shippingStatus = shippingStatus;
    }
    public String getShippingStatus(){
        return shippingStatus;
    }
}
