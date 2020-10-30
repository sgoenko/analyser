package hay.analyser;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import hay.analyser.entity.InputParams;
import hay.analyser.entity.Transaction;
import hay.analyser.utils.TransactionAnalyser;

public class Runner {
	public static void main(String[] args) {
		String csvFile = args[0];
		String paramsFile = args[1];
		
		TransactionAnalyser analyser = new TransactionAnalyser(); 

		List<Transaction> transactions = new ArrayList<>();
		try {
			transactions = analyser.getTransactions(csvFile);
		} catch (FileNotFoundException e1) {
			System.out.println("Input file missing.");
		} catch (ParseException e1) {
			System.out.println("Input file is incorrect.");
		}
		
		InputParams params = new InputParams();
		try {
			params = analyser.getParams(paramsFile);
		} catch (FileNotFoundException e) {
			System.out.println("Parameter file missing.");
		} catch (ParseException e) {
			System.out.println("Parameter file is incorrect.");
		}

		System.out.println(
				analyser.getStatistic(transactions,
							params.getFromDate(), 
							params.getToDate(), 
							params.getMerchant()
				)
		);
	}
}
