package com.hrm.document.service.impl;

import com.hrm.commons.beans.Document;
import com.hrm.document.dao.IDocumentDao;
import com.hrm.document.service.IDocumentService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IDocumentServiceImpl implements IDocumentService {
    @Autowired
    IDocumentDao iDocumentDao;
    @Override
    public List<Document> findDocument(PageModel pageModel, String title) {
        Map map = new HashMap();
        map.put("title",title);
        map.put("start",pageModel.getFirstLimitParam());
        map.put("pageSize",pageModel.getPageSize());
        return iDocumentDao.selectDocument(map);
    }

    @Override
    public int findDocumentCount(String title) {
        return iDocumentDao.selectDocumentCount(title);
    }

    @Override
    public int addDocument(Document document) {
        return iDocumentDao.insertDocument(document);
    }

    @Override
    public Document selectDocumentById(Integer id) {
        return iDocumentDao.selectDocumentById(id);
    }

    @Override
    public int modifyDocumentById(Document document) {
        return iDocumentDao.updateDocumentById(document);
    }

    @Override
    public int removeDocument(Integer id) {
        return iDocumentDao.deleteDocument(id);
    }
}
