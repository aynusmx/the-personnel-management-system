package com.hrm.document.service;

import com.hrm.commons.beans.Document;
import com.hrm.utils.PageModel;

import java.util.List;

public interface IDocumentService {

    List<Document> findDocument(PageModel pageModel, String title);

    int findDocumentCount(String title);

    int addDocument(Document document);

    Document selectDocumentById(Integer id);

    int modifyDocumentById(Document document);

    int removeDocument(Integer id);
}
