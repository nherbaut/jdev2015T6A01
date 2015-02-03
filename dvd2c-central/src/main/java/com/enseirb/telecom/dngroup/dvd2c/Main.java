package com.enseirb.telecom.dngroup.dvd2c;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.common.base.Throwables;

public class Main {

	private static int getPort(int defaultPort) {
		// grab port from environment, otherwise fall back to default port 9999
		String httpPort = ApplicationContext.getProperties().getProperty(
				"bindPort");
		if (null != httpPort) {
			try {
				return Integer.parseInt(httpPort);
			} catch (NumberFormatException e) {
			}
		}
		return defaultPort;
	}

	private static URI getBaseURI() {
		String ip = ApplicationContext.getProperties().getProperty("bindIp");
		return UriBuilder.fromUri("http://" + ip + "/api/").port(getPort(9999))
				.build();
	}

	protected static HttpServer startServer() throws IOException {

		ResourceConfig resources = new ResourceConfig();
		resources.packages("com.enseirb.telecom.s9.endpoints");
		resources.register(CORSResponseFilter.class);
		resources.register(MultiPartFeature.class);
		resources.register(JettisonFeature.class);
		System.out.println("Starting grizzly2...");
		// return GrizzlyServerFactory.createHttpServer(BASE_URI,
		// resourceConfig);
		return GrizzlyHttpServerFactory.createHttpServer(getBaseURI(),
				resources);
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// Properties
		FileInputStream in;
		try {
			ApplicationContext.properties = new Properties();
			in = new FileInputStream("application.properties");
			ApplicationContext.properties.load(in);
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Grizzly 2 initialization
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					HttpServer httpServer = startServer();
					httpServer.getServerConfiguration().addHttpHandler(
							new CLStaticHttpHandler(
									Main.class.getClassLoader(), "/"));

				} catch (IOException e) {
					throw Throwables.propagate(e);
				}
			}
		}).start();

		Thread.currentThread().join();

		// httpServer.stop();
	}
}
