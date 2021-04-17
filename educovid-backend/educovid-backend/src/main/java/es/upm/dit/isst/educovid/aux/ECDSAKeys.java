package es.upm.dit.isst.educovid.aux;

import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;

public class ECDSAKeys {
	private ECPublicKey publicKey;
	private ECPrivateKey privateKey;
	private static ECDSAKeys instance = null;
	private final CryptoService cryptoService;

	private ECDSAKeys() {
		this.cryptoService = new CryptoService();
		KeyPair pair = this.cryptoService.getKeyPairGenerator().generateKeyPair();
		this.publicKey = (ECPublicKey) pair.getPublic();
		this.privateKey = (ECPrivateKey) pair.getPrivate();
	}

	public static ECDSAKeys getInstance() {
		if (instance == null) {
			instance = new ECDSAKeys();
		}
		return instance;
	}

	public ECPublicKey getPublicKey() {
		return this.publicKey;
	}

	public ECPrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public byte[] getPublicKeyUncompressed() {
		return CryptoService.toUncompressedECPublicKey(this.publicKey);
	}

	public String getPublicKeyBase64() {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(this.getPublicKeyUncompressed());
	}
}
