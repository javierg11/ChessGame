package ConexionPartida;

import java.awt.Component;

import java.awt.Dimension;
import java.awt.Font;
import java.io.*;
import java.net.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import InterfazGrafica.JugarEnLAN;
import Tablero.FuncionesVisualesTablero;
import Tablero.MetodosMoverPiezas;
import Tablero.TableroAjedrez;

public class ServidorSala {
	private static final int PUERTO_BROADCAST = 8888;
	private int incrementoTiempo;
	private Socket socket = null;
	private ServerSocket serverSocket = null;
	private static String password = "";
	private float tiempo;
    public static Movimientos mov = new Movimientos();

	public void crearSala(JPanel Panel, JFrame frame) {
		try {
			String nombreSala = JOptionPane.showInputDialog(null, "Nombre de la sala:");

			int opcionPassword = JOptionPane.showConfirmDialog(null, "¿Requiere contraseña?", "Contraseña",
					JOptionPane.YES_NO_OPTION);
			boolean requierePassword = (opcionPassword == JOptionPane.YES_OPTION);

			if (requierePassword) {
				password = JOptionPane.showInputDialog(null, "Contraseña:");
			}

			Object[] opcionesColor = { "Blanco (w)", "Negro (b)" };
			int colorElegido = JOptionPane.showOptionDialog(null, "¿Qué color quieres?", "Color",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesColor, opcionesColor[0]);
			mov.setColorAJugar((colorElegido == 0)); 			// true = blanco, false = negro

			boolean tiempoValido = false;
			while (!tiempoValido) {
				try {
					String tiempoStr = JOptionPane.showInputDialog(null, "Pon el tiempo de la partida:");
					tiempo = Float.parseFloat(tiempoStr);
					tiempoValido = true;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido para el tiempo.");
				}
			}

			boolean incrementoValido = false;
			while (!incrementoValido) {
				try {
					String incrementoStr = JOptionPane.showInputDialog(null, "Pon el incremento de la partida:");
					incrementoTiempo = Integer.parseInt(incrementoStr);
					incrementoValido = true;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido para el incremento.");
				}
			}

			// Puedes mostrar un resumen de los datos
			String resumen = "Sala: " + nombreSala + "\nContraseña: " + (requierePassword ? password : "No")
					+ "\nColor: " + (mov.isColorAJugar() ? "Blanco" : "Negro") + "\nTiempo: " + tiempo + "\nIncremento: "
					+ incrementoTiempo;
			JOptionPane.showMessageDialog(null, resumen);

			// --- Aquí añadimos el spinner al panel ---
			SwingUtilities.invokeLater(() -> {
				Panel.removeAll();

				JLabel esperandoLabel = new JLabel("Esperando al oponente...");
				esperandoLabel.setFont(new Font("Arial", Font.BOLD, 24));
				esperandoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

				CircularSpinner spinner = new CircularSpinner(80);
				spinner.setAlignmentX(Component.CENTER_ALIGNMENT);

				Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
				Panel.add(Box.createRigidArea(new Dimension(0, 30)));
				Panel.add(esperandoLabel);
				Panel.add(Box.createRigidArea(new Dimension(0, 20)));
				Panel.add(spinner);
				Panel.add(Box.createVerticalGlue());

				Panel.revalidate();
				Panel.repaint();
			});

			// Elegir un puerto TCP libre en el rango 10000-10999
			int puertoTCP;
			while (true) {
				puertoTCP = 10000 + (int) (Math.random() * 1000);
				try {
					serverSocket = new ServerSocket(puertoTCP);
					break;
				} catch (IOException e) {
					// Puerto en uso, intenta otro
				}
			}

			SalaInfo info = new SalaInfo(nombreSala, puertoTCP, requierePassword, mov.isColorAJugar(), tiempo, incrementoTiempo);

			// Hilo para responder broadcasts
			new Thread(() -> {
				DatagramSocket broadcastSocket = null;
				try {
					broadcastSocket = new DatagramSocket(PUERTO_BROADCAST, InetAddress.getByName("0.0.0.0"));
					broadcastSocket.setBroadcast(true);
					byte[] buffer = new byte[1024];
					while (true) {
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						broadcastSocket.receive(packet);
						String msg = new String(packet.getData(), 0, packet.getLength()).trim();

						if (msg.equals("LIST_SALAS")) {
							byte[] data = info.toString().getBytes();
							DatagramPacket respuesta = new DatagramPacket(data, data.length, packet.getAddress(),
									packet.getPort());
							broadcastSocket.send(respuesta);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (broadcastSocket != null && !broadcastSocket.isClosed()) {
						broadcastSocket.close();
					}
				}
			}).start();

			System.out.println("Sala '" + nombreSala + "' creada en el puerto TCP " + puertoTCP);

			// Servidor TCP para aceptar conexiones de clientes
			while (true) {
				Socket client = serverSocket.accept();
				new Thread(() -> {
					BufferedReader in = null;
					BufferedWriter out = null;
					try {
						in = new BufferedReader(new InputStreamReader(client.getInputStream()));
						out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

						if (requierePassword) {
							out.write("PASSWORD?\n");
							out.flush();
							String entrada = in.readLine();
							if (entrada == null || !entrada.equals(password)) {
								out.write("ACCESO DENEGADO\n");
								out.flush();
								return; // Termina el hilo para este cliente
							}
						}

						out.write("BIENVENIDO A LA SALA " + nombreSala + "\n");
						out.flush();
						frame.dispose();
						JugarEnLAN.getFrame().dispose();
						TableroAjedrez.crearTipoTablero(true, (int) tiempo, incrementoTiempo, mov.isColorAJugar(),
								"Servidor de la sala: " + nombreSala);

						ControlDeJugadas controlDeJugadas = new ControlDeJugadas();

//	                	    // Es tu turno
						while (true) {

							if (mov.isColorAJugar()) {
								if (MetodosMoverPiezas.sensorDeTurnosDosJugadores) {
									controlDeJugadas.hacerJugadas(out);
									mov.setColorAJugar(false);
									MetodosMoverPiezas.sensorDeTurnosDosJugadores = false; // Resetea el flag SOLO
									// después de enviar

								}

							} else {
								controlDeJugadas.escucharJugadas(in);
								mov.setColorAJugar(true);
								MetodosMoverPiezas.sensorDeTurnosDosJugadores = false; // Resetea el flag SOLO
								// después de enviar

							}
							SalaInfo.setColor(mov.isColorAJugar());
							FuncionesVisualesTablero.setVerCasillas(true);
						}
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					finally {
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
							if (client != null && !client.isClosed())
								client.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Cierre de sockets al salir
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
