public class Payment extends API implements Chargeable, Refundable, Queryable{
    private double fee;
    private double charged; // Monto cobrado que todavia se puede devolver, sin la comision.

    Payment(String id, String name, boolean connected, double latency, double fee){
        super(id, name, connected, latency);
        this.fee = fee;
        this.charged = 0;
    }

    @Override
    public double charge(double amount){
        if(this.connected == true){
            if(amount > 0 && fee >= 0){ //Ambos la cantidad y costo tienen que ser positivos. 
                amount = Math.round(amount * 100.0) / 100.0; //Redondea a 2 decimales. Lo espamie porque o si no la cosa se imagina dinero, por alguna razon.
                double total = amount + (amount * fee);
                double newCharged = charged + amount;
                if(amount > 0){
                    this.charged = Math.round(newCharged * 100.0) / 100.0; 
                    return Math.round(total * 100.0) / 100.0;
                }
                else{
                    return -1; // si la cantidad es negativa o cero
                }
            }
            else{
                return -1; // si la cantidad entrada o la tarifa son 0 o negativos
            }
        }
        else{
            return -1; // si la cosa esta apagada
        }
    }
    @Override
    public double refund(double amount){
        if(this.connected == true && amount > 0){
            amount = Math.round(amount * 100.0) / 100.0;
            if(amount > 0 && amount <= this.charged){ //Si la cantidad de dinero que se quiere devuelta es menor o igual que la cantidad que se a cobrado.
                this.charged = Math.round((this.charged - amount) * 100.0) / 100.0;
                return amount;
            }
            else{
                return -1; // si la cantidad a devolver es negativa, cero o mayor que la cantidad cobrada
            }
        }
        else{
            return -1; // si la cosa no esta conectada
        }
    }
    @Override
    public String status(){
        if(this.connected == true){
            return "Connected. Amount available for refund: Q" + this.charged; //Pelie mucho con status, entonces aqui fue usado para dar el estado del dinero. No como en el analysis, pero siento que sirvio para las dos.
        }
        else{
            return "Disconnected.";
        }
    }
    //G/Setter
    public double getFee() {
        return fee;
    }
    public void setFee(double fee) {
        this.fee = fee;
    }
    public double getCharged() {
        return charged;
    }
}
