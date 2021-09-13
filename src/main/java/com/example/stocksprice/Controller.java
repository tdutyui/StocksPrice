package com.example.stocksprice;

import com.example.stocksprice.client.MOEXClient;
import com.example.stocksprice.model.PriceHistory;
import com.example.stocksprice.model.Response;
import com.example.stocksprice.model.Data;
import com.example.stocksprice.model.TickerAndPrice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private MOEXClient client;

    @GetMapping("/{market}/{ticker}")
    public String getLastPrice(@PathVariable String market, @PathVariable String ticker) {
        long start = System.nanoTime();
        List<List<String>> trades = client.getLastPrice(market.toLowerCase(), ticker.toUpperCase())
                .getInfo().getData();
        String price = trades.get(trades.size() - 1).get(4);
        return responseToJson(ticker.toUpperCase(), price, System.nanoTime() - start);
    }

    @Cacheable("history")
    @GetMapping("/{market}/{ticker}/history")
    public Map<String, String> getHistory(@PathVariable String market, @PathVariable String ticker,
                                          @RequestParam String from, @RequestParam String till) {
        Map<String, String> history = new LinkedHashMap<>();
        PriceHistory priceHistory = client.getHistory(market.toLowerCase(), ticker.toUpperCase(), from, till);
        for (List<String> day: priceHistory.getInfo().getData()) {
            if (day.get(0).equals("TQBR")) {
                history.put(day.get(1), day.get(9));
            }
        }
        return history;
    }

    private String responseToJson(String ticker, String price, Long time) {
        Data data = new Data();
        TickerAndPrice responseData = new TickerAndPrice();
        Response resp = new Response();
        List<TickerAndPrice> tickerAndPrices = new ArrayList<>();

        responseData.setTicker(ticker);
        responseData.setLastPrice(price);
        tickerAndPrices.add(responseData);
        data.setData(tickerAndPrices);
        resp.setResp(data);
        resp.setResponseTime(time);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(resp);
    }
}
