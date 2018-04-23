package com.cn.tbmsm.tbmom;

import java.io.IOException;
import java.util.ArrayList;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class Setup {
	private int N;
	Pairing pairing ;
	public Setup(int N){
		this.N = N;
		pairing = PairingFactory.getPairing("a.properties");
	}
	public ArrayList<TBMONPri> getSystemKey(){
		ArrayList<TBMONPri> listSystemkey = new ArrayList<TBMONPri>();
		TBMONPri objTBMONPri = new TBMONPri();
		for(int i=0;i<N;i++){
			objTBMONPri.setKp1(pairing.getZr().newRandomElement().getImmutable());
			objTBMONPri.setKp2(pairing.getZr().newRandomElement().getImmutable());
			System.out.println("�� "+(i+1)+"���û���\n"+"�ļ�������Կ��"+objTBMONPri.getKp1()+"\n"+"�ؼ��ʼ�����Կ��"+objTBMONPri.getKp2());
			listSystemkey.add(objTBMONPri);
		}
		return listSystemkey;
	}
//	public st
	public static void main(String[] args) throws IOException {
	}
}
