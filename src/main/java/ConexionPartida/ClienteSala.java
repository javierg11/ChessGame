package ConexionPartida;

import java.awt.Color;


import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Tablero.MetodosMoverPiezas;
import Tablero.TableroAjedrez;
import interfazGrafica.JugarEnLAN;

public class ClienteSala {
    private static final int PUERTO_BROADCAST = 8888;
    SalaInfo sala=null;
    private String password = "";
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;
	public static boolean jugando=true;

    public static Movimientos mov = new Movimientos();
    public void refrescarSalas(JPanel PanelFull, JPanel PanelMedio, JFrame frame) {
	    new Thread(() -> unirseASala(PanelFull,PanelMedio,frame)).start();
	}
    public void unirseASala(JPanel PanelFull, JPanel PanelMedio, JFrame frame) {
    	// Llama a esto cuando quieras refrescar la lista de salas
    	
    	PanelMedio.removeAll();
    	CircularSpinner spinner = new CircularSpinner(150);
		spinner.setAlignmentX(Component.CENTER_ALIGNMENT);

		GridBagConstraints gbc = new GridBagConstraints();
		PanelMedio.add(spinner, gbc);
		PanelMedio.revalidate();
		PanelMedio.repaint(); 

    	// Este es el método que contiene tu bloque
    	    List<SalaInfo> salas = new ArrayList<>();
    	    Map<Integer, InetAddress> salaIPs = new HashMap<>();
			

    	    DatagramSocket Datasocket = null;
    	    try {
    	        Datasocket = new DatagramSocket();
    	        Datasocket.setBroadcast(true);
    	        byte[] mensaje = "LIST_SALAS".getBytes();

    	        DatagramPacket packet = new DatagramPacket(
    	            mensaje, mensaje.length,
    	            InetAddress.getByName("255.255.255.255"), PUERTO_BROADCAST
    	        );
    	        Datasocket.send(packet);

    	        Datasocket.setSoTimeout(2000);
    	        
    	        byte[] buffer = new byte[1024];
    	        while (true) {
    	            try {
    	                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
    	                Datasocket.receive(respuesta);

    	                String recibido = new String(respuesta.getData(), 0, respuesta.getLength()).trim();
    	                SalaInfo sala = SalaInfo.fromString(recibido);
    	                salas.add(sala);
    	                salaIPs.put(sala.puerto, respuesta.getAddress());
    	            } catch (SocketTimeoutException e) {
    	                break;
    	            }
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    } finally {
    	        if (Datasocket != null && !Datasocket.isClosed()) {
    	            Datasocket.close();
    	        }
    	    }
    	    System.out.println(salas);
    	    SwingUtilities.invokeLater(() -> {

    	        if (salas.isEmpty()) {
    	            PanelMedio.removeAll();
    	            PanelMedio.setLayout(new BoxLayout(PanelMedio, BoxLayout.Y_AXIS));
    	            JLabel emptyLabel = new JLabel("No se encontraron salas.");
    	            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	            emptyLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    	            PanelMedio.add(Box.createVerticalStrut(40));
    	            PanelMedio.add(emptyLabel);

    	            PanelMedio.revalidate();
    	            PanelMedio.repaint();
    	        } else {
    	            PanelFull.removeAll();
    	            PanelFull.setLayout(new BoxLayout(PanelFull, BoxLayout.Y_AXIS));

    	            for (int i = 0; i < salas.size(); i++) {
    	                final SalaInfo sala = salas.get(i);
    	                final InetAddress ipSala = salaIPs.get(sala.puerto);
    	                String texto = String.format("Nombre de Sala: %s", sala.nombre);

    	                JButton btn = new JButton(texto) {
    	                    /**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							@Override
    	                    protected void paintComponent(Graphics g) {
    	                        Graphics2D g2 = (Graphics2D) g.create();
    	                        g2.setColor(new Color(200, 200, 200, 80));
    	                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    	                        g2.dispose();
    	                        super.paintComponent(g);
    	                    }
    	                };
    	                btn.setFont(new Font("SansSerif", Font.BOLD, 20));
    	                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    	                btn.setFocusPainted(false);
    	                btn.setBorderPainted(false);
    	                btn.setContentAreaFilled(false);
    	                btn.setOpaque(false);
    	                btn.setHorizontalAlignment(SwingConstants.LEFT);
    	                btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
    	                btn.setPreferredSize(new Dimension(0, 60));
    	                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    	                btn.addActionListener(e -> {
    	                    if (sala.requierePassword) {
    	                        password = JOptionPane.showInputDialog(PanelFull, "Esta sala requiere contraseña. Ingrésala:");
    	                        if (password == null) return;
    	                    }
    	                    final String passwordFinal = password;
    	                    new Thread(() -> conectarASala(sala, ipSala, passwordFinal, frame)).start();
    	                });

    	                PanelFull.add(Box.createVerticalStrut(12));
    	                PanelFull.add(btn);
    	            }

    	            PanelFull.revalidate();
    	            PanelFull.repaint();
    	        }
    	    });
    	}

    private void conectarASala(SalaInfo sala, InetAddress ipServidor, String password, JFrame frame) {
        try {
            socket = new Socket(ipServidor, sala.puerto);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String respuesta = in.readLine();
            if ("PASSWORD?".equals(respuesta)) {
                out.write(password + "\n");
                out.flush();

                String resultado = in.readLine();
                frame.dispose();

                if ("ACCESO DENEGADO".equals(resultado)) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Acceso denegado", JOptionPane.ERROR_MESSAGE));
                    return;
                }
            }
            // Aquí continúa la lógica original de unión a la sala
            SwingUtilities.invokeLater(() -> {
            	//SalaInfo.setColor(SalaInfo.color);
                JugarEnLAN.getFrame().dispose();
				SalaInfo.setColor(false);
				Movimientos.setColorAJugar(false);

                TableroAjedrez.crearTipoTablero(true, (int) sala.tiempo, sala.incrementoTiempo,  "Cliente de Partida: " +
                sala.nombre);
            });


            new Thread(() -> {
                try {
        			ControlDeJugadas controlDeJugadas = new ControlDeJugadas();

//                        Thread.sleep(50);
                    while (jugando) {

                    	if (mov.isColorAJugar() == null) {
                    		Movimientos.setColorAJugar(false);
                    	}
                	   if (mov.isColorAJugar()) {

                		   if (MetodosMoverPiezas.sensorDeTurnosDosJugadores) {
                			   controlDeJugadas.hacerJugadas(out);
                			   Movimientos.setColorAJugar(false);

								MetodosMoverPiezas.sensorDeTurnosDosJugadores = false; // Resetea el flag SOLO
								// después de enviar

							}

						} else {
							controlDeJugadas.escucharJugadas(in);
							Movimientos.setColorAJugar(true);
 
							MetodosMoverPiezas.sensorDeTurnosDosJugadores = false; // Resetea el flag SOLO
							// después de enviar

						}
                } } finally {

					try {
						if (out != null)
							out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						if (in != null)
							in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						if (socket != null && !socket.isClosed())
							socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
            

        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null, "Error al conectar con la sala.", "Error", JOptionPane.ERROR_MESSAGE));
        }
    }
    
    public static void algo() {
    	try {
    	    if (out != null) {
    	        out.write("FIN\n");
    	        out.flush();
    	        out.close();
    	    }
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	try {
    	    if (in != null)
    	        in.close();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	try {
    	    if (socket != null && !socket.isClosed())
    	        socket.close();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}

    }

}
