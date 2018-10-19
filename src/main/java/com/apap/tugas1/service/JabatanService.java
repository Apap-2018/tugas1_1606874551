package com.apap.tugas1.service;
import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;


public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	List<JabatanModel> listJabatan();
	JabatanModel findJabatanById(Long id);
	void deleteJabatan(Long id);
	JabatanDb viewAllJabatan();
}
