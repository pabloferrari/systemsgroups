/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import ninja.Result;
import ninja.Results;
import ninja.jpa.UnitOfWork;
import ninja.params.Param;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import models.Consorcios;
import models.Empleados;
import models.GastosGenerales;
import models.LiqSueldos;
import models.Propietarios;
import models.Proveedores;
import models.Usuarios;


@Singleton
public class ApplicationController_filter {
	
	@Inject 
	Provider<EntityManager> entityManagerProvider;
	
	@Inject

    public Result index() {

        return Results.html();

    }
    
    public Result helloWorldJson() {
        
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }
    
    public static class SimplePojo {

        public String content;
        
    }
    
    // FILTRO CONSORCIOS
    @UnitOfWork
	public Result filtroCons(
			@Param("Filtro") String filtro){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();
		String consulta = "SELECT x FROM models.Consorcios x WHERE (id like '%"+filtro+"%' or NombreCons like '%"+filtro+"%' or DireccionCons like '%"+filtro+"%' or CantCons like '%"+filtro+"%')";
    	Query q = entityManager.createQuery(consulta);
		List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
    	List<Consorcios> c = resultList;


		resultado.render("consorcios", c);
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/consorcios.ftl.html");

		return resultado;

	}
    
    // FILTRO PROPIETARIOS
    @UnitOfWork
	public Result filtroProp(
			@Param("Filtro") String filtro){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();
		String consulta = "SELECT x FROM Propietarios x WHERE (id_prop like '%"+filtro+"%' or Nom_prop like '%"+filtro+"%' or Ape_prop like '%"+filtro+"%' or Doc_prop like '%"+filtro+"%' or Dire_prop like '%"+filtro+"%' or Tel_prop like '%"+filtro+"%' or Email_prop like '%"+filtro+"%' or Edificio like '%"+filtro+"%' or Num_Dto_prop like '%"+filtro+"%' or Unid_prop like '%"+filtro+"%' or Nom_Inq like '%"+filtro+"%')";
    	Query q = entityManager.createQuery(consulta);
		List<Propietarios> resultList = (List<Propietarios>) q.getResultList();
    	List<Propietarios> prop = resultList;


		resultado.render("propietarios", prop);
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/propietarios.ftl.html");

		return resultado;

	}
    
	// FILTRO EMPLEADOS
	@UnitOfWork
	public Result filtroEmpl(
			@Param("Filtro") String filtro){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();
		String consulta = "SELECT x FROM Empleados x WHERE (id_empleado like '%"+filtro+"%' or Nombre_empleado like '%"+filtro+"%' or Apellido_empleado like '%"+filtro+"%' or Documento_empleado like '%"+filtro+"%' or CUIL_empleado like '%"+filtro+"%' or Tel_empleado like '%"+filtro+"%' or Cel_empleado like '%"+filtro+"%' or email_empleado like '%"+filtro+"%' or Horario_empleado like '%"+filtro+"%' or id_edificio like '%"+filtro+"%')";
    	Query q = entityManager.createQuery(consulta);
		List<Empleados> resultList = (List<Empleados>) q.getResultList();
    	List<Empleados> emple = resultList;


		resultado.render("empleados", emple);
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/empleados.ftl.html");

		return resultado;

	}
	
