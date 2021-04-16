package com.vista.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 避免HttpClient的”SSLPeerUnverifiedException: peer not authenticated”异常
 * 不用导入SSL证书
 * 
 *
 */
public  class SSLClient {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static HttpClientBuilder builder;

    @SuppressWarnings("deprecation")
	public static HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
            ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
            return new DefaultHttpClient(mgr, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取httpclient对象
     * @param https
     * @return
     */
    public static CloseableHttpClient getHttpsClient(boolean https) throws Exception{
		if(builder==null){
			builder=HttpClientBuilder.create();
		}
			
		if(!https){
			return builder.build(); 
		}else{
			X509TrustManager  tm=new X509TrustManager() {
				
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {
				}
				
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {
					
				}
			};
			HostnameVerifier  hv=new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};
			SSLContext ctx=SSLContext.getInstance("TLS");
			ctx.init(null,new X509TrustManager[]{tm} , null);
			SSLConnectionSocketFactory f=new SSLConnectionSocketFactory(ctx,hv);
			builder.setSSLSocketFactory(f);
		}
		return builder.build();
	}

}
