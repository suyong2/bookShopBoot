package com.bookshop.springboot.web.common.base;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookshop.springboot.web.dto.ImagesSaveRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController  {
	protected static String CURR_IMAGE_REPO_PATH = "D:\\shopping\\file_repo";
	private final String div = System.getProperty("file.separator");

	public BaseController(){
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("linux")) {
			CURR_IMAGE_REPO_PATH = "/home/ec2-user/shopping/file_repo";
		}
	}

	protected List<ImagesSaveRequestDto> upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<ImagesSaveRequestDto> fileList= new ArrayList<ImagesSaveRequestDto>(); //파일들 정보수집용리스트
		Iterator<String> fileNames = multipartRequest.getFileNames();// 파일이름들 목록을 가져온다..
		while(fileNames.hasNext()){
			ImagesSaveRequestDto imageFileVO =new ImagesSaveRequestDto();
			String fileName = fileNames.next();
			imageFileVO.setFileType(fileName);
			MultipartFile mFile = multipartRequest.getFile(fileName);

			String originalFileName=mFile.getOriginalFilename();
			imageFileVO.setFileName(originalFileName);
			fileList.add(imageFileVO);

//			if(mFile.getSize()!=0){ //File Null Check
//				File file = new File(CURR_IMAGE_REPO_PATH +div+ fileName);
//
//				if (!new File(CURR_IMAGE_REPO_PATH+div+"temp").exists()) {
//					try{
//						new File(CURR_IMAGE_REPO_PATH+div+"temp").mkdir();
//					}
//					catch(Exception e){
//						e.getStackTrace();
//					}
//				}
//				if(! file.exists()){ //경로상에 파일이 존재하지 않을 경우
//					if(file.getParentFile().mkdirs()){ //경로에 해당하는 디렉토리들을 생성
//						file.createNewFile(); //이후 파일 생성
//					}
//				}
//				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH +div+"temp"+ div+originalFileName)); //임시로 저장된 multipartFile을 실제 파일로 전송
//			}
		}
		return fileList;
	}
	
	private void deleteFile(String fileName) {
		File file =new File(CURR_IMAGE_REPO_PATH+div+fileName);
		try{
			file.delete();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
//	@RequestMapping(value="/*.do" ,method={RequestMethod.POST,RequestMethod.GET})
//	protected  ModelAndView viewForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String viewName=(String)request.getAttribute("viewName");
//		ModelAndView mav = new ModelAndView(viewName);
//		return mav;
//	}
	
	
	protected String calcSearchPeriod(String fixedSearchPeriod){
		String beginDate=null;
		String endDate=null;
		String endYear=null;
		String endMonth=null;
		String endDay=null;
		String beginYear=null;
		String beginMonth=null;
		String beginDay=null;
		DecimalFormat df = new DecimalFormat("00");
		Calendar cal=Calendar.getInstance();
		
		endYear   = Integer.toString(cal.get(Calendar.YEAR));
		endMonth  = df.format(cal.get(Calendar.MONTH) + 1);
		endDay   = df.format(cal.get(Calendar.DATE));
		endDate = endYear +"-"+ endMonth +"-"+endDay;
		
		if(fixedSearchPeriod == null) {
			cal.add(cal.MONTH,-4);
		}else if(fixedSearchPeriod.equals("one_week")) {
			cal.add(Calendar.DAY_OF_YEAR, -7);
		}else if(fixedSearchPeriod.equals("two_week")) {
			cal.add(Calendar.DAY_OF_YEAR, -14);
		}else if(fixedSearchPeriod.equals("one_month")) {
			cal.add(cal.MONTH,-1);
		}else if(fixedSearchPeriod.equals("two_month")) {
			cal.add(cal.MONTH,-2);
		}else if(fixedSearchPeriod.equals("three_month")) {
			cal.add(cal.MONTH,-3);
		}else if(fixedSearchPeriod.equals("four_month")) {
			cal.add(cal.MONTH,-4);
		}
		
		beginYear   = Integer.toString(cal.get(Calendar.YEAR));
		beginMonth  = df.format(cal.get(Calendar.MONTH) + 1);
		beginDay   = df.format(cal.get(Calendar.DATE));
		beginDate = beginYear +"-"+ beginMonth +"-"+beginDay;
		System.out.println("calcSearchPeriod : "+beginDate+","+endDate);
		return "2015-05-24"+","+endDate;
	}
	
}
