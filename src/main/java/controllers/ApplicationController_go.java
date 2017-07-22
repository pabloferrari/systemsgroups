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

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.jpa.UnitOfWork;
import ninja.params.Param;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import models.Caja;
import models.Consorcios;
import models.Empleados;
import models.EstadoCuentasYProrrateo;
import models.GastosGenerales;
import models.LiqSueldos;
import models.Liquidaciones;
import models.Mensajes;
import models.Propietarios;
import models.Proveedores;
import models.SubTipoGastos;
import models.Usuarios;



@Singleton
public class ApplicationController_go {

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

    // LOGIN
    @Transactional
    public Result login(@Param("us_user") String username, @Param("us_contra") String password2){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

    	Query log = entityManager.createQuery("SELECT x FROM Usuarios x WHERE username='"+username+"'");
    	System.out.print("SELECT x FROM Usuarios x WHERE username='"+username+"'");
    	List<Usuarios> resultList = (List<Usuarios>) log.getResultList();

    	if(resultList.size() == 0){
    		resultado.template("views/ApplicationController/index.ftl.html");
    	} else {
	    	List<Usuarios> usrs = resultList;

	    	Usuarios usuario;
	    	usuario = usrs.get(0);

		    String pass = usuario.getContrasena();
		    Globals.categoriaUsuario = usuario.getCate_usuario();

		    if(pass.equals(password2)){

		    	Globals.usuarioActual = username;
		    	Globals.token = Globals.generaToken(32);
		    	usuario.setToken(Globals.token);

		    	resultado.render("usuario", Globals.usuarioActual);
		    	resultado.render("categoria", Globals.categoriaUsuario);
		    	resultado.render("token", Globals.token);
		    	resultado.template("views/ApplicationController/home.ftl.html");

		    } else {

		    	resultado.template("views/ApplicationController/index.ftl.html");

		    }
    	}


	    return resultado;
	}

    // VOLVER A HOME
    public Result home(Context contexto){

    	Result resultado = Results.html();
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.render("token", Globals.token);
    	resultado.template("views/ApplicationController/home.ftl.html");

    	return resultado;
    }

    // VOLVER A INDEX
    public Result volverHome(Context contexto){

    	Result resultado = Results.html();
    	Globals.usuarioActual = null;
    	Globals.categoriaUsuario = null;
    	resultado.template("views/ApplicationController/index.ftl.html");

    	return resultado;
    }

    // PANTALLA USUARIOS
    @UnitOfWork
    public Result usuarios(){

    	Result r = Results.html();

    	EntityManager entityManager = entityManagerProvider.get();

    	Query q = entityManager.createQuery("SELECT x FROM Usuarios x");
    	List<Usuarios> resultList = (List<Usuarios>) q.getResultList();
    	List<Usuarios> u = resultList;

    	String nombre = "";
    	String apellido = "";
    	String dni = "";
    	String direccion = "";
    	String telefono = "";
    	String email = "";
    	String username = "";
    	String contrase = "";
    	String cate = "";
    	int id = 0;

    	r.render("nombre", nombre);
    	r.render("apellido", apellido);
    	r.render("dni", dni);
    	r.render("direccion", direccion);
    	r.render("telefono", telefono);
    	r.render("email", email);
    	r.render("userna", username);
    	r.render("contras", contrase);
    	r.render("cate", cate);
    	r.render("id", id);

    	String accion = "Cargar";
    	//accion = "Editar";
    	r.render("accion", accion);

    	String result = verificaToken();

    	if(result.equals("")){

    		r.render("usuarios", u);
        	r.render("categoria", Globals.categoriaUsuario);
        	r.render("usuario", Globals.usuarioActual);
        	r.template("views/ApplicationController/usuarios.ftl.html");

    	}else{

	    	r.render("usuarios", u);
	    	r.render("categoria", Globals.categoriaUsuario);
	    	r.render("usuario", Globals.usuarioActual);
	    	r.template(result);

    	}

		return r;
	}

    @UnitOfWork
	public String verificaToken(){

		String resultado = "";

		EntityManager entityManager = entityManagerProvider.get();

		Query log = entityManager.createQuery("SELECT x FROM Usuarios x WHERE username='"+Globals.usuarioActual+"'");
		List<Usuarios> usu = (List<Usuarios>) log.getResultList();

		Usuarios usActual = usu.get(0);

    	String tokenActual = usActual.getToken();

    	System.out.println("Token Actual: "+tokenActual);
    	System.out.println("Token Global: "+Globals.token);

    	if(!Globals.token.equals(tokenActual)){

	    	Globals.usuarioActual = null;
	    	Globals.categoriaUsuario = null;
	    	resultado = "views/ApplicationController/index.ftl.html";

    	}

    	return resultado;

    }

