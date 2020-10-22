To build the project run the command "mvn package" from the current location. 
In the /target folder the analyser-0.0.1-SNAPSHOT.jar file will appear.
To get result execute the command "java -jar analyser-0.0.1-SNAPSHOT.jar input.csv params.txt".

input.csv

ID, Date, Amount, Merchant, Type, Related Transaction
WLMFRDGD, 20/08/2018 12:45:33, 59.99, Kwik-E-Mart, PAYMENT,
YGXKOEIA, 20/08/2018 12:46:17, 10.95, Kwik-E-Mart, PAYMENT,
LFVCTEYM, 20/08/2018 12:50:02, 5.00, MacLaren, PAYMENT,
SUOVOISP, 20/08/2018 13:12:22, 5.00, Kwik-E-Mart, PAYMENT,
AKNBVHMN, 20/08/2018 13:14:11, 10.95, Kwik-E-Mart, REVERSAL, YGXKOEIA
JYAPKZFZ, 20/08/2018 14:07:10, 99.50, MacLaren, PAYMENT,

params.txt

fromDate: 20/08/2018 12:00:00
toDate: 20/08/2018 13:00:00
merchant: Kwik-E-Mart