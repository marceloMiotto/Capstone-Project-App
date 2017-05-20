package app.net.cafelegal.test;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import udacitynano.com.br.cafelegal.network.NetworkAccess;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetworkAccessTest {

    @Mock
    Context mockContext;

    @Mock
    ConnectivityManager connectivityManager;

    @Mock
    NetworkInfo networkInfo;

    private NetworkAccess mNetworkAccess;

    @Before
    public void initialize() {
        mNetworkAccess = new NetworkAccess(mockContext);
    }

    @Test
    public void networkUp() throws Exception {
        when(mockContext.getSystemService(mockContext.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnected()).thenReturn(false);
        assertFalse(mNetworkAccess.networkUp());

    }




}