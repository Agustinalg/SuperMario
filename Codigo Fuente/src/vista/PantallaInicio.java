package vista;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import juego.ControladorPartida;


public class PantallaInicio extends JPanel{

	private static final long serialVersionUID = 1L;
	protected JButton startButton, rankingButton, saveButton, backButton;
	protected JLabel backgroundLabel, selectionLabel, nameLabel;
	protected JPanel namePanel, buttonsPanel;
	protected JTextField nameField;
	protected ControladorPantallas controlador;
	protected ControladorPartida controladorPartida;

	public PantallaInicio(ControladorPantallas controlador, ControladorPartida controladorPartida){
		this.controlador = controlador;
		this.controladorPartida = controladorPartida;
		this.setPreferredSize(new Dimension(ConstantesPantalla.panelAncho, ConstantesPantalla.panelAlto));
		this.setLayout(null);

		agregarImagenFondo();
		inicializarIconoSeleccion();
		inicializarComponentesNombre();
		agregarBotonStart();
		agregarBotonRanking();
	}

	private void agregarImagenFondo(){
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/imageninicio.png"));
		Image imagen = icon.getImage().getScaledInstance(ConstantesPantalla.panelAncho, ConstantesPantalla.panelAlto, Image.SCALE_SMOOTH);
		backgroundLabel = new JLabel(new ImageIcon(imagen));
		backgroundLabel.setBounds(0, -40, ConstantesPantalla.panelAncho, ConstantesPantalla.panelAlto);
		backgroundLabel.setOpaque(false);

		add(backgroundLabel);		
	}

	private void inicializarIconoSeleccion() {
		ImageIcon iconSeleccion = new ImageIcon(getClass().getResource("/imagenes/selectIcon.png"));
		Image imagenSeleccion = iconSeleccion.getImage().getScaledInstance(46, 46, Image.SCALE_SMOOTH);

		selectionLabel = new JLabel(new ImageIcon(imagenSeleccion));
		selectionLabel.setPreferredSize(new Dimension(48,48));
	}

	private void inicializarComponentesNombre() {
		namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(3,1, 2,2));
		namePanel.setBounds(130, 300, 545, 180);
		namePanel.setOpaque(false);

		inicializarLabelNombre();
		inicializarCampoTexto();
		inicializarBotones();		
	}

	private void inicializarBotones() {
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setOpaque(false);

		saveButton = new JButton("Guardar");
		saveButton.setPreferredSize(new Dimension(260, 55));
		saveButton.setFont(ConfigurarFuente.getFuenteMario(18f));
		saveButton.setForeground(Color.WHITE);
		addMouseActions(saveButton);
		decorarBoton(saveButton);
		buttonsPanel.add(saveButton);

		backButton = new JButton("Volver");
		backButton.setPreferredSize(new Dimension(260, 55));
		backButton.setFont(ConfigurarFuente.getFuenteMario(18f));
		backButton.setForeground(Color.white);
		addMouseActions(backButton);
		decorarBoton(backButton);
		buttonsPanel.add(backButton);

		namePanel.add(buttonsPanel);
	}

	private void inicializarCampoTexto() {
		nameField = new JTextField(20);
		nameField.setFont(ConfigurarFuente.getFuenteMario(18f));
		nameField.setForeground(Color.WHITE); 
		nameField.setOpaque(false);
		nameField.setBorder(BorderFactory.createLineBorder(Color.white, 2));

		namePanel.add(nameField);
	}

	private void inicializarLabelNombre() {
		nameLabel = new JLabel(" Ingrese su nombre: ");
		nameLabel.setLayout(null);
		nameLabel.setFont(ConfigurarFuente.getFuenteMario(14f));
		nameLabel.setForeground(Color.white);
		nameLabel.setOpaque(false);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		namePanel.add(nameLabel);
	}

	private void agregarBotonStart(){
		startButton = new JButton("Start");
		startButton.setLayout(new BorderLayout());
		startButton.setBounds(275, 325, 255, 55);
		startButton.setFont(ConfigurarFuente.getFuenteMario(24f));
		startButton.setForeground(Color.WHITE);
		decorarBoton(startButton);

		startButton.addActionListener(e -> {
			rankingButton.setVisible(false);
			startButton.setVisible(false);
			gestionCuadroNombre();
		});
		addMouseActions(startButton);		

		add(startButton);
		backgroundLabel.add(startButton);	
	}

	private void gestionCuadroNombre() {
		startButton.remove(selectionLabel);
		rankingButton.remove(selectionLabel);
		revalidate();
		repaint();

		mostrarComponentes();
		agregarEventos();
	}

	private void mostrarComponentes() {
		this.add(namePanel);
		backgroundLabel.add(namePanel);
		namePanel.setVisible(true);
	}

	private void agregarEventos() {
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreJugador = nameField.getText();
				if (!nombreJugador.isEmpty()) {
					controladorPartida.guardarNombre(nombreJugador);                   
					controlador.mostrarPantallaSeleccion();
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, ingresa un nombre.");
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveButton.remove(selectionLabel);
				backButton.remove(selectionLabel);
				revalidate();
				repaint();

				namePanel.setVisible(false);
				startButton.setVisible(true);
				rankingButton.setVisible(true);
			}
		});

	}

	private void agregarBotonRanking(){
		rankingButton = new JButton("Ranking");
		rankingButton.setBounds(275, 415, 305, 55);
		rankingButton.setFont(ConfigurarFuente.getFuenteMario(24f));
		rankingButton.setForeground(Color.WHITE);
		decorarBoton(rankingButton);

		rankingButton.addActionListener(e -> {
			rankingButton.remove(selectionLabel);
			controlador.mostrarPantallaRanking();
		});
		addMouseActions(rankingButton);	

		add(rankingButton);
		backgroundLabel.add(rankingButton);		
	}

	private void addMouseActions(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				removeSelectionFromButtons();
				button.add(selectionLabel, BorderLayout.WEST);
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//selectionLabel.setVisible(false);
				button.remove(selectionLabel);
				revalidate();
				repaint();
			}
		});	
	}

	private void decorarBoton(JButton botonStart2) {
		botonStart2.setContentAreaFilled(false); 
		botonStart2.setBorderPainted(false); 
		botonStart2.setFocusPainted(false); 
		botonStart2.setOpaque(false);
	}

	private void removeSelectionFromButtons() {
		startButton.remove(selectionLabel);
		rankingButton.remove(selectionLabel);
		saveButton.remove(selectionLabel);
		backButton.remove(selectionLabel);
	}
}


