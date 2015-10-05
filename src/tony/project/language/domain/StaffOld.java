package tony.project.language.domain;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;

import tony.project.language.interfaces.StaffDAO;

@DynamoDBTable(tableName = "Staff")
public class StaffOld extends RootObject<StaffOld>implements StaffDAO {

	private static String StaffName;
	private String emailAddress;
	private String password;

	@DynamoDBHashKey(attributeName = "StaffName")
	public static String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		this.StaffName = staffName;
	}

	@DynamoDBAttribute(attributeName = "PrivateEmail")
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@DynamoDBAttribute(attributeName = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public StaffOld(String staffName, String emailAddress, String password) {
		super();
		this.StaffName = staffName;
		this.emailAddress = emailAddress;
		this.password = password;
	}

	public StaffOld() {
		super();
	}

	@Override
	public String toString() {
		return "Staff [staffName=" + StaffName + ", emailAddress=" 
					+ emailAddress + ", password=" + password + "]";
	}

	public void saveStaff(StaffOld user) {
		save(user);
	}

	public void deleteStaff(String hashKey) {
		try {
			deleteByMapper(hashKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateStaff(String Email, String Password) {
		
		update(this,this.getStaffName(), Email, Password);
	}



	@Override
	public void updateStaffEmail(String emailAddress) {

		Map<String, String> expressionAttributeNames = new HashMap<>();
		expressionAttributeNames.put("#PE", "PrivateEmail");
		
		Map<String, String> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":val1", emailAddress);
		
		
		
	}

	@Override
	public void saveStaff(Staff user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemCollection<ScanOutcome> getStaff(String hashKey) {
		// TODO Auto-generated method stub
		return null;
	}
}
