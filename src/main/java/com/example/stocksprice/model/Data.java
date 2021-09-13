package com.example.stocksprice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Data {

    private List<TickerAndPrice> data;
}
