package cadenas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;

public class Interfaz {
	private Analizador analizador;
	private JFrame frame;
	private JTextField txtCantidadDeLineas_1;
	private JTextField txtCaracteresTotales;
	private JTextField txtCantidadDeLineas;
	private JTextField txtCantidadDeCaracteres;
	private JTextField txtPorcentajeDeComentarios;
	private JTextField txtComplejidadCiclomatica;
	private JTextField txtFanIn;
	private JTextField txtFanOut;
	private JTextField txtHalsteadLongitud;
	private JTextField txtHalsteadVolumen;
	private JTextField txtHalsteadEsfuerzo;
	private Navegador navegador;
	private JTextField txtSeleccionarMetodo;
	private JTextField txtCodigo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz window = new Interfaz();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interfaz() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Analizador");
		this.frame.setSize(944, 624);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 73, 134, 177);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		JList<String> list = new JList<String>();
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultListModel<String> modeloLista=new DefaultListModel<String>();
		list.setModel(modeloLista);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 134, 177);
		scrollPane.setViewportView(list);
		panel.add(scrollPane);
		
		JButton btnTestear = new JButton("Testear");
		btnTestear.setEnabled(false);
		
		btnTestear.setText("Testear");
		btnTestear.setBounds(10, 398, 89, 23);
		frame.getContentPane().add(btnTestear);
		
		
		JTextPane rutaTxt = new JTextPane();
		rutaTxt.setBounds(10, 11, 144, 23);
		frame.getContentPane().add(rutaTxt);
		
		
		JTextPane txtpnTotal = new JTextPane();
		txtpnTotal.setForeground(Color.RED);
		txtpnTotal.setEditable(false);
		txtpnTotal.setText("  Total");
		txtpnTotal.setBounds(428, 11, 55, 20);
		frame.getContentPane().add(txtpnTotal);
		
		JButton btnSeleccionarMetodo = new JButton("Seleccionar");
		btnSeleccionarMetodo.setEnabled(false);
		
		Button button = new Button("Seleccionar archivo");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DefaultListModel<String>) modeloLista).clear();
				btnSeleccionarMetodo.setEnabled(false);
				navegador = new Navegador();
				int result = navegador.fileChooser.showSaveDialog(navegador.frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				   rutaTxt.setText(navegador.fileChooser.getSelectedFile().getAbsolutePath());
				   navegador.frame.setVisible(false);
				   navegador = null;
				   btnTestear.setEnabled(true);
				   
				} else if (result == JFileChooser.CANCEL_OPTION) {
				    navegador.frame.setVisible(false);
				}
			}
			
			
		});
		button.setBounds(160, 11, 115, 22);
		frame.getContentPane().add(button);
		
		
		
		txtCantidadDeLineas_1 = new JTextField();
		txtCantidadDeLineas_1.setEditable(false);
		txtCantidadDeLineas_1.setBackground(Color.LIGHT_GRAY);
		txtCantidadDeLineas_1.setForeground(Color.BLACK);
		txtCantidadDeLineas_1.setText("Cantidad de lineas totales");
		txtCantidadDeLineas_1.setColumns(10);
		txtCantidadDeLineas_1.setBounds(221, 42, 197, 20);
		frame.getContentPane().add(txtCantidadDeLineas_1);
		
		JTextPane lineasTotalesTxt = new JTextPane();
		lineasTotalesTxt.setEditable(false);
		lineasTotalesTxt.setBounds(428, 42, 55, 20);
		frame.getContentPane().add(lineasTotalesTxt);
		
		JTextPane lineasComentadasTxt = new JTextPane();
		lineasComentadasTxt.setEditable(false);
		lineasComentadasTxt.setBounds(428, 73, 55, 20);
		frame.getContentPane().add(lineasComentadasTxt);
		
		JTextPane lineasDeCodigoTxt = new JTextPane();
		lineasDeCodigoTxt.setEditable(false);
		lineasDeCodigoTxt.setBounds(428, 104, 55, 20);
		frame.getContentPane().add(lineasDeCodigoTxt);
		
		JTextPane lineasEnBlanco = new JTextPane();
		lineasEnBlanco.setEditable(false);
		lineasEnBlanco.setBounds(428, 135, 55, 20);
		frame.getContentPane().add(lineasEnBlanco);
		
		JTextPane porcentajeComentariosTxt = new JTextPane();
		porcentajeComentariosTxt.setEditable(false);
		porcentajeComentariosTxt.setBounds(428, 166, 55, 20);
		frame.getContentPane().add(porcentajeComentariosTxt);
		
		JTextPane complejidadCiclomaticaTxt = new JTextPane();
		complejidadCiclomaticaTxt.setEditable(false);
		complejidadCiclomaticaTxt.setBounds(428, 197, 55, 20);
		frame.getContentPane().add(complejidadCiclomaticaTxt);
		
		JTextPane fanInTxt = new JTextPane();
		
		fanInTxt.setEditable(false);
		fanInTxt.setBounds(428, 228, 55, 20);
		frame.getContentPane().add(fanInTxt);
		
		JTextPane fanOutTxt = new JTextPane();
		fanOutTxt.setEditable(false);
		fanOutTxt.setBounds(428, 259, 55, 20);
		frame.getContentPane().add(fanOutTxt);
		
		txtCaracteresTotales = new JTextField();
		txtCaracteresTotales.setBackground(Color.LIGHT_GRAY);
		txtCaracteresTotales.setText("Cantidad de lineas comentadas");
		txtCaracteresTotales.setEditable(false);
		txtCaracteresTotales.setColumns(10);
		txtCaracteresTotales.setBounds(221, 73, 197, 20);
		frame.getContentPane().add(txtCaracteresTotales);
		
		txtCantidadDeLineas = new JTextField();
		txtCantidadDeLineas.setBackground(Color.LIGHT_GRAY);
		txtCantidadDeLineas.setText("Cantidad de lineas de codigo");
		txtCantidadDeLineas.setEditable(false);
		txtCantidadDeLineas.setColumns(10);
		txtCantidadDeLineas.setBounds(221, 104, 197, 20);
		frame.getContentPane().add(txtCantidadDeLineas);
		
		txtCantidadDeCaracteres = new JTextField();
		txtCantidadDeCaracteres.setBackground(Color.LIGHT_GRAY);
		txtCantidadDeCaracteres.setText("Cantidad de lineas en blanco");
		txtCantidadDeCaracteres.setEditable(false);
		txtCantidadDeCaracteres.setColumns(10);
		txtCantidadDeCaracteres.setBounds(221, 135, 197, 20);
		frame.getContentPane().add(txtCantidadDeCaracteres);
		
		txtPorcentajeDeComentarios = new JTextField();
		txtPorcentajeDeComentarios.setBackground(Color.LIGHT_GRAY);
		txtPorcentajeDeComentarios.setText("Porcentaje de comentarios %");
		txtPorcentajeDeComentarios.setEditable(false);
		txtPorcentajeDeComentarios.setColumns(10);
		txtPorcentajeDeComentarios.setBounds(221, 166, 197, 20);
		frame.getContentPane().add(txtPorcentajeDeComentarios);
		
		txtComplejidadCiclomatica = new JTextField();
		txtComplejidadCiclomatica.setBackground(Color.LIGHT_GRAY);
		txtComplejidadCiclomatica.setText("Complejidad Ciclomatica");
		txtComplejidadCiclomatica.setEditable(false);
		txtComplejidadCiclomatica.setColumns(10);
		txtComplejidadCiclomatica.setBounds(221, 197, 197, 20);
		frame.getContentPane().add(txtComplejidadCiclomatica);
		
		txtFanIn = new JTextField();
		txtFanIn.setBackground(Color.LIGHT_GRAY);
		txtFanIn.setText("Fan in");
		txtFanIn.setEditable(false);
		txtFanIn.setColumns(10);
		txtFanIn.setBounds(221, 228, 197, 20);
		frame.getContentPane().add(txtFanIn);
		
		txtFanOut = new JTextField();
		txtFanOut.setBackground(Color.LIGHT_GRAY);
		txtFanOut.setText("Fan out");
		txtFanOut.setEditable(false);
		txtFanOut.setColumns(10);
		txtFanOut.setBounds(221, 259, 197, 20);
		frame.getContentPane().add(txtFanOut);
		
		txtHalsteadLongitud = new JTextField();
		txtHalsteadLongitud.setBackground(Color.LIGHT_GRAY);
		txtHalsteadLongitud.setText("Halstead - Longitud");
		txtHalsteadLongitud.setEditable(false);
		txtHalsteadLongitud.setColumns(10);
		txtHalsteadLongitud.setBounds(221, 290, 197, 20);
		frame.getContentPane().add(txtHalsteadLongitud);
		
		JTextPane longitudTxt = new JTextPane();
		longitudTxt.setEditable(false);
		longitudTxt.setBounds(428, 290, 55, 20);
		frame.getContentPane().add(longitudTxt);
		
		txtHalsteadVolumen = new JTextField();
		txtHalsteadVolumen.setBackground(Color.LIGHT_GRAY);
		txtHalsteadVolumen.setText("Halstead - Volumen");
		txtHalsteadVolumen.setEditable(false);
		txtHalsteadVolumen.setColumns(10);
		txtHalsteadVolumen.setBounds(221, 321, 197, 20);
		frame.getContentPane().add(txtHalsteadVolumen);
		
		JTextPane volumenTxt = new JTextPane();
		volumenTxt.setEditable(false);
		volumenTxt.setBounds(428, 321, 55, 20);
		frame.getContentPane().add(volumenTxt);
		
		txtHalsteadEsfuerzo = new JTextField();
		txtHalsteadEsfuerzo.setBackground(Color.LIGHT_GRAY);
		txtHalsteadEsfuerzo.setText("Halstead - Esfuerzo");
		txtHalsteadEsfuerzo.setEditable(false);
		txtHalsteadEsfuerzo.setColumns(10);
		txtHalsteadEsfuerzo.setBounds(221, 349, 197, 20);
		frame.getContentPane().add(txtHalsteadEsfuerzo);
		
		JTextPane esfuerzoTxt = new JTextPane();
		esfuerzoTxt.setEditable(false);
		esfuerzoTxt.setBounds(428, 349, 55, 20);
		frame.getContentPane().add(esfuerzoTxt);
		
		txtSeleccionarMetodo = new JTextField();
		txtSeleccionarMetodo.setForeground(Color.RED);
		txtSeleccionarMetodo.setEditable(false);
		txtSeleccionarMetodo.setText("Seleccionar Metodo:");
		txtSeleccionarMetodo.setToolTipText("");
		txtSeleccionarMetodo.setBounds(10, 42, 134, 20);
		frame.getContentPane().add(txtSeleccionarMetodo);
		txtSeleccionarMetodo.setColumns(10);
		//panel.add(list);
		
		JPanel panelCodigo = new JPanel();
		panelCodigo.setBounds(493, 42, 424, 532);
		frame.getContentPane().add(panelCodigo);
		panelCodigo.setLayout(null);
		
		JScrollPane scrollPaneCodigo = new JScrollPane();
		scrollPaneCodigo.setBounds(0, 0, 424, 532);
		panelCodigo.add(scrollPaneCodigo);
		
		JTextArea codigoTextArea = new JTextArea();
		scrollPaneCodigo.setViewportView(codigoTextArea);
		
		JPanel panelComentarios = new JPanel();
		panelComentarios.setBounds(221, 398, 262, 176);
		frame.getContentPane().add(panelComentarios);
		panelComentarios.setLayout(null);
		
		JScrollPane scrollComentarios = new JScrollPane();
		scrollComentarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollComentarios.setBounds(0, 0, 262, 176);
		panelComentarios.add(scrollComentarios);
		
		JTextArea comentariosTextArea = new JTextArea();
		scrollComentarios.setViewportView(comentariosTextArea);
		
		btnTestear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				analizador = new Analizador(rutaTxt.getText());	
				analizador.analizar();
				lineasTotalesTxt.setText(String.valueOf(analizador.getCantidadTotalLineas()));
				lineasComentadasTxt.setText(String.valueOf(analizador.getCantidadLineasComentadas()));
				lineasEnBlanco.setText(String.valueOf(analizador.getCantidadLineasBlanco()));
				lineasDeCodigoTxt.setText(String.valueOf(analizador.getCantidadSoloCodigo()));
	
				DecimalFormat numberFormat = new DecimalFormat("#.00");
				porcentajeComentariosTxt.setText(numberFormat.format(analizador.getProcentajeComentarios()));
				
				((DefaultListModel<String>) modeloLista).clear();
				btnSeleccionarMetodo.setEnabled(false);
				for(int i=0; i<analizador.getMetodos().size();i++) {
					((DefaultListModel<String>) modeloLista).addElement(analizador.getMetodos().get(i));
				}
				codigoTextArea.setText(analizador.todoCodigoAString());
			}
		});
		
		btnSeleccionarMetodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indice= list.getSelectedIndex();
				String metodo = (String) modeloLista.getElementAt(indice);
				int idx = metodo.length()-1;
				metodo=metodo.substring(0, idx-1);
				if(analizador!=null) {
					ArrayList <String> metodoCompleto=analizador.buscarMetodo(metodo);
					analizador.analizarMetodo(metodoCompleto);
					String codigo=analizador.ArrayListToString(metodoCompleto);
					codigoTextArea.setText(codigo);
					lineasTotalesTxt.setText(String.valueOf(analizador.getCantidadTotalLineas()));
					lineasComentadasTxt.setText(String.valueOf(analizador.getCantidadLineasComentadas()));
					lineasEnBlanco.setText(String.valueOf(analizador.getCantidadLineasBlanco()));
					lineasDeCodigoTxt.setText(String.valueOf(analizador.getCantidadSoloCodigo()));
					metodoCompleto=analizador.borrarComentarios(metodoCompleto);
					int complejidad = analizador.complejidad(metodoCompleto);
					complejidadCiclomaticaTxt.setText(String.valueOf(complejidad));
					ArrayList <String> codigoCompleto=analizador.codigoToArray();
					int fanIN=analizador.fanIn(codigoCompleto, metodo);
					fanInTxt.setText(String.valueOf(fanIN));
					int fanOUT = analizador.fanOut(metodoCompleto);
					fanOutTxt.setText(String.valueOf(fanOUT));
					DecimalFormat numberFormat = new DecimalFormat("#.00");
					double porcentaje = analizador.getProcentajeComentarios();
					porcentajeComentariosTxt.setText(numberFormat.format(porcentaje));
					analizador.halstead(metodo);
					esfuerzoTxt.setText(numberFormat.format(analizador.getEsfuerzo()));
					longitudTxt.setText(String.valueOf(analizador.getLongitud()));
					volumenTxt.setText(numberFormat.format(analizador.getVolumen()));
					
					String metodoSplit[] = metodo.split(" ");
					
					comentariosTextArea.setText("");
					if(porcentaje<10)
						comentariosTextArea.append("El porcentaje de comentarios en este metodo \n"
								+ "es menor al 10% con respecto a la cantidad \n"
								+ "de lineas de codigo, se recomienda un\n"
								+ "minimo de 10%\n\n");
					if(fanIN==0 && !metodoSplit[1].equals("main"))
						comentariosTextArea.append("El FanIn recomendado es de al menos 1, este \n"
								+ "metodo no se esta utilizando dentro del codigo\n\n");
					if(complejidad>10)
						comentariosTextArea.append("La complejidad ciclomatica de este metodo \n"
								+ " es muy alta, se recomienda modularizar \n"
								+ " el metodo\n\n");
				}
		
			}
		});
		btnSeleccionarMetodo.setBounds(50, 259, 104, 23);
		frame.getContentPane().add(btnSeleccionarMetodo);
		
		
		
		txtCodigo = new JTextField();
		txtCodigo.setForeground(Color.BLUE);
		txtCodigo.setBackground(Color.WHITE);
		txtCodigo.setEditable(false);
		txtCodigo.setText("Codigo:");
		txtCodigo.setBounds(493, 11, 86, 20);
		frame.getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);
		
		
		
		
		
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!list.isSelectionEmpty())
					btnSeleccionarMetodo.setEnabled(true);
			}
			
		});
	}
}
