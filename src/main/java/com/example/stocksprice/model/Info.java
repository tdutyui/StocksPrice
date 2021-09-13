
package com.example.stocksprice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Info {

    @JsonProperty("data")
    private List<List<String>> data = null;
}
