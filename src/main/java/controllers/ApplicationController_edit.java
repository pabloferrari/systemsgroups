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
import java.util.List;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import models.Consorcios;
import models.Empleados;
import models.EstadoCuentasYProrrateo;
import models.GastosGenerales;
import models.LiqSueldos;
import models.Liquidaciones;
import models.Mensajes;
import models.Proveedores;
import models.Propietarios;
import models.SubTipoGastos;
import models.Usuarios;


@Singleton
public class ApplicationController_edit {
	
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
    
    public void salidaPantalla(String mensaje, String variable, String variable1, String variable2){
    	
    	String mje = null;
    	
    	mje = "\n"+mensaje+": "+variable;
    	
    	if(variable1 != null){
    		mje += " "+variable1;
    	}
    	
    	if(variable2 != null){
    		mje += " "+variable2;
    	}
    	
    	System.out.print(mje);
    	
    	
    }
    
    // PANTALLA EDITAR SUELDOS NEW
    @Transactional
 	public Result edit_liqSueldo(@Param("id") String id,
 								@Param("flag") String flag){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();
 		
 		if(flag.equals("true")){
 				
 			Query x = entityManager.createQuery("SELECT x FROM LiqSueldos x WHERE id_sueldo_empleado = "+id);
 			List<LiqSueldos> resultList0 = (List<LiqSueldos>) x.getResultList();
 	 		
 	 		LiqSueldos liq = resultList0.get(0);
  	  		
  	  		String fecha = liq.getFecha_liq();
  	  		String[] dividoFecha = fecha.split(" ");
  	  		String mes = dividoFecha[0];
  	  		String edificio = liq.getEdificio();
  	  		
  	  		int mes_ = Globals.obtenerMes_(mes);
  	  		
  	  		if(mes_<10){
  	  			mes = "0"+mes_;
  	  		}
  	  		
  	  		// TRAIGO DE LIQUIDACIONES LAS QUERYS DEL PERIODO
  	  		String queryReAct = "SELECT x FROM Liquidaciones x WHERE Edificio = '"+edificio+"' AND Periodo = '"+dividoFecha[1]+"-"+mes+"-01 00:00:00'";
  	  		salidaPantalla(queryReAct, null, null,null);
  	  		Query z = entityManager.createQuery(queryReAct);
	  		List<Liquidaciones> resultList1 = (List<Liquidaciones>) z.getResultList();
	  		
	  		if(resultList1.size()>0){
	
		  		Liquidaciones liqAct = resultList1.get(0);
		  		
		  		String sGastos = liqAct.getSelectG();
		  		String sLiq = liqAct.getSelectL();
		  		
		  		//ACTUALIZO LOS GASTOS DEL PERIODO A LIQUIDADO = FALSE
		  		Query qq = entityManager.createQuery(sGastos);
	  	  		List<GastosGenerales> resultList2 = (List<GastosGenerales>) qq.getResultList();
	  	  		
	  	  		for (GastosGenerales gastosGenerales : resultList2) {
					
	  	  			gastosGenerales.setLiquidado("False");
	  	  			
				}
		  		
	  	  		//ACTUALIZO LAS LIQUIDACIONES DE SUELDO DEL PERIODO A LIQUIDADO = FALSE
		  		Query ww = entityManager.createQuery(sLiq);
	  	  		List<LiqSueldos> resultList3 = (List<LiqSueldos>) ww.getResultList();
	  		
	  	  		for (LiqSueldos liqSueldos : resultList3) {
					
	  	  			liqSueldos.setLiquidado("False");
	  	  			
				}
	  	  		
	  	  		// CORRIJO PRORRATEOS
	  	  		String queryPror = "SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = '"+edificio+"' AND Periodo = '"+dividoFecha[1]+"-"+mes+"-01 00:00:00'";
	  	  		
	  	  		Query ee = entityManager.createQuery(queryPror);
		  		List<EstadoCuentasYProrrateo> resultList4 = (List<EstadoCuentasYProrrateo>) ee.getResultList();
	
		  		for (EstadoCuentasYProrrateo estadoCuentasYProrrateo : resultList4) {
					String propie = "SELECT x FROM Propietarios x WHERE Edificio = '"+edificio+"' AND Num_Dto_prop = "+estadoCuentasYProrrateo.getUNI();
					
					Double total = estadoCuentasYProrrateo.getTotal();
					
					Query rr = entityManager.createQuery(propie);
		  	  		List<Propietarios> resultList5 = (List<Propietarios>) rr.getResultList();
		  	  		
		  	  		if(resultList5.size()>0){
		  	  		
		  	  			Propietarios propi = resultList5.get(0);
		  	  			propi.setSaldo_Ant(propi.getSaldo());
		  	  			propi.setPago(estadoCuentasYProrrateo.getTotal());
		  	  			Double saldoAct = propi.getSaldo() + estadoCuentasYProrrateo.getTotal_A() + estadoCuentasYProrrateo.getTotal_B() + estadoCuentasYProrrateo.getTotal_C();
		  	  			propi.setSaldo(saldoAct);	  	  		
		  	  		
		  	  			//ELIMINO EL REGISTRO DEL PRORRATEO
			  	  		entityManager.remove(estadoCuentasYProrrateo);
			  	  		
		  	  		}
		  	
		  		}
		  		
		  		// ELIMINO LAS QUERYS DE LIQUIDACIONES
		  		entityManager.remove(liqAct);  	  		
	  		}

 		}

 		Query x = entityManager.createQuery("SELECT x FROM LiqSueldos x WHERE id_sueldo_empleado = "+id);
 		List<LiqSueldos> resultList0 = (List<LiqSueldos>) x.getResultList();
 		List<LiqSueldos> liq = resultList0;

 		LiqSueldos sueldo = liq.get(0);

 		String basico = Double.toString(sueldo.getSueldo_basico());
 		resultado.render("basico", basico);
 		String present = Double.toString(sueldo.getPresentismo());
 		resultado.render("present", present);
 		String antigue = Double.toString(sueldo.getAntiguedad());
 		resultado.render("antigue", antigue);
 		String sac = Double.toString(sueldo.getSac());
 		resultado.render("sac", sac);
 		String vaca = Double.toString(sueldo.getVacaciones_ng());
 		resultado.render("vaca", vaca);
 		String horExt = Double.toString(sueldo.getHoras_extras());
 		resultado.render("horExt", horExt);
 		String jubila = Double.toString(sueldo.getJubilacion());
 		resultado.render("jubila", jubila);
 		String obsoc = Double.toString(sueldo.getObra_social());
 		resultado.render("obsoc", obsoc);
 		String afip = Double.toString(sueldo.getAFIP());
 		resultado.render("afip", afip);
 		String suter = Double.toString(sueldo.getSuterh());
 		resultado.render("suter", suter);
 		String fater = Double.toString(sueldo.getFateryh());
 		resultado.render("fater", fater);
 		String serac = Double.toString(sueldo.getSeracarh());
 		resultado.render("serac", serac);
 		String ganan = Double.toString(sueldo.getImpuesto_ganancias());
 		resultado.render("ganan", ganan);
 		String art = Double.toString(sueldo.getART());
 		resultado.render("art", art);
 		String otd = Double.toString(sueldo.getOtros_Items());
 		resultado.render("otd", otd);
 		String bruto = String.format("%.2f", sueldo.getTotal_bruto()); //Double.toString(sueldo.getTotal_bruto());
 		resultado.render("bruto", bruto);
 		String neto = String.format("%.2f", sueldo.getTotal_neto());
 		resultado.render("neto", neto);

 		resultado.render("liquidacion", liq);

     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/edit_liq_sueldos.ftl.html");

 		return resultado;

 	}

