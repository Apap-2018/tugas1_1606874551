package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	//home
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "home";
	}
	
	//melihat data jabatan
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam ("idJabatan") Long id, Model model) { 
		JabatanModel jabatan = jabatanService.findJabatanById(id);
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	//melihat data pegawai
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	public String viewPegawai(@RequestParam ("pegawaiNip") String pegawaiNip, Model model) {
		PegawaiModel pegawai = pegawaiService.getDetailByNip(pegawaiNip);
		double gaji = pegawaiService.getDetailGajiByNip(pegawaiNip);
		System.out.println(gaji);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", gaji);
		return "view-pegawai";
	}
	

	
	
	
	
	
	

}
