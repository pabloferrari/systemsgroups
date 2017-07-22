package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import controllers.Globals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Liquidaciones {
	
			// Variables de instancia
			@Id
			int id;
			private Date Periodo;
			private String Edificio;
			private String SelectG;
			private String SelectL;
			private String SelectT;
			private String SelectAuxI;
			private String SelectAuxII;
			private String SelectAuxIII;
			
			
			// Métodos
			
			public Liquidaciones(){}
			
			public Liquidaciones(int id, Date periodo, String edificio, String selG, String selL, String selT, String auxI, String auxII, String auxIII){
				super();
				this.id = id;
				this.Periodo = periodo;
				this.Edificio = edificio;
				this.SelectG = selG;
				this.SelectL = selL;
				this.SelectT = selT;
				this.SelectAuxI = auxI;
				this.SelectAuxII = auxII;
				this.SelectAuxIII = auxIII;
				
			}
			
			public Liquidaciones(Date periodo, String edificio, String selG, String selL, String selT, String auxI, String auxII, String auxIII){
				super();
				this.Periodo = periodo;
				this.Edificio = edificio;
				this.SelectG = selG;
				this.SelectL = selL;
				this.SelectT = selT;
				this.SelectAuxI = auxI;
				this.SelectAuxII = auxII;
				this.SelectAuxIII = auxIII;
				
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
				String[] fecha_ = fecha.split("/");
				
				fecha = Globals.obtenerMes(fecha_[1]);
				fecha = fecha+" "+fecha_[2];
				
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

			public String getSelectG() {
				return SelectG;
			}

			public void setSelectG(String selectG) {
				SelectG = selectG;
			}

			public String getSelectL() {
				return SelectL;
			}

			public void setSelectL(String selectL) {
				SelectL = selectL;
			}

			public String getSelectT() {
				return SelectT;
			}

			public void setSelectT(String selectT) {
				SelectT = selectT;
			}

			public String getSelectAuxI() {
				return SelectAuxI;
			}

			public void setSelectAuxI(String selectAuxI) {
				SelectAuxI = selectAuxI;
			}

			public String getSelectAuxII() {
				return SelectAuxII;
			}

			public void setSelectAuxII(String selectAuxII) {
				SelectAuxII = selectAuxII;
			}

			public String getSelectAuxIII() {
				return SelectAuxIII;
			}

			public void setSelectAuxIII(String selectAuxIII) {
				SelectAuxIII = selectAuxIII;
			}
			
			
			


}
