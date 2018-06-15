// Copyright (C) 2018 Sebastian Lühnen
//
//
// This file is part of NerdyPassword.
// 
// NerdyPassword is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// NerdyPassword is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with NerdyPassword. If not, see <http://www.gnu.org/licenses/>.
//
//
// Created By: Sebastian Lühnen
// Created On: 15.06.2018
// Last Edited On: 15.06.2018
// Language: Java
//
package io.github.schreddo.nerdy.password.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.github.schreddo.nerdy.password.enums.CharType;
import io.github.schreddo.nerdy.password.models.NewPassword;

public class PasswordGenerator {
	private static Random random = new Random();
	
	public static String generatePassword(NewPassword newPassword) {
		switch (newPassword.getPasswordType()) {
		case HUMEN: return humenPassword(newPassword);
		case MACHINE: return machinePassword(newPassword);
		case HUMEN_MACHINE_COMBINATION: return humenMachineCombinationPassword(newPassword);
		default:
			break;
		}
		
		return "";
	}
	
	private static String humenPassword(NewPassword newPassword) {	
		return "";
	}
	
	private static String machinePassword(NewPassword newPassword) {
		int passwordlength = PasswordGenerator.random.nextInt((newPassword.getMaxLength() - newPassword.getMinLength()) + 1) + newPassword.getMinLength();
		newPassword = PasswordGenerator.setNeededSymbolPosition(newPassword, passwordlength);
		int rounds = 0;
		
		for (int i = 0; i < passwordlength; i++) {
			
			if (newPassword.getCharTypeSequence()[i] == CharType.RANDOM) {
				newPassword = PasswordGenerator.setChar(newPassword);
			} else {
				newPassword = setNeededChar(newPassword, newPassword.getCharTypeSequence()[i]);
			}
			
			if (!newPassword.isSameCharTwiceInARow() && i > 0 && newPassword.getPassword().charAt(i) == newPassword.getPassword().charAt(i - 1)) {
				newPassword.setPassword((newPassword.getPassword().substring(0, newPassword.getPassword().length() - 1)));
				i--;
			}
			
			rounds++;
			
			if (rounds > 1000) {
				newPassword.setPassword("");
				return PasswordGenerator.machinePassword(newPassword);
			}
		}
		
		return newPassword.getPassword();
	}
	
	private static String humenMachineCombinationPassword(NewPassword newPassword) {
		return "";
	}
	
	private static NewPassword setNeededChar(NewPassword newPassword, CharType neededChar) {
		switch (neededChar) {
		case NUMBER: newPassword = PasswordGenerator.setNumber(newPassword); 
			break;
		case LETTER: newPassword = PasswordGenerator.setLetter(newPassword); 
			break;
		case LOWER_LETTER: newPassword = PasswordGenerator.setLowerLetter(newPassword); 
			break;
		case UPPER_LETTER: newPassword = PasswordGenerator.setUpperLetter(newPassword);
			break;
		case SYMBOL: newPassword = PasswordGenerator.setSymbol(newPassword);
			break;
		default: newPassword = PasswordGenerator.setChar(newPassword);
			break;
		}
		
		return newPassword;
	}
	
	private static NewPassword setChar(NewPassword newPassword) {
		return PasswordGenerator.addCharToPassword(newPassword, newPassword.getSymbolList().keySet().toArray()[PasswordGenerator.random.nextInt(newPassword.getSymbolList().size())]);
	}
	
	private static NewPassword setNumber(NewPassword newPassword) {
		return PasswordGenerator.addCharToPassword(newPassword, "numberList");
	}
	
	private static NewPassword setLetter(NewPassword newPassword) {
		int upOrLow = 0;
		if (newPassword.getSymbolList().containsKey("lowerLetterList") && newPassword.getSymbolList().containsKey("upperLetterList")) {
			upOrLow = 2;
		} else if (newPassword.getSymbolList().containsKey("lowerLetterList")) {
			upOrLow = 1;
		}
		
		switch (upOrLow) {
		case 2:
			if (PasswordGenerator.random.nextInt() % 2 == 0) {
				newPassword = PasswordGenerator.addCharToPassword(newPassword, "lowerLetterList");
			} else {
				newPassword = PasswordGenerator.addCharToPassword(newPassword, "upperLetterList");
			}
			break;
		case 1: newPassword = PasswordGenerator.addCharToPassword(newPassword, "lowerLetterList");
			break;
		default: newPassword = PasswordGenerator.addCharToPassword(newPassword, "upperLetterList");
			break;
		}
		
		return newPassword;
	}
	
