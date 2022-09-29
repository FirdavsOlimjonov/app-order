package uz.pdp.appproduct.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchingDTO {

    private List<String> columns = new ArrayList<>();

    private String value = "";
}