    // PANTALLA MENSAJES
    @UnitOfWork
	public Result mensajes(){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

    	Query q = entityManager.createQuery("SELECT x FROM Mensajes x");
    	List<Mensajes> resultList = (List<Mensajes>) q.getResultList();
    	List<Mensajes> m = resultList;

    	String query = "";
    	resultado.render("query", query);


    	resultado.render("mensajes", m);
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/mensajes.ftl.html");

		return resultado;

	}

    // PANTALLA CONSORCIOS
    @UnitOfWork
	public Result consorcios(){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

    	Query q = entityManager.createQuery("SELECT x FROM models.Consorcios x");
		List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
    	List<Consorcios> c = resultList;

    	resultado.render("consorcios", c);
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/consorcios.ftl.html");

		return resultado;

	}

    // PANTALLA GASTOS
 	@UnitOfWork
 	public Result gastos(){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query z = entityManager.createQuery("SELECT x FROM GastosGenerales x");

 		List<GastosGenerales> resultList = (List<GastosGenerales>) z.getResultList();
 		List<GastosGenerales> gast = resultList;

 		resultado.render("gastos", gast);

 		Query x = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList0 = (List<Consorcios>) x.getResultList();
 		List<Consorcios> c = resultList0;

 		resultado.render("consorcios", c);

     	Query w = entityManager.createQuery("SELECT x FROM Proveedores x");
     	List<Proveedores> resultList2 = (List<Proveedores>) w.getResultList();
     	List<Proveedores> prof = resultList2;

     	resultado.render("proveedores", prof);

     	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     	String data = df.format(new Date());

     	ArrayList<String> listaFiltros = new ArrayList<String>();
     	listaFiltros.add("SIN FILTROS");

     	resultado.render("filtros", listaFiltros);
     	resultado.render("fecha", data);
 		resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/gastos.ftl.html");

 		return resultado;

 	}

    // PANTALLA GASTOS RES
 	@UnitOfWork
 	public Result gastos_res(){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query z = entityManager.createQuery("SELECT x FROM GastosGenerales x");

 		List<GastosGenerales> resultList = (List<GastosGenerales>) z.getResultList();
 		List<GastosGenerales> gast = resultList;

 		resultado.render("gastos", gast);

 		Query x = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList0 = (List<Consorcios>) x.getResultList();
 		List<Consorcios> c = resultList0;

 		resultado.render("consorcios", c);

     	Query w = entityManager.createQuery("SELECT x FROM Proveedores x");
     	List<Proveedores> resultList2 = (List<Proveedores>) w.getResultList();
     	List<Proveedores> prof = resultList2;

     	resultado.render("proveedores", prof);

     	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     	String data = df.format(new Date());

     	ArrayList<String> listaFiltros = new ArrayList<String>();
     	listaFiltros.add("SIN FILTROS");

     	resultado.render("filtros", listaFiltros);
     	resultado.render("fecha", data);
 		resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/gastos_res.ftl.html");

 		return resultado;

 	}

 	// PANTALLA LIQUIDACION SUELDOS NEW
 	@UnitOfWork
 	public Result gastos_new(){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query z = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList = (List<Consorcios>) z.getResultList();
 		List<Consorcios> c = resultList;

 		resultado.render("consorcios", c);

     	Query w = entityManager.createQuery("SELECT x FROM Proveedores x");
     	List<Proveedores> resultList2 = (List<Proveedores>) w.getResultList();
     	List<Proveedores> prof = resultList2;

     	resultado.render("proveedores", prof);

     	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     	String data = df.format(new Date());

     	Query e = entityManager.createQuery("SELECT x FROM SubTipoGastos x");
     	List<SubTipoGastos> resultList4 = (List<SubTipoGastos>) e.getResultList();
     	List<SubTipoGastos> subt = resultList4;

     	resultado.render("subtipos", subt);

     	resultado.render("fecha", data);
     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/gastos_new.ftl.html");

 		return resultado;
 	}

 	// PANTALLA EMPLEADOS
 	@UnitOfWork
 	public Result empleados(){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query x = entityManager.createQuery("SELECT x FROM Empleados x");
     	List<Empleados> resultList3 = (List<Empleados>) x.getResultList();
     	List<Empleados> emple = resultList3;

     	resultado.render("empleados", emple);

     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/empleados.ftl.html");

 		return resultado;

 	}

 	// PANTALLA EMPLEADOS NEW
 	@UnitOfWork
 	public Result empleados_new(){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query q = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
 		List<Consorcios> c = resultList;

 		resultado.render("consorcios", c);
     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/empleados_new.ftl.html");

 		return resultado;

 	}

