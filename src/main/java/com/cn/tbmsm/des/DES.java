/**
 *  Copyright (C) 2018 HUN.lemon. All right reserved.
 *
 *  
 *  https://lemon2013.github.io
 *
 *  created on 2018年3月21日 下午3:09:20
 *
 */
package com.cn.tbmsm.des;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.SecureRandom;
import java.io.*;

import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;


/**
 * <p>
 * Title: DES
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Company: HUN.lemon
 * </p>
 * @author lemon
 * @date 2018年3月21日
 * 
 */
public class DES {
	/** 设置加密算法为DES */
	private String strAlgorithm = "DES";
	private KeyGenerator objKeyGen;
	private SecretKey secretKeyDES;
	private Cipher objCipher;
	/** 初始化密钥 */
	private String strKey = new String("despty");

	/*
	 * 初始化DES
	 */
	public DES() {
		init();
	}

	/**
	 * 设置密钥
	 * 
	 * @param s
	 */
	public void setKey(String s) {
		this.strKey = s;
	}

	/**
	 * 获得密钥
	 * 
	 * @return strKey
	 */
	public String getKey() {
		if (strKey.equals("")) {
			return "despty";
		}
		return strKey;
	}

	/**
	 * 初始化
	 */
	public void init() {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			objCipher = Cipher.getInstance(strAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public String encryptFile(String strSource, String strResult) {
		setsecretKeyDES();
		try {
			File fileSource = new File(strSource);
			if (fileSource.exists()) {
				
			} else {
				return "NO";
			}

			File fileResult = new File(strResult);
			if (fileResult.exists()) {
				fileResult.createNewFile();
			} else {
				fileResult.createNewFile();
			}
			DataInputStream dataInputStream = new DataInputStream(
					new FileInputStream(fileSource));
			DataOutputStream dataOutputStream = new DataOutputStream(
					new FileOutputStream(fileResult));
			objCipher.init(Cipher.ENCRYPT_MODE, secretKeyDES);
			CipherOutputStream cipherOutputStream = new CipherOutputStream(
					dataOutputStream, objCipher);
			byte[] arr = new byte[1024 * 1024 * 10]; // /缓冲
			int iTotal = dataInputStream.read(arr);
			while (iTotal != -1) {
				byte[] arrTemp = new byte[iTotal];
				for (int k = 0; k < iTotal; k++) {
					arrTemp[k] = arr[k];
				}
				cipherOutputStream.write(arrTemp);
				iTotal = dataInputStream.read(arr);
			}
			cipherOutputStream.close();
			dataInputStream.close();
			dataOutputStream.close();
		} catch (Exception e) {
			return "NO11111";
		}
		return "OK";

	}

	/**
	 * 对文件加密
	 * 
	 * @param strSource
	 *            源文件
	 * @param strResult
	 *            目标文件
	 * @return
	 */
	public String encryptorFile(String strSource, String strResult) {
		setsecretKeyDES();
		try {

			File fileSource = new File(strSource);
			if (fileSource.exists()) {
			} else {
				return "NO";
			}

			File fileResult = new File(strResult);
			if (fileResult.exists()) {
				fileResult.createNewFile();
			} else {
				fileResult.createNewFile();
			}
			DataInputStream dataInputStream = new DataInputStream(
					new FileInputStream(fileSource));
			DataOutputStream dataOutputStream = new DataOutputStream(
					new FileOutputStream(fileResult));
			objCipher.init(Cipher.ENCRYPT_MODE, secretKeyDES);
			CipherOutputStream cipherOutputStream = new CipherOutputStream(
					dataOutputStream, objCipher);
			byte[] arr = new byte[1024 * 1024 * 10]; // /缓冲
			int iTotal = dataInputStream.read(arr);
			while (iTotal != -1) {
				byte[] arrTemp = new byte[iTotal];
				for (int k = 0; k < iTotal; k++) {
					arrTemp[k] = arr[k];
				}
				cipherOutputStream.write(arrTemp);
				iTotal = dataInputStream.read(arr);
			}
			cipherOutputStream.close();
			dataInputStream.close();
			dataOutputStream.close();
		} catch (Exception e) {
			return "NO11111";
		}
		return "OK";

	}

	/**
	 * 对文件解密
	 * 
	 * @param source
	 *            源文件
	 * @param result
	 *            目标文件
	 * @return
	 */
	public String decryptorFile(String source, String result) {
		setsecretKeyDES();
		try {
			File fileSource = new File(source);
			if (fileSource.exists()) {
			} else {
				return "NO";
			}
			File fileResult = new File(result);
			if (fileResult.exists()) {
				fileResult.createNewFile();
			} else {
				fileResult.createNewFile();
			}
			DataInputStream dataInputStream = new DataInputStream(
					new FileInputStream(fileSource));
			DataOutputStream dataOutputStream = new DataOutputStream(
					new FileOutputStream(fileResult));
			objCipher.init(Cipher.DECRYPT_MODE, secretKeyDES);
			CipherInputStream cipherInputStream = new CipherInputStream(
					dataInputStream, objCipher);
			byte[] arr = new byte[1024 * 1024 * 10]; // 缓冲
			int iTotal = cipherInputStream.read(arr);
			while (iTotal != -1) {
				byte[] arrTemp = new byte[iTotal];
				for (int k = 0; k < iTotal; k++) {
					arrTemp[k] = arr[k];
				}
				dataOutputStream.write(arrTemp);
				iTotal = cipherInputStream.read(arr);
			}
			cipherInputStream.close();
			dataInputStream.close();
			dataOutputStream.close();
		} catch (Exception e) {
			return "NO11111";
		}
		return "OK";
	}

	/**
	 * 密钥des
	 */
	private void setsecretKeyDES() {
		try {
			objKeyGen = KeyGenerator.getInstance(strAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		objKeyGen.init(new SecureRandom(strKey.getBytes()));
		secretKeyDES = objKeyGen.generateKey();
	}
}
