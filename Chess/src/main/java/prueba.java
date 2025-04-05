import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class prueba {
    public static void main(String[] args) throws IOException {
    	
    	File prueba = new File("persona.json");
        String destinatario =  "javiergutierrezezquerro@gmail.com"; //A quien le quieres escribir.
        String asunto = "Correo de prueba enviado desde Java";
        String cuerpo = "Hello..."+prueba;

        //enviarConGMail(destinatario, asunto, cuerpo);

        
        
        
        ObjectMapper mapper = new ObjectMapper();
    	//Lee el archivo
        try {
            // Lee el archivo JSON y lo convierte en un objeto Persona
            Persona persona = mapper.readValue(prueba, Persona.class);

            // Imprime los datos de la persona
            System.out.println("Nombre: " + persona.getNombre());
            System.out.println("Apellidos: " + persona.getApellidos());
            System.out.println("Edad: " + persona.getEdad());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Fin leer archivo
        
        
        //Crear JSON
        Persona p = new Persona("juan", "gomez", 20);
        try {
            File fichero = new File("persona2.json");
            fichero.createNewFile();
            mapper.writeValue(fichero, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Fin de crear JSON
    }
      
    
    private static void enviarConGMail(String destinatario, String asunto, String cuerpo) throws IOException {
        //La dirección de correo de envío
        String remitente = "javiergutierrezezquerro@gmail.com";
        //La clave de aplicación obtenida según se explica en este artículo:
        String claveemail = "ccmd hskg pywz yslt";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", claveemail);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        MimeBodyPart mbp = new MimeBodyPart();
        MimeBodyPart mbp2 = new MimeBodyPart();

	    // attach the file to the message
	    
        try {
        	mbp2.attachFile("persona.json");
    	    Multipart mp = new MimeMultipart();
    	    
    	    
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            mbp.setText(cuerpo);
            mp.addBodyPart(mbp);
    	    mp.addBodyPart(mbp2);

    	    // add the Multipart to the message
    	    message.setContent(mp);

    	    // set the Date: header
    	    Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, claveemail);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
      }

}
