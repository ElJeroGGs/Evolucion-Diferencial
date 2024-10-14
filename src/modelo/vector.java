package modelo;

public class vector {

    double x;
    double y;
    double z;
    int id;

    public vector(double x, double y, double z, int id){
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }
    

    public double get_x() {
        return x;
    }   

    public double get_y(){
        return y;
    }

    public double get_z(){
        return z;
    }

    public int get_id(){
        return id;
    }

    public double valor_funcion(){

        return Math.round((Math.pow(x,2) + (y / z)) * 100.0) / 100.0;
    }

}
