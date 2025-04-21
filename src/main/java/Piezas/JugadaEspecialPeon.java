package Piezas;


import java.util.HashMap;

import javax.swing.JButton;

public class JugadaEspecialPeon {
	public String coronarPeon(int fila, String ficha) {
		if (fila==0 && ficha.equals("wP")) {
			return "wD";
		}
		else if (fila==7 && ficha.equals("bP")){
			return "bD";
		}
		else
			return ficha;
		}
	public boolean comerAlPaso(int filaOrigen, int filaDestino, String ficha, JButton [][] casillas, int columna,HashMap<Integer, String> jugadas) {
		
		
        Integer claveMasNueva = jugadas.size();
        String valorMasNuevo =jugadas.get(claveMasNueva);

        // Regex para saber si blancas o negras han hecho un movimiento doble con el peon
        String regexBlacas = "wP-6\\d-4\\d";
        String regexNegras = "bP-1\\d-3\\d";

        //Este comprueba si el regex es correcto, y si hay un peon de color contrario al aldo del peon (esto se hace para ver si es posible ejecutar 
        //el comer al paso)
        if (valorMasNuevo.matches(regexBlacas) && (casillas[filaDestino][columna-1].getText().equals("bP") || 
				casillas[filaDestino][columna+1].getText().equals("bP"))
        ||
        valorMasNuevo.matches(regexNegras) && (casillas[filaDestino][columna+1].getText().equals("wP") || 
				casillas[filaDestino][columna-1].getText().equals("wP"))
        
        ){
        	//Este if sirve para saber a que color se la hace el comer al paso, creando un peon temporal que si no se come en ese turno desaparace
        	//Si al que le hacen comer al paso es un peon blanco se crea un peon temporal negro (porque asi se controla que solo se lo 
        	//pueda comer un peon negro)
            if (ficha.equals("wP"))
            	casillas[filaOrigen-1][columna].setText("bPa");
            else
            	casillas[filaOrigen+1][columna].setText("wPa");
        } 
        
        else {
            System.out.println("El último valor NO coincide: " + valorMasNuevo);
            for (int i =0;i<8;i++) {
            	for (int j =0;j<8;j++) {
            		//Compruaba pasado un movimiento desde que se ha activado "comer al paso" que si no se ha comido el peon borra 
            		//la posibilidad de comer al paso
            		if (casillas[i][j].getText().equals("waP") || casillas[i][j].getText().equals("baP"))
            			casillas[i][j].setText("");
            	}
            }
        }
		

		return false;

	}
}
