package com.agent.services.impl;

import com.agent.entities.TradePoint;
import com.agent.repositories.TradePointRepository;
import com.agent.services.TradePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TradePointServiceImpl implements TradePointService {

    @Autowired
    private TradePointRepository tradePointRepository;

    @Override
    public Page<TradePoint> listAllByPage(Pageable pageable)
    {
        return tradePointRepository.findAll(pageable);
    }

    @Override
    public Iterable<TradePoint> listAll() {
        return tradePointRepository.findAll();
    }

    @Override
    public TradePoint getTradePointById(Integer id)
    {
        return tradePointRepository.findOne(id);
    }

    @Override
    public TradePoint saveTradePoint(TradePoint tradePoint) {
        return tradePointRepository.save(tradePoint);
    }

    @Override
    public void deleteTradePoint(TradePoint tradePoint) {
        tradePointRepository.delete(tradePoint);
    }
}
