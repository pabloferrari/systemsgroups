package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Propietarios {
	
			// Variables de instancia
			@Id
			int id_prop;
			private String Nom_prop;
			private String Ape_prop;
			private String Doc_prop;
			private String Dire_prop;
			private String Tel_prop;
			private String Email_prop;
			private String Edificio;
			private int Num_Dto_prop;
			private String Unid_prop;
			private String Nom_Inq;
			private Double exp_porc;
			private Double exp_Ext_porc;
			private Double Saldo;
			private Double Pago;
			private Double Saldo_Ant;

			// Métodos
			
			public Propietarios(){}
			
			public Propietarios(int id, String nom, String ape, String doc, String dire, String tel, String email, String edif, int numdto, String unid, String inq, Double exp, Double expExt, Double saldo, Double pago, Double saldo_ant){
				super();
				this.id_prop = id;
				this.Nom_prop = nom;
				this.Ape_prop = ape;
				this.Doc_prop = doc;
				this.Dire_prop = dire;
				this.Tel_prop = tel;
				this.Email_prop = email;
				this.Edificio = edif;
				this.Num_Dto_prop = numdto;
				this.Unid_prop = unid;
				this.Nom_Inq = inq;
				this.exp_porc = exp;
				this.exp_Ext_porc = expExt;
				this.Saldo = saldo;
				this.Pago = pago;
				this.Saldo_Ant = saldo_ant;
				
			}

			public Propietarios(String nom, String ape, String doc, String dire, String tel, String email, String edif, int numdto, String unid, String inq, Double exp, Double expExt, Double saldo, Double pago, Double saldo_ant){
				super();
				this.Nom_prop = nom;
				this.Ape_prop = ape;
				this.Doc_prop = doc;
				this.Dire_prop = dire;
				this.Tel_prop = tel;
				this.Email_prop = email;
				this.Edificio = edif;
				this.Num_Dto_prop = numdto;
				this.Unid_prop = unid;
				this.Nom_Inq = inq;
				this.exp_porc = exp;
				this.exp_Ext_porc = expExt;
				this.Saldo = saldo;
				this.Pago = pago;
				this.Saldo_Ant = saldo_ant;
				
			}
			
			public int getId(){
				return id_prop;
			}
			
			public void setId(int id){
				this.id_prop = id;
			}
			
			public String getNombre(){
				return Nom_prop;
			}
			
			public void setNombre(String nom){
				this.Nom_prop = nom;
			}
			
			public String getApellido(){
				return Ape_prop;
			}
			
			public void setApellido(String ape){
				this.Ape_prop = ape;
			}
			
			public String getDocumento(){
				return Doc_prop;
			}
			
			public void setDocumento(String doc){
				this.Doc_prop = doc;
			}
			
			public String getDireccion(){
				return Dire_prop;
			}
			
			public void setDireccion(String dire){
				this.Dire_prop = dire;
			}
			
			public String getTelefono(){
				return Tel_prop;
			}
			
			public void setTelefono(String tel){
				this.Tel_prop = tel;
			}
			
			public String getEmail(){
				return Email_prop;
			}
			
			public void setEmail(String email){
				this.Email_prop = email;
			}
			
			public String getEdificio(){
				return Edificio;
			}
			
			public void setEdificio(String edif){
				this.Edificio = edif;	
			}
			
			public int getNumeroDto(){
				return Num_Dto_prop;
			}
			
			public void setNumeroDto(int numdto){
				this.Num_Dto_prop = numdto;	
			}
			
			public String getUnidad(){
				return Unid_prop;
			}
			
			public void setUnidad(String unid){
				this.Unid_prop = unid;	
			}
			
			public String getInquilino(){
				return Nom_Inq;
			}
			
			public void setInquilino(String inq){
				this.Nom_Inq = inq;	
			}
			
			public Double getExp(){
				return exp_porc;
			}
			
			public void setExp(Double exp){
				this.exp_porc = exp;	
			}
			
			public Double getExpExt(){
				return exp_Ext_porc;
			}
			
			public void setExpExt(Double expExt){
				this.exp_Ext_porc = expExt;	
			}

			public Double getSaldo() {
				return Saldo;
			}

			public void setSaldo(Double saldo) {
				Saldo = saldo;
			}

			public Double getPago() {
				return Pago;
			}

			public void setPago(Double pago) {
				Pago = pago;
			}

			public Double getSaldo_Ant() {
				return Saldo_Ant;
			}

			public void setSaldo_Ant(Double saldo_Ant) {
				Saldo_Ant = saldo_Ant;
			}
			
			
			
			
			
			
}





