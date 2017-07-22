package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.Entity;
import javax.persistence.Id;

import controllers.Globals;

import java.util.Date;

@Entity
public class EstadoCuentasYProrrateo {

			// Variables de instancia
			@Id
			int id;
			private int identificador;
			private int UNI;
			private String Dto;
			private String Propietario;
			private double Saldo_Ant;
			private double Pago_Ant;
			private double Saldo_Pendiente;
			private double intereses;
			private double Porc_A;
			private double Total_A;
			private double Porc_B;
			private double Total_B;
			private double Porc_C;
			private double Total_C;
			private double Redondeo;
			private double Total;
			private String Edificio;
			private Date Periodo;

			// Métodos

			public EstadoCuentasYProrrateo(){}

			public EstadoCuentasYProrrateo(int id, int iden, int uni, String dto, String prop, double sAnt, double pAnt, double sPend, double inter, double porA, double totA, double porB, double totB, double porC, double totC, double red, double total, String edif, Date periodo){
				super();
				this.id = id;
				this.identificador = iden;
				this.UNI = uni;
				this.Dto = dto;
				this.Propietario = prop;
				this.Saldo_Ant = sAnt;
				this.Pago_Ant = pAnt;
				this.Saldo_Pendiente = sPend;
				this.intereses = inter;
				this.Porc_A = porA;
				this.Total_A = totA;
				this.Porc_B = porB;
				this.Total_B = totB;
				this.Porc_C = porC;
				this.Total_C = totC;
				this.Redondeo = red;
				this.Total = total;
				this.Edificio = edif;
				this.Periodo = periodo;
				
			}

			public EstadoCuentasYProrrateo(int iden, int uni, String dto, String prop, double sAnt, double pAnt, double sPend, double inter, double porA, double totA, double porB, double totB, double porC, double totC, double red, double total, String edif, Date periodo){
				super();
				this.identificador = iden;
				this.UNI = uni;
				this.Dto = dto;
				this.Propietario = prop;
				this.Saldo_Ant = sAnt;
				this.Pago_Ant = pAnt;
				this.Saldo_Pendiente = sPend;
				this.intereses = inter;
				this.Porc_A = porA;
				this.Total_A = totA;
				this.Porc_B = porB;
				this.Total_B = totB;
				this.Porc_C = porC;
				this.Total_C = totC;
				this.Redondeo = red;
				this.Total = total;
				this.Edificio = edif;
				this.Periodo = periodo;
				
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public int getIdentificador() {
				return identificador;
			}

			public void setIdentificador(int identificador) {
				this.identificador = identificador;
			}

			public int getUNI() {
				return UNI;
			}

			public void setUNI(int uNI) {
				UNI = uNI;
			}

			public String getDto() {
				return Dto;
			}

			public void setDto(String dto) {
				Dto = dto;
			}

			public String getPropietario() {
				return Propietario;
			}

			public void setPropietario(String propietario) {
				Propietario = propietario;
			}

			public double getSaldo_Ant() {
				return Saldo_Ant;
			}

			public void setSaldo_Ant(double saldo_Ant) {
				Saldo_Ant = saldo_Ant;
			}

			public double getPago_Ant() {
				return Pago_Ant;
			}

			public void setPago_Ant(double pago_Ant) {
				Pago_Ant = pago_Ant;
			}

			public double getSaldo_Pendiente() {
				return Saldo_Pendiente;
			}

			public void setSaldo_Pendiente(double saldo_Pendiente) {
				Saldo_Pendiente = saldo_Pendiente;
			}

			public double getIntereses() {
				return intereses;
			}

			public void setIntereses(double intereses) {
				this.intereses = intereses;
			}

			public double getPorc_A() {
				return Porc_A;
			}

			public void setPorc_A(double porc_A) {
				Porc_A = porc_A;
			}

			public double getTotal_A() {
				return Total_A;
			}

			public void setTotal_A(double total_A) {
				Total_A = total_A;
			}

			public double getPorc_B() {
				return Porc_B;
			}

			public void setPorc_B(double porc_B) {
				Porc_B = porc_B;
			}

			public double getTotal_B() {
				return Total_B;
			}

			public void setTotal_B(double total_B) {
				Total_B = total_B;
			}

			public double getPorc_C() {
				return Porc_C;
			}

			public void setPorc_C(double porc_C) {
				Porc_C = porc_C;
			}

			public double getTotal_C() {
				return Total_C;
			}

			public void setTotal_C(double total_C) {
				Total_C = total_C;
			}

			public double getRedondeo() {
				return Redondeo;
			}

			public void setRedondeo(double redondeo) {
				Redondeo = redondeo;
			}

			public double getTotal() {
				return Total;
			}

			public void setTotal(double total) {
				Total = total;
			}

			public String getEdificio() {
				return Edificio;
			}

			public void setEdificio(String edificio) {
				Edificio = edificio;
			}

			public String getPeriodo() {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
				String reportDate = df.format(Periodo);
				String[] corto = reportDate.split(" ");
				String fecha = corto[0];
				String[] fecha_ = fecha.split("/");
				
				fecha = Globals.obtenerMes(fecha_[1]);
				fecha = fecha+" "+fecha_[2];
				
				return fecha;
			}

			public void setPeriodo(Date periodo) {
				Periodo = periodo;
			}
			
			



}