	// FILTRO MOVIMIENTOS
	@UnitOfWork
	public Result filtroMov(
			@Param("edificio") String edificio,
			@Param("usuario") String usuario,
			@Param("fecIn") String fecIn,
			@Param("fecFn") String fecFn,
			@Param("filtro") String filtro){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();
		
		String consulta = "SELECT x FROM Caja x WHERE id > 0";
		
		ArrayList<String> listaFiltros = new ArrayList<String>();
		
		if(!edificio.equals("*")){
			consulta = consulta+" AND Consorcio = '"+edificio+"'";
			listaFiltros.add("Edificio: "+edificio);
		}
		
		if(!usuario.equals("*")){
			consulta = consulta+" AND Usuario = '"+usuario+"'";
			listaFiltros.add("Edificio: "+edificio);
		}
		
		if(!fecIn.equals("")){

			consulta += " AND Fecha >= '"+fecIn+"'";
			String[] separoFecha = fecIn.split("-");
			fecIn = separoFecha[2]+"-"+separoFecha[1]+"-"+separoFecha[0];
			listaFiltros.add("Desde: "+fecIn);
		}

		if(!fecFn.equals("")){

			consulta += " AND Fecha <= '"+fecFn+"'";
			String[] separoFecha = fecFn.split("-");
			fecFn = separoFecha[2]+"-"+separoFecha[1]+"-"+separoFecha[0];
			listaFiltros.add("Hasta: "+fecFn);

		}
		
		/*
		"https://translate.google.com/translate?hl=en&sl=auto&tl=es&u=http://tiku.io/questions/1616507/how-to-export-the-html-tables-data-into-pdf-using-jspdf"
		"http://tiku.io/questions/1616507/how-to-export-the-html-tables-data-into-pdf-using-jspdf"
		"http://www.gatodev.com/index.php?qa=4811&qa_1=c%C3%B3mo-exportar-los-datos-de-las-tablas-html-a-pdf-utilizando-jspdf"
		"https://parall.ax/products/jspdf"
		*/	
		
		
		if(!filtro.equals("")){
			consulta += " AND (id like '%"+filtro+"%' or Fecha like '%"+filtro+"%' or Consorcio like '%"+filtro+"%' or Importe like '%"+filtro+"%' or Saldo_Anterior like '%"+filtro+"%' or Saldo_Final like '%"+filtro+"%' or Consepto like '%"+filtro+"%' or Unidad_Funcional like '%"+filtro+"%' or Pagador like '%"+filtro+"%' or Usuario like '%"+filtro+"%')";
			listaFiltros.add("Filtro: "+filtro);
		}
		
		
		Query q = entityManager.createQuery(consulta);
		List<Empleados> resultList = (List<Empleados>) q.getResultList();
    	List<Empleados> caja = resultList;

    	Query qw = entityManager.createQuery("SELECT x FROM Usuarios x");
 		List<Usuarios> resultList0 = (List<Usuarios>) qw.getResultList();
 		List<Usuarios> usr = resultList0;

 		resultado.render("usuarios", usr);

    	Query z = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList1 = (List<Consorcios>) z.getResultList();
 		List<Consorcios> c = resultList1;

 		resultado.render("consorcios", c);

     	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     	String data = df.format(new Date());
  	
     	resultado.render("cajas", caja);

     	resultado.render("filtros", listaFiltros);
     	
     	resultado.render("fecha", data);
     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/cajaMovimientos.ftl.html");

 		return resultado;
	}
	
	// FILTRO LIQUIDACION SUELDOS
	@UnitOfWork
	public Result filtroLiqSueldo(
			@Param("selectEdif") String edificio,
			@Param("selectEmple") String emple,
			@Param("mes") String mes,
			@Param("anio") String anio,
			@Param("filtro") String filtro) throws java.text.ParseException{

		Result resultado = Results.html();

		String consulta = "";
		ArrayList<String> listaFiltros = new ArrayList<String>();

		if (!edificio.equals("*")){
			consulta += " AND Edificio = '"+edificio+"'";
			listaFiltros.add("Edificio: "+edificio);
		}

		if(!emple.equals("*")){
			consulta += " AND Empleado like '%"+emple+"%'";
			listaFiltros.add("Empleado: "+emple);
		}

		if((!mes.equals("*")) && (!anio.equals("*"))) {
			consulta += " AND fecha_liq = '"+anio+"-"+mes+"-01 00:00:00'";
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
			listaFiltros.add("Fecha: "+mes+" "+anio);
		}

		if(!filtro.equals("")){
			consulta += " AND (id_sueldo_empleado LIKE '%"+filtro+"%' OR Empleado LIKE '%"+filtro+"%' OR Edificio LIKE '%"+filtro+"%' OR sueldo_basico LIKE '%"+filtro+"%' OR presentismo LIKE '%"+filtro+"%' OR fecha_liq LIKE '%"+filtro+"%' OR Liquidado LIKE '%"+filtro+"%')";

			listaFiltros.add("Filtro: "+filtro);
		}


		EntityManager entityManager = entityManagerProvider.get();

		String consulta1 = "SELECT x FROM LiqSueldos x WHERE Liquidado = 'True' "+consulta;
		String consulta2 = "SELECT x FROM LiqSueldos x WHERE Liquidado = 'False' "+consulta;

		//resultado.render("consulta1", consulta1);
		//resultado.render("consulta2", consulta2);

    	Query q = entityManager.createQuery(consulta1);
		List<LiqSueldos> resultList = (List<LiqSueldos>) q.getResultList();
    	List<LiqSueldos> sueldosliq = resultList;

    	resultado.render("sueldos_liq", sueldosliq);

    	Query w = entityManager.createQuery(consulta2);
		List<LiqSueldos> resultList00 = (List<LiqSueldos>) w.getResultList();
    	List<LiqSueldos> sueldosSL = resultList00;

		resultado.render("sueldos_sl", sueldosSL);

    	Query x = entityManager.createQuery("SELECT x FROM Consorcios x");
		List<Consorcios> resultList0 = (List<Consorcios>) x.getResultList();
		List<Consorcios> c = resultList0;

		resultado.render("consorcios", c);

		Query y = entityManager.createQuery("SELECT x FROM Empleados x");
    	List<Empleados> resultList1 = (List<Empleados>) y.getResultList();
    	List<Empleados> empleado = resultList1;

    	resultado.render("empleados", empleado);

    	resultado.render("filtros", listaFiltros);

    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/liq_sueldos.ftl.html");

		return resultado;

	}
	
