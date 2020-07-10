package com.hrm.notice.service.impl;

import com.hrm.commons.beans.Notice;
import com.hrm.notice.dao.INoticeDao;
import com.hrm.notice.service.INoticeService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class INoticeServiceImpl implements INoticeService {
    @Autowired
    INoticeDao iNoticeDao;
    @Override
    public List<Notice> findNotice(Notice notice, PageModel pageModel) {
        return iNoticeDao.selectNotice(notice,pageModel);
    }

    @Override
    public int findNoticeCount(Notice notice) {
        return iNoticeDao.selectNoticeCount(notice);
    }

    @Override
    public Notice selectNoticeById(Integer id) {
        return iNoticeDao.selectNoticeById(id);
    }

    @Override
    public int modifyNotice(Notice notice) {
        return iNoticeDao.updateNotice(notice);
    }

    @Override
    public int removeNotice(Integer[] ids) {
        return iNoticeDao.deleteNotice(ids);
    }

    @Override
    public int addNotice(Notice notice) {
        return iNoticeDao.insertNotice(notice);
    }
}
