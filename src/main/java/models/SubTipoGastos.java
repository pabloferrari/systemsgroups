package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SubTipoGastos {
	
			// Variables de instancia
			@Id
			int id;
			private int num_subtipo;
			private String nombre_subtipo;
			
			// Métodos
			
			public SubTipoGastos(){}
			
			public SubTipoGastos(int id, int numero, String nombre) {
				super();
				this.id = id;
				this.num_subtipo = numero;
				this.nombre_subtipo = nombre;

			}
			
			public SubTipoGastos(int numero, String nombre) {
				super();
				this.num_subtipo = numero;
				this.nombre_subtipo = nombre;

				
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public int getNum_subtipo() {
				return num_subtipo;
			}

			public void setNum_subtipo(int num_subtipo) {
				this.num_subtipo = num_subtipo;
			}

			public String getNombre_subtipo() {
				return nombre_subtipo;
			}

			public void setNombre_subtipo(String nombre_subtipo) {
				this.nombre_subtipo = nombre_subtipo;
			}

			
			
			
			
}
