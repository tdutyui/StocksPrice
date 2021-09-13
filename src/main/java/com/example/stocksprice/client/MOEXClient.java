package com.example.stocksprice.client;

import com.example.stocksprice.model.PriceHistory;
import com.example.stocksprice.model.Trades;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "moex", url = "https://iss.moex.com/iss")
public interface MOEXClient {
    @GetMapping("/engines/stock/markets/{market}/securities/{ticker}/trades.json")
    public Trades getLastPrice(@PathVariable String market, @PathVariable String ticker);

    @GetMapping("/history/engines/stock/markets/{market}/securities/{ticker}.json")
    public PriceHistory getHistory(@PathVariable String market, @PathVariable String ticker,
                                   @RequestParam String from, @RequestParam String till);
}
