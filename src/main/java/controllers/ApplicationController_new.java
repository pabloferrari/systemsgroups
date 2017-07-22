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
import ninja.params.Param;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.text.ParseException;
import java.util.ArrayList;
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
import models.Liquidacion;
import models.Liquidaciones;
import models.Mensajes;
import models.Propietarios;
import models.Proveedores;
import models.SubTipoGastos;
import models.Usuarios;



@Singleton
public class ApplicationController_new {
	
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
    
    @Transactional
    public void funcionNuevoGasto(int id, Date periodo, String edificio, String tipo, int idsubtipo, String subtipo, String unidad, String qqg, String cuit, String detalle, String factura, double gastosA, double gastosB, double gastosC, double gastosextra, double total, String liqui){
    	
    	EntityManager entityManagerNG = entityManagerProvider.get();
    	GastosGenerales gastosNG;
    	gastosNG = new GastosGenerales(id, periodo, edificio, tipo, idsubtipo, subtipo, unidad, qqg, cuit, detalle, factura, gastosA, gastosB, gastosC, gastosextra, total, liqui);
    	entityManagerNG.persist(gastosNG);
    	
    }
    
    // NUEVO CONSORCIO
    @Transactional
	public Result NuevoConsorcio(
			@Param("nomNC") String nom,
			@Param("dirNC") String dir,
			@Param("cantNC") String cant,
			@Param("saldoNC") String sald){


		Result resultado = Results.html();

		if (nom.equals("") || dir.equals("") || cant.equals("")){

			EntityManager entityManager = entityManagerProvider.get();

			Query q = entityManager.createQuery("SELECT x FROM Consorcios x");
			List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
			List<Consorcios> c = resultList;


			resultado.render("consorcios", c);
			resultado.render("usuario", Globals.usuarioActual);
			resultado.render("categoria", Globals.categoriaUsuario);
			resultado.template("views/ApplicationController/consorcios.ftl.html");

			return resultado;

		} else {

			EntityManager entityManager = entityManagerProvider.get();

			Consorcios consorcioN;

			int cantidad = Integer.parseInt(cant);
			double saldo = Double.parseDouble(sald);
			
			String cuit = "30-50111222-0";
			String clave = "8888";

			consorcioN = new Consorcios(nom, dir, cantidad, saldo, cuit, clave);

			entityManager.persist(consorcioN);

			Query q = entityManager.createQuery("SELECT x FROM Consorcios x");
			List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
			List<Consorcios> u = resultList;

			resultado.render("consorcios", u);
			resultado.render("usuario", Globals.usuarioActual);
			resultado.render("categoria", Globals.categoriaUsuario);
			resultado.template("views/ApplicationController/consorcios.ftl.html");

			return resultado;
		}

	}
    
    // NUEVO PROPIETARIO
	@Transactional
	public Result NuevoPropietario(
			@Param("nomNP") String nombre,
 			@Param("apeNP") String apellido,
 			@Param("docNP") String documento,
 			@Param("direNP") String direccion,
 			@Param("telNP") String telefono,
 			@Param("emaNP") String email,
 			@Param("numNP") int numDto,
 			@Param("uniNP") String unid,
 			@Param("inqNP") String inqui,
 			@Param("expNP") Double exp,
 			@Param("expExtNP") Double expE,
 			@Param("ediNP") String edificio){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

		Propietarios propietarioN;
		
		Double saldo = (double) 0;
		Double pago = (double) 0;
		Double saldo_ant = (double) 0;
		
		if(numDto == 0){
			System.out.println("NUMERO DE DEPARTAMENTO = "+numDto);
		} else {
			
			propietarioN = new Propietarios(nombre, apellido, documento, direccion, telefono, email, edificio, numDto, unid, inqui, exp, expE, saldo, pago, saldo_ant);
			entityManager.persist(propietarioN);
		
		}
		
		Query q = entityManager.createQuery("SELECT x FROM Propietarios x");
		List<Propietarios> resultList = (List<Propietarios>) q.getResultList();
		List<Propietarios> p = resultList;

		resultado.render("propietarios", p);
		resultado.render("usuario", Globals.usuarioActual);
		resultado.render("categoria", Globals.categoriaUsuario);
		resultado.template("views/ApplicationController/propietarios.ftl.html");

		return resultado;
		
	}
	
