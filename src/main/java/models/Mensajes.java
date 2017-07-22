package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Mensajes {
	
			// Variables de instancia
			@Id
			int id;
			private String nombre;
			private String email;
			private Date fecha;
			private String asunto;
			private String mensaje;
			private String contactado;
			
			public Mensajes(){}
			
			public Mensajes(int id, String nombre, String email, Date fecha, String asunto, String mensaje, String contac) {
				super();
				this.id = id;
				this.nombre = nombre;
				this.email = email;
				this.fecha = fecha;
				this.asunto = asunto;
				this.mensaje = mensaje;
				this.contactado = contac;
				
			}
			
			public Mensajes(String nombre, String email, Date fecha, String asunto, String mensaje, String contac) {
				super();
				this.nombre = nombre;
				this.email = email;
				this.fecha = fecha;
				this.asunto = asunto;
				this.mensaje = mensaje;
				this.contactado = contac;
				
			}
			
			// Métodos

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getNombre() {
				return nombre;
			}

			public void setNombre(String nombre) {
				this.nombre = nombre;
			}

			public String getEmail() {
				return email;
			}

			public void setEmail(String email) {
				this.email = email;
			}

			public String getAsunto() {
				return asunto;
			}

			public void setAsunto(String asunto) {
				this.asunto = asunto;
			}

			public String getMensaje() {
				return mensaje;
			}

			public void setMensaje(String mensaje) {
				this.mensaje = mensaje;
			}

			public Date getFecha() {
				return fecha;
			}

			public void setFecha(Date fecha) {
				this.fecha = fecha;
			}
			
			public String getContactado() {
				return contactado;
			}

			public void setContactado(String contactado) {
				this.contactado = contactado;
			}

			
			
			
}