 	// FUNCION EDITAR LIQUIDACION SUELDO
 	@Transactional
 	public Result editarliqSueldo(
 			@Param("id") String id,
 			@Param("basNLS") String basico,
 			@Param("preNLS") String present,
 			@Param("antNLS") String antigue,
 			@Param("sacNLS") String sac,
 			@Param("vacNLS") String vaca,
 			@Param("hexNLS") String horExt,
 			@Param("jubNLS") String jubila,
 			@Param("obsNLS") String obsoc,
 			@Param("afiNLS") String afip,
 			@Param("suhNLS") String suter,
 			@Param("fahNLS") String fater,
 			@Param("sehNLS") String serac,
 			@Param("ganNLS") String ganan,
 			@Param("artNLS") String art,
 			@Param("odeNLS") String otd,
 			@Param("totBruNLS") String bruto,
 			@Param("totNetNLS") String neto) throws java.text.ParseException{

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		double su_basico = Double.parseDouble(basico);
 		double su_presen = Double.parseDouble(present);
 		double su_antigu = Double.parseDouble(antigue);
 		double su_sac = Double.parseDouble(sac);
 		double su_vacac = Double.parseDouble(vaca);
 		double su_horExt = Double.parseDouble(horExt);
 		double su_jubil = Double.parseDouble(jubila);
 		double su_obsoc = Double.parseDouble(obsoc);
 		double su_afip = Double.parseDouble(afip);
 		double su_suter = Double.parseDouble(suter);
 		double su_fater = Double.parseDouble(fater);
 		double su_serac = Double.parseDouble(serac);
 		double su_ganan = Double.parseDouble(ganan);
 		double su_art = Double.parseDouble(art);
 		double su_otd = Double.parseDouble(otd);
 		double su_bruto = Double.parseDouble(bruto);
 		double su_neto = Double.parseDouble(neto);
 		String liq = "False";

 		String update = "SELECT x FROM LiqSueldos x WHERE id_sueldo_empleado = "+id;

 		Query datos = entityManager.createQuery(update);
 		List<LiqSueldos> sueldos = (List<LiqSueldos>) datos.getResultList();

 	    LiqSueldos sueldo;
 	    sueldo = sueldos.get(0);

 	    sueldo.setSueldo_basico(su_basico);
 	    sueldo.setPresentismo(su_presen);
 	    sueldo.setAntiguedad(su_antigu);
 	    sueldo.setSac(su_sac);
 	    sueldo.setVacaciones_ng(su_vacac);
 	    sueldo.setHoras_extras(su_horExt);
 	    sueldo.setTotal_bruto(su_bruto);
 	    sueldo.setJubilacion(su_jubil);
 	    sueldo.setObra_social(su_obsoc);
 	    sueldo.setART(su_art);
 	    sueldo.setImpuesto_ganancias(su_ganan);
 	    sueldo.setAFIP(su_afip);
 	    sueldo.setSuterh(su_suter);
 	    sueldo.setFateryh(su_fater);
 	    sueldo.setSeracarh(su_serac);
 	    sueldo.setOtros_Items(su_otd);
 	    sueldo.setTotal_neto(su_neto);

 	    /*
 	    DELETE DE DATABASE

 	    Query datos = entityManager.createQuery("SELECT x FROM LiqSueldos x WHERE id_sueldo_empleado = 1");
 		List<LiqSueldos> sueldos = (List<LiqSueldos>) datos.getResultList();

 	    LiqSueldos sueldo;
 	    sueldo = sueldos.get(0);
 	    entityManager.remove(sueldo);

 		*/
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

 	private EntityManager getEntityManager() {
 		// TODO Auto-generated method stub
 		return null;
 	}

 	// PANTALLA EDITAR PROPIETARIOS
 	@UnitOfWork
 	public Result edit_propietarios(@Param("id") String id){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query x = entityManager.createQuery("SELECT x FROM Propietarios x WHERE id_prop = "+id);
 		List<Propietarios> resultList0 = (List<Propietarios>) x.getResultList();
 		List<Propietarios> liq = resultList0;

 		Propietarios prop = liq.get(0);

 		resultado.render("id", id);
 		String nombre = prop.getNombre();
 		resultado.render("nombre", nombre);
 		String apellido = prop.getApellido();
 		resultado.render("apellido", apellido);
 		String documento = prop.getDocumento();
 		resultado.render("documento", documento);
 		String direccion = prop.getDireccion();
 		resultado.render("direccion", direccion);
 		String telefono = prop.getTelefono();
 		resultado.render("telefono", telefono);
 		String email = prop.getEmail();
 		resultado.render("email", email);
 		String edificio = prop.getEdificio();
 		resultado.render("edificio", edificio);
 		String numDto = String.valueOf(prop.getNumeroDto());
 		resultado.render("numDto", numDto);
 		String unidad = prop.getUnidad();
 		resultado.render("unidad", unidad);
 		String inqui = prop.getInquilino();
 		resultado.render("inqui", inqui);
 		String expensas = (prop.getExp()).toString();
 		resultado.render("expensas", expensas);
 		String expExtra = (prop.getExpExt()).toString();
 		resultado.render("expExtra", expExtra);


 		Query z = entityManager.createQuery("SELECT x FROM Propietarios x WHERE id_prop = "+id);
 		List<Propietarios> resultList = (List<Propietarios>) z.getResultList();
 		List<Propietarios> propietarios = resultList;

 		resultado.render("propietario", propietarios);

     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/edit_propietarios.ftl.html");

 		return resultado;

 	}

 	// FUNCION EDITAR PROPIETARIOS
 	@Transactional
 	public Result editarPropietario(
 			@Param("id") String id,
 			@Param("nomNP") String nombre,
 			@Param("apeNP") String apellido,
 			@Param("docNP") String documento,
 			@Param("direNP") String direccion,
 			@Param("telNP") String telefono,
 			@Param("emaNP") String email,
 			@Param("inqNP") String inqui){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		String update = "SELECT x FROM Propietarios x WHERE id_prop = "+id;

 		Query datos = entityManager.createQuery(update);
 		List<Propietarios> prop = (List<Propietarios>) datos.getResultList();

 		Propietarios propietario = prop.get(0);

 		propietario.setNombre(nombre);
 		propietario.setApellido(apellido);
 		propietario.setDocumento(documento);
 		propietario.setDireccion(direccion);
 		propietario.setTelefono(telefono);
 		propietario.setEmail(email);
 		propietario.setInquilino(inqui);

 		Query q = entityManager.createQuery("SELECT x FROM Propietarios x");
     	List<Propietarios> resultList = (List<Propietarios>) q.getResultList();
     	List<Propietarios> prope = resultList;

     	resultado.render("propietarios", prope);

     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/propietarios.ftl.html");

 		return resultado;

 	}

 	// PANTALLA EDITAR EMPLEADOS
 	@UnitOfWork
 	public Result edit_empleados(@Param("id") String id){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query x = entityManager.createQuery("SELECT x FROM Empleados x WHERE id_empleado = "+id);
 		List<Empleados> resultList0 = (List<Empleados>) x.getResultList();
 		List<Empleados> liq = resultList0;

 		Empleados emple = liq.get(0);

 		resultado.render("id", id);
 		String nombre = emple.getNombre_empleado();
 		resultado.render("nombre", nombre);
 		String apellido = emple.getApellido_empleado();
 		resultado.render("apellido", apellido);
 		String documento = emple.getDocumento_empleado();
 		resultado.render("documento", documento);
 		String cuil = emple.getCUIL_empleado();
 		resultado.render("cuil", cuil);
 		String telefono = emple.getTel_empleado();
 		resultado.render("telefono", telefono);
 		String celu = emple.getCel_empleado();
 		resultado.render("celu", celu);
 		String email = emple.getEmail_empleado();
 		resultado.render("email", email);
 		String horario = emple.getHorario_empleado();
 		resultado.render("horario", horario);
 		String edificio = emple.getId_edificio();
 		resultado.render("edificio", edificio);


     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/edit_empleados.ftl.html");

 		return resultado;

 	}

 	// FUNCION EDITAR EMPLEADO
 	@Transactional
 	public Result editarEmpleado(
 			@Param("id") String id,
 			@Param("nomNE") String nombre,
 			@Param("apeNE") String apellido,
 			@Param("docNE") String documento,
 			@Param("cuilNE") String cuil,
 			@Param("telNE") String telefono,
 			@Param("celNE") String celu,
 			@Param("emaNE") String email,
 			@Param("horNE") String horne,
 			@Param("ediNE") String edif){

 		Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		String update = "SELECT x FROM Empleados x WHERE id_empleado = "+id;

 		Query datos = entityManager.createQuery(update);
 		List<Empleados> empl = (List<Empleados>) datos.getResultList();

 		Empleados empleado = empl.get(0);

 		empleado.setNombre_empleado(nombre);
 		empleado.setApellido_empleado(apellido);
 		empleado.setDocumento_empleado(documento);
 		empleado.setCUIL_empleado(cuil);
 		empleado.setTel_empleado(telefono);
 		empleado.setCel_empleado(celu);
 		empleado.setEmail_empleado(email);
 		empleado.setHorario_empleado(horne);

 		Query x = entityManager.createQuery("SELECT x FROM Empleados x");
     	List<Empleados> resultList3 = (List<Empleados>) x.getResultList();
     	List<Empleados> emple = resultList3;

     	resultado.render("empleados", emple);

     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/empleados.ftl.html");

 		return resultado;

 	}  
 	
 // PANTALLA EDITAR GASTOS
 	@Transactional
  	public Result edit_gastos(
  							@Param("id") String id,
  							@Param("flag") String flag){

  		Result resultado = Results.html();

  		EntityManager entityManager = entityManagerProvider.get();
  		System.out.print("Flag: "+flag);
  		
  		if(flag.equals("True")){
  			
  			Query x = entityManager.createQuery("SELECT x FROM GastosGenerales x WHERE id = "+id);
  	  		List<GastosGenerales> resultList0 = (List<GastosGenerales>) x.getResultList();
  	  		List<GastosGenerales> liq = resultList0;
  	  		
  	  		GastosGenerales gastoact = liq.get(0);
  	  		
  	  		String fecha = gastoact.getPeriodo();
  	  		String[] dividoFecha = fecha.split("/");
  	  		String mes = dividoFecha[1];
  	  		String edificio = gastoact.getEdificio();
  	  		
  	  		// TRAIGO DE LIQUIDACIONES LAS QUERYS DEL PERIODO
  	  		String queryReAct = "SELECT x FROM Liquidaciones x WHERE Edificio = '"+edificio+"' AND Periodo = '"+dividoFecha[2]+"-"+mes+"-01 00:00:00'";
  	  		
  	  		Query z = entityManager.createQuery(queryReAct);
	  		List<Liquidaciones> resultList1 = (List<Liquidaciones>) z.getResultList();
	  		
	  		if(resultList1.size()>0){
	
		  		Liquidaciones liqAct = resultList1.get(0);
		  		
		  		String sGastos = liqAct.getSelectG();
		  		String sLiq = liqAct.getSelectL();
		  		
		  		//ACTUALIZO LOS GASTOS DEL PERIODO A LIQUIDADO = FALSE
		  		Query qq = entityManager.createQuery(sGastos);
	  	  		List<GastosGenerales> resultList2 = (List<GastosGenerales>) qq.getResultList();
	  	  		
	  	  		for (GastosGenerales gastosGenerales : resultList2) {
					
	  	  			gastosGenerales.setLiquidado("False");
	  	  			
				}
		  		
	  	  		//ACTUALIZO LAS LIQUIDACIONES DE SUELDO DEL PERIODO A LIQUIDADO = FALSE
		  		Query ww = entityManager.createQuery(sLiq);
	  	  		List<LiqSueldos> resultList3 = (List<LiqSueldos>) ww.getResultList();
	  		
	  	  		for (LiqSueldos liqSueldos : resultList3) {
					
	  	  			liqSueldos.setLiquidado("False");
	  	  			
				}
	  	  		
	  	  		// CORRIJO PRORRATEOS
	  	  		String queryPror = "SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = '"+edificio+"' AND Periodo = '"+dividoFecha[2]+"-"+mes+"-01 00:00:00'";
	  	  		
	  	  		Query ee = entityManager.createQuery(queryPror);
		  		List<EstadoCuentasYProrrateo> resultList4 = (List<EstadoCuentasYProrrateo>) ee.getResultList();
	
		  		for (EstadoCuentasYProrrateo estadoCuentasYProrrateo : resultList4) {
					String propie = "SELECT x FROM Propietarios x WHERE Edificio = '"+edificio+"' AND Num_Dto_prop = "+estadoCuentasYProrrateo.getUNI();
					
					Double total = estadoCuentasYProrrateo.getTotal();
					
					Query rr = entityManager.createQuery(propie);
		  	  		List<Propietarios> resultList5 = (List<Propietarios>) rr.getResultList();
		  	  		
		  	  		if(resultList5.size()>0){
		  	  		
		  	  			Propietarios propi = resultList5.get(0);
		  	  			propi.setSaldo_Ant(propi.getSaldo());
		  	  			propi.setPago(estadoCuentasYProrrateo.getTotal());
		  	  			Double saldoAct = propi.getSaldo() + estadoCuentasYProrrateo.getTotal_A() + estadoCuentasYProrrateo.getTotal_B() + estadoCuentasYProrrateo.getTotal_C();
		  	  			propi.setSaldo(saldoAct);	  	  		
		  	  		
		  	  			//ELIMINO EL REGISTRO DEL PRORRATEO
			  	  		entityManager.remove(estadoCuentasYProrrateo);
			  	  		
		  	  		}
		  	
		  		}
		  		
		  		// ELIMINO LAS QUERYS DE LIQUIDACIONES
		  		entityManager.remove(liqAct);  	  		
	  		}
  		} 
  		
  		Query x = entityManager.createQuery("SELECT x FROM GastosGenerales x WHERE id = "+id);
  		List<GastosGenerales> resultList0 = (List<GastosGenerales>) x.getResultList();
  		List<GastosGenerales> liq = resultList0;

  		GastosGenerales gasto = liq.get(0);

  		resultado.render("id", id);
  		
  		String fecha = gasto.getPeriodo();
  		resultado.render("fecha", fecha);
  		String edificio = gasto.getEdificio();
  		resultado.render("edificio", edificio);
  		String tipo = gasto.getTipo();
  		resultado.render("tipo", tipo);
  		String subtipoCate = gasto.getSubTipo();
  		resultado.render("subtipoCate", subtipoCate);
  		
  		String subtipoOrd = "";
  		String subtipoExt = "";
  		
  		if(tipo.equals("Ord")){
  			subtipoOrd = "selected='selected'";
  		} else if(tipo.equals("Ext")){
  			subtipoExt = "selected='selected'";
  		}
  		
  		resultado.render("subtipoOrd", subtipoOrd);
  		resultado.render("subtipoExt", subtipoExt);
  		
  		String unidad = gasto.getUnidad();
  		resultado.render("unidad", unidad);
  		String qqg = gasto.getQQG()+", "+gasto.getCUIT();
  		resultado.render("qqg", qqg);
  		System.out.println("\n=>"+qqg+"<=\n");
  		String nrofac = gasto.getNro_Factura();
  		resultado.render("nrofac", nrofac);
  		String detalle = gasto.getDetalle();
  		resultado.render("detalle", detalle);
  		String gasA = Double.toString(gasto.getGastosA());
  		resultado.render("gasA", gasA);
  		String gasB = Double.toString(gasto.getGastosB());
  		resultado.render("gasB", gasB);
  		String gasC = Double.toString(gasto.getGastosC());
  		resultado.render("gasC", gasC);
  		String gasT = Double.toString(gasto.getTotal());
  		resultado.render("gasT", gasT);
  		String liqui = gasto.getLiquidado();
  		resultado.render("liqui", liqui);
  		
  		resultado.render("liquidacion", liq);
  		
  		Query e = entityManager.createQuery("SELECT x FROM SubTipoGastos x");
    	List<SubTipoGastos> resultList4 = (List<SubTipoGastos>) e.getResultList();
    	List<SubTipoGastos> subt = resultList4;

    	resultado.render("subtipos", subt);
    	
    	Query w = entityManager.createQuery("SELECT x FROM Proveedores x");
    	List<Proveedores> resultList2 = (List<Proveedores>) w.getResultList();
    	List<Proveedores> prof = resultList2;

    	resultado.render("proveedores", prof);

      	resultado.render("usuario", Globals.usuarioActual);
      	resultado.render("categoria", Globals.categoriaUsuario);
      	resultado.template("views/ApplicationController/edit_gastos.ftl.html");

  		return resultado;

  	}
  	
 // FUNCION EDITAR EMPLEADO
  	@Transactional
  	public Result editarGasto(
  			@Param("id") String id,
  			@Param("selectQGG") String qgg,
			@Param("uniNG") String unidad,
			@Param("facNG") String fact,
			@Param("tipoGasto") String tipoGasto,
			@Param("selectCate") String selectCate,			
			@Param("detNG") String deta,
			@Param("impGA") String impGA,
			@Param("impGB") String impGB,
			@Param("impGC") String impGC,
			@Param("impTot") String impTot){

  		Result resultado = Results.html();

  		EntityManager entityManager = entityManagerProvider.get();

  		String update = "SELECT x FROM GastosGenerales x WHERE id = "+id;

  		Query datos = entityManager.createQuery(update);
  		List<GastosGenerales> empl = (List<GastosGenerales>) datos.getResultList();

  		GastosGenerales empleado = empl.get(0);
  		
  		System.out.print("\nQGG: "+qgg+"\n");
		
  		String[] separoQGG = qgg.split(",");
		qgg = separoQGG[0];
		String cuit = separoQGG[1];
		
		System.out.print("\nQGG: "+qgg+"\nCuit: "+cuit+"\n");
		
  		double importeA = 0;
		double importeB = 0;
		double importeC = 0;
		double importeT = 0;
		
		if(!impGA.equals("")){
			importeA = Double.parseDouble(impGA);
		}
		
		if(!impGB.equals("")){
			importeB = Double.parseDouble(impGB);
		}
		
		if(!impGC.equals("")){
			importeC = Double.parseDouble(impGC);
		}
		
		importeT = importeA + importeB + importeC;
		
		if(!qgg.equals("")){
			empleado.setQQG(qgg);
		}
		
		if(!cuit.equals("")){
			empleado.setCUIT(cuit);
		}
  		
		if(!unidad.equals("")){
			empleado.setUnidad(unidad);	
		}
  		
		if(!fact.equals("")){
			empleado.setNro_Factura(fact);	
		}
  		
		if(!tipoGasto.equals("")){
			empleado.setTipo(tipoGasto);	
		}
  		
		if(!selectCate.equals("")){
			empleado.setSubTipo(selectCate);	
		}
  		
		if(!deta.equals("")){
			empleado.setDetalle(deta);	
		}

  		empleado.setGastosA(importeA);
  		empleado.setGastosB(importeB);
  		empleado.setGastosC(importeC);
  		empleado.setTotal(importeT);

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

     	ArrayList<String> listaFiltros = new ArrayList<String>();
     	listaFiltros.add("SIN FILTROS");

     	resultado.render("filtros", listaFiltros);

      	resultado.render("usuario", Globals.usuarioActual);
      	resultado.render("categoria", Globals.categoriaUsuario);
      	resultado.template("views/ApplicationController/gastos_res.ftl.html");

  		return resultado;

  	}
  	
  	// FUNCION EDITAR EMPLEADO
  	@UnitOfWork
  	public Result edit_usuarios(
  			@Param("id") int id){

  		Result r = Results.html();

  		EntityManager entityManager = entityManagerProvider.get();

  		String update = "SELECT x FROM Usuarios x WHERE id_usuario = "+id;

  		Query q = entityManager.createQuery(update);
    	List<Usuarios> resultList = (List<Usuarios>) q.getResultList();
    	Usuarios user = resultList.get(0);
    	
    	String nombre = user.getNom_usuario();
    	String apellido = user.getApe_usuario();
    	String dni = user.getDni_usuario();
    	String direccion = user.getDire_usuario();
    	String telefono = user.getTel_usuario();
    	String email = user.getEmail_usuario();
    	String username = user.getUsername();
    	String contrase = user.getContrasena();
    	String cate = user.getCate_usuario();
    	
  		Query w = entityManager.createQuery("SELECT x FROM Usuarios x");
    	List<Usuarios> u = (List<Usuarios>) w.getResultList();
    	
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
    	
    	String accion = "Editar";
    	r.render("accion", accion);
    	
    	r.render("usuarios", u);
    	r.render("categoria", Globals.categoriaUsuario);
    	r.render("usuario", Globals.usuarioActual);
    	r.template("views/ApplicationController/usuarios.ftl.html");

		return r;
	} 

   	// FUNCION EDITAR EMPLEADO
  	@Transactional
  	public Result editarUsuario(
  			@Param("id") int id,
  			@Param("Unom") String nomb,
  			@Param("Uape") String apel,
  			@Param("Udni") String dni,
  			@Param("Udir") String direc,
  			@Param("Utel") String telef,
  			@Param("Uema") String emai,
  			@Param("Upass") String passw,
  			@Param("Ucate") String cate){

  		Result r = Results.html();

  		EntityManager entityManager = entityManagerProvider.get();

  		String update = "SELECT x FROM Usuarios x WHERE id_usuario = "+id;

  		Query q = entityManager.createQuery(update);
    	List<Usuarios> resultList = (List<Usuarios>) q.getResultList();
    	Usuarios usuario = resultList.get(0);
    	
    	usuario.setNom_usuario(nomb);
    	usuario.setApe_usuario(apel);
    	usuario.setDni_usuario(dni);
    	usuario.setDire_usuario(direc);
    	usuario.setTel_usuario(telef);
    	usuario.setEmail_usuario(emai);
    	usuario.setContrasena(passw);
    	usuario.setCate_usuario(cate);

  		Query w = entityManager.createQuery("SELECT x FROM Usuarios x");
    	List<Usuarios> resultList0 = (List<Usuarios>) w.getResultList();
    	List<Usuarios> u = resultList0;
    	
    	nomb = "";
    	apel = "";
    	dni = "";
    	direc = "";
    	telef = "";
    	emai = "";
    	String username = "";
    	String contrase = "";
    	cate = "";
    	id = 0;
    	
    	r.render("nombre", nomb);
    	r.render("apellido", apel);
    	r.render("dni", dni);
    	r.render("direccion", direc);
    	r.render("telefono", telef);
    	r.render("email", emai);
    	r.render("userna", username);
    	r.render("contras", contrase);
    	r.render("cate", cate);
    	r.render("id", id);
    	
    	String accion = "Cargar";
    	r.render("accion", accion);
    	
    	r.render("usuarios", u);
    	r.render("categoria", Globals.categoriaUsuario);
    	r.render("usuario", Globals.usuarioActual);
    	r.template("views/ApplicationController/usuarios.ftl.html");

		return r;
	} 

  	// FUNCION EDITAR MENSAJE
  	@Transactional
   	public Result editarMensaje(
   			@Param("id") int id){
  		
  		Result resultado = Results.html();

  		EntityManager entityManager = entityManagerProvider.get();
  	
  		String query = "SELECT x FROM Mensajes x WHERE id = "+id+" AND contactado = 'False'";
  		Query w = entityManager.createQuery(query);
    	List<Mensajes> resultList0 = (List<Mensajes>) w.getResultList();
  		
    	if(resultList0.size() == 1){
    	
    		Mensajes mensaje = resultList0.get(0);
      		mensaje.setContactado("True");
      		
    	}
  		
  		Query q = entityManager.createQuery("SELECT x FROM Mensajes x");
    	List<Mensajes> resultList = (List<Mensajes>) q.getResultList();
    	List<Mensajes> m = resultList;

    	resultado.render("mensajes", m);
    	resultado.render("query", query);
  		
  		resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.template("views/ApplicationController/mensajes.ftl.html");
  		
  		return resultado;
  	
  	}
  	
  	// FUNCION EDITAR EMPLEADO
  	@UnitOfWork
	public Result edit_Subtipo(
			@Param("id") int id){
	
  		System.out.println(id);
   		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

    	Query q = entityManager.createQuery("SELECT x FROM Gastos x");
    	List<SubTipoGastos> resultList = (List<SubTipoGastos>) q.getResultList();
    	List<SubTipoGastos> m = resultList;
    
    	resultado.render("subtipos", m);
    	
    	String query = null;
    	
    	if(id > 0){
    		query = "SELECT x FROM SubTipoGastos x WHERE id = "+id;
    	} else {
    		query = "SELECT x FROM SubTipoGastos x";
    	}
    	
    	System.out.println(query);
    	
    	Query e = entityManager.createQuery(query);
    	List<SubTipoGastos> resultList0 = (List<SubTipoGastos>) e.setMaxResults(1).getResultList();
    	SubTipoGastos subtipo = resultList0.get(0);
    	
    	String estado = "editar";
    	resultado.render("estado", estado);
    	
    	int maxNum = subtipo.getNum_subtipo();
		
    	resultado.render("numNS", maxNum);
    	
    	String nom = subtipo.getNombre_subtipo();
    	resultado.render("nomNS", nom);
    	
    	int idS = id;
    	resultado.render("idS", idS);
    	
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/subtipo_gastos.ftl.html");

		return resultado;

  	}
  	
  	// FUNCION EDITAR SUBTIPO
  	@Transactional
   	public Result editarSubtipo(
   			@Param("numNS") int numNS,
   			@Param("nomNS") String nomNS,
   			@Param("id") int id){
  		
  		Result resultado = Results.html();

  		EntityManager entityManager = entityManagerProvider.get();
  	
  		String query = null;
    	
    	if(id > 0){
    		query = "SELECT x FROM SubTipoGastos x WHERE id = "+id;
    	} else {
    		query = "SELECT x FROM SubTipoGastos x";
    	}
    	
    	System.out.println(query);
    	
    	Query e = entityManager.createQuery(query);
    	List<SubTipoGastos> resultList0 = (List<SubTipoGastos>) e.setMaxResults(1).getResultList();
    	SubTipoGastos subtipo = resultList0.get(0);
    	
    	if(!nomNS.equals("")){
    		subtipo.setNombre_subtipo(nomNS);
    	}
    	
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
  	

  
    
    //
}
