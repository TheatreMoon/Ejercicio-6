import java.util.ArrayList;

public class Catalog{
    private ArrayList<API> integrations;

    Catalog(){
        this.integrations = new ArrayList<API>();
    }

    public API searchId(String id){
        for(API api: integrations){ //Por cada apo en la lista, revisar si su id es igual al id que metiste, y decirte si existe o no dicho api
            if(api.getId().equals(id)){
                return api;
            }
        }
        return null;
    }

    public void addAPI(API api){
        if(api != null){ // Para revisar que haya algo
            if(api.getId() != null && !api.getId().isBlank()){ // Dice null y isBlank, porque al parecer isBlank no lee un dato vacio(null) como vacio...
                if(searchId(api.getId()) == null){ //Porque el metodo da null si no hay nada, pues es seguro agregarlo solo si es null
                    this.integrations.add(api);
                }
            }
        }
    }
    //It removes it
    public void removeAPI(API api){
        this.integrations.remove(api);
    }
    /*Esta cosa es medio rara, pero por cada miembro de la lista, revisa, cada miembro de la lista despues de si mismo, los compara, si el resultado es positivo, salva el primer elemento que seria el mas grande
    lo remplaza por el segundo, remplaza el segundo, por lo que guardamos, y hace eso denuevo con cada elemento.*/
    public void sortByLate(){
        for(int i = 0; i < integrations.size() - 1; i++){ //El -1 en size es porque no queremos comparar el ultimo elemento con el nada que le sigue.
            for(int j = i + 1; j < integrations.size(); j++){ //
                if(integrations.get(i).compareTo(integrations.get(j)) > 0){
                    API move = integrations.get(i);
                    integrations.set(i, integrations.get(j));
                    integrations.set(j, move);
                }
            }
        }
    }
    //get 
    public ArrayList<API> getAPIs() {
        return integrations;
    }
}
