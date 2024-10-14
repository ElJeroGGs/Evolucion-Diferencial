import controlador.control_principal;
import vista.InterfazPrincipal;

public class main
{

    public static void main(String[] args)
    {
        InterfazPrincipal interfaz = new InterfazPrincipal();
        control_principal controlador = new control_principal();
        interfaz.setControlador(controlador);
        controlador.setInterfaz(interfaz);

    }


}