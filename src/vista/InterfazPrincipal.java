package vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import controlador.control_principal;
import modelo.vector;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazPrincipal extends JFrame implements ActionListener{

    control_principal ctrl;
    JTextField populationTextField;
    JTextPane maxTextField;
    JTextPane minTextField;
    JButton startButton;
    JButton generateButton;
    JComboBox<Integer> generationsComboBox;
    JScrollPane maxScrollPane;
    JScrollPane minScrollPane;

    public InterfazPrincipal() {
        super("Evolución Diferencial");
        this.setLayout(null);
        Font font = new Font("Arial", Font.BOLD, 18);

        JLabel titleLabel = new JLabel("Evolución Diferencial", SwingConstants.CENTER);
        titleLabel.setFont(font);
        titleLabel.setBounds(0, 0, 900, 50);
        this.add(titleLabel);

        JLabel functionLabel = new JLabel("f(x) = x^2 + y/z", SwingConstants.CENTER);
        functionLabel.setFont(font);
        functionLabel.setBounds(0, 60, 900, 50);
        this.add(functionLabel);

        JLabel generationsLabel = new JLabel("¿Cuántas generaciones?");
        generationsLabel.setFont(font);
        generationsLabel.setBounds(275, 120, 350, 50);
        this.add(generationsLabel);

        generationsComboBox = new JComboBox<>();
        for (int i = 1; i <= 100; i++) {
            generationsComboBox.addItem(i);
        }
        generationsComboBox.setFont(font);
        generationsComboBox.setBounds(275, 180, 350, 50);
        this.add(generationsComboBox);

        populationTextField = new JTextField();
        populationTextField.setFont(font);
        populationTextField.setEditable(false);
        populationTextField.setBounds(50, 240, 750, 50);
        this.add(populationTextField);

        generateButton = new JButton("Generar Población Inicial");
        generateButton.addActionListener(this);
        generateButton.setActionCommand("generate");
        generateButton.setFont(font);
        generateButton.setBounds(275, 300, 350, 50);
        this.add(generateButton);
        

        startButton = new JButton("Comenzar");
        startButton.addActionListener(this);
        startButton.setActionCommand("start");
        startButton.setFont(font);
        startButton.setEnabled(false);
        startButton.setBounds(275, 360, 350, 50);
        this.add(startButton);

        Font fonte = new Font("Arial", Font.PLAIN, 20);

        // Etiqueta de Maximización
        JLabel maxLabel = new JLabel("Algoritmo");
        maxLabel.setFont(fonte);
        maxLabel.setBounds(100, 420, 350, 30);
        this.add(maxLabel);

        // Cuadro de texto de Maximización
       
        maxTextField = new JTextPane();
        maxTextField.setFont(fonte);
        maxTextField.setEditable(false);
        maxScrollPane = new JScrollPane(maxTextField);
        maxScrollPane.setBounds(30, 460, 400, 450);
        this.add(maxScrollPane);
        //this.add(maxTextField);
        

        // Etiqueta de Minimización
        JLabel minLabel = new JLabel("Minimización");
        minLabel.setFont(fonte);
        minLabel.setBounds(450, 420, 350, 30);
        this.add(minLabel);

        // Cuadro de texto de Minimización
        minTextField = new JTextPane();
        minTextField.setFont(fonte);
        minTextField.setBounds(450, 460, 450, 450);
        minTextField.setEditable(false);
        

        minScrollPane = new JScrollPane(minTextField);
        minScrollPane.setBounds(450, 460, 400, 450);
        this.add(minScrollPane);

        this.setSize(900, 1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String accion = e.getActionCommand();

        ctrl.accion(accion);



    }

    public int getGenerations() {
        return (Integer) generationsComboBox.getSelectedItem();
    }

    public void turnStartButtonOn() {
        startButton.setEnabled(true);
    }

    public void turnStartButtonOff() {
        startButton.setEnabled(false);
    }

    public void turnGenerateButtonOn() {
        generateButton.setEnabled(true);
    }
    public void turnGenerateButtonOff() {
        generateButton.setEnabled(false);
    }

    

    public void setControlador(control_principal control_principal) {

        this.ctrl = control_principal;
        }

        public void mostrar_vectores(vector[] poblacion) {

        String texto = "";
        for (int i = 0; i < poblacion.length; i++) {
            texto += String.format("V%d: [%.2f, %.2f, %.2f] ", 
            (i + 1), 
            poblacion[i].get_x(), 
            poblacion[i].get_y(), 
            poblacion[i].get_z()
            );
        }
        this.populationTextField.setText(texto);
        }

        public void pintaMarcoMax(String texto) {
        String text = maxTextField.getText();
        text += texto + "\n";
        this.maxTextField.setText(text);

        
       
        
       
    }

    public void pintaMarcoMin(String texto) {
        String text = minTextField.getText();
        text += texto + "\n";
        this.minTextField.setText(text);

        }

        public void pintaVectoresMax(vector[] vectores_max, int generacion) {

        String texto = "Generación " + generacion + "\n";
        for (int i = 0; i < vectores_max.length; i++) {
            texto += String.format("V%d: [%.2f, %.2f, %.2f] ", 
            (i + 1), 
            vectores_max[i].get_x(), 
            vectores_max[i].get_y(), 
            vectores_max[i].get_z()
            );
            texto += "\n";
        }
        this.pintaMarcoMax(texto);

        }

        public void pintaVectoresMin(vector[] vectores_min, int generacion) {
        String texto = "Generación " + generacion + "\n";
        for (int i = 0; i < vectores_min.length; i++) {
            texto += String.format("V%d: [%.2f, %.2f, %.2f] ", 
            (i + 1), 
            vectores_min[i].get_x(), 
            vectores_min[i].get_y(), 
            vectores_min[i].get_z()
            );
            texto += "\n";
        }
        this.pintaMarcoMin(texto);
            
    }

    public void turnGenerateBoxOff() {
        generationsComboBox.setEnabled(false);
    }

    public void scroll_cero() {
        
        maxScrollPane.getVerticalScrollBar().setValue(maxScrollPane.getVerticalScrollBar().getMaximum());
        
        minScrollPane.getVerticalScrollBar().setValue(minScrollPane.getVerticalScrollBar().getMaximum());
    }
}
