package hay.analyser.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import hay.analyser.entity.InputParams;
import hay.analyser.entity.Statistic;
import hay.analyser.entity.Transaction;

public class TransactionAnalyser {
		
	public Statistic getStatistic(
			List<Transaction> transactions, 
			Date fromDate, Date toDate, String merchant) {
		
		List<String> reversalTransactions = transactions.stream()
				.filter(t -> t.getType().compareTo("REVERSAL") == 0)
				.map(t -> t.getRelated())
                .collect(Collectors.toList());  
		
		List<Transaction> filteredTransactions = transactions.stream()       
                .filter(t -> 
                t.getType().compareTo("PAYMENT") == 0 &&
                t.getMerchant().compareTo(merchant)==0 && 
                !reversalTransactions.contains(t.getId()) &&
                t.getDate().compareTo(fromDate) >= 0 && t.getDate().compareTo(toDate) <= 0 )    
                .collect(Collectors.toList());  
		
		double average = filteredTransactions.stream().mapToDouble(t -> t.getAmount()).average().orElse(0.0);

		Statistic statistic = new Statistic();
		statistic.setNumber(filteredTransactions.size());
		statistic.setAverage(average);

		return statistic;
	}
	
	public InputParams getParams(String paramsFile) throws FileNotFoundException, ParseException {
		InputParams params = new InputParams();
		
		Scanner scanner = new Scanner(new File(paramsFile));
		
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if (line.startsWith("fromDate:")) {
				Date fromDate = getDateFromInputLine(line);  
				params.setFromDate(fromDate);
			}
			if (line.startsWith("toDate:")) {
				Date toDate = getDateFromInputLine(line);  
				params.setToDate(toDate);
			}
			if (line.startsWith("merchant:")) {
				line = line.substring("merchant:".length()).trim();
				params.setMerchant(line);
			}
		}
		
		return params;
	}
	
	private Date getDateFromInputLine(String line) throws ParseException {
		String date = line.split(": ")[1];
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);  
	}
	
 	public List<Transaction> getTransactions(String csvFile) throws FileNotFoundException, ParseException {
	
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