	// NUEVO PROVEEDOR
	@Transactional
	public Result NuevoProveedor(
			@Param("selectProv") String select,
			@Param("nomNPr") String nom,
			@Param("cuitNPr") String cuit,
			@Param("detNPr") String deta,
			@Param("tipNPr") String tipo,
			@Param("dirNPr") String dire,
			@Param("telNPr") String tele,
			@Param("celNPr") String celu,
			@Param("emaNPr") String email){


		Result resultado = Results.html();

		

			EntityManager entityManager = entityManagerProvider.get();

			Proveedores proveedorN;
			deta = deta+" - Contacto: "+celu;
			proveedorN = new Proveedores(nom, cuit, tipo, deta, dire, tele, celu, email);
			entityManager.persist(proveedorN);

	    	Query w = entityManager.createQuery("SELECT x FROM Proveedores x");
	    	List<Proveedores> resultList2 = (List<Proveedores>) w.getResultList();
	    	List<Proveedores> prof = resultList2;

	    	resultado.render("proveedores", prof);

			resultado.render("usuario", Globals.usuarioActual);
			resultado.render("categoria", Globals.categoriaUsuario);
			resultado.template("views/ApplicationController/proveedores.ftl.html");

			return resultado;
		
	}
    
	// NUEVO GASTO
	@Transactional
	public Result NuevoGasto(
			@Param("selectEdif") String select,
			@Param("selectQGG") String qgg,
			@Param("selectUnidad") String unidad,
			@Param("fecNG") String strFecha,
			@Param("facNG") String fact,
			@Param("tipoGasto") String tipoGasto,
			@Param("selectCate") String subtipo,			
			@Param("detNG") String deta,
			@Param("impGA") String impGA,
			@Param("impGB") String impGB,
			@Param("impGC") String impGC,
			@Param("impTot") String impTot,
			@Param("couNG") String cuo) throws java.text.ParseException{


		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

		if(unidad.equals("*")){
			unidad = "0";
		} else {
			
			deta += " - Unidad Funcional: "+unidad;
			
		}
		String liq = "false";
		
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
		
		if(!impTot.equals("")){
			importeT = Double.parseDouble(impTot);
		}
		
		double importeGE = 0; // GASTOS EXTRAS NO INSERTA
		
		// VERIFICO LAS CUOTAS
		
		int cuota = 1;
		
		if(!cuo.equals("")){
			cuota = Integer.parseInt(cuo);
		}
		
		// DIVIDO LOS COSTOS
		double[] importesA = new double[cuota];
		double[] importesB = new double[cuota];
		double[] importesC = new double[cuota];
		
		double finA = 0;
		double finB = 0;
		double finC = 0;
		double aux_fin = 0;
		
		if(cuota > 1){
			
			for (int var = 0; var < (cuota-1); var++) {
				
				importesA[var] = Globals.redondear((importeA/cuota));
				finA = finA + importesA[var];
				importesB[var] = Globals.redondear((importeB / cuota));
				finB = finB + importesB[var];
				importesC[var] = Globals.redondear((importeC / cuota));
				finC = finC + importesC[var];
				
			}
			
			importesA[cuota-1] = importeA - finA;
			importesB[cuota-1] = importeB - finB;
			importesC[cuota-1] = importeC - finC;
			
		}
		
		String[] separoSubTipo = subtipo.split("-");
		subtipo = separoSubTipo[1];
		int idsubtipo;
		idsubtipo = Globals.StringToInt(separoSubTipo[0]);
		
		System.out.print("\nQGG: "+qgg+"\n");
		String[] separoQGG = qgg.split(",");
		qgg = separoQGG[0];
		String cuit = separoQGG[1];
		
		System.out.print("\nQGG: "+qgg+"\nCuit: "+cuit+"\n");
		
		// BUSCO ID MAS ALTO 
		Query Qid = entityManager.createQuery("SELECT MAX(id) FROM GastosGenerales x");
		int maxId;
		
		if(Qid.getSingleResult().equals(null)){
			maxId = 0;
		} else {
			maxId = (int) Qid.getSingleResult();
		}
		
    	int id = maxId;
    	Date fecha = null;
    	
    	// GUARDO DETALLE
    	String detalle_aux = deta;
    	
    	for (int i = 0; i < cuota; i++) {
			
    		id += 1;
    		
    		if(cuota == 1){
    			
    			// INICIO PARSE FECHA
    			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
   		    
    		    fecha = (Date) formatoDelTexto.parse(strFecha);
    			// FIN PARSE FECHA
    			
    		} else {
    			
    			// INICIO PRUEBA CUOTAS
    		    String[] soloFecha = strFecha.split(" ");
    			String fechaDate = soloFecha[0];
    			
    			// SERADO AÑO, MES, DIA
    			String[] separoFecha = fechaDate.split("-");
    			
    			int mes = Integer.parseInt(separoFecha[1]);
    			mes = mes + i;

    			if(mes>12){

    				int anio = Integer.parseInt(separoFecha[0]);
    				anio = anio + 1;
    				mes = mes - 12;
    				separoFecha[0] = String.valueOf(anio);

    			}
    			
    			if(mes < 10){
    				separoFecha[1] = "0";
    				separoFecha[1] += String.valueOf(mes);	
    			}else{
    				separoFecha[1] = String.valueOf(mes);	
    			}				

    			String nuevaFecha = String.valueOf(separoFecha[0]);
    			nuevaFecha += "-";				
    			nuevaFecha += String.valueOf(separoFecha[1]);
    			nuevaFecha += "-";
    			nuevaFecha += String.valueOf(separoFecha[2]);
    			
    			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
       		    
    		    fecha = (Date) formatoDelTexto.parse(nuevaFecha);
    			// FIN PRUEBA CUOTAS
    			
    		    // SETEO VALORES DE CADA CUOTA Y TOTAL
    		    importeA = importesA[i];
        		importeB = importesB[i];
        		importeC = importesC[i];
        		importeT = importeA + importeB + importeC;
        		
        		// AGREGO LAS CUOTAS AL DETALLE
        		deta = detalle_aux+" - Cuota "+(i+1)+"/"+cuota;
        		
    		} 		
	    	
			funcionNuevoGasto(id, fecha, select, tipoGasto, idsubtipo, subtipo, unidad, qgg, cuit, fact, deta, importeA, importeB, importeC, importeGE, importeT, liq);
    		
    		
		}
    	
    	ArrayList<String> listaFiltros = new ArrayList<String>();
    	listaFiltros.add("SIN FILTROS");

    	resultado.render("filtros", listaFiltros);

    	Query g = entityManager.createQuery("SELECT x FROM GastosGenerales x");
		List<GastosGenerales> resultList0 = (List<GastosGenerales>) g.getResultList();
		List<GastosGenerales> gasto = resultList0;

		resultado.render("gastos", gasto);
		
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

		resultado.render("fecha", data);
    	resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/gastos.ftl.html");

		return resultado;

	}
	
