package com.cn.tbmsm.util;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

import com.cn.tbmsm.sha.SHAUtil;

public class JPBCUtil {

	public static Element hashToZp(Pairing pairing, String message) {
		String strHashMeg = SHAUtil.SHA512(message);
//		System.out.println(strHashMeg);
		byte[] byte_message = strHashMeg.getBytes();
		Element hash = pairing.getZr().newRandomElement().setFromHash(byte_message, 0, byte_message.length)
				.getImmutable();
		return hash;
	}

	public static Element hashToG1(Pairing pairing, String message) {
		String strHashMeg = SHAUtil.SHA256(message);
		System.out.println(strHashMeg);
		byte[] byte_message = strHashMeg.getBytes();
		Element hash = pairing.getG1().newRandomElement().setFromHash(byte_message, 0, byte_message.length)
				.getImmutable();
		return hash;
	}

	public static Element hashToG2(Pairing pairing, String message) {
		String strHashMeg = SHAUtil.SHA256(message);
		System.out.println(strHashMeg);
		byte[] byte_message = strHashMeg.getBytes();
		Element hash = pairing.getG2().newRandomElement().setFromHash(byte_message, 0, byte_message.length)
				.getImmutable();
		return hash;
	}

	public static Element hashToGT(Pairing pairing, String message) {
		String strHashMeg = SHAUtil.SHA256(message);
		System.out.println(strHashMeg);
		byte[] byte_message = strHashMeg.getBytes();
		Element hash = pairing.getGT().newRandomElement().setFromHash(byte_message, 0, byte_message.length)
				.getImmutable();
		return hash;
	}
}
