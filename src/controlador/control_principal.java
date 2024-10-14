package controlador;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import modelo.evolucion;
import modelo.evolucion_max;
import modelo.evolucion_min;
import modelo.vector;
import vista.InterfazPrincipal;

public class control_principal extends Thread {

    InterfazPrincipal interfaz;
    vector[] vectores_max = new vector[4];
    vector[] vectores_min = new vector[4];
    evolucion_max evolucion1;
    evolucion_max evolucion2;
    evolucion_max evolucion3;
    evolucion_max evolucion4;
    evolucion_min evolucion5;
    evolucion_min evolucion6;
    evolucion_min evolucion7;
    evolucion_min evolucion8;
    boolean Max_ocupado = false;
    boolean Min_ocupado = false;
    boolean ocupado = false;
    int generaciones;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void setOcupado(boolean ocupado) {
        lock.lock();
        try {
            this.ocupado = ocupado;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean getOcupado() {
        lock.lock();
        try {
            return ocupado;
        } finally {
            lock.unlock();
        }
    }

    public void waitForOcupado() throws InterruptedException {
        lock.lock();
        try {
            while (ocupado) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void notifyAllOcupado() {
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void setVectores_max(vector[] vectores_max) {
        this.vectores_max = vectores_max;
    }

    public void setVectores_min(vector[] vectores_min) {
        this.vectores_min = vectores_min;
    }

    public synchronized void set_ocupado(boolean valor) {
        this.ocupado = valor;
    }

    public synchronized boolean get_ocupado() {
        return this.ocupado;
    }

    public control_principal() {
        evolucion1 = new evolucion_max(1);
        evolucion2 = new evolucion_max(2);
        evolucion3 = new evolucion_max(3);
        evolucion4 = new evolucion_max(4);
        evolucion5 = new evolucion_min(1);
        evolucion6 = new evolucion_min(2);
        evolucion7 = new evolucion_min(3);
        evolucion8 = new evolucion_min(4);

        evolucion1.setCtrl(this);
        evolucion2.setCtrl(this);
        evolucion3.setCtrl(this);
        evolucion4.setCtrl(this);
        evolucion5.setCtrl(this);
        evolucion6.setCtrl(this);
        evolucion7.setCtrl(this);
        evolucion8.setCtrl(this);
    }

    public void pintaMarcoMax(String texto) {
        interfaz.pintaMarcoMax(texto);
    }

    public void pintaMarcoMin(String texto) {
        interfaz.pintaMarcoMin(texto);
    }

    public synchronized void setOcupado(boolean ocupado, String tipo) {
        if (tipo.equals("max")) {
            this.Max_ocupado = ocupado;
        } else {
            this.Min_ocupado = ocupado;
        }
    }

    public int get_generaciones() {
        return this.generaciones;
    }

    public void set_generaciones(int generaciones) {
        this.generaciones = generaciones;
    }

    public void cambiar_generacion(String tipo) {
        lock.lock();
        try {
            // Cuando los 4 vectores hayan realizado la operación reemplazar, se cambia de generación
            switch (tipo) {
                case "max":
                    if (evolucion1.get_finished() && evolucion2.get_finished() && evolucion3.get_finished() && evolucion4.get_finished()) {

                        evolucion1.setGeneracion(evolucion1.getGeneracion() + 1);
                        evolucion2.setGeneracion(evolucion2.getGeneracion() + 1);
                        evolucion3.setGeneracion(evolucion3.getGeneracion() + 1);
                        evolucion4.setGeneracion(evolucion4.getGeneracion() + 1);

                        pintaVectoresMax(vectores_max, evolucion1.getGeneracion());

                        evolucion1.set_finished(false);
                        evolucion2.set_finished(false);
                        evolucion3.set_finished(false);
                        evolucion4.set_finished(false);

                        // Notificar a todos los hilos que la generación ha cambiado
                        notifyAllOcupado();
                        System.out.println("Generación cambiada a " + evolucion1.getGeneracion());
                    }
                    break;

                case "min":
                    if (evolucion5.get_finished() && evolucion6.get_finished() && evolucion7.get_finished() && evolucion8.get_finished()) {
                        evolucion5.setGeneracion(evolucion5.getGeneracion() + 1);
                        evolucion6.setGeneracion(evolucion6.getGeneracion() + 1);
                        evolucion7.setGeneracion(evolucion7.getGeneracion() + 1);
                        evolucion8.setGeneracion(evolucion8.getGeneracion() + 1);

                        pintaVectoresMin(vectores_min, evolucion5.getGeneracion());

                        evolucion5.set_finished(false);
                        evolucion6.set_finished(false);
                        evolucion7.set_finished(false);
                        evolucion8.set_finished(false);

                        // Notificar a todos los hilos que la generación ha cambiado
                        notifyAllOcupado();
                        System.out.println("Generación cambiada a " + evolucion5.getGeneracion());
                    }
                    break;
            }
        } finally {
            lock.unlock();
        }
    }

    public synchronized boolean getOcupado(String tipo) {
        if (tipo.equals("max")) {
            return this.Max_ocupado;
        } else {
            return this.Min_ocupado;
        }
    }

    public vector[] getVectores_min() {
        return vectores_min;
    }

    public vector[] getVectores_max() {
        return vectores_max;
    }

    public void setInterfaz(InterfazPrincipal interfaz) {
        this.interfaz = interfaz;
    }

    public void accion(String accion) {

        if (accion.equals("generate")) {
            generar_vectores();
            interfaz.turnStartButtonOn();
            this.set_generaciones(interfaz.getGenerations());
        } else if (accion.equals("start")) {
            interfaz.turnGenerateButtonOff();
            interfaz.turnStartButtonOff();

            interfaz.pintaVectoresMax(this.vectores_max, evolucion1.getGeneracion());

            evolucion1.start();
            evolucion2.start();
            evolucion3.start();
            evolucion4.start();

            interfaz.pintaVectoresMin(this.vectores_min, evolucion5.getGeneracion());

            evolucion5.start();
            evolucion6.start();
            evolucion7.start();
            evolucion8.start();

            this.start();

        }

        if (accion.equals("start2")) {

            evolucion1 = new evolucion_max(1);
            evolucion2 = new evolucion_max(2);
            evolucion3 = new evolucion_max(3);
            evolucion4 = new evolucion_max(4);

            evolucion1.setPoblacion(vectores_max);
            evolucion2.setPoblacion(vectores_max);
            evolucion3.setPoblacion(vectores_max);
            evolucion4.setPoblacion(vectores_max);

            evolucion1.setCtrl(this);
            evolucion2.setCtrl(this);
            evolucion3.setCtrl(this);
            evolucion4.setCtrl(this);

            try {
                evolucion1.start();
                evolucion2.start();
                evolucion3.start();
                evolucion4.start();
            } catch (Exception e) {
                System.out.println("Error al iniciar los hilos");
            }

        }

        if (accion.equals("start3")) {
            evolucion5 = new evolucion_min(1);
            evolucion6 = new evolucion_min(2);
            evolucion7 = new evolucion_min(3);
            evolucion8 = new evolucion_min(4);

            evolucion5.setCtrl(this);
            evolucion6.setCtrl(this);
            evolucion7.setCtrl(this);
            evolucion8.setCtrl(this);

            evolucion5.setPoblacion(vectores_min);
            evolucion6.setPoblacion(vectores_min);
            evolucion7.setPoblacion(vectores_min);
            evolucion8.setPoblacion(vectores_min);

        }
    }

    public void pintaVectoresMax(vector[] vectores_max, int generacion) {
        interfaz.pintaVectoresMax(vectores_max, generacion);
    }

    public void pintaVectoresMin(vector[] vectores_min, int generacion) {
        interfaz.pintaVectoresMin(vectores_min, generacion);
    }

    public void generar_vectores() {

        //genera los vectores de manera aleatoria
        evolucion1.generar();
        vector[] vectores = evolucion1.getPoblacion();
        this.vectores_max = evolucion1.getPoblacion();
        this.vectores_min = evolucion1.getPoblacion();
        //manda la información a los otro evolucion
        evolucion2.setPoblacion(vectores);
        evolucion3.setPoblacion(vectores);
        evolucion4.setPoblacion(vectores);
        evolucion5.setPoblacion(vectores);
        evolucion6.setPoblacion(vectores);
        evolucion7.setPoblacion(vectores);
        evolucion8.setPoblacion(vectores);

        //manda la información a la interfaz
        interfaz.mostrar_vectores(evolucion8.getPoblacion());

    }

    @Override
    public void run() {
        while (true) {
            try {
                cambiar_generacion("max");
                cambiar_generacion("min");
                Thread.sleep(100); // Adjust the sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
