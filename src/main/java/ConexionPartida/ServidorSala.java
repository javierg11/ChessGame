package ConexionPartida;

import java.awt.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Partida.FinPartida;
import Tablero.FuncionesVisualesTablero;
import Tablero.MetodosMoverPiezas;
import Tablero.TableroAjedrez;
import interfazGrafica.JugarEnLAN;

public class ServidorSala {
	private static final int PUERTO_BROADCAST = 8888;
	private int incrementoTiempo;
	private static Socket socket = null;
	private static ServerSocket serverSocket = null;
	private static String password = "";
	private float tiempo;
	public static boolean jugando = true;
	private static BufferedReader in = null;
	private static BufferedWriter out = null;
	private static Socket client = null;
	String nombreSala = null;
	private static SalaInfo info = null;
	public static Movimientos mov = new Movimientos();
	private static DatagramSocket broadcastSocket = null;

	public void crearSala(JPanel Panel, JFrame frame) {
		try {

			Icon icono = new ImageIcon(FinPartida.class.getResource("/imagesPiezas/wP.png"));

			// 1. Nombre de la sala
			nombreSala = (String) JOptionPane.showInputDialog(null, "Nombre de la sala:", "Crear sala",
					JOptionPane.QUESTION_MESSAGE, icono, null, "");

			if (nombreSala == null) { // Si el usuario cancela, puedes manejarlo aquí
				nombreSala = "";
			}

			// 2. Tiempo de la partida
			boolean tiempoValido = false;
			while (!tiempoValido) {
				String tiempoStr = (String) JOptionPane.showInputDialog(null,
						"Pon el tiempo de la partida (debe ser superior a 0):", "Tiempo de la partida",
						JOptionPane.QUESTION_MESSAGE, icono, null, "");
				try {
					tiempo = Float.parseFloat(tiempoStr);
					if (tiempo > 0) {
						tiempoValido = true;
					} else {
						JOptionPane.showMessageDialog(null, "El tiempo debe ser superior a 0. Inténtalo de nuevo.",
								"Error", JOptionPane.ERROR_MESSAGE, icono);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido para el tiempo.",
							"Error", JOptionPane.ERROR_MESSAGE, icono);
				}
			}

			// 3. Incremento de la partida
			boolean incrementoValido = false;
			while (!incrementoValido) {
				String incrementoStr = (String) JOptionPane.showInputDialog(null,
						"Pon el incremento de la partida (debe ser un número positivo):", "Incremento de la partida",
						JOptionPane.QUESTION_MESSAGE, icono, null, "");
				try {
					incrementoTiempo = Integer.parseInt(incrementoStr);
					if (incrementoTiempo >= 0) {
						incrementoValido = true;
					} else {
						JOptionPane.showMessageDialog(null,
								"El incremento debe ser un número positivo. Inténtalo de nuevo.", "Error",
								JOptionPane.ERROR_MESSAGE, icono);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido para el incremento.",
							"Error", JOptionPane.ERROR_MESSAGE, icono);
				}
			}

			int opcionPassword = JOptionPane.showConfirmDialog(null, "¿Requiere contraseña?", "Contraseña",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, // o el tipo de mensaje que quieras
					icono // aquí pones tu icono personalizado
			);
			boolean requierePassword = (opcionPassword == JOptionPane.YES_OPTION);

			if (requierePassword) {
				password = JOptionPane.showInputDialog(null, "Contraseña:");
			}
			// Puedes mostrar un resumen de los datos
			String resumen = "Sala: " + nombreSala + "\nContraseña: " + (requierePassword ? password : "No")
					+ "\nColor: Blanco\nTiempo: " + tiempo + "\nIncremento: " + incrementoTiempo;
			JOptionPane.showMessageDialog(null, resumen);

			// --- Aquí añadimos el spinner al panel ---
			SwingUtilities.invokeLater(() -> {
				Panel.removeAll();

				// Panel auxiliar para la esquina superior izquierda
				JPanel esquinaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
				esquinaPanel.setOpaque(false); // Para que sea transparente si quieres
				JugarEnLAN.esquinaButton.setVisible(false); // Asegúrate de que esté oculto al inicio
				esquinaPanel.add(JugarEnLAN.esquinaButton);

				// Panel principal con BoxLayout en Y_AXIS
				Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
				Panel.add(esquinaPanel);

				JLabel esperandoLabel = new JLabel("Esperando al oponente...");
				esperandoLabel.setFont(new Font("Arial", Font.BOLD, 24));
				esperandoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

				CircularSpinner spinner = new CircularSpinner(80);
				spinner.setAlignmentX(Component.CENTER_ALIGNMENT);

				Panel.add(Box.createRigidArea(new Dimension(0, 30)));
				Panel.add(esperandoLabel);
				Panel.add(Box.createRigidArea(new Dimension(0, 20)));
				Panel.add(spinner);
				Panel.add(Box.createVerticalGlue());

				Panel.revalidate();
				Panel.repaint();

				// Timer para hacer visible el botón después de 2 segundos (2,000 ms)
				Timer timer = new Timer(2000, e -> {
					JugarEnLAN.esquinaButton.setVisible(true);
					Panel.revalidate();
					Panel.repaint();
				});
				timer.setRepeats(false); // Solo se ejecuta una vez
				timer.start();
			});

			// Elegir un puerto TCP libre en el rango 10000-10999
			int puertoTCP = 0;
			while (jugando) {
				puertoTCP = 10000 + (int) (Math.random() * 1000);
				try {
					serverSocket = new ServerSocket(puertoTCP);
					break;
				} catch (IOException e) {
					// Puerto en uso, intenta otro
				}
			}

			info = new SalaInfo(nombreSala, puertoTCP, requierePassword, true, tiempo, incrementoTiempo);

			// Hilo para responder broadcasts
			new Thread(() -> {

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
			while (jugando) {
				client = serverSocket.accept();
				new Thread(() -> {
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
						SalaInfo.setColor(true);
						Movimientos.setColorAJugar(true);
						TableroAjedrez.crearTipoTablero(true, (int) tiempo, incrementoTiempo,
								"Servidor de la sala: " + nombreSala);

						ControlDeJugadas controlDeJugadas = new ControlDeJugadas();

//	                	    // Es tu turno
						while (jugando) {
							try {
								Thread.sleep(100); // Espera 100 milisegundos en cada iteración
							} catch (InterruptedException e) {

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
								MetodosMoverPiezas.sensorDeTurnosDosJugadores = false; // Resetea el flag SOLO después
																						// de enviar
							}

							FuncionesVisualesTablero.setVerCasillas(true);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
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
			try {
				if (client != null && !client.isClosed())
					client.close();
			} catch (IOException b) {
				b.printStackTrace();
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
				try {
					if (out != null)
						out.close();
				} catch (IOException t) {
					t.printStackTrace();
				}
				try {
					if (in != null)
						in.close();
				} catch (IOException n) {
					n.printStackTrace();
				}
			}
		}
	}

	public static void algo() {
		try {
			if (!(out == null)) {
				out.write("FIN\n");
				out.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (broadcastSocket != null && !broadcastSocket.isClosed()) {
			broadcastSocket.close();
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
			try {
				if (out != null)
					out.close();
			} catch (IOException t) {
				t.printStackTrace();
			}
			try {
				if (in != null)
					in.close();
			} catch (IOException n) {
				n.printStackTrace();
			}
			if (!(info == null)) {
				info = new SalaInfo(null, 0, false, false, 0, 0);
				info = null;
				SalaInfo.setColor(null);
			}
		}
	}

}
