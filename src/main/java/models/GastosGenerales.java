package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class GastosGenerales {

			// Variables de instancia
			@Id
			int id;
			private Date Periodo;
			private String Edificio;
			private String Tipo;
			private int idSubtipo;
			private String SubTipo;
			private String Unidad;
			private String QQG;
			private String CUIT;
			private String Nro_Factura;
			private String Detalle;
			private double GastosA;
			private double GastosB;
			private double GastosC;
			private double GastosExtra;
			private double Total;
			private String Liquidado;

			// Métodos

			public GastosGenerales(){}

			public GastosGenerales(int id, Date periodo, String edificio, String tipo, int idsub, String subtipo, String unidad, String qgg, String cuit, String factura, String detalle, double gastosA, double gastosB, double gastosC, double gastosextra, double total, String liqui) {
				super();
				this.id = id;
				this.Periodo = periodo;
				this.Edificio = edificio;
				this.Tipo = tipo;
				this.idSubtipo = idsub;
				this.SubTipo = subtipo;
				this.Unidad = unidad;
				this.QQG = qgg;
				this.CUIT = cuit;
				this.Detalle = detalle;
				this.Nro_Factura = factura;
				this.GastosA = gastosA;
				this.GastosB = gastosB;
				this.GastosC = gastosC;
				this.GastosExtra = gastosextra;
				this.Total = total;
				this.Liquidado = liqui;
				
			}

			public GastosGenerales(Date periodo, String edificio, String tipo, int idsub, String subtipo, String unidad, String qgg, String cuit, String factura, String detalle, double gastosA, double gastosB, double gastosC, double gastosextra, double total, String liqui) {
				super();
				this.Periodo = periodo;
				this.Edificio = edificio;
				this.Tipo = tipo;
				this.idSubtipo = idsub;
				this.SubTipo = subtipo;
				this.Unidad = unidad;
				this.QQG = qgg;
				this.CUIT = cuit;
				this.Detalle = detalle;
				this.Nro_Factura = factura;
				this.GastosA = gastosA;
				this.GastosB = gastosB;
				this.GastosC = gastosC;
				this.GastosExtra = gastosextra;
				this.Total = total;
				this.Liquidado = liqui;
				
			}


			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getPeriodo() {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
				String reportDate = df.format(Periodo);
				String[] corto = reportDate.split(" ");
				String fecha = corto[0];
				
				return fecha;
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

			public String getTipo() {
				return Tipo;
			}

			public void setTipo(String tipo) {
				Tipo = tipo;
			}

			public String getSubTipo() {
				return SubTipo;
			}

			public void setSubTipo(String subTipo) {
				SubTipo = subTipo;
			}

			public String getUnidad() {
				return Unidad;
			}

			public void setUnidad(String unidad) {
				Unidad = unidad;
			}
			
			public String getQQG() {
				return QQG;
			}

			public void setQQG(String qqg) {
				QQG = qqg;
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

			public double getGastosExtra() {
				return GastosExtra;
			}

			public void setGastosExtra(double gastosExtra) {
				GastosExtra = gastosExtra;
			}

			public double getTotal() {
				return Total;
			}

			public void setTotal(double total) {
				Total = total;
			}

			public String getNro_Factura() {
				return Nro_Factura;
			}

			public void setNro_Factura(String nro_Factura) {
				Nro_Factura = nro_Factura;
			}

			public String getLiquidado() {
				return Liquidado;
			}

			public void setLiquidado(String liquidado) {
				Liquidado = liquidado;
			}

			public String getCUIT() {
				return CUIT;
			}

			public void setCUIT(String cUIT) {
				CUIT = cUIT;
			}

			public int getIdSubtipo() {
				return idSubtipo;
			}

			public void setIdSubtipo(int idSubtipo) {
				this.idSubtipo = idSubtipo;
			}

			

}
