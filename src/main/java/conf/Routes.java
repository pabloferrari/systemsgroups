/**
 * Copyright (C) 2012 the original author or authors.
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

package conf;


import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;
import controllers.ApplicationController_edit;
import controllers.ApplicationController_filter;
import controllers.ApplicationController_go;
import controllers.ApplicationController_new;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {  
        
        router.GET().route("/").with(ApplicationController.class, "index");
        router.GET().route("/hello_world.json").with(ApplicationController.class, "helloWorldJson");
        
 
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController.class, "index");
        
        
		///////////////////////////////////////////////////////////////////////
		// FUNCIONES ESPECIALES
		///////////////////////////////////////////////////////////////////////
        
        //router.POST().route("/generaPDF").with(ApplicationController.class, "generaPDF");
        router.POST().route("/EliminarGasto").with(ApplicationController.class, "EliminarGasto");
        
		///////////////////////////////////////////////////////////////////////
		// GO
		///////////////////////////////////////////////////////////////////////
        
        router.POST().route("/login").with(ApplicationController_go.class, "login");  
        router.POST().route("/home").with(ApplicationController_go.class, "home");        
        router.POST().route("/volverHome").with(ApplicationController_go.class, "volverHome");
        router.POST().route("/usuarios").with(ApplicationController_go.class, "usuarios");
        router.POST().route("/mensajes").with(ApplicationController_go.class, "mensajes");
        router.POST().route("/consorcios").with(ApplicationController_go.class, "consorcios");
        router.POST().route("/gastos").with(ApplicationController_go.class, "gastos");
        router.POST().route("/gastos_res").with(ApplicationController_go.class, "gastos_res");
        router.POST().route("/gastos_new").with(ApplicationController_go.class, "gastos_new");
        router.POST().route("/empleados").with(ApplicationController_go.class, "empleados");
        router.POST().route("/empleados_new").with(ApplicationController_go.class, "empleados_new");
        router.POST().route("/liqSueldo").with(ApplicationController_go.class, "liqSueldo");
        router.POST().route("/liqSueldo_new").with(ApplicationController_go.class, "liqSueldo_new");
        router.POST().route("/liquidacion").with(ApplicationController_go.class, "liquidacion");
        router.POST().route("/liquidacion_new").with(ApplicationController_go.class, "liquidacion_new");
        router.POST().route("/propietarios").with(ApplicationController_go.class, "propietarios");
        router.POST().route("/propietarios_new").with(ApplicationController_go.class, "propietarios_new");
        router.POST().route("/proveedores").with(ApplicationController_go.class, "proveedores");
        router.POST().route("/proveedores_new").with(ApplicationController_go.class, "proveedores_new"); 
        router.POST().route("/subtipo_gastos").with(ApplicationController_go.class, "subtipo_gastos");
        router.POST().route("/caja").with(ApplicationController_go.class, "caja");
        router.POST().route("/movimientos").with(ApplicationController_go.class, "movimientos");
        router.POST().route("/saldos").with(ApplicationController_go.class, "saldos");        
        router.POST().route("/liquidaciones").with(ApplicationController_go.class, "liquidaciones");
        router.POST().route("/verLiquidaciones").with(ApplicationController_go.class, "verLiquidaciones");
        router.POST().route("/traerLiquidaciones").with(ApplicationController_go.class, "traerLiquidaciones");
        router.POST().route("/prorrateos").with(ApplicationController_go.class, "prorrateos");
        router.POST().route("/verPeriodo").with(ApplicationController_go.class, "verPeriodo");
        router.POST().route("/verProrrateo").with(ApplicationController_go.class, "verProrrateo");
        
        
        
		///////////////////////////////////////////////////////////////////////
		// NEW
		///////////////////////////////////////////////////////////////////////
        router.POST().route("/NuevoConsorcio").with(ApplicationController_new.class, "NuevoConsorcio");
        router.POST().route("/NuevoPropietario").with(ApplicationController_new.class, "NuevoPropietario");
        router.POST().route("/NuevoProveedor").with(ApplicationController_new.class, "NuevoProveedor");
        router.POST().route("/NuevoGasto").with(ApplicationController_new.class, "NuevoGasto");
        router.POST().route("/NuevoEmpleado").with(ApplicationController_new.class, "NuevoEmpleado");
        router.POST().route("/NuevaLiquidacionSueldo").with(ApplicationController_new.class, "NuevaLiquidacionSueldo");
        router.POST().route("/NuevoUsuario").with(ApplicationController_new.class, "NuevoUsuario");
        router.POST().route("/NuevaLiquidacion").with(ApplicationController_new.class, "NuevaLiquidacion");
        router.POST().route("/NuevoMovimiento").with(ApplicationController_new.class, "NuevoMovimiento");
        router.POST().route("/NuevoMensaje").with(ApplicationController_new.class, "NuevoMensaje");
        router.POST().route("/NuevoSubTipo").with(ApplicationController_new.class, "NuevoSubTipo");
        router.POST().route("/NuevoProrrateo").with(ApplicationController_new.class, "NuevoProrrateo");
              
        
        
		///////////////////////////////////////////////////////////////////////
		// FILTROS
		///////////////////////////////////////////////////////////////////////        
        router.POST().route("/filtroCons").with(ApplicationController_filter.class, "filtroCons");
        router.POST().route("/filtroProp").with(ApplicationController_filter.class, "filtroProp");
        router.POST().route("/filtroEmpl").with(ApplicationController_filter.class, "filtroEmpl");
        router.POST().route("/filtroGast").with(ApplicationController_filter.class, "filtroGast");
        router.POST().route("/filtroLiqSueldo").with(ApplicationController_filter.class, "filtroLiqSueldo");
        router.POST().route("/filtroMov").with(ApplicationController_filter.class, "filtroMov");
        
        
		///////////////////////////////////////////////////////////////////////
		// EDIT
		///////////////////////////////////////////////////////////////////////        
		router.POST().route("/edit_liqSueldo").with(ApplicationController_edit.class, "edit_liqSueldo");
		router.POST().route("/editarliqSueldo").with(ApplicationController_edit.class, "editarliqSueldo");
		router.POST().route("/edit_propietarios").with(ApplicationController_edit.class, "edit_propietarios");
		router.POST().route("/editarPropietario").with(ApplicationController_edit.class, "editarPropietario");
		router.POST().route("/edit_empleados").with(ApplicationController_edit.class, "edit_empleados");
		router.POST().route("/editarEmpleado").with(ApplicationController_edit.class, "editarEmpleado");
		router.POST().route("/edit_gastos").with(ApplicationController_edit.class, "edit_gastos");
		router.POST().route("/editarGasto").with(ApplicationController_edit.class, "editarGasto");
		router.POST().route("/edit_usuarios").with(ApplicationController_edit.class, "edit_usuarios");
		router.POST().route("/editarUsuario").with(ApplicationController_edit.class, "editarUsuario");
		router.POST().route("/editarMensaje").with(ApplicationController_edit.class, "editarMensaje");
		router.POST().route("/edit_Subtipo").with(ApplicationController_edit.class, "edit_Subtipo");
		router.POST().route("/editarSubtipo").with(ApplicationController_edit.class, "editarSubtipo");
		
		//router.GET().route("/").with(ApplicationController_edit.class, "index");
		
		
		
		
		///////////////////////////////////////////////////////////////////////
		// ELIMINADOS
		//  	
		// router.POST().route("/gastosExt").with(ApplicationController.class, "gastosExt");
        // router.POST().route("/gastosExt_new").with(ApplicationController.class, "gastosExt_new");
        // router.POST().route("/filtroGastExt").with(ApplicationController.class, "filtroGastExt");
		// router.POST().route("/NuevoGastoExt").with(ApplicationController.class, "NuevoGastoExt");
		//
		//
		//
		//
        ///////////////////////////////////////////////////////////////////////
    }

}
