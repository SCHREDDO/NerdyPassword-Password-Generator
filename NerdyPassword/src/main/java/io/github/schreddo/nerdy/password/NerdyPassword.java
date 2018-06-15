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
package io.github.schreddo.nerdy.password;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.schreddo.nerdy.password.enums.CharType;
import io.github.schreddo.nerdy.password.enums.PasswordType;
import io.github.schreddo.nerdy.password.functions.PasswordGenerator;
import io.github.schreddo.nerdy.password.models.NewPassword;

public class NerdyPassword {
	
	private NewPassword newPassword;
	private int errorCode;
	
	private NewPassword getNewPassword() {
		return newPassword;
	}
	private void setNewPassword(NewPassword newPassword) {
		this.newPassword = newPassword;
	}
	public int getErrorCode() {
		return errorCode;
	}
	private void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public NerdyPassword() {
		setNewPassword(new NewPassword());
		setErrorCode(0);
	}
	
	public NerdyPassword(int secureLVL) {
		setNewPassword(new NewPassword(secureLVL));
		setErrorCode(0);
	}
	
	public NerdyPassword(boolean defaultSettings) {
		setNewPassword(new NewPassword());
		setErrorCode(0);
		
		if (!defaultSettings) {
			resetAllSettings();
		}
	}
	
	public NerdyPassword(int passwordLength, boolean needNumbers, boolean needLetters, boolean needLowerUpperLetters, boolean needSymbols) {
		this(passwordLength, passwordLength, needNumbers, needLetters, needLowerUpperLetters, needSymbols, new ArrayList<Character>());
	}
	
	public NerdyPassword(int passwordLength, CharType[] charTypeSequence, boolean needNumbers, boolean needLetters, boolean needLowerUpperLetters, boolean needSymbols) {
		this(passwordLength, passwordLength, needNumbers, needLetters, needLowerUpperLetters, needSymbols, new ArrayList<Character>());
		getNewPassword().setCharTypeSequence(charTypeSequence);
	}
	
	public NerdyPassword(int passwordMinLength, int passwordMaxLength, boolean needNumbers, boolean needLetters, boolean needLowerUpperLetters, boolean needSymbols) {
		this(passwordMinLength, passwordMaxLength, needNumbers, needLetters, needLowerUpperLetters, needSymbols, new ArrayList<Character>());
		
	}
	
	public NerdyPassword(int passwordMinLength, int passwordMaxLength, boolean needNumbers, boolean needLetters, boolean needLowerUpperLetters, boolean needSymbols, List<Character> ownSymbollList) {
		setNewPassword(new NewPassword());
		getNewPassword().setMinLength(passwordMinLength);
		getNewPassword().setMaxLength(passwordMaxLength);
		getNewPassword().setNeedNumbers(needNumbers);
		getNewPassword().setNeedLetters(needLetters);
		getNewPassword().setNeedLowerUpperLetters(needLowerUpperLetters);
		getNewPassword().setNeedSymbols(needSymbols);
		
		addSymbolList1ToPosibilChars();
		addSymbolList2ToPosibilChars();
	}
	
	public void resetAllSettings() {
		getNewPassword().setPassword("");
		getNewPassword().setCharTypeSequence(null);
		getNewPassword().setMinLength(0);
		getNewPassword().setMaxLength(0);
		getNewPassword().setPasswordType(PasswordType.MACHINE);
		getNewPassword().setLeetSpeak(0);
		getNewPassword().setSameCharTwiceInARow(true);
		getNewPassword().setSymbolList(new HashMap<String , List<Character>>());
		getNewPassword().setWordList(new ArrayList<String>());
		getNewPassword().setNeedNumbers(false);
		getNewPassword().setNeedLetters(false);
		getNewPassword().setNeedLowerUpperLetters(false);
		getNewPassword().setNeedSymbols(false);
	}
	
	private boolean checkIfReady() {
		setErrorCode(0);
		
		if (!checklength()) {
			return false;
		}
		
		if (!checkCharTypeSequence()) {
			return false;
		}
		
		if (!checkSymbolList()) {
			return false;
		}
		
		if (!checkNeeds()) {
			return false;
		}
		
		return true;
	}
	
	private boolean checklength() {
		if (getNewPassword().getMinLength() <= 0) {
			setErrorCode(401);
			return false;
		}
		
		if (getNewPassword().getMaxLength() <= 0) {
			setErrorCode(402);
			return false;
		}
		
		if (getNewPassword().getMinLength() > getNewPassword().getMaxLength()) {
			setErrorCode(403);
			return false;
		}
		
		return true;
	}
	
	private boolean checkCharTypeSequence() {
		if (getNewPassword().getCharTypeSequence() != null && getNewPassword().getCharTypeSequence().length > 0) {
			if (getNewPassword().getMinLength() != getNewPassword().getMaxLength()) {
				setErrorCode(501);
				return false;
			}
			
			if (getNewPassword().getMinLength() != getNewPassword().getCharTypeSequence().length) {
				setErrorCode(502);
				return false;
			}
		}
		
		return true;
	}
	
	private boolean checkSymbolList() {
		if (getNewPassword().getSymbolList() == null || getNewPassword().getSymbolList().size() == 0) {
			setErrorCode(601);
			return false;
		}
		
		return true;
	}
	
