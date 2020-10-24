package hay.analyser;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TransactionAnalyser {

	public static void main(String[] args) throws Exception {
		String csvFile = args[0];
		String paramsFile = args[1];
		
		TransactionAnalyser analyser = new TransactionAnalyser(); 

		List<Transaction> transactions = analyser.populateTransactions(csvFile);
		
		InputParams params = analyser.getParams(paramsFile);

		System.out.println(
				analyser.getStatistic(transactions,
							params.getFromDate(), 
							params.getToDate(), 
							params.getMerchant()
				)
		);
	}
	
	public Statistic getStatistic(
			List<Transaction> transactions, 
			Date fromDate, Date toDate, String merchant) {
		
		List<String> reversal = transactions.stream()
				.filter(t -> t.getType().compareTo("REVERSAL") == 0)
				.map(t -> t.getRelated())
                .collect(Collectors.toList());  
		
		List<Transaction> filtered = transactions.stream()       
                .filter(t -> 
                t.getType().compareTo("PAYMENT") == 0 &&
                t.getMerchant().compareTo(merchant)==0 && 
                !reversal.contains(t.getId()) &&
                t.getDate().compareTo(fromDate) >= 0 && t.getDate().compareTo(toDate) <= 0 )    
                .collect(Collectors.toList());  
		
		Statistic statistic = new Statistic();
		
		double average = filtered.stream().mapToDouble(t -> t.getAmount()).average().orElse(0.0);
		statistic.setNumber(filtered.size());
		statistic.setAverage(average);
		return statistic;
	}
	
	public InputParams getParams(String paramsFile) throws FileNotFoundException, ParseException {
		InputParams params = new InputParams();
		
		Scanner scanner = new Scanner(new File(paramsFile));
		
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if (line.startsWith("fromDate:")) {
				line = line.substring(9).trim();
				Date fromDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(line);  
				params.setFromDate(fromDate);
			}
			if (line.startsWith("toDate:")) {
				line = line.substring(7).trim();
				Date toDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(line);  
				params.setToDate(toDate);
			}
			if (line.startsWith("merchant:")) {
				line = line.substring(9).trim();
				params.setMerchant(line);
			}
		}
		
		return params;
	}
	
 	public List<Transaction> populateTransactions(String csvFile) throws FileNotFoundException, ParseException {
	
		Scanner scanner = new Scanner(new File(csvFile));
		List<Transaction> transactions = new ArrayList<>();		
		
		scanner.nextLine(); // skip headers line
		while (scanner.hasNext()) {
			List<String> line = CsvParser.parseLine(scanner.nextLine());
			
			Transaction transaction = new Transaction();
			transaction.setId(line.get(0));
			
			String dt = line.get(1);
		    Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dt);  
			transaction.setDate(date);
			
			transaction.setAmount(Double.parseDouble(line.get(2)));
			
			transaction.setMerchant(line.get(3));
			transaction.setType(line.get(4));
			transaction.setRelated(line.get(5));
			
			transactions.add(transaction);
			
		}
		scanner.close();
		return transactions;
		
	}
}

//fromDate: 20/08/2018 12:00:00
//toDate: 20/08/2018 13:00:00
//merchant: Kwik-E-Mart
//The output will be:
//Number of transactions = 1
//Average Transaction Value = 59.99