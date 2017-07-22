package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Consorcios {

			// Variables de instancia
			@Id
			int id;
			private String NombreCons;
			private String DireccionCons;
			private int CantCons;
			private double Saldo;
			private String CUIT;
			private String Clave_Suterh;

			// Métodos

			public Consorcios(){}

			public Consorcios(int id, String nombre, String dire, int cant, double sald, String cuit, String clave){
				super();
				this.id = id;
				this.NombreCons = nombre;
				this.DireccionCons = dire;
				this.CantCons = cant;
				this.Saldo = sald;
				this.CUIT = cuit;
				this.Clave_Suterh = clave;
			}

			public Consorcios(String nombre,String dire, int cant, double sald, String cuit, String clave){
				super();
				this.NombreCons = nombre;
				this.DireccionCons = dire;
				this.CantCons = cant;
				this.Saldo = sald;
				this.CUIT = cuit;
				this.Clave_Suterh = clave;
			}

			public String getCUIT() {
				return CUIT;
			}

			public void setCUIT(String cUIT) {
				CUIT = cUIT;
			}

			public String getClave_Suterh() {
				return Clave_Suterh;
			}

			public void setClave_Suterh(String clave_Suterh) {
				Clave_Suterh = clave_Suterh;
			}

			public int get_id(){
				return id;
			}

			public void set_id(int id){
				this.id = id;
			}

			public String get_NombreCons(){
				return NombreCons;
			}

			public void set_NombreCons(String nombre){
				this.NombreCons = nombre;
			}

			public String get_DireccionCons(){
				return DireccionCons;
			}

			public void set_DireccionCons(String dire){
				this.DireccionCons = dire;
			}

			public int get_CantCons(){
				return CantCons;
			}

			public void set_CantCons(int cant){
				this.CantCons = cant;
			}

			public double getSaldo(){
				return Saldo;
			}

			public void setSaldo(double sald){
				this.Saldo = sald;
			}



}
