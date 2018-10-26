package com.clip.framework;

import org.ajbrown.namemachine.Gender;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;

import java.util.*;

public class MainGenerator extends MainDriver{
	
	
	
	public Map<String, String> generateNameInfo() {
		Map<String, String> nameInfo = new HashMap<>();
		try {
			String[] firstNames = {"Ben","Josh","Arturo","Brandon","Bob","David","Paul","Joseph","Mark","Mary","Betty","Helen","Laura","Carol","Michelle"};
			String[] lastNames = {"Smith", "Johnson","Silvia","Burch","Morgan","Adams","Bailey","Cooper","Brown","Gonzalez"};
			String[] secondLastNames = {"QA","Garcia","Lopez","Martin","Gomez","Suarez"};
			Random rand = new Random();
			String firstName = firstNames[rand.nextInt(firstNames.length)];
			String lastName = lastNames[rand.nextInt(lastNames.length)];
			String secondLastName = secondLastNames[rand.nextInt(secondLastNames.length)];
			
			nameInfo.put("firstName", firstName);
			nameInfo.put("lastName", lastName);
			nameInfo.put("secondLastName", secondLastName);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not generate name " + e);
		}
		return nameInfo;
	}
	
	public Map<String, String> generateAddressInfo(){
		Map<String, String> addressInfo = new HashMap<>();
		try {
			Random rand = new Random();
			Integer streetNumber = rand.nextInt(9999-1000+1) + 1000;
			String[] streetCompass = {"North","East","South","West"};
			String streetDirection = streetCompass[rand.nextInt(3)];
			String[] streetNames = {"Monza Dr", "Smith Dr","Main St","State St","Blair St"};
			String streetName = streetNames[rand.nextInt(streetNames.length)];
			String postalCode = "45454";
			String colony = "El Cebadero";
			String municipality = "Zapotlanejo";
			String state = "Jalisco";
			String country = "Mexico";
			String lineTwo = "";
			
			lineTwo = "APT " + rand.nextInt(9);
			
			
			
			addressInfo.put("addressLine1", Integer.toString(streetNumber) +" " + streetDirection + " " + streetName );
			addressInfo.put("addressLine2", lineTwo);
			addressInfo.put("postalCode", postalCode);
			addressInfo.put("colony", colony);
			addressInfo.put("municipality", municipality);
			addressInfo.put("state", state);
			addressInfo.put("country", country);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not generate address info " + e);
		}
		return addressInfo;
	}
	
	public long generateUSPhoneNumber() {
		long number;
		Random rand = new Random();
		StringBuilder builder = new StringBuilder();
		try {
			int areaCode,firstThree,lastFour = -1;
			areaCode = rand.nextInt(789 - 200 + 1) + 200;
			firstThree = rand.nextInt(789 - 200 + 1) + 200;
			lastFour = rand.nextInt(9999);
			builder.append(areaCode);
			builder.append(firstThree);
			builder.append(lastFour);
			number = Long.valueOf(builder.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not generate us phone number " + e);
		}
		return number;
	}
	
	public long generateMexPhoneNumber() {
		long number;
		Random rand = new Random();
		StringBuilder builder = new StringBuilder();
		try {
			int firstFour,lastFour = -1;
			firstFour = rand.nextInt(7890-2000+1) + 2000;
			lastFour = rand.nextInt(9999);
			builder.append(5255);
			builder.append(firstFour);
			builder.append(lastFour);
			number = Long.valueOf(builder.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not generate mex phone number " + e);
		}
		return number;
	}
	
	public String generateMerchantName() {
		//Random 50 word generation
		String[] merchantNames = {"mastermind","expertise","crevice",	"want","border",	"sale","disappear", 
				"package","lifestyle","finger","reactor","drill",	"resource","foundation","gallery","break", 
				"food","allowance","lot","salesperson","awful","corruption","illustrate","horn","perfect", 
				"avenue","criticism","fee","duck","impress","dragon",	"colorful","enter","ant","disturbance", 
				"facade","worker","suspect","ready","feminine","bath","guest","pneumonia","computer","spin", 
				"unaware","choke","campaign","camping","definition"};
		String merchantName = "";
		Random rand = new Random();
		try {
			int numOfWords = rand.nextInt(4-2 + 1) +2;
			//building merchant name and capitalizing first letters
			for(int i = 0; i<numOfWords;i++) {
				String selectedName = merchantNames[rand.nextInt(merchantNames.length)];
				merchantName += Character.toUpperCase(selectedName.charAt(0)) + selectedName.substring(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not generate merchant name " + e);
		}
		return merchantName;
	}
	
	public Map<String, String> generateBillingInfo(Integer billInt){
		//IF INT IS NULL WE GET RANDOM BANK
		Map<String,String> bancomerInfo = new HashMap<>();
		Map<String,String> santanderInfo = new HashMap<>();
		Map<String,String> banorteInfo = new HashMap<>();
		Map<String,String> banamexInfo = new HashMap<>();
		Map<String,String> bancoInfo = new HashMap<>();
		Map<String,String> finalInfo = new HashMap<>();
		List<Map<String, String>> billingList = new ArrayList<>();
		Random rand = new Random();
		try {
			//#0
			bancomerInfo.put("simpleName", "Bancomer");
			bancomerInfo.put("fullName", "BBVA Bancomer, S.A.");
			bancomerInfo.put("bankNumber", "012298026516924616");
			billingList.add(bancomerInfo);
			//#1
			santanderInfo.put("simpleName", "Santander");
			santanderInfo.put("fullName", "Banco Santander, S.A.");
			santanderInfo.put("bankNumber", "014180200084203115");
			billingList.add(santanderInfo);
			//#2
			banorteInfo.put("simpleName", "Banorte");
			banorteInfo.put("fullName", "Banco Mercantil del Norte, S.A.");
			banorteInfo.put("bankNumber", "072180000148794922");
			billingList.add(banorteInfo);
			//#3
			banamexInfo.put("simpleName", "Banamex");
			banamexInfo.put("fullName", "Banco Nacional de México, S.A.");
			banamexInfo.put("bankNumber", "002910900761221390");
			billingList.add(banamexInfo);
			//#4
			bancoInfo.put("simpleName", "BancoAzteca");
			bancoInfo.put("fullName", "Banco Azteca, S.A.");
			bancoInfo.put("bankNumber", "127180001086276964");
			billingList.add(bancoInfo);
			
			if(billInt.equals(null)) {
				finalInfo = billingList.get(rand.nextInt(billingList.size()));
			}else {
				finalInfo = billingList.get(billInt);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not generate billing info " + e);
		}
		return finalInfo;
	}
	public List<Name> generateNewNameStuff(){
		Map<String, String> nameInfo = new HashMap<>();
		List<Name> names;
		Gender genderRand = Gender.MALE;
		try{
			Random rand = new Random();
			if(rand.nextInt(2) > 0){
				genderRand = Gender.MALE;
			}else{
				genderRand = Gender.FEMALE;
			}
			NameGenerator generator = new NameGenerator();

			names = generator.generateNames(1, genderRand);
		}catch (Exception e){
			e.printStackTrace();
			throw new IllegalStateException("Could not generate new name stuff");
		}
		return names;

	}
}