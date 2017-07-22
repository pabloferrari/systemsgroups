package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Proveedores {
	
			// Variables de instancia
			@Id
			private int id_proveedor;
			private String Nombre_prov;
			private String CUIT_prov;
			private String Tipo_prov;
			private String Detalle_prov;
			private String Dire_prov;
			private String tel_prov;
			private String cel_prov;
			private String email_prov;
			
			// Métodos
			
			public Proveedores(){}
			
			public Proveedores(int id_prov, String nom_prov, String cuit_prov, String tipo_prov, String detalle_prov, String dire_prov, String tele_prov, String celu_prov, String ema_prov) {
				super();
				this.id_proveedor = id_prov;
				this.Nombre_prov = nom_prov;
				this.CUIT_prov = cuit_prov;
				this.Tipo_prov = tipo_prov;
				this.Detalle_prov = detalle_prov;
				this.Dire_prov = dire_prov;
				this.tel_prov = tele_prov;
				this.cel_prov = celu_prov;
				this.email_prov = ema_prov;
				
			}
			
			public Proveedores(String nom_prov, String cuit_prov, String tipo_prov, String detalle_prov, String dire_prov, String tele_prov, String celu_prov, String ema_prov) {
				super();
				this.Nombre_prov = nom_prov;
				this.CUIT_prov = cuit_prov;
				this.Tipo_prov = tipo_prov;
				this.Detalle_prov = detalle_prov;
				this.Dire_prov = dire_prov;
				this.tel_prov = tele_prov;
				this.cel_prov = celu_prov;
				this.email_prov = ema_prov;
				
			}

			public int getId_proveedor() {
				return id_proveedor;
			}

			public void setId_proveedor(int id_proveedor) {
				this.id_proveedor = id_proveedor;
			}

			public String getNombre_prov() {
				return Nombre_prov;
			}

			public void setNombre_prov(String nombre_prov) {
				Nombre_prov = nombre_prov;
			}

			public String getCUIT_prov() {
				return CUIT_prov;
			}

			public void setCUIT_prov(String cUIT_prov) {
				CUIT_prov = cUIT_prov;
			}

			public String getTipo_prov() {
				return Tipo_prov;
			}

			public void setTipo_prov(String tipo_prov) {
				Tipo_prov = tipo_prov;
			}

			public String getDetalle_prov() {
				return Detalle_prov;
			}

			public void setDetalle_prov(String detalle_prov) {
				Detalle_prov = detalle_prov;
			}

			public String getDire_prov() {
				return Dire_prov;
			}

			public void setDire_prov(String dire_prov) {
				Dire_prov = dire_prov;
			}

			public String getTel_prov() {
				return tel_prov;
			}

			public void setTel_prov(String tel_prov) {
				this.tel_prov = tel_prov;
			}

			public String getCel_prov() {
				return cel_prov;
			}

			public void setCel_prov(String cel_prov) {
				this.cel_prov = cel_prov;
			}

			public String getEmail_prov() {
				return email_prov;
			}

			public void setEmail_prov(String email_prov) {
				this.email_prov = email_prov;
			}

			
			

			
		
}
