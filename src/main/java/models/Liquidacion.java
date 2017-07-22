package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Liquidacion {
	
			// Variables de instancia
			@Id
			int id;
			private int identificador;
			private Date Periodo;
			private String Edificio;
			private String TipoGasto;
			private int NroGasto;
			private String Detalle;
			private double GastosA;
			private double GastosB;
			private double GastosC;
			private double Total;
			private int idGasto;
			
			// Métodos
			
			public Liquidacion(){}
			
			public Liquidacion(int id, int identi, Date periodo, String edificio, String tipogasto, int nrogasto, String detalle, double gastosA, double gastosB, double gastosC, double total, int idgasto){
				super();
				this.id = id;
				this.identificador = identi;
				this.Periodo = periodo;
				this.Edificio = edificio;
				this.TipoGasto = tipogasto;
				this.NroGasto = nrogasto;
				this.Detalle = detalle;
				this.GastosA = gastosA;
				this.GastosB = gastosB;
				this.GastosC = gastosC;
				this.Total = total;
				this.idGasto = idgasto;
			}
			
			public Liquidacion(int identi, Date periodo, String edificio, String tipogasto, int nrogasto, String detalle, double gastosA, double gastosB, double gastosC, double total, int idgasto){
				super();
				this.identificador = identi;
				this.Periodo = periodo;
				this.Edificio = edificio;
				this.TipoGasto = tipogasto;
				this.NroGasto = nrogasto;
				this.Detalle = detalle;
				this.GastosA = gastosA;
				this.GastosB = gastosB;
				this.GastosC = gastosC;
				this.Total = total;
				this.idGasto = idgasto;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public Date getPeriodo() {
				return Periodo;
			}

			public void setPeriodo(Date periodo) {
				Periodo = periodo;
			}

			public String getEdificio() {
				return Edificio;
			}

			public void setEdificio(String edificio) {
				Edificio = edificio;
			}

			public String getTipoGasto() {
				return TipoGasto;
			}

			public void setTipoGasto(String tipoGasto) {
				TipoGasto = tipoGasto;
			}

			public int getNroGasto() {
				return NroGasto;
			}

			public void setNroGasto(int nroGasto) {
				NroGasto = nroGasto;
			}

			public String getDetalle() {
				return Detalle;
			}

			public void setDetalle(String detalle) {
				Detalle = detalle;
			}

			public double getGastosA() {
				return GastosA;
			}

			public void setGastosA(double gastosA) {
				GastosA = gastosA;
			}

			public double getGastosB() {
				return GastosB;
			}

			public void setGastosB(double gastosB) {
				GastosB = gastosB;
			}

			public double getGastosC() {
				return GastosC;
			}

			public void setGastosC(double gastosC) {
				GastosC = gastosC;
			}

			public double getTotal() {
				return Total;
			}

			public void setTotal(double total) {
				Total = total;
			}

			public int getIdGasto() {
				return idGasto;
			}

			public void setIdGasto(int idGasto) {
				this.idGasto = idGasto;
			}

			public int getIdentificador() {
				return identificador;
			}

			public void setIdentificador(int identificador) {
				this.identificador = identificador;
			}
			
}
