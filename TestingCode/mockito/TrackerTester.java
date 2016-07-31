package gokul.mock;
import gokul.mock.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class TrackerTester {
@InjectMocks
Tracker tracker=new Tracker();
@Mock
TimeTracker timetrack;
@Test
public void testrefreshtime()
{
	String data="Task1";
	when(timetrack.start(data)).thenReturn((long) 201672746);
	Assert.assertEquals(tracker.refreshtime(data),201672746);
}
@Test
public void teststart()
{
	String data="Task1";
	when(timetrack.start(data)).thenReturn((long) 201672700);
	Assert.assertEquals(tracker.start(data),201672700);
}
@Test
public void testpause(){
	String data="Task1";
	when(timetrack.pause(data)).thenReturn((long) 201672758);
	Assert.assertEquals(tracker.pause(data),201672758);
}
@Test
public void testfinish(){
String data="Task1";
when(timetrack.finish(data)).thenReturn((long) 1085853);
Assert.assertEquals(tracker.finish(data),1085853);
}

}
