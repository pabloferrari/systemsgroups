package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LiqSueldos {
	
			// Variables de instancia
			@Id
			int id_sueldo_empleado;
			private String Empleado;
			private String Edificio;
			// SALDOS POSITIVOS
			private double sueldo_basico;
			private double presentismo;
			private double antiguedad;
			private double sac;
			private double vacaciones_ng;
			private double horas_extras;
			private double Total_bruto;
			// SALDOS NEGATIVOS
			private double jubilacion; // 11 %
			private double obra_social; // 3 %
			private double ART;
			private double impuesto_ganancias;
			private double AFIP;
			private double Suterh;
			private double Fateryh;
			private double Seracarh;
			private double Otros_Items;
			// SUBTOTAL Y TOTAL
			private double Total_neto;
			private java.util.Date fecha_liq;
			private String Liquidado;
			
			// Métodos
			
			public LiqSueldos(){}
			
			public LiqSueldos(int su_id, String su_emple, String su_cons, double su_basico, double su_pres, double su_ant, double su_sac, double su_vac, double su_HoEx, double su_TotB, double su_jub, double su_ObSoc, double su_art, double su_ImpGan, double su_Afip, double su_Sut, double su_Fat, double su_Ser, double su_otI, double su_TotN, java.util.Date su_fecha, String su_liq){
				super();
				this.id_sueldo_empleado = su_id;
				this.Empleado = su_emple;
				this.Edificio = su_cons;
				this.sueldo_basico = su_basico;
				this.presentismo = su_pres;
				this.antiguedad = su_ant;
				this.sac = su_sac;
				this.vacaciones_ng = su_vac;
				this.horas_extras = su_HoEx;
				this.Total_bruto = su_TotB;
				this.jubilacion = su_jub;
				this.obra_social = su_ObSoc;
				this.ART = su_art;
				this.impuesto_ganancias = su_ImpGan;
				this.AFIP = su_Afip;
				this.Suterh = su_Sut;
				this.Fateryh = su_Fat;
				this.Seracarh = su_Ser;
				this.Otros_Items = su_otI;
				this.Total_neto = su_TotN;
				this.fecha_liq = su_fecha;
				this.Liquidado = su_liq;
			
			}
			
			public LiqSueldos(String su_emp, String su_cons, double su_basico, double su_pres, double su_ant, double su_sac, double su_vac, double su_HoEx, double su_TotB, double su_jub, double su_ObSoc, double su_art, double su_ImpGan, double su_Afip, double su_Sut, double su_Fat, double su_Ser, double su_otI, double su_TotN, java.util.Date su_fecha, String su_liq){
				super();
				this.Empleado = su_emp;
				this.Edificio = su_cons;
				this.sueldo_basico = su_basico;
				this.presentismo = su_pres;
				this.antiguedad = su_ant;
				this.sac = su_sac;
				this.vacaciones_ng = su_vac;
				this.horas_extras = su_HoEx;
				this.Total_bruto = su_TotB;
				this.jubilacion = su_jub;
				this.obra_social = su_ObSoc;
				this.ART = su_art;
				this.impuesto_ganancias = su_ImpGan;
				this.AFIP = su_Afip;
				this.Suterh = su_Sut;
				this.Fateryh = su_Fat;
				this.Seracarh = su_Ser;
				this.Otros_Items = su_otI;
				this.Total_neto = su_TotN;
				this.fecha_liq = su_fecha;
				this.Liquidado = su_liq;
			
			}

			public int getId_sueldo_empleado() {
				return id_sueldo_empleado;
			}

			public void setId_sueldo_empleado(int id_sueldo_empleado) {
				this.id_sueldo_empleado = id_sueldo_empleado;
			}

			public String getEmpleado() {
				return Empleado;
			}

			public void setEmpleado(String empleado) {
				Empleado = empleado;
			}

			public String getEdificio() {
				return Edificio;
			}

			public void setEdificio(String edificio) {
				Edificio = edificio;
			}

			public double getSueldo_basico() {
				return sueldo_basico;
			}

			public void setSueldo_basico(double sueldo_basico) {
				this.sueldo_basico = sueldo_basico;
			}

			public double getPresentismo() {
				return presentismo;
			}

			public void setPresentismo(double presentismo) {
				this.presentismo = presentismo;
			}

			public double getAntiguedad() {
				return antiguedad;
			}

			public void setAntiguedad(double antiguedad) {
				this.antiguedad = antiguedad;
			}

			public double getSac() {
				return sac;
			}

			public void setSac(double sac) {
				this.sac = sac;
			}

			public double getVacaciones_ng() {
				return vacaciones_ng;
			}

			public void setVacaciones_ng(double vacaciones_ng) {
				this.vacaciones_ng = vacaciones_ng;
			}

			public double getHoras_extras() {
				return horas_extras;
			}

			public void setHoras_extras(double horas_extras) {
				this.horas_extras = horas_extras;
			}

			public double getTotal_bruto() {
				return Total_bruto;
			}

			public void setTotal_bruto(double total_bruto) {
				Total_bruto = total_bruto;
			}

			public double getJubilacion() {
				return jubilacion;
			}

			public void setJubilacion(double jubilacion) {
				this.jubilacion = jubilacion;
			}

			public double getObra_social() {
				return obra_social;
			}

			public void setObra_social(double obra_social) {
				this.obra_social = obra_social;
			}

			public double getART() {
				return ART;
			}

			public void setART(double aRT) {
				ART = aRT;
			}

			public double getImpuesto_ganancias() {
				return impuesto_ganancias;
			}

			public void setImpuesto_ganancias(double impuesto_ganancias) {
				this.impuesto_ganancias = impuesto_ganancias;
			}

			public double getAFIP() {
				return AFIP;
			}

			public void setAFIP(double aFIP) {
				AFIP = aFIP;
			}

			public double getSuterh() {
				return Suterh;
			}

			public void setSuterh(double suterh) {
				Suterh = suterh;
			}

			public double getFateryh() {
				return Fateryh;
			}

			public void setFateryh(double fateryh) {
				Fateryh = fateryh;
			}

			public double getSeracarh() {
				return Seracarh;
			}

			public void setSeracarh(double seracarh) {
				Seracarh = seracarh;
			}

			public double getOtros_Items() {
				return Otros_Items;
			}

			public void setOtros_Items(double otros_Items) {
				Otros_Items = otros_Items;
			}

			public double getTotal_neto() {
				return Total_neto;
			}

			public void setTotal_neto(double total_neto) {
				Total_neto = total_neto;
			}

			public String getFecha_liq() {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
				String reportDate = df.format(fecha_liq);
				String[] corto = reportDate.split(" ");
				String fecha = corto[0];
				String[] fechadiv = fecha.split("/");
				String mes = fechadiv[1];
				switch(mes){
				case "01":
					mes = "Enero";
					break;
				case "02":
					mes = "Febrero";
					break;
				case "03":
					mes = "Marzo";
					break;
				case "04":
					mes = "Abril";
					break;
				case "05":
					mes = "Mayo";
					break;
				case "06":
					mes = "Junio";
					break;
				case "07":
					mes = "Julio";
					break;
				case "08":
					mes = "Agosto";
					break;
				case "09":
					mes = "Septiembre";
					break;
				case "10":
					mes = "Octubre";
					break;
				case "11":
					mes = "Noviembre";
					break;
				case "12":
					mes = "Diciembre";
					break;
				}
				
				fecha = mes+" "+fechadiv[2];
				return fecha;
			}

			public void setFecha_liq(java.util.Date fecha_liq) {
				this.fecha_liq = fecha_liq;
			}

			public String getLiquidado() {
				return Liquidado;
			}

			public void setLiquidado(String liquidado) {
				Liquidado = liquidado;
			}


			
			
}