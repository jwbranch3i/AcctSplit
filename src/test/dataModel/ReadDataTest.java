package test.dataModel;

import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import data_model.DataSource;
import data_model.ReadData;
import javafx.application.Platform;
import transaction_objects.Transaction;

public class ReadDataTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		if (!DataSource.getInstance().open())
		{
			System.out.println("FATAL ERROR: Couldn't connect to database");
			Platform.exit();
		}
	}

	@After
	public void tearDown() throws Exception
	{
		DataSource.getInstance().close();
	}

	@Test
	public void testGetTransactions()
	{
		List<Transaction> list;
		
		list = ReadData.getTransactions(2);
		
		for (int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i));
		}
		System.out.println("***finish");
		
	}

	@Test
	public void testTotals()
	{
		List<Double> list;
		
		list = ReadData.getTotals();
		
		System.out.println(list.toString());
		
	}

}