Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});

$(document).ready(function(){
		//alert('FUNCIONAAAA');
	$("#fecha").datepicker();

	$('#pruebaJS').click(function(){
		alert('FUNCIONAAAA');
		// <hr><hr><button class="btn btn-success" id="pruebaJS">Click</button>
	});

	$('.alerta').click(function(){
		alert('FUNCIONAAAA');
		// <hr><hr><button class="btn btn-success" id="pruebaJS">Click</button>
	});


	$('#datePicker').val(new Date().toDateInputValue());

	/////// INICIO FORMULARIO NEW GASTO EXT ///////
	$('#LiquidacionSueldoNew').submit(function(){

		alert('FUNCIONAAAA');
		return false;

	}); /////// FIN FORMULARIO NEW GASTO EXT ///////

	/////// INICIO FORMULARIO LOGIN ///////
	$('#formularioLogin').submit(function(){

		if($('#us_user').val().length < 1){
			$('#llenarUser').text("[Ingrese nombre de usuario]");
			return false;
		} else {
			$('#llenarUser').text("");
		}

		if($('#us_contra').val().length < 1){
			$('#llenarPass').text("[Ingrese contraseña]");
			return false;
		} else {
			$('#llenarPass').text("");
		}

	}); /////// FIN FORMULARIO LOGIN ///////

	/////// INICIO FORMULARIO CONSORCIO ///////
	$('#formCons').submit(function(){

		if($('#nomNC').val().length < 1){
			$('#llenarNom').text("[Ingrese el Nombre]");
			return false;
		} else {
			$('#llenarNom').text("");
			var c = 1;
		}

		if($('#dirNC').val().length < 1){
			$('#llenarDir').text("[Ingrese la dirección]");
			return false;
		} else {
			$('#llenarDir').text("");
			c++;
		}

		if($('#cantNC').val().length < 1){
			$('#llenarCant').text("[Ingrese la cantidad de Unidades]");
			return false;
		} else {
			$('#llenarCant').text("");
			c++;
		}

		if($('#saldoNC').val().length < 1){
			$('#llenarSaldo').text("[Ingrese el saldo de caja]");
			return false;
		} else {
			$('#llenarSaldo').text("");
			c++;
		}


		if (c == 4){
			alert("Se ha generado un consorcio");
		}

	});
	/////// FIN FORMULARIO CONSORCIO ///////

	////// INICIO FILTRO CONSORCIO ///////
	$('#filtroCons').submit(function(){
		if($('#Filtro').val().length < 1){
			$('#llenarfiltro').text("[Ingrese un dato para buscar]");
			return false;
		} else {
			$('#llenarfiltro').text("");
		}
	});

	////// FIN FILTRO CONSORCIO ///////

	////// INICIO FORMULARIO NUEVO PROPIETARIO ///////
	$('#formPropietarioNew').submit(function(){

		if($('#nomNP').val().length < 1){
			$('#llenarNom').text("[Ingrese el nombre]");
			return false;
		} else {
			$('#llenarNom').text("");
			var a = 1;
		}

		if($('#apeNP').val().length < 1){
			$('#llenarApe').text("[Ingrese el apellido]");
			return false;
		} else {
			$('#llenarApe').text("");
			a++;
		}

		if($('#docNP').val().length < 1){
			$('#llenarDoc').text("[Ingrese el documento]");
			return false;
		} else {
			$('#llenarDoc').text("");
			a++;
		}

		if($('#direNP').val().length < 1){
			$('#llenarDir').text("[Ingrese la direccion]");
			return false;
		} else {
			$('#llenarDir').text("");
			a++;
		}

		if($('#telNP').val().length < 1){
			$('#llenarTel').text("[Ingrese el teléfono]");
			return false;
		} else {
			$('#llenarTel').text("");
			a++;
		}

		if($('#emaNP').val().length < 1){
			$('#llenarEma').text("[Ingrese el email]");
			return false;
		} else {
			$('#llenarEma').text("");
			a++;
		}

		if($('#uniNP').val().length < 1){
			$('#llenarUni').text("[Ingrese la unidad]");
			return false;
		} else {
			$('#llenarUni').text("");
			a++;
		}

		if($('#expNP').val().length < 1){
			$('#llenarExp').text("[Ingrese el porcentaje de expensas]");
			return false;
		} else {
			$('#llenarExp').text("");
			a++;
		}

		if($('#expExtNP').val().length < 1){
			$('#llenarExpExt').text("[Ingrese el porcentaje de expensas extraordinarias]");
			return false;
		} else {
			$('#llenarExpExt').text("");
			a++;
		}

		if($('#direNP').val().length < 1){
			$('#llenarDir').text("[Ingrese la direccion]");
			return false;
		} else {
			$('#llenarDir').text("");
			a++;
		}

		if (a == 10){
			alert("El propietario ha sido cargado correctamente");
		}

	}); ////// FIN FORMULARIO NUEVO PROPIETARIO ///////


	////// INICIO FORMULARIO NUEVO PROVEEDOR ///////
	$('#formProveedorNew').submit(function(){

		alert("AAAA");
		if($('#nomNPr').val().length < 1){
			$('#llenarNom').text("[Ingrese el nombre]");
			return false;
		} else {
			$('#llenarNom').text("");
			var a = 1;
		}

		if($('#cuitNPr').val().length < 1){
			$('#llenarCuit').text("[Ingrese el CUIT]");
			return false;
		} else {
			$('#llenarCuit').text("");
			a++;
		}

		if($('#detNPr').val().length < 1){
			$('#llenarDet').text("[Ingrese el detalle del servicio]");
			return false;
		} else {
			$('#llenarDet').text("");
			a++;
		}

		if($('#dirNPr').val().length < 1){
			$('#llenarDir').text("[Ingrese la direccion]");
			return false;
		} else {
			$('#llenarDir').text("");
			a++;
		}

		if($('#telNPr').val().length < 1){
			$('#llenarTel').text("[Ingrese el teléfono]");
			return false;
		} else {
			$('#llenarTel').text("");
			a++;
		}

		if($('#emaNPr').val().length < 1){
			$('#llenarEma').text("[Ingrese el email]");
			return false;
		} else {
			$('#llenarEma').text("");
			a++;
		}

		if ((a == 6) && ($('#selectProv').val() != "*")){
			alert("El servicio ha sido cargado correctamente");
		}

	}); ////// FIN FORMULARIO NUEVO PROVEEDOR ///////

	////// INICIO FORMULARIO NUEVO EMPLEADO ///////
	$('#formEmpleadoNew').submit(function(){

		if($('#nomNE').val().length < 1){
			$('#llenarNom').text("[Ingrese el nombre]");
			return false;
		} else {
			$('#llenarNom').text("");
			var a = 1;
		}

		if($('#apeNE').val().length < 1){
			$('#llenarApe').text("[Ingrese el apellido]");
			return false;
		} else {
			$('#llenarApe').text("");
			a++;
		}

		if($('#docNE').val().length < 1){
			$('#llenarDoc').text("[Ingrese el documento]");
			return false;
		} else {
			$('#llenarDoc').text("");
			a++;
		}

		if($('#cuilNE').val().length < 1){
			$('#llenarCUIL').text("[Ingrese el CUIL]");
			return false;
		} else {
			$('#llenarCUIL').text("");
			a++;
		}

		if($('#telNE').val().length < 1){
			$('#llenarTel').text("[Ingrese el teléfono]");
			return false;
		} else {
			$('#llenarTel').text("");
			a++;
		}

		if($('#celNE').val().length < 1){
			$('#llenarCel').text("[Ingrese el celular]");
			return false;
		} else {
			$('#llenarCel').text("");
			a++;
		}

		if($('#emaNE').val().length < 1){
			$('#llenarEma').text("[Ingrese el email]");
			return false;
		} else {
			$('#llenarEma').text("");
			a++;
		}

		if($('#horNE').val().length < 1){
			$('#llenarHor').text("[Ingrese el horario]");
			return false;
		} else {
			$('#llenarHor').text("");
			a++;
		}

		if ((a == 8) && ($('#selectProv').val() != "*")){
			alert("Se cargo un nuevo empleado correctamente");
		}

	}); ////// FIN FORMULARIO NUEVO EMPLEADO ///////


/*
	$('#formularioMensaje').submit(function(){

            if($('#nombreMje').val().length < 1){
                  $('#llenarNombre').text("[Ingrese su nombre]");
                  return false;
            } else {
                  $('#llenarNombre').text("");
                  var a = 1;
            }

            if($('#mailMje').val().length < 1){
                  $('#llenarMail').text("[Ingrese su mail]");
                  return false;
            } else {
                  $('#llenarMail').text("");
                  a++;
            }

            if($('#asuntoMje').val().length < 1){
                  $('#llenarAsunto').text("[Ingrese el Asunto del Mensaje]");
                  return false;
            } else {
                  $('#llenarAsunto').text("");
                  a++;
            }

            if($('#mensajeMje').val().length < 1){
                  $('#llenarMensaje').text("[Ingrese el contenido del mensaje]");
                  return false;
            } else {
                  $('#llenarMensaje').text("");
                  a++;
            }

            if (a == 4){
                  alert("El mensaje ha sido enviado. Recibira una respuesta a la brevedad. ¡Muchas gracias!");
            }

      }); /////// FIN FORMULARIO MENSAJE ///////



	$('#formUser').submit(function(){

		if($('#userNU').val().length < 1){
			$('#llenarUser').text("[Ingrese username]");
			return false;
		} else {
			$('#llenarUser').text("");
			var u = 1;
		}

		if($('#passNU').val().length < 1){
			$('#llenarPWD').text("[Ingrese un password]");
			return false;
		} else {
			$('#llenarPWD').text("");
			u++;
		}

		if($('#nomNU').val().length < 1){
			$('#llenarNom').text("[Ingrese el Nombre]");
			return false;
		} else {
			$('#llenarNom').text("");
			u++;
		}

		if($('#apeNU').val().length < 1){
			$('#llenarApe').text("[Ingrese el Apellido]");
			return false;
		} else {
			$('#llenarApe').text("");
			u++;
		}

		if($('#dniNU').val().length < 1){
			$('#llenarDNI').text("[Ingrese el DNI]");
			return false;
		} else {
			$('#llenarDNI').text("");
			u++;
		}

		if($('#emaNU').val().length < 1){
			$('#llenarMail').text("[Ingrese el mail]");
			return false;
		} else {
			$('#llenarMail').text("");
			u++;
		}

		if($('#edaNU').val().length < 1){
			$('#llenarEdad').text("[Ingrese la edad]");
			return false;
		} else {
			$('#llenarEdad').text("");
			u++;
		}

		if($('#cateNU').val().length < 1){
			$('#llenarCate').text("[Seleccione una categoría: A, B o C]");
			return false;
		} else {
			$('#llenarCate').text("");
			u++;
		}

		if (u == 8){
			alert("Se ha generado un nuevo usuario");
		}

	}); /////// FIN FORMULARIO USUARIO ///////

	$('#formProv').submit(function(){

		if($('#rubroNP').val().length < 1){
			$('#llenarRubro').text("[Ingrese el Rubro]");
			return false;
		} else {
			$('#llenarRubro').text("");
			var p = 1;
		}

		if($('#nomNP').val().length < 1){
			$('#llenarNom').text("[Ingrese el Nombre]");
			return false;
		} else {
			$('#llenarNom').text("");
			p++;
		}

		if($('#razNP').val().length < 1){
			$('#llenarRZ').text("[Ingrese la Razon Social]");
			return false;
		} else {
			$('#llenarRZ').text("");
			p++;
		}

		if($('#ctNP').val().length < 1){
			$('#llenarCuit').text("[Ingrese el CUIT]");
			return false;
		} else {
			$('#llenarCuit').text("");
			p++;
		}

		if($('#direNP').val().length < 1){
			$('#llenarDir').text("[Ingrese la Dirección]");
			return false;
		} else {
			$('#llenarDir').text("");
			p++;
		}

		if($('#telNP').val().length < 1){
			$('#llenarTel').text("[Ingrese el teléfono]");
			return false;
		} else {
			$('#llenarTel').text("");
			p++;
		}

		if($('#emaNP').val().length < 1){
			$('#llenarMai').text("[Ingrese el mail]");
			return false;
		} else {
			$('#llenarMai').text("");
			p++;
		}

		if (p == 7){
			alert("Se ha generado un nuevo proveedor");
		}

	});
	/////// FIN FORMULARIO PROVEEDOR ///////



	$('#formGasto').submit(function(){

		if($('#remNG').val().length < 1){
			$('#llenarRem').text("[Ingrese el número de Remito]");
			return false;
		} else {
			$('#llenarRem').text("");
			g = 1;
		}

		if($('#impNG').val().length < 1){
			$('#llenarImp').text("[Ingrese el Importe]");
			return false;
		} else {
			$('#llenarImp').text("");
			g++;
		}

		if($('#detaNG').val().length < 1){
			$('#llenarDate').text("[Ingrese el detalle]");
			return false;
		} else {
			$('#llenarDate').text("");
			g++;
		}

		if (g == 3){
			alert("Se ha generado un gasto");
		} else {
			return false;
		}

	}); /////// FIN FORMULARIO GASTOS ///////

	$('#prueba').click(function(){
		var text = $('#fecNG').val();
		alert(text);
	});

	$('#enviarMail').click(function(){

		var valor = $('#enviarMail').val();
		alert(valor);

	});
*/
});


