package gokul.mock;

import java.sql.ResultSet;
import java.sql.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
public class testfinish {
	private static final String query = null;
	@InjectMocks
	Finishtask finishtasks=new Finishtask();
	@Mock
	Finishtask finishtask;
	public void setup(){
	
	Object statement;
	ResultSet resultset =((Object) statement).executeQuery(query);
	while(resultset.next()){
		
	}
	}
	@Test
	when(finishtask.statement.executQuery(query)).thenReturn(resultset);
	when(finishtask.updateStatement.executeUpdate(updateQuery)).thenReturn(resultset);
    Assert.assertEquals(finishtask.Finishtask(data),20167776);
	}
	
	
	
}
