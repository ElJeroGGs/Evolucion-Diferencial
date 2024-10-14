package modelo;

public class evolucion_min extends evolucion{


    public evolucion_min(int i){
        this.id = i;
    }

    public  synchronized void reemplazar(vector w) {
        this.texto = "Peso de W=" + w.valor_funcion()+"\n";
        this.texto += "Peso de V" + (this.id) + "=" + this.poblacion[this.id-1].valor_funcion()+"\n";
        if(w.valor_funcion() < this.poblacion[this.id-1].valor_funcion()){
            
            this.texto += "W es mejor que V" + (this.id) + " se reemplaza";
            this.poblacion[this.id-1] = w;
        }
        else{
            this.texto += "W no es mejor que V" + (this.id) + " no se reemplaza"+ "\n";
        }
        controlador.pintaMarcoMin(texto);
    }

    public void run() {
        while (!this.finished && generacion < controlador.get_generaciones()) {
            try {
                // Esperar hasta que el controlador no esté ocupado
                controlador.waitForOcupado();
                controlador.setOcupado(true);
    
                // Calcular el vector w
                vector w = formulaW();
                Thread.sleep(100);
    
                // Pintar el marco con el texto actual
                controlador.pintaMarcoMin(this.getTexto());
    
                // Reemplazar el vector si es mejor
                reemplazar(w);
    
                // Liberar el estado de ocupado
                controlador.setOcupado(false);
    
                // Actualizar los vectores máximos en el controlador
                controlador.setVectores_min(this.poblacion);
    
                // Marcar como terminado
                this.finished = true;
    
                // Cambiar a la siguiente generación
                //controlador.cambiar_generacion("min");
    
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
