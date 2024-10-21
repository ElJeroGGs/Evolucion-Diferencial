package modelo;

import controlador.control_principal;

public abstract class evolucion extends Thread{

    vector[] poblacion =  new vector[4];
    int id; //Este sirve para saber cual es el vector "principal"
    int generacion = 0;
    
    control_principal controlador;
    boolean finished = false;
    String texto;

    public synchronized boolean get_finished(){
        return this.finished;
    }

    public synchronized void set_finished(boolean finished){
        this.finished = finished;
    }

    public int get_id(){
        return this.id;
    }

    public void set_id(int id){
        this.id = id;
    }

    

    public void setCtrl(control_principal controlador){
        this.controlador = controlador;
    }

    public synchronized int getGeneracion(){
        return this.generacion;
    }

    public synchronized void setGeneracion(int generacion){
        this.generacion = generacion;
    }

    public evolucion(int id){
        this.id = id;

    }

    public evolucion(){
      
    }


    public void generar(){

        poblacion = new vector[4];
        //Genera de manera alatoria 4 vectores
        for (int i = 0; i < 4; i++){

            double x = Math.random() * (Constantes.limite_superior - Constantes.limite_inferior) + Constantes.limite_inferior ;
            double y = Math.random() * (Constantes.limite_superior - Constantes.limite_inferior) + Constantes.limite_inferior ;
            double z = Math.random() * (Constantes.limite_superior - Constantes.limite_inferior) + Constantes.limite_inferior;
            poblacion[i] = new vector(x, y, z, i+1);
         
        }
    }

    public synchronized vector[] getPoblacion(){
        return this.poblacion;
    }

    public synchronized void setPoblacion(vector[] poblacion){
        this.poblacion = poblacion;
    }

    public void insertar(vector v){
        //Inserta un vector en la poblacion
        int i = v.get_id();

        this.poblacion[i] = v;
    } 

    public synchronized String getTexto(){
        return this.texto;
    }

    public synchronized vector formulaW(){

        vector w;
        
        int[] ids = new int[3];
        int j = 0;
        for(int i = 0; i < 4; i++){
            
            if (poblacion[i].get_id() != id){
                ids[j] = poblacion[i].get_id();
                j++;
            }
            
        }
        //Escogemos al azar uno de los 3 ids
        int id = (int) (Math.random() * 3);
       

        int id1 = ids[id];

        //Hacemos un nuevo arreglo con los 2 indices restantes

        int[] ids2 = new int[2];
        int k = 0;
        for(int i = 0; i < 3; i++){
            if (ids[i] != id1){

                ids2[k] = ids[i];
                k++;
            }
        }

        //Escogemos al azar uno de los 2 ids restantes
        id = (int) (Math.random() * 2);
        int id2 = ids2[id];

        int id3 = -1;

        //Guardamos el id restante

        for(int i = 0; i < 2; i++){
            if (ids2[i] != id1 && ids2[i] != id2){
                id3 = ids2[i];
            }
        }
        
        id1 = id1 - 1;
        id2 = id2 - 1;
        id3 = id3 - 1;

        //Calculamos el vector w

        double wx = this.poblacion[id1].get_x() + Constantes.M * (this.poblacion[id2].get_x() - this.poblacion[id3].get_x()) ;
        double wy = this.poblacion[id1].get_y() + Constantes.M * (this.poblacion[id2].get_y() - this.poblacion[id3].get_y()) ;
        double wz = this.poblacion[id1].get_z() + Constantes.M * (this.poblacion[id2].get_z() - this.poblacion[id3].get_z());
        //Pintamos el  calculo de las componentes del vector w

        texto = "Objetivo V" + this.id + "\n";
        texto += String.format("W = V%d + %.2f * (V%d - V%d)\n", id1, Constantes.M, id2, id3);
        texto += String.format("WX = %.2f + %.2f * (%.2f - %.2f) = %.2f\n", this.poblacion[id1].get_x(), Constantes.M, this.poblacion[id2].get_x(), this.poblacion[id3].get_x(), wx);
        texto += String.format("WY = %.2f + %.2f * (%.2f - %.2f) = %.2f\n", this.poblacion[id1].get_y(), Constantes.M, this.poblacion[id2].get_y(), this.poblacion[id3].get_y(), wy);
        texto += String.format("WZ = %.2f + %.2f * (%.2f - %.2f) = %.2f\n", this.poblacion[id1].get_z(), Constantes.M, this.poblacion[id2].get_z(), this.poblacion[id3].get_z(), wz);
        texto += String.format("W = [%.2f, %.2f, %.2f]\n", wx, wy, wz);

        w = new vector(wx, wy, wz, this.id); // Initialize w with appropriate values
        return w;
    }

    public double Sesgo(vector w){

        double sesgo = 0;
        sesgo = w.valor_funcion() / this.poblacion[id].valor_funcion();
        return sesgo;
        
    }

    public abstract void reemplazar(vector w);


}
