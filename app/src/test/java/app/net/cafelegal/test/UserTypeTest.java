package app.net.cafelegal.test;

import android.content.Context;
import android.content.SharedPreferences;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import udacitynano.com.br.cafelegal.singleton.UserType;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserTypeTest   {

    UserType userType;
    @Mock
    Context mockContext;
    @Mock
    SharedPreferences sharedPreferences;


    @Before
    public void initialize() {
        when(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
    }

    @Test
    public void getInstanceTest() throws Exception {
        when(sharedPreferences.getLong(anyString(),anyLong())).thenReturn((long)1);
        userType = UserType.getInstance(mockContext);
        assertNotNull(userType);
    }

    @Test
    public void isAdvogadoTest() throws Exception {
        when(sharedPreferences.getString(anyString(),anyString())).thenReturn("ADVOGADO");
        userType = UserType.getInstance(mockContext);
        assertTrue(UserType.isAdvogado());

    }

    @Test
    public void getAppUserTypeTest() throws Exception {
        when(sharedPreferences.getString(anyString(),anyString())).thenReturn("ADVOGADO");
        userType = UserType.getInstance(mockContext);
        assertEquals(UserType.getAppUserType(),"ADVOGADO");
    }

    @Test
    public void getUserIdTest() throws Exception {
        when(sharedPreferences.getLong(anyString(),anyLong())).thenReturn((long)1);
        userType = UserType.getInstance(mockContext);
        assertEquals(UserType.getUserId(),1);

    }

}