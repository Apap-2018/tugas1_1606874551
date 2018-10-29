package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	//home
	@RequestMapping("/")
	private String home(Model model) {
		List<InstansiModel> listInstansi = instansiService.getListInstansi();
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	//melihat data pegawai
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	public String viewPegawai(@RequestParam ("pegawaiNip") String pegawaiNip, Model model) {
		PegawaiModel pegawai = pegawaiService.getDetailByNip(pegawaiNip);
		double gaji = pegawaiService.getDetailGajiByNip(pegawaiNip);
//		System.out.println(gaji);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", gaji);
		return "view-pegawai";
	}
	
	//mencari pegawai termuda dan tertua di suatu instansi
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam ("idInstansi") Long id, Model model) {
		InstansiModel instansiObj = instansiService.getInstansiById(id);
		List<PegawaiModel> listPegawai = instansiObj.getPegawaiInstansi();
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
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
	
	//tambah pegawai
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setInstansi(new InstansiModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getProvinsiList());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		return "add-pegawai";
	}	

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		//proses generate NIP
		String nip = "";
		nip += pegawai.getInstansi().getId();
		String[] tglLahir = pegawai.getTanggalLahir().toString().split("-");
		String tglLahirString = tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2, 4);
		nip += tglLahirString;
		nip += pegawai.getTahunMasuk();
		int counterSama = 1;
		for (PegawaiModel pegawaiInstansi:pegawai.getInstansi().getPegawaiInstansi()) {
			if (pegawaiInstansi.getTahunMasuk().equals(pegawai.getTahunMasuk()) && pegawaiInstansi.getTanggalLahir().equals(pegawai.getTanggalLahir())) {
				counterSama += 1;
			}	
		}
		nip += "0" + counterSama;
		//proses penambahan object pegawai ke Db
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai-response";
	}
	
	//ubah pegawai
		@RequestMapping(value = "/pegawai/ubah")
		public String changePegawai(@RequestParam("nip") String nip, Model model) {
			PegawaiModel pegawai = pegawaiService.getDetailByNip(nip);
			
			model.addAttribute("listProvinsi", provinsiService.getProvinsiList());
			model.addAttribute("listJabatan", jabatanService.getListJabatan());
			model.addAttribute("pegawai", pegawai);
			return "update-pegawai";	
		}	
		
		@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
		private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
			String nip = "";
			
			nip += pegawai.getInstansi().getId();
			System.out.println(pegawai.getInstansi().getId());
			System.out.println(pegawai.getId());
			
			String[] tglLahir = pegawai.getTanggalLahir().toString().split("-");
			String tglLahirString = tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2, 4);
			nip += tglLahirString;
			System.out.println(pegawai.getTanggalLahir());
			
			nip += pegawai.getTahunMasuk();
			System.out.println(pegawai.getTahunMasuk());
			
			int counterSama = 1;
			for (PegawaiModel pegawaiInstansi : pegawai.getInstansi().getPegawaiInstansi()) {
				if (pegawaiInstansi.getTahunMasuk().equals(pegawai.getTahunMasuk()) && pegawaiInstansi.getTanggalLahir().equals(pegawai.getTanggalLahir()) && pegawaiInstansi.getId() != pegawai.getId()) {
					counterSama += 1;
				}	
			}
			nip += "0" + counterSama;

			pegawai.setNip(nip);
			System.out.println(pegawai.getNip());
			pegawaiService.addPegawai(pegawai);
			model.addAttribute("pegawai", pegawai);
			return "add-response";
		}
		
		//cari pegawai
		@RequestMapping(value= "/pegawai/cari", method=RequestMethod.GET)
		private String cariPegawaiSubmit(
				@RequestParam(value="idProvinsi", required = false) String idProvinsi,
				@RequestParam(value="idInstansi", required = false) String idInstansi,
				@RequestParam(value="idJabatan", required = false) String idJabatan,
				Model model) {
			
			
			
			model.addAttribute("listProvinsi", provinsiService.getProvinsiList());
			model.addAttribute("listInstansi", instansiService.getListInstansi());
			model.addAttribute("listJabatan", jabatanService.getListJabatan());
			List<PegawaiModel> listPegawai = pegawaiService.getListPegawai();
			
			if ((idProvinsi==null || idProvinsi.equals("")) && (idInstansi==null||idInstansi.equals("")) && (idJabatan==null||idJabatan.equals(""))) {
				return "search-pegawai";
			}
			else {
				if (idProvinsi!=null && !idProvinsi.equals("")) {
					List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
					for (PegawaiModel peg: listPegawai) {
						if (((Long)peg.getInstansi().getProvinsi().getId()).toString().equals(idProvinsi)) {
							temp.add(peg);
						}
					}
					listPegawai = temp;
					model.addAttribute("idProvinsi", Long.parseLong(idProvinsi));
				}
				else {
					model.addAttribute("idProvinsi", "");
				}
				if (idInstansi!=null&&!idInstansi.equals("")) {
					List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
					for (PegawaiModel peg: listPegawai) {
						if (((Long)peg.getInstansi().getId()).toString().equals(idInstansi)) {
							temp.add(peg);
						}
					}
					listPegawai = temp;
					model.addAttribute("idInstansi", Long.parseLong(idInstansi));
				}
				else {
					model.addAttribute("idInstansi", "");
				}
				if (idJabatan!=null&&!idJabatan.equals("")) {
					List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
					for (PegawaiModel peg: listPegawai) {
						for (JabatanModel jabatan:peg.getJabatanList()) {
							if (((Long)jabatan.getId()).toString().equals(idJabatan)) {
								temp.add(peg);
								break;
							}
						}
						
					}
					listPegawai = temp;
					model.addAttribute("idJabatan", Long.parseLong(idJabatan));
				}
				else {
					model.addAttribute("idJabatan", "");
				}
			}
			model.addAttribute("listPegawai",listPegawai);
			return "search-pegawai";
			
		}
}
