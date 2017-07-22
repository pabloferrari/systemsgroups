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

import controllers.Globals;
import models.Consorcios;
import models.EstadoCuentasYProrrateo;
import models.GastosGenerales;
import models.Liquidaciones;
import models.Propietarios;
import models.Proveedores;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;


@Singleton
public class ApplicationController {

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
 		
 		Query pro = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = '"+edificio+"' AND Periodo = '"+(periodo_)+"'");
 		List<EstadoCuentasYProrrateo> resultListPro = (List<EstadoCuentasYProrrateo>) pro.getResultList();
 		List<EstadoCuentasYProrrateo> prorra = resultListPro;
 		
 		resultado.render("prorra", prorra);
 		
 		Query proEd = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x GROUP BY Edificio");
 		List<EstadoCuentasYProrrateo> resultListPro2 = (List<EstadoCuentasYProrrateo>) proEd.getResultList();
 		List<EstadoCuentasYProrrateo> prorraD = resultListPro2;
 		
 		resultado.render("pro_ed", prorraD);
 		
 		Query proP = entityManager.createQuery("SELECT x FROM EstadoCuentasYProrrateo x WHERE Edificio = 'SIN DATOS' GROUP BY Periodo");
 		List<EstadoCuentasYProrrateo> resultListPro0 = (List<EstadoCuentasYProrrateo>) proP.getResultList();
 		List<EstadoCuentasYProrrateo> prorraP = resultListPro0;
 		
 		resultado.render("pro_pe", prorraP);
 		
 		boolean muestro = false;
 		resultado.render("muestro", muestro);
 		
     	resultado.render("usuario", Globals.usuarioActual);
     	resultado.render("categoria", Globals.categoriaUsuario);
     	resultado.template("views/ApplicationController/prorrateos.ftl.html");

 		return resultado;
 	}

    // ELIMINAR GASTO
    @Transactional
 	public Result EliminarGasto(
 		@Param("id") String id){

 		Result resultado = Results.html();
 	
 		EntityManager entityManager = entityManagerProvider.get();
 		
 		Query datos = entityManager.createQuery("SELECT x FROM GastosGenerales x WHERE id = "+id);
		List<GastosGenerales> listG = (List<GastosGenerales>) datos.getResultList();

		GastosGenerales gasto;
		
		if(listG.size()>0){
			gasto = listG.get(0);
		    entityManager.remove(gasto);
		}
	    
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
    

	
	

}
