package com.heh.fk.service.impl;

import com.heh.fk.dao.VideoCardDao;
import com.heh.fk.mode.VideoCardDO;
import com.heh.fk.service.VideoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoCardServiceImpl implements VideoCardService {

    @Autowired
    private VideoCardDao videoCardDao;

    @Override
    public List<VideoCardDO> list() {
        return videoCardDao.list();
    }

}
