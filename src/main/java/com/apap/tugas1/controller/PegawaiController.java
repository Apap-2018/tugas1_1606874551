package com.apap.tugas1.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	//home
	@RequestMapping("/")
	private String home(Model model) {
		List<InstansiModel> listInstansi = instansiService.listInstansi();
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "home";
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
	
	//mencari pegawai termuda dan tertua di suatu instansi
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam ("idInstansi") Long id, Model model) {
		InstansiModel instansiObj = instansiService.getInstansiById(id);
		List<PegawaiModel> listPegawai = instansiObj.getPegawaiInstansi();
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		model.addAttribute("listJabatan", listJabatan);
		PegawaiModel pegawaiTertua;
		PegawaiModel pegawaiTermuda;
		
		if(listPegawai.size()>0) {
			pegawaiTertua = listPegawai.get(1);
			pegawaiTermuda = listPegawai.get(1);
			
			for (PegawaiModel pegawai : listPegawai) {
				Date tanggalPegawaiTarget = pegawai.getTanggalLahir();
				
				if (pegawai.getTanggalLahir().before(pegawaiTertua.getTanggalLahir())) {
					pegawaiTertua = pegawai;
				}
				else if (pegawai.getTanggalLahir().after(pegawaiTermuda.getTanggalLahir())){
					pegawaiTermuda = pegawai;
				}
			}
			model.addAttribute("listPegawai", listPegawai);
			model.addAttribute("pegawaiTermuda", pegawaiTermuda);
			model.addAttribute("pegawaiTertua", pegawaiTertua);
			model.addAttribute("listJabatan", listJabatan);
		}
		return "pegawai-termuda-tertua";
		
	}
}
