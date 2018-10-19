package com.apap.tugas1.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;


@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public PegawaiModel getDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);		
	}

	@Override
	//untuk menghitung gaji pegawai
	public double getDetailGajiByNip(String nip) {
		PegawaiModel pegawai = this.getDetailByNip(nip);
		double totalGaji = 0;
		double gajiMax = 0;
		double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
		for (JabatanModel jabatan : pegawai.getJabatanList()) {
			if (jabatan.getGajiPokok() > gajiMax) {
				gajiMax = jabatan.getGajiPokok();
			}
		}
		System.out.println("gaji pokok : " + gajiMax);
		System.out.println("tunjangan : " + presentaseTunjangan);
		totalGaji = gajiMax + (gajiMax*presentaseTunjangan/100);
		return totalGaji;
	}

	@Override
	public PegawaiModel getPegawaiById(Long id) {
		return pegawaiDb.getOne(id);
	}

//	@Override
//	//untuk menambahkan pegawai
//	public void addPegawai(PegawaiModel pegawai) {
//		pegawaiDb.save(pegawai);
//		
//	}	
}