	private static NewPassword setLowerLetter(NewPassword newPassword) {
		return PasswordGenerator.addCharToPassword(newPassword, "lowerLetterList");
	}
	
	private static NewPassword setUpperLetter(NewPassword newPassword) {
		return PasswordGenerator.addCharToPassword(newPassword, "upperLetterList");
	}
	
	private static NewPassword setSymbol(NewPassword newPassword) {
		return PasswordGenerator.addCharToPassword(newPassword, "symbolList");
	}
	
	private static NewPassword addCharToPassword(NewPassword newPassword, Object charType ) {
		List<Character> temp = newPassword.getSymbolList().get(charType);
		
		newPassword.setPassword(newPassword.getPassword() + temp.get(PasswordGenerator.random.nextInt(temp.size())));
		
		return newPassword;
	}	
	
	private static NewPassword setNeededSymbolPosition(NewPassword newPassword, int passwordlength) {		
		if (newPassword.getCharTypeSequence() != null && newPassword.getCharTypeSequence().length == passwordlength) {
			return newPassword;
		}
		
		newPassword.setCharTypeSequence(new CharType[passwordlength]);
		
		for (int i = 0; i < newPassword.getCharTypeSequence().length; i++) {
			newPassword.getCharTypeSequence()[i] = CharType.RANDOM;
		}
	
		newPassword = setNumberPosition(newPassword);
		newPassword = setLowerUpperLetterPosition(newPassword);
		newPassword = setLowerLetterPosition(newPassword);
		newPassword = setUpperLetterPosition(newPassword);
		newPassword = setSymbolsPosition(newPassword);
		
		return  newPassword;
	}
	
	private static NewPassword setNumberPosition(NewPassword newPassword) {
		return PasswordGenerator.setCharPosition(newPassword, CharType.NUMBER, newPassword.isNeedNumbers());
	}
	
	private static NewPassword setLowerUpperLetterPosition(NewPassword newPassword) {
		int i = 0;
		
		if (newPassword.getSymbolList().containsKey("lowerLetterList") && newPassword.getSymbolList().containsKey("upperLetterList")) {
			i = PasswordGenerator.random.nextInt(Integer.SIZE - 1) % 2;
		} else if (newPassword.getSymbolList().containsKey("lowerLetterList")) {
			i = 1;
		}
		if (i == 1) {
			return PasswordGenerator.setCharPosition(newPassword, CharType.LOWER_LETTER, newPassword.isNeedLetters());
		}
		
		return PasswordGenerator.setCharPosition(newPassword, CharType.UPPER_LETTER, newPassword.isNeedLetters());
	}
	
	private static NewPassword setLowerLetterPosition(NewPassword newPassword) {
		return PasswordGenerator.setCharPosition(newPassword, CharType.LOWER_LETTER, newPassword.isNeedLowerUpperLetters());
	}
	
	private static NewPassword setUpperLetterPosition(NewPassword newPassword) {
		return PasswordGenerator.setCharPosition(newPassword, CharType.UPPER_LETTER, newPassword.isNeedLowerUpperLetters());
	}
	
	private static NewPassword setSymbolsPosition(NewPassword newPassword) {		
		return PasswordGenerator.setCharPosition(newPassword, CharType.SYMBOL, newPassword.isNeedSymbols());
	}
	
	private static NewPassword setCharPosition(NewPassword newPassword, CharType charType, boolean isNeeded) {
		int index = 0;
		
		if (isNeeded) {
			while (isNeeded) {
				index = PasswordGenerator.random.nextInt(newPassword.getCharTypeSequence().length);
				
				if (newPassword.getCharTypeSequence()[index] == CharType.RANDOM) {
					newPassword.getCharTypeSequence()[index] = charType;
					isNeeded = false;
				}
			}
		}
		
		return newPassword;
	}
	
	private static NewPassword leetSpeak(NewPassword npw, Map<String, String> lMap) {
		return npw;
	}
	
 	public static Map<String, List<Character>> genarateNumberSymbollList() {
 		Map<String, List<Character>> SymbollList = new HashMap<String, List<Character>>();
 		List<Character> numberList = new ArrayList<Character>();
		
		for (int i = 0; i < 10; i++) {
			numberList.add(("" + i).charAt(0));
		}
		
		SymbollList.put("numberList", numberList);
		
		return SymbollList;
	}
	
