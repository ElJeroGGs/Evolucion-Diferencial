package modelo;

public class evolucion_min extends evolucion{


    public evolucion_min(int i){
        this.id = i;
    }

    public  synchronized void reemplazar(vector w) {
        this.texto = "Peso de W=" + w.valor_funcion()+"\n";
        this.texto += "Peso de V" + (this.id) + "=" + this.poblacion[this.id-1].valor_funcion()+"\n";
        if(w.valor_funcion() < this.poblacion[this.id-1].valor_funcion()){
            
            this.texto += "W es mejor que V" + (this.id) + " se reemplaza"+ "\n";
            this.poblacion[this.id-1] = w;
        }
        else{
            this.texto += "W no es mejor que V" + (this.id) + " no se reemplaza"+ "\n";
        }
        controlador.pintaMarcoMin(texto);
    }

    public void hace(int generacion, vector[] vectores) {

        this.generacion = generacion;
       
                
    
                // Calcular el vector w
                vector w = formulaW();
              
    
                // Pintar el marco con el texto actual
                controlador.pintaMarcoMin(this.getTexto());
    
                // Reemplazar el vector si es mejor
                reemplazar(w);
    
              
    
                // Actualizar los vectores máximos en el controlador
                controlador.setVectores_min(this.poblacion);
    
                
    
                // Cambiar a la siguiente generación
                //controlador.cambiar_generacion("min");
    
           
    }

}
