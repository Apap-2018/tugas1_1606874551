package com.apap.tugas1.service;
import java.util.List;

import com.apap.tugas1.model.*;

public interface InstansiService {
	InstansiModel getInstansiById(Long id);
	List<InstansiModel> listInstansi();
}