 	// PANTALLA LIQUIDACION SUELDOS
 	@UnitOfWork
 	public Result liqSueldo(){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query z = entityManager.createQuery("SELECT x FROM LiqSueldos x WHERE Liquidado = 'True'");

 		List<LiqSueldos> resultList = (List<LiqSueldos>) z.getResultList();
 		List<LiqSueldos> sueldosliq = resultList;

 		resultado.render("sueldos_liq", sueldosliq);

 		Query y = entityManager.createQuery("SELECT x FROM LiqSueldos x WHERE Liquidado = 'False'");

 		List<LiqSueldos> resultList2 = (List<LiqSueldos>) y.getResultList();
 		List<LiqSueldos> sueldosSL = resultList2;

 		resultado.render("sueldos_sl", sueldosSL);

 		Query x = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList0 = (List<Consorcios>) x.getResultList();
 		List<Consorcios> c = resultList0;

 		resultado.render("consorcios", c);

 		Query q = entityManager.createQuery("SELECT x FROM Empleados x");
     	List<Empleados> resultList1 = (List<Empleados>) q.getResultList();
     	List<Empleados> emple = resultList1;

     	resultado.render("empleados", emple);

     	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     	String data = df.format(new Date());

     	ArrayList<String> listaFiltros = new ArrayList<String>();
     	listaFiltros.add("SIN FILTROS");

     	resultado.render("filtros", listaFiltros);
     	resultado.render("fecha", data);
 		resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/liq_sueldos.ftl.html");

     	/*
     	String consulta1 = "";
     	String consulta2 = "";
     	resultado.render("consulta1", consulta1);
 		resultado.render("consulta2", consulta2);
     	*/

 		return resultado;

 	}


 	// PANTALLA LIQUIDACION SUELDOS NEW
 	@UnitOfWork
 	public Result liqSueldo_new(){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query q = entityManager.createQuery("SELECT x FROM Empleados x");
     	List<Empleados> resultList1 = (List<Empleados>) q.getResultList();
     	List<Empleados> emple = resultList1;

     	resultado.render("empleados", emple);

     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/liq_sueldos_new.ftl.html");

     	/*
     	String consulta1 = "";
     	String consulta2 = "";
     	resultado.render("consulta1", consulta1);
 		resultado.render("consulta2", consulta2);
     	*/

 		return resultado;

 	}

 	// PANTALLA LIQUIDACION
  	@UnitOfWork
  	public Result liquidacion(){

  		Result resultado = Results.html();

  		EntityManager entityManager = entityManagerProvider.get();

  		Date fechaActual = new Date();
  		Calendar fecha = Calendar.getInstance();
  		int mes = fecha.get(Calendar.MONTH) + 1;

  		String ene = "";
  		String feb = "";
  		String mar = "";
  		String abr = "";
  		String may = "";
  		String jun = "";
  		String jul = "";
  		String ago = "";
  		String sep = "";
  		String oct = "";
  		String nov = "";
  		String dic = "";

  		switch (mes) {
	  		case 1:
	  		  ene = "selected='selected'";
	  		  break;
	  		case 2:
	  		  feb = "selected='selected'";
	  		  break;
	  		case 3:
	  		  mar = "selected='selected'";
	  		  break;
	  		case 4:
	  		  abr = "selected='selected'";
	  		  break;
	  		case 5:
	  		  may = "selected='selected'";
	  		  break;
	  		case 6:
	  		  jun = "selected='selected'";
	  		  break;
	  		case 7:
	  		  jul = "selected='selected'";
	  		  break;
	  		case 8:
	  		  ago = "selected='selected'";
	  		  break;
	  		case 9:
	  		  sep = "selected='selected'";
	  		  break;
	  		case 10:
	  		  oct = "selected='selected'";
	  		  break;
	  		case 11:
	  		  nov = "selected='selected'";
	  		  break;
	  		case 12:
	  		  dic = "selected='selected'";
	  		  break;

        }

  		resultado.render("ene", ene);
        resultado.render("feb", feb);
        resultado.render("mar", mar);
        resultado.render("abr", abr);
        resultado.render("may", may);
        resultado.render("jun", jun);
        resultado.render("jul", jul);
        resultado.render("ago", ago);
        resultado.render("sep", sep);
        resultado.render("oct", oct);
        resultado.render("nov", nov);
        resultado.render("dic", dic);

  		Query x = entityManager.createQuery("SELECT x FROM Consorcios x");
  		List<Consorcios> resultList0 = (List<Consorcios>) x.getResultList();
  		List<Consorcios> c = resultList0;

  		resultado.render("consorcios", c);

  		resultado.render("usuario", Globals.usuarioActual);
      	resultado.render("categoria", Globals.categoriaUsuario);
      	resultado.template("views/ApplicationController/liquidacion.ftl.html");

  		return resultado;

  	}