	private boolean checkNeeds() {
		if (getNewPassword().isNeedNumbers()) {
			if (!getNewPassword().getSymbolList().containsKey("numberList")) {
				setErrorCode(701);
				return false;
			}
		}
		
		if (getNewPassword().isNeedLetters()) {
			if (!getNewPassword().getSymbolList().containsKey("lowerLetterList") && !getNewPassword().getSymbolList().containsKey("upperLetterList")) {
				setErrorCode(702);
				return false;
			}
		}
		
		if (getNewPassword().isNeedLowerUpperLetters()) {
			if (!getNewPassword().getSymbolList().containsKey("lowerLetterList") || !getNewPassword().getSymbolList().containsKey("upperLetterList")) {
				setErrorCode(703);
				return false;
			}
		}
		
		if (getNewPassword().isNeedSymbols()) {
			if (!getNewPassword().getSymbolList().containsKey("symbolList")) {
				setErrorCode(704);
				return false;
			}
		}
		
		return true;
	}
	
	private String generatingPassword() {
		getNewPassword().setPassword("");
		return PasswordGenerator.generatePassword(getNewPassword());
	}
		
	public String startGeneratingPassword() {
		if (checkIfReady()) {
			return generatingPassword();
		}
		
		return null;
	}
	
	public List<String> startGeneratingPasswords(int quantity) {
		List<String> passwordList = new ArrayList<String>();
		
		if (checkIfReady()) {
			for (int i = 0; i < quantity; i++) {
				passwordList.add(generatingPassword());
			}
			
			return passwordList;
		}
		
		return null;
	}
	
	public NerdyPassword setPasswordlength(int length) {		
		return setPasswordlength(length, length);
	}
	
	public NerdyPassword setPasswordlength(int minlength, int maxlength) {
		getNewPassword().setMinLength(minlength);
		getNewPassword().setMaxLength(maxlength);
		
		return this;
	}
	
	public NerdyPassword allowSameCharTwiceInARow(boolean allow) {
		getNewPassword().setSameCharTwiceInARow(allow);
		return this;
	}
	
	public NerdyPassword addNumbersToPosibilChars() {
		getNewPassword().getSymbolList().putAll(PasswordGenerator.genarateNumberSymbollList());
		return this;
	}
	
	public NerdyPassword addLowerLettersToPosibilChars() {
		getNewPassword().getSymbolList().putAll(PasswordGenerator.genarateLowerLetterSymbollList());
		return this;
	}
	
	public NerdyPassword addUpperLettersToPosibilChars() {
		getNewPassword().getSymbolList().putAll(PasswordGenerator.genarateUpperLetterSymbollList());
		return this;
	}
	
	public NerdyPassword addSymbolListToPosibilChars() {
		return addSymbolList1ToPosibilChars().addSymbolList2ToPosibilChars().addSymbolList3ToPosibilChars().addSymbolList4ToPosibilChars().addSymbolList5ToPosibilChars();
	}
	
	public NerdyPassword addSymbolList1ToPosibilChars() {
		getNewPassword().getSymbolList().putAll(PasswordGenerator.addSymbollsToList(getNewPassword().getSymbolList(), PasswordGenerator.getSymbolList1()));
		return this;
	}
	
	public NerdyPassword addSymbolList2ToPosibilChars() {
		getNewPassword().getSymbolList().putAll(PasswordGenerator.addSymbollsToList(getNewPassword().getSymbolList(), PasswordGenerator.getSymbolList2()));
		return this;
	}
	
	public NerdyPassword addSymbolList3ToPosibilChars() {
		getNewPassword().getSymbolList().putAll(PasswordGenerator.addSymbollsToList(getNewPassword().getSymbolList(), PasswordGenerator.getSymbolList3()));
		return this;
	}
	
	public NerdyPassword addSymbolList4ToPosibilChars() {
		getNewPassword().getSymbolList().putAll(PasswordGenerator.addSymbollsToList(getNewPassword().getSymbolList(), PasswordGenerator.getSymbolList4()));
		return this;
	}
	
	public NerdyPassword addSymbolList5ToPosibilChars() {
		getNewPassword().getSymbolList().putAll(PasswordGenerator.addSymbollsToList(getNewPassword().getSymbolList(), PasswordGenerator.getSymbolList5()));
		return this;
	}
	
	public NerdyPassword addOwnSymbolListToPosibilChars(List<Character> SymbolList) {
		getNewPassword().getSymbolList().putAll(PasswordGenerator.genarateNumberSymbollList());
		return this;
	}
	
	public NerdyPassword setCharTypeSequence(CharType[] charTypeSequence) {
		getNewPassword().setCharTypeSequence(charTypeSequence);
		return this;
	}
	
	public NerdyPassword setNumberNeeded(boolean need) {
		getNewPassword().setNeedNumbers(need);
		return this;
	}
	
	public NerdyPassword setLetterNeeded(boolean need) {
		getNewPassword().setNeedLetters(need);
		return this;
	}
	
	public NerdyPassword setLowerUpperLetterNeeded(boolean need) {
		getNewPassword().setNeedLowerUpperLetters(need);
		return this;
	}
	
	public NerdyPassword setSymbollNeeded(boolean need) {
		getNewPassword().setNeedSymbols(need);
		return this;
	}
	
	public NerdyPassword setSecureLVL(int secureLVL) {
		switch (secureLVL) {
		case 0: getNewPassword().setSecureLVLEasy();
			break;
		case 1: getNewPassword().setSecureLVLNormel();
			break;
		case 2: getNewPassword().setSecureLVLHard();
			break;
		default: getNewPassword().setSecureLVLNormel();
			break;
		}
		return this;
	}
}