	// FILTRO GASTOS
	@UnitOfWork
	public Result filtroGast(
			@Param("selectEdif") String edificio,
			@Param("selectQGG") String qgg,
			@Param("selectGasto") String tipoGasto,
			@Param("selectEstado") String estado,
			@Param("fecIn") String fecIn,
			@Param("fecFn") String fecFn,
			@Param("filtro") String filtro) throws java.text.ParseException{

		Result resultado = Results.html();

		String consulta = "";
		ArrayList<String> listaFiltros = new ArrayList<String>();

		if (!edificio.equals("*")){
			consulta += " AND Edificio = '"+edificio+"'";
			listaFiltros.add("Edificio: "+edificio);
		}

		if(!qgg.equals("*")){
			consulta += " AND QQG = '"+qgg+"'";
			listaFiltros.add("QGG: "+qgg);
		}

		if(!tipoGasto.equals("*")){
			consulta += " AND Tipo = '"+tipoGasto+"'";
			listaFiltros.add("Tipo Gasto: "+tipoGasto);
		}

		if(!estado.equals("*")){
			consulta += " AND Liquidado = '"+estado+"'";
			listaFiltros.add("Liquidado: "+estado);
		}

		if(!filtro.equals("")){
			consulta += " AND (id LIKE '%"+filtro+"%' OR Periodo LIKE '%"+filtro+"%' OR Edificio LIKE '%"+filtro+"%' OR Tipo LIKE '%"+filtro+"%' OR SubTipo LIKE '%"+filtro+"%' OR Unidad LIKE '%"+filtro+"%' OR QQG LIKE '%"+filtro+"%' OR Nro_Factura LIKE '%"+filtro+"%' OR GastosA LIKE '%"+filtro+"%' OR GastosB LIKE '%"+filtro+"%' OR GastosC LIKE '%"+filtro+"%' OR Total LIKE '%"+filtro+"%' OR Liquidado LIKE '%"+filtro+"%')";

			listaFiltros.add("Filtro: "+filtro);
		}

		if(!fecIn.equals("")){

			consulta += " AND Periodo > '"+fecIn+"'";
			String[] separoFecha = fecIn.split("-");
			fecIn = separoFecha[2]+"-"+separoFecha[1]+"-"+separoFecha[0];
			listaFiltros.add("Desde: "+fecIn);
		}

		if(!fecFn.equals("")){

			consulta += " AND Periodo < '"+fecFn+"'";
			String[] separoFecha = fecFn.split("-");
			fecFn = separoFecha[2]+"-"+separoFecha[1]+"-"+separoFecha[0];
			listaFiltros.add("Hasta: "+fecFn);

		}

		EntityManager entityManager = entityManagerProvider.get();

		resultado.render("filtros", listaFiltros);
		
		consulta = "SELECT x FROM GastosGenerales x WHERE id <> 0 "+consulta;
		
		System.out.println(consulta);
		
    	Query q = entityManager.createQuery(consulta);
		List<GastosGenerales> resultList = (List<GastosGenerales>) q.getResultList();
    	List<GastosGenerales> g = resultList;
    	
   		resultado.render("gastos", g);
   		
   		Query x = entityManager.createQuery("SELECT x FROM Consorcios x");
		List<Consorcios> resultList0 = (List<Consorcios>) x.getResultList();
		List<Consorcios> c = resultList0;
		
		resultado.render("consorcios", c);
		
		Query w = entityManager.createQuery("SELECT x FROM Proveedores x"); 
    	List<Proveedores> resultList2 = (List<Proveedores>) w.getResultList();
    	List<Proveedores> prof = resultList2;
    	
    	resultado.render("proveedores", prof);   		
   		
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/gastos.ftl.html");

		return resultado;

	}
    
    
}
