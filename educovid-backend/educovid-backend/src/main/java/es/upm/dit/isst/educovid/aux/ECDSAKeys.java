package es.upm.dit.isst.educovid.aux;

import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class ECDSAKeys {
	private ECPublicKey publicKey;
	private ECPrivateKey privateKey;
	private static ECDSAKeys instance = null;
	private final CryptoService cryptoService;
	
	private ECDSAKeys() {
		this.cryptoService = new CryptoService();
	}
	
	public static ECDSAKeys getInstance() {
		if (instance == null) {
			instance = new ECDSAKeys();
		}
		return instance;
			
	}
}
