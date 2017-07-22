package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Empleados{
	
			// Variables de instancia
			@Id
			@GeneratedValue(strategy=GenerationType.AUTO)
			int id_empleado;
			
			private String Nombre_empleado;
			private String Apellido_empleado;
			private String Documento_empleado;
			private String CUIL_empleado;
			private String Tel_empleado;
			private String Cel_empleado;
			private String email_empleado;
			private String Horario_empleado;
			private String id_edificio;
						
			
			// Métodos
			
			public Empleados(){}
			
			public Empleados(int emp_id, String emp_Nom, String emp_Ape, String emp_Doc, String emp_CUIL, String emp_Tel, String emp_Cel, String emp_Ema, String emp_Hor, String emp_IdCons) {
				super();
				this.id_empleado = emp_id;
				this.Nombre_empleado = emp_Nom;
				this.Apellido_empleado = emp_Ape;
				this.Documento_empleado = emp_Doc;
				this.CUIL_empleado = emp_CUIL;
				this.Tel_empleado = emp_Tel;
				this.Cel_empleado = emp_Cel;
				this.email_empleado = emp_Ema;
				this.Horario_empleado = emp_Hor;
				this.id_edificio = emp_IdCons;

			}
			
			public Empleados(String emp_Nom, String emp_Ape, String emp_Doc, String emp_CUIL, String emp_Tel, String emp_Cel, String emp_Ema, String emp_Hor, String emp_IdCons) {
				super();
				this.Nombre_empleado = emp_Nom;
				this.Apellido_empleado = emp_Ape;
				this.Documento_empleado = emp_Doc;
				this.CUIL_empleado = emp_CUIL;
				this.Tel_empleado = emp_Tel;
				this.Cel_empleado = emp_Cel;
				this.email_empleado = emp_Ema;
				this.Horario_empleado = emp_Hor;
				this.id_edificio = emp_IdCons;

			}

			public int getId_empleado() {
				return id_empleado;
			}

			public void setId_empleado(int id_empleado) {
				this.id_empleado = id_empleado;
			}

			public String getNombre_empleado() {
				return Nombre_empleado;
			}

			public void setNombre_empleado(String nombre_empleado) {
				Nombre_empleado = nombre_empleado;
			}

			public String getApellido_empleado() {
				return Apellido_empleado;
			}

			public void setApellido_empleado(String apellido_empleado) {
				Apellido_empleado = apellido_empleado;
			}

			public String getDocumento_empleado() {
				return Documento_empleado;
			}

			public void setDocumento_empleado(String documento_empleado) {
				Documento_empleado = documento_empleado;
			}

			public String getCUIL_empleado() {
				return CUIL_empleado;
			}

			public void setCUIL_empleado(String cUIL_empleado) {
				CUIL_empleado = cUIL_empleado;
			}

			public String getTel_empleado() {
				return Tel_empleado;
			}

			public void setTel_empleado(String tel_empleado) {
				Tel_empleado = tel_empleado;
			}

			public String getCel_empleado() {
				return Cel_empleado;
			}

			public void setCel_empleado(String cel_empleado) {
				Cel_empleado = cel_empleado;
			}

			public String getEmail_empleado() {
				return email_empleado;
			}

			public void setEmail_empleado(String email_empleado) {
				this.email_empleado = email_empleado;
			}

			public String getHorario_empleado() {
				return Horario_empleado;
			}

			public void setHorario_empleado(String horario_empleado) {
				Horario_empleado = horario_empleado;
			}

			public String getId_edificio() {
				return id_edificio;
			}

			public void setId_edificio(String id_edificio) {
				this.id_edificio = id_edificio;
			}
			
			
						
}