	// NUEVO EMPLEADO
	@Transactional
	public Result NuevoEmpleado(
			@Param("selectEdif") String select,
			@Param("nomNE") String nom,
			@Param("apeNE") String ape,
			@Param("docNE") String doc,
			@Param("cuilNE") String cuit,
			@Param("telNE") String tel,
			@Param("celNE") String cel,
			@Param("emaNE") String ema,
			@Param("horNE") String hora){


		Result resultado = Results.html();

		if (select.equals("*")){

			EntityManager entityManager = entityManagerProvider.get();

			Query q = entityManager.createQuery("SELECT x FROM Consorcios x");
			List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
			List<Consorcios> c = resultList;

			resultado.render("consorcios", c);

			resultado.render("usuario", Globals.usuarioActual);
			resultado.render("categoria", Globals.categoriaUsuario);
			resultado.template("views/ApplicationController/empleados_new.ftl.html");

			return resultado;

		} else {

			EntityManager entityManager = entityManagerProvider.get();

			Empleados empleadoN;

			empleadoN = new Empleados(nom, ape, doc, cuit, tel, cel, ema, hora, select);

			entityManager.persist(empleadoN);


			Query x = entityManager.createQuery("SELECT x FROM Empleados x");
	    	List<Empleados> resultList3 = (List<Empleados>) x.getResultList();
	    	List<Empleados> emple = resultList3;

	    	resultado.render("empleados", emple);

			resultado.render("usuario", Globals.usuarioActual);
			resultado.render("categoria", Globals.categoriaUsuario);
			resultado.template("views/ApplicationController/empleados.ftl.html");

			return resultado;
		}

	}
	
