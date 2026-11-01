import java.util.ArrayList;

public class Wallet extends API implements Chargeable, Queryable, Auditable{
    private double balance;
    private ArrayList<String> audit;

    Wallet(String id, String name, boolean connected, double latency, double balance){
        super(id, name, connected, latency);
        this.balance = balance;
        this.audit = new ArrayList<String>();
    }

    @Override
    public void registerEvent(String event){ //registra un evento
        if(event != null && !event.isBlank()){
            this.audit.add(event);
        }
    }
    @Override
    public ArrayList<String> getEvent(){ //The glorified getter.
        return audit;
    }

    @Override
    public double charge(double amount){
        if(this.connected == true){
            if(amount > 0){
                amount = Math.round(amount * 100.0) / 100.0;
                if(amount > 0 && amount <= this.balance){ //Revisa que se puede cobrar el dinero.
                    this.balance = Math.round((this.balance - amount) * 100.0) / 100.0;
                    registerEvent("Charged Q" + amount + ". Balance: Q" + this.balance); //Registra la compra por si hay fraude.
                    return amount;
                }
                else{
                    registerEvent("Charge rejected: Q" + amount);//registra un fallo-
                    return -1;
                }
            }
            else{
                registerEvent("Charge rejected: Q" + amount); //-en ambos casos, que no se tenga dinero, o que no se pida gastar algo
                return -1;
            }
        }
        else{
            return -1;
        }
    }
    @Override
    public String status(){
        if(this.connected == true){
            return "Connected. Balance: Q" + this.balance; //Otro uso raro de status, pero era una buena forma que meter mas interfaces.
        }
        else{
            return "Disconnected.";
        }
    }
    //Getters y su version con S.
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        if(balance >= 0){
            this.balance = Math.round(balance * 100.0) / 100.0;
        }
    }
    public ArrayList<String> getAudit() {
        return audit;
    }
    public void setAudit(ArrayList<String> audit) {
        this.audit = audit;
    }
}