  	// PANTALLA LIQUIDACION
   	@UnitOfWork
   	public Result liquidacion_new(
   			@Param("selectEdif") String id,
   			@Param("mes") String mes,
   			@Param("anio") String anio){

        //System.out.print("id: "+id+" mes: "+mes+" anio: "+anio);
   		Result resultado = Results.html();

   		EntityManager entityManager = entityManagerProvider.get();

   		Query edif = entityManager.createQuery("SELECT NombreCons FROM Consorcios WHERE id = "+id);
	    String edificio = (String) edif.getSingleResult();

	    String fecha_ini = null;
	    String fecha_fin = null;
	    String mes_ = null;

		switch(mes){
			case "01":
				fecha_ini = anio+"-"+mes+"-"+"01";
				fecha_fin = anio+"-"+mes+"-"+"31";
			case "03":
			case "05":
			case "07":
			case "08":
			case "10":
			case "12":
				fecha_ini = anio+"-"+mes+"-"+"01";
				fecha_fin = anio+"-"+mes+"-"+"31";
				break;

			case "02":
				fecha_ini = anio+"-"+mes+"-"+"01";
				fecha_fin = anio+"-"+mes+"-"+"29";
				break;

			case "04":
			case "06":
			case "09":
			case "11":
				fecha_ini = anio+"-"+mes+"-"+"01";
				fecha_fin = anio+"-"+mes+"-"+"30";
				break;

		}

		mes_ = Globals.obtenerMes(mes);

		Query q = entityManager.createQuery("SELECT x FROM Consorcios x WHERE id = '"+id+"'");
		List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
		List<Consorcios> c = resultList;

		resultado.render("consorcios", c);

		// GASTOS SUBTIPO 1 => LIQUIDACION SUELDOS
		Query w = entityManager.createQuery("SELECT x FROM LiqSueldos x WHERE Edificio='"+edificio+"' AND fecha_liq >= '"+fecha_ini+"' AND fecha_liq <= '"+fecha_fin+"' AND Liquidado = 'False'");
		List<LiqSueldos> resultList0 = (List<LiqSueldos>) w.getResultList();
		List<LiqSueldos> liqS = resultList0;

		// ENVÍO LIQUIDACION DE SUELDO
		resultado.render("liqS", liqS);

		String liq_sueldo = "SELECT x FROM LiqSueldos x WHERE Edificio='"+edificio+"' AND fecha_liq >= '"+fecha_ini+"' AND fecha_liq <= '"+fecha_fin+"' AND Liquidado = 'False'";
		// ENVÍO LIQUIDACION DE SUELDO CONSULTA
		resultado.render("liq_sueldo", liq_sueldo);

		Query e = entityManager.createQuery("SELECT x FROM Empleados x WHERE id_edificio='"+edificio+"'");
		List<LiqSueldos> resultList1 = (List<LiqSueldos>) e.getResultList();
		List<LiqSueldos> empl = resultList1;

		// ENVIO EMPLEADOS
		resultado.render("empl", empl);

		Query r = entityManager.createQuery("SELECT x FROM SubTipoGastos x ORDER BY num_subtipo");
		List<SubTipoGastos> resultList2 = (List<SubTipoGastos>) r.getResultList();
		List<SubTipoGastos> sub = resultList2;

		Query todos = entityManager.createQuery("SELECT x FROM GastosGenerales x WHERE Edificio='"+edificio+"' AND Periodo >= '"+fecha_ini+"' AND Periodo <= '"+fecha_fin+"' AND Liquidado = 'False' ORDER BY idSubtipo");
		List<GastosGenerales> resultListTodos = (List<GastosGenerales>) todos.getResultList();

		// ENVIO TODOS LOS GASTOS ORDENADOS POR SUBTIPOS
		resultado.render("resultListTodos", resultListTodos);

		String consulta_total = "SELECT SUM(Total) AS sum FROM GastosGenerales x WHERE Edificio='"+edificio+"' AND Periodo >= '"+fecha_ini+"' AND Periodo <= '"+fecha_fin+"' AND Liquidado = 'False' Group By Subtipo ORDER BY idSubtipo";
		Query sumTodos = entityManager.createQuery(consulta_total);
		List<Double> listtotal = sumTodos.getResultList();

		// ENVIO TOTALES POR SUBTIPO
		resultado.render("listtotal", listtotal);

		String gastos_tot = "SELECT x FROM GastosGenerales x WHERE Edificio='"+edificio+"' AND Periodo >= '"+fecha_ini+"' AND Periodo <= '"+fecha_fin+"' AND Liquidado = 'False' ORDER BY idSubtipo";

		resultado.render("gastos_tot", gastos_tot);
		resultado.render("consulta_total", consulta_total);

		resultado.render("edificio", edificio);

		// SUBTIPO 1
		SubTipoGastos subtipo1 = sub.get(0);

		int st1_nu = subtipo1.getNum_subtipo();
		String st1_no = subtipo1.getNombre_subtipo();

		resultado.render("st1_nu", st1_nu);
		resultado.render("st1_no", st1_no);

		resultado.render("mes", mes_);
		resultado.render("anio", anio);
		resultado.render("usuario", Globals.usuarioActual);
       	resultado.render("categoria", Globals.categoriaUsuario);
       	resultado.template("views/ApplicationController/liquidacion_new.ftl.html");

   		return resultado;

   	}