	// NUEVA LIQUIDACION SUELDO
	@Transactional
	public Result NuevaLiquidacionSueldo(
			@Param("mes") String mes,
			@Param("anio") String anio,
			@Param("selectEmple") String emple,
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

		LiqSueldos sueldoNS;

		String[] separo_emple = emple.split(" ");
		emple = separo_emple[0]+" "+separo_emple[1];
		String edif = separo_emple[2];

		// INICIO PARSE FECHA
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");

	    Date fecha = null;
	    String strFecha;
	    strFecha = anio+"-"+mes+"-01";
	    fecha = (Date) formatoDelTexto.parse(strFecha);

		// FIN PARSE FECHA

	    sueldoNS = new LiqSueldos(emple, edif, su_basico, su_presen, su_antigu, su_sac, su_vacac, su_horExt, su_bruto, su_jubil, su_obsoc, su_art, su_ganan, su_afip, su_suter, su_fater, su_serac, su_otd, su_neto, fecha, liq);

	    entityManager.persist(sueldoNS);

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
    	List<Empleados> emplea = resultList1;

    	resultado.render("empleados", emplea);

    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	String data = df.format(new Date());

    	ArrayList<String> listaFiltros = new ArrayList<String>();
    	listaFiltros.add("SIN FILTROS");

    	resultado.render("filtros", listaFiltros);
    	resultado.render("fecha", data);
		resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	resultado.template("views/ApplicationController/liq_sueldos.ftl.html");

		return resultado;

	}
	
	// NUEVA LIQUIDACION SUELDO
	@Transactional
	public Result NuevoUsuario(
		@Param("Unom") String nombre,
		@Param("Uape") String apellido,
		@Param("Udir") String dire,
		@Param("Utel") String tel,
		@Param("Udni") String dni,
		@Param("Uema") String email,
		@Param("Uuse") String username,
		@Param("Upass") String pass,
		@Param("Ucate") String cate,
		@Param("accion") String accion,
		@Param("id") String id){
	
		Result r = Results.html();
	
		EntityManager entityManager = entityManagerProvider.get();
				
		Usuarios usuariosN;
		
		usuariosN = new Usuarios(nombre, apellido, dni, dire, tel, email, username, pass, cate);
		entityManager.persist(usuariosN);
				
		    	
    	nombre = "";
    	apellido = "";
    	dni = "";
    	dire = "";
    	tel = "";
    	email = "";
    	username = "";
    	pass = "";
    	cate = "";
    
		Query q = entityManager.createQuery("SELECT x FROM Usuarios x");
    	List<Usuarios> resultList1 = (List<Usuarios>) q.getResultList();
    	List<Usuarios> u = resultList1;

    	int id_ = 0;
    	
    	r.render("nombre", nombre);
    	r.render("apellido", apellido);
    	r.render("dni", dni);
    	r.render("direccion", dire);
    	r.render("telefono", tel);
    	r.render("email", email);
    	r.render("userna", username);
    	r.render("contras", pass);
    	r.render("cate", cate);
    	r.render("id", id);
  
    	r.render("accion", accion);
    	
    	r.render("usuarios", u);
    	r.render("categoria", Globals.categoriaUsuario);
    	r.render("usuario", Globals.usuarioActual);
    	r.template("views/ApplicationController/usuarios.ftl.html");
    	
    	return r;
		
	}
	
