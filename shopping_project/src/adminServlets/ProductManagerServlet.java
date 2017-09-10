package adminServlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import domain.Message;
import domain.PageBean;
import domain.Product;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import services.CS_service;
import services.impl.ServiceImpl;
import servlets.BaseServlet;
import sun.awt.RepaintArea;
import toolUtils.UUIDUtils;

@SuppressWarnings("all")
public class ProductManagerServlet extends BaseServlet {

	public void findAllProductsByPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("访问服务器成功..findAllProductsByPage...");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		ServiceImpl si = new CS_service();
		PageBean pb = si.finProductByPage(Integer.parseInt(page), Integer.parseInt(rows), null);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				return value == null ? "" : sd.format(value);
			}

			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				return null;
			}
		});
		Map<String, Object> map = new HashMap<>();
		map.put("total", pb.getCount());
		map.put("rows", pb.getPageList());
		response.getWriter().println(JSONObject.fromObject(map,jsonConfig).toString());

	}

	public void addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> map = new HashMap<>();
		Product p = new Product();
		p.setPid(UUIDUtils.getUUID());
	
		p.setPdate(new Date().toString());
		ServiceImpl si = new CS_service();
		FileItemFactory factory = new DiskFileItemFactory();

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		try {
			List fileitems = upload.parseRequest(request);
			Iterator iter = fileitems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {
					// 普通字段
					String name = item.getFieldName();
					String value = item.getString("utf-8");
					map.put(name, value);
				} else {
					// 上传文件字段
					String name = item.getFieldName();
					String filestr = item.getName();
					String filename = UUIDUtils.getUUID() +"."+ FilenameUtils.getExtension(filestr);
					map.put(name, "products/1/" + filename);
					
					String realPath = this.getServletContext().getRealPath("products/1");
				    File realF = new File(realPath);
					if(!realF.exists()){
						realF.mkdir();
					}
					
//					FileOutputStream fout = new FileOutputStream(realPath+"/"+filename);
//					InputStream is = item.getInputStream();
//					int len = 0;
//					byte[] buf = new byte[1024];
//					while ((len = is.read(buf)) != -1) {
//						fout.write(buf, 0, len);
//					}
//					is.close();
//					fout.close();

//					//创建一个文件对象
					File file = new File(realPath+"/"+filename);
					
					//写入磁盘
					item.write(file);
					item.delete();

					
				}
			}
			BeanUtils.populate(p, map);
			boolean issuccess = si.addProduct(p);
			Message msg = new Message();
           if(issuccess){
        	   
        	   msg.setMsg("商品添加成功!");
        	   
           }else{
        	   
        	   msg.setMsg("商品添加失败!");
           }
			response.getWriter().println(JSONObject.fromObject(msg).toString());
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void delProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		ServiceImpl si = new CS_service();
		boolean issuccess =  si.delProduct(pid);
		Message msg = new Message();
		if(issuccess){
			msg.setMsg("删除商品成功");
		}else{
			 msg.setMsg("删除商品异常");
		}
		
		response.getWriter().print(JSONObject.fromObject(msg).toString());
		
		
	}
	public void finPWithPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		ServiceImpl si = new CS_service();
	    Product p = si.findProductByPid(pid);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				return value == null ? "" : sd.format(value);
			}

			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				return null;
			}
		});
		System.out.println("************xxxxxxxxxxxx*******"+p.toString());
		response.getWriter().print(JSONObject.fromObject(p,jsonConfig).toString());
		
	}
	
	public void updateProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> map = new HashMap<>();
		Product p = new Product();
		ServiceImpl si = new CS_service();
		FileItemFactory factory = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List fileitems = upload.parseRequest(request);
			Iterator iter = fileitems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {
					// 普通字段
					String name = item.getFieldName();
					String value = item.getString("utf-8");
					map.put(name, value);
				} else {
					// 上传文件字段
					String name = item.getFieldName();
					String filestr = item.getName();
					String filename = UUIDUtils.getUUID() +"."+ FilenameUtils.getExtension(filestr);
					map.put(name, "products/1/" + filename);
					
					String realPath = this.getServletContext().getRealPath("products/1");
				    File realF = new File(realPath);
					if(!realF.exists()){
						realF.mkdir();
					}

//					//创建一个文件对象
					File file = new File(realPath+"/"+filename);
					
					//写入磁盘
					item.write(file);
					item.delete();
					
				}
			}
			
		
		BeanUtils.populate(p, map);
	    boolean issuccess = si.updateProduct(p);
	    Message msg = new Message();
		if(issuccess){
			msg.setMsg("编辑商品成功");
		}else{
			 msg.setMsg("编辑商品异常");
		}
		System.out.println("编辑:"+p.toString());
		response.getWriter().print(JSONObject.fromObject(msg).toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