	public static Map<String, List<Character>> genarateLowerLetterSymbollList() {
		Map<String, List<Character>> SymbollList = new HashMap<String, List<Character>>();
 		List<Character> lowerLetterList = new ArrayList<Character>();
		
		for (int i = 'a'; i <= 'z'; i++) {
			lowerLetterList.add((char)i);
		}
		
		SymbollList.put("lowerLetterList", lowerLetterList);
		
		return SymbollList;
	}
	
	public static Map<String, List<Character>> genarateUpperLetterSymbollList() {
		Map<String, List<Character>> SymbollList = new HashMap<String, List<Character>>();
 		List<Character> upperLetterList = new ArrayList<Character>();
		
		for (int i = 'A'; i <= 'Z'; i++) {
			upperLetterList.add((char)i);
		}
		
		SymbollList.put("upperLetterList", upperLetterList);
		
		return SymbollList;
	}
	
	public static Map<String, List<Character>> genarateExtraLowerLetterSymbollList() {
		Map<String, List<Character>> SymbollList = new HashMap<String, List<Character>>();
 		List<Character> extraLowerLetterList = new ArrayList<Character>();
		
 		extraLowerLetterList.add('ä');
 		extraLowerLetterList.add('ö');
 		extraLowerLetterList.add('ü');
 		extraLowerLetterList.add('ß');
 		
 		SymbollList.put("extraLowerLetterList", extraLowerLetterList);
		
		return SymbollList;
	}
	
	public static Map<String, List<Character>> genarateExtraUpperLetterSymbollList() {
		Map<String, List<Character>> SymbollList = new HashMap<String, List<Character>>();
 		List<Character> extraUpperLetterList = new ArrayList<Character>();
		
 		extraUpperLetterList.add('Ä');
 		extraUpperLetterList.add('Ö');
 		extraUpperLetterList.add('Ü');
 		extraUpperLetterList.add('ß');
 		
 		SymbollList.put("extraUpperLetterList", extraUpperLetterList);
		
		return SymbollList;
	}
	
	public static List<Character> getSymbolList1() {
		List<Character> SymbollList = new ArrayList<Character>();
		
		SymbollList.add('_');
		SymbollList.add('-');
		
		return SymbollList;
	}
	
	public static List<Character> getSymbolList2() {
		List<Character> SymbollList = new ArrayList<Character>();
		
		SymbollList.add('@');
		SymbollList.add('#');
		SymbollList.add('§');
		SymbollList.add('$');
		SymbollList.add('%');
		SymbollList.add('&');
		SymbollList.add('!');
		SymbollList.add('?');
		
		return SymbollList;
	}
	
	public static List<Character> getSymbolList3() {
		List<Character> SymbollList = new ArrayList<Character>();
		
		SymbollList.add('*');
		SymbollList.add('+');
		SymbollList.add('"');
		SymbollList.add('=');
		
		return SymbollList;
	}
	
	public static List<Character> getSymbolList4() {
		List<Character> SymbollList = new ArrayList<Character>();
		
		SymbollList.add(',');
		SymbollList.add(';');
		SymbollList.add('.');
		SymbollList.add(':');
		
		return SymbollList;
	}
	
	public static List<Character> getSymbolList5() {
		List<Character> SymbollList = new ArrayList<Character>();
		
		SymbollList.add('(');
		SymbollList.add(')');
		SymbollList.add('{');
		SymbollList.add('}');
		SymbollList.add('[');
		SymbollList.add(']');
		SymbollList.add('<');
		SymbollList.add('>');
		
		return SymbollList;
	}
	
	public static Map<String, List<Character>> addSymbollsToList(Map<String, List<Character>> SymbollList, List<Character> newSymbolls) {
		if (!SymbollList.containsKey("symbolList")) {
 			SymbollList.put("symbolList", new ArrayList<Character>());
		}
 		
 		SymbollList.get("symbolList").addAll(newSymbolls);
		
		return SymbollList;
	}
	
	public static List<String> getWordList(String path) {
		return new ArrayList<String>();
	}
	
	public static NewPassword addOwnSymbollList(NewPassword newPassword, List<Character> SymbollList) {
		if (!newPassword.getSymbolList().containsKey("symbolList")) {
			newPassword.getSymbolList().put("symbolList", new ArrayList<Character>());
		}
		
		newPassword.getSymbolList().get("symbolList").addAll(SymbollList);
		
		return newPassword;
	}
	
	public static NewPassword addOwnWordList(NewPassword newPassword, List<String> wordList) {
		if (newPassword.getWordList() == null) {
			newPassword.setWordList(wordList);
		} else {
			newPassword.getWordList().addAll(wordList);
		}
		
		return newPassword;
	}
}
