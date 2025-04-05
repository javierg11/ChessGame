


//El motivo por el que el campo sigue apareciendo como "apellidos" 
//en el JSON generado es porque Jackson utiliza los nombres de los métodos getter y setter 
//para determinar los nombres de las claves en el JSON, no directamente los nombres de las variables 
//(campos) privadas de la clase.
//
//En tu clase Persona, aunque el campo privado se llama apellido222s, 
//el método getter correspondiente se llama getApellidos(). 
//Por convención, Jackson toma el nombre del método getter, elimina el prefijo get, 
//y convierte la primera letra del resto del nombre a minúscula (Apellidos → apellidos). 
//Por lo tanto, la clave generada en el JSON es "apellidos".





public class Persona {
    private String nombre;
    private String apellido222s;
    private int edad;

    // Constructores, getters y setters
    public Persona(String nombre, String apellidos, int edad) {
    	this.nombre=nombre;
    	this.apellido222s=apellidos;
    	this.edad=edad;
    }
    
    public Persona() {}
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellido222s;
    }

    public void setApellidos(String apellidos) {
        this.apellido222s = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
