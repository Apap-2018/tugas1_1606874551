package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getDetailByNip(String nip);
	double getDetailGajiByNip(String nip);
	PegawaiModel getPegawaiById(Long id);
//	void addPegawai(PegawaiModel pegawai);
}
