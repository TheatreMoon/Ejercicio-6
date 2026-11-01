import java.util.ArrayList;

public interface Auditable{
    void registerEvent(String event); //Mi version de lo que pide el pdf, registra eventos, no tiene nada porque es interface.
    ArrayList<String> getEvent(); // Y pues, esto era para lo de, "obtener eventos", sacar el listado entero de eventos.
}
