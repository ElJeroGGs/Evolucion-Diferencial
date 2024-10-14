package modelo;

public class evolucion_max  extends evolucion{



    public evolucion_max(int i) {
       this.id = i;
    }

    @Override
    public  synchronized void reemplazar(vector w) {
        this.texto = "Peso de W=" + w.valor_funcion()+"\n";
        this.texto += "Peso de V" + (this.id) + "=" + this.poblacion[this.id-1].valor_funcion()+"\n";
        if(w.valor_funcion() > this.poblacion[this.id-1].valor_funcion()){
            
            this.texto += "W es mejor que V" + (this.id) + " se reemplaza";
            this.poblacion[this.id-1] = w;
        }
        else{
            this.texto += "W no es mejor que V" + (this.id) + " no se reemplaza"+ "\n";
        }
        controlador.pintaMarcoMax(texto);
    }

    public void hace(int generacion, vector[] vectores) {

        this.generacion = generacion;
              
            
                        // Calcular el vector w
                        vector w = formulaW();
                        
            
                        // Pintar el marco con el texto actual
                        controlador.pintaMarcoMax(this.getTexto());
            
                        // Reemplazar el vector si es mejor
                        reemplazar(w);
            
                        // Liberar el estado de ocupado
                        controlador.setOcupado(false);
            
                        // Actualizar los vectores máximos en el controlador
                        controlador.setVectores_max(this.poblacion);
            
            
                        // Cambiar a la siguiente generación
                        //controlador.cambiar_generacion("max");
            
                    
                
            }

}
