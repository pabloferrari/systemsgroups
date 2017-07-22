package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuarios {
	
			// Variables de instancia
			@Id
			int id_usuario;
			private String nom_usuario;
			private String ape_usuario;
			private String dni_usuario;
			private String dire_usuario;
			private String tel_usuario;
			private String email_usuario;
			private String username;
			private String contrasena;
			private String cate_usuario;
			private String token;
			
			// Métodos
			
			public Usuarios(){}
			
			public Usuarios(int id_user, String us_nom, String us_ape, String us_dni, String us_dir, String us_tel, String us_ema, String us_user, String us_contra, String us_cate) {
				super();
				this.id_usuario = id_user;
				this.nom_usuario = us_nom;
				this.ape_usuario = us_ape;
				this.dni_usuario = us_dni;
				this.dire_usuario = us_dir;
				this.tel_usuario = us_tel;
				this.email_usuario = us_ema;
				this.username = us_user;
				this.contrasena = us_contra;
				this.cate_usuario = us_cate;
				
			}
			
			public Usuarios(String us_nom, String us_ape, String us_dni, String us_dir, String us_tel, String us_ema, String us_user, String us_contra, String us_cate) {
				super();
				this.nom_usuario = us_nom;
				this.ape_usuario = us_ape;
				this.dni_usuario = us_dni;
				this.dire_usuario = us_dir;
				this.tel_usuario = us_tel;
				this.email_usuario = us_ema;
				this.username = us_user;
				this.contrasena = us_contra;
				this.cate_usuario = us_cate;
				
			}

			public int getId_usuario() {
				return id_usuario;
			}

			public void setId_usuario(int id_usuario) {
				this.id_usuario = id_usuario;
			}

			public String getNom_usuario() {
				return nom_usuario;
			}

			public void setNom_usuario(String nom_usuario) {
				this.nom_usuario = nom_usuario;
			}

			public String getApe_usuario() {
				return ape_usuario;
			}

			public void setApe_usuario(String ape_usuario) {
				this.ape_usuario = ape_usuario;
			}

			public String getDni_usuario() {
				return dni_usuario;
			}

			public void setDni_usuario(String dni_usuario) {
				this.dni_usuario = dni_usuario;
			}

			public String getDire_usuario() {
				return dire_usuario;
			}

			public void setDire_usuario(String dire_usuario) {
				this.dire_usuario = dire_usuario;
			}

			public String getTel_usuario() {
				return tel_usuario;
			}

			public void setTel_usuario(String tel_usuario) {
				this.tel_usuario = tel_usuario;
			}

			public String getEmail_usuario() {
				return email_usuario;
			}

			public void setEmail_usuario(String email_usuario) {
				this.email_usuario = email_usuario;
			}

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getContrasena() {
				return contrasena;
			}

			public void setContrasena(String contrasena) {
				this.contrasena = contrasena;
			}

			public String getCate_usuario() {
				return cate_usuario;
			}

			public void setCate_usuario(String cate_usuario) {
				this.cate_usuario = cate_usuario;
			}

			public String getToken() {
				return token;
			}

			public void setToken(String token) {
				this.token = token;
			}
			
			
		
}
