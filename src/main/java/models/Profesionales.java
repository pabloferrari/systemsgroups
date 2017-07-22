package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Profesionales {
	
			// Variables de instancia
			@Id
			private int id_profesional;
			private String Nombre_prof;
			private String CUIT_prof;
			private String Detalle_prof;
			private String Dire_prof;
			private String tel_prof;
			private String cel_prof;
			private String email_prof;
			
			// Métodos
			
			public Profesionales(){}
			
			public Profesionales(int id_prof, String nom_prof, String cuit_prof, String detalle_prof, String dire_prof, String tele_prof, String celu_prof, String ema_prof) {
				super();
				this.id_profesional = id_prof;
				this.Nombre_prof = nom_prof;
				this.CUIT_prof = cuit_prof;
				this.Detalle_prof = detalle_prof;
				this.Dire_prof = dire_prof;
				this.tel_prof = tele_prof;
				this.cel_prof = celu_prof;
				this.email_prof = ema_prof;
				
			}
			
			public Profesionales(String nom_prof, String cuit_prof, String detalle_prof, String dire_prof, String tele_prof, String celu_prof, String ema_prof) {
				super();
				this.Nombre_prof = nom_prof;
				this.CUIT_prof = cuit_prof;
				this.Detalle_prof = detalle_prof;
				this.Dire_prof = dire_prof;
				this.tel_prof = tele_prof;
				this.cel_prof = celu_prof;
				this.email_prof = ema_prof;
							
			}

			public int getId_profesional() {
				return id_profesional;
			}

			public void setId_profesional(int id_profesional) {
				this.id_profesional = id_profesional;
			}

			public String getNombre_prof() {
				return Nombre_prof;
			}

			public void setNombre_prof(String nombre_prof) {
				Nombre_prof = nombre_prof;
			}

			public String getCUIT_prof() {
				return CUIT_prof;
			}

			public void setCUIT_prof(String cUIT_prof) {
				CUIT_prof = cUIT_prof;
			}

			public String getDetalle_prof() {
				return Detalle_prof;
			}

			public void setDetalle_prof(String detalle_prof) {
				Detalle_prof = detalle_prof;
			}

			public String getDire_prof() {
				return Dire_prof;
			}

			public void setDire_prof(String dire_prof) {
				Dire_prof = dire_prof;
			}

			public String getTel_prof() {
				return tel_prof;
			}

			public void setTel_prof(String tel_prof) {
				this.tel_prof = tel_prof;
			}

			public String getCel_prof() {
				return cel_prof;
			}

			public void setCel_prof(String cel_prof) {
				this.cel_prof = cel_prof;
			}

			public String getEmail_prof() {
				return email_prof;
			}

			public void setEmail_prof(String email_prof) {
				this.email_prof = email_prof;
			}

			
		
}
