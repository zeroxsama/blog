package cn.cjun.blog.service;



import cn.cjun.blog.rsa.Base64Utils;
import cn.cjun.blog.rsa.RSA;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;


/**
 * RSA 加解密
 *
 * @author Administrator
 */
@Service()
public class RsaService {

    /***
     * RSA解密
     *
     * @param encryptData
     * @return
     * @throws Exception
     */
    public String RSADecryptDataPEM(String encryptData, String prvKey) throws Exception {
    	byte[] encryptBytes = encryptData.getBytes();
        byte[] prvdata = RSA.decryptByPrivateKey(Base64Utils.decode(encryptData), prvKey);

        String outString = new String(prvdata, "UTF-8");
        return outString;
    }


	public String RSADecryptDataBytes(byte[] encryptBytes, String prvKey)
			throws Exception {
		// TODO Auto-generated method stub
    	byte[] prvdata = RSA.decryptByPrivateKey(encryptBytes, prvKey);
        String outString = new String(prvdata, "utf-8");
        return outString;
	}

    /***
     * RSA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String RSAEncryptDataPEM(String data, String pubKey) throws Exception {

        byte[] pubdata = RSA.encryptByPublicKey(data.getBytes("UTF-8"), pubKey);
        String outString = new String(Base64Utils.encode(pubdata));

        return outString;
    }

	public String getRsaAlgorithm() {
		// TODO Auto-generated method stub
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keyFactory.getAlgorithm();
	}

	
}
