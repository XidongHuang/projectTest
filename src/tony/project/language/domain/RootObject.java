package tony.project.language.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.sun.org.apache.xml.internal.security.Init;

import tony.project.language.initial.Initial;

public class RootObject<T> {

	private static DynamoDB dynamoDB;
	private static AmazonDynamoDBClient client;
	private static DynamoDBMapper mapper;
	
	
	private Class<T> clazz;

	public RootObject() {

		Type superClass = getClass().getGenericSuperclass();

		if (superClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) superClass;

			Type[] typeArgs = parameterizedType.getActualTypeArguments();

			if (typeArgs != null && typeArgs.length > 0) {
				if (typeArgs[0] instanceof Class) {

					clazz = (Class<T>) typeArgs[0];
				}

			}

		}

	}

	public void save(T entity) {
		try {

			client = Initial.getClient();
			mapper = new DynamoDBMapper(client);
			mapper.save(entity);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public ItemCollection<ScanOutcome> load(String accountName) {
		client = Initial.getClient();
		dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable(clazz.getSimpleName());
		
		
		Map<String, Object> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":an", accountName);
		
		ItemCollection<ScanOutcome> items = table.scan(
				"accountName = :an",
				"accountName, Password, PrivateEmail",
				null,
				expressionAttributeValues);
		

		
//		T item = null;
//
//		try {
//
//			client = Initial.getClient();
//			mapper = new DynamoDBMapper(client);
//			item = mapper.load(clazz, hashKey);
//			return item;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
		return items;
	}
	
	
	public void update(T entity,String hashKey,String emailAddress, String passWord){
		T item = null;
		try {
			client = Initial.getClient();
			mapper = new DynamoDBMapper(client);
			dynamoDB = new DynamoDB(client);
			String tableName = entity.getClass().getSimpleName();
			Table table = dynamoDB.getTable(tableName);
			System.out.println(tableName);
			TableDescription desc = table.describe();
			System.out.println(desc);
			
			
			Map<String, String> expressionAttributeNames = new HashMap<>();
			expressionAttributeNames.put("#PE", "PrivateEmail");
			expressionAttributeNames.put("#P", "password");
			
			Map<String, Object> expressionAttributeValues = new HashMap<>();
			expressionAttributeValues.put(":val1", emailAddress);
			expressionAttributeValues.put(":val2", passWord);
			
			
//			table.updateItem("StaffName", hashKey,
//					"set #PE = :val1, #P = :val2",
//					expressionAttributeNames, expressionAttributeValues);
			
			
//			Field hashKeyField = entity.getClass().getDeclaredField("staffName");
//			Method methodEmail = entity.getClass().getDeclaredMethod("setEmailAddress", String.class);
//			Method methodPassword = entity.getClass().getDeclaredMethod("setPassword", String.class);
//			
//			hashKeyField.setAccessible(true);
//			
//			System.out.println(hashKeyField);
//			
//			String hashKey = (String) hashKeyField.get(entity);
//			
//			item = load(hashKey);
//			
//			System.out.println(item);
//			
//			methodEmail.invoke(item, emailAddress);
//			methodPassword.invoke(item, passWord);
//			mapper.save(item);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		
	}
	
	
	public void deleteByMapper(String hashKey){
		T item = null;
		
		try {
			client = Initial.getClient();
			mapper = new DynamoDBMapper(client);
			item = loadByMapper(hashKey);
			
			mapper.delete(item);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
	}
	
	
	public T loadByMapper(String hashKey){
		
		client = Initial.getClient();
		mapper = new DynamoDBMapper(client);
		
		return mapper.load(clazz,hashKey);
		
		
	}
	

}
