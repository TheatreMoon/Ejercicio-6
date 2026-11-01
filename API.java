public abstract class API implements Comparable<API>{
    protected String id;
    protected String name;
    protected boolean connected;
    protected double latency;

    API(String id, String name, boolean connected, double latency){
        this.id = id;
        this.name = name;
        this.connected = connected;
        this.latency = latency;
    }
    // Aqui el metodo esta porque, se pidio en las instrucciones, y compara "Latency", para ordernar la lista mas facil. Como se dijo en clase, -1 es menor que, 1 mayor que, 0 igual que.
    @Override
    public int compareTo(API other){
        if(this.latency < other.getLatency()){
            return -1; 
        }
        else if(this.latency > other.getLatency()){
            return 1;
        }
        else{
            return this.id.compareTo(other.getId());
        }
    }
    // Las instrucciones dicen que todo metodo necesita comentario, No voy a explicar cada setter y getter, eso son.
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean getConnected() {
        return connected;
    }
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    public double getLatency() {
        return latency;
    }
    public void setLatency(double latency) {
        this.latency = latency;
    }
}