  	// PANTALLA PROPIETARIOS
    @UnitOfWork
	public Result propietarios(){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

		Query q = entityManager.createQuery("SELECT x FROM Propietarios x");
    	List<Propietarios> resultList = (List<Propietarios>) q.getResultList();
    	List<Propietarios> prop = resultList;

    	resultado.render("propietarios", prop);

    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/propietarios.ftl.html");

		return resultado;

	}

    // PANTALLA PROPIETARIOS NEW
    @UnitOfWork
	public Result propietarios_new(
			@Param("selectCons") String select,
			@Param("nombreCons") String nombre){

    	Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

		Query q = entityManager.createQuery("SELECT x FROM Consorcios x WHERE id = '"+select+"'");
		List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
		List<Consorcios> c = resultList;

		resultado.render("consorcios", c);

		Query e = entityManager.createQuery("SELECT x FROM Propietarios x WHERE Edificio = '"+nombre+"'");
		List<Propietarios> resultList2 = (List<Propietarios>) e.getResultList();
		List<Propietarios> cantidad = resultList2;

		int minimo = cantidad.size();
		minimo = minimo + 1;
		resultado.render("minimo", minimo);

		// VERIFICO DISPONIBLE DE EXPENSAS
		Query qq = entityManager.createQuery("SELECT SUM(exp_porc) FROM Propietarios WHERE Edificio = '"+nombre+"'");
		Query qq2 = entityManager.createQuery("SELECT SUM(exp_Ext_porc) FROM Propietarios WHERE Edificio = '"+nombre+"'");

	    Double q1 = (Double) qq.getSingleResult();
	    Double q2 = (Double) qq2.getSingleResult();
		String expensa = "";
		String mensaje = "";

	    if(q1 == null){
	    	q1 = (double) 0;
	    }

	    if(q2 == null){
	    	q2 = (double) 0;
	    }

	    if(q1 >= 100 || q2 >= 100){
	    	expensa = "disabled";
	    	mensaje = "No tiene propietarios disponibles: Porcentaje máximo alcanzado.";
	    }

	    q1 = Globals.redondear(100 - q1);
	    q2 = Globals.redondear(100 - q2);
	    // FIN VERIFICO DISPONIBLE DE EXPENSAS
	    resultado.render("expensa", expensa);
	    resultado.render("mensaje", mensaje);

		resultado.render("expensas", q1);
		resultado.render("expensasExtra", q2);
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/propietarios_new.ftl.html");

		return resultado;

	}

    // PANTALLA PROVEEDORES
    @UnitOfWork
	public Result proveedores(){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

    	Query w = entityManager.createQuery("SELECT x FROM Proveedores x");
    	List<Proveedores> resultList2 = (List<Proveedores>) w.getResultList();
    	List<Proveedores> prof = resultList2;

    	resultado.render("proveedores", prof);

    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/proveedores.ftl.html");

		return resultado;

	}

    // PANTALLA PROVEEDORES NEW
 	@UnitOfWork
 	public Result proveedores_new(){

 		Result resultado = Results.html();


     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/proveedores_new.ftl.html");

 		return resultado;

 	}

 	// PANTALLA SUBTIPO GASTOS
    @UnitOfWork
	public Result subtipo_gastos(){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

    	Query q = entityManager.createQuery("SELECT x FROM SubTipoGastos x");
    	List<SubTipoGastos> resultList = (List<SubTipoGastos>) q.getResultList();
    	List<SubTipoGastos> m = resultList;

    	resultado.render("subtipos", m);

    	String estado = "nuevo";
    	resultado.render("estado", estado);

    	Query w = entityManager.createQuery("SELECT MAX(num_subtipo) FROM SubTipoGastos x");
		int maxNum;

		if(w.getSingleResult().equals(null)){
			maxNum = 1;
		} else {
			maxNum = (int) w.getSingleResult();
			maxNum = maxNum+1;
		}

    	resultado.render("numNS", maxNum);

    	String nom = " ";
    	resultado.render("nomNS", nom);

    	int idS = 0;
    	resultado.render("idS", idS);

    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/subtipo_gastos.ftl.html");

		return resultado;

	}

    // PANTALLA CAJA
    @UnitOfWork
	public Result caja(){

    	Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query z = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList = (List<Consorcios>) z.getResultList();
 		List<Consorcios> c = resultList;

 		resultado.render("consorcios", c);

     	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     	String data = df.format(new Date());

     	Query e = entityManager.createQuery("SELECT x FROM Caja x ORDER BY id DESC");
     	List<Caja> resultList4 = (List<Caja>) e.setMaxResults(10).getResultList();
     	List<Caja> cajas10 = resultList4;

     	resultado.render("cajas10", cajas10);

     	Query w = entityManager.createQuery("SELECT x FROM Caja x ORDER BY id DESC");
     	List<Caja> resultList5 = (List<Caja>) w.getResultList();
     	List<Caja> caja = resultList5;

     	resultado.render("cajas", caja);

     	resultado.render("fecha", data);
     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/caja.ftl.html");

 		return resultado;
 	}

