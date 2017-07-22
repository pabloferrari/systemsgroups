package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Caja {
	
			// Variables de instancia
			@Id
			int id;
			private Date Fecha;
			private String Consorcio;
			private double Importe;
			private double Saldo_Anterior;
			private double Saldo_Final;
			private String Consepto;
			private String Unidad_Funcional;
			private String Pagador;
			private String Usuario;
			
			// Métodos
			
			public Caja(){}
			
			public Caja(int id, Date fecha, String edificio, double importe, double saldo_a, double saldo_f, String consep, String unid, String pagador, String usuario){
				super();
				this.id = id;
				this.Fecha = fecha;
				this.Consorcio = edificio;
				this.Importe = importe;
				this.Saldo_Anterior = saldo_a;
				this.Saldo_Final = saldo_f;
				this.Consepto = consep;
				this.Unidad_Funcional = unid;
				this.Pagador = pagador;
				this.Usuario = usuario;
			}
			
			public Caja(Date fecha, String edificio, double importe, double saldo_a, double saldo_f, String consep, String unid, String pagador, String usuario){
				super();
				this.Fecha = fecha;
				this.Consorcio = edificio;
				this.Importe = importe;
				this.Saldo_Anterior = saldo_a;
				this.Saldo_Final = saldo_f;
				this.Consepto = consep;
				this.Unidad_Funcional = unid;
				this.Pagador = pagador;
				this.Usuario = usuario;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getFecha() {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
				String reportDate = df.format(Fecha);
				String[] corto = reportDate.split(" ");
				String fecha = corto[0];
				
				return fecha;
			}

			public void setFecha(Date fecha) {
				Fecha = fecha;
			}

			public String getConsorcio() {
				return Consorcio;
			}

			public void setConsorcio(String consorcio) {
				Consorcio = consorcio;
			}

			public double getImporte() {
				return Importe;
			}

			public void setImporte(double importe) {
				Importe = importe;
			}

			public double getSaldo_Anterior() {
				return Saldo_Anterior;
			}

			public void setSaldo_Anterior(double saldo_Anterior) {
				Saldo_Anterior = saldo_Anterior;
			}

			public double getSaldo_Final() {
				return Saldo_Final;
			}

			public void setSaldo_Final(double saldo_Final) {
				Saldo_Final = saldo_Final;
			}

			public String getConsepto() {
				return Consepto;
			}

			public void setConsepto(String consepto) {
				Consepto = consepto;
			}

			public String getUnidad_Funcional() {
				return Unidad_Funcional;
			}

			public void setUnidad_Funcional(String unidad_Funcional) {
				Unidad_Funcional = unidad_Funcional;
			}

			public String getPagador() {
				return Pagador;
			}

			public void setPagador(String pagador) {
				Pagador = pagador;
			}

			public String getUsuario() {
				return Usuario;
			}

			public void setUsuario(String usuario) {
				Usuario = usuario;
			}
				
			
			
}
