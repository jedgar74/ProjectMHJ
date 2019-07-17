package io;

/*
 * Terminal.java
 * 
 * Copyright 2019 Jhon Amaya <tauger@darkstar>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */


public class Terminal {
	
	public static String title(String m){
		String sp ="*******";
		String temp =sp+"     "+m+"     ";
		int diff= 40 - temp.length();
		if (diff > 0){
			for (int rr = 0; rr < diff; rr++) {	
				temp = temp+" ";
			}
		}  
		temp = temp + sp;
		
		return "\n***********************************************" +
			"\n"+ temp +
			"\n***********************************************\n"; 
	}
	
	public static String separator(){
		
		return "-----------------------------------------------\n"  ; 
	}
	
}