    // PANTALLA MOVIMIENTOS
    @UnitOfWork
	public Result movimientos(){

    	Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query z = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList = (List<Consorcios>) z.getResultList();
 		List<Consorcios> c = resultList;

 		resultado.render("consorcios", c);

 		Query qw = entityManager.createQuery("SELECT x FROM Usuarios x");
 		List<Usuarios> resultList1 = (List<Usuarios>) qw.getResultList();
 		List<Usuarios> usr = resultList1;

 		resultado.render("usuarios", usr);

     	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     	String data = df.format(new Date());

     	Query e = entityManager.createQuery("SELECT x FROM Caja x ORDER BY id DESC");
     	List<Caja> resultList4 = (List<Caja>) e.setMaxResults(10).getResultList();
     	List<Caja> cajas10 = resultList4;

     	resultado.render("cajas10", cajas10);

     	Query w = entityManager.createQuery("SELECT x FROM Caja x ORDER BY id DESC");
     	List<Caja> resultList5 = (List<Caja>) w.getResultList();
     	List<Caja> caja = resultList5;

     	resultado.render("cajas", caja);

     	ArrayList<String> listaFiltros = new ArrayList<String>();
     	listaFiltros.add("SIN FILTROS");

     	resultado.render("filtros", listaFiltros);

     	resultado.render("fecha", data);
     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/cajaMovimientos.ftl.html");

 		return resultado;
 	}

    // PANTALLA SALDOS
    @UnitOfWork
	public Result saldos(){

    	Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query z = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList = (List<Consorcios>) z.getResultList();
 		List<Consorcios> c = resultList;

 		resultado.render("consorcios", c);

     	Query e = entityManager.createQuery("SELECT x FROM Propietarios x ORDER BY Edificio, Num_Dto_Prop");
     	List<Propietarios> resultList4 = (List<Propietarios>) e.getResultList();
     	List<Propietarios> prop = resultList4;

     	resultado.render("propietarios", prop);

     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/cajaSaldos.ftl.html");

 		return resultado;
 	}

    // PANTALLA LIQUIDACIONES
    @UnitOfWork
	public Result liquidaciones(){

    	Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query z = entityManager.createQuery("SELECT x FROM Liquidaciones x WHERE Edificio <> '' GROUP BY Edificio");
 		List<Liquidaciones> resultList = (List<Liquidaciones>) z.getResultList();
 		List<Liquidaciones> liqui = resultList;

 		resultado.render("liquidaciones", liqui);

 		Query x = entityManager.createQuery("SELECT x FROM Liquidaciones x WHERE id = 0");
 		List<Liquidaciones> resultList0 = (List<Liquidaciones>) x.getResultList();
 		List<Liquidaciones> liquida = resultList0;
 		List<Liquidaciones> liquiddd = resultList0;

 		String edificio = "";
 		resultado.render("edificio", edificio);
 		resultado.render("liquida", liquida);

 		resultado.render("liquiddd", liquiddd);

     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/liquidaciones.ftl.html");

 		return resultado;
 	}


    // PANTALLA LIQUIDACIONES
    @UnitOfWork
	public Result verLiquidaciones(
			@Param("edificio") String edificio){

    	Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query z = entityManager.createQuery("SELECT x FROM Liquidaciones x GROUP BY Edificio");
 		List<Liquidaciones> resultList = (List<Liquidaciones>) z.getResultList();
 		List<Liquidaciones> liqui = resultList;

 		resultado.render("liquidaciones", liqui);

 		Query x = entityManager.createQuery("SELECT x FROM Liquidaciones x WHERE Edificio = '"+edificio+"' ORDER BY Periodo DESC");
 		List<Liquidaciones> resultList0 = (List<Liquidaciones>) x.getResultList();
 		List<Liquidaciones> liquida = resultList0;
 		String query = "SELECT x FROM Liquidaciones x WHERE Edificio = '"+edificio+"' ORDER BY Periodo DESC";

 		resultado.render("edificio", edificio);
 		resultado.render("liquida", liquida);

     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/liquidaciones.ftl.html");

 		return resultado;
 	}


