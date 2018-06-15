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
package io.github.schreddo.nerdy.password.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.schreddo.nerdy.password.enums.CharType;
import io.github.schreddo.nerdy.password.enums.PasswordType;
import io.github.schreddo.nerdy.password.functions.PasswordGenerator;

public class NewPassword {
	
	private String password;
	private CharType[] charTypeSequence;
	
	private int minLength;
	private int maxLength;
	private PasswordType passwordType;
	private int leetSpeak;
	private boolean sameCharTwiceInARow;
	
	private Map<String, List<Character>> symbolList;
	private List<String> wordList;
	
	private boolean needNumbers;
	private boolean needLetters;
	private boolean needLowerUpperLetters;
	private boolean needSymbols;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public CharType[] getCharTypeSequence() {
		return charTypeSequence;
	}
	public void setCharTypeSequence(CharType[] charTypeSequence) {
		this.charTypeSequence = charTypeSequence;
	}
	
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public PasswordType getPasswordType() {
		return passwordType;
	}
	public void setPasswordType(PasswordType passwordType) {
		this.passwordType = passwordType;
	}
	public int getLeetSpeak() {
		return leetSpeak;
	}
	public void setLeetSpeak(int leetSpeak) {
		this.leetSpeak = leetSpeak;
	}
	public boolean isSameCharTwiceInARow() {
		return sameCharTwiceInARow;
	}
	public void setSameCharTwiceInARow(boolean sameCharTwiceInARow) {
		this.sameCharTwiceInARow = sameCharTwiceInARow;
	}
	
	public Map<String, List<Character>> getSymbolList() {
		return symbolList;
	}
	public void setSymbolList(Map<String, List<Character>> symbolList) {
		this.symbolList = symbolList;
	}
	public List<String> getWordList() {
		return wordList;
	}
	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}
	
	public boolean isNeedNumbers() {
		return needNumbers;
	}
	public void setNeedNumbers(boolean needNumbers) {
		this.needNumbers = needNumbers;
	}
	public boolean isNeedLetters() {
		return needLetters;
	}
	public void setNeedLetters(boolean needLetters) {
		this.needLetters = needLetters;
	}
	public boolean isNeedLowerUpperLetters() {
		return needLowerUpperLetters;
	}
	public void setNeedLowerUpperLetters(boolean needLowerUpperLetters) {
		this.needLowerUpperLetters = needLowerUpperLetters;
	}
	public boolean isNeedSymbols() {
		return needSymbols;
	}
	public void setNeedSymbols(boolean needSymbols) {
		this.needSymbols = needSymbols;
	}
	
	public NewPassword() {
		this(1);
	}
	
	public NewPassword(int secureLVL) {
		this(0, 0, null, null, 0, true, true, true, true, true, new HashMap<String, List<Character>>(), new ArrayList<String>());
		switch (secureLVL) {
		case 0: setSecureLVLEasy();
			break;
		case 1: setSecureLVLNormel();
			break;
		case 2: setSecureLVLHard();
			break;
		default: setSecureLVLNormel();
			break;
		}
	}
	
	public NewPassword(int minLength, int maxLength, PasswordType passwordType, CharType[] charTypeSequence, int leetSpeak, boolean sameCharTwiceInARow, boolean needNumbers, boolean needLetters, boolean needLowerUpperLetters, boolean needSymbols, Map<String, List<Character>> symbolList, List<String> wordList) {
		setPassword("");
		setMinLength(minLength);
		setMaxLength(maxLength);
		setPasswordType(passwordType);
		setCharTypeSequence(charTypeSequence);
		setLeetSpeak(leetSpeak);
		setSameCharTwiceInARow(sameCharTwiceInARow);
		setNeedNumbers(needNumbers);
		setNeedLetters(needLetters);
		setNeedLowerUpperLetters(needLowerUpperLetters);
		setNeedSymbols(needSymbols);
		setSymbolList(symbolList);
		setWordList(wordList);
	}
	
	public void setSecureLVLEasy() {
		setMinLength(8);
		setMaxLength(8);
		setPasswordType(PasswordType.MACHINE);
		setSameCharTwiceInARow(false);
		setNeedNumbers(true);
		setNeedLetters(true);
		setNeedLowerUpperLetters(true);
		setNeedSymbols(false);
		setSymbolList(new HashMap<String, List<Character>>());
		getSymbolList().putAll(PasswordGenerator.genarateNumberSymbollList());
		getSymbolList().putAll(PasswordGenerator.genarateLowerLetterSymbollList());
		getSymbolList().putAll(PasswordGenerator.genarateUpperLetterSymbollList());
		setWordList(new ArrayList<String>());
	}
	
	public void setSecureLVLNormel() {
		setMinLength(8);
		setMaxLength(25);
		setPasswordType(PasswordType.MACHINE);
		setSameCharTwiceInARow(false);
		setNeedNumbers(true);
		setNeedLetters(true);
		setNeedLowerUpperLetters(true);
		setNeedSymbols(true);
		setSymbolList(new HashMap<String, List<Character>>());
		getSymbolList().putAll(PasswordGenerator.genarateNumberSymbollList());
		getSymbolList().putAll(PasswordGenerator.genarateLowerLetterSymbollList());
		getSymbolList().putAll(PasswordGenerator.genarateUpperLetterSymbollList());
		getSymbolList().putAll(PasswordGenerator.addSymbollsToList(getSymbolList(), PasswordGenerator.getSymbolList1()));
		setWordList(new ArrayList<String>());
	}
	
	public void setSecureLVLHard() {
		setMinLength(25);
		setMaxLength(50);
		setPasswordType(PasswordType.MACHINE);
		setSameCharTwiceInARow(false);
		setNeedNumbers(true);
		setNeedLetters(true);
		setNeedLowerUpperLetters(true);
		setNeedSymbols(true);
		setSymbolList(new HashMap<String, List<Character>>());
		getSymbolList().putAll(PasswordGenerator.genarateNumberSymbollList());
		getSymbolList().putAll(PasswordGenerator.genarateLowerLetterSymbollList());
		getSymbolList().putAll(PasswordGenerator.genarateUpperLetterSymbollList());
		getSymbolList().putAll(PasswordGenerator.addSymbollsToList(getSymbolList(), PasswordGenerator.getSymbolList1()));
		getSymbolList().putAll(PasswordGenerator.addSymbollsToList(getSymbolList(), PasswordGenerator.getSymbolList2()));
		getSymbolList().putAll(PasswordGenerator.addSymbollsToList(getSymbolList(), PasswordGenerator.getSymbolList3()));
		setWordList(new ArrayList<String>());
	}
	
	public void setLeekSpeackTyp(int seekSpeackTyp) {
		switch (seekSpeackTyp) {
		case 0: setLeetSpeak(seekSpeackTyp);
			break;
		case 1: setLeetSpeak(seekSpeackTyp);
			break;
		case 2: setLeetSpeak(seekSpeackTyp);
			break;
		case 3: setLeetSpeak(seekSpeackTyp);
			break;
		default: setLeetSpeak(0);
			break;
		}
	}
}
