package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Globals {

	public static String usuarioActual = null;
	public static String categoriaUsuario = null;
	public static String categoriaA = null;
	public static String categoriaB = null;
	public static String categoriaC = null;
	public static String token = null;
	
	public static double redondear(double numero){
	
		return Math.round(numero*Math.pow(10,2))/Math.pow(10,2);
	
	}
	
	public static Date StrtoFecha(String strFecha) throws ParseException{
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha = null;  
	    fecha = (Date) formatoDelTexto.parse(strFecha);
	    return fecha;
		
	}
	
	public static String obtenerMes(String mes){
		
		String mes_ = null;
		
		switch (mes) {
		
			case "01":
				mes_ = "Enero";
				break;
			case "02":
				mes_ = "Febrero";
				break;
			case "03":
				mes_ = "Marzo";
				break;
			case "04":
				mes_ = "Abril";
				break;
			case "05":
				mes_ = "Mayo";
				break;
			case "06":
				mes_ = "Junio";
				break;
			case "07":
				mes_ = "Julio";
				break;
			case "08":
				mes_ = "Agosto";
				break;
			case "09":
				mes_ = "Septiembre";
				break;
			case "10":
				mes_ = "Octubre";
				break;
			case "11":
				mes_ = "Noviembre";
				break;
			case "12":
				mes_ = "Diciembre";
				break;
		
		}		
		
		return mes_;
		
	}
	
	public static String obtenerMes(int mes){
			
		String mes_ = null;
		
		switch (mes) {
		
			case 01:
				mes_ = "Enero";
				break;
			case 02:
				mes_ = "Febrero";
				break;
			case 03:
				mes_ = "Marzo";
				break;
			case 04:
				mes_ = "Abril";
				break;
			case 05:
				mes_ = "Mayo";
				break;
			case 06:
				mes_ = "Junio";
				break;
			case 07:
				mes_ = "Julio";
				break;
			case 8:
				mes_ = "Agosto";
				break;
			case 9:
				mes_ = "Septiembre";
				break;
			case 10:
				mes_ = "Octubre";
				break;
			case 11:
				mes_ = "Noviembre";
				break;
			case 12:
				mes_ = "Diciembre";
				break;
		
		}		
		
		return mes_;
		
	}
	
	public static int obtenerMes_(String mes){
		
		int mes_ = 0;
		
		switch (mes) {
		
			case "Enero":
				mes_ = 1;
				break;
			case "Febrero":
				mes_ = 2;
				break;
			case "Marzo":
				mes_ = 3;
				break;
			case "Abril":
				mes_ = 4;
				break;
			case "Mayo":
				mes_ = 5;
				break;
			case "Junio":
				mes_ = 6;
				break;
			case "Julio":
				mes_ = 7;
				break;
			case "Agosto":
				mes_ = 8;
				break;
			case "Septiembre":
				mes_ = 9;
				break;
			case "Octubre":
				mes_ = 10;
				break;
			case "Noviembre":
				mes_ = 11;
				break;
			case "Diciembre":
				mes_ = 12;
				break;
		
		}		
		
		return mes_;
		
	}
	
	public static Date PeriodoToDatetime(String periodo){
		
		Date fecha = new Date();
		return fecha;
				
	}
	
	public static String DoubleToString(double numero){
		
		String valor = Double.toString(numero);
		return valor;
		
	}
	
	public static double StringToDouble(String numero){
		
		double valor = Double.parseDouble(numero);
		return valor;
		
	}
	
	public static String IntToString(int numero){
		
		String valor = String.valueOf(numero);
		return valor;
		
	}
	
	public static int StringToInt(String numero){
		
		int valor = Integer.parseInt(numero);
		return valor;
		
	}
	
	public static String generaToken(int longitud){
		
		String token = "";
		
		String variable = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		int indice;
		
		for (int i = 0; i < longitud; i++) {
			
			indice = (int) (Math.random()*longitud);
			token += variable.charAt(indice);
			
		}
		
		return token;
		
	}
	

	
	
	
	


}
