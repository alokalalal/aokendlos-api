package com.intentlabs.endlos.firestore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.firestore.Firestore;

/**
 * This is a firebase configuration.
 *
 * @author Hemil.Shah
 * @since 26/04/2022
 */

@ComponentScan
@Configuration
public class FirestoreConfiguration {

	@Value("${firebase.credential.path}")
	private String path;

	@Bean(name = "firestore")
	public Firestore getFireStore() {
//		InputStream serviceAccount;
//		try {
//			serviceAccount = new FileInputStream(path);
//			GoogleCredentials credentials;
//			credentials = GoogleCredentials.fromStream(serviceAccount);
//			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).build();
//			FirebaseApp.initializeApp(options);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return FirestoreClient.getFirestore();
		return null;
	}
}