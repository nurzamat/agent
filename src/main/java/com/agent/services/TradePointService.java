package com.agent.services;

import com.agent.entities.TradePoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TradePointService {

    Page<TradePoint> listAllByPage(Pageable pageable);

    Iterable<TradePoint> listAll();

    TradePoint getTradePointById(Integer id);

    TradePoint saveTradePoint(TradePoint tradePoint);

    void deleteTradePoint(TradePoint tradePoint);
}
