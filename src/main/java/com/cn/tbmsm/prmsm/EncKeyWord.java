package com.cn.tbmsm.prmsm;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

import com.cn.tbmsm.util.ConstantsUtil;
import com.cn.tbmsm.util.JPBCUtil;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
public class EncKeyWord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4901765094167804031L;
	private byte[] E1;
	private byte[] E2;
	public EncKeyWord(String keyword){
		Random r = new Random();
		BigInteger ro = BigInteger.valueOf(Math.abs(r.nextInt()));
		Pairing pairing = PairingFactory.getPairing("a.properties");
		Element elmentk = pairing.getZr().newRandomElement();
		elmentk.setFromBytes(ConstantsUtil.USER1);
		Element elmentk1 = pairing.getZr().newRandomElement();
		elmentk1.setFromBytes(ConstantsUtil.USER2);
		Element elmentk2 = pairing.getZr().newRandomElement();
		elmentk2.setFromBytes(ConstantsUtil.USER3);
		Element elmentbase = pairing.getG1().newRandomElement();
		elmentbase.setFromBytes(ConstantsUtil.SYSTEM_BASE);
		Element E1=elmentbase.duplicate().powZn(elmentk.duplicate().mul(ro));
		Element E2 = E1.duplicate().powZn(JPBCUtil.hashToZp(pairing, keyword));
		Element E=E2.duplicate().mul(elmentbase.duplicate().powZn(elmentk1));
		Element E3 = E.duplicate().powZn(elmentk2);
		setE1(E3.toBytes());
		setE2(E1.toBytes());
	}
	public byte[] getE1() {
		return E1;
	}
	public void setE1(byte[] e1) {
		E1 = e1;
	}
	public byte[] getE2() {
		return E2;
	}
	public void setE2(byte[] e2) {
		E2 = e2;
	}
}