    // PANTALLA LIQUIDACIONES
    @UnitOfWork
	public Result traerLiquidaciones(
   			@Param("edificio") String edificio,
   			@Param("periodo") String periodo){

   		Result resultado = Results.html();

   		EntityManager entityManager = entityManagerProvider.get();

	    String fecha_ini = null;
	    String fecha_fin = null;
	    String mes_ = null;

	    String[] dividoFecha = periodo.split(" ");

	    int mes = Globals.obtenerMes_(dividoFecha[0]);
	    String anio = dividoFecha[1];

		switch(mes){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
				fecha_ini = anio+"-0"+mes+"-"+"01";
				fecha_fin = anio+"-0"+mes+"-"+"31";
				break;

			case 10:
			case 12:
				fecha_ini = anio+"-"+mes+"-"+"01";
				fecha_fin = anio+"-"+mes+"-"+"31";
				break;

			case 2:
				fecha_ini = anio+"-0"+mes+"-"+"01";
				fecha_fin = anio+"-0"+mes+"-"+"29";
				break;

			case 4:
			case 6:
			case 9:
				fecha_ini = anio+"-0"+mes+"-"+"01";
				fecha_fin = anio+"-0"+mes+"-"+"30";
				break;
			case 11:
				fecha_ini = anio+"-"+mes+"-"+"01";
				fecha_fin = anio+"-"+mes+"-"+"30";
				break;

		}

		mes_ = Globals.obtenerMes(mes);

		Query q = entityManager.createQuery("SELECT x FROM Consorcios x WHERE NombreCons = '"+edificio+"'");
		List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
		List<Consorcios> c = resultList;

		resultado.render("consorcios", c);

		// GASTOS SUBTIPO 1 => LIQUIDACION SUELDOS
		Query w = entityManager.createQuery("SELECT x FROM LiqSueldos x WHERE Edificio='"+edificio+"' AND fecha_liq >= '"+fecha_ini+"' AND fecha_liq <= '"+fecha_fin+"' AND Liquidado = 'True'");
		List<LiqSueldos> resultList0 = (List<LiqSueldos>) w.getResultList();
		List<LiqSueldos> liqS = resultList0;

		// ENVÍO LIQUIDACION DE SUELDO
		resultado.render("liqS", liqS);

		String liq_sueldo = "SELECT x FROM LiqSueldos x WHERE Edificio='"+edificio+"' AND fecha_liq >= '"+fecha_ini+"' AND fecha_liq <= '"+fecha_fin+"' AND Liquidado = 'True'";
		// ENVÍO LIQUIDACION DE SUELDO CONSULTA
		resultado.render("liq_sueldo", liq_sueldo);

		Query e = entityManager.createQuery("SELECT x FROM Empleados x WHERE id_edificio='"+edificio+"'");
		List<LiqSueldos> resultList1 = (List<LiqSueldos>) e.getResultList();
		List<LiqSueldos> empl = resultList1;

		// ENVIO EMPLEADOS
		resultado.render("empl", empl);

		Query r = entityManager.createQuery("SELECT x FROM SubTipoGastos x ORDER BY num_subtipo");
		List<SubTipoGastos> resultList2 = (List<SubTipoGastos>) r.getResultList();
		List<SubTipoGastos> sub = resultList2;

		Query todos = entityManager.createQuery("SELECT x FROM GastosGenerales x WHERE Edificio='"+edificio+"' AND Periodo >= '"+fecha_ini+"' AND Periodo <= '"+fecha_fin+"' AND Liquidado = 'True' ORDER BY idSubtipo");
		List<GastosGenerales> resultListTodos = (List<GastosGenerales>) todos.getResultList();

		// ENVIO TODOS LOS GASTOS ORDENADOS POR SUBTIPOS
		resultado.render("resultListTodos", resultListTodos);

		String consulta_total = "SELECT SUM(Total) AS sum FROM GastosGenerales x WHERE Edificio='"+edificio+"' AND Periodo >= '"+fecha_ini+"' AND Periodo <= '"+fecha_fin+"' AND Liquidado = 'True' Group By Subtipo ORDER BY idSubtipo";
		Query sumTodos = entityManager.createQuery(consulta_total);
		List<Double> listtotal = sumTodos.getResultList();

		// ENVIO TOTALES POR SUBTIPO
		resultado.render("listtotal", listtotal);

		String gastos_tot = "SELECT x FROM GastosGenerales x WHERE Edificio='"+edificio+"' AND Periodo >= '"+fecha_ini+"' AND Periodo <= '"+fecha_fin+"' AND Liquidado = 'True' ORDER BY idSubtipo";

		resultado.render("gastos_tot", gastos_tot);
		resultado.render("consulta_total", consulta_total);

		resultado.render("edificio", edificio);

		// SUBTIPO 1
		SubTipoGastos subtipo1 = sub.get(0);

		int st1_nu = subtipo1.getNum_subtipo();
		String st1_no = subtipo1.getNombre_subtipo();

		resultado.render("st1_nu", st1_nu);
		resultado.render("st1_no", st1_no);

		Query caj = entityManager.createQuery("SELECT x FROM Caja x WHERE Consorcio='"+edificio+"' AND Fecha >= '"+fecha_ini+"' AND Fecha <= '"+fecha_fin+"'");
     	List<Caja> resultList5 = (List<Caja>) caj.getResultList();
     	List<Caja> caja = resultList5;

		resultado.render("cajas", caja);

		resultado.render("mes", mes_);
		resultado.render("anio", anio);
		resultado.render("usuario", Globals.usuarioActual);
       	resultado.render("categoria", Globals.categoriaUsuario);
       	resultado.template("views/ApplicationController/liquidacion_ver.ftl.html");

   		return resultado;

   	}


