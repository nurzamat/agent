package com.agent.api;

import com.agent.entities.TradePoint;
import com.agent.services.TradePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;


@RestController
@RequestMapping("/api/trade_points")
public class TradePointRestController {

    @Autowired
    private TradePointService tradePointService;

    /**
     * List all Trade points
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TradePoint>> listAllTradePoints() {
        List<TradePoint> tradePoints = (List<TradePoint>) tradePointService.listAll();
        if(tradePoints.isEmpty()){
            return new ResponseEntity<>(tradePoints, HttpStatus.NO_CONTENT);//or HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(tradePoints, HttpStatus.OK);
    }

    /**
     * Get Trade point by id
     */

    @RequestMapping(value = "/trade_point/{id}", method = RequestMethod.GET)
    public ResponseEntity<TradePoint> getTradePoint(@PathVariable("id") Integer id) {
        TradePoint tradePoint = tradePointService.getTradePointById(id);
        if (tradePoint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tradePoint, HttpStatus.OK);
    }

    /**
     * Create Trade point
     */

    @RequestMapping(value = "/trade_point/", method = RequestMethod.POST)
    public ResponseEntity<Void> createTradePoint(@RequestBody TradePoint tradePoint, UriComponentsBuilder ucBuilder) {
        tradePointService.saveTradePoint(tradePoint);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/trade_point/{id}").buildAndExpand(tradePoint.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Update Trade point
     */

    @RequestMapping(value = "/trade_point/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TradePoint> updateTradePoint(@PathVariable("id") Integer id, @RequestBody TradePoint tradePoint) {
        TradePoint currentTradePoint = tradePointService.getTradePointById(id);
        if (currentTradePoint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //todo
        currentTradePoint.setName(tradePoint.getName());
        currentTradePoint.setPhoneNumber(tradePoint.getPhoneNumber());
        currentTradePoint.setAddress(tradePoint.getAddress());
        currentTradePoint.setLatitude(tradePoint.getLatitude());
        currentTradePoint.setLongitude(tradePoint.getLongitude());
        tradePointService.saveTradePoint(currentTradePoint);
        return new ResponseEntity<>(currentTradePoint, HttpStatus.OK);
    }

    /**
     * Delete Trade point
     */

    @RequestMapping(value = "/trade_point/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TradePoint> deleteTradePoint(@PathVariable("id") Integer id) {
        TradePoint tradePoint = tradePointService.getTradePointById(id);
        if (tradePoint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //todo
        tradePointService.deleteTradePoint(tradePoint);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
