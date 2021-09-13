
package com.example.stocksprice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceHistory {

    @JsonProperty("history")
    private Info info;
}
