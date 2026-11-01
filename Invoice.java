import java.util.ArrayList;

public class Invoice extends API implements Documentable, Auditable{ //Es API, genera documentos y puede tener fraude.
    private double taxRate;
    private ArrayList<String> audit;
    private int documents; //Agregue un contador de documentos para llenar mas el documento generado. 

    Invoice(String id, String name, boolean connected, double latency, double taxRate){
        super(id, name, connected, latency);
        this.taxRate = taxRate;
        this.audit = new ArrayList<String>();
        this.documents = 0;
    }

    @Override //Es obligatorio usar las funciones de las interfaces-
    public void registerEvent(String event){
        if(event != null && !event.isBlank()){ //I'll stop with this joke
            this.audit.add(event); //Agrega un evento al listado.
        }
    }
    @Override // o no corre-
    public ArrayList<String> getEvent(){ //Glorified getter.
        return audit; 
    }

    @Override // la cosa.
    public String genDoc(){
        if(this.connected == true){
            this.documents++; //Incrementa la cuenta para que los documentos esten en orden, lo hace antes para que el doc sea id-1.
            registerEvent("Document generated: " + this.id + "-" + this.documents); //Registra el evento con lo minimo
            return "Simulated invoice: " + this.id + "-" + this.documents + " | Tax rate: " + this.taxRate; //fluffed up
        }
        else{
            return "API disconnected. Document couldn't generate."; // Si no esta conectado el API no se puede generar doc.
        }
    }
    
    //Again, not doing that. Setters y getters
    public double getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
    public ArrayList<String> getAudit() {
        return audit;
    }
    public void setAudit(ArrayList<String> audit) {
        this.audit = audit;
    }
}
