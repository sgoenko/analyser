package hay.analyser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


class TransactionAnalyserTest {
	List<Transaction> transactions = new ArrayList<>();		
	
	@Test
    public void getstatistic() {
		TransactionAnalyser analyser = new TransactionAnalyser(); 
		
		transactions.add(new Transaction("1", new Date(), 1.0, "1","PAYMENT",""));
		transactions.add(new Transaction("2", new Date(), 2.0, "1","PAYMENT",""));
		transactions.add(new Transaction("3", new Date(), 3.0, "1","PAYMENT",""));
		transactions.add(new Transaction("4", new Date(), 4.0, "1","PAYMENT",""));
		transactions.add(new Transaction("5", new Date(), 1.0, "1","REVERSAL","1"));

		final Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, -1);
		Date fromDate = cal.getTime();
		cal.add(Calendar.DATE, +1);
		Date toDate = cal.getTime();
		
		Statistic statistic = analyser.getStatistic(transactions, fromDate, toDate, "1");
        
        Assert.assertTrue(statistic.getNumber() == 3);
        Assert.assertTrue(statistic.getAverage() == 3.0);
    }

}
