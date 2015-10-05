package tony.project.language.interfaces;

import java.util.List;

import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;

import tony.project.language.domain.Staff;

public interface StaffDAO {

	public void saveStaff(Staff user);
	
	public ItemCollection<ScanOutcome> getStaff(String hashKey);
	
	public void updateStaff(String emailAddress, String passWord);
	
	public void updateStaffEmail(String emailAddress);
	
	public void deleteStaff(String hashKey);
	
	
}
