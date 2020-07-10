package com.hrm.document.handler;

import com.hrm.commons.beans.Document;
import com.hrm.commons.beans.User;
import com.hrm.document.service.IDocumentService;
import com.hrm.utils.PageModel;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentHandler {
    @Autowired
    IDocumentService iDocumentService;

    @RequestMapping("/findDocument")
    public String findDocument(@RequestParam(defaultValue = "1") int pageIndex, String title, Model model){
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        List<Document> documents = iDocumentService.findDocument(pageModel,title);

        int recordCount = iDocumentService.findDocumentCount(title);
        pageModel.setRocordCount(recordCount);
        model.addAttribute("documents",documents);
        model.addAttribute("pageModel",pageModel);
        model.addAttribute("title",title);
        return "/jsp/document/document.jsp";

    }

    @RequestMapping("/addDocument")
    public String addDocument(Document document, HttpSession session,Model model) throws IOException {
        //1.保存上传的文件到服务器
        String path = "G:/uploadtest";
        File file = new File(path);
        if (!file.exists()){//如果上传文件路径不存在
            file.mkdirs();//创建对应路径
        }
        //获取原始文件名称 重命名文件防止被覆盖
        String filename = System.currentTimeMillis() + "_" + document.getFile().getOriginalFilename();
        //定义保存的文件就是吧MultipartFile，保存到服务器对应地址
        document.getFile().transferTo(new File(path,filename));
        //2.数据库中插入对应数据，给document的filename复制
        document.setFilename(filename);
        //获取当前登录用户，也就是文档上传者
        User login_user = (User) session.getAttribute("login_user");
        document.setUser(login_user);
        //调用service方法插入文档对应信息
        int rows = iDocumentService.addDocument(document);
        if (rows>0){
            PageModel pageModel = new PageModel();
            int recordCount = iDocumentService.findDocumentCount(null);
            pageModel.setRocordCount(recordCount);
            int totalSize = pageModel.getTotalSize();
            //如果不使用redirect重定向，无法查询全部信息；
            return "redirect:/document/findDocument?pageIndex="+totalSize;
        }else{
            model.addAttribute("fail","文件上传失败!");
            return "/jsp/fail.jsp";
        }

    }

    @RequestMapping("/modifyDocument")
    public String modifyDocument(Document document,Model model,String flag,int pageIndex) throws IOException {
        if (flag == null) {
            //根据id查询文件信息
            document = iDocumentService.selectDocumentById(document.getId());
            model.addAttribute("document", document);
            model.addAttribute("pageIndex",pageIndex);
            return "/jsp/document/showUpdateDocument.jsp";
        } else {
            //判断修改文件信息时有没有上传新的文件，如果有就执行if,如果没有则不执行
            if (!document.getFile().isEmpty()){
                //在修改文件信息前，先判断该文件是否存在，存在则将服务器中存储的原文件删除，防止修改时只上传新的文件，而原来的文件不删除
                String path = "G:/uploadtest";
                //获取原始目标文件信息
                Document target = iDocumentService.selectDocumentById(document.getId());
                //判断原始文件是否存在
                File targetFile = new File(path,target.getFilename());
                if (targetFile.exists()){
                    //exists判断文件是否存在，存在就使用delete()删除
                    targetFile.delete();
                }

                //设置新上传文件的文件名
                String filename = System.currentTimeMillis() + "_" + document.getFile().getOriginalFilename();
                //把修改后接收的文件存储到服务器中
                document.getFile().transferTo(new File(path,filename));
                //往实体类中存入修改后文件的文件名
                document.setFilename(filename);
            }
             //将其余信息修改进数据库中
            int rows = iDocumentService.modifyDocumentById(document);
            if (rows>0){
                return "redirect:/document/findDocument?pageIndex="+pageIndex;
            }else {
                model.addAttribute("fail","修改文件信息失败！");
                return "/jsp/fail.jsp";
            }
        }

    }

    //文件下载  该方法返回类型是响应实体
    @RequestMapping("/downLoad")
    public ResponseEntity<Object> downLoad(int id, HttpServletRequest request) throws IOException {
        String path = "G:/uploadtest";
        Document targetDocument = iDocumentService.selectDocumentById(id);
        //获取要下载文件的文件名
        String filename = targetDocument.getFilename();
        //获取下载的目标文件
        File file = new File(path,filename);
        if(file.exists()){
            // filename = new String(filename.getBytes("UTF-8"),"iso8859-1");解决下载文件名中文乱码，但是不通用 字节回退
            filename = processFileName(request,filename);

            //设置响应头的响应内容类型
            HttpHeaders headers = new HttpHeaders();
            //响应消息头的的数据类型 ：   流文件
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //将其作为附件响应回去
            headers.setContentDispositionFormData("attachment",filename);
            //泛型(响应体内容，这里把文件变成字节数组返回)、MultiValueMap（消息头类型）、HTTPStatus(状态码)
            return new ResponseEntity<Object>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
        }else {
            String error = "下载文件不存在！";
            HttpHeaders headers = new HttpHeaders();
            //设置响应消息头的响应内容为html
            MediaType mediaType = new MediaType("text","html", Charset.forName("utf-8"));
            headers.setContentType(mediaType);
            return new ResponseEntity<Object>(error,headers,HttpStatus.OK);
        }

    }

    //删除文件
    @RequestMapping("removeDocument")
    public String removeDocument(Integer[] ids,Model model){
        String path = "G:/uploadtest";
        int rows = 0;
        //查找要删除的目标文件
        for(Integer id:ids){
            Document target = iDocumentService.selectDocumentById(id);
            File targetFile = new File(path,target.getFilename());
            //如果这个文件上传的文档存在在磁盘中，就从服务器中删除
            if(targetFile.exists()){
                targetFile.delete();
            }
            //删除文件对应的数据库信息  定义在循环中，每循环一个id，删除一个文件，也删除一个文件信息
            int i = iDocumentService.removeDocument(id);
            if(i>0){
                rows++;
            }
        }
        //当rows操作记录数等于数组ids的长度时，证明删除完成
        if (rows == ids.length){
            return "redirect:/document/findDocument";
        }else {
            model.addAttribute("fail","文档删除失败！");
            return "jsp/fail.jsp";
        }
    }

    //IE,Chrom,Firefox文件中乱码问题
    public  String processFileName(HttpServletRequest request, String fileNames) {
        String codedfilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
                    && -1 != agent.indexOf("Trident")) {// ie

                String name = null;

                codedfilename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等


                codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }
}
