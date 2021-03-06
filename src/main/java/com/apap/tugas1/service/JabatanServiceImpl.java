package com.apap.tugas1.service;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);	
	}

	@Override
	public List<JabatanModel> getListJabatan() {
		return jabatanDb.findAll();
	}

	@Override
	public JabatanModel findJabatanById(Long id) {
		return jabatanDb.getOne(id);
	}

	@Override
	public void deleteJabatan(Long id) {
		jabatanDb.deleteById(id);
	}

	@Override
	public JabatanDb viewAllJabatan() {
		return jabatanDb;
		
	}
}
