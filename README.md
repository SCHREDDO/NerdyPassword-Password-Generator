# NerdyPassword-Password-Generator
![build version](https://img.shields.io/badge/version-0.14.0-brightgreen.svg)
![release version](https://img.shields.io/badge/release-v0.0.0-blue.svg)
![framework or language](https://img.shields.io/badge/Java-1.5-blue.svg)
![license](https://img.shields.io/badge/license-AGPL--3.0-lightgrey.svg)

### Current Version 0.0.0
### Development Version 0.14.0

## About
NerdyPassword is a library to generat passwords. The library provides you with methods to easily generate passwords for your needs. The library is programed in Java.

##### Info: 
- English isn’t my first language, so please excuse any mistakes.
- Contact me for proposals or questions.

## Features
### Features Timeline
#### Version 0.14.0
- set password language (min, max)
- min one numer
- min one letter
- min one lower and one upper letter
- min one symbol
- block same character twice in a row
- generate characters password
- generate passwords
- set sequence of characters for password
- predefined security level
- choose what character shout be use for password (numbers, lower/upper letters, symbols)
- choose from predefined symbols lists
- add own symbol list
- generate password with randome characters
### Upcoming Features
- [x] set password language (min, max)
- [x] min one numer
- [x] min one letter
- [x] min one lower and one upper letter
- [x] min one symbol
- [x] block same character twice in a row
- [x] generate characters password
- [x] generate passwords
- [x] set sequence of characters for password
- [x] predefined security level
- [x] choose what character shout be use for password (numbers, lower/upper letters, symbols)
- [x] choose from predefined symbols lists
- [x] add own symbol list
- [ ] use leet speak in password
- [x] generate password with randome characters
- [ ] generate password with words
- [ ] generate password with words and randome characters

## Getting Started
```java
NerdyPassword nerdyPassword = new NerdyPassword();
System.out.println(nerdyPassword.startGeneratingPassword());
```
If NerdyPassword() is use the default secure level is 1.

## Usage
### Predefined Secure Level
#### Secure Level 0
- Password length: 8
- Same character twice in a row: false
- Min one number: true
- Min one letter: true
- Min one lower and one upper letter: true
- Min one symbol: false
- Symbol lists: non
#### Secure Level 1
- Password length: 8 - 25
- Same character twice in a row: false
- Min one number: true
- Min one letter: true
- Min one lower and one upper letter: true
- Min one symbol: true
- Symbol lists: 1
#### Secure Level 2
- Password length: 25 - 50
- Same character twice in a row: false
- Min one number: true
- Min one letter: true
- Min one lower and one upper letter: true
- Min one symbol: true
- Symbol lists: 1, 2, 3

### Predefined Symbol Lists
| List 1 | List 2 | List 3 | List 4 | List 5 |
|--------|--------|--------|--------|--------|
| '_' | '@' | '*' | ',' | '(' |
| '-' | '#' | '+' | ';' | ')' |
|| '§' | '"' | '.' | '{' |
|| '$' | '=' | ':' | '}' |
|| '%' ||| '['|
|| '&' ||| ']' |
|| '!' ||| '<' |
|| '?' ||| '>' |

### Function / Methode
#### Class: NerdyPassword 

| Name | Return | Description | Throws |
|------|--------|-------------|--------|
| NerdyPassword ||||
| resetAllSettings | void |||
| startGeneratingPassword | String |||
| startGeneratingPasswords | List\<String\> |||
| setPasswordlength | NerdyPassword |||
| allowSameCharTwiceInARow | NerdyPassword |||
| addNumbersToPosibilChars | NerdyPassword |||
| addLowerLettersToPosibilChars | NerdyPassword |||
| addUpperLettersToPosibilChars | NerdyPassword |||
| addSymbolListToPosibilChars | NerdyPassword |||
| addSymbolList1ToPosibilChars | NerdyPassword |||
| addSymbolList2ToPosibilChars | NerdyPassword |||
| addSymbolList3ToPosibilChars | NerdyPassword |||
| addSymbolList4ToPosibilChars | NerdyPassword |||
| addSymbolList5ToPosibilChars | NerdyPassword |||
| addOwnSymbolListToPosibilChars | NerdyPassword |||
| setCharTypeSequence | NerdyPassword |||
| setNumberNeeded | NerdyPassword |||
| setLetterNeeded | NerdyPassword |||
| setLowerUpperLetterNeeded | NerdyPassword |||
| setSymbollNeeded | NerdyPassword |||
| setSecureLVL | NerdyPassword |||

### Examples

#### Generating password with one of the defined secure level
```java
NerdyPassword nerdyPassword = new NerdyPassword(2);
System.out.println(nerdyPassword.startGeneratingPassword());
```

#### Defined password settings with one of the constructors
```java
NerdyPassword nerdyPassword = new NerdyPassword(8, true, true, true, true);
System.out.println(nerdyPassword.startGeneratingPassword());
```

#### Generating n password with the same settings
```java
NerdyPassword nerdyPassword = new NerdyPassword();
List<String> passwords = nerdyPassword.startGeneratingPasswords(100);

for (int i = 0; i < passwords.size(); i++) {
  System.out.println(passwords.get(i));
}
```

#### Defined password settings
```java
NerdyPassword nerdyPassword = new NerdyPassword(0);
String password = nerdyPassword.setPasswordlength(10, 15).addSymbohList1ToPosibilChars().startGeneratingPassword();

System.out.println(password);
```

#### Get errorcode
```java
NerdyPassword nerdyPassword = new NerdyPassword(false);
if (nerdyPassword.startGeneratingPassword() == null) {
  System.out.println(nerdyPassword.getErrorCode());
}
```
If startGeneratingPassword() returns a null, the error occurred.

#### Start without default settings
```java
NerdyPassword nerdyPassword = new NerdyPassword(false);
```

#### Like "Start without default settings" but as methode
```java
NerdyPassword nerdyPassword = new NerdyPassword();
nerdyPassword.resetAllSettings();
```

#### Set sequence of characters for password
```java
CharType[] charTypeSequence = new CharType[8];
charTypeSequence[0] = CharType.UPPER_LETTER;
charTypeSequence[1] = CharType.LOWER_LETTER;
charTypeSequence[2] = CharType.LOWER_LETTER;
charTypeSequence[3] = CharType.LOWER_LETTER;
charTypeSequence[4] = CharType.NUMBER;
charTypeSequence[5] = CharType.NUMBER;
charTypeSequence[6] = CharType.NUMBER;
charTypeSequence[7] = CharType.NUMBER;

NerdyPassword nerdyPassword = new NerdyPassword(0);
nerdyPassword.setCharTypeSequence(charTypeSequence);

System.out.println(nerdyPassword.startGeneratingPassword());
```

## List of Error/Warning Codes
| Name | Description |
|------|-------------|
| 0 | No error or warning |
| 401 | Min password length have to be > 0. |
| 402 | Max password length have to be > 0. |
| 403 | Max password length have to be >= Min password length. |
| 501 | Min and max password length have to be the same for a charTypeSequence. |
| 502 | Password length have to be the same as the charTypeSequence.length. |
| 601 | No characters definet for the new password. |
| 701 | Numbers are needed but no numbers are definet for the new password. |
| 702 | Letters are needed but no letters are definet for the new password. |
| 703 | Lower/Upper letters are needed but no lower/upper letters are definet for the new password. |
| 704 | Symbols are needed but no symbols are definet for the new password. |

## Dependencies
### Runtime Dependencies
- Java 1.5 or higher
### Development Dependencies
- Java 1.5

## Unit Tests
No Unit Tests defined.

## Changelog
### = 0.14.0 June 15th 2018 =
#### Added
- set password language (min, max)
- min one numer
- min one letter
- min one lower and one upper letter
- min one symbol
- block same character twice in a row
- generate characters password
- generate passwords
- set sequence of characters for password
- predefined security level
- choose what character shout be use for password (numbers, lower/upper letters, symbols)
- choose from predefined symbols lists
- add own symbol list
- generate password with randome characters

## Support Possibilities
- give proposals
- report bugs

## License
NerdyPassword is released under the [AGPL-3.0](https://www.gnu.org/licenses/agpl-3.0.de.html) License.
