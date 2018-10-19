package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getDetailByNip(String nip);
	double getDetailGajiByNip(String nip);
//	void addPegawai(PegawaiModel pegawai);
}
