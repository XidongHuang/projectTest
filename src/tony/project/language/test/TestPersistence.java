package tony.project.language.test;

import java.util.Iterator;

import org.junit.Test;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;

import tony.project.language.domain.Staff;
import tony.project.language.interfaces.StaffDAO;

public class TestPersistence {

	private static StaffDAO staffDao = new Staff();

	@Test
	public void testSave(){
		
		Staff newUser = new Staff("001","Hanna","hanna.lin@algomau.ca","algomauESL");
		staffDao.saveStaff(newUser);
		
		newUser = new Staff("002","Ashley","ashley@algomau.ca","algomauLevel4");
		staffDao.saveStaff(newUser);
		
		newUser = new Staff("Tony","xhuang@algomau.ca","12345678");
		staffDao.saveStaff(newUser);
		
		System.out.println(newUser);
		
		
	}
	
	@Test
	public void testRetrieved(){

		
		ItemCollection<ScanOutcome> users= staffDao.getStaff("Tony");
		Iterator<Item> iterator = users.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
		
		
	}
	
	@Test
	public void testUpdate(){
		ItemCollection<ScanOutcome> item = staffDao.getStaff("Tony");
//		item.updateStaff("ewrau@qq.com", "666666");
		
		
	}
	@Test
	public void testDelete(){
		staffDao.deleteStaff("Tony");
		ItemCollection<ScanOutcome> user1 = staffDao.getStaff("Tony");
		Iterator<Item> iterator = user1.iterator();
		while(iterator.hasNext()){
		System.out.println(iterator.next().toJSONPretty());
		}
		
	}
	
	
	
	
}