    // PANTALLA PRORRATEOS
    @UnitOfWork
    public Result prorrateos(){

    	Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query proEd = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio <> 'Prueba' GROUP BY Edificio");
 		List<EstadoCuentasYProrrateo> resultListPro = (List<EstadoCuentasYProrrateo>) proEd.getResultList();
 		List<EstadoCuentasYProrrateo> prorraD = resultListPro;

 		resultado.render("pro_ed", prorraD);

 		Query proP = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = 'SIN DATOS' GROUP BY Periodo");
 		List<EstadoCuentasYProrrateo> resultListPro0 = (List<EstadoCuentasYProrrateo>) proP.getResultList();
 		List<EstadoCuentasYProrrateo> prorraP = resultListPro0;

 		resultado.render("pro_pe", prorraP);

 		Query pror = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = 'SIN DATOS' GROUP BY Periodo");
 		List<EstadoCuentasYProrrateo> resultListPro1 = (List<EstadoCuentasYProrrateo>) pror.getResultList();
 		List<EstadoCuentasYProrrateo> prorra = resultListPro1;

 		resultado.render("prorra", prorra);

 		boolean muestro = false;
 		resultado.render("muestro", muestro);

 		resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/prorrateos.ftl.html");

 		return resultado;

    }

    // PANTALLA PRORRATEOS
    @UnitOfWork
    public Result verPeriodo(
    		@Param("edificio") String edificio){

    	Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();


 		Query proEd = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x GROUP BY Edificio");
 		List<EstadoCuentasYProrrateo> resultListPro = (List<EstadoCuentasYProrrateo>) proEd.getResultList();
 		List<EstadoCuentasYProrrateo> prorraD = resultListPro;

 		resultado.render("pro_ed", prorraD);

 		Query proP = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = '"+edificio+"' GROUP BY Periodo ORDER BY id DESC");
 		List<EstadoCuentasYProrrateo> resultListPro0 = (List<EstadoCuentasYProrrateo>) proP.getResultList();
 		List<EstadoCuentasYProrrateo> prorraP = resultListPro0;

 		resultado.render("pro_pe", prorraP);

 		Query pror = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = '"+edificio+"' AND Periodo = 'SIN DATOS'");
 		List<EstadoCuentasYProrrateo> resultListPro1 = (List<EstadoCuentasYProrrateo>) pror.getResultList();
 		List<EstadoCuentasYProrrateo> prorra = resultListPro1;

 		resultado.render("prorra", prorra);

 		boolean muestro = false;
 		resultado.render("muestro", muestro);

 		resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/prorrateos.ftl.html");

 		return resultado;

    }

    // PANTALLA PRORRATEOS
    @UnitOfWork
    public Result verProrrateo(
    		@Param("edificio") String edificio,
    		@Param("periodo") String periodo){

    	Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

    	Query proEd = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x GROUP BY Edificio");
 		List<EstadoCuentasYProrrateo> resultListPro = (List<EstadoCuentasYProrrateo>) proEd.getResultList();
 		List<EstadoCuentasYProrrateo> prorraD = resultListPro;

 		resultado.render("pro_ed", prorraD);

 		Query proP = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = '"+edificio+"' GROUP BY Periodo ORDER BY id DESC");
 		List<EstadoCuentasYProrrateo> resultListPro0 = (List<EstadoCuentasYProrrateo>) proP.getResultList();
 		List<EstadoCuentasYProrrateo> prorraP = resultListPro0;

 		resultado.render("pro_pe", prorraP);

 		String[] dividoPeriodo = periodo.split(" ");
 		String mes = dividoPeriodo[0];
 		int mes_ = Globals.obtenerMes_(mes);

 		if(mes_<10){
 			periodo = dividoPeriodo[1]+"-0"+mes_+"-01 00:00:00";
 		} else {
 			periodo = dividoPeriodo[1]+"-"+mes_+"-01 00:00:00";
 		}

 		Query pror = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = '"+edificio+"' AND Periodo = '"+periodo+"'");
 		List<EstadoCuentasYProrrateo> resultListPro1 = (List<EstadoCuentasYProrrateo>) pror.getResultList();
 		List<EstadoCuentasYProrrateo> prorra = resultListPro1;

 		resultado.render("prorra", prorra);

 		boolean muestro = true;
 		resultado.render("muestro", muestro);

 		resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/prorrateos.ftl.html");

 		return resultado;

    }
    // verProrrateo

}
