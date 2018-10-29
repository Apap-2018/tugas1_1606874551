package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanDb jabatanDb;

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
		
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		return "add-jabatan-response";
	}
	
	//melihat data jabatan
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam ("idJabatan") Long id, Model model) { 
		JabatanModel jabatan = jabatanService.findJabatanById(id);
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	//mengupdate data jabatan get
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam ("idJabatan") Long id, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(id);
		model.addAttribute("jabatan", jabatan);
		return "update-jabatan";
	}
	
	//mengupdate data jabatan post
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		return "update-jabatan-response";
	}
	
	//menghapus data jabatan
	@RequestMapping(value="/jabatan/hapus", method = RequestMethod.POST)
	private String deleteJabatanSubmit(Long id) {
		JabatanModel jabatan = jabatanService.findJabatanById(id);
		
		if (jabatan.jabatanSize() < 1) {
			jabatanService.deleteJabatan(id);
			return "delete-jabatan-response";
		}		
		else {
			return "error-jabatan-delete";
		}
	}
	
	//menghapus data jabatan
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	public String viewAllJabatan(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		for(JabatanModel jabatan: listJabatan) {
			jabatan.setSizePegawai(jabatan.jabatanSize());
			}
		model.addAttribute("listJabatan", listJabatan);
		return "view-all-jabatan";
	 }
	
}
