import java.util.ArrayList;

public class Controller{
    private Catalog catalog;

    Controller(Catalog catalog){
        this.catalog = catalog;
    }
    //Comparten el listado, separe controller de catalog mayormente porque ando tratando de entender que signfica MVC, segun el conjunto de las classes seria el modelo, entonces los controles estan separados.
    public ArrayList<API> listAPI(){
        return catalog.getAPIs();
    }
    // otra cosa compartida
    public API findAPI(String id){
        return catalog.searchId(id);
    }
    //:O
    public void sort(){
        catalog.sortByLate();
    }

    //Agrega a una lista de strings las operaciones de la APi que le metas.
    public ArrayList<String> getOperations(API api){
        ArrayList<String> operations = new ArrayList<String>();
        if(api instanceof Chargeable){ //Si es instancia de la interface, a la fuerza necesita su metodo, entonces solo revisar uno por uno.
            operations.add("charge");
        }
        if(api instanceof Refundable){
            operations.add("refund");
        }
        if(api instanceof Costable){
            operations.add("calcCost");
        }
        if(api instanceof Queryable){
            operations.add("status");
        }
        if(api instanceof Documentable){
            operations.add("generar Doc");
        }
        if(api instanceof Auditable){
            operations.add("registerEvent");
            operations.add("getEvent");
        }
        return operations;
    }
    //Revisa si la operacion que se metio esta dentro de la API que se metio.
    public String checkOperation(API api, String operation){
        if(api == null){ //Revisa que existe
            return "API not found.";
        }
        else if(getOperations(api).contains(operation)){//Aqui es como lo compara, se crea el listado, dsps se revisa si la operacion esta dentro del Array.
            return "Operation available.";
        }
        else{ //La operacion no esta
            return "Operation unavailable. The purchase process can continue.";
        }
    }

    public double charge(API api, double amount){
        if(api instanceof Chargeable){ //isBlank no me puede decir que no existe algo, pero instanceof si...
            if(api.getConnected() == true){//Revisa que esta activa la API
                Chargeable service = (Chargeable) api; //casting para que no me saque error dsps y no tenga que hacer las mil ((((()))))
                return service.charge(amount);
            }
            else{
                return -1; //error, tecnicamente no, solo es que no esta activa
            }
        }
        else{
            return -1; //error, lo mismo pero nisiquiera esta la operacion.
        }
    }
    public double refund(API api, double amount){ //Copy paste de charge, solo que para el milagro de obtener dinero devuelta
        if(api instanceof Refundable){ //I heavily dislike this language.
            if(api.getConnected() == true){
                Refundable service = (Refundable) api;
                return service.refund(amount);
            }
            else{
                return -1;
            }
        }
        else{
            return -1;
        }
    }
    public double calcCost(API api, double distance){ //Copy paste de charge, solo con el hecho que no te quitaran dinero, solo miraran la cantidad que te podrian quitar
        if(api instanceof Costable){
            if(api.getConnected() == true){
                Costable service = (Costable) api;
                return service.calcCost(distance);
            }
            else{
                return -1;
            }
        }
        else{
            return -1;
        }
    }
    //Regresa el status de una API
    public String status(API api){
        if(api instanceof Queryable){
            Queryable service = (Queryable) api;
            return service.status(); //Si si es queryable, entonces manda el estado.
        }
        else{
            return "Couldn't check status."; //Si no tiene el metodo no esta salvando el estado.
        }
    }
    public boolean changeShippingStatus(API api){
        if(api instanceof Logistics){
            Logistics logistics = (Logistics) api;
            String status = logistics.getShippingStatus();
            if(status.equals("Pending")){
                logistics.setShippingStatus("In transit");
                return true;
            }
            else if(status.equals("In transit")){
                logistics.setShippingStatus("Delivered");
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    //Regresa el documento generado de una API
    public String genDoc(API api){
        if(api instanceof Documentable){
            Documentable service = (Documentable) api;
            return service.genDoc();
        }
        else{
            return "Couldn't generate document."; //Si no tiene el metodo no puede generar un documento.
        }
    }

    public boolean registerEvent(API api, String event){
        if(api instanceof Auditable){
            if(api.getConnected() == true && event != null && !event.isBlank()){ //Still hate this language. Revisa que este activa, con algo mas que...    en el listado
                Auditable service = (Auditable) api;
                service.registerEvent(event);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    public ArrayList<String> getEvent(API api){ //Trae el listado entero.
        if(api instanceof Auditable){
            Auditable service = (Auditable) api;
            return service.getEvent();
        }
        else{
            return null;
        }
    }
}