	// NUEVA LIQUIDACION SUELDO
	@Transactional
	public Result NuevaLiquidacion(
			@Param("liq_sueldo") String liqSueldo,
			@Param("gastos_tot") String gasTotales,
			@Param("mes") String mes,
			@Param("anio") String anio,
			@Param("edificio") String edificio,
			@Param("consulta_total") String consulta_total) throws ParseException{
		
		
		System.out.println(liqSueldo);
		System.out.println(gasTotales);
		
		Result resultado = Results.html();
		
		EntityManager entityManager = entityManagerProvider.get();
		
		Query q = entityManager.createQuery("SELECT MAX(id) FROM Liquidacion x");
		int maxId;
		
		if(q.getSingleResult().equals(null)){
			maxId = 0;
		} else {
			maxId = (int) q.getSingleResult();
		}
		
		Query w = entityManager.createQuery("SELECT MAX(identificador) FROM Liquidacion x");
		int maxIdenti;
		
		if(w.getSingleResult().equals(null)){
			maxIdenti = 0;
		} else {
			maxIdenti = (int) w.getSingleResult();
		}
		
		Liquidacion nuevaLiq;
		
		String nuevaFecha_ = null;
		Date fecha_;
		String tipogasto = null;
		int nrogasto;
		String detalle;
		double gastosA, gastosB, gastosC, total;
		int idgasto;
		
		int mes_ = Globals.obtenerMes_(mes);
		if(mes_<10){
			nuevaFecha_ = "0"+mes_;
		} else {
			nuevaFecha_ = Globals.IntToString(mes_);
		}
		
		String  vieja_fecha = Globals.obtenerMes(nuevaFecha_)+" "+anio;
		nuevaFecha_ = anio+"-"+nuevaFecha_+"-01 00:00:00";
		fecha_ = Globals.StrtoFecha(nuevaFecha_);
		
		////////////////////////
		// TRAIGO DATOS DE LA LIQUIDACIÓN
		////////////////////////
		Query e = entityManager.createQuery(liqSueldo);
		List<LiqSueldos> resultList0 = (List<LiqSueldos>) e.getResultList();
		List<LiqSueldos> liqS = resultList0;
		
		maxIdenti = maxIdenti + 1;
		
		for (LiqSueldos liqSueldos : liqS) {
			
			maxId += 1;
			edificio = liqSueldos.getEdificio();
			tipogasto = "Liq Sueldo"; 
			nrogasto = 1;
			detalle = "Basico "+liqSueldos.getEmpleado()+" $ "+liqSueldos.getSueldo_basico();
			gastosA = 0;
			gastosB = liqSueldos.getSueldo_basico();
			gastosC = 0;
			total = gastosB;
			idgasto = liqSueldos.getId_sueldo_empleado();
			
			nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
			entityManager.persist(nuevaLiq);
			
			if(liqSueldos.getPresentismo()>0){
				maxId += 1;
				detalle = "Presentismo  $ "+liqSueldos.getPresentismo();
				gastosB = liqSueldos.getPresentismo();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getAntiguedad()>0){
				maxId += 1;
				detalle = "Antigüedad   $ "+liqSueldos.getAntiguedad();
				gastosB = liqSueldos.getAntiguedad();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getSac()>0){
				maxId += 1;
				detalle = "SAC  $ "+liqSueldos.getSac();
				gastosB = liqSueldos.getSac();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getVacaciones_ng()>0){
				maxId += 1;
				detalle = "Vacaciones No Gozadas  $ "+liqSueldos.getVacaciones_ng();
				gastosB = liqSueldos.getVacaciones_ng();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getHoras_extras()>0){
				maxId += 1;
				detalle = "Horas Extras  $ "+liqSueldos.getHoras_extras();
				gastosB = liqSueldos.getHoras_extras();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
						
			if(liqSueldos.getJubilacion()>0){
				maxId += 1;
				detalle = "Jubilación 11%  Período: "+mes+" "+anio+"  $ "+liqSueldos.getJubilacion();
				gastosB = liqSueldos.getJubilacion();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getObra_social()>0){
				maxId += 1;
				detalle = "Obra Social 3%  Período: "+mes+" "+anio+"  $ "+liqSueldos.getObra_social();
				gastosB = liqSueldos.getObra_social();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getART()>0){
				maxId += 1;
				detalle = "ART - CCT 589/10 Período: "+mes+" "+anio+"  $ "+liqSueldos.getART();
				gastosB = liqSueldos.getART();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getImpuesto_ganancias()>0){
				maxId += 1;
				detalle = "Impuesto Ganancias  Período: "+mes+" "+anio+"  $ "+liqSueldos.getImpuesto_ganancias();
				gastosB = liqSueldos.getImpuesto_ganancias();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getAFIP()>0){
				maxId += 1;
				detalle = "AFIP F931  Período: "+mes+" "+anio+"  $ "+liqSueldos.getAFIP();
				gastosB = liqSueldos.getAFIP();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getSuterh()>0){
				maxId += 1;
				detalle = "Suterh  Período: "+mes+" "+anio+"  $ "+liqSueldos.getSuterh();
				gastosB = liqSueldos.getSuterh();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getFateryh()>0){
				maxId += 1;
				detalle = "Fateryh  Período: "+mes+" "+anio+"  $ "+liqSueldos.getFateryh();
				gastosB = liqSueldos.getFateryh();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getSeracarh()>0){
				maxId += 1;
				detalle = "Seracarh  Período: "+mes+" "+anio+"  $ "+liqSueldos.getSeracarh();
				gastosB = liqSueldos.getSeracarh();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			if(liqSueldos.getOtros_Items()>0){
				maxId += 1;
				detalle = "Otros Descuentos  $ "+liqSueldos.getOtros_Items();
				gastosB = liqSueldos.getOtros_Items();
				total = gastosB;
				
				nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
				entityManager.persist(nuevaLiq);
			}
			
			// CIERRO LA LIQUIDACIÓN
			liqSueldos.setLiquidado("True");
			
		
		}
		
		////////////////////////
		// TRAIGO DATOS DE LOS GASTOS
		////////////////////////
		Query r = entityManager.createQuery(gasTotales);
		List<GastosGenerales> resultList1 = (List<GastosGenerales>) r.getResultList();
		List<GastosGenerales> gastosS = resultList1;
		
		for (GastosGenerales gastosGenerales : gastosS) {
			
			maxId += 1;
			tipogasto = "Gasto";
			nrogasto = gastosGenerales.getIdSubtipo();
			detalle = gastosGenerales.getQQG()+" | CUIT: "+gastosGenerales.getCUIT()+" | Factura: "+gastosGenerales.getNro_Factura()+" - "+gastosGenerales.getDetalle();
			gastosA = gastosGenerales.getGastosA();
			gastosB = gastosGenerales.getGastosB();
			gastosC = gastosGenerales.getGastosC();
			total = gastosGenerales.getTotal();
			idgasto = gastosGenerales.getId();
			
			nuevaLiq = new Liquidacion(maxId, maxIdenti, fecha_, edificio, tipogasto, nrogasto, detalle, gastosA, gastosB, gastosC, total, idgasto);
			entityManager.persist(nuevaLiq);
			
			// CIERRO EL GASTO
			// CIERRO LA LIQUIDACIÓN
			gastosGenerales.setLiquidado("True");
		}
		
		String auxI = "";
		String auxII = "";
		String auxIII = "";
		
		String[] reconstruyo = gasTotales.split("Liquidado = 'False'");
		gasTotales = reconstruyo[0]+"Liquidado = 'True'"+reconstruyo[1];
		reconstruyo = liqSueldo.split("Liquidado = 'False'");
		liqSueldo = reconstruyo[0]+"Liquidado = 'True'";
		reconstruyo = consulta_total.split("Liquidado = 'False'");
		consulta_total = reconstruyo[0]+"Liquidado = 'True'"+reconstruyo[1];
		
		Liquidaciones nuevaLiqExp;
		nuevaLiqExp = new Liquidaciones(fecha_, edificio, gasTotales, liqSueldo, consulta_total, auxI, auxII, auxIII);
		entityManager.persist(nuevaLiqExp);
		
		Query qwe = entityManager.createQuery("SELECT MAX(id) FROM Liquidaciones x");
		int id_liq;
		
		if(qwe.getSingleResult().equals(null)){
			id_liq = 0;
		} else {
			id_liq = (int) qwe.getSingleResult();
		}
		
		resultado.render("fecha", vieja_fecha);
		resultado.render("edificio", edificio);
		resultado.render("id", id_liq);
		
		resultado.render("usuario", Globals.usuarioActual);
    	resultado.render("categoria", Globals.categoriaUsuario);
    	
    	// PRUEBA GENERAR PRORRATEO
   
    	NuevoProrrateo(id_liq,nuevaFecha_,edificio);
    	resultado.template("views/ApplicationController/home.ftl.html");
    	
    	// FIN PRUEBA GENERAR PRORRATEO
    	
    	//resultado.template("views/ApplicationController/generarProrrateo.ftl.html");
    	
    	
    	
		return resultado;
		
	}
	
	// PANTALLA LIQUIDACIONES
    @Transactional
	public Result NuevoProrrateo(
			@Param("id") int id,
			@Param("periodo") String periodo_,
			@Param("edificio") String edificio) throws java.text.ParseException{

    	Result resultado = Results.html();

 		EntityManager entityManager = entityManagerProvider.get();

 		Query c = entityManager.createQuery("SELECT x FROM Liquidaciones x WHERE id = '"+id+"'");
 		List<Liquidaciones> resultList1 = (List<Liquidaciones>) c.getResultList();
 		List<Liquidaciones> liquidacion = resultList1;
 		
 		Liquidaciones liq = liquidacion.get(0);
 		
 		double totalA = (double) 0;
 		double totalB = (double) 0;
 		double totalC = (double) 0;
 		double totalT = (double) 0;
 		double aux = (double) 0;
 		
 		// TRAIGO SUMA DE GASTOS POR TIPO
 		String queryGastos = liq.getSelectG();
 		String queryLiqSu = liq.getSelectL();
 		
 		String[] qGas = queryGastos.split("ELECT x FROM");
 		
 		queryGastos = "SELECT SUM(GastosA) FROM"+qGas[1];
 		Query qqq = entityManager.createQuery(queryGastos);
 		totalA = (double) qqq.getSingleResult();
 		
 		queryGastos = "SELECT SUM(GastosB) FROM"+qGas[1];
 		qqq = entityManager.createQuery(queryGastos);
 		totalB = (double) qqq.getSingleResult();
 		
 		queryGastos = "SELECT SUM(GastosC) FROM"+qGas[1];
 		qqq = entityManager.createQuery(queryGastos);
 		totalC = (double) qqq.getSingleResult();
 		
 		queryGastos = "SELECT SUM(Total) FROM"+qGas[1];
 		qqq = entityManager.createQuery(queryGastos);
 		totalT = (double) qqq.getSingleResult();
 		
 		// TRAIGO SUMA DE LIQUIDACIONES
 		String[] qLiq = queryLiqSu.split("ELECT x FROM");
 		
 		queryLiqSu = "SELECT SUM(Total_bruto) FROM"+qLiq[1];
 		qqq = entityManager.createQuery(queryLiqSu);
 		
 		try {
 			totalB += (double) qqq.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
 		//totalB += (double) qqq.getSingleResult();
 		
 		Query sumaPorcent = entityManager.createQuery("SELECT SUM(exp_porc) FROM Propietarios WHERE Edificio = '"+edificio+"'");
 		double sumaPor = (double) sumaPorcent.getSingleResult();
 		
 		if(sumaPor != 100){
 			
 			System.out.println("El porcentaje de expensas no es 100%, es: "+sumaPor);
 			
 		} else {
 			
 			Query Qid = entityManager.createQuery("SELECT MAX(id) FROM EstadoCuentasYProrrateo x");
 			int maxId;
 			
 			if(Qid.getSingleResult().equals(null)){
 				maxId = 0;
 			} else {
 				maxId = (int) Qid.getSingleResult();
 			}
 			
 			Qid = entityManager.createQuery("SELECT MAX(identificador) FROM EstadoCuentasYProrrateo x");
 			int maxIdenti;
 			
 			if(Qid.getSingleResult().equals(null)){
 				maxIdenti = 0;
 			} else {
 				maxIdenti = (int) Qid.getSingleResult();
 			}
 			
 			String query_pror = "SELECT x FROM Propietarios x WHERE Edificio = '"+edificio+"'";
 	 		Query ppp = entityManager.createQuery(query_pror);
 	 		List<Propietarios> prop = ppp.getResultList();
 	 		
 	 		for (Propietarios propietarios : prop) {
 				
 	 			EstadoCuentasYProrrateo estado;
 	 			
 	 			maxId=maxId+1;
 	 			int uni = propietarios.getNumeroDto();
 	 			String dto = propietarios.getUnidad();
 	 			String propie = propietarios.getNombre()+" "+propietarios.getApellido();
 	 			double ant = propietarios.getSaldo_Ant();
 	 			double pagAnt = propietarios.getPago();
 	 			double salPen = propietarios.getSaldo();
 	 			if(salPen<0){
 	 				salPen = -salPen;
 	 			}
 	 			double intereses = salPen * 0.02;
 	 			double porA = propietarios.getExp();
 	 			double impA = (totalA * porA)/100;
 	 			double porB = propietarios.getExp();
 	 			double impB = (totalB * porB)/100;
 	 			double porC = propietarios.getExp();
 	 			double impC = (totalC * porC)/100;
 	 			double total = salPen + impA + impB + impC;
 	 			double redondeo = Globals.redondear(total - Math.round(total));
 	 			total = total + redondeo;
 	 			Date fecha = Globals.StrtoFecha(periodo_);
 	 			
 	 			estado = new EstadoCuentasYProrrateo(maxId, maxIdenti, uni, dto, propie, ant, pagAnt, salPen, intereses, porA, impA, porB, impB, porC, impC, redondeo, total, edificio, fecha);
 	 			entityManager.persist(estado);
 	 			
 	 			double cero = 0;
 	 			propietarios.setSaldo_Ant(salPen);
 	 			propietarios.setPago(cero);
 	 			propietarios.setSaldo(-total);
 	 			
 	 			
 			}
 	 		
 		} 		
 		
 		boolean muestro = false;
 		resultado.render("muestro", muestro);
 		
     	//resultado.render("usuario", Globals.usuarioActual);
     	//resultado.render("categoria", Globals.categoriaUsuario);
     	//resultado.template("views/ApplicationController/prorrateos.ftl.html");

 		return resultado;
 	}
	
	// NUEVO EMPLEADO
	@Transactional
	public Result NuevoMovimiento(
			@Param("Cfec") String fec,
			@Param("selectEdif") String id,
			@Param("Cunid") String unid,
			@Param("Cmov") String movim,
			@Param("Cimp") double imp,
			@Param("Cdet") String deta,
			@Param("Cpag") String pag) throws ParseException{


		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

		Query q = entityManager.createQuery("SELECT x FROM Consorcios x WHERE id = "+id);
    	List<Consorcios> resultList = (List<Consorcios>) q.getResultList();
    	Consorcios consorcio;
    	consorcio = resultList.get(0);
    	
    	Date fecha = Globals.StrtoFecha(fec);
    	String edificio = consorcio.get_NombreCons();
   
    	double importe = imp;
    	double saldo = consorcio.getSaldo();
    	double saldo_anterior = saldo;
    	double saldo_final;
    	
    	Query w = entityManager.createQuery("SELECT x FROM Usuarios x WHERE nom_usuario = '"+Globals.usuarioActual+"'");
    	List<Usuarios> Usuarios = (List<Usuarios>) w.getResultList();
    	Usuarios user;
    	user = Usuarios.get(0);
    	
    	String usuario = user.getNom_usuario()+" "+user.getApe_usuario();
    	
    	if(movim.equals("ingreso")){
    		saldo_final = saldo + importe;
    	} else {
    		saldo_final = saldo - importe;
    	}
    	
    	consorcio.setSaldo(saldo_final);
    	
    	Caja nuevoMov;
    	nuevoMov = new Caja(fecha, edificio, importe, saldo_anterior, saldo_final, deta, unid, pag, usuario);
    	
    	entityManager.persist(nuevoMov);
    	
    	int unid_func;
    	
    	double p_saldoAnt;
    	double p_pago;
    	double p_saldo;
    	
    	try {
			unid_func = Globals.StringToInt(unid);
			
			if(unid_func>0){
				
				String queryProp = "SELECT x FROM Propietarios x WHERE Edificio = '"+edificio+"' AND Num_Dto_Prop = "+unid_func;
				Query ww = entityManager.createQuery(queryProp);
				List<Propietarios> prop = (List<Propietarios>) ww.getResultList();
				
				if(prop.size()==1){
					
					Propietarios propietario = prop.get(0);
					
					p_saldoAnt = propietario.getSaldo();
					p_saldo = p_saldoAnt + importe;
					
					propietario.setSaldo_Ant(p_saldoAnt);
					propietario.setPago(importe);
					propietario.setSaldo(p_saldo);					
					
				}
				
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
    	
		

    	Query z = entityManager.createQuery("SELECT x FROM Consorcios x");
 		List<Consorcios> resultList0 = (List<Consorcios>) z.getResultList();
 		List<Consorcios> c = resultList0;

 		resultado.render("consorcios", c);

     	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     	String data = df.format(new Date());
  	
     	Query e = entityManager.createQuery("SELECT x FROM Caja x ORDER BY id DESC");
     	List<Caja> resultList4 = (List<Caja>) e.setMaxResults(10).getResultList();
     	List<Caja> cajas10 = resultList4;

     	resultado.render("cajas10", cajas10);
     	
     	Query ww = entityManager.createQuery("SELECT x FROM Caja x ORDER BY id DESC");
     	List<Caja> resultList5 = (List<Caja>) ww.getResultList();
     	List<Caja> caja = resultList5;

     	resultado.render("cajas", caja);
     	
     	resultado.render("fecha", data);
     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/caja.ftl.html");

		return resultado;

	}
	
	// NUEVO PROPIETARIO
	@Transactional
	public Result NuevoMensaje(
			@Param("nombreMje") String nombreMje,
			@Param("mailMje") String mailMje,
			@Param("asuntoMje") String asuntoMje,
			@Param("mensajeMje") String mensajeMje){

		Result resultado = Results.html();

		Date fecha = new Date();
		EntityManager entityManager = entityManagerProvider.get();
		
		
		Mensajes nuevoMen;
		nuevoMen = new Mensajes(nombreMje, mailMje, fecha, asuntoMje, mensajeMje, "false");
    	entityManager.persist(nuevoMen);
    	
		resultado.template("views/ApplicationController/index.ftl.html");

		return resultado;
		
	}
    
    /*
     * */
	
	// NUEVO SUBTIPO
	@Transactional
	public Result NuevoSubTipo(
			@Param("nomNS") String nombre,
			@Param("numNS") String numero){

		Result resultado = Results.html();

		EntityManager entityManager = entityManagerProvider.get();

		int nume = Globals.StringToInt(numero);
		
		SubTipoGastos nuevoSubTipo;
		nuevoSubTipo = new SubTipoGastos(nume, nombre);
    	entityManager.persist(nuevoSubTipo);
    	
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
}
